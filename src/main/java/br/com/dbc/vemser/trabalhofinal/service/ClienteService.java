package br.com.dbc.vemser.trabalhofinal.service;

import br.com.dbc.vemser.trabalhofinal.dto.ClienteCompletoDTO;
import br.com.dbc.vemser.trabalhofinal.dto.ClienteCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.ClienteDTO;
import br.com.dbc.vemser.trabalhofinal.entity.ClienteEntity;
import br.com.dbc.vemser.trabalhofinal.entity.TipoUsuario;
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

    public ClienteCompletoDTO adicionar(ClienteCreateDTO cliente) throws RegraDeNegocioException {
        usuarioService.verificarIdUsuario(cliente.getIdUsuario(), TipoUsuario.CLIENTE);
        ClienteEntity clienteEntity = objectMapper.convertValue(cliente, ClienteEntity.class);
        clienteRepository.save(clienteEntity);

        return getById(clienteEntity.getIdCliente());

        //        ConvenioEntity convenioEntity = objectMapper.convertValue(convenio, ConvenioEntity.class);
        //        convenioRepository.save(convenioEntity);
        //        return objectMapper.convertValue(convenioEntity, ConvenioDTO.class);
    }

    public void remover(Integer id) throws RegraDeNegocioException {
//        try {
//            getCliente(id);
//            clienteRepository.remover(id);
//        } catch (BancoDeDadosException e) {
//            throw new RegraDeNegocioException("Erro no Banco!");
//        }
    }

    public ClienteCompletoDTO editar(Integer id, ClienteCreateDTO cliente) throws RegraDeNegocioException {
//        try {
//            ClienteEntity clienteEntity = objectMapper.convertValue(cliente, ClienteEntity.class);
//            if (!Objects.equals(getCliente(id).getIdUsuario(), clienteEntity.getIdUsuario())){
//                usuarioService.verificarIdUsuario(clienteEntity.getIdUsuario(), TipoUsuario.CLIENTE);
//            }
//            clienteRepository.editar(id, clienteEntity);
//            return getById(clienteEntity.getIdCliente());
//        } catch (BancoDeDadosException e) {
//            throw new RegraDeNegocioException("Erro no Banco!");
//        }
        return null;
    }

    public List<ClienteDTO> listar() throws RegraDeNegocioException {
//        try {
//            return clienteRepository.listar().stream().map(clienteEntity ->
//                    objectMapper.convertValue(clienteEntity, ClienteDTO.class)).toList();
//        } catch (BancoDeDadosException e) {
//            throw new RegraDeNegocioException("Erro no Banco!");
//        }
        return null;
    }

    public List<ClienteCompletoDTO> listarFull() throws RegraDeNegocioException {
//        try {
//            return clienteRepository.listarClienteDTOs();
//        } catch (BancoDeDadosException e) {
//            throw new RegraDeNegocioException("Erro no Banco!");
//        }
        return null;
    }

    public ClienteCompletoDTO getById(Integer idCliente) throws RegraDeNegocioException {
//        try {
//            ClienteCompletoDTO clienteCompletoDTO = clienteRepository.getClienteCompletoDTO(idCliente);
//            if (clienteCompletoDTO == null) {
//                throw new RegraDeNegocioException("Cliente não existe!");
//            }
//            return clienteCompletoDTO;
//        } catch (BancoDeDadosException e) {
//            throw new RuntimeException(e);
//        }
        return null;
    }

    public ClienteEntity getCliente(Integer id) throws RegraDeNegocioException {
//        try {
//            return clienteRepository.listar()
//                    .stream()
//                    .filter(clienteEntity -> clienteEntity.getIdCliente().equals(id))
//                    .findFirst()
//                    .orElseThrow(() -> new RegraDeNegocioException("Cliente não encontrado!"));
//        } catch (BancoDeDadosException e) {
//            throw new RegraDeNegocioException("Erro no Banco!");
//        }
        return null;
    }


}
