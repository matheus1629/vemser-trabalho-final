package br.com.dbc.vemser.trabalhofinal.dto.email;

import br.com.dbc.vemser.trabalhofinal.entity.TipoEmail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioEmailDTO {

    private String nomeUsuario;
    private String emailUsuario;
    private String codigoRecuperacao;
    private TipoEmail tipoEmail;

}
