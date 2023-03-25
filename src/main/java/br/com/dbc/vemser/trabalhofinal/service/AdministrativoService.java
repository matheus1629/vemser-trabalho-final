package br.com.dbc.vemser.trabalhofinal.service;

import br.com.dbc.vemser.trabalhofinal.dto.ClienteCompletoDTO;
import br.com.dbc.vemser.trabalhofinal.dto.PageDTO;
import br.com.dbc.vemser.trabalhofinal.dto.UsuarioCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.UsuarioDTO;
import br.com.dbc.vemser.trabalhofinal.entity.UsuarioEntity;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AdministrativoService {

    private final UsuarioService usuarioService;
    private final ObjectMapper objectMapper;
    private final UsuarioRepository usuarioRepository;

    private final EmailService emailService;

    public UsuarioDTO reativarUsuario(Integer idUsuario) throws RegraDeNegocioException {
        return usuarioService.reativarUsuario(idUsuario);
    }

    public List<UsuarioDTO> listar(){
        return usuarioRepository.findAll()
                .stream()
                .filter(usuarioEntity -> usuarioEntity.getIdCargo().equals(1))
                .map(adm -> objectMapper.convertValue(adm, UsuarioDTO.class))
                .collect(Collectors.toList());
    }

    public UsuarioDTO adicionar(UsuarioCreateDTO usuario) throws RegraDeNegocioException{

        UsuarioEntity usuarioEntity = objectMapper.convertValue(usuario, UsuarioEntity.class);
        usuarioEntity.setIdCargo(1);

        usuarioService.validarUsuarioAdicionado(usuarioEntity);
        usuarioService.adicionar(usuarioEntity);

        usuarioRepository.save(usuarioEntity);

        return usuarioService.getById(usuarioEntity.getIdUsuario());
    }

    public UsuarioDTO editar(Integer id, UsuarioCreateDTO usuario) throws RegraDeNegocioException {

        UsuarioEntity usuarioEntity = getAdm(id);

        UsuarioCreateDTO usuarioCreateDTO = objectMapper.convertValue(usuario, UsuarioCreateDTO.class);
        usuarioCreateDTO.setIdCargo(1);

        usuarioService.validarUsuarioEditado(usuarioCreateDTO, usuarioEntity.getIdUsuario());
        usuarioService.editar(usuarioCreateDTO, usuarioEntity.getIdUsuario());


        UsuarioEntity usuarioEditado = usuarioRepository.save(usuarioEntity);

        return usuarioService.getById(usuarioEditado.getIdUsuario());
    }

    public void remover(Integer id) throws RegraDeNegocioException {
        usuarioService.remover(getAdm(id).getIdUsuario());
    }

    public UsuarioEntity getAdm(Integer id) throws RegraDeNegocioException {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Administrador não existe!"));
    }
}
