package br.com.dbc.vemser.trabalhofinal.service;


import br.com.dbc.vemser.trabalhofinal.client.EnderecoClient;
import br.com.dbc.vemser.trabalhofinal.repository.ClienteRepository;
import br.com.dbc.vemser.trabalhofinal.repository.MedicoRepository;
import br.com.dbc.vemser.trabalhofinal.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

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
}
