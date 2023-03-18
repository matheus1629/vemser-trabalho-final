package br.com.dbc.vemser.trabalhofinal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class EspecialidadeCreateDTO {

    @NotNull
    @Min(1)
    @Schema(description = "Valor da especialidade", example = "500.00", required = true)
    private double valor;
    @NotBlank
    @Schema(description = "Nome da especialdiade", example = "Cardiologista", required = true)
    private String nomeEspecialidade;
}
