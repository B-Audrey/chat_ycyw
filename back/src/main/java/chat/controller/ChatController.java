package chat.controller;

import chat.model.ChatMessage;
import chat.services.MessagesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/ws/")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ChatController {

    private final MessagesService messagesService;

    @MessageMapping("/chat")
    @SendTo("/collaborator/messages")
    public ChatMessage send(ChatMessage message) throws Exception {
        Thread.sleep(500); // simulate delay
        return message;
    }


}
