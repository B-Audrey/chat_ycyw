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

@Controller
@RequiredArgsConstructor
@Slf4j
@Validated
public class ChatController {


    @MessageMapping("/chat.sendMessage")
    @SendTo("/collaborator/messages")
    public ChatMessage send(@Payload ChatMessage message,
            SimpMessageHeaderAccessor accessor
    ) {
        log.warn(
                "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!JE RENTRE DANS LE CONTROLLER " +
                        "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        log.info("Message received: {}", message);
        ChatMessage chatMessage = new ChatMessage();

        chatMessage.setBody(message.getBody());
        String uuid = (String) accessor.getSessionAttributes().get("userUuid");
        chatMessage.setFromUuid(uuid);
        chatMessage.setStatus("RECEIVED");
        return chatMessage;

    }


}
