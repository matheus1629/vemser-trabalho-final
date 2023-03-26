package br.com.dbc.vemser.trabalhofinal.controller.documentacao;

import br.com.dbc.vemser.trabalhofinal.dto.PageDTO;
import br.com.dbc.vemser.trabalhofinal.dto.agendamento.AgendamentoClienteRelatorioDTO;
import br.com.dbc.vemser.trabalhofinal.dto.agendamento.AgendamentoMedicoRelatorioDTO;
import br.com.dbc.vemser.trabalhofinal.dto.cliente.ClienteCompletoDTO;
import br.com.dbc.vemser.trabalhofinal.dto.medico.MedicoCompletoDTO;
import br.com.dbc.vemser.trabalhofinal.dto.usuario.UsuarioCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.usuario.UsuarioDTO;
import br.com.dbc.vemser.trabalhofinal.dto.usuario.UsuarioUpdateDTO;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface DocumentacaoAdministracao {

    @Operation(summary = "Reativa um Usuário", description = "Reativa um usuário passando seu ID")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "O usuário foi reativado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idUsuario}/reativação")
    ResponseEntity<UsuarioDTO> reativarUsuario(@PathVariable("idUsuario") Integer idUsuario) throws RegraDeNegocioException;


    @Operation(summary = "Recuperar um Cliente", description = "Recupera um Cliente passando seu ID")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "O Cliente foi recuperado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/cliente/{id}")
    ResponseEntity<ClienteCompletoDTO> getByIdCliente(Integer id) throws RegraDeNegocioException;

    @Operation(summary = "Recuperar um Médico", description = "Recupera um Médico passando seu ID")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "O Médico foi recuperado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/medico/{id}")
    ResponseEntity<MedicoCompletoDTO> getByIdMedico(Integer id) throws RegraDeNegocioException;

    @Operation(summary = "Lista todos os administradores", description = "Lista todos os administradores")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Os Administradores foram listados com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/listar-administradores")
    ResponseEntity<List<UsuarioDTO>> list() throws RegraDeNegocioException;

    @Operation(summary = "Desativa um Médico", description = "Desativa um Médico passando seu ID")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "O Médico foi desativado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/desativar-medico/{id}")
    ResponseEntity<Void> deleteMedico(Integer id) throws RegraDeNegocioException;


    @Operation(summary = "Desativa um Cliente", description = "Desativa um Cliente passando seu ID")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "O Cliente foi desativado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/desativar-cliente/{id}")
    ResponseEntity<Void> deleteCliente(Integer id) throws RegraDeNegocioException;


    @Operation(summary = "Cria um Administrador", description = "Cria um Administrador")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "O Administrador foi criado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/criar-admin")
    ResponseEntity<UsuarioDTO> create(UsuarioCreateDTO usuario) throws RegraDeNegocioException;


    @Operation(summary = "Atualiza um Administrador", description = "Atualiza um Administrador pssando seu ID")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "O Administrador foi atualizado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/editar-admin/{id}")
    ResponseEntity<UsuarioDTO> update(Integer id, UsuarioUpdateDTO admin) throws RegraDeNegocioException;


    @Operation(summary = "Desativa um Administrador", description = "Desativa um Administrador pssando seu ID")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "O Desativado foi atualizado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/desativar-admin/{id}")
    ResponseEntity<Void> remove(Integer id) throws RegraDeNegocioException;


    @Operation(summary = "Lista todos os Médicos", description = "Lista todos os Médicos de forma paginada")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Os Médicos foram listados com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/paginado-medico/")
    ResponseEntity<PageDTO<MedicoCompletoDTO>> paginadoMedico(Integer pagina, Integer tamanho);


    @Operation(summary = "Lista todos os Clientes", description = "Lista todos os Clientes de forma paginada")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Os Clientes foram listados com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/paginado-cliente/")
    ResponseEntity<PageDTO<ClienteCompletoDTO>> paginadoCliente(Integer pagina, Integer tamanho);
}