package br.com.dbc.vemser.trabalhofinal.entity;

import lombok.*;

import javax.persistence.*;

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
    private String nome;

}
