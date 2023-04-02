package br.com.dbc.vemser.trabalhofinal.dto.solicitacao;

import br.com.dbc.vemser.trabalhofinal.entity.StatusSolicitacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SolicitacaoDTO {
    private String idSoliciatacao;
    private Integer idMedico;
    private Integer idCliente;
    private String motivo;
    private LocalDateTime dataHorario;
    private StatusSolicitacao statusSolicitacao;
}
