package br.com.dbc.vemser.trabalhofinal.controller;

import br.com.dbc.vemser.trabalhofinal.dto.UsuarioDTO;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.service.AdministrativoService;
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

    @PutMapping("/{idUsuario}/reativação")
    public ResponseEntity<UsuarioDTO> reativarUsuario (@PathVariable ("idUsuario") Integer idUsuario) throws RegraDeNegocioException {
        return new ResponseEntity<>(administrativoService.reativarUsuario(idUsuario), HttpStatus.OK);
    }
}
