package br.com.dbc.vemser.trabalhofinal.controller;

import br.com.dbc.vemser.trabalhofinal.controller.documentacao.DocumentacaoLog;
import br.com.dbc.vemser.trabalhofinal.dto.log.LogDTO;
import br.com.dbc.vemser.trabalhofinal.entity.TipoLog;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.service.LogService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@Tag(name="Logs")
@Slf4j
@Validated
@RequestMapping("/logs")
@RestController
@RequiredArgsConstructor
public class LogController implements DocumentacaoLog {

    private final LogService logService;

    @Override
    public ResponseEntity<List<LogDTO>> buscarPeloIdSolicitacao(Integer idSolicitacao){
        return new ResponseEntity<>(logService.listLogsByIdSolicitacao(idSolicitacao), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<LogDTO>> buscarPeloIdAgendamento(Integer idAgendamento){
        return new ResponseEntity<>(logService.listLogsByIdAgendamento(idAgendamento), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<LogDTO>> buscarPeloIdUsuario(Integer idUsuario){
        return new ResponseEntity<>(logService.listLogsByIdUsuario(idUsuario), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<LogDTO>> buscarPelaData(LocalDate data){
        return new ResponseEntity<>(logService.listLogsByData(data), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<LogDTO>> buscarPelaData(TipoLog tipoLog){
        return new ResponseEntity<>(logService.listLogsByTipoLog(tipoLog), HttpStatus.OK);
    }
}
