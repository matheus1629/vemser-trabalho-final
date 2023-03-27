package br.com.dbc.vemser.trabalhofinal.repository;

import br.com.dbc.vemser.trabalhofinal.dto.medico.MedicoCompletoDTO;
import br.com.dbc.vemser.trabalhofinal.entity.MedicoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicoRepository extends JpaRepository<MedicoEntity, Integer> {

    @Query("select new br.com.dbc.vemser.trabalhofinal.dto.medico.MedicoCompletoDTO(" +
            " m.idMedico, " +
            " m.crm, " +
            " m.idEspecialidade, " +
            " m.idUsuario, " +
            " es.valor, " +
            " es.nomeEspecialidade, " +
            " u.cpf, " +
            " u.email, " +
            " u.nome ," +
            " ca.nomeCargo, " +
            " u.contatos, " +
            " u.cep, " +
            " u.numero) " +
            " from Medico m" +
            " left join m.usuarioEntity u" +
            " left join m.especialidadeEntity es" +
            " left join u.cargoEntity ca" +
            " where m.idMedico = :id" +
            " and m.usuarioEntity.ativo = 1"
    )
    Optional<MedicoCompletoDTO> getByIdPersonalizado(Integer id);

    @Query("select new br.com.dbc.vemser.trabalhofinal.dto.medico.MedicoCompletoDTO(" +
            " m.idMedico, " +
            " m.crm, " +
            " m.idEspecialidade, " +
            " m.idUsuario, " +
            " es.valor, " +
            " es.nomeEspecialidade, " +
            " u.cpf, " +
            " u.email, " +
            " u.nome," +
            " ca.nomeCargo, " +
            " u.contatos, " +
            " u.cep, " +
            " u.numero) " +
            " from Medico m" +
            " left join m.usuarioEntity u" +
            " left join u.cargoEntity ca" +
            " left join m.especialidadeEntity es" +
            " where m.usuarioEntity.ativo = 1"
    )
    Page<MedicoCompletoDTO> listarFull(Pageable pageable);


    @Query("SELECT m from Medico m where m.idMedico = :id and m.usuarioEntity.ativo = 1")
    Optional<MedicoEntity> findById(Integer id);

    MedicoEntity getMedicoEntityByIdUsuario(Integer idLoggedUser);
}
