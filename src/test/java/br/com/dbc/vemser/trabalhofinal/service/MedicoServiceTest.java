package br.com.dbc.vemser.trabalhofinal.service;


import br.com.dbc.vemser.trabalhofinal.dto.convenio.ConvenioDTO;
import br.com.dbc.vemser.trabalhofinal.entity.ConvenioEntity;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MedicoServiceTest {
    @Spy
    @InjectMocks
    private MedicoService medicoService;
    @Test
    public void deveRetornarConvenioPorId() throws RegraDeNegocioException {
        // declaração de variaveis (SETUP)
        ConvenioEntity convenioMockadaDoBanco = getConvenioEntityMock();
        ConvenioDTO convenioMockadaDoBancoDTO = getConvenioEntityMockDTO();
        when(convenioRepository.findById(any())).thenReturn(Optional.of(convenioMockadaDoBanco));
//        Mockito.doReturn(convenioMockadaDoBanco).when(convenioService).getConvenio(anyInt());
        // ACT
        ConvenioDTO resultado = convenioService.getById(1);
        // Assert
        assertNotNull(resultado);
        assertEquals(convenioMockadaDoBancoDTO,resultado);
    }
}
