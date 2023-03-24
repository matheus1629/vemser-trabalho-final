package br.com.dbc.vemser.trabalhofinal.service;

import br.com.dbc.vemser.trabalhofinal.dto.UsuarioDTO;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AdministrativoService {

    private final UsuarioService usuarioService;


    public UsuarioDTO reativarUsuario(Integer idUsuario) throws RegraDeNegocioException {
        return usuarioService.reativarUsuario(idUsuario);
    }
}
