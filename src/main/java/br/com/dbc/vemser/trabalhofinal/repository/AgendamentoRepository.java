package br.com.dbc.vemser.trabalhofinal.repository;

import br.com.dbc.vemser.trabalhofinal.entity.AgendamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendamentoRepository extends JpaRepository<AgendamentoEntity, Integer> {

}
