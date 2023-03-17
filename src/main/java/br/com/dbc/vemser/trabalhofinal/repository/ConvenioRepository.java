package br.com.dbc.vemser.trabalhofinal.repository;

import br.com.dbc.vemser.trabalhofinal.entity.ConvenioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConvenioRepository extends JpaRepository<ConvenioEntity, Integer> {

}
