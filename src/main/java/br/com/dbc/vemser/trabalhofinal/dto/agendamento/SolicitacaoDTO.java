package br.com.dbc.vemser.trabalhofinal.dto.agendamento;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class SolicitacaoDTO  extends SolicitacaoCreateDTO{

    private String idSoliciatacao;
}
