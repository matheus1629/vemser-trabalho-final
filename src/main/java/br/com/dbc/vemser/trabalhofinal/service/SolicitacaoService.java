package br.com.dbc.vemser.trabalhofinal.service;

import br.com.dbc.vemser.trabalhofinal.dto.solicitacao.SolicitacaoCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.solicitacao.SolicitacaoDTO;
import br.com.dbc.vemser.trabalhofinal.dto.solicitacao.SolicitacaoPesquisaDTO;
import br.com.dbc.vemser.trabalhofinal.entity.SolicitacaoEntity;
import br.com.dbc.vemser.trabalhofinal.entity.StatusSolicitacao;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.repository.SolicitacaoReposiroty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.model.Filters;
import lombok.RequiredArgsConstructor;
import org.bson.conversions.Bson;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
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

    public List<SolicitacaoDTO> list(Integer idMedico, Integer idCliente, LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim, StatusSolicitacao statusSolicitacao) {

        if (dataHoraInicio == null) {
            dataHoraInicio = (LocalDateTime.of(2000, 01, 01, 00, 00));
        }

        if (dataHoraFim == null) {
            dataHoraFim = (LocalDateTime.of(3000, 01, 01, 00, 00));
        }

//        Integer idMedico = solicitacaoPesquisaDTO.getIdMedico();
//        Integer idCliente = solicitacaoPesquisaDTO.getIdCliente();
//        String statusSolicitacao = solicitacaoPesquisaDTO.getStatusSolicitacao().toString();
//        LocalDateTime dataHoraInicio = solicitacaoPesquisaDTO.getDataHoraInicio();
//        LocalDateTime dataHoraFim = solicitacaoPesquisaDTO.getDataHoraFim();

//        Bson query = Filters.and(
//                Filters.gte("dataHora", dataHoraInicio),
//                Filters.lt("dataHora", dataHoraFim)
//        );
//
//        if (idMedico != null) {
//            query = Filters.and(query, Filters.eq("idMedico", idMedico));
//        }
//
//        if (idCliente != null) {
//            query = Filters.and(query, Filters.eq("idCliente", idCliente));
//        }
//
//        if (statusSolicitacao != null) {
//            query = Filters.and(query, Filters.eq("statusSolicitacao", statusSolicitacao));
//        }
//        ZoneId zoneId = ZoneId.systemDefault();
//        Date dateInicio = Date.from(dataHoraInicio.atZone(zoneId).toInstant());
//        Date dateFim = Date.from(dataHoraFim.atZone(zoneId).toInstant());

        List<SolicitacaoDTO> buscaPersonalizada = solicitacaoReposiroty.findByDataHoraBetweenAndIdMedicoAndIdClienteAndStatusSolicitacao(
                        dataHoraInicio, dataHoraFim, idMedico, idCliente, statusSolicitacao.toString()
                ).stream().map(solicitacaoEntity -> objectMapper.convertValue(solicitacaoEntity, SolicitacaoDTO.class))
                .collect(Collectors.toList());


        return buscaPersonalizada;
       /*
        if (solicitacaoPesquisaDTO.getIdMedico() == null && solicitacaoPesquisaDTO.getIdCliente() == null) {
            solicitacaoReposiroty.findIdMedicoIdClienteIsNull(solicitacaoPesquisaDTO);
        }
        */
    }

    public List<SolicitacaoEntity> resgatarTodasSolicitacoes() {
        return solicitacaoReposiroty.findAll();
    }



}
