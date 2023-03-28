package br.com.dbc.vemser.trabalhofinal.dto.agendamento;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SolicitacaoCreateDTO {
    private Integer idMedico;
    private String motivo;
    @NotBlank
    private String especialidade;
    @NotBlank
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataHora;
}
