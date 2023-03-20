package br.com.dbc.vemser.trabalhofinal.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicoCreateDTO {

    @NotBlank
    @Schema(description = "CRM", example = "CRM/SP 123456", required = true)
    private String crm;
    @NotNull
    @Schema(description = "Id da especialidade", example = "1", required = true)
    private Integer idEspecialidade;
    @NotBlank
    @Size(min = 11, max = 11)
    @Schema(description = "CPF", example = "12345678911", required = true)
    private String cpf;
    @NotBlank
    @Size(max = 300)
    @Schema(description = "Email", example = "fulano.silva@gmail.com", required = true)
    private String email;
    @NotBlank
    @Size(max = 255)
    @Schema(description = "Nome", example = "Fulano da Silva", required = true)
    private String nome;
    @NotBlank
    @Size(max = 300)
    @Schema(description = "Senha", example = "123senha123", required = true)
    private String senha;
    @NotBlank
    @Size(max = 8)
    @Schema(description = "CEP", example = "12345678", required = true)
    private String cep;
    @NotNull
    @Min(value = 1)
    @Max(value = 999999999)
    @Schema(description = "Número do endereço", example = "12345678", required = true)
    private Integer numero;
    @NotNull
    @Schema(description = "Número de telefone ou celular", example = "849261850, 4599765234")
    private String contatos;



}
