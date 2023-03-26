package br.com.dbc.vemser.trabalhofinal.service;

import br.com.dbc.vemser.trabalhofinal.client.EnderecoClient;
import br.com.dbc.vemser.trabalhofinal.dto.PageDTO;
import br.com.dbc.vemser.trabalhofinal.dto.RedefinicaoSenhaDTO;
import br.com.dbc.vemser.trabalhofinal.dto.TrocaSenhaDTO;
import br.com.dbc.vemser.trabalhofinal.dto.usuario.UsuarioCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.usuario.UsuarioDTO;
import br.com.dbc.vemser.trabalhofinal.entity.RegistroTemporarioEntity;
import br.com.dbc.vemser.trabalhofinal.entity.UsuarioEntity;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;
    private final RegistroTemporarioService registroTemporarioService;
    private final EmailService emailService;
    private final EnderecoClient enderecoClient;


    public void adicionar(UsuarioEntity usuario) throws RegraDeNegocioException {
        usuario.setAtivo(1);
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        usuarioRepository.save(usuario);
    }

    public void remover(Integer id) throws RegraDeNegocioException {
        UsuarioEntity usuario = getUsuario(id);
        if (usuario.getAtivo().equals(0)){
            throw new RegraDeNegocioException("Este usuário já está desativado!");
        }
        usuario.setAtivo(0);
        usuarioRepository.save(usuario);
    }

    public void hardDelete(Integer id) throws RegraDeNegocioException {
        UsuarioEntity usuario = getUsuario(id);
        usuarioRepository.delete(usuario);
    }

    public void editar(UsuarioCreateDTO usuario, Integer id) throws RegraDeNegocioException {

        UsuarioEntity usuarioRecuperado = getUsuario(id);
        usuarioRecuperado.setCpf(usuario.getCpf());
        usuarioRecuperado.setNome(usuario.getNome());
        usuarioRecuperado.setCep(usuario.getCep());
        usuarioRecuperado.setNumero(usuario.getNumero());
        usuarioRecuperado.setContatos(usuario.getContatos());

        usuarioRepository.save(usuarioRecuperado);
    }

    public List<UsuarioDTO> listar() throws RegraDeNegocioException {
        return usuarioRepository.findAll().stream().map(usuario -> objectMapper.convertValue(usuario, UsuarioDTO.class)).toList();
    }

    public void validarUsuarioAdicionado(UsuarioEntity usuarioEntity) throws RegraDeNegocioException {

        List<UsuarioEntity> usuarioEntities = usuarioRepository.findAll();

        if (usuarioEntity.getIdCargo().equals(2)) {
            for (UsuarioEntity value : usuarioEntities) {
                if (value.getMedicoEntity() != null) {
                    if (value.getMedicoEntity().getCrm().equals(usuarioEntity.getMedicoEntity().getCrm())) {
                        throw new RegraDeNegocioException("Já existe usuário com esse CRM!");
                    }
                }
            }
        }

        for (UsuarioEntity value : usuarioEntities) {
            //Quando estivermos atualizando, devemos verifiar se email e cpf já existem em outro
            if (value.getCpf().equals(usuarioEntity.getCpf())) {
                throw new RegraDeNegocioException("Já existe usuário com esse CPF!");
            }
            if (value.getEmail().equals(usuarioEntity.getEmail())) {
                throw new RegraDeNegocioException("Já existe usuário com esse e-mail!");
            }

        }
    }

    public void validarUsuarioEditado(UsuarioCreateDTO usuarioCreateDTO, Integer id) throws RegraDeNegocioException {

        List<UsuarioEntity> usuarioEntities = usuarioRepository.findAll();

        for (UsuarioEntity value : usuarioEntities) {
            //Quando estivermos atualizando, devemos verifiar se email e cpf já existem em outro usuário ALÉM do que está sendo atualizado.
            if (value.getCpf().equals(usuarioCreateDTO.getCpf()) && !value.getIdUsuario().equals(id)) {
                throw new RegraDeNegocioException("Já existe usuário com esse CPF!");
            }
            if (value.getEmail().equals(usuarioCreateDTO.getEmail()) && !value.getIdUsuario().equals(id)) {
                throw new RegraDeNegocioException("Já existe usuário com esse e-mail!");
            }
        }
    }

    public UsuarioEntity getUsuario(Integer id) throws RegraDeNegocioException {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado!"));

    }

    public UsuarioDTO getById(Integer id) throws RegraDeNegocioException {
        UsuarioDTO usuarioDTO = objectMapper.convertValue(getUsuario(id), UsuarioDTO.class);
        usuarioDTO.setEnderecoDTO(enderecoClient.getEndereco(usuarioDTO.getCep()));
        return usuarioDTO;
    }

    // Verifica a disponilidade do id_usuario


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

    public Optional<UsuarioEntity> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public UsuarioDTO reativarUsuario(Integer idUsuario) throws RegraDeNegocioException {
        UsuarioEntity usuarioEntity = getUsuario(idUsuario);
        if (usuarioEntity.getAtivo() == 1) {
            throw new RegraDeNegocioException("Este usuário já está ativo!");
        }
        usuarioEntity.setAtivo(1);
        usuarioRepository.save(usuarioEntity);

        return objectMapper.convertValue(usuarioEntity, UsuarioDTO.class);
    }

    public Integer getIdLoggedUser() {
        return Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
    }

    public void trocarSenha(TrocaSenhaDTO trocaSenhaDTO) throws RegraDeNegocioException {

        UsuarioEntity usuarioAcessado = getUsuario(getIdLoggedUser());
        if(passwordEncoder.matches(trocaSenhaDTO.getSenhaAntiga(), usuarioAcessado.getSenha()) && !passwordEncoder.matches(trocaSenhaDTO.getSenhaNova(), usuarioAcessado.getSenha())){
            usuarioAcessado.setSenha(passwordEncoder.encode(trocaSenhaDTO.getSenhaNova()));
            usuarioRepository.save(usuarioAcessado);
        }else{
            throw new RegraDeNegocioException("Senha inválida.");
        }

    }

    public void solicitarRedefinirSenha(String email) throws RegraDeNegocioException{

        Optional<UsuarioEntity> usuario = findByEmail(email);
        if(usuario.isEmpty()){
            throw new RegraDeNegocioException("Usuário não encontrado.");
        }else{
            Optional<RegistroTemporarioEntity> registroTemporarioEntity = registroTemporarioService.findByIdUsuario(usuario.get().getIdUsuario());
            RegistroTemporarioEntity novoRegistro  = new RegistroTemporarioEntity();
            novoRegistro.setUsuarioEntity(usuario.get());
            Random random = new Random();
            Integer codigoGerado = random.nextInt(8);
            novoRegistro.setCodigo(passwordEncoder.encode(codigoGerado.toString()));
            novoRegistro.setDataGeracao(LocalDateTime.now());
            registroTemporarioService.create(novoRegistro);

            try{
                emailService.sendEmailUsuario(usuario.get(), TipoEmail.USUARIO_REDEFINIR_SENHA, codigoGerado);
            } catch (MessagingException | TemplateException | IOException e) {
                throw new RegraDeNegocioException("Erro ao enviar o e-mail com código de redefinição.");
            }

        }

    }

    public void redefinirSenha(RedefinicaoSenhaDTO redefinicaoSenhaDTO) throws RegraDeNegocioException{

        Optional<UsuarioEntity> usuario = findByEmail(redefinicaoSenhaDTO.getEmail());
        if(usuario.isEmpty()){
            throw new RegraDeNegocioException("Usuário não encontrado.");
        }else{
            Optional<RegistroTemporarioEntity> registroTemporarioEntity = registroTemporarioService.findByIdUsuario(usuario.get().getIdUsuario());
            if(registroTemporarioEntity.isEmpty()){
                throw new RegraDeNegocioException("Registro de redefinição não encontrado ou expirado, solicite o código antes.");
            }else{
                if(passwordEncoder.matches(redefinicaoSenhaDTO.getCodigoConfirmacao().toString(), registroTemporarioEntity.get().getCodigo())){
                    usuario.get().setSenha(passwordEncoder.encode(redefinicaoSenhaDTO.getSenhaNova()));
                }else{
                    throw new RegraDeNegocioException("O código inválido.");
                }
            }

        }

    }

}
