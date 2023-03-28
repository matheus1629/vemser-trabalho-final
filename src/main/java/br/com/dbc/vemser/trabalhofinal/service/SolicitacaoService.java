package br.com.dbc.vemser.trabalhofinal.service;

import br.com.dbc.vemser.trabalhofinal.dto.agendamento.SolicitacaoCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.agendamento.SolicitacaoDTO;
import br.com.dbc.vemser.trabalhofinal.entity.SolicitacaoEntity;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.repository.SolicitacaoReposiroty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SolicitacaoService {

    private final SolicitacaoReposiroty solicitacaoReposiroty;
    private final ClienteService clienteService;
    private final ObjectMapper objectMapper;
    public SolicitacaoDTO create(SolicitacaoCreateDTO solicitacaoCreateDTO) throws RegraDeNegocioException {
        solicitacaoCreateDTO.setIdCliente(clienteService.recuperarCliente().getIdCliente());

        var solicitacaoEntity = new SolicitacaoEntity();
        BeanUtils.copyProperties(solicitacaoCreateDTO, solicitacaoEntity);

        solicitacaoReposiroty.save(solicitacaoEntity);

        var solicitacaoDTO = new SolicitacaoDTO();
        BeanUtils.copyProperties(solicitacaoEntity, solicitacaoDTO);

        return solicitacaoDTO;

    }

}
