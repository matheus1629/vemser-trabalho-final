package br.com.dbc.vemser.trabalhofinal.controller;

import br.com.dbc.vemser.trabalhofinal.controller.documentacao.DocumentacaoMedico;
import br.com.dbc.vemser.trabalhofinal.dto.AgendamentoMedicoEditarCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.agendamento.AgendamentoDTO;
import br.com.dbc.vemser.trabalhofinal.dto.agendamento.AgendamentoListaDTO;
import br.com.dbc.vemser.trabalhofinal.dto.medico.MedicoCompletoDTO;
import br.com.dbc.vemser.trabalhofinal.dto.medico.MedicoUpdateDTO;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.service.MedicoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "Médico")
@Slf4j
@Validated
@RequestMapping("/medico")
@RestController
@RequiredArgsConstructor
public class MedicoController implements DocumentacaoMedico<MedicoCompletoDTO, Integer, AgendamentoMedicoEditarCreateDTO> {

    private final MedicoService medicoService;


    @Override
    @GetMapping("/verificar-info")
    public ResponseEntity<MedicoCompletoDTO> recuperarMedico() throws RegraDeNegocioException {
        return new ResponseEntity<>(medicoService.recuperarMedico(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AgendamentoListaDTO> getMedicoAgentamentos() throws RegraDeNegocioException {
        return new ResponseEntity<>(medicoService.getMedicoAgentamentos(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MedicoCompletoDTO> update(MedicoUpdateDTO medico) throws RegraDeNegocioException {
        MedicoCompletoDTO medicoAtualizado = medicoService.editar(medico);
        return new ResponseEntity<>(medicoAtualizado, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AgendamentoDTO> editarAgendamento(@RequestParam("idAgendamento") Integer idAgendamento,
                                                            @RequestBody @Valid AgendamentoMedicoEditarCreateDTO agendamentoMedicoEditarCreateDTO) throws RegraDeNegocioException, JsonProcessingException {
        return new ResponseEntity<>(medicoService.editarAgendamentoMedico(idAgendamento, agendamentoMedicoEditarCreateDTO), HttpStatus.OK);
    }

}
