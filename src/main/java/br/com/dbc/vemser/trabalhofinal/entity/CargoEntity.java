package br.com.dbc.vemser.trabalhofinal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Cargo")
public class CargoEntity implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CARGO")
    @SequenceGenerator(name = "SEQ_CARGO", sequenceName = "SEQ_CARGO", allocationSize = 1)
    @Column(name = "id_cargo")
    private Integer idCargo;
    @Column(name = "nome", insertable= false, updatable=false)
    private String nomeCargo;
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cargoEntity", cascade = CascadeType.MERGE)
    private Set<UsuarioEntity> usuarioEntities;

    @Override
    public String getAuthority() {
        return nomeCargo;
    }
}
