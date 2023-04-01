package br.com.dbc.vemser.trabalhofinal.service;

import br.com.dbc.vemser.trabalhofinal.dto.solicitacao.SolicitacaoCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.solicitacao.SolicitacaoDTO;
import br.com.dbc.vemser.trabalhofinal.dto.solicitacao.SolicitacaoPesquisaDTO;
import br.com.dbc.vemser.trabalhofinal.entity.SolicitacaoEntity;
import br.com.dbc.vemser.trabalhofinal.entity.StatusSolicitacao;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.repository.SolicitacaoReposiroty;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SolicitacaoService {
    private final SolicitacaoReposiroty solicitacaoReposiroty;
    private final ClienteService clienteService;

    public SolicitacaoDTO create(SolicitacaoCreateDTO solicitacaoCreateDTO) throws RegraDeNegocioException {
        solicitacaoCreateDTO.setIdCliente(clienteService.recuperarCliente().getIdCliente());
        solicitacaoCreateDTO.setStatusSolicitacao(StatusSolicitacao.PENDENTE);

        var solicitacaoEntity = new SolicitacaoEntity();
        BeanUtils.copyProperties(solicitacaoCreateDTO, solicitacaoEntity);

       solicitacaoReposiroty.save(solicitacaoEntity);

        var solicitacaoDTO = new SolicitacaoDTO();
        BeanUtils.copyProperties(solicitacaoEntity, solicitacaoDTO);

        return solicitacaoDTO;

    }

    public SolicitacaoDTO list(@Valid SolicitacaoPesquisaDTO solicitacaoPesquisaDTO) {
        if (solicitacaoPesquisaDTO.getDataHoraInicio() == null) {
            solicitacaoPesquisaDTO.setDataHoraInicio(LocalDateTime.of(2000, 01, 01, 00, 00));
        }

        if (solicitacaoPesquisaDTO.getDataHoraFim() == null) {
            solicitacaoPesquisaDTO.setDataHoraFim(LocalDateTime.of(3000, 01, 01, 00, 00));
        }

        if (solicitacaoPesquisaDTO.getIdMedico() == null && solicitacaoPesquisaDTO.getIdCliente() == null) {
            solicitacaoReposiroty.findIdMedicoIdClienteIsNull(solicitacaoPesquisaDTO);
        }
    }


}
