package br.com.dbc.vemser.trabalhofinal.repository;

import br.com.dbc.vemser.trabalhofinal.dto.solicitacao.SolicitacaoDTO;
import br.com.dbc.vemser.trabalhofinal.dto.solicitacao.SolicitacaoPesquisaDTO;
import br.com.dbc.vemser.trabalhofinal.entity.SolicitacaoEntity;
import br.com.dbc.vemser.trabalhofinal.entity.StatusSolicitacao;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface SolicitacaoReposiroty extends MongoRepository<SolicitacaoEntity, String> {


    @Aggregation(pipeline = {
            "{$match:  {idMedico: ?0, $expr: { $ne: ['idMedico', null] } }}",
            "{$match:  {idCliente: ?1, $expr: { $ne: ['idCliente', null] } }}",
            "{$match:  {StatusSolicitacao: ?2, $expr: { $ne: ['StatusSolicitacao', null] } }}",
            "{$match:  {dataHoraInicio: ?3, $gte: 'dataHoraInicio'}}",
            "{$match:  {dataHoraFim: ?4, $lte: 'dataHoraFim'}}",
            "{$group:  {_id: {idSoliciatacao: '$idSoliciatacao', idMedico:  '$idMedico', idCliente:  '$idCliente', motivo:  '$motivo', dataHora:  '$dataHora', statusSolicitacao:  '$statusSolicitacao'} }}"
    })
    SolicitacaoDTO findByCustomSearch(Integer idMedico, Integer idCliente, StatusSolicitacao statusSolicitacao, LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim);
}
