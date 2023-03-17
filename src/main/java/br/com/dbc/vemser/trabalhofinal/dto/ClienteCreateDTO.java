package br.com.dbc.vemser.trabalhofinal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ClienteCreateDTO {

    @NotNull
    @Schema(description = "Id do Usuario",example = "1", required = true)
    private Integer idUsuario;

    @Schema(description = "Id do Convenio",example = "1", required = true)
    private Integer idConvenio;

}
