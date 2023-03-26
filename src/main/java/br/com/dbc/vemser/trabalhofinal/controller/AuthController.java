package br.com.dbc.vemser.trabalhofinal.controller;

import br.com.dbc.vemser.trabalhofinal.controller.documentacao.DocunentacaoAuth;
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
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Tag(name="Autenticação")
@RestController
@RequestMapping("/auth")
@Validated
@RequiredArgsConstructor
public class AuthController implements DocunentacaoAuth {
    private final TokenService tokenService;
    private final ClienteService clienteService;
    private final MedicoService medicoService;
    private final UsuarioService usuarioService;

    @Override
    public String auth(@RequestBody @Valid LoginDTO loginDTO) throws RegraDeNegocioException {
        return tokenService.autenticar(loginDTO);
    }
    @Override
    public ClienteCompletoDTO adicionarCliente(@RequestBody @Valid ClienteCreateDTO cliente) throws RegraDeNegocioException {
        return clienteService.adicionar(cliente);
    }
    @Override
    public MedicoCompletoDTO adicionarMedico(@RequestBody @Valid MedicoCreateDTO medico) throws RegraDeNegocioException {
        return medicoService.adicionar(medico);
    }

    @Override
    public ResponseEntity<Void> trocarSenha(@RequestBody @Valid TrocaSenhaDTO trocaSenhaDTO) throws RegraDeNegocioException {
        usuarioService.trocarSenha(trocaSenhaDTO);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> solicitarRedefinicao(@RequestParam(name="email") @NotNull String email) throws RegraDeNegocioException {
        usuarioService.solicitarRedefinirSenha(email);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> redefinir(@RequestBody @Valid RedefinicaoSenhaDTO redefinicaoSenhaDTO) throws RegraDeNegocioException {
        usuarioService.redefinirSenha(redefinicaoSenhaDTO);
        return ResponseEntity.ok().build();
    }

}
