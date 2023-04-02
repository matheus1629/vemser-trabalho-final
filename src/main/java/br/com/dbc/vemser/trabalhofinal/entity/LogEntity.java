package br.com.dbc.vemser.trabalhofinal.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Document(collection = "Log")
public class LogEntity {

    Integer idOperaedor;
    Integer idSolicitacao;
    LocalDateTime dataHora = LocalDateTime.now();
    StatusSolicitacao statusSolicitacao;
}
