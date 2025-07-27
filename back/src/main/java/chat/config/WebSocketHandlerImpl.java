package chat.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

@Component
@Slf4j
public class WebSocketHandlerImpl implements WebSocketHandler {

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String userEmail = (String) session.getAttributes().get("userEmail");
        log.info(">>> Connexion WebSocket établie avec : {}", userEmail);
        session.sendMessage(new TextMessage("Bonjour " + userEmail));
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        log.info(">>> Message reçu : {}", message.getPayload());
        session.sendMessage(new TextMessage("Reçu : " + message.getPayload()));
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.error(">>> Erreur WebSocket", exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info(">>> Connexion WebSocket fermée : {}", status);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}

