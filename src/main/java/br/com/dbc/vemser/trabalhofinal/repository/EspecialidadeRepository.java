package br.com.dbc.vemser.trabalhofinal.repository;

import br.com.dbc.vemser.trabalhofinal.entity.EspecialidadeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EspecialidadeRepository extends JpaRepository<EspecialidadeEntity, Integer> {

}
