package br.com.dbc.vemser.trabalhofinal.dto.email;

import br.com.dbc.vemser.trabalhofinal.entity.TipoEmail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgendamentoEmailDTO {

    private Integer idAgendamento;
    private LocalDateTime dataHorario;
    private String nomeMedico;
    private String nomeCliente;
    private String emailCliente;
    private String emailMedico;
    private TipoEmail tipoEmail;

}