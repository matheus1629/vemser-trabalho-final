package br.com.dbc.vemser.trabalhofinal.repository;

<<<<<<< HEAD
import br.com.dbc.vemser.trabalhofinal.entity.MedicoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

=======

import br.com.dbc.vemser.trabalhofinal.dto.MedicoDTO;
import br.com.dbc.vemser.trabalhofinal.entity.MedicoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

>>>>>>> cf9164b22fed28fc288384cc71d004dd1541212d
@Repository
public interface MedicoRepository extends JpaRepository<MedicoEntity, Integer> {

    @Query("SELECT m FROM Medico m JOIN m.especialidadeEntity e JOIN m.usuarioEntity u WHERE m.idMedico = :idMedico")
    MedicoDTO findMedicoComEspecialidadeEUsuario(Integer idMedico);
}
