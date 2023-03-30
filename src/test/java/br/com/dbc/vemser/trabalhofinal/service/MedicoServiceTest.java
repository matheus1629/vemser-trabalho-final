package br.com.dbc.vemser.trabalhofinal.service;


import br.com.dbc.vemser.trabalhofinal.dto.convenio.ConvenioDTO;
import br.com.dbc.vemser.trabalhofinal.dto.medico.MedicoCompletoDTO;
import br.com.dbc.vemser.trabalhofinal.dto.medico.MedicoDTO;
import br.com.dbc.vemser.trabalhofinal.entity.ConvenioEntity;
import br.com.dbc.vemser.trabalhofinal.entity.MedicoEntity;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.repository.ConvenioRepository;
import br.com.dbc.vemser.trabalhofinal.repository.MedicoRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.validation.constraints.NotNull;
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
    private ObjectMapper objectMapper = new ObjectMapper();
    @Mock
    private MedicoRepository medicoRepository;

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(medicoService, "objectMapper", objectMapper);
    }
    @Test
    public void deveRetornarConvenioPorId() throws RegraDeNegocioException {
        // declaração de variaveis (SETUP)
        MedicoEntity convenioMockadaDoBanco = getConvenioEntityMock();
        MedicoDTO convenioMockadaDoBancoDTO = getConvenioEntityMockDTO();
        when(medicoRepository.findById(any())).thenReturn(Optional.of(convenioMockadaDoBanco));
//        Mockito.doReturn(convenioMockadaDoBanco).when(convenioService).getConvenio(anyInt());
        // ACT
        MedicoCompletoDTO resultado = medicoService.getById(1);
        // Assert
        assertNotNull(resultado);
        assertEquals(convenioMockadaDoBancoDTO,resultado);
    }

    @NotNull
    private static MedicoEntity getMedicoEntityMock() {
        MedicoEntity medicoMockadaDoBanco = new MedicoEntity();
        medicoMockadaDoBanco.setIdMedico(1);
        medicoMockadaDoBanco.set
        return medicoMockadaDoBanco;
    }

    @NotNull
    private static MedicoDTO getMedicoEntityMockDTO() {
        ConvenioDTO convenioMockadaDoBanco = new ConvenioDTO();
        convenioMockadaDoBanco.setIdConvenio(1);
        convenioMockadaDoBanco.setCadastroOrgaoRegulador("Exemplo A");
        convenioMockadaDoBanco.setTaxaAbatimento(50.0);
        return convenioMockadaDoBanco;
    }
}
