package br.com.dbc.vemser.trabalhofinal.dto.solicitacao;

import br.com.dbc.vemser.trabalhofinal.entity.StatusSolicitacao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SolicitacaoCreateDTO {
    @Schema(description = "Id do médico", example = "8")
    private Integer idMedico;
    @Schema(description = "Motivo da consulta", example = "dor no peito")
    private String motivo;
//    @Schema(description = "Especialidade do médico", example = "Cardiologista", required = true)
//    @NotBlank
//    private String especialidade;
    @Schema(description = "Data que deseja ser atendido", example = "15/04/2023 15:30", required = true)
    @NotNull
    @Future
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataHora;
    @JsonIgnore
    private Integer idCliente;
    @JsonIgnore
    private StatusSolicitacao statusSolicitacao;
}
