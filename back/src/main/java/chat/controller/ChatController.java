package chat.controller;

import chat.model.ChatMessage;
import chat.services.MessagesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
@Validated
public class ChatController {

    private final MessagesService messagesService;

    //recoit sur app/chat
    //envoie vers /collaborator/messages
    //le client doit s'abonner à /collaborator/messages pour recevoir les messages retournés
    @MessageMapping("/chat")
    @SendTo("/collaborator/messages")
    public String send(ChatMessage chatMessage) throws Exception {
        log.warn("JE RENTRE ICI " + chatMessage.getContent());
        return "Bien recu";
    }

    @MessageExceptionHandler
    @SendToUser("/queue")
    public String handleException(Throwable exception) {
        log.error("Exception WebSocket", exception);
        return "Erreur WebSocket : " + exception.getMessage();
    }


}
