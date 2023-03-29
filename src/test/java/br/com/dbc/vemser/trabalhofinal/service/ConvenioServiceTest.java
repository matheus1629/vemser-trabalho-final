package br.com.dbc.vemser.trabalhofinal.service;

import br.com.dbc.vemser.trabalhofinal.dto.convenio.ConvenioCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.convenio.ConvenioDTO;
import br.com.dbc.vemser.trabalhofinal.entity.ConvenioEntity;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.repository.ClienteRepository;
import br.com.dbc.vemser.trabalhofinal.repository.ConvenioRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.junit.Before;

import javax.validation.constraints.NotNull;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class ConvenioServiceTest {
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

//    public ConvenioDTO getById(Integer id) throws RegraDeNegocioException {
//        return objectMapper.convertValue(getConvenio(id), ConvenioDTO.class);
//    }
//
//    public ConvenioDTO adicionar(ConvenioCreateDTO convenio) throws RegraDeNegocioException {
//        ConvenioEntity convenioEntity = objectMapper.convertValue(convenio, ConvenioEntity.class);
//        ConvenioEntity convenioEntityAdicionado = convenioRepository.save(convenioEntity);
//        return objectMapper.convertValue(convenioEntityAdicionado, ConvenioDTO.class);
//    }
//
//    public void remover(Integer id) throws RegraDeNegocioException {
//        getConvenio(id);
//        convenioRepository.deleteById(id);
//    }
//
//
//    public ConvenioDTO editar(Integer id, ConvenioCreateDTO convenio) throws RegraDeNegocioException {
//        ConvenioEntity convenioEntityRecuperado = getConvenio(id);
//        convenioEntityRecuperado.setTaxaAbatimento(convenio.getTaxaAbatimento());
//        convenioEntityRecuperado.setCadastroOrgaoRegulador(convenio.getCadastroOrgaoRegulador());
//
//        convenioRepository.save(convenioEntityRecuperado);
//
//        return objectMapper.convertValue(convenioEntityRecuperado, ConvenioDTO.class);
//    }
//
//    public ConvenioEntity getConvenio(Integer id) throws RegraDeNegocioException {
//        return convenioRepository.findById(id).orElseThrow(() -> new RegraDeNegocioException("Convenio não existe"));
//    }

    @NotNull
    private static ConvenioEntity getConvenioEntityMock() {
        ConvenioEntity convenioMockadaDoBanco = new ConvenioEntity();
        convenioMockadaDoBanco.setIdConvenio(1);
        convenioMockadaDoBanco.setCadastroOrgaoRegulador("Exemplo A");
        convenioMockadaDoBanco.setTaxaAbatimento(50.0);
        return convenioMockadaDoBanco;
    }
}