package br.com.dbc.vemser.trabalhofinal.repository;

import br.com.dbc.vemser.trabalhofinal.dto.AgendamentoDTO;
import br.com.dbc.vemser.trabalhofinal.entity.AgendamentoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface AgendamentoRepository extends JpaRepository<AgendamentoEntity, Integer> {

    @Query("select a from Agendamento a")
    Page<AgendamentoEntity> findAllPaginado(Pageable pageable);

    @Query("SELECT a FROM Agendamento a")
    List<AgendamentoDTO> findAgendamentoWithMedicoNomeAndClienteNome();
}
