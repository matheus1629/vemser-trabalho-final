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
    private String emailCliente;
    private LocalDateTime dataHorario;
    private StatusSolicitacao statusSolicitacao;
    private TipoEmail tipoEmail;


}
