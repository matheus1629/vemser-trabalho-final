package br.com.dbc.vemser.trabalhofinal.config;

import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {
    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value(value = "${spring.kafka.properties.sasl.jaas.config}")
    private  String jaasTemplate;

    @Value(value = "${spring.kafka.properties.sasl.mechanism}")
    private  String SCRAMSHA256;

    @Value(value = "${spring.kafka.properties.security.protocol}")
    private  String SASLSSL;

    @Value(value = "${spring.kafka.properties.enable.idempotence}")
    private  String idempotence;


    @Bean
    public KafkaTemplate<String,String> configKafkaTemplate(){
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress); // servidor
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class); // chave
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class); // valor
        configProps.put("sasl.mechanism", SCRAMSHA256);
        configProps.put("sasl.jaas.config", jaasTemplate);
        configProps.put("security.protocol", SASLSSL);
        configProps.put("enable.idempotence" , idempotence);


        DefaultKafkaProducerFactory<String, String> kafkaProducerFactory = new DefaultKafkaProducerFactory<>(configProps);
        return new KafkaTemplate<>(kafkaProducerFactory);
    }
}
