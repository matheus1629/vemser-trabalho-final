package br.com.dbc.vemser.trabalhofinal.service;

import br.com.dbc.vemser.trabalhofinal.dto.log.LogCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.log.LogDTO;
import br.com.dbc.vemser.trabalhofinal.entity.LogEntity;
import br.com.dbc.vemser.trabalhofinal.entity.TipoLog;
import br.com.dbc.vemser.trabalhofinal.repository.LogRepository;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LogServiceTest {

    @Spy
    @InjectMocks
    private LogService logService;
    @Mock
    private LogRepository logRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(logService, "objectMapper", objectMapper);
    }

    @Test
    public void deveSalvarLog() {
        //setup
        LogCreateDTO logCreateDTO = getLogCreateDTOMock();
        //act
        logService.salvarLog(logCreateDTO);
        //asserts
        verify(logService, times(1)).salvarLog(logCreateDTO);
    }

    @Test
    public void deveListLogsByIdSolicitacao(){
        //setup
        List<LogEntity> logEntities = List.of(getLogEntityMock());
        when(logRepository.findAllByIdSolicitacao(any())).thenReturn(logEntities);
        //act
        List<LogDTO> logDTOList = logService.listLogsByIdSolicitacao("1");
        //asserts
        Assert.assertNotNull(logDTOList);
    }

    @Test
    public void deveListLogsByIdAgendamento(){
        //setup
        List<LogEntity> logEntities = List.of(getLogEntityMock());
        when(logRepository.findAllByIdAgendamento(any())).thenReturn(logEntities);
        //act
        List<LogDTO> logDTOList = logService.listLogsByIdAgendamento(1);
        //asserts
        Assert.assertNotNull(logDTOList);
    }

    @Test
    public void deveListLogsByIdUsuario(){
        //setup
        List<LogEntity> logEntities = List.of(getLogEntityMock());
        when(logRepository.findAllByIdUsuario(any())).thenReturn(logEntities);
        //act
        List<LogDTO> logDTOList = logService.listLogsByIdUsuario(1);
        //asserts
        Assert.assertNotNull(logDTOList);
    }

    @Test
    public void deveListLogsByData(){
        //setup
        List<LogEntity> logEntities = List.of(getLogEntityMock());
        LocalDate localDate = LocalDate.now();

        when(logRepository.findByDataHoraBetween(any(),any())).thenReturn(logEntities);
        //act
        List<LogDTO> logDTOList = logService.listLogsByData(localDate,localDate);
        //asserts
        Assert.assertNotNull(logDTOList);
    }

    @Test
    public void deveListLogsByTipoLog(){
        //setup
        List<LogEntity> logEntities = List.of(getLogEntityMock());
        when(logRepository.findAllByTipoLog(any())).thenReturn(logEntities);
        //act
        List<LogDTO> logDTOList = logService.listLogsByTipoLog(TipoLog.APROVACAO_SOLICITACAO);
        //asserts
        Assert.assertNotNull(logDTOList);
    }

    public static LogCreateDTO getLogCreateDTOMock(){
        LogCreateDTO logCreateDTO = new LogCreateDTO();
        logCreateDTO.setIdSolicitacao("1");
        logCreateDTO.setIdAgendamento(1);
        logCreateDTO.setIdUsuario(1);
        logCreateDTO.setDataHora(LocalDateTime.now());
        logCreateDTO.setTipoLog(TipoLog.APROVACAO_SOLICITACAO);
        return logCreateDTO;
    }

    public static LogEntity getLogEntityMock(){
        LogEntity logEntity = new LogEntity();
        logEntity.setIdSolicitacao("1");
        logEntity.setIdAgendamento(1);
        logEntity.setIdUsuario(1);
        logEntity.setDataHora(LocalDateTime.now());
        logEntity.setTipoLog(TipoLog.APROVACAO_SOLICITACAO);
        return logEntity;
    }
}
