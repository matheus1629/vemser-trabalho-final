package br.com.dbc.vemser.trabalhofinal.controller;

import br.com.dbc.vemser.trabalhofinal.dto.*;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/cliente")
public class ClienteController implements InterfaceDocumentacao<ClienteCompletoDTO, ClienteCreateDTO, Integer> {

    private final ClienteService clienteService;

    @Override
    public ResponseEntity<PageDTO<ClienteCompletoDTO>> list(Integer pagina, Integer tamanho) {
        return new ResponseEntity<>(clienteService.list(pagina, tamanho), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ClienteCompletoDTO> getById(Integer id) throws RegraDeNegocioException {
        return new ResponseEntity<>(clienteService.getById(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ClienteCompletoDTO> create(ClienteCreateDTO cliente) throws RegraDeNegocioException {
        return new ResponseEntity<>(clienteService.adicionar(cliente), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ClienteCompletoDTO> update(Integer id, ClienteCreateDTO cliente) throws RegraDeNegocioException {
        return new ResponseEntity<>(clienteService.editar(id, cliente), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> delete(Integer id) throws RegraDeNegocioException {
        clienteService.remover(id);
        return ResponseEntity.ok().build();
    }


}
