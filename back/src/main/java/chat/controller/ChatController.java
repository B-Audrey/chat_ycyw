package chat.controller;

import chat.entity.UsersEntity;
import chat.model.ChatMessage;
import chat.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

@Controller
@RequiredArgsConstructor
@Slf4j
@Validated
public class ChatController {


    private final UserService userService;

    @MessageMapping("/chat.sendMessage")
    @SendTo("/collaborator/messages")
    public ChatMessage send(@Payload ChatMessage message,
            SimpMessageHeaderAccessor accessor
    ) throws Exception {
        String uuid = (String) accessor.getSessionAttributes().get("userUuid");
        UsersEntity user = userService.findUserByUuid(uuid);
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setFromFirstName(user.getFirstName());
        chatMessage.setFromLastName(user.getLastName());
        chatMessage.setBody(message.getBody());
        chatMessage.setFromUuid(uuid);
        chatMessage.setStatus("RECEIVED");
        return chatMessage;

    }


}
