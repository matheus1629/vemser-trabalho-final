package br.com.dbc.vemser.trabalhofinal.controller;

import br.com.dbc.vemser.trabalhofinal.controller.documentacao.DocumentacaoCliente;
import br.com.dbc.vemser.trabalhofinal.controller.documentacao.InterfaceDocumentacao;
import br.com.dbc.vemser.trabalhofinal.dto.ClienteCompletoDTO;
import br.com.dbc.vemser.trabalhofinal.dto.ClienteCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.PageDTO;
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

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/cliente")
public class ClienteController implements DocumentacaoCliente<ClienteCompletoDTO, ClienteCreateDTO, Integer, Integer> {

    private final ClienteService clienteService;



    @GetMapping("/verificar-info")
    public ResponseEntity<ClienteCompletoDTO> recuperarCliente() throws RegraDeNegocioException {
        return new ResponseEntity<>(clienteService.recuperarCliente(), HttpStatus.OK);
    }


//    @Operation(summary = "Recuperar um registro", description = "Lista um registro passando seu ID")
//    @ApiResponses(
//            value = {
//                    @ApiResponse(responseCode = "200", description = "O registro foi lsitado com sucesso"),
//                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
//                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
//            }
//    )
//    @GetMapping("/{id}")
//    public ResponseEntity<ClienteCompletoDTO> getById(Integer id) throws RegraDeNegocioException {
//        return new ResponseEntity<>(clienteService.getById(id), HttpStatus.OK);
//    }



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
