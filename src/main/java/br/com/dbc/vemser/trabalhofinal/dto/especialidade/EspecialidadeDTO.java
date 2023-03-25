package br.com.dbc.vemser.trabalhofinal.dto.especialidade;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EspecialidadeDTO extends EspecialidadeCreateDTO{

    @Schema(description = "Id da especialidade", example = "1", required = true)
    private Integer idEspecialidade;
}
