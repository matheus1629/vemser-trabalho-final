package br.com.dbc.vemser.trabalhofinal.controller;

import br.com.dbc.vemser.trabalhofinal.controller.documentacao.DocumentacaoCliente;
import br.com.dbc.vemser.trabalhofinal.dto.ClienteCompletoDTO;
import br.com.dbc.vemser.trabalhofinal.dto.ClienteCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.ClienteUpdateDTO;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.service.ClienteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name="Cliente")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/cliente")
public class ClienteController implements DocumentacaoCliente<ClienteCompletoDTO, ClienteUpdateDTO> {

    private final ClienteService clienteService;


    @Override
    public ResponseEntity<ClienteCompletoDTO> recuperarCliente() throws RegraDeNegocioException {
        return new ResponseEntity<>(clienteService.recuperarCliente(), HttpStatus.OK);
    }


    @Override
    public ResponseEntity<ClienteCompletoDTO> update(ClienteUpdateDTO cliente) throws RegraDeNegocioException {
        return new ResponseEntity<>(clienteService.editar(cliente), HttpStatus.OK);
    }


}
