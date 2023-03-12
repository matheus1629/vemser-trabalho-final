package br.com.dbc.vemser.trabalhofinal.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Usuario {
    private Integer idUsuario;
    private String cpf;
    private String email;
    private String nome;
    private String senha;
    private String cep;
    private Integer numero;
    private String[] contatos;
    private TipoUsuario tipoUsuario;

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}
