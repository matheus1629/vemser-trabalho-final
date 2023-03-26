package br.com.dbc.vemser.trabalhofinal.dto.medico;

import br.com.dbc.vemser.trabalhofinal.dto.EnderecoDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicoCompletoDTO {

    private Integer idMedico;
    private String crm;
    private Integer idEspecialidade;
    private Integer idUsuario;
    private Double valor;
    private String nomeEspecilidade;
    private String cpf;
    private String email;
    private String nome;
    private String nomeCargo;
    private String contatos;
    @JsonIgnore
    private String cep;
    private Integer numero;
    private EnderecoDTO enderecoDTO;

    public MedicoCompletoDTO(Integer idMedico, String crm, Integer idEspecialidade, Integer idUsuario, Double valor, String nomeEspecilidade, String cpf, String email, String nome, String nomeCargo, String contatos, String cep, Integer numero) {
        this.idMedico = idMedico;
        this.crm = crm;
        this.idEspecialidade = idEspecialidade;
        this.idUsuario = idUsuario;
        this.valor = valor;
        this.nomeEspecilidade = nomeEspecilidade;
        this.cpf = cpf;
        this.email = email;
        this.nome = nome;
        this.nomeCargo = nomeCargo;
        this.contatos = contatos;
        this.cep = cep;
        this.numero = numero;
    }
}
