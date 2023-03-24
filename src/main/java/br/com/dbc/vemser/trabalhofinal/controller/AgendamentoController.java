package br.com.dbc.vemser.trabalhofinal.controller;

import br.com.dbc.vemser.trabalhofinal.controller.documentacao.InterfaceDocumentacao;
import br.com.dbc.vemser.trabalhofinal.dto.*;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.service.AgendamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/agendamento")
@RequiredArgsConstructor
public class AgendamentoController  {

    private final AgendamentoService agendamentoService;


    @Operation(summary = "Listar registros", description = "Lista todos os registros")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Todos os registros foram listados com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping()
    public ResponseEntity<PageDTO<AgendamentoDTO>> list(Integer pagina, Integer tamanho) {
        return new ResponseEntity<>(agendamentoService.findAllPaginado(pagina, tamanho), HttpStatus.OK);
    }

    @Operation(summary = "Recuperar um registro", description = "Lista um registro passando seu ID")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "O registro foi lsitado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoDTO> getById(Integer id) throws RegraDeNegocioException {
        return new ResponseEntity<>(agendamentoService.getById(id), HttpStatus.OK);
    }

    @GetMapping("/{idCliente}/relatorio-cliente")
    public ResponseEntity<AgendamentoClienteRelatorioDTO> getClienteByIdPersonalizado(@PathVariable("idCliente") Integer idCliente) throws RegraDeNegocioException {
        return new ResponseEntity<>(agendamentoService.getRelatorioClienteById(idCliente), HttpStatus.OK);
    }

    @GetMapping("/{idMedico}/relatorio-medico")
    public ResponseEntity<AgendamentoMedicoRelatorioDTO> getMedicoByIdPersonalizado(@PathVariable("idMedico") Integer idMedico) throws RegraDeNegocioException {
        return new ResponseEntity<>(agendamentoService.getRelatorioMedicoById(idMedico), HttpStatus.OK);
    }

    @Operation(summary = "Criar registro", description = "Cria um registro")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Registro criado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    public ResponseEntity<AgendamentoDTO> create(AgendamentoCreateDTO agendamento) throws RegraDeNegocioException {
        return new ResponseEntity<>(agendamentoService.adicionar(agendamento), HttpStatus.OK);
    }

    @Operation(summary = "Atualizar registro", description = "Atualiza um registro passando o id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Registro atualizado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<AgendamentoDTO> update(Integer id, AgendamentoCreateDTO agendamento) throws RegraDeNegocioException {
        return new ResponseEntity<>(agendamentoService.editar(id, agendamento), HttpStatus.OK);
    }

    @Operation(summary = "Deletar registro", description = "Detela um registro passando o id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Registro deletado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(Integer id) throws RegraDeNegocioException {
        agendamentoService.remover(id);
        return ResponseEntity.ok().build();
    }



}
