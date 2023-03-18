package br.com.dbc.vemser.trabalhofinal.service;

import br.com.dbc.vemser.trabalhofinal.dto.AgendamentoCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.AgendamentoDTO;
import br.com.dbc.vemser.trabalhofinal.dto.AgendamentoDadosDTO;
import br.com.dbc.vemser.trabalhofinal.entity.AgendamentoEntity;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.repository.AgendamentoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final ClienteService clienteService;
    private final MedicoService medicoService;
    private final ObjectMapper objectMapper;
    private final EmailService emailService;
    private final UsuarioService usuarioService;


    public AgendamentoDTO adicionar(AgendamentoCreateDTO agendamentoCreateDTO) throws RegraDeNegocioException {
//        try {
//
//            AgendamentoDTO novoAgendamento = objectMapper.convertValue(agendamentoRepository.adicionar(
//                    objectMapper.convertValue(agendamentoCreateDTO, AgendamentoEntity.class)), AgendamentoDTO.class);
//
//            AgendamentoDadosDTO agendamentoARemover = objectMapper.convertValue(novoAgendamento, AgendamentoDadosDTO.class);
//            UsuarioDTO usuarioCliente = usuarioService.getById(clienteService.getById(agendamentoARemover.getIdCliente()).getUsuario().getIdUsuario());
//            UsuarioDTO usuarioMedico = usuarioService.getById(medicoService.getById(agendamentoARemover.getIdMedico()).getUsuario().getIdUsuario());
//
//            AgendamentoDadosDTO agendamentoDadosDTO = objectMapper.convertValue(agendamentoARemover, AgendamentoDadosDTO.class);
//            agendamentoDadosDTO.setCliente(usuarioCliente.getNome());
//            agendamentoDadosDTO.setMedico(usuarioMedico.getNome());
//
//            try {
//                emailService.sendEmailAgendamento(usuarioCliente, agendamentoDadosDTO, TipoEmail.AGENDAMENTO_CRIADO_CLIENTE);
//                emailService.sendEmailAgendamento(usuarioMedico, agendamentoDadosDTO, TipoEmail.AGENDAMENTO_CRIADO_MEDICO);
//            } catch (MessagingException | TemplateException | IOException e) {
//                throw new RegraDeNegocioException("Erro ao enviar o e-mail!");
//            }
//
//            return novoAgendamento;
//
//        } catch (BancoDeDadosException e) {
//            throw new RegraDeNegocioException("Erro no banco de dados.");
//        }
        return null;
    }

    public void remover(Integer id) throws RegraDeNegocioException {
//        try {
//            getAgendamento(id);
//            AgendamentoDadosDTO agendamentoARemover = objectMapper.convertValue(getAgendamento(id), AgendamentoDadosDTO.class);
//            UsuarioDTO usuarioCliente = usuarioService.getById(clienteService.getById(agendamentoARemover.getIdCliente()).getUsuario().getIdUsuario());
//            UsuarioDTO usuarioMedico = usuarioService.getById(medicoService.getById(agendamentoARemover.getIdMedico()).getUsuario().getIdUsuario());
//
//            agendamentoARemover.setCliente(usuarioCliente.getNome());
//            agendamentoARemover.setMedico(usuarioMedico.getNome());
//
//            try {
//                agendamentoRepository.remover(id);
//                emailService.sendEmailAgendamento(usuarioCliente, agendamentoARemover, TipoEmail.AGENDAMENTO_CANCELADO_CLIENTE);
//                emailService.sendEmailAgendamento(usuarioMedico, agendamentoARemover, TipoEmail.AGENDAMENTO_CANCELADO_MEDICO);
//            } catch (MessagingException | TemplateException | IOException e) {
//                throw new RegraDeNegocioException("Erro ao enviar o e-mail!");
//            }
//
//        } catch (BancoDeDadosException e) {
//            throw new RegraDeNegocioException("Erro no banco de dados.");
//        }
    }

    public AgendamentoDTO editar(Integer id, AgendamentoCreateDTO agendamentoCreateDTO) throws RegraDeNegocioException {
//        try {
//            getAgendamento(id);
//            AgendamentoEntity agendamentoEntityEditado = objectMapper.convertValue(agendamentoCreateDTO, AgendamentoEntity.class);
//
//            UsuarioDTO usuarioCliente = usuarioService.getById(clienteService.getById(agendamentoEntityEditado.getIdCliente()).getUsuario().getIdUsuario());
//            UsuarioDTO usuarioMedico = usuarioService.getById(medicoService.getById(agendamentoEntityEditado.getIdMedico()).getUsuario().getIdUsuario());
//
//            AgendamentoDadosDTO agendamentoDados = objectMapper.convertValue(agendamentoRepository.editar(id, agendamentoEntityEditado), AgendamentoDadosDTO.class);
//            agendamentoDados.setCliente(usuarioCliente.getNome());
//            agendamentoDados.setMedico(usuarioMedico.getNome());
//
//            try {
//                emailService.sendEmailAgendamento(usuarioCliente, agendamentoDados, TipoEmail.AGENDAMENTO_EDITADO_CLIENTE);
//                emailService.sendEmailAgendamento(usuarioMedico, agendamentoDados, TipoEmail.AGENDAMENTO_EDITADO_MEDICO);
//            } catch (MessagingException | TemplateException | IOException e) {
//                throw new RegraDeNegocioException("Erro ao enviar o e-mail!");
//            }
//
//            return objectMapper.convertValue(agendamentoDados, AgendamentoDTO.class);
//        } catch (BancoDeDadosException e) {
//            throw new RegraDeNegocioException("Erro no banco de dados.");
//        }
        return null;
    }

    public List<AgendamentoDTO> listar() throws RegraDeNegocioException {
//        try {
//            return agendamentoRepository.listar().stream().map(agendamentoEntity ->
//                    objectMapper.convertValue(agendamentoEntity, AgendamentoDTO.class)).toList();
//        } catch (BancoDeDadosException e) {
//            throw new RegraDeNegocioException("Erro no banco de dados.");
//        } catch (Exception e) {
//            throw new RegraDeNegocioException("Houve algum erro ao listar os agendamentos.");
//        }
        return null;
    }

    public List<AgendamentoDadosDTO> listByMedico(Integer idMedico) throws RegraDeNegocioException {
//        try {
//            return agendamentoRepository.listarAgendamentosUsuario(idMedico, TipoUsuario.MEDICO);
//        } catch (BancoDeDadosException e) {
//            throw new RegraDeNegocioException("Erro no banco!");
//        }
        return null;
    }

    public List<AgendamentoDadosDTO> listByCliente(Integer idCliente) throws RegraDeNegocioException {
//        try {
//            return agendamentoRepository.listarAgendamentosUsuario(idCliente, TipoUsuario.CLIENTE);
//        } catch (BancoDeDadosException e) {
//            throw new RegraDeNegocioException("Erro no banco!");
//        }
        return null;
    }

    public AgendamentoEntity getAgendamento(Integer id) throws RegraDeNegocioException {
            return agendamentoRepository.findById(id).orElseThrow(() -> new RegraDeNegocioException("Agendamento não encontrado."));
    }
    public AgendamentoDTO getById(Integer id) throws RegraDeNegocioException {
        return objectMapper.convertValue(getAgendamento(id), AgendamentoDTO.class);
    }

}