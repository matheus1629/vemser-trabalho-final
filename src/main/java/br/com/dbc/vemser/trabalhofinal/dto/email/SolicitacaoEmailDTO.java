package br.com.dbc.vemser.trabalhofinal.dto.email;

import br.com.dbc.vemser.trabalhofinal.entity.StatusSolicitacao;
import br.com.dbc.vemser.trabalhofinal.entity.TipoEmail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SolicitacaoEmailDTO {
    private String idSoliciatacao;
    private String nomeMedico;
    private String nomeCliente;
    private String email;
    private LocalDateTime dataHorario;
    private StatusSolicitacao statusSolicitacao;
    private TipoEmail tipoEmail;

    public SolicitacaoEmailDTO(String idSoliciatacao, String nomeMedico, String nomeCliente, LocalDateTime dataHorario) {
        this.idSoliciatacao = idSoliciatacao;
        this.nomeMedico = nomeMedico;
        this.nomeCliente = nomeCliente;
        this.dataHorario = dataHorario;
    }
}
