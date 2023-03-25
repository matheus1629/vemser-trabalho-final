package br.com.dbc.vemser.trabalhofinal.dto.convenio;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConvenioDTO extends ConvenioCreateDTO{

    @Schema(description = "Id do Convenio", example = "1", required = true)
    private Integer idConvenio;

}
