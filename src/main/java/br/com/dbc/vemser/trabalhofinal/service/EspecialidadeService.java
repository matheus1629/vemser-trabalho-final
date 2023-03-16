package br.com.dbc.vemser.trabalhofinal.service;

import br.com.dbc.vemser.trabalhofinal.dto.EspecialidadeCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.EspecialidadeDTO;
import br.com.dbc.vemser.trabalhofinal.entity.EspecialidadeEntity;
import br.com.dbc.vemser.trabalhofinal.exceptions.BancoDeDadosException;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.repository.EspecialidadeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EspecialidadeService {
    private final EspecialidadeRepository especialidadeRepository;
    private final ObjectMapper objectMapper;

    public EspecialidadeService(EspecialidadeRepository especialidadeRepository, ObjectMapper objectMapper) {
        this.especialidadeRepository = especialidadeRepository;
        this.objectMapper = objectMapper;
    }

    // criação de um objeto
    public EspecialidadeDTO adicionar(EspecialidadeCreateDTO especialidade) throws RegraDeNegocioException {
//        try {
//            EspecialidadeEntity novaEspecialidadeEntity = objectMapper.convertValue(especialidade, EspecialidadeEntity.class);
//            EspecialidadeEntity especialidadeEntityAdicionada = especialidadeRepository.adicionar(novaEspecialidadeEntity);
//            return objectMapper.convertValue(especialidadeEntityAdicionada, EspecialidadeDTO.class);
//        } catch (BancoDeDadosException e) {
//            throw new RegraDeNegocioException("Especialidade não adicionada por problema no banco de dados.");
//        }
        return null;
    }

    // remoção
    public void remover(Integer id) throws RegraDeNegocioException {
//        try {
//            getEspecialidade(id);
//            especialidadeRepository.remover(id);
//        } catch (BancoDeDadosException e) {
//            throw new RegraDeNegocioException("Especialidade não removida por problema no banco de dados.");
//        }
    }


    // atualização de um objeto
    public EspecialidadeDTO editar(Integer id, EspecialidadeCreateDTO especialidade) throws RegraDeNegocioException {
//        try {
//            getEspecialidade(id);
//            EspecialidadeEntity especialidadeEntityEditar = objectMapper.convertValue(especialidade, EspecialidadeEntity.class);
//            especialidadeRepository.editar(id, especialidadeEntityEditar);
//            return getById(id);
//        } catch (BancoDeDadosException e) {
//            throw new RegraDeNegocioException("Especialidade não editada por problema no banco de dados.");
//        }
        return null;
    }

    // leitura
    public List<EspecialidadeDTO> listar() throws RegraDeNegocioException {
//        try {
//            return especialidadeRepository.listar().stream().map(especialidadeEntity -> objectMapper.convertValue(especialidadeEntity, EspecialidadeDTO.class)).toList();
//        } catch (BancoDeDadosException e) {
//            throw new RegraDeNegocioException("Especialidade não adicionada por problema no banco de dados.");
//        }
        return null;
    }

    public EspecialidadeDTO getById(Integer id) throws RegraDeNegocioException {
        return objectMapper.convertValue(getEspecialidade(id), EspecialidadeDTO.class);
    }

    public EspecialidadeEntity getEspecialidade(Integer id) throws RegraDeNegocioException {
//        try{
//            return especialidadeRepository.listar()
//                    .stream()
//                    .filter(especialidadeEntity -> especialidadeEntity.getIdEspecialidade().equals(id))
//                    .findFirst()
//                    .orElseThrow(() -> new RegraDeNegocioException("Especialidade não encontrada!"));
//        }catch(BancoDeDadosException e){
//            throw new RegraDeNegocioException("Especialidade não encontrado por problema no banco de dados.");
//        }
        return null;
    }


}
