package chat.controller;

import chat.model.ChatMessage;
import chat.services.MessagesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

import java.security.Principal;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
@Validated
public class ChatController {

    private final MessagesService messagesService;

    @MessageMapping("/chat.sendMessage")
    @SendTo("/collaborator/messages")
    public ChatMessage send(@Payload ChatMessage message,
            Principal principal,
            SimpMessageHeaderAccessor accessor) {
        log.warn(
                "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!JE RENTRE DANS LE CONTROLLER " +
                        "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        log.info("Msg reçu de {}: {}", principal.getName(), message);
        return message;
    }

    @MessageExceptionHandler
    @SendToUser("/queue")
    public String handleException(Throwable exception) {
        log.error("Exception WebSocket", exception);
        return "Erreur WebSocket : " + exception.getMessage();
    }


}
