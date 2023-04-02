package br.com.dbc.vemser.trabalhofinal.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "Solicitacao")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SolicitacaoEntity {

    @Id
    private String idSoliciatacao;
    private Integer idMedico;
    private Integer idCliente;
    private String motivo;
    private LocalDateTime dataHora;
    private StatusSolicitacao statusSolicitacao;

}
