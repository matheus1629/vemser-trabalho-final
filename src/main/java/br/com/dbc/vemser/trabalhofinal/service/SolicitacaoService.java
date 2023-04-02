package br.com.dbc.vemser.trabalhofinal.service;

import br.com.dbc.vemser.trabalhofinal.dto.solicitacao.SolicitacaoCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.solicitacao.SolicitacaoDTO;
import br.com.dbc.vemser.trabalhofinal.entity.QSolicitacaoEntity;
import br.com.dbc.vemser.trabalhofinal.entity.SolicitacaoEntity;
import br.com.dbc.vemser.trabalhofinal.entity.StatusSolicitacao;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.repository.SolicitacaoReposiroty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SolicitacaoService {
    private final SolicitacaoReposiroty solicitacaoReposiroty;
    private final ClienteService clienteService;
    private final ObjectMapper objectMapper;

    public SolicitacaoDTO create(SolicitacaoCreateDTO solicitacaoCreateDTO) throws RegraDeNegocioException {
        solicitacaoCreateDTO.setIdCliente(clienteService.recuperarCliente().getIdCliente());
        solicitacaoCreateDTO.setStatusSolicitacao(StatusSolicitacao.PENDENTE);

        var solicitacaoEntity = new SolicitacaoEntity();
        BeanUtils.copyProperties(solicitacaoCreateDTO, solicitacaoEntity);

        solicitacaoReposiroty.save(solicitacaoEntity);

        var solicitacaoDTO = new SolicitacaoDTO();
        BeanUtils.copyProperties(solicitacaoEntity, solicitacaoDTO);

        return solicitacaoDTO;

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

        builder.and(solicitacao.dataHora.between(dataHoraInicio, dataHoraFim));

        List<SolicitacaoEntity> results = (List<SolicitacaoEntity>) solicitacaoReposiroty.findAll(builder.getValue());

        return results.stream().map(solicitacaoEntity ->  objectMapper.convertValue(solicitacaoEntity, SolicitacaoDTO.class)).collect(Collectors.toList());
    }


}


