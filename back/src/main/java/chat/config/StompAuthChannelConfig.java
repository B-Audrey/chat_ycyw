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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.List;
import java.util.Objects;

@Configuration
@RequiredArgsConstructor
public class StompAuthChannelConfig implements WebSocketMessageBrokerConfigurer {

    private final JwtUtils    jwtUtils;
    private final UserService userService;

    @Override
    public void configureClientInboundChannel(ChannelRegistration reg) {

        reg.interceptors(new ChannelInterceptor() {

            @Override
            public Message<?> preSend(Message<?> msg, MessageChannel ch) {
                StompHeaderAccessor sha = StompHeaderAccessor.wrap(msg);

                if (StompCommand.CONNECT.equals(sha.getCommand())) {

                    String token = sha.getFirstNativeHeader("Authorization");
                    if (token == null) {                               // header en minuscule ?
                        token = sha.getFirstNativeHeader("authorization");
                    }
                    if (token == null && sha.getSessionAttributes()!=null) {
                        token = (String) sha.getSessionAttributes().get("token");
                    }
                    if (token != null && token.startsWith("Bearer ")) {
                        token = token.substring(7);
                    }

                    if (token != null) {
                        String email = jwtUtils.validateAndExtractEmail(token);
                        UsersEntity user = userService.findUserByMail(email);

                        Authentication auth =
                                new UsernamePasswordAuthenticationToken(user, null, List.of());

                        sha.setUser(auth);
                        Objects.requireNonNull(sha.getSessionAttributes()).put("userUuid", user.getUuid());

                        SecurityContextHolder.getContext().setAuthentication(auth);

                        sha.setLeaveMutable(true);
                    }
                    else {
                        return null; // ferme la connexion
                    }
                }

                return msg;
            }



        });
    }
}
