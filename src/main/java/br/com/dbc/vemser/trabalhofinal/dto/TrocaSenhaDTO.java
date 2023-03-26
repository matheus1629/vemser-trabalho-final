package br.com.dbc.vemser.trabalhofinal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TrocaSenhaDTO {
    @NotNull
    @Schema(description = "Senha antiga para confirmação", example = "123", required = true)
    private String senhaAntiga;
    @NotNull
    @Schema(description = "Senha nova", example = "158", required = true)
    private String senhaNova;

}
