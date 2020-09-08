package dev.allanlarangeiras.kafkapoc.config;

import dev.allanlarangeiras.kafkapoc.handlers.ArubaPresenceHandler;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

import java.net.URI;
import java.util.concurrent.ExecutionException;

@Configuration
@EnableWebSocket
@Log4j2
public class WebSocketConfig {

    @Autowired
    private ArubaPresenceHandler arubaPresenceHandler;

    @Value("${aruba.websocket.url}")
    private URI arubaWebSocketUrl;

    @Value("${aruba.websocket.username}")
    private String arubaUserName;

    @Value("${aruba.websocket.authorization}")
    private String arubaAuthorization;

    @Value("${aruba.websocket.topic}")
    private String arubaTopic;

    @Bean
    public WebSocketSession webSocketClient() throws ExecutionException, InterruptedException {
        final WebSocketClient client = new StandardWebSocketClient();
        final WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
        headers.add("UserName", arubaUserName);
        headers.add("Authorization", arubaAuthorization);
        headers.add("Topic", arubaTopic);

        try {
            return client.doHandshake(arubaPresenceHandler, headers, arubaWebSocketUrl).get();

        } catch (Exception exception) {
            log.error("Erro ao inicializar o websocket", exception);
            throw exception;
        }
    }

}
