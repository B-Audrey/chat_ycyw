package chat.services;

import chat.ChatApplication;
import chat.dto.PaginationQueryDto;
import chat.entity.MessagesEntity;
import chat.entity.UsersEntity;
import chat.model.ChatMessage;
import chat.repository.MessagesRepository;
import chat.utils.PaginationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessagesService {

    public static final Logger logger = LoggerFactory.getLogger(MessagesService.class);

    private final MessagesRepository messagesRepository;

    public Page<MessagesEntity> findAll(UsersEntity user, PaginationQueryDto pageDto) {
        Pageable pageable = PaginationUtil.createPageable(pageDto.getPage(), pageDto.getSize(), pageDto.getSort());
        log.info("Fetching messages for user: {} with page: {}, size: {}, sort: {}", user.getEmail(), pageDto.getPage(), pageDto.getSize(), pageDto.getSort());
       Page<MessagesEntity> messages = messagesRepository.findMessagesEntitiesByTo(user, pageable);
        logger.info(messages.toString());
        return messages;
    }
}
