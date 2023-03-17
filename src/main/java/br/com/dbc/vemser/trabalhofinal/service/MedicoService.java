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

@RequiredArgsConstructor
@Service
public class MedicoService {
    private final MedicoRepository medicoRepository;
    private final ObjectMapper objectMapper;
    private final UsuarioService usuarioService;
    private final EspecialidadeService especialidadeService;


    public MedicoCompletoDTO adicionar(MedicoCreateDTO medico) throws RegraDeNegocioException {
        MedicoEntity medicoEntity = objectMapper.convertValue(medico, MedicoEntity.class);
        EspecialidadeEntity especialidade = especialidadeService.getEspecialidade(medico.getIdEspecialidade());
        UsuarioEntity usuario = usuarioService.getUsuario(medico.getIdUsuario());

        medicoEntity.setEspecialidadeEntity(especialidade);
        medicoEntity.setUsuarioEntity(usuario);

        medicoRepository.save(medicoEntity);

        // criação do MedicoCompletoDTO para retorno
        MedicoCompletoDTO medicoCompletoDTO = objectMapper.convertValue(medicoEntity, MedicoCompletoDTO.class);
        medicoCompletoDTO.setEspecialidade(objectMapper.convertValue(especialidade, EspecialidadeDTO.class));
        medicoCompletoDTO.setUsuario(objectMapper.convertValue(usuario, UsuarioDTO.class));

        return medicoCompletoDTO;
    }

    public void remover(Integer id) throws RegraDeNegocioException {
//        try {
//            getMedico(id);
//            medicoRepository.remover(id);
//        } catch (BancoDeDadosException e) {
//            throw new RegraDeNegocioException("Médico não removido por problema no banco de dados.");
//        }
    }

    public MedicoCompletoDTO editar(Integer id, MedicoCreateDTO medico) throws RegraDeNegocioException {
//        try {
//            getMedico(id);
//            if (!Objects.equals(getMedico(id).getIdUsuario(), medico.getIdUsuario())) {
//                usuarioService.verificarIdUsuario(medico.getIdUsuario(), TipoUsuario.MEDICO);
//            }
//            MedicoEntity medicoEntityEditar = objectMapper.convertValue(medico, MedicoEntity.class);
//            medicoRepository.editar(id, medicoEntityEditar);
//            return getById(id);
//        } catch (BancoDeDadosException e) {
//            throw new RegraDeNegocioException("Médico não editado por problema no banco de dados.");
//        }

        return null;
    }

    public List<MedicoDTO> listar() throws RegraDeNegocioException {
        return medicoRepository.findAll().stream()
                .map(medicoEntity -> objectMapper.convertValue(medicoEntity, MedicoDTO.class))
                .toList();
    }

    public MedicoCompletoDTO getById(Integer idMedico) throws RegraDeNegocioException { //
//        getMedico(idMedico);
//        MedicoDTO medicoComEspecialidadeEUsuario = medicoRepository.findMedicoComEspecialidadeEUsuario(idMedico);
//        return medicoComEspecialidadeEUsuario;
        return null;
    }

    public List<MedicoCompletoDTO> listarFull() throws RegraDeNegocioException {
            List<MedicoDTO> medicoDTOS = listar();
            List<EspecialidadeDTO> especialidadeDTOS = especialidadeService.listar();
//            List<UsuarioDTO> usuarioDTOS = usuarioService
        return null;
    }

    public MedicoEntity getMedico(Integer id) throws RegraDeNegocioException {
        return medicoRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Médico não existe!"));
    }

}
