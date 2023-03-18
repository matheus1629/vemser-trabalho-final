package br.com.dbc.vemser.trabalhofinal.repository;

import br.com.dbc.vemser.trabalhofinal.entity.AgendamentoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

@Repository
public interface AgendamentoRepository extends JpaRepository<AgendamentoEntity, Integer> {

//    @Query("SELECT a, c.nome AS nomeCliente, m.nome AS nomeMedico FROM Agendamento a LEFT JOIN a.clienteEntity c LEFT JOIN a.medicoEntity m")
//    List<AgendamentoDTO> findAgendamentoWithMedicoNomeAndClienteNome();

    @Query("select a from Agendamento a")
    Page<AgendamentoEntity> findAllPaginado(Pageable pageable);
}
