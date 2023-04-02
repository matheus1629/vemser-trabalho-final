package br.com.dbc.vemser.trabalhofinal.controller.documentacao;

import br.com.dbc.vemser.trabalhofinal.dto.PageDTO;
import br.com.dbc.vemser.trabalhofinal.dto.agendamento.AgendamentoClienteRelatorioDTO;
import br.com.dbc.vemser.trabalhofinal.dto.agendamento.AgendamentoCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.agendamento.AgendamentoDTO;
import br.com.dbc.vemser.trabalhofinal.dto.agendamento.AgendamentoMedicoRelatorioDTO;
import br.com.dbc.vemser.trabalhofinal.entity.AprovarReprovarSolicitacao;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

public interface DocumentacaoAgendamento {

    @Operation(summary = "Listar Agendamentos", description = "Lista todos os Agendamentos")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Todos os Agendamentos foram listados com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping()
    ResponseEntity<PageDTO<AgendamentoDTO>> list(@RequestParam Integer pagina, @RequestParam Integer tamanho);


    @Operation(summary = "Recuperar um Agendamentos", description = "Lista um Agendamentos passando seu ID")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "O Agendamentos foi lsitado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{id}")
    ResponseEntity<AgendamentoDTO> getById(@PathVariable Integer id) throws RegraDeNegocioException;

    @Operation(summary = "Recuperar um Agendamentos", description = "Recupera um Agendamentos passando seu ID")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "O registro foi lsitado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{idCliente}/relatorio-cliente")
    ResponseEntity<AgendamentoClienteRelatorioDTO> getClienteByIdPersonalizado(@PathVariable("idCliente") Integer idCliente) throws RegraDeNegocioException;

    @Operation(summary = "Recuperar um Agendamentos", description = "Recupera um Agendamentos passando seu ID")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "O Agendamentos foi lsitado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{idMedico}/relatorio-medico")
    ResponseEntity<AgendamentoMedicoRelatorioDTO> getMedicoByIdPersonalizado(@PathVariable("idMedico") Integer idMedico) throws RegraDeNegocioException;

    @Operation(summary = "Aprovar ou reprovar solicitação", description = "Ao aprovar a solicitação, é criado o agendamento no banco de dados Oracle e o status da solicitação é alterado para APROVADO. Ao reprovar, não é criado agendamento, apenas é trocado o status da solicitação para REPROVADO")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Solicitação aprovada/reprovada com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    ResponseEntity<AgendamentoDTO> create(String idSolicitacao, AprovarReprovarSolicitacao aprovarReprovarSolicitacao) throws RegraDeNegocioException;

    @Operation(summary = "Atualizar Agendamentos", description = "Atualiza um Agendamentos passando o id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Agendamentos atualizado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{id}")
    ResponseEntity<AgendamentoDTO> update(@PathVariable Integer id, @Valid @RequestBody AgendamentoCreateDTO agendamento) throws RegraDeNegocioException;

    @Operation(summary = "Deletar Agendamentos", description = "Detela um Agendamentos passando o id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Agendamentos deletado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Integer id) throws RegraDeNegocioException;

}
