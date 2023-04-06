package br.com.dbc.vemser.trabalhofinal.service;

import br.com.dbc.vemser.trabalhofinal.dto.RedefinicaoSenhaDTO;
import br.com.dbc.vemser.trabalhofinal.dto.TrocaSenhaDTO;
import br.com.dbc.vemser.trabalhofinal.dto.usuario.UsuarioCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.usuario.UsuarioDTO;
import br.com.dbc.vemser.trabalhofinal.entity.EspecialidadeEntity;
import br.com.dbc.vemser.trabalhofinal.entity.MedicoEntity;
import br.com.dbc.vemser.trabalhofinal.entity.UsuarioEntity;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.repository.UsuarioRepository;
import br.com.dbc.vemser.trabalhofinal.security.CodigoTrocaSenha;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@MockitoSettings(strictness = Strictness.LENIENT)
@RunWith(MockitoJUnitRunner.class)
public class UsuarioServiceTest {

    @Spy
    @InjectMocks
    private UsuarioService usuarioService;

    @Mock(lenient = true)
    private UsuarioRepository usuarioRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
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
    public void deveRemoverUsuarioSucesso() throws RegraDeNegocioException{
        //SETUP
        UsuarioEntity usuarioEntityMockDoBanco = getUsuarioEntityMockDoBanco();
        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuarioEntityMockDoBanco));
        Integer idProcurado = 1;
        //ACT
        usuarioService.remover(idProcurado);
        //ASSERT
        verify(usuarioRepository, times(1)).save(usuarioEntityMockDoBanco);
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveRemoverUsuarioFalha() throws RegraDeNegocioException{
        //SETUP
        UsuarioEntity usuarioEntityMockDoBanco = getUsuarioEntityDesativadoMockDoBanco();

        doReturn(usuarioEntityMockDoBanco).when(usuarioService).getUsuario(anyInt());
        //ACT
        usuarioService.remover(usuarioEntityMockDoBanco.getIdUsuario());
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
    @Test(expected = RegraDeNegocioException.class)
    public void deveEntrarIfCpfJaExisteUsuarioAdicionado() throws RegraDeNegocioException {
        //SETUP
        UsuarioEntity usuarioEntity = getUsuarioEntityMock();
        List<UsuarioEntity> usuarioEntities = List.of(getUsuarioEntityMock());

        when(usuarioRepository.findAll()).thenReturn(usuarioEntities);
        //ACT
        usuarioService.validarUsuarioAdicionado(usuarioEntity);
        //ASSERT
        verify(usuarioService,times(1)).validarUsuarioAdicionado(usuarioEntity);
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveEntrarIfEmailJaExisteUsuarioAdicionado() throws RegraDeNegocioException {
        //SETUP
        UsuarioEntity usuarioEntity = getUsuarioEntityMock();
        usuarioEntity.setIdCargo(2);
        usuarioEntity.setCpf("12345678999");
        List<UsuarioEntity> usuarioEntities = List.of(getUsuarioEntityMock());

        when(usuarioRepository.findAll()).thenReturn(usuarioEntities);
        //ACT
        usuarioService.validarUsuarioAdicionado(usuarioEntity);
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveEntrarIfCrmJaExisteUsuarioAdicionado() throws RegraDeNegocioException {
        //SETUP
        UsuarioEntity usuarioEntity = getUsuarioEntityMock();
        usuarioEntity.setIdCargo(2);
        usuarioEntity.setMedicoEntity(getMedicoEntityMock());


        List<UsuarioEntity> usuarioEntities = List.of(usuarioEntity);
        when(usuarioRepository.findAll()).thenReturn(usuarioEntities);
        //ACT
        usuarioService.validarUsuarioAdicionado(usuarioEntity);
    }

    @Test
    public void naoDeveEntrarNoForUsuarioAdicionado() throws RegraDeNegocioException {
        //Setup
        UsuarioEntity usuarioEntity = getUsuarioEntityMock();
        usuarioEntity.setCpf("12345678999");
        usuarioEntity.setEmail("abc@gmail.com");
        List<UsuarioEntity> usuarioEntities = List.of(getUsuarioEntityMock());

        when(usuarioRepository.findAll()).thenReturn(usuarioEntities);
        //ACT
        usuarioService.validarUsuarioAdicionado(usuarioEntity);
    }

    //<TODO>
    @Test
    public void deveValidarUsuarioEditado() throws RegraDeNegocioException {
        //SETUP
        UsuarioCreateDTO usuarioCreateDTO = getUsuarioCreateDTOMock();
        List<UsuarioEntity> usuarioEntities = List.of(getUsuarioEntityMock());
        when(usuarioRepository.findAll()).thenReturn(usuarioEntities);
        //ACT
        usuarioService.validarUsuarioEditado(usuarioCreateDTO,1);
        //ASSERT
        verify(usuarioService,times(1)).validarUsuarioEditado(usuarioCreateDTO,1);
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveEntrarIfDeValidarUsuarioEditado() throws RegraDeNegocioException{
        //Setup
        UsuarioCreateDTO usuarioCreateDTO = getUsuarioCreateDTOMock();
        List<UsuarioEntity> usuarioEntities = List.of(getUsuarioEntityMockDoBanco());

        when(usuarioRepository.findAll()).thenReturn(usuarioEntities);
        //act
        usuarioService.validarUsuarioEditado(usuarioCreateDTO,2);
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveEntrarIfEmailDeValidarUsuarioEditado() throws RegraDeNegocioException{
        //Setup
        UsuarioCreateDTO usuarioCreateDTO = getUsuarioCreateDTOMock();
        List<UsuarioEntity> usuarioEntities = List.of(getUsuarioEntityMockDoBanco());
        usuarioCreateDTO.setCpf("11122233344");
        when(usuarioRepository.findAll()).thenReturn(usuarioEntities);
        //act
        usuarioService.validarUsuarioEditado(usuarioCreateDTO,2);
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

    @Test
    public void deveReativarUsuarioSucesso() throws RegraDeNegocioException {
        //SETUP
        UsuarioEntity usuarioEntityDesativadoMockDoBanco = getUsuarioEntityDesativadoMockDoBanco();
        doReturn(usuarioEntityDesativadoMockDoBanco).when(usuarioService).getUsuario(any());
        UsuarioDTO usuarioDTOMock = getUsuarioDTOMock();
        doReturn(usuarioDTOMock).when(usuarioService).getById(any());

        //ACT
        UsuarioDTO usuarioDTOReativadoMock = usuarioService.reativarUsuario(1);

        //ASSERT
        assertNotNull(usuarioDTOReativadoMock);
        assertEquals(1, usuarioEntityDesativadoMockDoBanco.getAtivo());
        assertEquals(usuarioDTOMock, usuarioDTOReativadoMock);
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveReativarUsuarioFalha() throws RegraDeNegocioException {
        //SETUP
        UsuarioEntity usuarioEntityDesativadoMockDoBanco = getUsuarioEntityMockDoBanco();
        doReturn(usuarioEntityDesativadoMockDoBanco).when(usuarioService).getUsuario(any());
        UsuarioDTO usuarioDTOMock = getUsuarioDTOMock();
        //ACT
        UsuarioDTO usuarioDTOReativadoMock = usuarioService.reativarUsuario(1);
    }

    @Test
    public void deveRetornarIdUsuarioLogado(){
        //SETUP
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        Integer id = 1;

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(id);

        //ACT
        Integer variavel = usuarioService.getIdLoggedUser();
        //ASSERT
        assertNotNull(variavel);

    }
    @Test
    public void deveTrocarSenhaUsuarioSucesso() throws RegraDeNegocioException{
        //SETUP
        UsuarioEntity usuarioEntityMock = getUsuarioEntityMock();
        TrocaSenhaDTO trocaSenhaDTO = getTrocaSenhaDTOMock();

        doReturn(1).when(usuarioService).getIdLoggedUser();
        doReturn(usuarioEntityMock).when(usuarioService).getUsuario(any());
        when(passwordEncoder.matches("123rogerio123", "$2a$12$8iOr2M0EsANYEXQtP0MzV.UyhaVk/wc26UcAdOThNBtUSycZz/0gS")).thenReturn(true);
        when(passwordEncoder.matches("147", "$2a$12$8iOr2M0EsANYEXQtP0MzV.UyhaVk/wc26UcAdOThNBtUSycZz/0gS")).thenReturn(false);
        when(passwordEncoder.encode("147")).thenReturn("$2a$12$TNNR7Ii/s7A6h5Ie/owuEu3/fFh9OYZqtXDPvVwZCSuwACnZcZQwu");

        //ACT
        usuarioService.trocarSenha(trocaSenhaDTO);

        //ASSERT
        verify(usuarioRepository, times(1)).save(usuarioEntityMock);
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTrocarSenhaUsuarioFalha() throws RegraDeNegocioException{
        //SETUP
        UsuarioEntity usuarioEntityMock = getUsuarioEntityMock();
        TrocaSenhaDTO trocaSenhaDTO = getTrocaSenhaDTOMock();

        doReturn(1).when(usuarioService).getIdLoggedUser();
        doReturn(usuarioEntityMock).when(usuarioService).getUsuario(any());
        //ACT
        usuarioService.trocarSenha(trocaSenhaDTO);
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveSolicitarRedefinirSenhaFalha() throws RegraDeNegocioException, JsonProcessingException {
        //setup
        String email = "aab@gmail.com";
        UsuarioEntity usuario = getUsuarioEntityMock();

        when(usuarioService.findByEmail(anyString())).thenReturn(Optional.of(usuario));
        //act
        usuarioService.solicitarRedefinirSenha(email);
        //asserts
        verify(usuarioService,times(1)).solicitarRedefinirSenha(email);
    }


    @Test(expected = RegraDeNegocioException.class)
    public void deveRedefinirSenhaFalha() throws RegraDeNegocioException, JsonProcessingException {
        //SETUP
        UsuarioEntity usuario = getUsuarioEntityMock();
        RedefinicaoSenhaDTO redefinicaoSenhaDTO = getRedefinicaoSenhaDTOMock();

        doReturn(Optional.of(usuario)).when(usuarioService).findByEmail(any());
        //ACT
        usuarioService.redefinirSenha(redefinicaoSenhaDTO);
        //ASSERT
        verify(usuarioService,times(1)).redefinirSenha(redefinicaoSenhaDTO);
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
        usuarioEntityMockado.setSenha("$2a$12$8iOr2M0EsANYEXQtP0MzV.UyhaVk/wc26UcAdOThNBtUSycZz/0gS");
        usuarioEntityMockado.setCep("01010904");
        usuarioEntityMockado.setNumero(15);
        usuarioEntityMockado.setContatos("34999748512, 34999658741");
        usuarioEntityMockado.setIdCargo(1);
        usuarioEntityMockado.setAtivo(1);
        return usuarioEntityMockado;
    }

    @NotNull
    public static RedefinicaoSenhaDTO getRedefinicaoSenhaDTOMock(){
        RedefinicaoSenhaDTO redefinicaoSenhaDTO = new RedefinicaoSenhaDTO();
        redefinicaoSenhaDTO.setEmail("rogerio.santos@gmail.com");
        redefinicaoSenhaDTO.setCodigoConfirmacao(123);
        redefinicaoSenhaDTO.setSenhaNova("123");
        return redefinicaoSenhaDTO;
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
    static MedicoEntity getMedicoEntityMock() {
        MedicoEntity medicoMockadaDoBanco = new MedicoEntity();
        medicoMockadaDoBanco.setIdMedico(1);
        medicoMockadaDoBanco.setCrm("123456");
        medicoMockadaDoBanco.setUsuarioEntity(getUsuarioEntityMock());
        medicoMockadaDoBanco.setEspecialidadeEntity(getEspecialidadeEntityMock());
        return medicoMockadaDoBanco;
    }
    @NotNull
    private static EspecialidadeEntity getEspecialidadeEntityMock() {
        EspecialidadeEntity especialidadeEntity = new EspecialidadeEntity();
        especialidadeEntity.setIdEspecialidade(1);
        especialidadeEntity.setNomeEspecialidade("Cardiologista");
        especialidadeEntity.setValor(500);
        return especialidadeEntity;
    }

    @NotNull
    private static UsuarioEntity getUsuarioEntityMockDoBanco(){
        UsuarioEntity usuarioEntityMockDoBanco = getUsuarioEntityMock();
        usuarioEntityMockDoBanco.setAtivo(1);
        return usuarioEntityMockDoBanco;
    }

    @NotNull
    private static UsuarioEntity getUsuarioEntityDesativadoMockDoBanco(){
        UsuarioEntity usuarioEntityMockDoBanco = getUsuarioEntityMock();
        usuarioEntityMockDoBanco.setAtivo(0);
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
        usuarioCreateDTOMock.setEmail("rogerio.santos@gmail.com");
        return usuarioCreateDTOMock;
    }

    @NotNull
    private static TrocaSenhaDTO getTrocaSenhaDTOMock(){
        TrocaSenhaDTO trocaSenhaDTO = new TrocaSenhaDTO();

        trocaSenhaDTO.setSenhaAntiga("123rogerio123");
        trocaSenhaDTO.setSenhaNova("147");

        return trocaSenhaDTO;
    }

}
