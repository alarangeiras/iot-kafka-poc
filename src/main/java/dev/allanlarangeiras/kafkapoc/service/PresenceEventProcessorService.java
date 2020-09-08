package dev.allanlarangeiras.kafkapoc.service;

import dev.allanlarangeiras.kafkapoc.dto.PresenceDTO;
import dev.allanlarangeiras.kafkapoc.mappers.PresenceMapper;
import dev.allanlarangeiras.kafkapoc.producers.ArubaPresenceStreamProducer;
import dev.allanlarangeiras.kafkapoc.proto.Presence;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class PresenceEventProcessorService {

    @Autowired
    private PresenceMapper presenceMapper;

    @Autowired
    private ArubaPresenceStreamProducer arubaPresenceStreamProducer;

    @Async
    public void process(Presence.presence_event presenceEvent) {
        Presence.presence_event.presence_event_type eventType = presenceEvent.getEventType();
        if (eventType.equals(Presence.presence_event.presence_event_type.proximity)) {
            List<PresenceDTO> presenceDTOList = presenceMapper.mapFrom(presenceEvent);
            presenceDTOList
                    .forEach(presence -> arubaPresenceStreamProducer.send(presence));
        } else {
            log.debug("Evento ocorrido no timestamp {} nao foi tratado por nao se enquadrar no tipo proximity", presenceEvent.getTimestamp());
        }

    }

}
