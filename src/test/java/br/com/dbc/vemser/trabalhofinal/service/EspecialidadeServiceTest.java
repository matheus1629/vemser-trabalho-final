package br.com.dbc.vemser.trabalhofinal.service;


import br.com.dbc.vemser.trabalhofinal.dto.PageDTO;
import br.com.dbc.vemser.trabalhofinal.dto.especialidade.EspecialidadeDTO;
import br.com.dbc.vemser.trabalhofinal.entity.EspecialidadeEntity;
import br.com.dbc.vemser.trabalhofinal.repository.EspecialidadeRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Tag;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EspecialidadeServiceTest {

    @Spy
    @InjectMocks
    private EspecialidadeService especialidadeService;
    @Mock
    private EspecialidadeRepository especialidadeRepository;

    private ObjectMapper objectMapper = new ObjectMapper();


    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(especialidadeService, "objectMapper", objectMapper);
    }

//    @Test
//    public void deveListarPaginado() {
//        //SETUP
//        Pageable solicitacaoPaginaMock = (Pageable) new org.springdoc.core.converters.models.Pageable(any(), any(), List.of(any()));
//
//        Page<EspecialidadeEntity> especialidadeMock = new PageImpl<>(any());
//
//        when(PageRequest.of(any(),any())).thenReturn((PageRequest) solicitacaoPaginaMock);
//        when(especialidadeRepository.findAll(solicitacaoPaginaMock)).thenReturn(especialidadeMock);
//        //ACT
//        PageDTO<EspecialidadeDTO> listRecuperada = especialidadeService.list(any(), any());
//        //ASSERT
//
//    }

    @Test
    public void deveRecuperarPeloId() {
        //ACT
    }
}
