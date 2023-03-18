package br.com.dbc.vemser.trabalhofinal.repository;

import br.com.dbc.vemser.trabalhofinal.dto.ClientePersonalizadoDTO;
import br.com.dbc.vemser.trabalhofinal.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, Integer> {

    @Query("select new br.com.dbc.vemser.trabalhofinal.dto.ClientePersonalizadoDTO(" +
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
    ClientePersonalizadoDTO clientePersonalizado(Integer id);


}
