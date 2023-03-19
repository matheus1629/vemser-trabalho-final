package br.com.dbc.vemser.trabalhofinal.dto;

import br.com.dbc.vemser.trabalhofinal.entity.TipoUsuario;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
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
    private TipoUsuario tipoUsuario;
    private String contatos;
    private String cep;
    private Integer numero;
}
