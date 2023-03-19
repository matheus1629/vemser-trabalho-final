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

    public List<MedicoCompletoDTO> listarFull() throws RegraDeNegocioException {
        return medicoRepository.listarFull();
    }

    public MedicoCompletoDTO getById(Integer idMedico) throws RegraDeNegocioException {
        getMedico(idMedico);
        return medicoRepository.getByIdPersonalizado(idMedico);
    }

    public MedicoCompletoDTO adicionar(MedicoCreateDTO medico) throws RegraDeNegocioException {

        MedicoEntity medicoEntity = objectMapper.convertValue(medico, MedicoEntity.class);
        EspecialidadeEntity especialidade = especialidadeService.getEspecialidade(medico.getIdEspecialidade());
        UsuarioEntity usuario = usuarioService.getUsuario(medico.getIdUsuario());

        if (!Objects.equals(usuario.getTipoUsuario().ordinal(), 1)) {
            throw new RegraDeNegocioException("Este usuário não é um médico!");
        }

        medicoEntity.setEspecialidadeEntity(especialidade);
        medicoEntity.setUsuarioEntity(usuario);

        MedicoEntity medicoAdicionado = medicoRepository.save(medicoEntity);

        return getById(medicoAdicionado.getIdMedico());
    }

    public MedicoCompletoDTO editar(Integer id, MedicoCreateDTO medico) throws RegraDeNegocioException {
        MedicoEntity medicoEntity = objectMapper.convertValue(getMedico(id), MedicoEntity.class);

        EspecialidadeEntity especialidade = especialidadeService.getEspecialidade(medico.getIdEspecialidade());
        UsuarioEntity usuario = usuarioService.getUsuario(medico.getIdUsuario());

        if (!Objects.equals(usuario.getTipoUsuario().ordinal(), 1)) {
            throw new RegraDeNegocioException("Este usuário não é um médico!");
        }

        medicoEntity.setUsuarioEntity(usuario);
        medicoEntity.setEspecialidadeEntity(especialidade);
        medicoEntity.setCrm(medico.getCrm());

        MedicoEntity medicoEditado = medicoRepository.save(medicoEntity);

        return getById(medicoEditado.getIdMedico());
    }

    public void remover(Integer id) throws RegraDeNegocioException {
        medicoRepository.delete(getMedico(id));
    }

    public MedicoEntity getMedico(Integer id) throws RegraDeNegocioException {
        return medicoRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Médico não existe!"));
    }

}
