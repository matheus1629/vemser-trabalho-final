package br.com.dbc.vemser.trabalhofinal.service;

import br.com.dbc.vemser.trabalhofinal.dto.*;
import br.com.dbc.vemser.trabalhofinal.entity.EspecialidadeEntity;
import br.com.dbc.vemser.trabalhofinal.entity.MedicoEntity;
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
import java.util.Objects;
import java.util.Optional;

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
