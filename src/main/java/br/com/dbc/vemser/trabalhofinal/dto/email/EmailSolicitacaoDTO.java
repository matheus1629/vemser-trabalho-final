package br.com.dbc.vemser.trabalhofinal.dto.email;

import br.com.dbc.vemser.trabalhofinal.entity.StatusSolicitacao;
import br.com.dbc.vemser.trabalhofinal.entity.TipoEmail;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EmailSolicitacaoDTO {
    private String idSoliciatacao;
    private String nomeMedico;
    private String nomeCliente;
    private LocalDateTime dataHorario;
    private StatusSolicitacao statusSolicitacao;
    private TipoEmail tipoEmail;

}
