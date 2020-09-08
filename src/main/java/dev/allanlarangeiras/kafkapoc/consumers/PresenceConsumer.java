package dev.allanlarangeiras.kafkapoc.consumers;

import com.google.gson.Gson;
import dev.allanlarangeiras.kafkapoc.dto.PresenceDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class PresenceConsumer {

    @Autowired
    private Gson gson;

    @KafkaListener(topics = "${topics.presence}", groupId = "${spring.kafka.consumer.group-id}")
    public void process(String message) {
        PresenceDTO presenceDTO = gson.fromJson(message, PresenceDTO.class);
        //TODO process message
        log.info("Mensagem recebida: {}", message);
    }

}
