package br.com.dbc.vemser.trabalhofinal.service;

import br.com.dbc.vemser.trabalhofinal.dto.*;
import br.com.dbc.vemser.trabalhofinal.entity.*;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.repository.ClienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final ObjectMapper objectMapper;
    private final UsuarioService usuarioService;
    private final ConvenioService convenioService;

    public List<ClienteDTO> listar() throws RegraDeNegocioException {
        return clienteRepository.findAll().stream().map(clienteEntity ->
                objectMapper.convertValue(clienteEntity, ClienteDTO.class))
                .toList();
    }

    public List<ClienteCompletoDTO> listarFull() throws RegraDeNegocioException {
        return clienteRepository.listarFull();
    }

    public ClienteCompletoDTO getById(Integer idCliente) throws RegraDeNegocioException {
        Optional<ClienteCompletoDTO> clienteEncontrado = clienteRepository.getByIdPersonalizado(idCliente);
        return clienteEncontrado.get();
    }

    public ClienteCompletoDTO adicionar(ClienteCreateDTO cliente) throws RegraDeNegocioException {

        // Adicionando o Usuario com as informações recebidas no ClienteCreateDTO
        UsuarioEntity usuarioEntity = objectMapper.convertValue(cliente, UsuarioEntity.class);
        usuarioEntity.setTipoUsuario(TipoUsuario.CLIENTE);
        usuarioService.validarUsuarioAdicionado(usuarioEntity);
        usuarioService.adicionar(usuarioEntity);

        // Adicionando o usuario que foi salvado no Cliente a salvar
        ClienteEntity clienteEntity = objectMapper.convertValue(cliente, ClienteEntity.class);
        clienteEntity.setUsuarioEntity(usuarioEntity);

        // Adicionando Convenio em Cliente a salvar
        clienteEntity.setConvenioEntity(convenioService.getConvenio(cliente.getIdConvenio()));

        clienteRepository.save(clienteEntity);

        return getById(clienteEntity.getIdCliente());
    }

    public ClienteCompletoDTO editar(Integer id, ClienteCreateDTO cliente) throws RegraDeNegocioException {

        ClienteEntity clienteEntity = getCliente(id);

//        UsuarioEntity usuarioDTO = usuarioService.getUsuario(clienteEntity.getUsuarioEntity().getIdUsuario());

        UsuarioCreateDTO usuarioDTO = objectMapper.convertValue(cliente, UsuarioCreateDTO.class);
        usuarioDTO.setTipoUsuario(TipoUsuario.CLIENTE);

        usuarioService.validarUsuarioEditado(usuarioDTO, clienteEntity.getIdUsuario());
        usuarioService.editar(usuarioDTO, clienteEntity.getIdUsuario());


        ClienteEntity clienteEditado = clienteRepository.save(clienteEntity);

        return getById(clienteEditado.getIdCliente());
    }

    public void remover(Integer id) throws RegraDeNegocioException {
        ClienteEntity clienteEntity = getCliente(id);
        usuarioService.remover(clienteEntity.getIdUsuario());
        clienteRepository.delete(clienteEntity);
    }

    public ClienteEntity getCliente(Integer id) throws RegraDeNegocioException {
            return clienteRepository.findAll()
                    .stream()
                    .filter(clienteEntity -> clienteEntity.getIdCliente().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new RegraDeNegocioException("Cliente não encontrado!"));
    }
}
