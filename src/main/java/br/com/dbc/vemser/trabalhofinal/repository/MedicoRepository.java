package br.com.dbc.vemser.trabalhofinal.repository;

import br.com.dbc.vemser.trabalhofinal.dto.EspecialidadeDTO;
import br.com.dbc.vemser.trabalhofinal.dto.MedicoCompletoDTO;
import br.com.dbc.vemser.trabalhofinal.dto.UsuarioDTO;
import br.com.dbc.vemser.trabalhofinal.entity.MedicoEntity;
import br.com.dbc.vemser.trabalhofinal.entity.TipoUsuario;
import br.com.dbc.vemser.trabalhofinal.exceptions.BancoDeDadosException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface MedicoRepository extends JpaRepository<MedicoEntity, Integer> {
}
