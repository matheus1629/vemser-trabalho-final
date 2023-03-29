package br.com.dbc.vemser.trabalhofinal.service;

import br.com.dbc.vemser.trabalhofinal.dto.PageDTO;
import br.com.dbc.vemser.trabalhofinal.dto.convenio.ConvenioCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.convenio.ConvenioDTO;
import br.com.dbc.vemser.trabalhofinal.entity.ConvenioEntity;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.repository.ClienteRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;
import org.junit.Before;
import java.util.List;


@RunWith(MockitoJUnitRunner.class)
public class ConvenioServiceTest {
    @InjectMocks
    private ConvenioService convenioService;
    private ObjectMapper objectMapper = new ObjectMapper();
    @Mock
    private ClienteRepository clienteRepository;

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(convenioService, "objectMapper", objectMapper);
    }

    public PageDTO<ConvenioDTO> list(Integer pagina, Integer tamanho) {
        Pageable solicitacaoPagina = PageRequest.of(pagina,tamanho);
        Page<ConvenioEntity> convenio = convenioRepository.findAll(solicitacaoPagina);
        List<ConvenioDTO> convenioDTO = convenio.getContent().stream()
                .map(x -> objectMapper.convertValue(x, ConvenioDTO.class))
                .toList();

        return new PageDTO<>(convenio.getTotalElements(),
                convenio.getTotalPages(),
                pagina,
                tamanho,
                convenioDTO);
    }

    public ConvenioDTO getById(Integer id) throws RegraDeNegocioException {
        return objectMapper.convertValue(getConvenio(id), ConvenioDTO.class);
    }

    public ConvenioDTO adicionar(ConvenioCreateDTO convenio) throws RegraDeNegocioException {
        ConvenioEntity convenioEntity = objectMapper.convertValue(convenio, ConvenioEntity.class);
        ConvenioEntity convenioEntityAdicionado = convenioRepository.save(convenioEntity);
        return objectMapper.convertValue(convenioEntityAdicionado, ConvenioDTO.class);
    }

    public void remover(Integer id) throws RegraDeNegocioException {
        getConvenio(id);
        convenioRepository.deleteById(id);
    }


    public ConvenioDTO editar(Integer id, ConvenioCreateDTO convenio) throws RegraDeNegocioException {
            ConvenioEntity convenioEntityRecuperado = getConvenio(id);
            convenioEntityRecuperado.setTaxaAbatimento(convenio.getTaxaAbatimento());
            convenioEntityRecuperado.setCadastroOrgaoRegulador(convenio.getCadastroOrgaoRegulador());

            convenioRepository.save(convenioEntityRecuperado);

            return objectMapper.convertValue(convenioEntityRecuperado, ConvenioDTO.class);
    }

    public ConvenioEntity getConvenio(Integer id) throws RegraDeNegocioException {
            return convenioRepository.findById(id).orElseThrow(()-> new RegraDeNegocioException("Convenio não existe"));
    }

}
