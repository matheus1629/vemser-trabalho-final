package br.com.dbc.vemser.trabalhofinal.controller;

import br.com.dbc.vemser.trabalhofinal.dto.solicitacao.SolicitacaoCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.solicitacao.SolicitacaoDTO;
import br.com.dbc.vemser.trabalhofinal.dto.solicitacao.SolicitacaoPesquisaDTO;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.service.SolicitacaoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name="Solicitação")
@Slf4j
@Validated
@RequestMapping("/solicitacao")
@RestController
@RequiredArgsConstructor
public class SolicitacaoController {

    private final SolicitacaoService solicitacaoService;
    @PostMapping("/requisitar-solicitacao")
    public ResponseEntity<SolicitacaoDTO> Requisitarsolicitacao(@RequestBody @Valid SolicitacaoCreateDTO solicitacaoCreateDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(solicitacaoService.create(solicitacaoCreateDTO), HttpStatus.OK);
    }

    @GetMapping("/resgatar-solicitacao")
    public ResponseEntity<SolicitacaoDTO> resgatarSolicitacao(@RequestBody @Valid SolicitacaoPesquisaDTO solicitacaoCreateDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(solicitacaoService.list(solicitacaoCreateDTO), HttpStatus.OK);
    }
}
