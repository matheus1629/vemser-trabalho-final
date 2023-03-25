package br.com.dbc.vemser.trabalhofinal.dto.cargo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CargoDTO {
    @Schema(description = "Id do Cargo",example = "1", required = true)
    private Integer idCargo;
}
