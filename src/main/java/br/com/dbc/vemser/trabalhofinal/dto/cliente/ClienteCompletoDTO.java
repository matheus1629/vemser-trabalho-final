package br.com.dbc.vemser.trabalhofinal.dto.cliente;

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
    private String nomeCargo;
    private String contatos;
    private String cep;
    private Integer numero;
}