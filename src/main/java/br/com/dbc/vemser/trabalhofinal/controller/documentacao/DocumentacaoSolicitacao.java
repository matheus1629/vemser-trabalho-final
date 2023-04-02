package br.com.dbc.vemser.trabalhofinal.controller.documentacao;

import br.com.dbc.vemser.trabalhofinal.dto.solicitacao.SolicitacaoCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.solicitacao.SolicitacaoDTO;
import br.com.dbc.vemser.trabalhofinal.entity.StatusSolicitacao;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

public interface DocumentacaoSolicitacao {

    @Operation(summary = "Cria uma solicitação", description = "Cria uma solicitação que ficará em PENDENTE até que um administrador aprove ou reprove")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Solicitação criada com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/requisitar")
    ResponseEntity<SolicitacaoDTO> Requisitarsolicitacao(@RequestBody @Valid SolicitacaoCreateDTO solicitacaoCreateDTO) throws RegraDeNegocioException;


    @Operation(summary = "Lista solicitações", description = "Lista solicitações de acordo com os filtros preenchidos. Os filtros que não forem preenchidos serão desconsiderados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Solicitação listadas com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/resgatar-personalizado")
    ResponseEntity<List<SolicitacaoDTO>> resgatarSolicitacao(@RequestParam(value = "idMedico", required = false) Integer idMedico,
                                                             @RequestParam(value = "idCliente", required = false) Integer idCliente,
                                                             @RequestParam(value = "dataHoraInicio", required = false)
                                                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataHoraInicio,
                                                             @RequestParam(value = "dataHoraFim", required = false)
                                                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataHoraFim,
                                                             @RequestParam(value = "statusSolicitacao", required = false) StatusSolicitacao statusSolicitacao);
}
