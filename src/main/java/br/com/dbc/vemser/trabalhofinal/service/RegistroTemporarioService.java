package br.com.dbc.vemser.trabalhofinal.service;

import br.com.dbc.vemser.trabalhofinal.entity.RegistroTemporarioEntity;
import br.com.dbc.vemser.trabalhofinal.repository.RegistroTemporarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RegistroTemporarioService {

    private final RegistroTemporarioRepository registroTemporarioRepository;
    private static final Integer TEMPO_LIMITE = 15;

    @Scheduled(fixedRate = 300000)
    public void removerRegistrosExcedidos(){
        LocalDateTime tempoAtual = LocalDateTime.now();
        LocalDateTime tempoLimite = tempoAtual.minusMinutes(TEMPO_LIMITE);
        registroTemporarioRepository.deleteByDataGeracaoBefore(tempoLimite);
    }

    public Optional<RegistroTemporarioEntity> findByIdUsuario(Integer id){
        return registroTemporarioRepository.findByIdUsuario(id);
    }

    public void create(RegistroTemporarioEntity registroTemporarioEntity){
        registroTemporarioRepository.save(registroTemporarioEntity);
    }

}
