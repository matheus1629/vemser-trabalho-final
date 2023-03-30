package br.com.dbc.vemser.trabalhofinal.service;


import br.com.dbc.vemser.trabalhofinal.dto.convenio.ConvenioDTO;
import br.com.dbc.vemser.trabalhofinal.dto.medico.MedicoCompletoDTO;
import br.com.dbc.vemser.trabalhofinal.dto.medico.MedicoCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.medico.MedicoDTO;
import br.com.dbc.vemser.trabalhofinal.entity.*;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.repository.ConvenioRepository;
import br.com.dbc.vemser.trabalhofinal.repository.MedicoRepository;
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
import org.springframework.test.util.ReflectionTestUtils;

import javax.validation.constraints.NotNull;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MedicoServiceTest {
    @Spy
    @InjectMocks
    private MedicoService medicoService;
    private ObjectMapper objectMapper = new ObjectMapper();
    @Mock
    private MedicoRepository medicoRepository;

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(medicoService, "objectMapper", objectMapper);
    }

    @Test // deveCriarComSucesso
    public void deveCriarComSucesso() throws RegraDeNegocioException {
        // declaração de variaveis (SETUP)
        MedicoEntity medicoEntity = getMedicoEntityMock();
        MedicoCreateDTO medicoDTO = medicoCreateDTO();

        when(medicoRepository.save(any())).thenReturn(medicoEntity);
        // ação (ACT)
//        PessoaService pessoaService = new PessoaService();
        MedicoCompletoDTO medicoRetornado = medicoService.adicionar(medicoDTO);

        // verificar se deu certo / afirmativa  (ASSERT)
        assertNotNull(medicoRetornado);
//        assertEquals(medicoEntity.getTaxaAbatimento(), convenioRetornado.getTaxaAbatimento());
//        assertEquals(medicoEntity.getCadastroOrgaoRegulador(), convenioRetornado.getCadastroOrgaoRegulador());
//        assertEquals(1,convenioRetornado.getIdConvenio());
    }

    @Test
    public void deveRetornarMedicoPorId() throws RegraDeNegocioException {
        // declaração de variaveis (SETUP)
        MedicoEntity medicoEntity = getMedicoEntityMock();
        MedicoCompletoDTO medicoCompletoDTO = getMedicoEntityMockDTO();
        when(medicoRepository.findById(any())).thenReturn(Optional.of(medicoEntity));
//        Mockito.doReturn(convenioMockadaDoBanco).when(convenioService).getConvenio(anyInt());
        // ACT
        MedicoCompletoDTO resultado = medicoService.getById(1);
        // Assert
        assertNotNull(resultado);
        assertEquals(medicoCompletoDTO,resultado);
    }

    @NotNull
    private static MedicoEntity getMedicoEntityMock() {
        MedicoEntity medicoMockadaDoBanco = new MedicoEntity();
        medicoMockadaDoBanco.setIdMedico(1);
        medicoMockadaDoBanco.setCrm("123456");
        medicoMockadaDoBanco.setUsuarioEntity(getUsuarioEntityMock());
        medicoMockadaDoBanco.setEspecialidadeEntity(getEspecialidadeEntityMock());
        return medicoMockadaDoBanco;
    }

    @NotNull
    private static MedicoCompletoDTO getMedicoEntityMockDTO() {
        MedicoCompletoDTO medicoMockadaDoBancoDTO = new MedicoCompletoDTO();
        medicoMockadaDoBancoDTO.setIdMedico(1);
        medicoMockadaDoBancoDTO.setCrm("123456");
        medicoMockadaDoBancoDTO.setCep("49042140");
        return medicoMockadaDoBancoDTO;
    }

    @NotNull
    private static MedicoCreateDTO medicoCreateDTO() {
        MedicoCreateDTO medicoMockadaDoBancoDTO = new MedicoCreateDTO();
        medicoMockadaDoBancoDTO.setCrm("123456");
        medicoMockadaDoBancoDTO.setCep("49042140");
        return medicoMockadaDoBancoDTO;
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

    private static EspecialidadeEntity getEspecialidadeEntityMock() {
        EspecialidadeEntity especialidadeEntity = new EspecialidadeEntity();
        especialidadeEntity.setIdEspecialidade(1);
        especialidadeEntity.setNomeEspecialidade("Cardiologista");
        especialidadeEntity.setValor(500);
        return especialidadeEntity;
    }
}
