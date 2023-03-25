package br.com.dbc.vemser.trabalhofinal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Cliente")
public class ClienteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CLIENTE")
    @SequenceGenerator(name = "SEQ_CLIENTE", sequenceName = "SEQ_CLIENTE", allocationSize = 1)
    @Column(name = "id_cliente")
    private Integer idCliente;
    @Column(name = "id_convenio", insertable= false, updatable=false)
    private Integer idConvenio;
    @Column(name = "id_usuario", insertable= false, updatable=false)
    private Integer idUsuario;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_convenio", referencedColumnName = "id_convenio")
    private ConvenioEntity convenioEntity;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    private UsuarioEntity usuarioEntity;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "clienteEntity", cascade = CascadeType.ALL)
    private Set<AgendamentoEntity> agendamentoEntities;

}
