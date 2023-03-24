package br.com.dbc.vemser.trabalhofinal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteCompletoDTO {

    private Integer idCliente;
    private Integer idConvenio;
    private Integer idUsuario;
    private String cadastroOrgaoRegulador;
    private Double taxaAbatimento;
    private String cpf;
    private String email;
    private String nome;
    private Integer idCargo;
    private String contatos;
    private String cep;
    private Integer numero;
}
