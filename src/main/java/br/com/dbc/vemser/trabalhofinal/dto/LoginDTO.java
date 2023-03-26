package br.com.dbc.vemser.trabalhofinal.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LoginDTO {
    @NotNull
    @Schema(description = "E-mail", example = "nome@email.com", required = true)
    private String email;
    @NotNull
    @Schema(description = "Senha", example = "senha123", required = true)
    private String senha;
}