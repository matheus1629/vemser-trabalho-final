package br.com.dbc.vemser.trabalhofinal.entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Medico")
public class MedicoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MEDICO")
    @SequenceGenerator(name = "SEQ_MEDICO", sequenceName = "SEQ_MEDICO", allocationSize = 1)
    @Column(name = "id_medico")
    private Integer idMedico;
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Column(name = "id_especialidade")
    private Integer idEspecialidade;
    @Column(name = "crm")
    private String crm;
}
