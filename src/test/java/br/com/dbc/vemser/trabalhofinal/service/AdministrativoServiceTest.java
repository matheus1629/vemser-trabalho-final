package br.com.dbc.vemser.trabalhofinal.service;


import br.com.dbc.vemser.trabalhofinal.client.EnderecoClient;
import br.com.dbc.vemser.trabalhofinal.dto.cliente.ClienteCompletoDTO;
import br.com.dbc.vemser.trabalhofinal.dto.medico.MedicoCompletoDTO;
import br.com.dbc.vemser.trabalhofinal.dto.usuario.UsuarioDTO;
import br.com.dbc.vemser.trabalhofinal.entity.UsuarioEntity;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.repository.ClienteRepository;
import br.com.dbc.vemser.trabalhofinal.repository.MedicoRepository;
import br.com.dbc.vemser.trabalhofinal.repository.UsuarioRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AdministrativoServiceTest {
    @Spy
    @InjectMocks
    private AdministrativoService administrativoService;
    @Mock
    private UsuarioService usuarioService;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private EmailService emailService;
    @Mock
    private ClienteService clienteService;
    @Mock
    private MedicoService medicoService;
    @Mock
    private MedicoRepository medicoRepository;
    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private EnderecoClient enderecoClient;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(administrativoService, "objectMapper", objectMapper);
    }

    @Test
    public void deveReativarUsuario() throws RegraDeNegocioException {
        //SETUP
        when(usuarioService.reativarUsuario(any())).thenReturn(getUsuarioDTOMock());
        //acts
        administrativoService.reativarUsuario(1);
    }

    @Test
    public void testarGetClienteById() throws RegraDeNegocioException{
        //setup
        when(clienteService.getById(any())).thenReturn(getClienteCompletoDTOMock());
        //act
        administrativoService.getClienteById(getClienteCompletoDTOMock().getIdCliente());
    }

    @Test
    public void testarGetMedicoById() throws RegraDeNegocioException{
        //setup
        when(medicoService.getById(any())).thenReturn(getMedicoCompletoDTOMock());
        //act
        administrativoService.getMedicoById(getMedicoCompletoDTOMock().getIdMedico());
    }
    @Test
    public void testeRemoverCliente() throws RegraDeNegocioException{

    }

    @NotNull
    private static UsuarioDTO getUsuarioDTOMock(){
        UsuarioDTO usuarioDTOMockado = new UsuarioDTO();
        usuarioDTOMockado.setIdUsuario(1);
        usuarioDTOMockado.setIdCargo(1);
        usuarioDTOMockado.setCpf("14574198422");
        usuarioDTOMockado.setEmail("rogerio.santos@gmail.com");
        usuarioDTOMockado.setNome("Rogério Santos");
        usuarioDTOMockado.setCep("01010904");
        usuarioDTOMockado.setNumero(15);
        usuarioDTOMockado.setContatos("34999748512, 34999658741");
        usuarioDTOMockado.setIdCargo(1);
        return usuarioDTOMockado;
    }
    @NotNull
    private static ClienteCompletoDTO getClienteCompletoDTOMock() {
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

    @NotNull
    private static MedicoCompletoDTO getMedicoCompletoDTOMock() {
        MedicoCompletoDTO medicoMockadaDoBancoDTO = new MedicoCompletoDTO();
        medicoMockadaDoBancoDTO.setCep("12345678");
        medicoMockadaDoBancoDTO.setCrm("123456");
        medicoMockadaDoBancoDTO.setNome("Alan");
        medicoMockadaDoBancoDTO.setCpf("12345678910");
        medicoMockadaDoBancoDTO.setEmail("Alan@gmail.com");
        medicoMockadaDoBancoDTO.setContatos("12345678");
        medicoMockadaDoBancoDTO.setNumero(145);
        medicoMockadaDoBancoDTO.setIdEspecialidade(1);
        return medicoMockadaDoBancoDTO;
    }
}
