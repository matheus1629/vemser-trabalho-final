package br.com.dbc.vemser.trabalhofinal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgendamentoMedicoRelatorioDTO extends MedicoCompletoDTO{

    private Integer idMedico;
    private String crm;
    private Integer idEspecialidade;
    private Integer idUsuario;
    private Double valor;
    private String nomeEspecilidade;
    private String cpf;
    private String email;
    private String nome;
    private Integer idCargo;
    private String contatos;
    private String cep;
    private Integer numero;

    private List<AgendamentoDTO> agendamentoDTOList;

    public AgendamentoMedicoRelatorioDTO(Integer idMedico, String crm, Integer idEspecialidade, Integer idUsuario, Double valor, String nomeEspecilidade, String cpf, String email, String nome, Integer idCargo, String contatos, String cep, Integer numero) {
        this.idMedico = idMedico;
        this.crm = crm;
        this.idEspecialidade = idEspecialidade;
        this.idUsuario = idUsuario;
        this.valor = valor;
        this.nomeEspecilidade = nomeEspecilidade;
        this.cpf = cpf;
        this.email = email;
        this.nome = nome;
        this.idCargo = idCargo;
        this.contatos = contatos;
        this.cep = cep;
        this.numero = numero;
    }
}
