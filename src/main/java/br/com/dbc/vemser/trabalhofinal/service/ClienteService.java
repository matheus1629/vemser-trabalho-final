package br.com.dbc.vemser.trabalhofinal.service;

import br.com.dbc.vemser.trabalhofinal.dto.*;
import br.com.dbc.vemser.trabalhofinal.entity.*;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.repository.ClienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final AgendamentoService agendamentoService;


    public ClienteCompletoDTO adicionar(ClienteCreateDTO cliente) throws RegraDeNegocioException {
        ClienteEntity clienteEntity = objectMapper.convertValue(cliente, ClienteEntity.class);
        UsuarioEntity usuarioEntity = usuarioService.getUsuario(cliente.getIdUsuario());
        ConvenioEntity convenioEntity = convenioService.getConvenio(cliente.getIdConvenio());

        if (Objects.equals(usuarioEntity.getTipoUsuario().ordinal(), 2)) {
            throw new RegraDeNegocioException("Este usuário não é um cliente!");
        }

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

        ClienteEntity clienteEntity = objectMapper.convertValue(getCliente(id), ClienteEntity.class);
        UsuarioEntity usuarioEntity = usuarioService.getUsuario(cliente.getIdUsuario());
        ConvenioEntity convenioEntity = convenioService.getConvenio(cliente.getIdConvenio());

        if (Objects.equals(usuarioEntity.getTipoUsuario().ordinal(), 2)) {
            throw new RegraDeNegocioException("Este usuário não é um cliente!");
        }

        clienteEntity.setUsuarioEntity(usuarioEntity);
        clienteEntity.setConvenioEntity(convenioEntity);

        clienteRepository.save(clienteEntity);

        ClienteCompletoDTO clienteCompletoDTO = objectMapper.convertValue(clienteEntity, ClienteCompletoDTO.class);
        clienteCompletoDTO.setConvenio(objectMapper.convertValue(convenioEntity, ConvenioDTO.class));
        clienteCompletoDTO.setUsuario(objectMapper.convertValue(usuarioEntity, UsuarioDTO.class));

        return clienteCompletoDTO;

    }

    public List<ClienteDTO> listar() throws RegraDeNegocioException {
        return clienteRepository.findAll().stream().map(clienteEntity ->
        objectMapper.convertValue(clienteEntity, ClienteDTO.class)).toList();
    }

    public ClientePersonalizadoDTO getById(Integer idCliente) throws RegraDeNegocioException {
        ClientePersonalizadoDTO clientePersonalizadoDTO = clienteRepository.clientePersonalizado(idCliente);
        List<AgendamentoDTO> listaAgendamento = agendamentoService.getAllByIdClienteOrMedico(idCliente);

        clientePersonalizadoDTO.setAgendamentoDTOList(listaAgendamento);

        return clientePersonalizadoDTO;
    }
    public List<ClienteCompletoDTO> listarFull() throws RegraDeNegocioException {
//            return clienteRepository.listarClienteDTOs();
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
