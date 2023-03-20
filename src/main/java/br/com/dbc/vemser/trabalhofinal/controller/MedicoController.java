package br.com.dbc.vemser.trabalhofinal.controller;

import br.com.dbc.vemser.trabalhofinal.dto.*;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.service.MedicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Validated
@RequestMapping("/medico")
@RestController
@RequiredArgsConstructor
public class MedicoController implements InterfaceDocumentacao<MedicoCompletoDTO, MedicoCreateDTO, Integer, Integer> {

    private final MedicoService medicoService;


    @Override
    public ResponseEntity<PageDTO<MedicoCompletoDTO>> list(Integer pagina, Integer tamanho) {
        return null;
    }

    @Override
    public ResponseEntity<MedicoCompletoDTO> getById(Integer id) throws RegraDeNegocioException {
        return new ResponseEntity<>(medicoService.getById(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MedicoCompletoDTO> create(MedicoCreateDTO medico) throws RegraDeNegocioException {
        MedicoCompletoDTO medicoCriado = medicoService.adicionar(medico);
        return new ResponseEntity<>(medicoCriado, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MedicoCompletoDTO> update(Integer id, MedicoCreateDTO medico) throws RegraDeNegocioException {
        MedicoCompletoDTO medicoAtualizado = medicoService.editar(id, medico);
        return new ResponseEntity<>(medicoAtualizado, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> delete(Integer id) throws RegraDeNegocioException {
        medicoService.remover(id);
        return ResponseEntity.ok().build();
    }

}
