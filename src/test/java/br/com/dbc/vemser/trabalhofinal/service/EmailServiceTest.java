package br.com.dbc.vemser.trabalhofinal.service;

import br.com.dbc.vemser.trabalhofinal.entity.AgendamentoEntity;
import br.com.dbc.vemser.trabalhofinal.entity.TipoEmail;
import br.com.dbc.vemser.trabalhofinal.entity.UsuarioEntity;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.util.ReflectionTestUtils;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.time.LocalDateTime;

import static br.com.dbc.vemser.trabalhofinal.service.ClienteServiceTest.getClienteEntityMock;
import static br.com.dbc.vemser.trabalhofinal.service.MedicoServiceTest.getMedicoEntityMock;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EmailServiceTest {

    @Spy
    @InjectMocks
    private EmailService emailService;
    @Mock
    private Configuration fmConfiguration;
    @Mock
    private JavaMailSender emailSender;

    @Before
    public void init() {
        ReflectionTestUtils.setField(emailService,"from","thassio@gmail.com");
    }

    @Test
    public void sendEmailUsaurio() throws MessagingException, TemplateException, IOException {
        //setup
        UsuarioEntity usuario = getUsuarioEntityMock();
        TipoEmail tipoEmail = TipoEmail.AGENDAMENTO_CANCELADO_CLIENTE;
        Integer codigo = 123;

        MimeMessage mimeMessage = new MimeMessage((Session)null);
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        Mockito.doReturn(mimeMessageHelper).when(emailService).buildEmailUsuario(any(),any());
        Mockito.doReturn("abc").when(emailService).getUsuarioTemplate(any(),any());
        //act
        emailService.sendEmailUsuario(usuario,tipoEmail,codigo);
        //assert
        Mockito.verify(emailSender,Mockito.times(1)).send(any(MimeMessage.class));
    }

    @Test
    public void sendEmailUsaurioIf() throws MessagingException, TemplateException, IOException {
        //setup
        UsuarioEntity usuario = getUsuarioEntityMock();
        TipoEmail tipoEmail = TipoEmail.USUARIO_REDEFINIR_SENHA;
        Integer codigo = 123;

        MimeMessage mimeMessage = new MimeMessage((Session)null);
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        Mockito.doReturn(mimeMessageHelper).when(emailService).buildEmailUsuario(any(),any());
        Mockito.doReturn("abc").when(emailService).getUsuarioTemplateRedefinicao(any(),any());
        //act
        emailService.sendEmailUsuario(usuario,tipoEmail,codigo);
        //assert
        Mockito.verify(emailSender,Mockito.times(1)).send(any(MimeMessage.class));
    }

    @Test
    public void testarBuildEmailUsuario() throws MessagingException {
        //setup
        String email = "thassio@gmail.com";
        TipoEmail tipoEmail = TipoEmail.USUARIO_CADASTRO;

        Mockito.when(emailSender.createMimeMessage()).thenReturn(new MimeMessage((Session)null));
        //act
        MimeMessageHelper mimeMessageHelper = emailService.buildEmailUsuario(email,tipoEmail);
        //assert
        Assert.assertNotNull(mimeMessageHelper);
    }

    @Test
    public void testarGetUsuarioTemplate() throws TemplateException, IOException {
        //setup
        UsuarioEntity usuario = getUsuarioEntityMock();
        TipoEmail tipoEmail = TipoEmail.USUARIO_CADASTRO;
        Template template = mock(Template.class);

        Mockito.when(fmConfiguration.getTemplate(any())).thenReturn(template);
        //act USUARIO_CADASTRO
        String variavel = emailService.getUsuarioTemplate(usuario,tipoEmail);
        //asserts USUARIO_CADASTRO
        Assert.assertNotNull(variavel);

        //setup USUARIO_SENHA_REDEFINIDA
        tipoEmail = TipoEmail.USUARIO_SENHA_REDEFINIDA;
        //act USUARIO_SENHA_REDEFINIDA
        String variavel2 = emailService.getUsuarioTemplate(usuario,tipoEmail);
        //assert USUARIO_SENHA_REDEFINIDA
        Assert.assertNotNull(variavel2);

        //setup default
        tipoEmail = TipoEmail.AGENDAMENTO_EDITADO_MEDICO;
        //act default
        String variavel3 = emailService.getUsuarioTemplate(usuario,tipoEmail);
        //assert default
        Assert.assertNotNull(variavel3);
    }

    @Test
    public void testarGetUsuarioTemplateRedefinicao() throws IOException, TemplateException{
        //setup
        UsuarioEntity usuario = getUsuarioEntityMock();
        Integer codigo = 123;

        Template template = mock(Template.class);

        Mockito.when(fmConfiguration.getTemplate(any())).thenReturn(template);
        //act
        String variavel = emailService.getUsuarioTemplateRedefinicao(usuario,codigo);
        //assert
        Assert.assertNotNull(variavel);
    }

    @Test
    public void testeSendEmailAgendamento() throws MessagingException, TemplateException, IOException{
        //setup
        UsuarioEntity usuario = getUsuarioEntityMock();
        AgendamentoEntity agendamento = getAgendamentoEntityMock();
        TipoEmail tipoEmail = TipoEmail.USUARIO_CADASTRO;
        Template template = mock(Template.class);

        Mockito.when(fmConfiguration.getTemplate(any())).thenReturn(template);
        MimeMessage mimeMessage = new MimeMessage((Session)null);
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        Mockito.doReturn(mimeMessageHelper).when(emailService).buildEmailAgendamento(any(),any());
        //act
        emailService.sendEmailAgendamento(usuario,agendamento,tipoEmail);
        //asserts
        verify(emailSender,times(1)).send(any(MimeMessage.class));
    }

    @Test
    public void testeBuildEmailAgendamento() throws MessagingException{
        //setup
        String email = "thassio@gmail.com";
        TipoEmail tipoEmail = TipoEmail.USUARIO_CADASTRO;

        Mockito.when(emailSender.createMimeMessage()).thenReturn(new MimeMessage((Session)null));
        //act
        MimeMessageHelper variavel = emailService.buildEmailAgendamento(email,tipoEmail);
        //assert
        Assert.assertNotNull(variavel);
    }

    @Test
    public void testeGetAgendamentoTemplate() throws IOException, TemplateException {
        //setup AGENDAMENTO_CRIADO_CLIENTE
        AgendamentoEntity agendamento = getAgendamentoEntityMock();
        TipoEmail tipoEmail = TipoEmail.AGENDAMENTO_CRIADO_CLIENTE;
        Template template = mock(Template.class);

        Mockito.when(fmConfiguration.getTemplate(any())).thenReturn(template);
        //act AGENDAMENTO_CRIADO_CLIENTE
        String variavel = emailService.getAgendamentoTemplate(agendamento,tipoEmail);
        //asserts AGENDAMENTO_CRIADO_CLIENTE
        Assert.assertNotNull(variavel);

        //setup AGENDAMENTO_CRIADO_MEDICO
        tipoEmail = TipoEmail.AGENDAMENTO_CRIADO_MEDICO;
        //act AGENDAMENTO_CRIADO_MEDICO
        String variavel1 = emailService.getAgendamentoTemplate(agendamento,tipoEmail);
        //asserts AGENDAMENTO_CRIADO_MEDICO
        Assert.assertNotNull(variavel1);

        //setup AGENDAMENTO_EDITADO_CLIENTE
        tipoEmail = TipoEmail.AGENDAMENTO_EDITADO_CLIENTE;
        //act AGENDAMENTO_EDITADO_CLIENTE
        String variavel2 = emailService.getAgendamentoTemplate(agendamento,tipoEmail);
        //asserts AGENDAMENTO_CRIADO_CLIENTE
        Assert.assertNotNull(variavel2);

        //setup AGENDAMENTO_EDITADO_MEDICO
        tipoEmail = TipoEmail.AGENDAMENTO_EDITADO_MEDICO;
        //act AGENDAMENTO_EDITADO_MEDICO
        String variavel3 = emailService.getAgendamentoTemplate(agendamento,tipoEmail);
        //asserts AGENDAMENTO_EDITADO_MEDICO
        Assert.assertNotNull(variavel3);

        //setup AGENDAMENTO_CANCELADO_CLIENTE
        tipoEmail = TipoEmail.AGENDAMENTO_CANCELADO_CLIENTE;
        //act AGENDAMENTO_CANCELADO_CLIENTE
        String variavel4 = emailService.getAgendamentoTemplate(agendamento,tipoEmail);
        //asserts AGENDAMENTO_CANCELADO_CLIENTE
        Assert.assertNotNull(variavel4);

        //setup AGENDAMENTO_CANCELADO_MEDICO
        tipoEmail = TipoEmail.AGENDAMENTO_CANCELADO_MEDICO;
        //act AGENDAMENTO_CANCELADO_MEDICO
        String variavel5 = emailService.getAgendamentoTemplate(agendamento,tipoEmail);
        //asserts AGENDAMENTO_CANCELADO_MEDICO
        Assert.assertNotNull(variavel5);

    }

    @NotNull
    private AgendamentoEntity getAgendamentoEntityMock() {
        AgendamentoEntity agendamentoEntity = new AgendamentoEntity();
        agendamentoEntity.setValorAgendamento(450.0);
        agendamentoEntity.setIdAgendamento(1);
        agendamentoEntity.setIdCliente(1);
        agendamentoEntity.setIdMedico(1);
        agendamentoEntity.setMedicoEntity(getMedicoEntityMock());
        agendamentoEntity.setClienteEntity(getClienteEntityMock());
        agendamentoEntity.setExame("Um exame");
        agendamentoEntity.setTratamento("Um tratamento");
        agendamentoEntity.setDataHorario(LocalDateTime.of(2023, 05, 12, 20, 15));
        return agendamentoEntity;
    }

    @NotNull
    private static UsuarioEntity getUsuarioEntityMock(){
        UsuarioEntity usuarioEntityMockado = new UsuarioEntity();
        usuarioEntityMockado.setIdUsuario(1);
        usuarioEntityMockado.setIdCargo(1);
        usuarioEntityMockado.setCpf("14574198422");
        usuarioEntityMockado.setEmail("rogerio.santos@gmail.com");
        usuarioEntityMockado.setNome("Rogério Santos");
        usuarioEntityMockado.setSenha("$2a$12$8iOr2M0EsANYEXQtP0MzV.UyhaVk/wc26UcAdOThNBtUSycZz/0gS");
        usuarioEntityMockado.setCep("01010904");
        usuarioEntityMockado.setNumero(15);
        usuarioEntityMockado.setContatos("34999748512, 34999658741");
        usuarioEntityMockado.setIdCargo(1);
        return usuarioEntityMockado;
    }


}
