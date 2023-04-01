package br.com.dbc.vemser.trabalhofinal.entity;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
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
    private Integer idCliente;
    private String motivo;
    private LocalDateTime dataHora;
    private StatusSolicitacao statusSolicitacao;

}
