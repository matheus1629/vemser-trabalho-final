package br.com.dbc.vemser.trabalhofinal.repository;

import br.com.dbc.vemser.trabalhofinal.entity.AgendamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgendamentoRepository extends JpaRepository<AgendamentoEntity, Integer> {

    List<AgendamentoEntity> findAllByIdCliente(Integer id);

    List<AgendamentoEntity> findAllByIdMedico(Integer id);

}
