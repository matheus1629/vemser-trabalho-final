package br.com.dbc.vemser.trabalhofinal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class AgendamentoDadosDTO extends AgendamentoDTO{ // <todo> EXCLUIR!!!

    @Schema(description = "Nome do cliente")
    private String nomeCliente;
    @Schema(description = "Nome do Médico")
    private String nomeMedico;
}
