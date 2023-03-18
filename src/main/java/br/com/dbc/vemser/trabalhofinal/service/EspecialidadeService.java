package br.com.dbc.vemser.trabalhofinal.service;

import br.com.dbc.vemser.trabalhofinal.dto.EspecialidadeCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.EspecialidadeDTO;
import br.com.dbc.vemser.trabalhofinal.entity.EspecialidadeEntity;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.repository.EspecialidadeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class EspecialidadeService {
    private final EspecialidadeRepository especialidadeRepository;
    private final ObjectMapper objectMapper;


    public List<EspecialidadeDTO> listar() throws RegraDeNegocioException {
        return especialidadeRepository.findAll().stream()
                .map(especialidadeEntity -> objectMapper.convertValue(especialidadeEntity, EspecialidadeDTO.class))
                .toList();
    }

    public EspecialidadeDTO getById(Integer id) throws RegraDeNegocioException {
        return objectMapper.convertValue(getEspecialidade(id), EspecialidadeDTO.class);
    }

    public EspecialidadeDTO adicionar(EspecialidadeCreateDTO especialidade) throws RegraDeNegocioException {
        checarSeTemNumero(especialidade.getNome());

        EspecialidadeEntity especialidadeEntity = objectMapper.convertValue(especialidade, EspecialidadeEntity.class);
        especialidadeRepository.save(especialidadeEntity);
        EspecialidadeDTO especialidadeDTO = objectMapper.convertValue(especialidadeEntity, EspecialidadeDTO.class);

        return especialidadeDTO;
    }

    public EspecialidadeDTO editar(Integer id, EspecialidadeCreateDTO especialidade) throws RegraDeNegocioException {
        checarSeTemNumero(especialidade.getNome());

        EspecialidadeEntity especialidadeEntityRecuperada = getEspecialidade(id);

        especialidadeEntityRecuperada.setNomeEspecialidade(especialidade.getNome());
        especialidadeEntityRecuperada.setValor(especialidade.getValor());

        especialidadeRepository.save(especialidadeEntityRecuperada);

        return objectMapper.convertValue(especialidadeEntityRecuperada, EspecialidadeDTO.class);
    }

    public void remover(Integer id) throws RegraDeNegocioException {
        especialidadeRepository.delete(getEspecialidade(id));
    }

    public EspecialidadeEntity getEspecialidade(Integer id) throws RegraDeNegocioException {
        return especialidadeRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Especialidade não existe!"));
    }

    public void checarSeTemNumero(String string) throws RegraDeNegocioException {
        if (string.matches(".*[0-9].*")) { // checa se tem número no nome
            throw new RegraDeNegocioException("O nome da especialidade não pode conter número");
        }
    }
}
