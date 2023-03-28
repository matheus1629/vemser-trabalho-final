package br.com.dbc.vemser.trabalhofinal.service;

import br.com.dbc.vemser.trabalhofinal.dto.agendamento.SolicitacaoCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.agendamento.SolicitacaoDTO;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SolicitacaoService {

    private final SolicitacaoReposiroty solicitacaoReposiroty;
    private final ClienteService clienteService;
    public SolicitacaoDTO create(SolicitacaoCreateDTO solicitacaoCreateDTO) throws RegraDeNegocioException {
        solicitacaoCreateDTO.setIdCliente(clienteService.recuperarCliente().getIdCliente());
        return solicitacaoReposiroty.create(solicitacaoCreateDTO);
    }

}
