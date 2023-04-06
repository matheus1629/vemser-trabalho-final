package br.com.dbc.vemser.trabalhofinal.service;

import br.com.dbc.vemser.trabalhofinal.dto.cliente.ClienteCompletoDTO;
import br.com.dbc.vemser.trabalhofinal.dto.solicitacao.SolicitacaoCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.solicitacao.SolicitacaoDTO;
import br.com.dbc.vemser.trabalhofinal.entity.SolicitacaoEntity;
import br.com.dbc.vemser.trabalhofinal.entity.StatusSolicitacao;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.repository.SolicitacaoReposiroty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import freemarker.template.TemplateException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SolicitacaoServiceTest {

    @Spy
    @InjectMocks
    private SolicitacaoService solicitacaoService;
    @Mock
    private SolicitacaoReposiroty solicitacaoReposiroty;
    @Mock
    private ClienteService clienteService;
    @Mock
    private EmailService emailService;
    @Mock
    private UsuarioService usuarioService;
    private ObjectMapper objectMapper = new ObjectMapper();
    @Mock
    private MedicoService medicoService;
    @Mock
    private LogService logService;

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(solicitacaoService, "objectMapper", objectMapper);
    }

    @Test
    public void deveCriarSolicitacaoSucesso() throws RegraDeNegocioException, JsonProcessingException {
        //setup
        SolicitacaoCreateDTO solicitacaoCreateDTO = getSolicitacaoCreateDTO();

        Mockito.when(clienteService.recuperarCliente()).thenReturn(getClienteCompletoDTOMock());
        //act
        SolicitacaoDTO solicitacaoDTO = solicitacaoService.create(solicitacaoCreateDTO);
        //assert
        Assert.assertNotNull(solicitacaoDTO);
    }

    @Test
    public void deveReprovarSolicitacao(){
        //setup
        SolicitacaoEntity solicitacaoEntity = getSolicitacaoEntity();
        //act
        solicitacaoService.reprovarSolicitacao(solicitacaoEntity);
        //asserts
        verify(solicitacaoService, times(1)).reprovarSolicitacao(solicitacaoEntity);
    }

    @Test
    public void deveAprovarSolicitacao(){
        //setup
        SolicitacaoEntity solicitacaoEntity = getSolicitacaoEntity();

        when(solicitacaoReposiroty.save(any())).thenReturn(solicitacaoEntity);
        //act
        solicitacaoService.aprovarSolicitacao(solicitacaoEntity);
        //asserts
        verify(solicitacaoService, times(1)).aprovarSolicitacao(solicitacaoEntity);
    }

    @Test
    public void deveRetornarSoliciatacaoPorID() throws RegraDeNegocioException{
        //setup
        SolicitacaoEntity solicitacaoEntity = getSolicitacaoEntity();
        when(solicitacaoReposiroty.findById(anyString())).thenReturn(Optional.of(solicitacaoEntity));
        //act
        solicitacaoService.getSolicitacao("1");
        //asserts
        verify(solicitacaoService, times(1)).getSolicitacao("1");

    }

    @Test
    public void deveRetornarSolicitacaoes() {
        //act
        List<SolicitacaoDTO> solicitacaoDTOList = solicitacaoService.findSolicitacoes(1,1,null,null,StatusSolicitacao.PENDENTE);
        //assert
        Assert.assertNotNull(solicitacaoDTOList);
    }


    static ClienteCompletoDTO getClienteCompletoDTOMock() {
        ClienteCompletoDTO clienteCompletoDTO = new ClienteCompletoDTO();
        clienteCompletoDTO.setIdCliente(1);
        clienteCompletoDTO.setIdUsuario(1);
        clienteCompletoDTO.setIdConvenio(1);
        clienteCompletoDTO.setCadastroOrgaoRegulador("123456");
        clienteCompletoDTO.setTaxaAbatimento(20.0);
        clienteCompletoDTO.setCpf("12345678910");
        clienteCompletoDTO.setCep("12345678");
        clienteCompletoDTO.setNumero(523);
        clienteCompletoDTO.setContatos("12345678");
        clienteCompletoDTO.setNome("Carlos");
        clienteCompletoDTO.setEmail("Carlos@gmail.com");
        clienteCompletoDTO.setNomeCargo("ROLE_CLIENTE");
        return clienteCompletoDTO;
    }

    public static SolicitacaoCreateDTO getSolicitacaoCreateDTO(){
        SolicitacaoCreateDTO solicitacaoCreateDTO = new SolicitacaoCreateDTO();
        solicitacaoCreateDTO.setIdMedico(1);
        solicitacaoCreateDTO.setMotivo("teste");
        solicitacaoCreateDTO.setDataHorario(LocalDateTime.now());
        solicitacaoCreateDTO.setIdCliente(2);
        solicitacaoCreateDTO.setStatusSolicitacao(StatusSolicitacao.APROVADA);
        return solicitacaoCreateDTO;
    }

    public static SolicitacaoEntity getSolicitacaoEntity(){
        SolicitacaoEntity solicitacaoEntity = new SolicitacaoEntity();
        solicitacaoEntity.setIdSoliciatacao("1");
        solicitacaoEntity.setIdMedico(1);
        solicitacaoEntity.setMotivo("teste");
        solicitacaoEntity.setDataHorario(LocalDateTime.now());
        solicitacaoEntity.setIdCliente(2);
        solicitacaoEntity.setStatusSolicitacao(StatusSolicitacao.APROVADA);
        return solicitacaoEntity;
    }
}
