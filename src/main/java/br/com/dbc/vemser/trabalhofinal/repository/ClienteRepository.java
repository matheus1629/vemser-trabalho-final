package br.com.dbc.vemser.trabalhofinal.repository;

import br.com.dbc.vemser.trabalhofinal.dto.ClienteCompletoDTO;
import br.com.dbc.vemser.trabalhofinal.entity.ClienteEntity;
import br.com.dbc.vemser.trabalhofinal.entity.MedicoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, Integer> {

    @Query("select new br.com.dbc.vemser.trabalhofinal.dto.ClienteCompletoDTO(" +
            "c.idCliente, " +
            "c.idConvenio, " +
            "c.idUsuario, " +
            "con.cadastroOrgaoRegulador, " +
            "con.taxaAbatimento, " +
            "u.cpf, " +
            "u.email, " +
            "u.nome, " +
            "ca.nomeCargo, " +
            "u.contatos, " +
            "u.cep, " +
            "u.numero) " +
            "from Cliente c" +
            " left join c.usuarioEntity u " +
            " left join c.convenioEntity con " +
            " left join c.usuarioEntity.cargoEntity ca" +
            " where c.idCliente = :id"
    )
    Optional<ClienteCompletoDTO> getByIdPersonalizado(Integer id);

    @Query("select new br.com.dbc.vemser.trabalhofinal.dto.ClienteCompletoDTO(" +
            "c.idCliente, " +
            "c.idConvenio, " +
            "c.idUsuario, " +
            "con.cadastroOrgaoRegulador, " +
            "con.taxaAbatimento, " +
            "u.cpf, " +
            "u.email, " +
            "u.nome, " +
            "ca.nomeCargo, " +
            "u.contatos, " +
            "u.cep, " +
            "u.numero) " +
            "from Cliente c" +
            " left join c.usuarioEntity u " +
            " left join c.usuarioEntity.cargoEntity ca" +
            " left join c.convenioEntity con "
    )
    Page<ClienteCompletoDTO> listarFull(Pageable pageable);

    @Query("SELECT c from Cliente c where c.idCliente = :id and c.usuarioEntity.ativo = 1")
    Optional<ClienteEntity> findById(Integer id);

    ClienteEntity getClienteEntityByIdUsuario(Integer idLoggedUser);
}
