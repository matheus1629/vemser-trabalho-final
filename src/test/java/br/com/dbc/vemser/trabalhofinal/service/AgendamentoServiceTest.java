package br.com.dbc.vemser.trabalhofinal.service;


import br.com.dbc.vemser.trabalhofinal.dto.PageDTO;
import br.com.dbc.vemser.trabalhofinal.dto.agendamento.AgendamentoClienteRelatorioDTO;
import br.com.dbc.vemser.trabalhofinal.dto.agendamento.AgendamentoCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.agendamento.AgendamentoDTO;
import br.com.dbc.vemser.trabalhofinal.dto.agendamento.AgendamentoMedicoRelatorioDTO;
import br.com.dbc.vemser.trabalhofinal.dto.cliente.ClienteCompletoDTO;
import br.com.dbc.vemser.trabalhofinal.dto.medico.MedicoCompletoDTO;
import br.com.dbc.vemser.trabalhofinal.entity.*;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.repository.AgendamentoRepository;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static br.com.dbc.vemser.trabalhofinal.service.ClienteServiceTest.getClienteCompletoDTOMock;
import static br.com.dbc.vemser.trabalhofinal.service.ClienteServiceTest.getClienteEntityMock;
import static br.com.dbc.vemser.trabalhofinal.service.MedicoServiceTest.getMedicoCompletoDTOMock;
import static br.com.dbc.vemser.trabalhofinal.service.MedicoServiceTest.getMedicoEntityMock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AgendamentoServiceTest {

    @Spy
    @InjectMocks
    private AgendamentoService agendamentoService;
    @Mock
    private AgendamentoRepository agendamentoRepository;
    @Mock
    private ClienteService clienteService;
    @Mock
    private MedicoService medicoService;
    @Mock
    private SolicitacaoService solicitacaoService;

    private ObjectMapper objectMapper = new ObjectMapper();
    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(agendamentoService, "objectMapper", objectMapper);
    }



    @Test(expected = RegraDeNegocioException.class)
    public void deveEntrarNoIfPendenteAdicionarAgendamento() throws RegraDeNegocioException, JsonProcessingException {
        //SETUP
        ClienteEntity clienteEntityMock = getClienteEntityMock();
        MedicoEntity medicoEntityMock = getMedicoEntityMock();
        AgendamentoEntity agendamentoEntityMock = getAgendamentoEntityMock();
        SolicitacaoEntity solicitacaoEntity = getSolicitacaoEntityMock();
        AprovarReprovarSolicitacao aprovarReprovarSolicitacao = AprovarReprovarSolicitacao.APROVADA;

        when(solicitacaoService.getSolicitacao(any())).thenReturn(solicitacaoEntity);
        //ACT
        AgendamentoDTO agendamentoAdicionado = agendamentoService.adicionar("1",aprovarReprovarSolicitacao);

        //ASSERT
        verify(agendamentoRepository, times(1)).save(any());
        assertNotNull(agendamentoAdicionado);
    }

    @Test
    public void deveRemoverAgendamentosAoDesativarMedicoRelacionado() throws RegraDeNegocioException {
        //SETUP
        MedicoEntity medicoEntityMock = getMedicoEntityMock();

        //ACT
        agendamentoService.removerPorMedicoDesativado(medicoEntityMock);

        //ASSERT
        verify(agendamentoRepository, times(1)).deleteByMedicoEntity(medicoEntityMock);
    }

    @Test
    public void deveRemoverAgendamentosAoDesativarClienteRelacionado() throws RegraDeNegocioException {
        //SETUP
        ClienteEntity clienteEntityMock = getClienteEntityMock();

        //ACT
        agendamentoService.removerPorClienteDesativado(clienteEntityMock);

        //ASSERT
        verify(agendamentoRepository, times(1)).deleteByClienteEntity(clienteEntityMock);
    }

    @Test
    public void deveRecuperarAgendamentoEntityPeloId() throws RegraDeNegocioException {
        //SETUP
        AgendamentoEntity agendamentoEntityMock = getAgendamentoEntityMock();

        when(agendamentoRepository.findById(1)).thenReturn(Optional.of(agendamentoEntityMock));

        //ACT
        AgendamentoEntity agendamentoRecuperado = agendamentoService.getAgendamento(1);

        //ASSERT
        assertNotNull(agendamentoRecuperado);
        assertEquals(agendamentoEntityMock, agendamentoRecuperado);

    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveDarExceptionAoProcurarAgendamentoInexistente() throws RegraDeNegocioException {
        //ACT
        AgendamentoEntity agendamentoRecuperado = agendamentoService.getAgendamento(1);
    }

    @Test
    public void deveRecuperarAgendamentoDTOPeloId() throws RegraDeNegocioException {
        //SETUP
        AgendamentoEntity agendamentoEntityMock = getAgendamentoEntityMock();

        doReturn(agendamentoEntityMock).when(agendamentoService).getAgendamento(any());

        //ACT
        AgendamentoDTO agendamentoRecuperado = agendamentoService.getById(1);

        //ASSERT
        assertNotNull(agendamentoRecuperado);
        assertEquals(agendamentoEntityMock.getIdAgendamento(), agendamentoRecuperado.getIdAgendamento());
        assertEquals(agendamentoEntityMock.getValorAgendamento(), agendamentoRecuperado.getValorAgendamento());
        assertEquals(agendamentoEntityMock.getIdCliente(), agendamentoRecuperado.getIdCliente());
        assertEquals(agendamentoEntityMock.getIdMedico(), agendamentoRecuperado.getIdMedico());
        assertEquals(agendamentoEntityMock.getExame(), agendamentoRecuperado.getExame());
        assertEquals(agendamentoEntityMock.getTratamento(), agendamentoRecuperado.getTratamento());

    }

    @Test
    public void deveRetornarRelatorioPersonalizadoDoClientePeloId() throws RegraDeNegocioException {
        //SETUP
        List<AgendamentoEntity> agendamentoEntityListMock = List.of(getAgendamentoEntityMock(), getAgendamentoEntityMock());
        ClienteCompletoDTO clienteCompletoDTOMock = getClienteCompletoDTOMock();
        AgendamentoClienteRelatorioDTO agendamentoClienteRelatorioDTOMock = getAgendamentoClienteRelatorioDTOMock();


        when(agendamentoRepository.findAllByIdCliente(any())).thenReturn(agendamentoEntityListMock);
        doReturn(clienteCompletoDTOMock).when(clienteService).getById(any());

        //ACT
        AgendamentoClienteRelatorioDTO relatorioClienteByIdRecuperado = agendamentoService.getRelatorioClienteById(1);
        //ASSERT

        assertNotNull(relatorioClienteByIdRecuperado);
        assertEquals(agendamentoClienteRelatorioDTOMock, relatorioClienteByIdRecuperado);
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveRetornarExceptionClienteNaoExiste() throws RegraDeNegocioException {
        //SETUP
        List<AgendamentoEntity> agendamentoEntityListMock = List.of();
        ClienteCompletoDTO clienteCompletoDTOMock = getClienteCompletoDTOMock();

        doReturn(clienteCompletoDTOMock).when(clienteService).getById(any());
        when(agendamentoRepository.findAllByIdCliente(any())).thenReturn(agendamentoEntityListMock);

        //ACT
        agendamentoService.getRelatorioClienteById(2);
    }

    @Test
    public void deveRetornarRelatorioPersonalizadoDoMedicoPeloId() throws RegraDeNegocioException {
        //SETUP
        List<AgendamentoEntity> agendamentoEntityListMock = List.of(getAgendamentoEntityMock(), getAgendamentoEntityMock());
        MedicoCompletoDTO medicoCompletoDTOMock = getMedicoCompletoDTOMock();
        AgendamentoClienteRelatorioDTO agendamentoClienteRelatorioDTOMock = getAgendamentoClienteRelatorioDTOMock();


        when(agendamentoRepository.findAllByIdMedico(any())).thenReturn(agendamentoEntityListMock);
        doReturn(medicoCompletoDTOMock).when(medicoService).getById(any());

        //ACT
        AgendamentoMedicoRelatorioDTO relatorioMedicoByIdRecuperado = agendamentoService.getRelatorioMedicoById(1);
        //ASSERT
        assertNotNull(relatorioMedicoByIdRecuperado);
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveEntrarNoIfRelatorioMedicoById() throws RegraDeNegocioException {
        //SETUP
        List<AgendamentoEntity> agendamentoEntityListMock = List.of();
        MedicoCompletoDTO medicoCompletoDTOMock = getMedicoCompletoDTOMock();

        when(agendamentoRepository.findAllByIdMedico(any())).thenReturn(agendamentoEntityListMock);
        doReturn(medicoCompletoDTOMock).when(medicoService).getById(any());

        //ACT
        AgendamentoMedicoRelatorioDTO relatorioMedicoByIdRecuperado = agendamentoService.getRelatorioMedicoById(1);
        //ASSERT
        assertNotNull(relatorioMedicoByIdRecuperado);
    }

    @Test
    public void findAllPaginado(){
        //SETUP
        Pageable solicitacao = PageRequest.of(0, 10);
        PageImpl<AgendamentoEntity> agendamentoEntities = new PageImpl<>(List.of(), solicitacao, 1);

        Mockito.when(agendamentoRepository.findAll(solicitacao)).thenReturn(agendamentoEntities);

        //ACT
        PageDTO<AgendamentoDTO> agendamentoDTOPageDTO = agendamentoService.findAllPaginado(0, 10);

        //ASSERT
        assertNotNull(agendamentoDTOPageDTO);
    }

    private AgendamentoEntity getAgendamentoEntityMock() {
        AgendamentoEntity agendamentoEntity = new AgendamentoEntity();
        agendamentoEntity.setValorAgendamento(450.0);
        agendamentoEntity.setIdAgendamento(1);
        agendamentoEntity.setIdCliente(1);
        agendamentoEntity.setIdMedico(1);
        agendamentoEntity.setMedicoEntity(getMedicoEntityMock());
        agendamentoEntity.setClienteEntity(getClienteEntityMock());
        agendamentoEntity.setExame("Um exame");
        agendamentoEntity.setTratamento("Um tratamento");
        agendamentoEntity.setDataHorario(LocalDateTime.of(2023, 05, 12, 20, 15));
        return agendamentoEntity;
    }

    public static AgendamentoClienteRelatorioDTO getAgendamentoClienteRelatorioDTOMock() {
        AgendamentoClienteRelatorioDTO agendamentoClienteRelatorioDTO = new AgendamentoClienteRelatorioDTO();
        agendamentoClienteRelatorioDTO.setNome("Carlos");
        agendamentoClienteRelatorioDTO.setCpf("12345678910");
        agendamentoClienteRelatorioDTO.setContatos("12345678");
        agendamentoClienteRelatorioDTO.setIdUsuario(1);
        agendamentoClienteRelatorioDTO.setEmail("Carlos@gmail.com");
        agendamentoClienteRelatorioDTO.setNumero(523);
        agendamentoClienteRelatorioDTO.setIdCliente(1);
        agendamentoClienteRelatorioDTO.setIdConvenio(1);
        agendamentoClienteRelatorioDTO.setCadastroOrgaoRegulador("123456");
        agendamentoClienteRelatorioDTO.setTaxaAbatimento(20.0);
        agendamentoClienteRelatorioDTO.setNomeCargo("ROLE_CLIENTE");
        agendamentoClienteRelatorioDTO.setAgendamentoDTOList(List.of(getAgendamentoDTOMock(), getAgendamentoDTOMock()));
        return agendamentoClienteRelatorioDTO;
    }

    public static SolicitacaoEntity getSolicitacaoEntityMock(){
        SolicitacaoEntity solicitacaoEntity = new SolicitacaoEntity();
        solicitacaoEntity.setStatusSolicitacao(StatusSolicitacao.APROVADA);
        solicitacaoEntity.setIdCliente(1);
        solicitacaoEntity.setDataHorario(LocalDateTime.now());
        solicitacaoEntity.setMotivo("abc");
        solicitacaoEntity.setIdMedico(1);
        solicitacaoEntity.setIdSoliciatacao("1");
        return solicitacaoEntity;
    }

    public static AgendamentoCreateDTO getAgendamentoCreateDTOMock() {
        AgendamentoCreateDTO agendamentoCreateDTO = new AgendamentoCreateDTO();
        agendamentoCreateDTO.setTratamento("Um tratamento");
        agendamentoCreateDTO.setExame("Um exame");
        agendamentoCreateDTO.setDataHorario(LocalDateTime.of(2023, 05, 12, 20, 15));
        agendamentoCreateDTO.setIdMedico(1);
        agendamentoCreateDTO.setIdCliente(1);
        return agendamentoCreateDTO;
    }

    public static AgendamentoDTO getAgendamentoDTOMock() {
        AgendamentoDTO agendamentoDTO = new AgendamentoDTO();
        agendamentoDTO.setTratamento("Um tratamento");
        agendamentoDTO.setExame("Um exame");
        agendamentoDTO.setDataHorario(LocalDateTime.of(2023, 05, 12, 20, 15));
        agendamentoDTO.setIdMedico(1);
        agendamentoDTO.setIdCliente(1);
        agendamentoDTO.setIdAgendamento(1);
        agendamentoDTO.setValorAgendamento(450.0);
        return agendamentoDTO;
    }


}