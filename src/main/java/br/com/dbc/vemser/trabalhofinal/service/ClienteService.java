package br.com.dbc.vemser.trabalhofinal.service;

import br.com.dbc.vemser.trabalhofinal.client.EnderecoClient;
import br.com.dbc.vemser.trabalhofinal.dto.*;
import br.com.dbc.vemser.trabalhofinal.dto.agendamento.AgendamentoClienteRelatorioDTO;
import br.com.dbc.vemser.trabalhofinal.dto.agendamento.AgendamentoDTO;
import br.com.dbc.vemser.trabalhofinal.dto.agendamento.AgendamentoListaDTO;
import br.com.dbc.vemser.trabalhofinal.dto.cliente.ClienteCompletoDTO;
import br.com.dbc.vemser.trabalhofinal.dto.cliente.ClienteCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.cliente.ClienteUpdateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.usuario.UsuarioCreateDTO;
import br.com.dbc.vemser.trabalhofinal.entity.AgendamentoEntity;
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
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final ObjectMapper objectMapper;
    private final UsuarioService usuarioService;
    private final ConvenioService convenioService;
    private final AgendamentoService agendamentoService;
    private final EmailService emailService;
    private final EnderecoClient enderecoClient;

    public ClienteService(ClienteRepository clienteRepository, ObjectMapper objectMapper, UsuarioService usuarioService, ConvenioService convenioService, @Lazy AgendamentoService agendamentoService, EmailService emailService, EnderecoClient enderecoClient) {
        this.clienteRepository = clienteRepository;
        this.objectMapper = objectMapper;
        this.usuarioService = usuarioService;
        this.convenioService = convenioService;
        this.agendamentoService = agendamentoService;
        this.emailService = emailService;
        this.enderecoClient = enderecoClient;
    }



    public ClienteCompletoDTO recuperarCliente() throws RegraDeNegocioException {
        ClienteEntity clienteEntity = clienteRepository.getClienteEntityByIdUsuario(usuarioService.getIdLoggedUser());
        return getById(clienteEntity.getIdCliente());
    }

    public ClienteCompletoDTO getById(Integer idCliente) throws RegraDeNegocioException {
        Optional<ClienteCompletoDTO> clienteRetornado = clienteRepository.getByIdPersonalizado(idCliente);
        if (clienteRetornado.isEmpty()) {
            throw new RegraDeNegocioException("Usuário não encontrado.");
        }
        clienteRetornado.get().setEnderecoDTO(enderecoClient.getEndereco(clienteRetornado.get().getCep()));
        return clienteRetornado.get();
    }

    public ClienteEntity getCliente(Integer id) throws RegraDeNegocioException {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Cliente não existe!"));
    }

    public AgendamentoListaDTO getClienteAgentamentos() throws RegraDeNegocioException {
        ClienteCompletoDTO clienteCompletoDTO = recuperarCliente();
        AgendamentoClienteRelatorioDTO agendamentoClienteRelatorioDTO = agendamentoService.getRelatorioClienteById(clienteCompletoDTO.getIdCliente());

        return objectMapper.convertValue(agendamentoClienteRelatorioDTO, AgendamentoListaDTO.class);
    }

    public ClienteCompletoDTO adicionar(ClienteCreateDTO cliente) throws RegraDeNegocioException {
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
            emailService.sendEmailUsuario(clienteEntity.getUsuarioEntity(), TipoEmail.USUARIO_CADASTRO, null);
        } catch (MessagingException | TemplateException | IOException e) {
            usuarioService.hardDelete(clienteEntity.getUsuarioEntity().getIdUsuario());
            throw new RegraDeNegocioException("Erro ao enviar o e-mail. Cadastro não realizado.");
        }

        return getById(clienteEntity.getIdCliente());
    }

    public ClienteCompletoDTO editar(ClienteUpdateDTO cliente) throws RegraDeNegocioException {
        ClienteEntity clienteEntity = objectMapper.convertValue(recuperarCliente(), ClienteEntity.class);

        clienteEntity.setConvenioEntity(convenioService.getConvenio(cliente.getIdConvenio()));
        clienteEntity.setUsuarioEntity(usuarioService.getUsuario(clienteEntity.getIdUsuario()));

        checarSeTemNumero(cliente.getNome());

        UsuarioCreateDTO usuarioCreateDTO = objectMapper.convertValue(cliente, UsuarioCreateDTO.class);

        usuarioService.validarUsuarioEditado(usuarioCreateDTO, clienteEntity.getIdUsuario());
        usuarioService.editar(usuarioCreateDTO, clienteEntity.getIdUsuario());

        ClienteEntity clienteEditado = clienteRepository.save(clienteEntity);

        return getById(clienteEditado.getIdCliente());
    }

    @Transactional
    public void remover(Integer id) throws RegraDeNegocioException {
        ClienteEntity clienteEntity = getCliente(id);
        usuarioService.remover(clienteEntity.getIdUsuario());
        agendamentoService.removerPorClienteDesativado(clienteEntity);
    }

    public void checarSeTemNumero(String string) throws RegraDeNegocioException {
        if (string.matches(".*[0-9].*")) { // checa se tem número no nome
            throw new RegraDeNegocioException("O nome da especialidade não pode conter número");
        }
    }


}
