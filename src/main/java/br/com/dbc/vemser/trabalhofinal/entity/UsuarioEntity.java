package br.com.dbc.vemser.trabalhofinal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Usuario")
public class UsuarioEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USUARIO")
    @SequenceGenerator(name = "SEQ_USUARIO", sequenceName = "SEQ_USUARIO", allocationSize = 1)
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Column(name = "id_cargo")
    private Integer idCargo;
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

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "usuarioEntity", cascade = CascadeType.ALL)
    private ClienteEntity clienteEntity;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "usuarioEntity", cascade = CascadeType.ALL)
    private MedicoEntity medicoEntity;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cargo", referencedColumnName = "id_cargo",insertable = false, updatable=false)
    private CargoEntity cargoEntity;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<CargoEntity> cargoEntity = null;
        cargoEntity.add(this.cargoEntity); // PODE DAR ERRO!!!
        return cargoEntity;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
