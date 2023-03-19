package br.com.dbc.vemser.trabalhofinal.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgendamentoDTO {


    //@Schema(description = "Nome do cliente")
    //private String nome;
    private Integer idAgendamento;
    private Integer idCliente;
    private Integer idMedico;
    private String tratamento;
    private String exame;
    private LocalDateTime dataHorario;



}
