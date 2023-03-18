package br.com.dbc.vemser.trabalhofinal.service;

import br.com.dbc.vemser.trabalhofinal.dto.ConvenioCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.ConvenioDTO;
import br.com.dbc.vemser.trabalhofinal.entity.ConvenioEntity;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.repository.ConvenioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ConvenioService {
    private final ConvenioRepository convenioRepository;
    private final ObjectMapper objectMapper;


    public ConvenioDTO adicionar(ConvenioCreateDTO convenio) throws RegraDeNegocioException {
        ConvenioEntity convenioEntity = objectMapper.convertValue(convenio, ConvenioEntity.class);
        ConvenioEntity convenioEntityAdicionado = convenioRepository.save(convenioEntity);
        return objectMapper.convertValue(convenioEntityAdicionado, ConvenioDTO.class);
    }

    public void remover(Integer id) throws RegraDeNegocioException {
        getConvenio(id);
        convenioRepository.deleteById(id);
    }


    public ConvenioDTO editar(Integer id, ConvenioCreateDTO convenio) throws RegraDeNegocioException {
            ConvenioEntity convenioEntityRecuperado = getConvenio(id);
            convenioEntityRecuperado.setTaxaAbatimento(convenio.getTaxaAbatimento());
            convenioEntityRecuperado.setCadastroOrgaoRegulador(convenio.getCadastroOrgaoRegulador());

            convenioRepository.save(convenioEntityRecuperado);

            return objectMapper.convertValue(convenioEntityRecuperado, ConvenioDTO.class);
    }

    public List<ConvenioDTO> listar() throws RegraDeNegocioException {
        return convenioRepository.findAll()
                .stream()
                .map(convenioEntity -> objectMapper.convertValue(convenioEntity, ConvenioDTO.class))
                .collect(Collectors.toList());
    }

    public ConvenioEntity getConvenio(Integer id) throws RegraDeNegocioException {
            return convenioRepository.findById(id).orElseThrow(()-> new RegraDeNegocioException("Convenio não existe"));
    }

    public ConvenioDTO getById(Integer id) throws RegraDeNegocioException {
        return objectMapper.convertValue(getConvenio(id), ConvenioDTO.class);
    }
}
