package br.com.dbc.vemser.trabalhofinal.controller;

import br.com.dbc.vemser.trabalhofinal.dto.*;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.service.AgendamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/agendamento")
@RequiredArgsConstructor
public class AgendamentoController implements InterfaceDocumentacao<AgendamentoDTO, AgendamentoCreateDTO, Integer> {

    private final AgendamentoService agendamentoService;

    @Override
    public ResponseEntity<List<AgendamentoDTO>> listAll() throws RegraDeNegocioException {
        return new ResponseEntity<>(agendamentoService.listar(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AgendamentoDTO> getById(Integer id) throws RegraDeNegocioException {
        return new ResponseEntity<>(agendamentoService.getById(id), HttpStatus.OK);
    }

    @GetMapping("/Relatorio-Cliente/{idCliente}")
    public ResponseEntity<AgendamentoClientePersonalizadoDTO> getClienteByIdPersonalizado(@PathVariable("idCliente") Integer idCliente) throws RegraDeNegocioException {
        return new ResponseEntity<>(agendamentoService.getRelatorioClienteById(idCliente), HttpStatus.OK);
    }

//    @GetMapping("/medico/${idMedico}")
//    public ResponseEntity<MedicoPersonalizadoDTO> getMedicoByIdPersonalizado(@RequestParam("idMedico") Integer idMedico) throws RegraDeNegocioException {
//        return null;
////        return new ResponseEntity<>(agendamentoService.getRelatorioMedicoById(id), HttpStatus.OK);
//    }

    @Override
    public ResponseEntity<AgendamentoDTO> create(AgendamentoCreateDTO agendamento) throws RegraDeNegocioException {
        return new ResponseEntity<>(agendamentoService.adicionar(agendamento), HttpStatus.OK);
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

    @GetMapping("/paginado")
    public PageDTO<AgendamentoDTO> listarPaginado(Integer pagina, Integer tamanho) {
        return agendamentoService.findAllPaginado(pagina, tamanho);
    }

}
