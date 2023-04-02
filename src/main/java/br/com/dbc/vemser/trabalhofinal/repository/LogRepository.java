package br.com.dbc.vemser.trabalhofinal.repository;

import br.com.dbc.vemser.trabalhofinal.entity.LogEntity;
import br.com.dbc.vemser.trabalhofinal.entity.TipoLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LogRepository extends MongoRepository<LogEntity, String> {

    List<LogEntity> findAllByIdSolicitacao(String idSolicitacao);
    List<LogEntity> findAllByIdAgendamento(Integer idAgendamento);
    List<LogEntity> findAllByIdUsuario(Integer idUsuario);
    List<LogEntity> findByDataHoraBetween(LocalDate localDateInicio, LocalDate localDateFim);
    List<LogEntity> findAllByTipoLog(TipoLog tipoLog);

}
