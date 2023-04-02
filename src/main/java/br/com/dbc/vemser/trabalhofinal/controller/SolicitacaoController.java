package br.com.dbc.vemser.trabalhofinal.controller;

import br.com.dbc.vemser.trabalhofinal.controller.documentacao.DocumentacaoSolicitacao;
import br.com.dbc.vemser.trabalhofinal.dto.solicitacao.SolicitacaoCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.solicitacao.SolicitacaoDTO;
import br.com.dbc.vemser.trabalhofinal.entity.StatusSolicitacao;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.service.SolicitacaoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Tag(name="Solicitação")
@Slf4j
@Validated
@RequestMapping("/solicitacao")
@RestController
@RequiredArgsConstructor
public class SolicitacaoController implements DocumentacaoSolicitacao {

    private final SolicitacaoService solicitacaoService;

    @Override
    public ResponseEntity<SolicitacaoDTO> Requisitarsolicitacao(@RequestBody @Valid SolicitacaoCreateDTO solicitacaoCreateDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(solicitacaoService.create(solicitacaoCreateDTO), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<SolicitacaoDTO>> resgatarSolicitacao(@RequestParam( value = "idMedico", required = false) Integer idMedico,
                                                                    @RequestParam(value = "idCliente", required = false) Integer idCliente,
                                                                    @RequestParam (value = "dataHoraInicio", required = false)
                                                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataHoraInicio,
                                                                    @RequestParam(value = "dataHoraFim", required = false)
                                                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime dataHoraFim,
                                                                    @RequestParam(value = "statusSolicitacao", required = false)StatusSolicitacao statusSolicitacao) {
        return new ResponseEntity<>(solicitacaoService.findSolicitacoes(idMedico, idCliente, dataHoraInicio, dataHoraFim, statusSolicitacao), HttpStatus.OK);
    }


}
