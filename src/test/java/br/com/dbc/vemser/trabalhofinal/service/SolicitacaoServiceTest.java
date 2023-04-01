package br.com.dbc.vemser.trabalhofinal.service;

import br.com.dbc.vemser.trabalhofinal.dto.solicitacao.SolicitacaoCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.solicitacao.SolicitacaoDTO;
import br.com.dbc.vemser.trabalhofinal.dto.cliente.ClienteCompletoDTO;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.repository.SolicitacaoReposiroty;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import javax.validation.constraints.NotNull;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class SolicitacaoServiceTest {

    @Spy
    @InjectMocks
    private SolicitacaoService solicitacaoService;
    @Mock
    private SolicitacaoReposiroty solicitacaoReposiroty;
    @Mock
    private ClienteService clienteService;

    @Test
    public void deveCriarSolicitacao() throws RegraDeNegocioException {
        //SETUP
        SolicitacaoCreateDTO solicitacaoCreateDTOMock = getSolicitacaoCreateDTOMock();
        ClienteCompletoDTO clienteCompletoDTOMock = getClienteCompletoDTOMock();
        doReturn(clienteCompletoDTOMock).when(clienteService).recuperarCliente();
        SolicitacaoDTO solicitacaoDTOMockEsperada = getSolicitacaoDTODoBancoMock();

        //ACT
        SolicitacaoDTO solicitacaoDTOMockRetornada = solicitacaoService.create(solicitacaoCreateDTOMock);

        //ASSERT
        assertNotNull(solicitacaoDTOMockRetornada);
        assertEquals(solicitacaoDTOMockEsperada, solicitacaoDTOMockRetornada);
    }


    @NotNull
    public static SolicitacaoDTO getSolicitacaoDTODoBancoMock(){
        SolicitacaoDTO solicitacaoDTOMock = new SolicitacaoDTO();
//        solicitacaoDTOMock.setIdSoliciatacao("1");
        solicitacaoDTOMock.setIdCliente(1);
        solicitacaoDTOMock.setIdMedico(1);
        solicitacaoDTOMock.setMotivo("Dor no braço");
        solicitacaoDTOMock.setEspecialidade("Cardiologista");
        solicitacaoDTOMock.setDataHora(LocalDateTime.of(2023, 5, 15, 14, 0));
        return solicitacaoDTOMock;
    }

    @NotNull
    public static SolicitacaoCreateDTO getSolicitacaoCreateDTOMock(){
        SolicitacaoCreateDTO solicitacaoCreateDTOMock = new SolicitacaoCreateDTO();
        solicitacaoCreateDTOMock.setIdCliente(1);
        solicitacaoCreateDTOMock.setIdMedico(1);
        solicitacaoCreateDTOMock.setMotivo("Dor no braço");
        solicitacaoCreateDTOMock.setEspecialidade("Cardiologista");
        solicitacaoCreateDTOMock.setDataHora(LocalDateTime.of(2023, 5, 15, 14, 0));
        return solicitacaoCreateDTOMock;
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


}
