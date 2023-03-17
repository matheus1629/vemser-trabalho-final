package br.com.dbc.vemser.trabalhofinal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Null;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Cliente")
public class ClienteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CLIENTE")
    @SequenceGenerator(name = "SEQ_CLIENTE", sequenceName = "SEQ_CLIENTE", allocationSize = 1)
    @Column(name = "id_convenio")
    private Integer idConvenio;
    @Column(name = "id_cliente")
    private Integer idCliente;
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Null
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_convenio", referencedColumnName = "id_convenio")
    private ConvenioEntity convenioEntity;
}
