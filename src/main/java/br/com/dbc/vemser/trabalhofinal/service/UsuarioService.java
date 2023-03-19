package br.com.dbc.vemser.trabalhofinal.service;

import br.com.dbc.vemser.trabalhofinal.dto.AgendamentoDTO;
import br.com.dbc.vemser.trabalhofinal.dto.PageDTO;
import br.com.dbc.vemser.trabalhofinal.dto.UsuarioCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.UsuarioDTO;
import br.com.dbc.vemser.trabalhofinal.entity.AgendamentoEntity;
import br.com.dbc.vemser.trabalhofinal.entity.TipoUsuario;
import br.com.dbc.vemser.trabalhofinal.entity.UsuarioEntity;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

//<TODO> VER SE VAMOS MANTER A CONTROLLER DO USUARIO OU SE VAMOS MECLAR USUARIO SERVICE NO MEDICO E CLIENTE.

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;
    private final EmailService emailService;

    public UsuarioDTO adicionar(UsuarioCreateDTO usuario) throws RegraDeNegocioException {

        validarUsuario(objectMapper.convertValue(usuario, UsuarioEntity.class));
        UsuarioEntity usuarioAdicionado = usuarioRepository.save(objectMapper.convertValue(usuario, UsuarioEntity.class));

        return objectMapper.convertValue(usuarioAdicionado, UsuarioDTO.class);
    }

    public void remover(Integer id) throws RegraDeNegocioException {
        UsuarioEntity usuario = getUsuario(id);
        usuarioRepository.deleteById(usuario.getIdUsuario());
    }

    public UsuarioDTO editar(Integer id, UsuarioCreateDTO usuario) throws RegraDeNegocioException {

            UsuarioEntity usuarioRecuperado = getUsuario(id);
            validarUsuario(objectMapper.convertValue(usuario, UsuarioEntity.class));

            usuarioRecuperado.setCpf(usuario.getCpf());
            usuarioRecuperado.setEmail(usuario.getEmail());
            usuarioRecuperado.setNome(usuario.getNome());
            usuarioRecuperado.setSenha(usuario.getSenha());
            usuarioRecuperado.setCep(usuario.getCep());
            usuarioRecuperado.setNumero(usuario.getNumero());
            usuarioRecuperado.setContatos(usuario.getContatos());
            usuarioRecuperado.setTipoUsuario(usuario.getTipoUsuario());

        return objectMapper.convertValue(usuarioRepository.save(usuarioRecuperado), UsuarioDTO.class);
    }

    public List<UsuarioDTO> listar() throws RegraDeNegocioException {
        return usuarioRepository.findAll().stream().map(usuario -> objectMapper.convertValue(usuario, UsuarioDTO.class)).toList();
    }

    //<TODO> ver uma forma de verificar diretamente no banco
    private UsuarioEntity validarUsuario(UsuarioEntity usuarioEntity) throws RegraDeNegocioException {

        List<UsuarioEntity> usuarioEntities = usuarioRepository.findAll();

        for (UsuarioEntity value : usuarioEntities) {
            //Quando estivermos atualizando, devemos verifiar se email e cpf já existem em outro usuário ALÉM do que está sendo atualizado.
            if (value.getCpf().equals(usuarioEntity.getCpf()) &&
                    !Objects.equals(value.getIdUsuario(), usuarioEntity.getIdUsuario())) {
                throw new RegraDeNegocioException("Já existe usuário com esse CPF!");
            }
            if (value.getEmail().equals(usuarioEntity.getEmail()) &&
                    !Objects.equals(value.getIdUsuario(), usuarioEntity.getIdUsuario())) {
                throw new RegraDeNegocioException("Já existe usuário com esse e-mail!");
            }
        }
        return usuarioEntity;
    }

    public UsuarioEntity getUsuario(Integer id) throws RegraDeNegocioException {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado!"));
    }

    public UsuarioDTO getById(Integer id) throws RegraDeNegocioException {
        return objectMapper.convertValue(getUsuario(id), UsuarioDTO.class);
    }

    // Verifica a disponilidade do id_usuario

    public void verificarIdUsuario(Integer id, TipoUsuario tipoUsuario) throws RegraDeNegocioException {
        if (getUsuario(id).getTipoUsuario() != tipoUsuario) {
            throw new RegraDeNegocioException("O id de usuário informado não é adequado para esta operação!");
        }
    }
    public PageDTO<UsuarioDTO> listAll(Integer pagina,Integer tamanho ) {
        Pageable solicitacaoPagina = PageRequest.of(pagina,tamanho);
        Page<UsuarioEntity> usuario = usuarioRepository.findAll(solicitacaoPagina);
        List<UsuarioDTO> usuarioDTO = usuario.getContent().stream()
                .map(x -> objectMapper.convertValue(x, UsuarioDTO.class))
                .toList();

        return new PageDTO<>(usuario.getTotalElements(),
                usuario.getTotalPages(),
                pagina,
                tamanho,
                usuarioDTO);
    }
}
