package br.com.dbc.vemser.trabalhofinal.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service //<todo> @Configuration
public class CodigoTrocaSenha {
    private static Map<String, Integer> tokenBD;

    public  Map<String, Integer> getTokenBD() {
        return tokenBD;
    }

    public  void setTokenBD(Map<String, Integer> tokenBD) {
        CodigoTrocaSenha.tokenBD = tokenBD;
    }


}
