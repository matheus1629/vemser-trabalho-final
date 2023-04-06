package br.com.dbc.vemser.trabalhofinal.service;

import br.com.dbc.vemser.trabalhofinal.dto.email.AgendamentoEmailDTO;
import br.com.dbc.vemser.trabalhofinal.dto.email.SolicitacaoEmailDTO;
import br.com.dbc.vemser.trabalhofinal.dto.email.ParticaoKafka;
import br.com.dbc.vemser.trabalhofinal.entity.AgendamentoEntity;
import br.com.dbc.vemser.trabalhofinal.entity.SolicitacaoEntity;
import br.com.dbc.vemser.trabalhofinal.entity.TipoEmail;
import br.com.dbc.vemser.trabalhofinal.entity.UsuarioEntity;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import com.fasterxml.jackson.core.JsonProcessingException;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.IOException;

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
                usuarioService.getUsuario(agendamentoEntity.getIdMedico()).getNome(),
                usuarioService.getUsuario(agendamentoEntity.getIdCliente()).getNome(),
                null,
                tipoEmail);

        if (tipoEmail.equals(TipoEmail.AGENDAMENTO_CRIADO_CLIENTE) || tipoEmail.equals(TipoEmail.AGENDAMENTO_EDITADO_CLIENTE) || tipoEmail.equals(TipoEmail.AGENDAMENTO_CANCELADO_CLIENTE)) {
            agendamentoEmailDTO.setEmail(agendamentoEntity.getClienteEntity().getUsuarioEntity().getEmail());
        } else {
            agendamentoEmailDTO.setEmail(agendamentoEntity.getMedicoEntity().getUsuarioEntity().getEmail());
        }

        producerService.send(agendamentoEmailDTO, ParticaoKafka.SEND_EMAIL_AGENDAMENTO);
    }

    public void producerSolicitacao(SolicitacaoEntity solicitacaoEntity, TipoEmail tipoEmail) throws RegraDeNegocioException, JsonProcessingException {
        SolicitacaoEmailDTO emailSolicitacaoDTO = new SolicitacaoEmailDTO(
                solicitacaoEntity.getIdSoliciatacao(),
                usuarioService.getUsuario(medicoService.getMedico(solicitacaoEntity.getIdMedico()).getIdUsuario()).getNome(),
                usuarioService.getUsuario(clienteService.getCliente(solicitacaoEntity.getIdCliente()).getIdUsuario()).getNome(),
                usuarioService.getUsuario(solicitacaoEntity.getIdCliente()).getEmail(),
                solicitacaoEntity.getDataHorario(),
                solicitacaoEntity.getStatusSolicitacao(),
                tipoEmail);

        producerService.send(emailSolicitacaoDTO, ParticaoKafka.SEND_EMAIL_CLIENTE);

    }

//    // USUÁRIO
//    public void sendEmailUsuario(UsuarioEntity usuario, TipoEmail tipoEmail, Integer codigo) throws MessagingException, TemplateException, IOException {
//
//        MimeMessageHelper mimeMessageHelper = buildEmail(usuario.getEmail(), tipoEmail);
//        if (tipoEmail == TipoEmail.USUARIO_REDEFINIR_SENHA) {
//            mimeMessageHelper.setText(getUsuarioTemplateRedefinicao(usuario, codigo), true);
//        } else {
//            mimeMessageHelper.setText(getUsuarioTemplate(usuario, tipoEmail), true);
//        }
//
//        emailSender.send(mimeMessageHelper.getMimeMessage());
//    }
//
//    // AGENDAMENTO
//    public void sendEmailAgendamento(UsuarioEntity usuario, AgendamentoEntity agendamento, TipoEmail tipoEmail) throws MessagingException, TemplateException, IOException {
//        MimeMessageHelper mimeMessageHelper = buildEmail(usuario.getEmail(), tipoEmail);
//        mimeMessageHelper.setText(getAgendamentoTemplate(agendamento, tipoEmail), true);
//
//        emailSender.send(mimeMessageHelper.getMimeMessage());
//    }
//
//    // CLIENTE
//    public void sendEmailCliente(UsuarioEntity usuario, TipoEmail tipoEmail, String codigo) throws MessagingException, TemplateException, IOException {
//        MimeMessageHelper mimeMessageHelper = buildEmail(usuario.getEmail(), tipoEmail);
//        mimeMessageHelper.setText(getClienteTemplateSolicitacao(usuario, codigo, tipoEmail), true);
//
//        emailSender.send(mimeMessageHelper.getMimeMessage());
//    }


}
