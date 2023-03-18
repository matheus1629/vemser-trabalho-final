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
@Entity(name = "Especialidade")
public class EspecialidadeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CONVENIO")
    @SequenceGenerator(name = "SEQ_CONVENIO", sequenceName = "SEQ_CONVENIO", allocationSize = 1)
    @Column(name = "id_especialidade")
    private Integer idEspecialidade;
    @Column(name = "valor")
    private double valor;
    @Column(name = "nome")
    private String nomeEspecialidade;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "especialidadeEntity", cascade = CascadeType.MERGE)
    private Set<MedicoEntity> medicoEntities;

}
