package br.com.dbc.vemser.trabalhofinal.service;

import br.com.dbc.vemser.trabalhofinal.client.EnderecoClient;
import br.com.dbc.vemser.trabalhofinal.dto.agendamento.AgendamentoClienteRelatorioDTO;
import br.com.dbc.vemser.trabalhofinal.dto.agendamento.AgendamentoDTO;
import br.com.dbc.vemser.trabalhofinal.dto.agendamento.AgendamentoListaDTO;
import br.com.dbc.vemser.trabalhofinal.dto.cliente.ClienteCompletoDTO;
import br.com.dbc.vemser.trabalhofinal.dto.cliente.ClienteCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.cliente.ClienteUpdateDTO;
import br.com.dbc.vemser.trabalhofinal.entity.CargoEntity;
import br.com.dbc.vemser.trabalhofinal.entity.ClienteEntity;
import br.com.dbc.vemser.trabalhofinal.entity.ConvenioEntity;
import br.com.dbc.vemser.trabalhofinal.entity.UsuarioEntity;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.repository.ClienteRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ClienteServiceTest {

    @Spy
    @InjectMocks
    private ClienteService clienteService;
    @Mock
    private UsuarioService usuarioService;
    @Mock
    private ConvenioService convenioService;
    @Mock
    private AgendamentoService agendamentoService;
    @Mock
    private EmailService emailService;
    @Mock
    private EnderecoClient enderecoClient;
    @Mock
    private ClienteRepository clienteRepository;

    private ObjectMapper objectMapper = new ObjectMapper();


    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(clienteService, "objectMapper", objectMapper);
    }


    @Test
    public void deveRecuperarClienteLogadoPorId() throws RegraDeNegocioException {
        //SETUP
        ClienteEntity clienteEntityMock = getClienteEntityMock();
        ClienteCompletoDTO clienteCompletoDTOMock = getClienteCompletoDTOMock();

        when(usuarioService.getIdLoggedUser()).thenReturn(1);
        when(clienteRepository.getClienteEntityByIdUsuario(any())).thenReturn(clienteEntityMock);
        when(clienteRepository.getByIdPersonalizado(any())).thenReturn(Optional.of(clienteCompletoDTOMock));

        //ACT
        ClienteCompletoDTO clienteCompletoDTORecuperado = clienteService.recuperarCliente();

        //ASSERT
        assertNotNull(clienteCompletoDTORecuperado);
        assertEquals(clienteCompletoDTORecuperado, clienteCompletoDTOMock);
    }

    @Test
    public void deveRetornarUmaBuscaPersonalizadaPeloId() throws RegraDeNegocioException {
        //SETUP
        ClienteCompletoDTO clienteCompletoDTOMock = getClienteCompletoDTOMock();
        when(clienteRepository.getByIdPersonalizado(any())).thenReturn(Optional.ofNullable(clienteCompletoDTOMock));

        //ACT
        ClienteCompletoDTO clienteCompletoDTORecuperado = clienteService.getById(1);

        //ASSERT
        assertNotNull(clienteCompletoDTORecuperado);
        Assertions.assertEquals(clienteCompletoDTORecuperado.getIdCliente(), clienteCompletoDTOMock.getIdCliente());
        assertEquals(clienteCompletoDTORecuperado.getIdUsuario(), clienteCompletoDTOMock.getIdUsuario());
        assertEquals(clienteCompletoDTORecuperado.getIdConvenio(), clienteCompletoDTOMock.getIdConvenio());
        assertEquals(clienteCompletoDTORecuperado.getCpf(), clienteCompletoDTOMock.getCpf());
        assertEquals(clienteCompletoDTORecuperado.getCep(), clienteCompletoDTOMock.getCep());
        assertEquals(clienteCompletoDTORecuperado.getNumero(), clienteCompletoDTOMock.getNumero());
        assertEquals(clienteCompletoDTORecuperado.getContatos(), clienteCompletoDTOMock.getContatos());
        assertEquals(clienteCompletoDTORecuperado.getEmail(), clienteCompletoDTOMock.getEmail());
        assertEquals(clienteCompletoDTORecuperado.getCadastroOrgaoRegulador(), clienteCompletoDTOMock.getCadastroOrgaoRegulador());
        assertEquals(clienteCompletoDTORecuperado.getTaxaAbatimento(), clienteCompletoDTOMock.getTaxaAbatimento());
        assertEquals(clienteCompletoDTORecuperado.getNomeCargo(), clienteCompletoDTOMock.getNomeCargo());
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarUsuarioNaoEncontrado() throws RegraDeNegocioException {
        //SETUP
        Integer idProcurado = 5;

        //ACT
        ClienteCompletoDTO clienteCompletoDTORecuperado = clienteService.getById(1);
    }

    @Test
    public void deveRetornarClienteEntityPeloId() throws RegraDeNegocioException {
        //SETUP
        ClienteEntity clienteEntityMock = getClienteEntityMock();
        when(clienteRepository.findById(any())).thenReturn(Optional.of(clienteEntityMock));

        //ACT
        ClienteEntity clienteEntityRecuperado = clienteService.getCliente(1);

        //ASSERT
        assertNotNull(clienteEntityRecuperado);
        assertEquals(clienteEntityMock.getIdCliente(), clienteEntityRecuperado.getIdUsuario());
        assertEquals(clienteEntityMock.getUsuarioEntity(), clienteEntityRecuperado.getUsuarioEntity());
        assertEquals(clienteEntityMock.getConvenioEntity(), clienteEntityRecuperado.getConvenioEntity());

    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarClienteNaoEncontrado() throws RegraDeNegocioException {
        //SETUP
        Integer idProcurado = 5;

        //ACT
        ClienteEntity clienteEntity = clienteService.getCliente(1);
    }

    @Test
    public void deveRetornarOsAgndamentosDoClienteLogado() throws RegraDeNegocioException {
        //SETUP
        ClienteEntity clienteEntityMock = getClienteEntityMock();
        ClienteCompletoDTO clienteCompletoDTOMock = getClienteCompletoDTOMock();
        List<AgendamentoDTO> agendamentoDTOListMock = List.of(getAgendamentoDTOMock(), getAgendamentoDTOMock(), getAgendamentoDTOMock());
        AgendamentoListaDTO agendamentoListaDTO = new AgendamentoListaDTO(agendamentoDTOListMock);
        AgendamentoClienteRelatorioDTO agendamentoClienteRelatorioDTO = getAgendamentoClienteRelatorioDTOMock();
        agendamentoClienteRelatorioDTO.setAgendamentoDTOList(agendamentoDTOListMock);


        when(usuarioService.getIdLoggedUser()).thenReturn(1);
        when(clienteRepository.getClienteEntityByIdUsuario(any())).thenReturn(clienteEntityMock);
        when(clienteRepository.getByIdPersonalizado(any())).thenReturn(Optional.of(clienteCompletoDTOMock));
        when(agendamentoService.getRelatorioClienteById(any())).thenReturn(agendamentoClienteRelatorioDTO);


        //ACT
        AgendamentoListaDTO clienteAgentamentosRecuperado = clienteService.getClienteAgentamentos();

        //ASSERT
        assertNotNull(clienteAgentamentosRecuperado);
        assertEquals(agendamentoListaDTO, clienteAgentamentosRecuperado);
    }

    @Test
    public void deveCriarUmCliente() throws RegraDeNegocioException { //<todo> testar o envio de email
        //SETUP
        ClienteCreateDTO clienteCreateDTOMock = new ClienteCreateDTO();
        clienteCreateDTOMock.setCep("12345678");
        clienteCreateDTOMock.setNome("Carlos");
        clienteCreateDTOMock.setEmail("Carlos@gmail.com");
        clienteCreateDTOMock.setCpf("12345678910");
        clienteCreateDTOMock.setNumero(123);
        clienteCreateDTOMock.setContatos("12345678");
        clienteCreateDTOMock.setSenha("123");
        clienteCreateDTOMock.setIdConvenio(1);

        ClienteCompletoDTO clienteCompletoDTOMock = getClienteCompletoDTOMock();

        doReturn(clienteCompletoDTOMock).when(clienteService).getById(any());

        // ACT
        ClienteCompletoDTO ClienteAdicionado = clienteService.adicionar(clienteCreateDTOMock);

        //ASSERT
        assertNotNull(ClienteAdicionado);
        assertEquals(clienteCompletoDTOMock, ClienteAdicionado);
    }

    @Test
    public void deveEditarCliente() throws RegraDeNegocioException {
        //SETUP
        ClienteUpdateDTO clienteUpdateDTO = getclienteUpdateDTOMock();
        ClienteEntity clienteEntityMock = getClienteEntityMock();
        ClienteCompletoDTO clienteCompletoDTOMock = getClienteCompletoDTOMock();

        doReturn(clienteCompletoDTOMock).when(clienteService).recuperarCliente();
        doReturn(clienteCompletoDTOMock).when(clienteService).getById(any());
        when(clienteRepository.save(any())).thenReturn(clienteEntityMock);


        // ACT
        ClienteCompletoDTO clienteEditado = clienteService.editar(clienteUpdateDTO);

        //ASSERT
        assertNotNull(clienteEditado);
        assertEquals(clienteCompletoDTOMock, clienteEditado);
    }

    @Test
    public void deveRemover() throws RegraDeNegocioException {
        //SETUP
        ClienteEntity clienteEntityMock = getClienteEntityMock();
        doReturn(clienteEntityMock).when(clienteService).getCliente(any());
        //ACT
        clienteService.remover(clienteEntityMock.getIdCliente());
        //ASSERT
        verify(usuarioService, times(1)).remover(clienteEntityMock.getIdUsuario());
        verify(agendamentoService, times(1)).removerPorClienteDesativado(clienteEntityMock);
    }

    private static ClienteUpdateDTO getclienteUpdateDTOMock(){
        ClienteUpdateDTO clienteUpdateDTO = new ClienteUpdateDTO();
        clienteUpdateDTO.setCep("12345678");
        clienteUpdateDTO.setNome("Carlos");
        clienteUpdateDTO.setCpf("12345678910");
        clienteUpdateDTO.setNumero(123);
        clienteUpdateDTO.setContatos("12345678");
        clienteUpdateDTO.setIdUsuario(1);
        clienteUpdateDTO.setIdConvenio(1);
        return clienteUpdateDTO;
    }

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

    private static ClienteEntity getClienteEntityMock() {
        ClienteEntity clienteEntity = new ClienteEntity();
        clienteEntity.setUsuarioEntity(getUsuarioEntityMock());
        clienteEntity.setConvenioEntity(getConvenioEntityMock());
        return clienteEntity;

    }

    private static UsuarioEntity getUsuarioEntityMock() {
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setSenha("123");
        usuarioEntity.setCargoEntity(getCargoEntityMock());
        usuarioEntity.setAtivo(1);
        usuarioEntity.setNome("Carlos");
        usuarioEntity.setCpf("12345678910");
        usuarioEntity.setCep("12345678");
        usuarioEntity.setContatos("12345678");
        usuarioEntity.setIdUsuario(1);
        usuarioEntity.setEmail("Carlos@gamil.com");
        usuarioEntity.setNumero(123);
        return usuarioEntity;
    }

    private static CargoEntity getCargoEntityMock() {
        CargoEntity cargoEntity = new CargoEntity();
        cargoEntity.setIdCargo(1);
        cargoEntity.setNomeCargo("ROLE_CLIENTE");
        return cargoEntity;
    }

    private static ConvenioEntity getConvenioEntityMock() {
        ConvenioEntity convenioEntity = new ConvenioEntity();
        convenioEntity.setIdConvenio(1);
        convenioEntity.setTaxaAbatimento(10.0);
        convenioEntity.setCadastroOrgaoRegulador("123456");
        return convenioEntity;
    }

    private static AgendamentoDTO getAgendamentoDTOMock() {
        AgendamentoDTO agendamentoDTO = new AgendamentoDTO();
        agendamentoDTO.setIdAgendamento(1);
        agendamentoDTO.setValorAgendamento(500.0);
        agendamentoDTO.setExame("Exame de Sangue");
        agendamentoDTO.setDataHorario(LocalDateTime.of(2022, 8, 15, 15, 20));
        agendamentoDTO.setIdMedico(1);
        agendamentoDTO.setIdCliente(1);
        agendamentoDTO.setTratamento("Paracetamol");
        return agendamentoDTO;
    }

    private static AgendamentoClienteRelatorioDTO getAgendamentoClienteRelatorioDTOMock(){
        AgendamentoClienteRelatorioDTO agendamentoClienteRelatorioDTO = new AgendamentoClienteRelatorioDTO();
        agendamentoClienteRelatorioDTO.setIdCliente(1);
        agendamentoClienteRelatorioDTO.setIdConvenio(1);
        agendamentoClienteRelatorioDTO.setIdUsuario(1);
        agendamentoClienteRelatorioDTO.setCadastroOrgaoRegulador("123456");
        agendamentoClienteRelatorioDTO.setTaxaAbatimento(500.0);
        agendamentoClienteRelatorioDTO.setCpf("12345678910");
        agendamentoClienteRelatorioDTO.setEmail("Carlos@gmail.com");
        agendamentoClienteRelatorioDTO.setNome("Carlos");
        agendamentoClienteRelatorioDTO.setNomeCargo("ROLE_CLIENTE");
        agendamentoClienteRelatorioDTO.setContatos("12345678");
        agendamentoClienteRelatorioDTO.setCep("12345678");
        agendamentoClienteRelatorioDTO.setNumero(123);
        return agendamentoClienteRelatorioDTO;
    }


}
