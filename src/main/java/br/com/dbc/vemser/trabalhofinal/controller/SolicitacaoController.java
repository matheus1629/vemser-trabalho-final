package br.com.dbc.vemser.trabalhofinal.controller;

import br.com.dbc.vemser.trabalhofinal.dto.agendamento.SolicitacaoCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.agendamento.SolicitacaoDTO;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.service.SolicitacaoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name="Solicitação")
@Slf4j
@Validated
@RequestMapping("/solicitacao")
@RestController
@RequiredArgsConstructor
public class SolicitacaoController {

    private final SolicitacaoService solicitacaoService;
    @PostMapping("/agendamento")
    public ResponseEntity<SolicitacaoDTO> solicitacao(@RequestBody @Valid SolicitacaoCreateDTO solicitacaoCreateDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(solicitacaoService.create(solicitacaoCreateDTO), HttpStatus.OK);
    }
}
