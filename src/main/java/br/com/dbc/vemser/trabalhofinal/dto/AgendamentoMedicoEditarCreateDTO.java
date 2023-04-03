package br.com.dbc.vemser.trabalhofinal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgendamentoMedicoEditarCreateDTO {

    @Size(max = 40)
    @Schema(description = "Tratamento a ser seguido pelo cliente", example = "Dipirona de 6 em 6 horas")
    private String tratamento;

    @Size(max = 40)
    @Schema(description = "Exame(s) pedidos pelo médico", example = "Sangue e urina")
    private String exame;
}
