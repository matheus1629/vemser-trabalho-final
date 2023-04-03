package br.com.dbc.vemser.trabalhofinal.dto.log;

import br.com.dbc.vemser.trabalhofinal.entity.TipoLog;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LogCreateDTO {

    private String idSolicitacao;
    private Integer idAgendamento;
    private Integer idUsuario;
    private LocalDateTime dataHora;
    private TipoLog tipoLog;

}
