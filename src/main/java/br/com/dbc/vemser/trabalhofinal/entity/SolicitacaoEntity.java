package br.com.dbc.vemser.trabalhofinal.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "Solicitacao")
@Getter
@Setter
public class SolicitacaoEntity {

    @Id
    private String idSoliciatacao;
    private Integer idMedico;
    private String motivo;
    private String especialidade;
    private LocalDateTime dataHora;
    private Integer idCliente;

}
