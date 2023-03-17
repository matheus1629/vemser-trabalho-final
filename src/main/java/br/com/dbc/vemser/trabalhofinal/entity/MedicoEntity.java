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
@Entity(name = "Medico")
public class    MedicoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MEDICO")
    @SequenceGenerator(name = "SEQ_MEDICO", sequenceName = "SEQ_MEDICO", allocationSize = 1)
    @Column(name = "id_medico")
    private Integer idMedico;

    @Column(name = "id_usuario", insertable = false, updatable=false)
    private Integer idUsuario;
    @Column(name = "id_especialidade", insertable = false, updatable = false)
    private Integer idEspecialidade;
    @Column(name = "crm")
    private String crm;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_especialidade", referencedColumnName = "id_especialidade")
    private EspecialidadeEntity especialidadeEntity;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    private UsuarioEntity usuarioEntity;

}
