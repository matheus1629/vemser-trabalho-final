package br.com.dbc.vemser.trabalhofinal.service;


import br.com.dbc.vemser.trabalhofinal.client.EnderecoClient;
import br.com.dbc.vemser.trabalhofinal.dto.PageDTO;
import br.com.dbc.vemser.trabalhofinal.dto.cliente.ClienteCompletoDTO;
import br.com.dbc.vemser.trabalhofinal.dto.medico.MedicoCompletoDTO;
import br.com.dbc.vemser.trabalhofinal.dto.usuario.UsuarioCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.usuario.UsuarioDTO;
import br.com.dbc.vemser.trabalhofinal.dto.usuario.UsuarioUpdateDTO;
import br.com.dbc.vemser.trabalhofinal.entity.UsuarioEntity;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.repository.ClienteRepository;
import br.com.dbc.vemser.trabalhofinal.repository.MedicoRepository;
import br.com.dbc.vemser.trabalhofinal.repository.UsuarioRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import freemarker.template.TemplateException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import javax.mail.MessagingException;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

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
        UsuarioDTO usuarioDTO = administrativoService.reativarUsuario(1);
        //assert
        assertNotNull(usuarioDTO);
    }

    @Test
    public void testeListar() {
        //setup
        when(usuarioRepository.findAll()).thenReturn(List.of(getUsuarioEntityMock()));
        //action
        List<UsuarioDTO> usuarioDTOS = administrativoService.listar();
        //assert
        assertNotNull(usuarioDTOS);
    }

    @Test
    public void testarAdicionarSucesso() throws RegraDeNegocioException, JsonProcessingException {
        //setup
        when(usuarioService.getById(any())).thenReturn(getUsuarioDTOMock());
        //act
        UsuarioDTO usuarioDTO = administrativoService.adicionar(getUsuarioCreateDTOMock());
        //assert
        assertNotNull(usuarioDTO);
    }

    @Test
    public void testarEditarSuceso() throws RegraDeNegocioException{
        //setup
        UsuarioEntity usuarioEntity = getUsuarioEntityMock();
        doReturn(usuarioEntity).when(administrativoService).getAdm(any());
        when(usuarioRepository.save(any())).thenReturn(usuarioEntity);
        when(usuarioService.getById(any())).thenReturn(getUsuarioDTOMock());
        //act
        UsuarioDTO usuarioDTO = administrativoService.editar(1,getUsuarioUpdate());
        //assert
        assertNotNull(usuarioDTO);
        assertNotEquals(usuarioEntity.getCpf(),usuarioDTO.getCpf());
    }

    @Test(expected = RegraDeNegocioException.class)
    public void testarEditarFracasso() throws RegraDeNegocioException{
        //setup

        UsuarioEntity usuarioEntity = getUsuarioEntityMock();
        usuarioEntity.setIdCargo(2);
        doReturn(usuarioEntity).when(administrativoService).getAdm(any());
        //act
        administrativoService.editar(1,getUsuarioUpdate());
    }

    @Test
    public void testeGetAdm() throws RegraDeNegocioException{
        //setup
        when(usuarioRepository.findById(any())).thenReturn(Optional.of(getUsuarioEntityMock()));
        //act
        UsuarioEntity usuario = administrativoService.getAdm(1);
        //assert
        assertNotNull(usuario);
    }

    @Test
    public void testarGetClienteById() throws RegraDeNegocioException{
        //setup
        when(clienteService.getById(any())).thenReturn(getClienteCompletoDTOMock());
        //act
        ClienteCompletoDTO completoDTO = administrativoService.getClienteById(getClienteCompletoDTOMock().getIdCliente());
        //assert
        assertNotNull(completoDTO);
    }

    @Test
    public void testarGetMedicoById() throws RegraDeNegocioException{
        //setup
        when(medicoService.getById(any())).thenReturn(getMedicoCompletoDTOMock());
        //act
        MedicoCompletoDTO medicoCompletoDTO = administrativoService.getMedicoById(getMedicoCompletoDTOMock().getIdMedico());
        //assert
        assertNotNull(medicoCompletoDTO);
    }

    @Test
    public void testeRemoverAdm() throws RegraDeNegocioException{
        //setup
        UsuarioEntity usuario = getUsuarioEntityMock();
        doReturn(usuario).when(administrativoService).getAdm(any());
        //act
        administrativoService.remover(1);
        //assert
        verify(usuarioService,Mockito.times(1)).remover(anyInt());
    }
    @Test
    public void testeRemoverCliente() throws RegraDeNegocioException{
        //act
        administrativoService.removerCliente(1);
        //assert
        verify(clienteService,Mockito.times(1)).remover(anyInt());
    }

    @Test
    public void testeRemoverMedico() throws RegraDeNegocioException{
        //act
        administrativoService.removerMedico(1);
        verify(medicoService,Mockito.times(1)).remover(anyInt());
    }

    @Test
    public void deveListarComSucessoCliente() {
        //setup
        Integer pagina = 1, tamanho = 1;
        Page<ClienteCompletoDTO> clienteCompletoDTO = new PageImpl<>(List.of(getClienteCompletoDTOMock()));
        when(clienteRepository.listarFull(any(Pageable.class))).thenReturn(clienteCompletoDTO);
        //act
        PageDTO<ClienteCompletoDTO> pageClienteCompletoDTO = administrativoService.listCliente(pagina,tamanho);
        //assert
        assertNotNull(pageClienteCompletoDTO);
        assertEquals(clienteCompletoDTO.getTotalElements(),pageClienteCompletoDTO.getTotalElementos());
        assertEquals(clienteCompletoDTO.getTotalPages(),pageClienteCompletoDTO.getQuantidadePaginas());
        assertEquals(pageClienteCompletoDTO.getPagina(),pagina);
        assertEquals(pageClienteCompletoDTO.getTamanho(),tamanho);
        assertEquals(clienteCompletoDTO.getContent().size(),pageClienteCompletoDTO.getElementos().size());
    }

    @Test
    public void deveListarComSucessoMedico() {
        //setup
        Integer pagina = 1, tamanho = 1;
        Page<MedicoCompletoDTO> medicoCompletoDTO = new PageImpl<>(List.of(getMedicoCompletoDTOMock()));
        when(medicoRepository.listarFull(any(Pageable.class))).thenReturn(medicoCompletoDTO);
        //act
        PageDTO<MedicoCompletoDTO> pageMedicoCompletoDTO = administrativoService.listMedico(pagina,tamanho);
        //assert
        assertNotNull(pageMedicoCompletoDTO);
        assertEquals(medicoCompletoDTO.getTotalElements(),pageMedicoCompletoDTO.getTotalElementos());
        assertEquals(medicoCompletoDTO.getTotalPages(),pageMedicoCompletoDTO.getQuantidadePaginas());
        assertEquals(pageMedicoCompletoDTO.getPagina(),pagina);
        assertEquals(pageMedicoCompletoDTO.getTamanho(),tamanho);
        assertEquals(medicoCompletoDTO.getContent().size(),pageMedicoCompletoDTO.getElementos().size());
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
    private static UsuarioEntity getUsuarioEntityMock(){
        UsuarioEntity usuarioEntityMockado = new UsuarioEntity();
        usuarioEntityMockado.setIdUsuario(1);
        usuarioEntityMockado.setIdCargo(1);
        usuarioEntityMockado.setCpf("12345678910");
        usuarioEntityMockado.setEmail("rogerio.santos@gmail.com");
        usuarioEntityMockado.setNome("Rogério Santos");
        usuarioEntityMockado.setSenha("$2a$12$8iOr2M0EsANYEXQtP0MzV.UyhaVk/wc26UcAdOThNBtUSycZz/0gS");
        usuarioEntityMockado.setCep("01010904");
        usuarioEntityMockado.setNumero(15);
        usuarioEntityMockado.setContatos("34999748512, 34999658741");
        usuarioEntityMockado.setAtivo(1);
        return usuarioEntityMockado;
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

    private static UsuarioUpdateDTO getUsuarioUpdate(){
        UsuarioUpdateDTO usuarioUpdateDTO = new UsuarioUpdateDTO();
        usuarioUpdateDTO.setCep("12345678");
        usuarioUpdateDTO.setNome("Carlos");
        usuarioUpdateDTO.setCpf("12345678910");
        usuarioUpdateDTO.setNumero(123);
        usuarioUpdateDTO.setContatos("12345678");
        return usuarioUpdateDTO;
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
