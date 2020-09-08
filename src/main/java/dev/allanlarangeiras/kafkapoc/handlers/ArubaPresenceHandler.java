package dev.allanlarangeiras.kafkapoc.handlers;

import com.google.protobuf.InvalidProtocolBufferException;
import dev.allanlarangeiras.kafkapoc.proto.Streaming;
import dev.allanlarangeiras.kafkapoc.proto.Presence;
import dev.allanlarangeiras.kafkapoc.service.PresenceEventProcessorService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

@Component
@Log4j2
public class ArubaPresenceHandler extends AbstractWebSocketHandler {

    @Autowired
    private PresenceEventProcessorService presenceEventProcessorService;

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
        try {
            Streaming.MsgProto parsedMessageProto = Streaming.MsgProto.parseFrom(message.getPayload());
            Presence.presence_event presenceEvent = Presence.presence_event.parseFrom(parsedMessageProto.getData());
            presenceEventProcessorService.process(presenceEvent);

        } catch (InvalidProtocolBufferException invalidProtocolBufferException) {
            log.error("Erro ao realizar o parse da mensagem de evento", invalidProtocolBufferException);
            throw invalidProtocolBufferException;
        }

    }

}
