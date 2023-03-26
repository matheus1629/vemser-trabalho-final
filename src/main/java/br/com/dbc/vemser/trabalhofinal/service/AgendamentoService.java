package br.com.dbc.vemser.trabalhofinal.service;

import br.com.dbc.vemser.trabalhofinal.dto.*;
import br.com.dbc.vemser.trabalhofinal.dto.agendamento.AgendamentoClienteRelatorioDTO;
import br.com.dbc.vemser.trabalhofinal.dto.agendamento.AgendamentoCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.agendamento.AgendamentoDTO;
import br.com.dbc.vemser.trabalhofinal.dto.agendamento.AgendamentoMedicoRelatorioDTO;
import br.com.dbc.vemser.trabalhofinal.entity.AgendamentoEntity;
import br.com.dbc.vemser.trabalhofinal.entity.ClienteEntity;
import br.com.dbc.vemser.trabalhofinal.entity.MedicoEntity;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.repository.AgendamentoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final ClienteService clienteService;
    private final MedicoService medicoService;
    private final ObjectMapper objectMapper;
    private final EmailService emailService;

    public AgendamentoDTO adicionar(AgendamentoCreateDTO agendamentoCreateDTO) throws RegraDeNegocioException {
        ClienteEntity clienteEntity = clienteService.getCliente(agendamentoCreateDTO.getIdCliente());
        MedicoEntity medicoEntity = medicoService.getMedico(agendamentoCreateDTO.getIdMedico());

        AgendamentoEntity agendamentoEntity = objectMapper.convertValue(agendamentoCreateDTO, AgendamentoEntity.class);

        agendamentoEntity.setClienteEntity(clienteEntity);
        agendamentoEntity.setMedicoEntity(medicoEntity);
        agendamentoEntity.setValorAgendamento((medicoEntity.getEspecialidadeEntity().getValor()) -
                medicoEntity.getEspecialidadeEntity().getValor() * (clienteEntity.getConvenioEntity().getTaxaAbatimento()/100));

        agendamentoRepository.save(agendamentoEntity);
        try{
            emailService.sendEmailAgendamento(clienteEntity.getUsuarioEntity(), agendamentoEntity, TipoEmail.AGENDAMENTO_CRIADO_CLIENTE);
            emailService.sendEmailAgendamento(medicoEntity.getUsuarioEntity(), agendamentoEntity, TipoEmail.AGENDAMENTO_CRIADO_MEDICO);
        } catch (MessagingException | TemplateException | IOException e) {
            throw new RegraDeNegocioException("Erro ao enviar o e-mail com as informações do agendamento.");
        }
    // <todo está criando no banco de dados o agendamento mesmo quando da uma exception de destinatário inválido
        return objectMapper.convertValue(agendamentoEntity, AgendamentoDTO.class);
    }

    public AgendamentoDTO editar(Integer id, AgendamentoCreateDTO agendamentoCreateDTO) throws RegraDeNegocioException {
        AgendamentoEntity agendamentoEntity = getAgendamento(id);
        ClienteEntity clienteEntity = clienteService.getCliente(agendamentoCreateDTO.getIdCliente());
        MedicoEntity medicoEntity = medicoService.getMedico(agendamentoCreateDTO.getIdMedico());

        agendamentoEntity.setMedicoEntity(medicoEntity);
        agendamentoEntity.setClienteEntity(clienteEntity);
        agendamentoEntity.setExame(agendamentoCreateDTO.getExame());
        agendamentoEntity.setTratamento(agendamentoCreateDTO.getTratamento());
        agendamentoEntity.setDataHorario(agendamentoCreateDTO.getDataHorario());
        agendamentoEntity.setValorAgendamento(medicoEntity.getEspecialidadeEntity().getValor() * (clienteEntity.getConvenioEntity().getTaxaAbatimento()/100));

        agendamentoRepository.save(agendamentoEntity);
        try{
            emailService.sendEmailAgendamento(clienteEntity.getUsuarioEntity(), agendamentoEntity, TipoEmail.AGENDAMENTO_EDITADO_CLIENTE);
            emailService.sendEmailAgendamento(medicoEntity.getUsuarioEntity(), agendamentoEntity, TipoEmail.AGENDAMENTO_EDITADO_MEDICO);
        } catch (MessagingException | TemplateException | IOException e) {
            throw new RegraDeNegocioException("Erro ao enviar o e-mail de edição no agendamento.");
        }

        return objectMapper.convertValue(agendamentoEntity, AgendamentoDTO.class);
    }

    public void remover(Integer id) throws RegraDeNegocioException {
        AgendamentoEntity agendamentoEntity = getAgendamento(id);
        try{
            emailService.sendEmailAgendamento(agendamentoEntity.getClienteEntity().getUsuarioEntity(), agendamentoEntity, TipoEmail.AGENDAMENTO_CANCELADO_CLIENTE);
            emailService.sendEmailAgendamento(agendamentoEntity.getMedicoEntity().getUsuarioEntity(), agendamentoEntity, TipoEmail.AGENDAMENTO_CANCELADO_MEDICO);
        } catch (MessagingException | TemplateException | IOException e) {
            throw new RegraDeNegocioException("Erro ao enviar o e-mail de cancelamento do agendamento.");
        }
        agendamentoRepository.delete(agendamentoEntity);
    }

    public void removerPorMedicoDesativado(MedicoEntity medicoEntity) throws RegraDeNegocioException {
        agendamentoRepository.deleteByMedicoEntity(medicoEntity);
    }

    public void removerPorClienteDesativado(ClienteEntity clienteEntity) throws RegraDeNegocioException {
        agendamentoRepository.deleteByClienteEntity(clienteEntity);
    }

    public AgendamentoEntity getAgendamento(Integer id) throws RegraDeNegocioException {
        return agendamentoRepository.findById(id).orElseThrow(() -> new RegraDeNegocioException("Agendamento não encontrado."));
    }

    public AgendamentoDTO getById(Integer id) throws RegraDeNegocioException {
        return objectMapper.convertValue(getAgendamento(id), AgendamentoDTO.class);
    }


    public AgendamentoClienteRelatorioDTO getRelatorioClienteById(Integer idCliente) throws RegraDeNegocioException {
        AgendamentoClienteRelatorioDTO agendamentoRelatorio = objectMapper.convertValue(clienteService.getById(idCliente), AgendamentoClienteRelatorioDTO.class);

        List<AgendamentoDTO> allByIdCliente = agendamentoRepository.findAllByIdCliente(idCliente).stream()
                .map(agendamentoEntity -> objectMapper.convertValue(agendamentoEntity, AgendamentoDTO.class))
                .toList();
        agendamentoRelatorio.setAgendamentoDTOList(allByIdCliente);

        if (allByIdCliente.isEmpty()) {
            throw new RegraDeNegocioException("Esse cliente não possui agendamento");
        }

        return agendamentoRelatorio;
    }

    public AgendamentoMedicoRelatorioDTO getRelatorioMedicoById(Integer idMedico) throws RegraDeNegocioException {
        AgendamentoMedicoRelatorioDTO agendamentoRelatorio = objectMapper.convertValue(medicoService.getById(idMedico), AgendamentoMedicoRelatorioDTO.class);

        List<AgendamentoDTO> allByIdMedico = agendamentoRepository.findAllByIdMedico(idMedico).stream()
                .map(agendamentoEntity -> objectMapper.convertValue(agendamentoEntity, AgendamentoDTO.class))
                .toList();
        agendamentoRelatorio.setAgendamentoDTOList(allByIdMedico);

        if (allByIdMedico.isEmpty()) {
            throw new RegraDeNegocioException("Esse médico não possui agendamento");
        }

        return agendamentoRelatorio;
    }

    public PageDTO<AgendamentoDTO> findAllPaginado(Integer pagina, Integer tamanho) {

        Pageable solicitacaoPagina = PageRequest.of(pagina, tamanho);
        Page<AgendamentoEntity> agendamento = agendamentoRepository.findAll(solicitacaoPagina);
        List<AgendamentoDTO> agendamentoDTO = agendamento.getContent().stream()
                .map(x -> objectMapper.convertValue(x, AgendamentoDTO.class))
                .toList();

        return new PageDTO<>(agendamento.getTotalElements(),
                agendamento.getTotalPages(),
                pagina,
                tamanho,
                agendamentoDTO);
    }

}