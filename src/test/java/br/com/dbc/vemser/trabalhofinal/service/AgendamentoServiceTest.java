package br.com.dbc.vemser.trabalhofinal.service;


import br.com.dbc.vemser.trabalhofinal.dto.agendamento.AgendamentoCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.agendamento.AgendamentoDTO;
import br.com.dbc.vemser.trabalhofinal.entity.AgendamentoEntity;
import br.com.dbc.vemser.trabalhofinal.entity.ClienteEntity;
import br.com.dbc.vemser.trabalhofinal.entity.MedicoEntity;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.repository.AgendamentoRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;

import static br.com.dbc.vemser.trabalhofinal.service.ClienteServiceTest.getClienteEntityMock;
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
    private EmailService emailService;

    private ObjectMapper objectMapper = new ObjectMapper();
    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(agendamentoService, "objectMapper", objectMapper);
    }

    @Test
    public void deveAdicionarAgendamento() throws RegraDeNegocioException {
        //SETUP
        AgendamentoCreateDTO agendamentoCreateDTOMock = getAgendamentoCreateDTOMock();
        ClienteEntity clienteEntityMock = getClienteEntityMock();
        MedicoEntity medicoEntityMock = getMedicoEntityMock();
        AgendamentoEntity agendamentoEntityMock = getAgendamentoEntityMock();
        AgendamentoDTO agendamentoDTOMock = getAgendamentoDTOMock();

        doReturn(clienteEntityMock).when(clienteService).getCliente(any());
        doReturn(medicoEntityMock).when(medicoService).getMedico(any());
        when(agendamentoRepository.save(any())).thenReturn(agendamentoEntityMock);

        //ACT
        AgendamentoDTO agendamentoAdicionado = agendamentoService.adicionar(agendamentoCreateDTOMock);
        //ASSERT
        verify(agendamentoRepository, times(1)).save(any());
        assertNotNull(agendamentoAdicionado);
        assertEquals(agendamentoDTOMock, agendamentoAdicionado); // <todo verificar poeque id retorna null
    }

    private AgendamentoEntity getAgendamentoEntityMock() {
        AgendamentoEntity agendamentoEntity = new AgendamentoEntity();
        agendamentoEntity.setValorAgendamento(100.0);
        agendamentoEntity.setIdAgendamento(1);
        agendamentoEntity.setMedicoEntity(getMedicoEntityMock());
        agendamentoEntity.setClienteEntity(getClienteEntityMock());
        agendamentoEntity.setExame("Um exame");
        agendamentoEntity.setTratamento("Um tratamento");
        agendamentoEntity.setDataHorario(LocalDateTime.of(2023, 05, 12, 20, 15));
        return agendamentoEntity;
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