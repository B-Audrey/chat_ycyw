package chat.config;

import chat.entity.UsersEntity;
import chat.services.UserService;
import chat.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor      // injection via constructeur
public class StompAuthChannelConfig implements WebSocketMessageBrokerConfigurer {

    private final JwtUtils jwtUtils;
    private final UserService userService;

    @Override
    public void configureClientInboundChannel(ChannelRegistration reg) {
        reg.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> msg, MessageChannel ch) {
                StompHeaderAccessor sha = StompHeaderAccessor.wrap(msg);

                /* On ne traite que la frame CONNECT */
                if (StompCommand.CONNECT.equals(sha.getCommand())) {
                    String token = sha.getFirstNativeHeader("Authorization");
                    if (token != null && token.startsWith("Bearer ")) {
                        token = token.substring(7);

                        /* 1) validation de la signature + date d’expiration */
                        String email = jwtUtils.validateAndExtractEmail(token);

                        /* 2) récupération de l’utilisateur en base */
                        UsersEntity user = null;
                        try {
                            user = userService.getUserByEmail(email);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }

                        /* 3) création du Principal */
                        Authentication auth =
                                new UsernamePasswordAuthenticationToken(user, null, List.of());

                        sha.setUser(auth);        // —► injecte le Principal
                    }
                }
                return msg;
            }
        });
    }
}
