package chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class ChatApplication {
    @Value("${server.port}")
    String port;

    public static final Logger logger = LoggerFactory.getLogger(ChatApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ChatApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void logOnStartup() {
        logger.info("Let's go 🚀 ! Listening on port : {}", port);
    }
}


