package br.com.dbc.vemser.trabalhofinal.service;

import br.com.dbc.vemser.trabalhofinal.dto.*;
import br.com.dbc.vemser.trabalhofinal.entity.EspecialidadeEntity;
import br.com.dbc.vemser.trabalhofinal.entity.MedicoEntity;
import br.com.dbc.vemser.trabalhofinal.entity.UsuarioEntity;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.repository.MedicoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class MedicoService {
    private final MedicoRepository medicoRepository;
    private final ObjectMapper objectMapper;
    private final UsuarioService usuarioService;
    private final EspecialidadeService especialidadeService;


    public List<MedicoDTO> listar() throws RegraDeNegocioException {
        return medicoRepository.findAll().stream()
                .map(medicoEntity -> objectMapper.convertValue(medicoEntity, MedicoDTO.class))
                .toList();
    }

    public MedicoCompletoDTO getById(Integer idMedico) throws RegraDeNegocioException {
//        getMedico(idMedico);
//        MedicoDTO medicoComEspecialidadeEUsuario = medicoRepository.findMedicoComEspecialidadeEUsuario(idMedico);
//        return medicoComEspecialidadeEUsuario;
        return null;
    }

    public MedicoCompletoDTO adicionar(MedicoCreateDTO medico) throws RegraDeNegocioException {

        MedicoEntity medicoEntity = objectMapper.convertValue(medico, MedicoEntity.class);
        EspecialidadeEntity especialidade = especialidadeService.getEspecialidade(medico.getIdEspecialidade());
        UsuarioEntity usuario = usuarioService.getUsuario(medico.getIdUsuario());

        if (Objects.equals(usuario.getTipoUsuario().getValor(), 2)) {
            throw new RegraDeNegocioException("Este usuário não é um médico!");
        }

        medicoEntity.setEspecialidadeEntity(especialidade);
        medicoEntity.setUsuarioEntity(usuario);

        medicoRepository.save(medicoEntity);

        // criação do MedicoCompletoDTO para retorno
        MedicoCompletoDTO medicoCompletoDTO = objectMapper.convertValue(medicoEntity, MedicoCompletoDTO.class);
        medicoCompletoDTO.setEspecialidade(objectMapper.convertValue(especialidade, EspecialidadeDTO.class));
        medicoCompletoDTO.setUsuario(objectMapper.convertValue(usuario, UsuarioDTO.class));

        return medicoCompletoDTO;
    }

    public MedicoCompletoDTO editar(Integer id, MedicoCreateDTO medico) throws RegraDeNegocioException {
        MedicoEntity medicoEntity = objectMapper.convertValue(getMedico(id), MedicoEntity.class);

        EspecialidadeEntity especialidade = especialidadeService.getEspecialidade(medico.getIdEspecialidade());
        UsuarioEntity usuario = usuarioService.getUsuario(medico.getIdUsuario());

        if (Objects.equals(usuario.getTipoUsuario().getValor(), 2)) {
            throw new RegraDeNegocioException("Este usuário não é um médico!");
        }

        medicoEntity.setUsuarioEntity(usuario);
        medicoEntity.setEspecialidadeEntity(especialidade);
        medicoEntity.setCrm(medico.getCrm());

        medicoRepository.save(medicoEntity);

        MedicoCompletoDTO medicoCompletoDTO = objectMapper.convertValue(medicoEntity, MedicoCompletoDTO.class);
        medicoCompletoDTO.setEspecialidade(objectMapper.convertValue(especialidade, EspecialidadeDTO.class));
        medicoCompletoDTO.setUsuario(objectMapper.convertValue(usuario, UsuarioDTO.class));

        return medicoCompletoDTO;
    }

    public void remover(Integer id) throws RegraDeNegocioException {
        medicoRepository.delete(getMedico(id));
    }

    public List<MedicoCompletoDTO> listarFull() throws RegraDeNegocioException {
        return null;
    }

    public MedicoEntity getMedico(Integer id) throws RegraDeNegocioException {
        return medicoRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Médico não existe!"));
    }

}
