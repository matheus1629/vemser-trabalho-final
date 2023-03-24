package br.com.dbc.vemser.trabalhofinal.controller;

import br.com.dbc.vemser.trabalhofinal.controller.documentacao.InterfaceDocumentacao;
import br.com.dbc.vemser.trabalhofinal.dto.ClienteCompletoDTO;
import br.com.dbc.vemser.trabalhofinal.dto.MedicoCompletoDTO;
import br.com.dbc.vemser.trabalhofinal.dto.MedicoCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.PageDTO;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.service.MedicoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RequestMapping("/medico")
@RestController
@RequiredArgsConstructor
public class MedicoController implements InterfaceDocumentacao<MedicoCompletoDTO, MedicoCreateDTO, Integer, Integer> {

    private final MedicoService medicoService;


    @Override
    public ResponseEntity<PageDTO<MedicoCompletoDTO>> list(Integer pagina, Integer tamanho) {
        return new ResponseEntity<>(medicoService.list(pagina, tamanho), HttpStatus.OK);
    }

    @GetMapping("/verificar-info")
    public ResponseEntity<MedicoCompletoDTO> recuperarCliente() throws RegraDeNegocioException {
        return new ResponseEntity<>(medicoService.recuperarMedico(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MedicoCompletoDTO> getById(Integer id) throws RegraDeNegocioException {
        return new ResponseEntity<>(medicoService.getById(id), HttpStatus.OK);
    }


    @Override     //TODO retirar o create do Medico e Cliente
    public ResponseEntity<MedicoCompletoDTO> create(MedicoCreateDTO medico) throws RegraDeNegocioException {
        return new ResponseEntity<>(medicoService.adicionar(medico), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MedicoCompletoDTO> update(Integer id, MedicoCreateDTO medico) throws RegraDeNegocioException {
        MedicoCompletoDTO medicoAtualizado = medicoService.editar(id, medico);
        return new ResponseEntity<>(medicoAtualizado, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> delete(Integer id) throws RegraDeNegocioException {
        medicoService.remover(id);
        return ResponseEntity.ok().build();
    }

}
