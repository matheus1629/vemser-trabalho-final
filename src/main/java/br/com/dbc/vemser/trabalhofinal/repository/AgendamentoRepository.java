package br.com.dbc.vemser.trabalhofinal.repository;

import br.com.dbc.vemser.trabalhofinal.dto.AgendamentoDTO;
import br.com.dbc.vemser.trabalhofinal.dto.AgendamentoDadosDTO;
import br.com.dbc.vemser.trabalhofinal.entity.AgendamentoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgendamentoRepository extends JpaRepository<AgendamentoEntity, Integer> {

    @Query("select a from Agendamento a")
    Page<AgendamentoEntity> findAllPaginado(Pageable pageable);

//    @Query("select new br.com.dbc.vemser.trabalhofinal.dto.AgendamentoDTO(" +
//            "a.idAgendamento, " +
//            "a.idCliente, " +
//            "a.idMedico, " +
//            "a.tratamento, " +
//            "a.exame, " +
//            "a.dataHorario)" +
//            "from Agendamento a" +
//            " left join a.clienteEntity c" +
//            " left join a.medicoEntity m"
//    )
//    List<AgendamentoDTO> buscarAgendamentoCompleto();

    @Query(value = "Select a from Agendamento a where a.idCliente = :id")
    List<AgendamentoEntity> findAllByIdCliente(Integer id);

    @Query(value = "Select a from Agendamento a where a.idMedico = :id")
    List<AgendamentoEntity> findAllByIdMedico(Integer id);


}
