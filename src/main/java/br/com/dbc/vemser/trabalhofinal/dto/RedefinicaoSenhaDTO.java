package br.com.dbc.vemser.trabalhofinal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RedefinicaoSenhaDTO {

    @NotNull
    @Schema(description = "E-mail", example = "nome@email.com", required = true)
    private String email;
    @NotNull
    @Schema(description = "Cógido recebdido por e-mail", example = "748152", required = true)
    private Integer codigoConfirmacao;
    @NotNull
    @Schema(description = "Senha nova", example = "158", required = true)
    private String senhaNova;

}
