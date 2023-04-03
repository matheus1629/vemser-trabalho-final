package br.com.dbc.vemser.trabalhofinal.controller.documentacao;

import br.com.dbc.vemser.trabalhofinal.dto.AgendamentoMedicoEditarCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.agendamento.AgendamentoDTO;
import br.com.dbc.vemser.trabalhofinal.dto.agendamento.AgendamentoListaDTO;
import br.com.dbc.vemser.trabalhofinal.dto.medico.MedicoUpdateDTO;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

public interface DocumentacaoMedico<MedicoCompletoDTO, idAgendamento, agendamentoMedicoEditarCreateDTO> {

    @Operation(summary = "Atualizar Medico", description = "Recuperar as informações de Medico pelo respectivo token")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Medico recuperado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    ResponseEntity<MedicoCompletoDTO> recuperarMedico() throws RegraDeNegocioException;

    @Operation(summary = "Recuperar os Agendamentos do Medico", description = "Lista os Agendamentos de acordo com o Medico logado")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Os Agendamentos foram lsitados com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/agendamentos")
    ResponseEntity<AgendamentoListaDTO> getMedicoAgentamentos() throws RegraDeNegocioException;

    @Operation(summary = "Atualizar Medico", description = "Atualiza um Medico Logado")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Medico atualizado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping()
    ResponseEntity<MedicoCompletoDTO> update(MedicoUpdateDTO medico) throws RegraDeNegocioException;


    @Operation(summary = "Edita um agendamento", description = "Atualiza o exame ou o tratamento de determinado agendamento que aquele médico possui")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Agendamento atualizado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/editar-agendamento/")
    ResponseEntity<AgendamentoDTO> editarAgendamento(@RequestParam("idAgendamento") Integer idAgendamento,
                                                     @RequestBody @Valid AgendamentoMedicoEditarCreateDTO agendamentoMedicoEditarCreateDTO) throws RegraDeNegocioException;

}
