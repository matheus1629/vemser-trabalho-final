package br.com.dbc.vemser.trabalhofinal.service;

import br.com.dbc.vemser.trabalhofinal.client.EnderecoClient;
import br.com.dbc.vemser.trabalhofinal.dto.PageDTO;
import br.com.dbc.vemser.trabalhofinal.dto.cliente.ClienteCompletoDTO;
import br.com.dbc.vemser.trabalhofinal.dto.medico.MedicoCompletoDTO;
import br.com.dbc.vemser.trabalhofinal.dto.usuario.UsuarioCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.usuario.UsuarioDTO;
import br.com.dbc.vemser.trabalhofinal.dto.usuario.UsuarioUpdateDTO;
import br.com.dbc.vemser.trabalhofinal.entity.UsuarioEntity;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.repository.ClienteRepository;
import br.com.dbc.vemser.trabalhofinal.repository.MedicoRepository;
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


@Service
@RequiredArgsConstructor
public class AdministrativoService {

    private final UsuarioService usuarioService;
    private final ObjectMapper objectMapper;
    private final UsuarioRepository usuarioRepository;
    private final EmailService emailService;
    private final ClienteService clienteService;
    private final MedicoService medicoService;
    private final MedicoRepository medicoRepository;
    private final ClienteRepository clienteRepository;
    private final EnderecoClient enderecoClient;

    public UsuarioDTO reativarUsuario(Integer idUsuario) throws RegraDeNegocioException {
        return usuarioService.reativarUsuario(idUsuario);
    }

    public List<UsuarioDTO> listar(){
        List<UsuarioDTO> usuarioDTOS = usuarioRepository.findAll()
                .stream()
                .filter(usuarioEntity -> usuarioEntity.getIdCargo().equals(1))
                .map(adm -> objectMapper.convertValue(adm, UsuarioDTO.class))
                .collect(Collectors.toList());
        System.out.println();
        for (UsuarioDTO usuarioDTO: usuarioDTOS) {
            usuarioDTO.setEnderecoDTO(enderecoClient.getEndereco(usuarioDTO.getCep()));
        }

        return usuarioDTOS;
    }

    public UsuarioDTO adicionar(UsuarioCreateDTO usuario) throws RegraDeNegocioException{

        UsuarioEntity usuarioEntity = objectMapper.convertValue(usuario, UsuarioEntity.class);
        usuarioEntity.setIdCargo(1);

        usuarioService.validarUsuarioAdicionado(usuarioEntity);
        usuarioService.adicionar(usuarioEntity);

        usuarioRepository.save(usuarioEntity);

        try{
            emailService.sendEmailUsuario(usuarioEntity, TipoEmail.USUARIO_CADASTRO, null);
        } catch (MessagingException | TemplateException | IOException e) {
            usuarioService.hardDelete(usuarioEntity.getIdUsuario());
            throw new RegraDeNegocioException("Erro ao enviar o e-mail. Cadastro não realizado.");
        }

        return usuarioService.getById(usuarioEntity.getIdUsuario());
    }

    public UsuarioDTO editar(Integer id, UsuarioUpdateDTO usuario) throws RegraDeNegocioException {

        UsuarioEntity usuarioEntity = getAdm(id);
        if (!usuarioEntity.getIdCargo().equals(1)) {
            throw new RegraDeNegocioException("Este usuário não é um administrador.");
        }

        UsuarioCreateDTO usuarioCreateDTO = objectMapper.convertValue(usuario, UsuarioCreateDTO.class);

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

    public ClienteCompletoDTO getClienteById(Integer id) throws RegraDeNegocioException {
        return clienteService.getById(id);
    }

    public MedicoCompletoDTO getMedicoById(Integer id) throws RegraDeNegocioException {
        return medicoService.getById(id);
    }

    public void removerMedico(Integer id) throws RegraDeNegocioException {
        medicoService.remover(id);
    }

    public void removerCliente(Integer id) throws RegraDeNegocioException {
        clienteService.remover(id);
    }

    public PageDTO<MedicoCompletoDTO> listMedico(Integer pagina, Integer tamanho){
        Pageable solicitacaoPagina = PageRequest.of(pagina,tamanho);
        Page<MedicoCompletoDTO> medico = medicoRepository.listarFull(solicitacaoPagina);
        List<MedicoCompletoDTO> medicoCompletoDTOS = medico.getContent().stream()
                .toList();

        for (MedicoCompletoDTO medicoCompletoDTO: medicoCompletoDTOS) {
            medicoCompletoDTO.setEnderecoDTO(enderecoClient.getEndereco(medicoCompletoDTO.getCep()));
        }

        return new PageDTO<>(medico.getTotalElements(),
                medico.getTotalPages(),
                pagina,
                tamanho,
                medicoCompletoDTOS);
    }

    public PageDTO<ClienteCompletoDTO> listCliente(Integer pagina, Integer tamanho) {
        Pageable solicitacaoPagina = PageRequest.of(pagina,tamanho);
        Page<ClienteCompletoDTO> cliente = clienteRepository.listarFull(solicitacaoPagina);
        List<ClienteCompletoDTO> clienteCompletoDTOS = cliente.getContent().stream()
                .toList();

        for (ClienteCompletoDTO clienteCompletoDTO: clienteCompletoDTOS) {
            clienteCompletoDTO.setEnderecoDTO(enderecoClient.getEndereco(clienteCompletoDTO.getCep()));
        }

        return new PageDTO<>(cliente.getTotalElements(),
                cliente.getTotalPages(),
                pagina,
                tamanho,
                clienteCompletoDTOS);
    }
}
