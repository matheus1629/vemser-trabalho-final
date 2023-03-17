package br.com.dbc.vemser.trabalhofinal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Usuario")
public class UsuarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USUARIO")
    @SequenceGenerator(name = "SEQ_USUARIO", sequenceName = "SEQ_USUARIO", allocationSize = 1)
    @Column(name = "id_medico")
    private Integer idUsuario;
    @Column(name = "cpf")
    private String cpf;
    @Column(name = "email")
    private String email;
    @Column(name = "nome")
    private String nome;
    @Column(name = "senha")
    private String senha;
    @Column(name = "cep")
    private String cep;
    @Column(name = "numero")
    private Integer numero;
    @Column(name = "contatos")
    private String contatos;
    @Column(name = "tipo")
    private TipoUsuario tipoUsuario;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "cliente", cascade = CascadeType.ALL)
    private ClienteEntity cliente;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "cliente", cascade = CascadeType.ALL)
    private MedicoEntity medico;

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}
