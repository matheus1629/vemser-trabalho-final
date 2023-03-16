package br.com.dbc.vemser.trabalhofinal.entity;

import lombok.*;

import javax.persistence.*;

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

}
