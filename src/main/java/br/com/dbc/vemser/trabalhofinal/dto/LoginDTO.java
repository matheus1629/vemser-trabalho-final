package br.com.dbc.vemser.trabalhofinal.dto;


import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LoginDTO {
    @NotNull
    private String email;
    @NotNull
    private String senha;
}