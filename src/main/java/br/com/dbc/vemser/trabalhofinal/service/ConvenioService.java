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
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ConvenioService {
    private final ConvenioRepository convenioRepository;
    private final ObjectMapper objectMapper;


    public ConvenioDTO adicionar(ConvenioCreateDTO convenio) throws RegraDeNegocioException {
        ConvenioEntity convenioEntity = objectMapper.convertValue(convenio, ConvenioEntity.class);
        convenioRepository.save(convenioEntity);
        return objectMapper.convertValue(convenioEntity, ConvenioDTO.class);
    }

    public void remover(Integer id) throws RegraDeNegocioException {
        getConvenio(id);
        convenioRepository.deleteById(id);
    }


    public ConvenioDTO editar(Integer id, ConvenioCreateDTO convenio) throws RegraDeNegocioException {
//        try {
//            getConvenio(id);
//            ConvenioEntity convenioEntity = objectMapper.convertValue(convenio, ConvenioEntity.class);
//            convenioRepository.editar(id, convenioEntity);
//            return objectMapper.convertValue(convenioEntity, ConvenioDTO.class);
//        } catch (BancoDeDadosException e) {
//            throw new RegraDeNegocioException("Erro no Banco!");
//        }

        return null;
    }

    public List<ConvenioDTO> listar() throws RegraDeNegocioException {
        return convenioRepository.findAll()
                .stream()
                .map(convenioEntity -> objectMapper.convertValue(convenioEntity, ConvenioDTO.class))
                .collect(Collectors.toList());
    }

    public Optional<ConvenioEntity> getConvenio(Integer id) throws RegraDeNegocioException {
            Optional<ConvenioEntity> convenioEntityEncontrado = convenioRepository.findById(id);
            if (convenioEntityEncontrado == null) {
                throw new RegraDeNegocioException("Convênio não encontrado!");
            }
            return convenioEntityEncontrado;
    }

    public ConvenioDTO getById(Integer id) throws RegraDeNegocioException {
        return objectMapper.convertValue(getConvenio(id), ConvenioDTO.class);
    }
}
