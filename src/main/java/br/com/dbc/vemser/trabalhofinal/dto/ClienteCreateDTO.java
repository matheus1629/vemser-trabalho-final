package br.com.dbc.vemser.trabalhofinal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteCreateDTO {

    @NotNull
    @Schema(description = "Id do Usuario",example = "1", required = true)
    private Integer idUsuario;

    @Schema(description = "Id do Convenio",example = "1", required = true)
    private Integer idConvenio;

}
