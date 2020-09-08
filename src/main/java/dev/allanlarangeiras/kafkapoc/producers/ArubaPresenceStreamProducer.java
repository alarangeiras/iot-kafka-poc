package dev.allanlarangeiras.kafkapoc.producers;

import com.google.gson.Gson;
import dev.allanlarangeiras.kafkapoc.dto.PresenceDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Log4j2
public class ArubaPresenceStreamProducer {

    @Autowired
    private Gson gson;

    @Value("${topics.presence}")
    private String presenceTopic;

    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void send(PresenceDTO presence) {
        final String key = UUID.randomUUID().toString();
        String jsonString = gson.toJson(presence);
        kafkaTemplate.send(presenceTopic, key, jsonString);
        log.info("Mensagem enviada, Key: {}, Content: {}", key, jsonString);
    }


}
