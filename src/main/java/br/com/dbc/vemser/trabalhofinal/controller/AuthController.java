package br.com.dbc.vemser.trabalhofinal.controller;

import br.com.dbc.vemser.trabalhofinal.dto.*;
import br.com.dbc.vemser.trabalhofinal.dto.cliente.ClienteCompletoDTO;
import br.com.dbc.vemser.trabalhofinal.dto.cliente.ClienteCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.medico.MedicoCompletoDTO;
import br.com.dbc.vemser.trabalhofinal.dto.medico.MedicoCreateDTO;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.security.TokenService;
import br.com.dbc.vemser.trabalhofinal.service.ClienteService;
import br.com.dbc.vemser.trabalhofinal.service.MedicoService;
import br.com.dbc.vemser.trabalhofinal.service.UsuarioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name="Autenticação")
@RestController
@RequestMapping("/auth")
@Validated
@RequiredArgsConstructor
public class AuthController {

    private final TokenService tokenService;
    private final ClienteService clienteService;
    private final MedicoService medicoService;

    private final UsuarioService usuarioService;

    @PostMapping
    public String auth(@RequestBody @Valid LoginDTO loginDTO) throws RegraDeNegocioException {
        return tokenService.autenticar(loginDTO);
    }
    @PostMapping("/cadastro-cliente")
    public ClienteCompletoDTO adicionarCliente(@RequestBody @Valid ClienteCreateDTO cliente) throws RegraDeNegocioException {
        return clienteService.adicionar(cliente);
    }
    @PostMapping("/cadastro-medico")
    public MedicoCompletoDTO adicionarMedico(@RequestBody @Valid MedicoCreateDTO medico) throws RegraDeNegocioException {
        return medicoService.adicionar(medico);
    }

    @PostMapping("/alterar-senha")
    public ResponseEntity<Void> trocarSenha(@RequestBody @Valid TrocaSenhaDTO trocaSenhaDTO) throws RegraDeNegocioException {
        usuarioService.trocarSenha(trocaSenhaDTO);
        return ResponseEntity.ok().build();
    }
}
