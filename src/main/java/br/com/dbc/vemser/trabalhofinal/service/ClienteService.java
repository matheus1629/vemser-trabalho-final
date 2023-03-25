package br.com.dbc.vemser.trabalhofinal.service;

import br.com.dbc.vemser.trabalhofinal.dto.*;
import br.com.dbc.vemser.trabalhofinal.entity.ClienteEntity;
import br.com.dbc.vemser.trabalhofinal.entity.ConvenioEntity;
import br.com.dbc.vemser.trabalhofinal.entity.UsuarioEntity;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.repository.ClienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import freemarker.template.TemplateException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j

public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final ObjectMapper objectMapper;
    private final UsuarioService usuarioService;
    private final ConvenioService convenioService;
    private final AgendamentoService agendamentoService;
    private final EmailService emailService;

    public ClienteService(ClienteRepository clienteRepository,
                          ObjectMapper objectMapper,
                          UsuarioService usuarioService,
                          ConvenioService convenioService,
                          @Lazy AgendamentoService agendamentoService,
                          EmailService emailService) {
        this.clienteRepository = clienteRepository;
        this.objectMapper = objectMapper;
        this.usuarioService = usuarioService;
        this.convenioService = convenioService;
        this.agendamentoService = agendamentoService;
        this.emailService = emailService;
    }

    public PageDTO<ClienteCompletoDTO> list(Integer pagina, Integer tamanho) {
        Pageable solicitacaoPagina = PageRequest.of(pagina,tamanho);
        Page<ClienteCompletoDTO> cliente = clienteRepository.listarFull(solicitacaoPagina);
        List<ClienteCompletoDTO> clienteDTO = cliente.getContent().stream()
                .toList();

        return new PageDTO<>(cliente.getTotalElements(),
                cliente.getTotalPages(),
                pagina,
                tamanho,
                clienteDTO);
    }

    public ClienteCompletoDTO recuperarCliente() throws RegraDeNegocioException {
        ClienteEntity clienteEntity = clienteRepository.getClienteEntityByIdUsuario(usuarioService.getIdLoggedUser());
        return getById(clienteEntity.getIdCliente());
    }

    public ClienteCompletoDTO getById(Integer idCliente) throws RegraDeNegocioException {
        Optional<ClienteCompletoDTO> clienteRetornado = clienteRepository.getByIdPersonalizado(idCliente);
        if(clienteRetornado.isEmpty()){
            throw new RegraDeNegocioException("Usuário não encontrado.");
        }
        return clienteRetornado.get();
    }

    public ClienteCompletoDTO adicionar(ClienteCreateDTO cliente) throws RegraDeNegocioException{
        checarSeTemNumero(cliente.getNome());

        // Adicionando o Usuario com as informações recebidas no ClienteCreateDTO
        UsuarioEntity usuarioEntity = objectMapper.convertValue(cliente, UsuarioEntity.class);
        usuarioEntity.setIdCargo(3);
        ConvenioEntity convenioEntity = convenioService.getConvenio(cliente.getIdConvenio());

        usuarioService.validarUsuarioAdicionado(usuarioEntity);
        usuarioService.adicionar(usuarioEntity);

        // Adicionando o usuario que foi salvado no Cliente a salvar
        ClienteEntity clienteEntity = objectMapper.convertValue(cliente, ClienteEntity.class);
        clienteEntity.setUsuarioEntity(usuarioEntity);

        // Adicionando Convenio em Cliente a salvar
        clienteEntity.setConvenioEntity(convenioEntity);

        clienteRepository.save(clienteEntity);
        try{
            emailService.sendEmailUsuario(clienteEntity.getUsuarioEntity(), TipoEmail.USUARIO_CADASTRO);
        } catch (MessagingException | TemplateException | IOException e) {
            usuarioService.hardDelete(clienteEntity.getUsuarioEntity().getIdUsuario());
            throw new RegraDeNegocioException("Erro ao enviar o e-mail. Cadastro não realizado.");
        }

        return getById(clienteEntity.getIdCliente());
    }

    public ClienteCompletoDTO editar(Integer id, ClienteCreateDTO cliente) throws RegraDeNegocioException {
        checarSeTemNumero(cliente.getNome());

        ClienteEntity clienteEntity = getCliente(id);

        UsuarioCreateDTO usuarioCreateDTO = objectMapper.convertValue(cliente, UsuarioCreateDTO.class);
        usuarioCreateDTO.setIdCargo(3);

        usuarioService.validarUsuarioEditado(usuarioCreateDTO, clienteEntity.getIdUsuario());
        usuarioService.editar(usuarioCreateDTO, clienteEntity.getIdUsuario());


        ClienteEntity clienteEditado = clienteRepository.save(clienteEntity);

        return getById(clienteEditado.getIdCliente());
    }

    public void remover(Integer id) throws RegraDeNegocioException {
        usuarioService.remover(getCliente(id).getIdUsuario());
        agendamentoService.removerPorClienteDesativado(getCliente(id));
    }

    public ClienteEntity getCliente(Integer id) throws RegraDeNegocioException {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Cliente não existe!"));
    }

    public void checarSeTemNumero(String string) throws RegraDeNegocioException {
        if (string.matches(".*[0-9].*")) { // checa se tem número no nome
            throw new RegraDeNegocioException("O nome da especialidade não pode conter número");
        }
    }

}
