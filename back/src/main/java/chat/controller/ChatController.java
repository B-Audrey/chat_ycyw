package chat.controller;

import chat.model.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/chat")
    @SendTo("/collaborator/messages")
    public ChatMessage send(ChatMessage message) throws Exception {
        Thread.sleep(500); // simulate delay
        return message;
    }
}
