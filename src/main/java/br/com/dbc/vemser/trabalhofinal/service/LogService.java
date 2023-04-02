package br.com.dbc.vemser.trabalhofinal.service;

import br.com.dbc.vemser.trabalhofinal.dto.log.LogCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.log.LogDTO;
import br.com.dbc.vemser.trabalhofinal.entity.LogEntity;
import br.com.dbc.vemser.trabalhofinal.entity.TipoLog;
import br.com.dbc.vemser.trabalhofinal.repository.LogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LogService {

    private final LogRepository logRepository;
    private final ObjectMapper objectMapper;

    public void salvarLog(LogCreateDTO logCreateDTO) {
        LogEntity logEntity = objectMapper.convertValue(logCreateDTO, LogEntity.class);
        logEntity.setIdSolicitacao(logCreateDTO.getIdSolicitacao());
        logEntity.setIdAgendamento(logCreateDTO.getIdAgendamento());
        logEntity.setIdUsuario(logCreateDTO.getIdUsuario());
        logEntity.setDataHora(LocalDateTime.now());
        logEntity.setTipoLog(logCreateDTO.getTipoLog());
        logRepository.save(logEntity);
    }


    public List<LogDTO> listLogsByIdSolicitacao(Integer idSolicitacao) {
        return logRepository.findAllByIdSolicitacao(idSolicitacao).stream().map(log -> objectMapper.convertValue(log, LogDTO.class)).collect(Collectors.toList());
    }

    public List<LogDTO> listLogsByIdAgendamento(Integer idAgendamento) {
        return logRepository.findAllByIdAgendamento(idAgendamento).stream().map(log -> objectMapper.convertValue(log, LogDTO.class)).collect(Collectors.toList());
    }

    public List<LogDTO> listLogsByIdUsuario(Integer idUsuario) {
        return logRepository.findAllByIdUsuario(idUsuario).stream().map(log -> objectMapper.convertValue(log, LogDTO.class)).collect(Collectors.toList());
    }

    public List<LogDTO> listLogsByData(LocalDate localDate) {
        return logRepository.findByDataHora(localDate).stream().map(log -> objectMapper.convertValue(log, LogDTO.class)).collect(Collectors.toList());
    }

    public List<LogDTO> listLogsByTipoLog(TipoLog tipoLog) {
        return logRepository.findAllByTipoLog(tipoLog).stream().map(log -> objectMapper.convertValue(log, LogDTO.class)).collect(Collectors.toList());
    }
}
