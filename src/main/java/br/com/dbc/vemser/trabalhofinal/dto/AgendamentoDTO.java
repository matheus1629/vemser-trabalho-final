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

    @Schema(description = "Id do agendamento")
    private Integer idAgendamento;
    @Schema(description = "Nome do cliente")
    private String nomeCliente;
    @Schema(description = "Nome do Médico")
    private String nomeMedico;
    @Schema(description = "Id do cliente", example = "1", required = true)
    private Integer idCliente;
    @Schema(description = "Id do médico", example = "2", required = true)
    private Integer idMedico;
    @Schema(description = "Tratamento a ser seguido pelo cliente", example = "Dipirona de 6 em 6 horas")
    private String tratamento;
    @Schema(description = "Exame(s) pedidos pelo médico", example = "Sangue e urina")
    private String exame;
    @Schema(description = "Data e horário da consulta", example = "25/08/2023 15:00", required = true)
    private LocalDateTime dataHorario;



}
