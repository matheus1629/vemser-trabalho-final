package br.com.dbc.vemser.trabalhofinal.service;

import br.com.dbc.vemser.trabalhofinal.dto.MedicoCompletoDTO;
import br.com.dbc.vemser.trabalhofinal.dto.MedicoCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.MedicoDTO;
import br.com.dbc.vemser.trabalhofinal.entity.MedicoEntity;
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


    public MedicoCompletoDTO adicionar(MedicoCreateDTO medico) throws RegraDeNegocioException {
//        try {
//            usuarioService.verificarIdUsuario(medico.getIdUsuario(), TipoUsuario.MEDICO);
//            MedicoEntity medicioAdicionar = objectMapper.convertValue(medico, MedicoEntity.class);
//            MedicoEntity medicoEntityAdicionado = medicoRepository.adicionar(medicioAdicionar);
//
//            return getById(medicoEntityAdicionado.getIdMedico());
//        } catch (BancoDeDadosException e) {
//            throw new RegraDeNegocioException("Médico não adicionado por problema no banco de dados.");
//        }
        return null;
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
//        try {
//            return medicoRepository.listar().stream().map(medicoEntity ->
//                    objectMapper.convertValue(medicoEntity, MedicoDTO.class)).toList();
//        } catch (BancoDeDadosException e) {
//            throw new RegraDeNegocioException("Médicos não listados por problema no banco de dados.");
//        }
        return null;
    }

    public MedicoCompletoDTO getById(Integer idMedico) throws RegraDeNegocioException {
//        try {
//             MedicoCompletoDTO medicoEncontrado = medicoRepository.getMedicoCompletoDTO(idMedico);
//            if (medicoEncontrado == null) {
//                throw new RegraDeNegocioException("Médico não existe!");
//            }
//            return medicoEncontrado;
//        } catch (BancoDeDadosException e) {
//            throw new RegraDeNegocioException("Informações do médico não mostradas por problema no banco de dados.");
//        }
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
//        try {
//            return medicoRepository.listar()
//                    .stream()
//                    .filter(medicoEntity -> medicoEntity.getIdMedico().equals(id))
//                    .findFirst()
//                    .orElseThrow(() -> new RegraDeNegocioException("Médico não encontrado!"));
//        } catch (BancoDeDadosException e) {
//            throw new RegraDeNegocioException("Médico não encontrado por problema no banco de dados.");
//        }
//    }

        return null;
    }
}
