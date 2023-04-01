package br.com.dbc.vemser.trabalhofinal.dto.solicitacao;

import br.com.dbc.vemser.trabalhofinal.entity.StatusSolicitacao;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SolicitacaoPesquisaDTO {

    @Schema(description = "Id do médico", example = "8")
    private Integer idMedico;
    @Schema(description = "Id do cliente", example = "8")
    private Integer idCliente;

    @Schema(description = "Data início", example = "15/04/2023 15:30")
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataHoraInicio;

    @Schema(description = "Data fim", example = "15/04/2023 15:30")
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataHoraFim;

    @Schema(description = "Status  da solicitação", example = "APROVADO")
    private StatusSolicitacao statusSolicitacao;
}
