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
        getCliente(idCliente);
        return clienteRepository.getByIdPersonalizado(idCliente);
    }

    public ClienteCompletoDTO adicionar(ClienteCreateDTO cliente) throws RegraDeNegocioException {
        ClienteEntity clienteEntity = objectMapper.convertValue(cliente, ClienteEntity.class);
        UsuarioEntity usuarioEntity = usuarioService.getUsuario(cliente.getIdUsuario());
        ConvenioEntity convenioEntity = convenioService.getConvenio(cliente.getIdConvenio());

        if (!Objects.equals(usuarioEntity.getTipoUsuario().ordinal(), 2)) {
            throw new RegraDeNegocioException("Este usuário não é um cliente!");
        }

        clienteEntity.setUsuarioEntity(usuarioEntity);
        clienteEntity.setConvenioEntity(convenioEntity);

        ClienteEntity clienteAdicionado = clienteRepository.save(clienteEntity);

        return getById(clienteAdicionado.getIdCliente());
    }

    public ClienteCompletoDTO editar(Integer id, ClienteCreateDTO cliente) throws RegraDeNegocioException {

        ClienteEntity clienteEntity = objectMapper.convertValue(getCliente(id), ClienteEntity.class);
        UsuarioEntity usuarioEntity = usuarioService.getUsuario(cliente.getIdUsuario());
        ConvenioEntity convenioEntity = convenioService.getConvenio(cliente.getIdConvenio());

        if (!Objects.equals(usuarioEntity.getTipoUsuario().ordinal(), 2)) {
            throw new RegraDeNegocioException("Este usuário não é um cliente!");
        }

        clienteEntity.setUsuarioEntity(usuarioEntity);
        clienteEntity.setConvenioEntity(convenioEntity);

        ClienteEntity clienteEditado = clienteRepository.save(clienteEntity);

        return getById(clienteEditado.getIdCliente());

    }

    public void remover(Integer id) throws RegraDeNegocioException {
        clienteRepository.delete(getCliente(id));
    }

    public ClienteEntity getCliente(Integer id) throws RegraDeNegocioException {
            return clienteRepository.findAll()
                    .stream()
                    .filter(clienteEntity -> clienteEntity.getIdCliente().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new RegraDeNegocioException("Cliente não encontrado!"));
    }
}
