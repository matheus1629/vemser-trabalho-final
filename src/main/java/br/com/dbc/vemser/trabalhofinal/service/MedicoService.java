package br.com.dbc.vemser.trabalhofinal.service;

import br.com.dbc.vemser.trabalhofinal.dto.MedicoCompletoDTO;
import br.com.dbc.vemser.trabalhofinal.dto.MedicoCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.MedicoDTO;
import br.com.dbc.vemser.trabalhofinal.entity.MedicoEntity;
import br.com.dbc.vemser.trabalhofinal.entity.TipoUsuario;
import br.com.dbc.vemser.trabalhofinal.exceptions.BancoDeDadosException;
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


    public MedicoCompletoDTO adicionar(MedicoCreateDTO medico) throws RegraDeNegocioException {
        MedicoEntity medicoEntity = objectMapper.convertValue(medico, MedicoEntity.class);
        medicoEntity.setEspecialidadeEntity(especialidadeService.getEspecialidade(medico.getIdEspecialidade()));
        medicoEntity.setUsuarioEntity(usuarioService.);

        medicoRepository.save(medicoEntity);

        return objectMapper.convertValue(medicoEntity, MedicoCompletoDTO.class);
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
//        try {
//            return medicoRepository.listarMedicoCompletoDTOs();
//        } catch (BancoDeDadosException e) {
//            throw new RegraDeNegocioException("Informações do médico não mostradas por problema no banco de dados.");
//        }
        return null;
    }

    public MedicoEntity getMedico(Integer id) throws RegraDeNegocioException {
        return medicoRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Médico não existe!"));
    }

}
