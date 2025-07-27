package chat.config;

import chat.entity.UsersEntity;
import chat.model.error.UnauthorizedException;
import chat.services.UserService;
import chat.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtHandshakeInterceptor implements HandshakeInterceptor {

    private final JwtUtils jwtUtils;
    private final UserService userService;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request,
            ServerHttpResponse response,
            WebSocketHandler wsHandler,
            Map<String, Object> attributes) {
        log.info(">>> JwtHandshakeInterceptor triggered");

        try {
            String query = request.getURI().getQuery(); // e.g. "token=xxx"
            if (query != null && query.startsWith("token=")) {
                String token = query.substring(6);
                String userEmail = jwtUtils.extractUserEmail(token);
                if (userEmail != null && jwtUtils.validateToken(token, userService.getUserByEmail(userEmail))) {
                    UsersEntity user = userService.getUserByEmail(userEmail);
                    attributes.put("user", user);
                    return true;
                }
            }
        } catch (Exception e) {
            log.warn("WebSocket JWT auth failed: {}", e.getMessage());
            throw new UnauthorizedException("WebSocket JWT auth failed: " + e.getMessage());
        }

        return false; // reject connection if token is invalid
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
            WebSocketHandler wsHandler, Exception ex) {
        // No-op
    }
}

