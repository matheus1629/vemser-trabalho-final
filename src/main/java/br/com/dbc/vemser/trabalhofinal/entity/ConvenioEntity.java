package br.com.dbc.vemser.trabalhofinal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Convenio")
public class ConvenioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CONVENIO")
    @SequenceGenerator(name = "SEQ_CONVENIO", sequenceName = "SEQ_CONVENIO", allocationSize = 1)
    @Column(name = "id_convenio")
    private Integer idConvenio;
    @Column(name = "Cadastro_Orgao_Regulador")
    private String cadastroOragaoRegulador;
    @Column(name = "taxa_abatimento")
    private Double taxaAbatimento;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "convenioEntity", cascade = CascadeType.MERGE)
    private Set<ClienteEntity> clienteEntities;
}
