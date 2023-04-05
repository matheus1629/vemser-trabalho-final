package br.com.dbc.vemser.trabalhofinal.dto.email;

public enum ParticaoKafka {
    SEND_EMAIL_USUARIO(1),
    SEND_EMAIL_AGENDAMENTO(2),
    SEND_EMAIL_CLIENTE(3);

    private Integer particao;

    ParticaoKafka(Integer particao) {
        this.particao = particao;
    }

    public Integer getParticao() {
        return particao;
    }

}
