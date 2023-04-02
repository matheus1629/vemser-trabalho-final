package br.com.dbc.vemser.trabalhofinal.repository;

import br.com.dbc.vemser.trabalhofinal.entity.SolicitacaoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitacaoReposiroty extends MongoRepository<SolicitacaoEntity, String>, QuerydslPredicateExecutor<SolicitacaoEntity> {

}
