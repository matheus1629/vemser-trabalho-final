package br.com.dbc.vemser.trabalhofinal.security;

import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class CodigoTrocaSenha {
    private static Map<String, Integer> tokenBD;

    public  Map<String, Integer> getTokenBD() {
        return tokenBD;
    }

    public  void setTokenBD(Map<String, Integer> tokenBD) {
        CodigoTrocaSenha.tokenBD = tokenBD;
    }


}
