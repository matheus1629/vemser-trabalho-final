package br.com.dbc.vemser.trabalhofinal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Usuario")
public class UsuarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USUARIO")
    @SequenceGenerator(name = "SEQ_USUARIO", sequenceName = "SEQ_USUARIO", allocationSize = 1)
    @Column(name = "id_usuario")
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
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "usuarioEntity", cascade = CascadeType.ALL)
    private ClienteEntity clienteEntity;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "cliente", cascade = CascadeType.ALL)
    private MedicoEntity medicoEntity;

}
