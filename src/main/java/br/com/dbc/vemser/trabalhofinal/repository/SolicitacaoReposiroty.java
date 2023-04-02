package br.com.dbc.vemser.trabalhofinal.repository;

import br.com.dbc.vemser.trabalhofinal.dto.solicitacao.SolicitacaoDTO;
import br.com.dbc.vemser.trabalhofinal.dto.solicitacao.SolicitacaoPesquisaDTO;
import br.com.dbc.vemser.trabalhofinal.entity.SolicitacaoEntity;
import br.com.dbc.vemser.trabalhofinal.entity.StatusSolicitacao;
import org.bson.conversions.Bson;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public interface SolicitacaoReposiroty extends MongoRepository<SolicitacaoEntity, String> {

    List<SolicitacaoEntity> findByDataHoraBetweenAndIdMedicoAndIdClienteAndStatusSolicitacao(
            LocalDateTime dataHoraInicio,
            LocalDateTime dataHoraFim,
            Integer idMedico,
            Integer idCliente,
            String statusSolicitacao
    );


}
