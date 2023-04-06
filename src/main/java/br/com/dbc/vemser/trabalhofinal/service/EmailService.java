package br.com.dbc.vemser.trabalhofinal.service;

import br.com.dbc.vemser.trabalhofinal.dto.email.AgendamentoEmailDTO;
import br.com.dbc.vemser.trabalhofinal.dto.email.ParticaoKafka;
import br.com.dbc.vemser.trabalhofinal.dto.email.SolicitacaoEmailDTO;
import br.com.dbc.vemser.trabalhofinal.dto.email.UsuarioEmailDTO;
import br.com.dbc.vemser.trabalhofinal.entity.AgendamentoEntity;
import br.com.dbc.vemser.trabalhofinal.entity.SolicitacaoEntity;
import br.com.dbc.vemser.trabalhofinal.entity.TipoEmail;
import br.com.dbc.vemser.trabalhofinal.entity.UsuarioEntity;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class EmailService {

    private final UsuarioService usuarioService;
    private final MedicoService medicoService;
    private final ClienteService clienteService;
    private final ProducerService producerService;


    public void producerAgendamentoEmail(AgendamentoEntity agendamentoEntity, TipoEmail tipoEmail) throws RegraDeNegocioException, JsonProcessingException {

        AgendamentoEmailDTO agendamentoEmailDTO = new AgendamentoEmailDTO(
                agendamentoEntity.getIdAgendamento(),
                agendamentoEntity.getDataHorario(),
                usuarioService.getUsuario(medicoService.getMedico(agendamentoEntity.getIdMedico()).getIdUsuario()).getNome(),
                usuarioService.getUsuario(clienteService.getCliente(agendamentoEntity.getIdCliente()).getIdUsuario()).getNome(),
                null,
                tipoEmail);

        if (tipoEmail.equals(TipoEmail.AGENDAMENTO_CRIADO_CLIENTE) || tipoEmail.equals(TipoEmail.AGENDAMENTO_EDITADO_CLIENTE) || tipoEmail.equals(TipoEmail.AGENDAMENTO_CANCELADO_CLIENTE)) {
            agendamentoEmailDTO.setEmail(agendamentoEntity.getClienteEntity().getUsuarioEntity().getEmail());
        } else {
            agendamentoEmailDTO.setEmail(agendamentoEntity.getMedicoEntity().getUsuarioEntity().getEmail());
        }

        producerService.send(agendamentoEmailDTO, ParticaoKafka.SEND_EMAIL_AGENDAMENTO);
    }

    public void producerSolicitacaoEmail(SolicitacaoEntity solicitacaoEntity, TipoEmail tipoEmail) throws RegraDeNegocioException, JsonProcessingException {
        SolicitacaoEmailDTO emailSolicitacaoDTO = new SolicitacaoEmailDTO(
                solicitacaoEntity.getIdSoliciatacao(),
                usuarioService.getUsuario(medicoService.getMedico(solicitacaoEntity.getIdMedico()).getUsuarioEntity().getIdUsuario()).getNome(),
                usuarioService.getUsuario(clienteService.getCliente(solicitacaoEntity.getIdCliente()).getUsuarioEntity().getIdUsuario()).getNome(),
                usuarioService.getUsuario(clienteService.getCliente(solicitacaoEntity.getIdCliente()).getUsuarioEntity().getIdUsuario()).getEmail(),
                solicitacaoEntity.getDataHorario(),
                solicitacaoEntity.getStatusSolicitacao(),
                tipoEmail);

        producerService.send(emailSolicitacaoDTO, ParticaoKafka.SEND_EMAIL_CLIENTE);
    }

    public void producerUsuarioEmail(UsuarioEntity usuarioEntity, TipoEmail tipoEmail, Integer codigoRecuperacao) throws JsonProcessingException {
        UsuarioEmailDTO usuarioEmailDTO = new UsuarioEmailDTO(
                usuarioEntity.getNome(),
                usuarioEntity.getEmail(),
                null,
                tipoEmail);

        if (tipoEmail.equals(TipoEmail.USUARIO_REDEFINIR_SENHA)) {
            usuarioEmailDTO.setCodigoRecuperacao(codigoRecuperacao);
        }

        producerService.send(usuarioEmailDTO, ParticaoKafka.SEND_EMAIL_USUARIO);
    }


}
