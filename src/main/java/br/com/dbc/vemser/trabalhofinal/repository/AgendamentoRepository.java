package br.com.dbc.vemser.trabalhofinal.repository;

import br.com.dbc.vemser.trabalhofinal.dto.AgendamentoClientePersonalizadoDTO;
import br.com.dbc.vemser.trabalhofinal.dto.AgendamentoDTO;
import br.com.dbc.vemser.trabalhofinal.entity.AgendamentoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Repository
public interface AgendamentoRepository extends JpaRepository<AgendamentoEntity, Integer> {

    @Query("select a from Agendamento a")
    Page<AgendamentoEntity> findAllPaginado(Pageable pageable);

    @Query("SELECT a FROM Agendamento a")
    List<AgendamentoDTO> findAgendamentoWithMedicoNomeAndClienteNome();

    @Query("select new br.com.dbc.vemser.trabalhofinal.dto.AgendamentoClientePersonalizadoDTO(" +
            "c.idCliente," +
            "c.idConvenio," +
            "c.idUsuario," +
            "con.cadastroOrgaoRegulador," +
            "con.taxaAbatimento," +
            "u.cpf," +
            "u.email," +
            "u.nome," +
            "u.tipoUsuario," +
            "u.contatos," +
            "u.cep," +
            "u.numero)" +
            "from Cliente c" +
            " left join c.usuarioEntity u " +
            " left join c.convenioEntity con " +
            "where c.idCliente = :id"
    )
    AgendamentoClientePersonalizadoDTO clientePersonalizado(Integer id);

    @Query(value = "Select a from Agendamento a where idCliente = :id")
    List<AgendamentoEntity> findAllByIdCliente(Integer id);


}
