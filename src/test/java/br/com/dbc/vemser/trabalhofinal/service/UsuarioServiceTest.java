package br.com.dbc.vemser.trabalhofinal.service;

import br.com.dbc.vemser.trabalhofinal.client.EnderecoClient;
import br.com.dbc.vemser.trabalhofinal.dto.usuario.UsuarioCreateDTO;
import br.com.dbc.vemser.trabalhofinal.entity.CargoEntity;
import br.com.dbc.vemser.trabalhofinal.entity.ClienteEntity;
import br.com.dbc.vemser.trabalhofinal.entity.ConvenioEntity;
import br.com.dbc.vemser.trabalhofinal.entity.UsuarioEntity;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
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
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import javax.validation.constraints.NotNull;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private EmailService emailService;
    @Mock
    private EnderecoClient enderecoClient;
    @Mock
    private CodigoTrocaSenha codigoTrocaSenha;


    private ObjectMapper objectMapper = new ObjectMapper();
    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(usuarioService, "objectMapper", objectMapper);
    }

    @Test
    public void deveAdicionarUsuario() throws RegraDeNegocioException{
        //SETUP
        UsuarioEntity usuarioEntityMock = getUsuarioEntityMock();
        //ACT
         usuarioService.adicionar(usuarioEntityMock);
        //ASSERT
        verify(usuarioRepository, times(1)).save(usuarioEntityMock);
    }

    @Test
    public void deveRemoverUsuario() throws RegraDeNegocioException{
        //SETUP
        UsuarioEntity usuarioEntityMockDoBanco = getUsuarioEntityMockDoBanco();
        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuarioEntityMockDoBanco));
        Integer idProcurado = 1;
        //ACT
        usuarioService.remover(idProcurado);
        //ASSERT
        verify(usuarioRepository, times(1)).save(usuarioEntityMockDoBanco);
    }

    @Test
    public void deveRemoverComHardDelete() throws RegraDeNegocioException {
        //SETUP
        UsuarioEntity usuarioEntityMockDoBanco = getUsuarioEntityMockDoBanco();
        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuarioEntityMockDoBanco));
        //ACT
        usuarioService.hardDelete(1);
        //ASSERT
        verify(usuarioRepository, times(1)).delete(usuarioEntityMockDoBanco);
    }

    @Test
    public void deveEditarUsuario() throws RegraDeNegocioException {
        //SETUP
        UsuarioEntity usuarioEntityMockDoBanco = getUsuarioEntityMockDoBanco();
        UsuarioCreateDTO usuarioCreateDTOMock = getUsuarioCreateDTOMock();

        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuarioEntityMockDoBanco));

        //ACT
        usuarioService.editar(usuarioCreateDTOMock, 1);
        //ASSERT
        verify(usuarioRepository, times(1)).save(usuarioEntityMockDoBanco);
    }

    @Test
    public void deveRetornarUsuarioEntityPeloId() throws RegraDeNegocioException{
        //SETUP
        UsuarioEntity usuarioEntityMockDoBanco = getUsuarioEntityMockDoBanco();
        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuarioEntityMockDoBanco));

        //ACT
        UsuarioEntity usuarioEntityMockProcurado = usuarioService.getUsuario(1);

        //ASSERT
        assertNotNull(usuarioEntityMockProcurado);
        assertEquals(usuarioEntityMockDoBanco.getIdUsuario(), usuarioEntityMockProcurado.getIdUsuario());
        assertEquals(usuarioEntityMockDoBanco.getIdCargo(), usuarioEntityMockProcurado.getIdCargo());
        assertEquals(usuarioEntityMockDoBanco.getCpf(), usuarioEntityMockProcurado.getCpf());
        assertEquals(usuarioEntityMockDoBanco.getEmail(), usuarioEntityMockProcurado.getEmail());
        assertEquals(usuarioEntityMockDoBanco.getNome(), usuarioEntityMockProcurado.getNome());
        assertEquals(usuarioEntityMockDoBanco.getSenha(), usuarioEntityMockProcurado.getSenha());
        assertEquals(usuarioEntityMockDoBanco.getCep(), usuarioEntityMockProcurado.getCep());
        assertEquals(usuarioEntityMockDoBanco.getNumero(), usuarioEntityMockProcurado.getNumero());
        assertEquals(usuarioEntityMockDoBanco.getContatos(), usuarioEntityMockProcurado.getContatos());
        assertEquals(usuarioEntityMockDoBanco.getAtivo(), usuarioEntityMockProcurado.getAtivo());
        
    }

    // ABSTRAÇÃO DE MÉTODOS

    @NotNull
    private static UsuarioEntity getUsuarioEntityMock(){
        UsuarioEntity usuarioEntityMockado = new UsuarioEntity();
        usuarioEntityMockado.setIdUsuario(1);
        usuarioEntityMockado.setIdCargo(1);
        usuarioEntityMockado.setCpf("14574198422");
        usuarioEntityMockado.setEmail("rogerio.santos@gmail.com");
        usuarioEntityMockado.setNome("Rogério Santos");
        usuarioEntityMockado.setSenha("123rogerio123");
        usuarioEntityMockado.setCep("01010904");
        usuarioEntityMockado.setNumero(15);
        usuarioEntityMockado.setContatos("34999748512, 34999658741");
        usuarioEntityMockado.setIdCargo(1);
        return usuarioEntityMockado;
    }

    @NotNull
    private static UsuarioEntity getUsuarioEntityMockDoBanco(){
        UsuarioEntity usuarioEntityMockDoBanco = getUsuarioEntityMock();
        usuarioEntityMockDoBanco.setAtivo(1);
        return usuarioEntityMockDoBanco;
    }

    @NotNull
    private static UsuarioCreateDTO getUsuarioCreateDTOMock(){
        UsuarioCreateDTO usuarioCreateDTOMock = new UsuarioCreateDTO();
        usuarioCreateDTOMock.setCpf("14574198422");
        usuarioCreateDTOMock.setNome("Rogério Santos");
        usuarioCreateDTOMock.setCep("04650185");
        usuarioCreateDTOMock.setNumero(15);
        usuarioCreateDTOMock.setContatos("34999748512, 34999658741");
        return usuarioCreateDTOMock;
    }


}
