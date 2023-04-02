package br.com.dbc.vemser.trabalhofinal.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.time.LocalDateTime;

@Document(collection = "Log")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LogEntity {

    @Id
    private Integer idLog;
    private String idSolicitacao;
    private Integer idAgendamento;
    private Integer idUsuario;
    private LocalDateTime dataHora;
    private TipoLog tipoLog;
}
