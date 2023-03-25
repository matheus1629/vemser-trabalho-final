package br.com.dbc.vemser.trabalhofinal.controller;


import br.com.dbc.vemser.trabalhofinal.controller.documentacao.DocumentacaoEspecialidadeConvenio;
import br.com.dbc.vemser.trabalhofinal.dto.EspecialidadeCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.EspecialidadeDTO;
import br.com.dbc.vemser.trabalhofinal.dto.PageDTO;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.service.EspecialidadeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name="Especialidade")
@Validated
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/especialidade")
public class DocumentacaoEspecialidadeController implements DocumentacaoEspecialidadeConvenio<EspecialidadeDTO, EspecialidadeCreateDTO, Integer, Integer> {

    private final EspecialidadeService especialidadeService;


    @Override
    public ResponseEntity<PageDTO<EspecialidadeDTO>> list(Integer pagina, Integer tamanho) {
        return new ResponseEntity<>(especialidadeService.list(pagina, tamanho), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<EspecialidadeDTO> getById(Integer id) throws RegraDeNegocioException {
        return new ResponseEntity<>(especialidadeService.getById(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<EspecialidadeDTO> create(EspecialidadeCreateDTO especialidade) throws RegraDeNegocioException {
        return new ResponseEntity<>(especialidadeService.adicionar(especialidade), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<EspecialidadeDTO> update(Integer id, EspecialidadeCreateDTO especialidade) throws RegraDeNegocioException {
        return new ResponseEntity<>(especialidadeService.editar(id, especialidade), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> delete(Integer id) throws RegraDeNegocioException {
        especialidadeService.remover(id);
        return ResponseEntity.ok().build();
    }

}
