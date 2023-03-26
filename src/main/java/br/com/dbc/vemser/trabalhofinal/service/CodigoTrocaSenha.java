package br.com.dbc.vemser.trabalhofinal.service;

import lombok.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

@Service
public class CodigoTrocaSenha {
    private static Map<String, Integer> tokenBD;

    public  Map<String, Integer> getTokenBD() {
        return tokenBD;
    }

    public  void setTokenBD(Map<String, Integer> tokenBD) {
        CodigoTrocaSenha.tokenBD = tokenBD;
    }


}
