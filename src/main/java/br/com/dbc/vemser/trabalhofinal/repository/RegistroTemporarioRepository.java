package br.com.dbc.vemser.trabalhofinal.repository;

import br.com.dbc.vemser.trabalhofinal.entity.RegistroTemporarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface RegistroTemporarioRepository extends JpaRepository<RegistroTemporarioEntity, Integer> {

    Optional<RegistroTemporarioEntity> findByIdUsuario(Integer id);


}
