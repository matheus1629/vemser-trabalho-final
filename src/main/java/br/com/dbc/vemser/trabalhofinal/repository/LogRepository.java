package br.com.dbc.vemser.trabalhofinal.repository;

import br.com.dbc.vemser.trabalhofinal.entity.LogEntity;
import br.com.dbc.vemser.trabalhofinal.entity.TipoLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LogRepository extends MongoRepository<LogEntity, String> {


    List<LogEntity> findAllByIdSolicitacao(Integer idSolicitacao);

    List<LogEntity> findAllByIdAgendamento(Integer idAgendamento);

    List<LogEntity> findAllByIdUsuario(Integer idUsuario);
/*
    @Query("{ dataHora : { $gte : { $trunc : { date : ?0 , unit : 'day' } } , $lt : { $trunc : { date : ?0 , unit : 'day' , 'add' : 1 } } } }")
    List<LogEntity> findByDataHora(LocalDate data);
*/
    List<LogEntity> findByDataHora(LocalDate data);

    List<LogEntity> findAllByTipoLog(TipoLog tipoLog);

}
