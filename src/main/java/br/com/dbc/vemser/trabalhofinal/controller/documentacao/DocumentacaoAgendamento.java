package br.com.dbc.vemser.trabalhofinal.controller.documentacao;

import br.com.dbc.vemser.trabalhofinal.dto.PageDTO;
import br.com.dbc.vemser.trabalhofinal.dto.agendamento.AgendamentoClienteRelatorioDTO;
import br.com.dbc.vemser.trabalhofinal.dto.agendamento.AgendamentoMedicoRelatorioDTO;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

public interface DocumentacaoAgendamento<AgendamentoDTO, entrada, id, numero2> {

    @Operation(summary = "Listar Agendamentos", description = "Lista todos os Agendamentos")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Todos os Agendamentos foram listados com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping()
    ResponseEntity<PageDTO<AgendamentoDTO>> list(@RequestParam id pagina, @RequestParam numero2 tamanho);


    @Operation(summary = "Recuperar um Agendamentos", description = "Lista um Agendamentos passando seu ID")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "O Agendamentos foi lsitado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{id}")
    ResponseEntity<AgendamentoDTO> getById(@PathVariable id id) throws RegraDeNegocioException;

    @Operation(summary = "Recuperar um Agendamentos", description = "Lista um Agendamentos passando seu ID")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "O registro foi lsitado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{idCliente}/relatorio-cliente")
    ResponseEntity<AgendamentoClienteRelatorioDTO> getClienteByIdPersonalizado(@PathVariable("idCliente") id idCliente) throws RegraDeNegocioException;

    @Operation(summary = "Recuperar um Agendamentos", description = "Lista um Agendamentos passando seu ID")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "O Agendamentos foi lsitado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{idMedico}/relatorio-medico")
    ResponseEntity<AgendamentoMedicoRelatorioDTO> getMedicoByIdPersonalizado(@PathVariable("idMedico") id idMedico) throws RegraDeNegocioException;

    @Operation(summary = "Criar Agendamentos", description = "Cria um Agendamentos")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Agendamentos criado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    ResponseEntity<AgendamentoDTO> create(@Valid @RequestBody entrada e) throws RegraDeNegocioException;


    @Operation(summary = "Atualizar Agendamentos", description = "Atualiza um Agendamentos passando o id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Agendamentos atualizado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{id}")
    ResponseEntity<AgendamentoDTO> update(@PathVariable id id, @Valid @RequestBody entrada e) throws RegraDeNegocioException;

    @Operation(summary = "Deletar Agendamentos", description = "Detela um Agendamentos passando o id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Agendamentos deletado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable id id) throws RegraDeNegocioException;

}
