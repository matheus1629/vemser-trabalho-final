package br.com.dbc.vemser.trabalhofinal.service;

import br.com.dbc.vemser.trabalhofinal.dto.*;
import br.com.dbc.vemser.trabalhofinal.entity.ClienteEntity;
import br.com.dbc.vemser.trabalhofinal.entity.ConvenioEntity;
import br.com.dbc.vemser.trabalhofinal.entity.TipoUsuario;
import br.com.dbc.vemser.trabalhofinal.entity.UsuarioEntity;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.repository.ClienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Slf4j
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final ObjectMapper objectMapper;
    private final UsuarioService usuarioService;

    private final ConvenioService convenioService;

    public ClienteCompletoDTO adicionar(ClienteCreateDTO cliente) throws RegraDeNegocioException {
        ClienteEntity clienteEntity = objectMapper.convertValue(cliente, ClienteEntity.class);
        UsuarioEntity usuarioEntity = usuarioService.getUsuario(cliente.getIdUsuario());
        ConvenioEntity convenioEntity = convenioService.getConvenio(cliente.getIdConvenio());
        clienteEntity.setUsuarioEntity(usuarioEntity);
        clienteEntity.setConvenioEntity(convenioEntity);

        clienteRepository.save(clienteEntity);

        ClienteCompletoDTO clienteCompletoDTO = objectMapper.convertValue(clienteEntity, ClienteCompletoDTO.class);
        clienteCompletoDTO.setConvenio(objectMapper.convertValue(convenioEntity, ConvenioDTO.class));
        clienteCompletoDTO.setUsuario(objectMapper.convertValue(usuarioEntity, UsuarioDTO.class));

        return clienteCompletoDTO;
    }

    public void remover(Integer id) throws RegraDeNegocioException {
        getCliente(id);
        clienteRepository.deleteById(id);
    }

    public ClienteCompletoDTO editar(Integer id, ClienteCreateDTO cliente) throws RegraDeNegocioException {
        ClienteEntity clienteEntityRecuperado = getCliente(id);
        clienteEntityRecuperado.setIdConvenio(cliente.getIdConvenio());

        clienteRepository.save(clienteEntityRecuperado);

        return objectMapper.convertValue(clienteEntityRecuperado, ClienteCompletoDTO.class);
    }

    public List<ClienteDTO> listar() throws RegraDeNegocioException {
        return clienteRepository.findAll().stream().map(clienteEntity ->
        objectMapper.convertValue(clienteEntity, ClienteDTO.class)).toList();
    }

//    public List<ClienteCompletoDTO> listarFull() throws RegraDeNegocioException {
//            return clienteRepository.listarClienteDTOs();
//    }

//    public ClienteCompletoDTO getById(Integer idCliente) throws RegraDeNegocioException {
//        clienteRepository.getById(idCliente);
//    }
    public List<ClienteCompletoDTO> listarFull() throws RegraDeNegocioException {
//            return clienteRepository.listarClienteDTOs();
        return null;
    }

    public ClienteCompletoDTO getById(Integer idCliente) throws RegraDeNegocioException {
        clienteRepository.getById(idCliente);
        return null;
    }
    public ClienteEntity getCliente(Integer id) throws RegraDeNegocioException {
            return clienteRepository.findAll()
                    .stream()
                    .filter(clienteEntity -> clienteEntity.getIdCliente().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new RegraDeNegocioException("Cliente não encontrado!"));
    }


}
