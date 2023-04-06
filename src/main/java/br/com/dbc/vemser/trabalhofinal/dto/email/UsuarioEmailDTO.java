package br.com.dbc.vemser.trabalhofinal.dto.email;

import br.com.dbc.vemser.trabalhofinal.entity.TipoEmail;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsuarioEmailDTO {
    private String nomeUsuario;
    private String emailUsuario;
    private Integer codigoRecuperacao;
    private TipoEmail tipoEmail;

}
