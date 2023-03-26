package br.com.dbc.vemser.trabalhofinal.service;

import br.com.dbc.vemser.trabalhofinal.dto.cliente.ClienteCompletoDTO;
import br.com.dbc.vemser.trabalhofinal.dto.medico.MedicoCompletoDTO;
import br.com.dbc.vemser.trabalhofinal.dto.usuario.UsuarioCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.usuario.UsuarioDTO;
import br.com.dbc.vemser.trabalhofinal.dto.usuario.UsuarioUpdateDTO;
import br.com.dbc.vemser.trabalhofinal.entity.UsuarioEntity;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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


    public UsuarioDTO reativarUsuario(Integer idUsuario) throws RegraDeNegocioException {
        return usuarioService.reativarUsuario(idUsuario);
    }

    public List<UsuarioDTO> listar(){
        return usuarioRepository.findAll()
                .stream()
                .filter(usuarioEntity -> usuarioEntity.getIdCargo().equals(1))
                .map(adm -> objectMapper.convertValue(adm, UsuarioDTO.class))
                .collect(Collectors.toList());
    }

    public UsuarioDTO adicionar(UsuarioCreateDTO usuario) throws RegraDeNegocioException{

        UsuarioEntity usuarioEntity = objectMapper.convertValue(usuario, UsuarioEntity.class);
        usuarioEntity.setIdCargo(1);

        usuarioService.validarUsuarioAdicionado(usuarioEntity);
        usuarioService.adicionar(usuarioEntity);

        usuarioRepository.save(usuarioEntity);

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
}
