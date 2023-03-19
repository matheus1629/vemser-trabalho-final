package br.com.dbc.vemser.trabalhofinal.repository;

import br.com.dbc.vemser.trabalhofinal.dto.AgendamentoDTO;
import br.com.dbc.vemser.trabalhofinal.entity.AgendamentoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface AgendamentoRepository extends JpaRepository<AgendamentoEntity, Integer> {

    @Query("select a from Agendamento a")
    Page<AgendamentoEntity> findAllPaginado(Pageable pageable);

    @Query("SELECT a FROM Agendamento a")
    List<AgendamentoDTO> findAgendamentoWithMedicoNomeAndClienteNome();


    @Query(value = "Select a from Agendamento a where idCliente = :id")
    List<AgendamentoEntity> findAllByIdCliente(Integer id);


    @Query(value = "Select a from Agendamento a where idMedico = :id")
    List<AgendamentoEntity> findAllByIdMedico(Integer id);
}
