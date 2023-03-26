package br.com.dbc.vemser.trabalhofinal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "RegistroTemporarioEntity")
@Table(name="reg_temp_usu")
public class RegistroTemporarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_reg_temp")
    @SequenceGenerator(name = "seq_reg_temp", sequenceName = "seq_reg_temp", allocationSize = 1)
    @Column(name = "id_registro")
    private Integer idRegistro;
    @Column(name = "data_geracao")
    private LocalDateTime dataGeracao;
    @Column(name = "codigo")
    private String codigo;
    @Column(name = "id_usuario", insertable = false, updatable = false)
    private Integer idUsuario;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    private UsuarioEntity usuarioEntity;

}
