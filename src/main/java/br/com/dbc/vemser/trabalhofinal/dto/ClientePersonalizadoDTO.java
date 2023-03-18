package br.com.dbc.vemser.trabalhofinal.dto;

import br.com.dbc.vemser.trabalhofinal.entity.TipoUsuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientePersonalizadoDTO {

    private Integer idCliente;
    private Integer idConvenio;
    private Integer idUsuario;
    private String cadastroOrgaoRegulador;
    private Double taxaAbatimento;
    private String cpf;
    private String email;
    private String nome;
    private TipoUsuario tipoUsuario;
    private String contatos;
    private String cep;
    private Integer numero;
    private Set<AgendamentoDTO> agendamentoDTOList;

    public ClientePersonalizadoDTO(Integer idCliente, Integer idConvenio, Integer idUsuario, String cadastroOrgaoRegulador, Double taxaAbatimento, String cpf, String email, String nome, TipoUsuario tipoUsuario, String contatos, String cep, Integer numero) {
        this.idCliente = idCliente;
        this.idConvenio = idConvenio;
        this.idUsuario = idUsuario;
        this.cadastroOrgaoRegulador = cadastroOrgaoRegulador;
        this.taxaAbatimento = taxaAbatimento;
        this.cpf = cpf;
        this.email = email;
        this.nome = nome;
        this.tipoUsuario = tipoUsuario;
        this.contatos = contatos;
        this.cep = cep;
        this.numero = numero;

    }
}
