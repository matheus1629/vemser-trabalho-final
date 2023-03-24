package br.com.dbc.vemser.trabalhofinal.service;

import br.com.dbc.vemser.trabalhofinal.dto.ClienteCompletoDTO;
import br.com.dbc.vemser.trabalhofinal.dto.ClienteCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.PageDTO;
import br.com.dbc.vemser.trabalhofinal.dto.UsuarioCreateDTO;
import br.com.dbc.vemser.trabalhofinal.entity.ClienteEntity;
import br.com.dbc.vemser.trabalhofinal.entity.UsuarioEntity;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.repository.ClienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final ObjectMapper objectMapper;
    private final UsuarioService usuarioService;
    private final ConvenioService convenioService;

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
    public ClienteCompletoDTO getById(Integer idCliente) throws RegraDeNegocioException {
        Optional<ClienteCompletoDTO> clienteRetornado = clienteRepository.getByIdPersonalizado(idCliente);
        if(clienteRetornado.isEmpty()){
            throw new RegraDeNegocioException("Usuário não encontrado.");
        }
        return clienteRetornado.get();
    }

    public ClienteCompletoDTO adicionar(ClienteCreateDTO cliente) throws RegraDeNegocioException {
        checarSeTemNumero(cliente.getNome());

        // Adicionando o Usuario com as informações recebidas no ClienteCreateDTO
        UsuarioEntity usuarioEntity = objectMapper.convertValue(cliente, UsuarioEntity.class);
        usuarioEntity.setIdCargo(3);
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
    }

    public ClienteEntity getCliente(Integer id) throws RegraDeNegocioException {
            return clienteRepository.findAll()
                    .stream()
                    .filter(clienteEntity -> clienteEntity.getIdCliente().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new RegraDeNegocioException("Cliente não encontrado!"));
    }

    public void checarSeTemNumero(String string) throws RegraDeNegocioException {
        if (string.matches(".*[0-9].*")) { // checa se tem número no nome
            throw new RegraDeNegocioException("O nome da especialidade não pode conter número");
        }
    }

}
