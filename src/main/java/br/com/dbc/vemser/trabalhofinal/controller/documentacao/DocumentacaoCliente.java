package br.com.dbc.vemser.trabalhofinal.controller.documentacao;

import br.com.dbc.vemser.trabalhofinal.dto.agendamento.AgendamentoClienteRelatorioDTO;
import br.com.dbc.vemser.trabalhofinal.dto.agendamento.AgendamentoDTO;
import br.com.dbc.vemser.trabalhofinal.dto.agendamento.AgendamentoListaDTO;
import br.com.dbc.vemser.trabalhofinal.dto.cliente.ClienteUpdateDTO;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface DocumentacaoCliente<ClienteCompletoDTO> {

    @Operation(summary = "Recuperar Cliente", description = "Recuperar as informações de Cliente pelo respectivo token")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Cliente recuperado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/verificar-info")
    ResponseEntity<ClienteCompletoDTO> recuperarCliente() throws RegraDeNegocioException;

    @Operation(summary = "Recuperar os Agendamentos do Cliente", description = "Lista os Agendamentos de acordo com o Cliente logado")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Os Agendamentos foram lsitados com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/agendamentos")
    ResponseEntity<AgendamentoListaDTO> getClienteAgentamentos() throws RegraDeNegocioException;

    @Operation(summary = "Atualizar Cliente", description = "Atualiza um Cliente passando o id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping()
    ResponseEntity<ClienteCompletoDTO> update(ClienteUpdateDTO cliente) throws RegraDeNegocioException;


}
