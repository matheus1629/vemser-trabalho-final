package br.com.dbc.vemser.trabalhofinal.controller;

import br.com.dbc.vemser.trabalhofinal.controller.documentacao.DocumentacaoAgendamento;

import br.com.dbc.vemser.trabalhofinal.dto.PageDTO;
import br.com.dbc.vemser.trabalhofinal.dto.agendamento.AgendamentoClienteRelatorioDTO;
import br.com.dbc.vemser.trabalhofinal.dto.agendamento.AgendamentoCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.agendamento.AgendamentoDTO;
import br.com.dbc.vemser.trabalhofinal.dto.agendamento.AgendamentoMedicoRelatorioDTO;
import br.com.dbc.vemser.trabalhofinal.entity.AprovarReprovarSolicitacao;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.service.AgendamentoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Agendamento")
@Validated
@RestController
@RequestMapping("/agendamento")
@RequiredArgsConstructor
public class AgendamentoController implements DocumentacaoAgendamento {

    private final AgendamentoService agendamentoService;

    @Override
    public ResponseEntity<PageDTO<AgendamentoDTO>> list(Integer pagina, Integer tamanho) {
        return new ResponseEntity<>(agendamentoService.findAllPaginado(pagina, tamanho), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AgendamentoDTO> getById(Integer id) throws RegraDeNegocioException {
        return new ResponseEntity<>(agendamentoService.getById(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AgendamentoClienteRelatorioDTO> getClienteByIdPersonalizado(@PathVariable("idCliente") Integer idCliente) throws RegraDeNegocioException {
        return new ResponseEntity<>(agendamentoService.getRelatorioClienteById(idCliente), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AgendamentoMedicoRelatorioDTO> getMedicoByIdPersonalizado(@PathVariable("idMedico") Integer idMedico) throws RegraDeNegocioException {
        return new ResponseEntity<>(agendamentoService.getRelatorioMedicoById(idMedico), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AgendamentoDTO> create(String idSolicitacao, AprovarReprovarSolicitacao aprovarReprovarSolicitacao) throws RegraDeNegocioException {
        return new ResponseEntity<>(agendamentoService.adicionar(idSolicitacao, aprovarReprovarSolicitacao), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AgendamentoDTO> update(Integer id, AgendamentoCreateDTO agendamento) throws RegraDeNegocioException {
        return new ResponseEntity<>(agendamentoService.editar(id, agendamento), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> delete(Integer id) throws RegraDeNegocioException {
        agendamentoService.remover(id);
        return ResponseEntity.ok().build();
    }

}
