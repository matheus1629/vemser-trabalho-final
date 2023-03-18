package br.com.dbc.vemser.trabalhofinal.repository;

import br.com.dbc.vemser.trabalhofinal.dto.AgendamentoDTO;
import br.com.dbc.vemser.trabalhofinal.entity.AgendamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgendamentoRepository extends JpaRepository<AgendamentoEntity, Integer> {

//    @Query("SELECT a, c.nome AS nomeCliente, m.nome AS nomeMedico FROM Agendamento a LEFT JOIN a.clienteEntity c LEFT JOIN a.medicoEntity m")
//    List<AgendamentoDTO> findAgendamentoWithMedicoNomeAndClienteNome();
}
