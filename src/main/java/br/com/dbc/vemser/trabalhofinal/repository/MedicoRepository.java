package br.com.dbc.vemser.trabalhofinal.repository;

import br.com.dbc.vemser.trabalhofinal.dto.MedicoDTO;
import br.com.dbc.vemser.trabalhofinal.dto.MedicoPersonalizadoDTO;
import br.com.dbc.vemser.trabalhofinal.entity.MedicoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicoRepository extends JpaRepository<MedicoEntity, Integer> {

    @Query("SELECT m FROM Medico m JOIN m.especialidadeEntity e JOIN m.usuarioEntity u WHERE m.idMedico = :idMedico")
    MedicoDTO findMedicoComEspecialidadeEUsuario(Integer idMedico);

    @Query("select new br.com.dbc.vemser.trabalhofinal.dto.MedicoPersonalizadoDTO(" +
            "m.idMedico, " +
            "m.crm, " +
            "m.idEspecialidade, " +
            "m.idUsuario, " +
            "es.valor, " +
            "es.nomeEspecialidade, " +
            "u.cpf, " +
            "u.email, " +
            "u.nome ," +
            "u.tipoUsuario, " +
            "u.contatos, " +
            "u.cep, " +
            "u.numero) " +
            "from Medico m" +
            " left join m.usuarioEntity u" +
            " left join m.especialidadeEntity es" +
            " where m.idMedico = :id"
    )
    MedicoPersonalizadoDTO getByIdPersonalizado(Integer id);

}
