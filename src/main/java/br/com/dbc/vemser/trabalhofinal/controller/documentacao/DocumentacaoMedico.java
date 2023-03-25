package br.com.dbc.vemser.trabalhofinal.controller.documentacao;

import br.com.dbc.vemser.trabalhofinal.dto.agendamento.AgendamentoListaDTO;
import br.com.dbc.vemser.trabalhofinal.dto.medico.MedicoCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.medico.MedicoUpdateDTO;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

public interface DocumentacaoMedico<MedicoCompletoDTO> {

    @Operation(summary = "Atualizar Medico", description = "Recuperar as informações de Medico pelo respectivo token")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Medico recuperado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    ResponseEntity<MedicoCompletoDTO> recuperarCliente() throws RegraDeNegocioException;

    @Operation(summary = "Recuperar os Agendamentos do Medico", description = "Lista os Agendamentos de acordo com o Medico logado")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Os Agendamentos foram lsitados com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/agendamentos")
    ResponseEntity<AgendamentoListaDTO> getClienteAgentamentos() throws RegraDeNegocioException;

    @Operation(summary = "Atualizar Medico", description = "Atualiza um Medico passando o id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Medico atualizado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping()
    ResponseEntity<MedicoCompletoDTO> update(MedicoUpdateDTO medico) throws RegraDeNegocioException;
}
