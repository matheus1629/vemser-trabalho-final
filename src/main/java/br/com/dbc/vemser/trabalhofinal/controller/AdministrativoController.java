package br.com.dbc.vemser.trabalhofinal.controller;

import br.com.dbc.vemser.trabalhofinal.controller.documentacao.DocumentacaoAdministracao;
import br.com.dbc.vemser.trabalhofinal.dto.PageDTO;
import br.com.dbc.vemser.trabalhofinal.dto.cliente.ClienteCompletoDTO;
import br.com.dbc.vemser.trabalhofinal.dto.medico.MedicoCompletoDTO;
import br.com.dbc.vemser.trabalhofinal.dto.usuario.UsuarioCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.usuario.UsuarioDTO;
import br.com.dbc.vemser.trabalhofinal.dto.usuario.UsuarioUpdateDTO;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.service.AdministrativoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="Administrativo")
@Validated
@RestController
@RequestMapping("/administrativo")
@RequiredArgsConstructor
public class AdministrativoController implements DocumentacaoAdministracao {
    private final AdministrativoService administrativoService;

    @Override
    public ResponseEntity<UsuarioDTO> reativarUsuario (@PathVariable ("idUsuario") Integer idUsuario) throws RegraDeNegocioException {
        return new ResponseEntity<>(administrativoService.reativarUsuario(idUsuario), HttpStatus.OK);
    }
    @Override
    public ResponseEntity<ClienteCompletoDTO> getByIdCliente(Integer id) throws RegraDeNegocioException {
        return new ResponseEntity<>(administrativoService.getClienteById(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MedicoCompletoDTO> getByIdMedico(Integer id) throws RegraDeNegocioException {
        return new ResponseEntity<>(administrativoService.getMedicoById(id), HttpStatus.OK);
    }
    @Override
    public ResponseEntity<List<UsuarioDTO>> list() throws RegraDeNegocioException{
        return new ResponseEntity<>(administrativoService.listar(),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteMedico(Integer id) throws RegraDeNegocioException {
        administrativoService.removerMedico(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deleteCliente(Integer id) throws RegraDeNegocioException {
        administrativoService.removerCliente(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<UsuarioDTO> create(UsuarioCreateDTO usuario) throws RegraDeNegocioException {
        return new ResponseEntity<>(administrativoService.adicionar(usuario), HttpStatus.OK);
    }
    @Override
    public ResponseEntity<UsuarioDTO> update(Integer id, UsuarioUpdateDTO admin) throws RegraDeNegocioException {
        UsuarioDTO usuarioAtualizado = administrativoService.editar(id,admin);
        return new ResponseEntity<>(usuarioAtualizado, HttpStatus.OK);
    }
    @Override
    public ResponseEntity<Void> remove(Integer id) throws RegraDeNegocioException {
        administrativoService.remover(id);
        return ResponseEntity.ok().build();
    }
    @Override
    public ResponseEntity<PageDTO<MedicoCompletoDTO>> paginadoMedico(Integer pagina, Integer tamanho){
        return new ResponseEntity<>(administrativoService.listMedico(pagina,tamanho),HttpStatus.OK);
    }
    @Override
    public ResponseEntity<PageDTO<ClienteCompletoDTO>> paginadoCliente(Integer pagina, Integer tamanho){
        return new ResponseEntity<>(administrativoService.listCliente(pagina,tamanho),HttpStatus.OK);
    }
}
