package br.com.dbc.vemser.trabalhofinal.service;

import br.com.dbc.vemser.trabalhofinal.entity.AgendamentoEntity;
import br.com.dbc.vemser.trabalhofinal.entity.UsuarioEntity;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Component
@RequiredArgsConstructor
public class EmailService {

    private final Configuration fmConfiguration;

    @Value("${spring.mail.username}")
    private String from;

    private final JavaMailSender emailSender;

    // USUÁRIO

    public void sendEmailUsuario(UsuarioEntity usuario, TipoEmail tipoEmail, Integer codigo) throws MessagingException, TemplateException, IOException {
        MimeMessageHelper mimeMessageHelper = buildEmailUsuario(usuario.getEmail(), tipoEmail);
        if(tipoEmail == TipoEmail.USUARIO_REDEFINIR_SENHA){
            mimeMessageHelper.setText(getUsuarioTemplateRedefinicao(usuario, codigo), true);
        }else{
            mimeMessageHelper.setText(getUsuarioTemplate(usuario, tipoEmail), true);
        }


        emailSender.send(mimeMessageHelper.getMimeMessage());
    }

    public MimeMessageHelper buildEmailUsuario(String email, TipoEmail tipoEmail) throws MessagingException {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setFrom(from);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject(tipoEmail.getAssunto());
        return mimeMessageHelper;
    }

    public String getUsuarioTemplate(UsuarioEntity usuario, TipoEmail tipo) throws IOException, TemplateException {
        Map<String, Object> dados = new HashMap<>();
        dados.put("email", from);
        dados.put("usuario", usuario);
        Template template;

        switch(tipo){
            case USUARIO_CADASTRO ->
                    template = fmConfiguration.getTemplate("usuario-cadastro.ftl");
            case USUARIO_SENHA_REDEFINIDA ->
                template = fmConfiguration.getTemplate("usuario-senha-redefinida.ftl");
            default ->
                    template = fmConfiguration.getTemplate("usuario-cadastro.ftl");
        }

        return FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
    }

    public String getUsuarioTemplateRedefinicao(UsuarioEntity usuario, Integer codigo) throws IOException, TemplateException {
        Map<String, Object> dados = new HashMap<>();
        dados.put("email", from);
        dados.put("usuario", usuario);
        dados.put("codigo", codigo);
        Template template= fmConfiguration.getTemplate("usuario-redefinir-senha.ftl");

        return FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
    }

    // AGENDAMENTO

    public void sendEmailAgendamento(UsuarioEntity usuario, AgendamentoEntity agendamento, TipoEmail tipoEmail) throws MessagingException, TemplateException, IOException {
        MimeMessageHelper mimeMessageHelper = buildEmailAgendamento(usuario.getEmail(), tipoEmail);
        mimeMessageHelper.setText(getAgendamentoTemplate(agendamento, tipoEmail), true);

        emailSender.send(mimeMessageHelper.getMimeMessage());
    }

    public MimeMessageHelper buildEmailAgendamento(String email, TipoEmail tipoEmail) throws MessagingException {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setFrom(from);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject(tipoEmail.getAssunto());
        return mimeMessageHelper;
    }

    public String getAgendamentoTemplate(AgendamentoEntity agendamento, TipoEmail tipo) throws IOException, TemplateException {
        Template template;
        Map<String, Object> dados = new HashMap<>();
        dados.put("agendamento", agendamento);
        dados.put("email", from);

        switch (tipo){
            case AGENDAMENTO_CRIADO_CLIENTE ->
                    template = fmConfiguration.getTemplate("agendamento-criado-cliente.ftl");

            case AGENDAMENTO_CRIADO_MEDICO ->
                    template = fmConfiguration.getTemplate("agendamento-criado-medico.ftl");

            case AGENDAMENTO_EDITADO_CLIENTE ->
                    template = fmConfiguration.getTemplate("agendamento-editado-cliente.ftl");

            case AGENDAMENTO_EDITADO_MEDICO ->
                    template = fmConfiguration.getTemplate("agendamento-editado-medico.ftl");

            case AGENDAMENTO_CANCELADO_CLIENTE ->
                    template = fmConfiguration.getTemplate("agendamento-cancelado-cliente.ftl");

            case AGENDAMENTO_CANCELADO_MEDICO ->
                    template = fmConfiguration.getTemplate("agendamento-cancelado-medico.ftl");
            default ->
                    template = fmConfiguration.getTemplate("agendamento-criado-cliente.ftl");
        }

        return FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
    }

}
