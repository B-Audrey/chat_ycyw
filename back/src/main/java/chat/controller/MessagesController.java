package chat.controller;

import chat.dto.PaginationQueryDto;
import chat.entity.MessagesEntity;
import chat.entity.UsersEntity;
import chat.model.ChatMessage;
import chat.model.PageModel;
import chat.model.error.BadRequestException;
import chat.services.MessagesService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
@Slf4j
@Validated
public class MessagesController {

    public static final Logger logger = LoggerFactory.getLogger(MessagesService.class);
    private final MessagesService messagesService;

    @GetMapping("history")
    public ResponseEntity<?> getMessages(
            HttpServletRequest request,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sort
    ) {
        try {
            UsersEntity user = (UsersEntity) request.getAttribute("user");
            PaginationQueryDto pageDto = new PaginationQueryDto(page, size, sort);
            Page<MessagesEntity> result = messagesService.findAll(user, pageDto);
            logger.info("Fetched {} messages for user: {}", result.getTotalElements(), user.getEmail());
            PageModel<MessagesEntity> messagesPage = new PageModel<>(
                    result.getContent(),
                    new PageModel.Pagination(result.getTotalElements(), result.getNumber(), result.getSize())
            );
            logger.info("Fetched {} messages for user: {}", result.getTotalElements(), user.getEmail());
            return ResponseEntity.ok(messagesPage);
        } catch (Exception e) {
            String message = e.getMessage();
            log.warn(message);
            throw new BadRequestException(message);
        }
    }


}
