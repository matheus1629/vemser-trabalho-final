package br.com.dbc.vemser.trabalhofinal.service;

import br.com.dbc.vemser.trabalhofinal.dto.MedicoCompletoDTO;
import br.com.dbc.vemser.trabalhofinal.dto.MedicoCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.PageDTO;
import br.com.dbc.vemser.trabalhofinal.dto.UsuarioCreateDTO;
import br.com.dbc.vemser.trabalhofinal.entity.MedicoEntity;
import br.com.dbc.vemser.trabalhofinal.entity.TipoUsuario;
import br.com.dbc.vemser.trabalhofinal.entity.UsuarioEntity;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.repository.MedicoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class MedicoService {
    private final MedicoRepository medicoRepository;
    private final ObjectMapper objectMapper;
    private final UsuarioService usuarioService;
    private final EspecialidadeService especialidadeService;


    public PageDTO<MedicoCompletoDTO> list(Integer pagina, Integer tamanho){
        Pageable solicitacaoPagina = PageRequest.of(pagina,tamanho);
        Page<MedicoCompletoDTO> medico = medicoRepository.listarFull(solicitacaoPagina);
        List<MedicoCompletoDTO> medicoDTO = medico.getContent().stream()
                .toList();

        return new PageDTO<>(medico.getTotalElements(),
                medico.getTotalPages(),
                pagina,
                tamanho,
                medicoDTO);
    }

    public MedicoCompletoDTO getById(Integer idMedico) throws RegraDeNegocioException {
        Optional<MedicoCompletoDTO> medicoRetornado = medicoRepository.getByIdPersonalizado(idMedico);
        if(medicoRetornado.isEmpty()){
            throw new RegraDeNegocioException("Usuário não encontrado.");
        }
        return medicoRetornado.get();
    }

    public MedicoCompletoDTO adicionar(MedicoCreateDTO medico) throws RegraDeNegocioException {

        checarSeTemNumero(medico.getNome());

        UsuarioEntity usuarioEntity = objectMapper.convertValue(medico, UsuarioEntity.class);
        usuarioEntity.setTipoUsuario(TipoUsuario.MEDICO);

        // Adicionando o usuario que foi salvado no Medico a salvar
        MedicoEntity medicoEntity = objectMapper.convertValue(medico, MedicoEntity.class);
        usuarioEntity.setMedicoEntity(medicoEntity);
        medicoEntity.setUsuarioEntity(usuarioEntity);

        // Adicionando Convenio em Medico a salvar
        medicoEntity.setEspecialidadeEntity(especialidadeService.getEspecialidade(medico.getIdEspecialidade()));

        usuarioService.validarUsuarioAdicionado(usuarioEntity);
        usuarioService.adicionar(usuarioEntity);

        medicoRepository.save(medicoEntity);

        return getById(medicoEntity.getIdMedico());
    }

    public MedicoCompletoDTO editar(Integer id, MedicoCreateDTO medico) throws RegraDeNegocioException {

        checarSeTemNumero(medico.getNome());

        MedicoEntity medicoEntity = getMedico(id);

        UsuarioCreateDTO usuarioDTO = objectMapper.convertValue(medico, UsuarioCreateDTO.class);
        usuarioDTO.setTipoUsuario(TipoUsuario.MEDICO);


        List<MedicoEntity> listaMedico = medicoRepository.findAll().stream().filter(medicoEntity1 -> medicoEntity1.getCrm().equals(medico.getCrm())).toList();;
        for (MedicoEntity medicoVerificarCRM: listaMedico) {
            if (medico.getCrm().equals(medicoVerificarCRM.getCrm()))
                throw new RegraDeNegocioException("CRM já existe!");
        }
        usuarioService.validarUsuarioEditado(usuarioDTO, medicoEntity.getIdUsuario());
        usuarioService.editar(usuarioDTO, medicoEntity.getIdUsuario());
        
        medicoEntity.setCrm(medico.getCrm());
        medicoEntity.setEspecialidadeEntity(especialidadeService.getEspecialidade(medico.getIdEspecialidade()));
        MedicoEntity medicoEditado = medicoRepository.save(medicoEntity);

        return getById(medicoEditado.getIdMedico());
    }

    public void remover(Integer id) throws RegraDeNegocioException {
        usuarioService.remover(getMedico(id).getIdUsuario());
    }

    public MedicoEntity getMedico(Integer id) throws RegraDeNegocioException {
        return medicoRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Médico não existe!"));
    }

    public void checarSeTemNumero(String string) throws RegraDeNegocioException {
        if (string.matches(".*[0-9].*")) { // checa se tem número no nome
            throw new RegraDeNegocioException("O nome da especialidade não pode conter número");
        }
    }

}
