package br.com.dbc.vemser.trabalhofinal.dto.cliente;

import br.com.dbc.vemser.trabalhofinal.dto.EnderecoDTO;
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
    private EnderecoDTO enderecoDTO;

    public ClienteCompletoDTO(Integer idCliente, Integer idConvenio, Integer idUsuario, String cadastroOrgaoRegulador, Double taxaAbatimento, String cpf, String email, String nome, String nomeCargo, String contatos, String cep, Integer numero) {
        this.idCliente = idCliente;
        this.idConvenio = idConvenio;
        this.idUsuario = idUsuario;
        this.cadastroOrgaoRegulador = cadastroOrgaoRegulador;
        this.taxaAbatimento = taxaAbatimento;
        this.cpf = cpf;
        this.email = email;
        this.nome = nome;
        this.nomeCargo = nomeCargo;
        this.contatos = contatos;
        this.cep = cep;
        this.numero = numero;
    }
}
