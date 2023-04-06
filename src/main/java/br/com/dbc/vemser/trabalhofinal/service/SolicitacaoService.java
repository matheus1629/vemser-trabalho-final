package br.com.dbc.vemser.trabalhofinal.service;

import br.com.dbc.vemser.trabalhofinal.dto.log.LogCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.solicitacao.SolicitacaoCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.solicitacao.SolicitacaoDTO;
import br.com.dbc.vemser.trabalhofinal.entity.*;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.repository.SolicitacaoReposiroty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.core.BooleanBuilder;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SolicitacaoService {
    private final SolicitacaoReposiroty solicitacaoReposiroty;
    private final ClienteService clienteService;
    private final EmailService emailService;
    private final UsuarioService usuarioService;
    private final ObjectMapper objectMapper;
    private final MedicoService medicoService;
    private final LogService logService;

    public SolicitacaoDTO create(SolicitacaoCreateDTO solicitacaoCreateDTO) throws RegraDeNegocioException, JsonProcessingException {
        medicoService.getMedico(solicitacaoCreateDTO.getIdMedico()); //verifica se o médico existe

        solicitacaoCreateDTO.setIdCliente(clienteService.recuperarCliente().getIdCliente());
        solicitacaoCreateDTO.setStatusSolicitacao(StatusSolicitacao.PENDENTE);

        var solicitacaoEntity = new SolicitacaoEntity();
        BeanUtils.copyProperties(solicitacaoCreateDTO, solicitacaoEntity);

        solicitacaoReposiroty.save(solicitacaoEntity);

        emailService.producerSolicitacaoEmail(solicitacaoEntity, TipoEmail.SOLICITACAO_CRIADA);

        var solicitacaoDTO = new SolicitacaoDTO();
        BeanUtils.copyProperties(solicitacaoEntity, solicitacaoDTO);

        return solicitacaoDTO;
    }

    public void reprovarSolicitacao(SolicitacaoEntity solicitacaoEntity){
        solicitacaoReposiroty.save(solicitacaoEntity);
        LogCreateDTO logCreateDTO = new LogCreateDTO();
        logCreateDTO.setIdAgendamento(null);
        logCreateDTO.setIdSolicitacao(solicitacaoEntity.getIdSoliciatacao());
        logCreateDTO.setIdUsuario(usuarioService.getIdLoggedUser());
        logCreateDTO.setDataHora(LocalDateTime.now());
        logCreateDTO.setTipoLog(TipoLog.REPROVACAO_SOLICITACAO);

        logService.salvarLog(logCreateDTO);
    }

    public void aprovarSolicitacao(SolicitacaoEntity solicitacaoEntity){
        solicitacaoReposiroty.save(solicitacaoEntity);
    }

    public SolicitacaoEntity getSolicitacao(String idSolicitacao) throws RegraDeNegocioException {
        return solicitacaoReposiroty.findById(idSolicitacao)
                .orElseThrow(() -> new RegraDeNegocioException("Esta solicitação não existe!"));
    }

    public List<SolicitacaoDTO> findSolicitacoes(
            Integer idMedico, Integer idCliente, LocalDateTime dataHoraInicio, LocalDateTime
            dataHoraFim, StatusSolicitacao statusSolicitacao
    ) {

        if (dataHoraInicio == null) {
            dataHoraInicio = (LocalDateTime.of(2000, 01, 01, 00, 00));
        }

        if (dataHoraFim == null) {
            dataHoraFim = (LocalDateTime.of(3000, 01, 01, 00, 00));
        }

        QSolicitacaoEntity solicitacao = QSolicitacaoEntity.solicitacaoEntity;
        BooleanBuilder builder = new BooleanBuilder();

        if (idMedico != null) {
            builder.and(solicitacao.idMedico.eq(idMedico));
        }

        if (idCliente != null) {
            builder.and(solicitacao.idCliente.eq(idCliente));
        }

        if (statusSolicitacao != null) {
            builder.and(solicitacao.statusSolicitacao.eq(statusSolicitacao));
        }

        builder.and(solicitacao.dataHorario.between(dataHoraInicio, dataHoraFim));

        List<SolicitacaoEntity> results = (List<SolicitacaoEntity>) solicitacaoReposiroty.findAll(builder.getValue());

        return results.stream().map(solicitacaoEntity ->  objectMapper.convertValue(solicitacaoEntity, SolicitacaoDTO.class)).collect(Collectors.toList());
    }
}


