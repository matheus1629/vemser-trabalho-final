package br.com.dbc.vemser.trabalhofinal.dto.solicitacao;

import br.com.dbc.vemser.trabalhofinal.entity.StatusSolicitacao;
import lombok.Data;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

@Data
public class SolicitacaoDTO {
    private String idSoliciatacao;
    private Integer idMedico;
    private Integer idCliente;
    private String motivo;
    private LocalDateTime dataHora;
    private StatusSolicitacao statusSolicitacao;
}
