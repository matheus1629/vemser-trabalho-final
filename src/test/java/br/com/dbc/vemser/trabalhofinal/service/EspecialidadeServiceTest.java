package br.com.dbc.vemser.trabalhofinal.service;


import br.com.dbc.vemser.trabalhofinal.dto.PageDTO;
import br.com.dbc.vemser.trabalhofinal.dto.especialidade.EspecialidadeCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.especialidade.EspecialidadeDTO;
import br.com.dbc.vemser.trabalhofinal.entity.ClienteEntity;
import br.com.dbc.vemser.trabalhofinal.entity.EspecialidadeEntity;
import br.com.dbc.vemser.trabalhofinal.entity.MedicoEntity;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.repository.EspecialidadeRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Assert;
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

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static br.com.dbc.vemser.trabalhofinal.service.MedicoServiceTest.getMedicoEntityMock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

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

    @Test
    public void deveListarPaginado() {
        //SETUP
        Pageable solicitacaoPaginaMock = (Pageable) new org.springdoc.core.converters.models.Pageable(any(), any(), List.of(any()));
//
        Page<EspecialidadeEntity> especialidadeMock = new PageImpl<>(any());
//
        when(PageRequest.of(any(),any())).thenReturn((PageRequest) solicitacaoPaginaMock);
        when(especialidadeRepository.findAll(solicitacaoPaginaMock)).thenReturn(especialidadeMock);

        //ACT
        PageDTO<EspecialidadeDTO> listRecuperada = especialidadeService.list(any(), any());

        //ASSERT

    }

    @Test
    public void deveRecuperarPeloId() throws RegraDeNegocioException {
        //SETUP
        EspecialidadeEntity especialidadeEntityMock = getEspecialidadeEntityMock();
        EspecialidadeDTO especialidadeDTOMock = getEspecialidadeDTOMock();
        doReturn(especialidadeEntityMock).when(especialidadeService).getEspecialidade(any());

        //ACT
        EspecialidadeDTO especialidadeRecuperada = especialidadeService.getById(any());

        //ASSERT
        assertNotNull(especialidadeRecuperada);
        assertEquals(especialidadeDTOMock, especialidadeRecuperada);
    }

    @Test
    public void deveAdicionarEspecialidade() throws RegraDeNegocioException {
        //SETUP
        EspecialidadeCreateDTO especialidadeCreateDTOMock = getEspecialidadeCreateDTOMock();
        EspecialidadeEntity especialidadeEntityMock = getEspecialidadeEntityMock();
        EspecialidadeDTO especialidadeDTOMock = getEspecialidadeDTOMock();

        when(especialidadeRepository.save(any())).thenReturn(especialidadeEntityMock);
        //ACT
        EspecialidadeDTO especialidadeCriada = especialidadeService.adicionar(especialidadeCreateDTOMock);
        //ASSERT
        verify(especialidadeRepository, times(1)).save(any());
        assertNotNull(especialidadeCriada);
        assertEquals(especialidadeDTOMock.getNomeEspecialidade(), especialidadeCriada.getNomeEspecialidade());
        assertEquals(especialidadeDTOMock.getValor(), especialidadeCriada.getValor());
        assertEquals(especialidadeDTOMock.getIdEspecialidade(), especialidadeCriada.getIdEspecialidade()); //todo não está retornando o id
    }

    @Test
    public void deveEditarEspecialidade() throws RegraDeNegocioException {
        //SETUP
        EspecialidadeCreateDTO especialidadeCreateDTOMock = getEspecialidadeCreateDTOMock();
        EspecialidadeEntity especialidadeEntityMock = getEspecialidadeEntityMock();
        EspecialidadeDTO especialidadeDTOMock = getEspecialidadeDTOMock();

        doReturn(especialidadeEntityMock).when(especialidadeService).getEspecialidade(any());

        //ACT
        EspecialidadeDTO especialidadeEditada = especialidadeService.editar(1, getEspecialidadeCreateDTOMock());
        //ASSERT
        verify(especialidadeRepository, times(1)).save(especialidadeEntityMock);
        assertNotNull(especialidadeEditada);
        assertEquals(especialidadeDTOMock, especialidadeEditada);
    }

    @Test
    public void deveRemover() throws RegraDeNegocioException {
        //SETUP
        EspecialidadeEntity especialidadeEntityMock = getEspecialidadeEntityMock();

        doReturn(especialidadeEntityMock).when(especialidadeService).getEspecialidade(1);

        //ACT
        especialidadeService.remover(1);
        //ASSERT
        verify(especialidadeRepository, times(1)).deleteById(1);
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveRetornarException() throws RegraDeNegocioException {
        //SETUP
        EspecialidadeEntity especialidadeEntityMock = getEspecialidadeEntityComMedicoEntitiesMock();

        doReturn(especialidadeEntityMock).when(especialidadeService).getEspecialidade(1);

        //ACT
        especialidadeService.remover(1);
    }

    @Test
    public void deveRetornarEspecialidadeEntityPeloId() throws RegraDeNegocioException {
        //SETUP
        EspecialidadeEntity especialidadeEntityMock = getEspecialidadeEntityMock();

        when(especialidadeRepository.findById(any())).thenReturn(Optional.of(especialidadeEntityMock));

        //ACT
        EspecialidadeEntity especialidadeRecuperada = especialidadeService.getEspecialidade(1);

        //ASSERT
        assertEquals(especialidadeEntityMock, especialidadeRecuperada);

    }

    private static EspecialidadeEntity getEspecialidadeEntityComMedicoEntitiesMock() {
        Set<MedicoEntity> medicoEntities = Set.of(getMedicoEntityMock(), getMedicoEntityMock());
        EspecialidadeEntity especialidadeEntity = new EspecialidadeEntity();
        especialidadeEntity.setIdEspecialidade(1);
        especialidadeEntity.setValor(100.0);
        especialidadeEntity.setNomeEspecialidade("Cardiologista");
        especialidadeEntity.setMedicoEntities(medicoEntities);
        return especialidadeEntity;
    }
    private static EspecialidadeEntity getEspecialidadeEntityMock() {
        EspecialidadeEntity especialidadeEntity = new EspecialidadeEntity();
        especialidadeEntity.setIdEspecialidade(1);
        especialidadeEntity.setValor(100.0);
        especialidadeEntity.setNomeEspecialidade("Cardiologista");
        especialidadeEntity.setMedicoEntities(Collections.emptySet());
        return especialidadeEntity;
    }

    public static EspecialidadeDTO getEspecialidadeDTOMock() {
        EspecialidadeDTO especialidadeDTO = new EspecialidadeDTO();
        especialidadeDTO.setIdEspecialidade(1);
        especialidadeDTO.setNomeEspecialidade("Cardiologista");
        especialidadeDTO.setValor(100.0);
        return especialidadeDTO;
    }




    public static EspecialidadeCreateDTO getEspecialidadeCreateDTOMock() {
        EspecialidadeCreateDTO especialidadeCreateDTO = new EspecialidadeDTO();
        especialidadeCreateDTO.setNomeEspecialidade("Cardiologista");
        especialidadeCreateDTO.setValor(100.0);
        return especialidadeCreateDTO;
    }
}
