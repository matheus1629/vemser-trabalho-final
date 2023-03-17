package br.com.dbc.vemser.trabalhofinal.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class MedicoCreateDTO {

    @NotBlank
    @Schema(description = "CRM", example = "CRM/SP 123456", required = true)
    private String crm;
    @NotNull
    @Schema(description = "Id da especialidade", example = "1", required = true)
    private Integer idEspecialidade;
    @NotNull
    @Schema(description = "Id do usuário", example = "1", required = true)
    private Integer idUsuario;


}
