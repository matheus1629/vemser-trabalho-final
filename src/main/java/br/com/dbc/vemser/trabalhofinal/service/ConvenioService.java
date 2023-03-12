package br.com.dbc.vemser.trabalhofinal.service;

import br.com.dbc.vemser.trabalhofinal.dto.ConvenioCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.ConvenioDTO;
import br.com.dbc.vemser.trabalhofinal.entity.Convenio;
import br.com.dbc.vemser.trabalhofinal.exceptions.BancoDeDadosException;
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
        try {
            Convenio convenioEntity = objectMapper.convertValue(convenio, Convenio.class);
            convenioRepository.adicionar(convenioEntity);
            return objectMapper.convertValue(convenioEntity, ConvenioDTO.class);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro no Banco!");
        }
    }

    public void remover(Integer id) throws RegraDeNegocioException {
        try {
            getConvenio(id);
            convenioRepository.remover(id);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro no Banco!");
        }
    }


    public ConvenioDTO editar(Integer id, ConvenioCreateDTO convenio) throws RegraDeNegocioException {
        try {
            getConvenio(id);
            Convenio convenioEntity = objectMapper.convertValue(convenio, Convenio.class);
            convenioRepository.editar(id, convenioEntity);
            return objectMapper.convertValue(convenioEntity, ConvenioDTO.class);
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro no Banco!");
        }

    }

    public List<ConvenioDTO> listar() throws RegraDeNegocioException {
        try {
            return convenioRepository.listar()
                    .stream()
                    .map(convenio -> objectMapper.convertValue(convenio, ConvenioDTO.class))
                    .collect(Collectors.toList());
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro no banco!");
        }
    }

    public Convenio getConvenio(Integer id) throws RegraDeNegocioException {
        try {
            Convenio convenioEncontrado = convenioRepository.getConvenio(id);
            if (convenioEncontrado == null) {
                throw new RegraDeNegocioException("Convênio não encontrado!");
            }
            return convenioEncontrado;
        } catch (BancoDeDadosException e) {
            throw new RegraDeNegocioException("Erro no Banco!");
        }
    }

    public ConvenioDTO getById(Integer id) throws RegraDeNegocioException {
        return objectMapper.convertValue(getConvenio(id), ConvenioDTO.class);
    }
}
