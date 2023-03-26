package br.com.dbc.vemser.trabalhofinal.service;

import br.com.dbc.vemser.trabalhofinal.entity.RegistroTemporarioEntity;
import br.com.dbc.vemser.trabalhofinal.exceptions.RegraDeNegocioException;
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

    public boolean removerRegistroExcedido(Integer id) throws RegraDeNegocioException {
        RegistroTemporarioEntity registro = findById(id);
        LocalDateTime tempoAtual = LocalDateTime.now();
        LocalDateTime tempoLimite = tempoAtual.minusMinutes(TEMPO_LIMITE);
        if(tempoLimite.isAfter(registro.getDataGeracao())){
            registroTemporarioRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public RegistroTemporarioEntity findById(Integer id) throws RegraDeNegocioException {
        return registroTemporarioRepository.findById(id).orElseThrow(()-> new RegraDeNegocioException("Registro não encontrado."));
    }

    public Optional<RegistroTemporarioEntity> findByIdUsuario(Integer id){
        return registroTemporarioRepository.findByIdUsuario(id);
    }

    public void create(RegistroTemporarioEntity registroTemporarioEntity){
        registroTemporarioRepository.save(registroTemporarioEntity);
    }

    public void deleteById(Integer id){
        registroTemporarioRepository.deleteById(id);
    }

}
