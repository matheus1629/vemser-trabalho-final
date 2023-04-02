package br.com.dbc.vemser.trabalhofinal.controller.documentacao;

import br.com.dbc.vemser.trabalhofinal.dto.log.LogDTO;
import br.com.dbc.vemser.trabalhofinal.entity.TipoLog;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public interface DocumentacaoLog {

    @Operation(summary = "Recuperar Logs", description = "Recupera os logs por determinado ID de Solicitação")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Logs retornados com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/id-solicitacao")
    ResponseEntity<List<LogDTO>> buscarPeloIdSolicitacao(@RequestParam(value = "idSolicitacao") @NotNull Integer idSolicitacao);

    @Operation(summary = "Recuperar Logs", description = "Recupera os logs por determinado ID de Agendamento")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Logs retornados com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/id-agendamento")
    ResponseEntity<List<LogDTO>> buscarPeloIdAgendamento(@RequestParam(value = "idAgendamento") @NotNull Integer idAgendamento);

    @Operation(summary = "Recuperar Logs", description = "Recupera os logs por determinado ID de Usuário")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Logs retornados com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/id-usuario")
    ResponseEntity<List<LogDTO>> buscarPeloIdUsuario(@RequestParam(value = "idUsuario") @NotNull Integer idUsuario);


    @Operation(summary = "Recuperar Logs", description = "Recupera os logs por determinada data.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Logs retornados com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/data")
    ResponseEntity<List<LogDTO>> buscarPelaData(@RequestParam(value = "data") @NotNull LocalDate data);


    @Operation(summary = "Recuperar Logs", description = "Recupera os logs por determinada tipo de log.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Logs retornados com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/tipo-log")
    ResponseEntity<List<LogDTO>> buscarPelaData(@RequestParam(value = "tipoLog") @NotNull TipoLog tipoLog);

}
