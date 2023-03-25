package br.com.dbc.vemser.trabalhofinal.controller;

import br.com.dbc.vemser.trabalhofinal.dto.*;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.service.AdministrativoService;
import br.com.dbc.vemser.trabalhofinal.service.ClienteService;
import br.com.dbc.vemser.trabalhofinal.service.MedicoService;
import br.com.dbc.vemser.trabalhofinal.service.UsuarioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name="Administrativo")
@Validated
@RestController
@RequestMapping("/administrativo")
@RequiredArgsConstructor
public class AdministrativoController {

    private final AdministrativoService administrativoService;

    private final ClienteService clienteService;
    private final UsuarioService usuarioService;
    private final MedicoService medicoService;

    @PutMapping("/{idUsuario}/reativação")
    public ResponseEntity<UsuarioDTO> reativarUsuario (@PathVariable ("idUsuario") Integer idUsuario) throws RegraDeNegocioException {
        return new ResponseEntity<>(administrativoService.reativarUsuario(idUsuario), HttpStatus.OK);
    }

    @GetMapping("/cliente/{id}")
    public ResponseEntity<ClienteCompletoDTO> getByIdCliente(Integer id) throws RegraDeNegocioException {
        return new ResponseEntity<>(clienteService.getById(id), HttpStatus.OK);
    }

    @GetMapping("/medico/{id}")
    public ResponseEntity<MedicoCompletoDTO> getByIdMedico(Integer id) throws RegraDeNegocioException {
        return new ResponseEntity<>(medicoService.getById(id), HttpStatus.OK);
    }

    @DeleteMapping("/deletar-medico/{id}")
    public ResponseEntity<Void> deleteMedico(Integer id) throws RegraDeNegocioException {
        medicoService.remover(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deletar-cliente/{id}")
    public ResponseEntity<Void> deleteCliente(Integer id) throws RegraDeNegocioException {
        clienteService.remover(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/criar-admin/{id}")
    public ResponseEntity<UsuarioDTO> create(UsuarioCreateDTO usuario) throws RegraDeNegocioException {
        return new ResponseEntity<>(administrativoService.adicionar(usuario), HttpStatus.OK);
    }

    @PutMapping("/editar-admin/{id}")
    public ResponseEntity<UsuarioDTO> update(Integer id, UsuarioCreateDTO admin) throws RegraDeNegocioException {
        UsuarioDTO usuarioAtualizado = administrativoService.editar(id,admin);
        return new ResponseEntity<>(usuarioAtualizado, HttpStatus.OK);
    }


    @DeleteMapping("/deletar-admin/{id}")
    public ResponseEntity<Void> deleteAdmin(Integer id) throws RegraDeNegocioException {
        administrativoService.remover(id);
        return ResponseEntity.ok().build();
    }
}
