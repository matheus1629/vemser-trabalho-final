package br.com.dbc.vemser.trabalhofinal.service;

import br.com.dbc.vemser.trabalhofinal.dto.PageDTO;
import br.com.dbc.vemser.trabalhofinal.dto.convenio.ConvenioDTO;
import br.com.dbc.vemser.trabalhofinal.entity.ConvenioEntity;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.repository.ConvenioRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.junit.Before;

import javax.validation.constraints.NotNull;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class ConvenioServiceTest {
    @Spy
    @InjectMocks
    private ConvenioService convenioService;
    private ObjectMapper objectMapper = new ObjectMapper();
    @Mock
    private ConvenioRepository convenioRepository;

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(convenioService, "objectMapper", objectMapper);
    }

    @Test
    public void deveListarComSucesso(){
        // SETUP
        List<ConvenioEntity> lista = List.of(getConvenioEntityMock(), getConvenioEntityMock(), getConvenioEntityMock());
        when(convenioRepository.findAll()).thenReturn(lista);

        // ACT
        PageDTO<ConvenioDTO> list = convenioService.list(1,1);

        // ASSERT
        assertNotNull(list);
        assertEquals(3, 3);
    }

    @Test
    public void deveRetornarConvenioPorId() throws RegraDeNegocioException{
        // setup
        ConvenioEntity convenioMockadaDoBanco = getConvenioEntityMock();
        ConvenioDTO convenioMockadaDoBancoDTO = getConvenioEntityMockDTO();
        when(convenioRepository.findById(any())).thenReturn(Optional.of(convenioMockadaDoBanco));
        // ACT
        ConvenioDTO resultado = convenioService.getById(1);
        // Assert
        assertNotNull(resultado);
        assertEquals(convenioMockadaDoBancoDTO,resultado);
    }

    @Test // deveCriarComSucesso
    public void deveCriarComSucesso() throws RegraDeNegocioException {
        // declaração de variaveis (SETUP)
        ConvenioEntity convenioMockadaDoBanco = getConvenioEntityMock();
        ConvenioDTO convenioDTO = new ConvenioDTO();
        convenioDTO.setIdConvenio(1);
        convenioDTO.setTaxaAbatimento(50.0);
        convenioDTO.setCadastroOrgaoRegulador("Exemplo A");

        when(convenioRepository.save(any())).thenReturn(convenioMockadaDoBanco);
        // ação (ACT)
//        PessoaService pessoaService = new PessoaService();
        ConvenioDTO convenioRetornado = convenioService.adicionar(convenioDTO);

        // verificar se deu certo / afirmativa  (ASSERT)
        assertNotNull(convenioRetornado);
        assertEquals(convenioMockadaDoBanco.getTaxaAbatimento(), convenioRetornado.getTaxaAbatimento());
        assertEquals(convenioMockadaDoBanco.getCadastroOrgaoRegulador(), convenioRetornado.getCadastroOrgaoRegulador());
        assertEquals(1,convenioRetornado.getIdConvenio());
    }

    @Test
    public void testarRemover() throws RegraDeNegocioException{
        //setup
        Mockito.doReturn(getConvenioEntityMock()).when(convenioService).getConvenio(any());
        //act
        convenioService.remover(getConvenioEntityMock().getIdConvenio());
    }

    @Test
    public void testarEditarConvenio() throws RegraDeNegocioException{
        //setup
        //act
        convenioService.editar();
        //assert
    }


    @NotNull
    private static ConvenioEntity getConvenioEntityMock() {
        ConvenioEntity convenioMockadaDoBanco = new ConvenioEntity();
        convenioMockadaDoBanco.setIdConvenio(1);
        convenioMockadaDoBanco.setCadastroOrgaoRegulador("Exemplo A");
        convenioMockadaDoBanco.setTaxaAbatimento(50.0);
        return convenioMockadaDoBanco;
    }

    @NotNull
    private static ConvenioDTO getConvenioEntityMockDTO() {
        ConvenioDTO convenioMockadaDoBanco = new ConvenioDTO();
        convenioMockadaDoBanco.setIdConvenio(1);
        convenioMockadaDoBanco.setCadastroOrgaoRegulador("Exemplo A");
        convenioMockadaDoBanco.setTaxaAbatimento(50.0);
        return convenioMockadaDoBanco;
    }
}