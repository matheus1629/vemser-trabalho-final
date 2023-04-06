package br.com.dbc.vemser.trabalhofinal.service;

import br.com.dbc.vemser.trabalhofinal.client.EnderecoClient;
import br.com.dbc.vemser.trabalhofinal.dto.AgendamentoMedicoEditarCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.agendamento.AgendamentoCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.agendamento.AgendamentoDTO;
import br.com.dbc.vemser.trabalhofinal.dto.agendamento.AgendamentoListaDTO;
import br.com.dbc.vemser.trabalhofinal.dto.agendamento.AgendamentoMedicoRelatorioDTO;
import br.com.dbc.vemser.trabalhofinal.dto.medico.MedicoCompletoDTO;
import br.com.dbc.vemser.trabalhofinal.dto.medico.MedicoCreateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.medico.MedicoUpdateDTO;
import br.com.dbc.vemser.trabalhofinal.dto.usuario.UsuarioCreateDTO;
import br.com.dbc.vemser.trabalhofinal.entity.AgendamentoEntity;
import br.com.dbc.vemser.trabalhofinal.entity.MedicoEntity;
import br.com.dbc.vemser.trabalhofinal.entity.TipoEmail;
import br.com.dbc.vemser.trabalhofinal.entity.UsuarioEntity;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.trabalhofinal.repository.MedicoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MedicoService {
    private final MedicoRepository medicoRepository;
    private final ObjectMapper objectMapper;
    private final UsuarioService usuarioService;
    private final EspecialidadeService especialidadeService;
    private final AgendamentoService agendamentoService;
    private final EmailService emailService;
    private final EnderecoClient enderecoClient;

    public MedicoService(MedicoRepository medicoRepository,
                         ObjectMapper objectMapper,
                         UsuarioService usuarioService,
                         EspecialidadeService especialidadeService,
                         @Lazy AgendamentoService agendamentoService,
                         @Lazy EmailService emailService, EnderecoClient enderecoClient) {
        this.medicoRepository = medicoRepository;
        this.objectMapper = objectMapper;
        this.usuarioService = usuarioService;
        this.especialidadeService = especialidadeService;
        this.agendamentoService = agendamentoService;
        this.emailService = emailService;
        this.enderecoClient = enderecoClient;
    }



    public MedicoCompletoDTO recuperarMedico() throws RegraDeNegocioException {
        MedicoEntity medicoEntity = medicoRepository.getMedicoEntityByIdUsuario(usuarioService.getIdLoggedUser());
        return getById(medicoEntity.getIdMedico());
    }

    public MedicoCompletoDTO getById(Integer idMedico) throws RegraDeNegocioException {
        Optional<MedicoCompletoDTO> medicoRetornado = medicoRepository.getByIdPersonalizado(idMedico);
        if (medicoRetornado.isEmpty()) {
            throw new RegraDeNegocioException("Usuário não encontrado.");
        }
        medicoRetornado.get().setEnderecoDTO(enderecoClient.getEndereco(medicoRetornado.get().getCep()));
        return medicoRetornado.get();
    }

    public MedicoEntity getMedico(Integer id) throws RegraDeNegocioException {
        return medicoRepository.findById(id)
                .filter(medicoEntity -> medicoEntity.getUsuarioEntity().getAtivo().equals(1))
                .orElseThrow(() -> new RegraDeNegocioException("Médico não existe!"));
    }

    public AgendamentoListaDTO getMedicoAgentamentos() throws RegraDeNegocioException {
        MedicoCompletoDTO medicoCompletoDTO = recuperarMedico();
        AgendamentoMedicoRelatorioDTO agendamentoMedicoRelatorioDTO = agendamentoService.getRelatorioMedicoById(medicoCompletoDTO.getIdMedico());

        return objectMapper.convertValue(agendamentoMedicoRelatorioDTO, AgendamentoListaDTO.class);
    }

    public MedicoCompletoDTO adicionar(MedicoCreateDTO medico) throws RegraDeNegocioException, JsonProcessingException {
        checarSeTemNumero(medico.getNome());

        UsuarioEntity usuarioEntity = objectMapper.convertValue(medico, UsuarioEntity.class);
        usuarioEntity.setIdCargo(2);

        // Adicionando o usuario que foi salvado no Medico a salvar
        MedicoEntity medicoEntity = objectMapper.convertValue(medico, MedicoEntity.class);
        usuarioEntity.setMedicoEntity(medicoEntity);
        medicoEntity.setUsuarioEntity(usuarioEntity);

        // Adicionando Convenio em Medico a salvar
        medicoEntity.setEspecialidadeEntity(especialidadeService.getEspecialidade(medico.getIdEspecialidade()));

        usuarioService.validarUsuarioAdicionado(usuarioEntity);
        usuarioService.adicionar(usuarioEntity);

        medicoRepository.save(medicoEntity);

        emailService.producerUsuarioEmail(usuarioEntity, TipoEmail.USUARIO_CADASTRO, null);

        return getById(medicoEntity.getIdMedico());
    }

    public MedicoCompletoDTO editar(MedicoUpdateDTO medico) throws RegraDeNegocioException {
        MedicoEntity medicoEntity = objectMapper.convertValue(recuperarMedico(), MedicoEntity.class);

        medicoEntity.setCrm(medico.getCrm());
        medicoEntity.setEspecialidadeEntity(especialidadeService.getEspecialidade(medico.getIdEspecialidade()));
        medicoEntity.setUsuarioEntity(usuarioService.getUsuario(medicoEntity.getIdUsuario()));

        checarSeTemNumero(medico.getNome());

        UsuarioCreateDTO usuarioCreateDTO = objectMapper.convertValue(medico, UsuarioCreateDTO.class);

        List<MedicoEntity> listaMedico = medicoRepository.findAll().stream()
                .filter(medicoEntity1 -> !medicoEntity1.getCrm().equals(medico.getCrm()))
                .toList();

        for (MedicoEntity medicoVerificarCRM : listaMedico) {
            if (medico.getCrm().equals(medicoVerificarCRM.getCrm())) {
                throw new RegraDeNegocioException("CRM já existe!");
            }
        }

        usuarioService.validarUsuarioEditado(usuarioCreateDTO, medicoEntity.getIdUsuario());
        usuarioService.editar(usuarioCreateDTO, medicoEntity.getIdUsuario());

        MedicoEntity medicoEditado = medicoRepository.save(medicoEntity);

        return getById(medicoEditado.getIdMedico());
    }



    public AgendamentoDTO editarAgendamentoMedico(Integer idAgendamento, AgendamentoMedicoEditarCreateDTO agendamentoMedicoEditarCreateDTO) throws RegraDeNegocioException, JsonProcessingException {
        AgendamentoEntity agendamentoEntity = agendamentoService.getAgendamento(idAgendamento);

        MedicoEntity medicoEntity = medicoRepository.getMedicoEntityByIdUsuario(usuarioService.getIdLoggedUser());

        if (!agendamentoEntity.getMedicoEntity().getIdMedico().equals(medicoEntity.getIdMedico())){
            throw new RegraDeNegocioException("Você não pode alterar um agendamento que não é seu.");
        }

        agendamentoEntity.setTratamento(agendamentoMedicoEditarCreateDTO.getTratamento());
        agendamentoEntity.setExame(agendamentoMedicoEditarCreateDTO.getExame());

        AgendamentoCreateDTO agendamentoCreateDTO = objectMapper.convertValue(agendamentoEntity, AgendamentoCreateDTO.class);

        AgendamentoDTO agendamentoEditado = agendamentoService.editar(idAgendamento, agendamentoCreateDTO);

        return agendamentoEditado;
    }

    @Transactional
    public void remover(Integer id) throws RegraDeNegocioException {
        MedicoEntity medicoEntity = getMedico(id);
        usuarioService.remover(medicoEntity.getIdUsuario());
        agendamentoService.removerPorMedicoDesativado(medicoEntity);
    }

    public void checarSeTemNumero(String string) throws RegraDeNegocioException {
        if (string.matches(".*[0-9].*")) { // checa se tem número no nome
            throw new RegraDeNegocioException("O nome da especialidade não pode conter número");
        }
    }
}
