package br.com.dbc.vemser.trabalhofinal.dto;

import br.com.dbc.vemser.trabalhofinal.entity.TipoUsuario;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class MedicoPersonalizadoDTO {
    private Integer idMedico;
    private String crm;
    private Integer idEspecialidade;
    private Integer idUsuario;
    private Double valor;
    private String nomeEspecilidade;
    private String cpf;
    private String email;
    private String nome;
    private TipoUsuario tipoUsuario;
    private String contatos;
    private String cep;
    private Integer numero;
    private Set<AgendamentoDTO> agendamentoDTOList;

    public MedicoPersonalizadoDTO(Integer idMedico, String crm, Integer idEspecialidade, Integer idUsuario, Double valor, String nomeEspecilidade, String cpf, String email, String nome, TipoUsuario tipoUsuario, String contatos, String cep, Integer numero) {
        this.idMedico = idMedico;
        this.crm = crm;
        this.idEspecialidade = idEspecialidade;
        this.idUsuario = idUsuario;
        this.valor = valor;
        this.nomeEspecilidade = nomeEspecilidade;
        this.cpf = cpf;
        this.email = email;
        this.nome = nome;
        this.tipoUsuario = tipoUsuario;
        this.contatos = contatos;
        this.cep = cep;
        this.numero = numero;
    }
}
