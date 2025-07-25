package chat.controller;

import chat.dto.AuthLoginDto;
import chat.entity.UsersEntity;
import chat.model.UserModel;
import chat.model.error.ForbiddenException;
import chat.model.error.NotFoundException;
import chat.model.error.UnauthorizedException;
import chat.services.UserService;
import chat.utils.JwtUtils;
import chat.utils.mappers.UserMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
@Validated
public class AuthController {
    private final JwtUtils jwtUtils;
    private final UserService userService;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;

    @Value("${app.refresh-expiration-time}")
    private String refreshExpirationTime;

    /**
     * takes the personal user infos from the token
     *
     * @param request the request will contain the user extracted from the token by jwt filter
     * @return the user infos
     */
    @GetMapping("/me")
    public ResponseEntity<?> returnMeInfos(HttpServletRequest request) {
        try {
            UsersEntity user = (UsersEntity) request.getAttribute("user");
            if (user != null) {
                UserModel me = userMapper.convertToUserModel(user);
                return ResponseEntity.ok(me);
            }
            throw new UnauthorizedException("user not found");
        } catch (Exception e) {
            if ("user not found".equals(e.getMessage())) {
                log.error("An unknown user tried to ask for me info with a valid token !!! ");
                throw new NotFoundException(e.getMessage());
            }
            log.error(e.getMessage());
            throw new ForbiddenException(e.getMessage());
        }
    }

    /**
     * refresh the access token
     *
     * @param refreshToken the refresh token with the user to refresh
     * @param response     the response to add the new refresh token in cookies
     * @return a new access token and a refresh token in cookie into the response
     */
    @GetMapping("/refresh")
    public ResponseEntity<?> refreshToken(@CookieValue(value = "chatRefreshToken", required = false) String refreshToken, HttpServletResponse response) {
        try {
            if (refreshToken == null) {
                throw new UnauthorizedException("error");
            }
            String email = jwtUtils.extractUserEmail(refreshToken);
            if (email == null) {
                throw new UnauthorizedException("error");
            }
            UsersEntity user = userService.getUserByEmail(email);
            if (user == null) {
                throw new UnauthorizedException("error");
            }
            String accessToken = jwtUtils.generateToken(email);
            String newRefreshToken = jwtUtils.generateRefreshToken(email);
            Cookie refreshTokenCookie = new Cookie("chatRefreshToken", newRefreshToken);
            refreshTokenCookie.setHttpOnly(true);
            refreshTokenCookie.setPath("/api/auth/refresh");
            refreshTokenCookie.setMaxAge(Integer.parseInt(refreshExpirationTime));
            response.addCookie(refreshTokenCookie);

            Map<String, Object> authData = new HashMap<>();
            authData.put("token", accessToken);
            return ResponseEntity.ok(authData);
        } catch (Exception e) {
            String message = e.getMessage();
            log.warn(message);
            throw new UnauthorizedException(message);
        }
    }

    /**
     * login the user
     *
     * @param authLoginDto the infos to log user
     * @param response     the access token in the response
     * @return access token and refresh token in cookie
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthLoginDto authLoginDto, HttpServletResponse response) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authLoginDto.getUsername(),
                    authLoginDto.getPassword()));
            UsersEntity user = userService.findUserByMail(authLoginDto.getUsername());
            if (user == null) {
                throw new UnauthorizedException("error");
            }
            if (authentication.isAuthenticated()) {
                String accessToken = jwtUtils.generateToken(user.getEmail());
                String refreshToken = jwtUtils.generateRefreshToken(user.getEmail());
                Cookie refreshTokenCookie = new Cookie("chatRefreshToken", refreshToken);
                refreshTokenCookie.setHttpOnly(true);
                refreshTokenCookie.setPath("/api/auth/refresh");
                refreshTokenCookie.setMaxAge(Integer.parseInt(refreshExpirationTime));
                response.addCookie(refreshTokenCookie);

                Map<String, Object> authData = new HashMap<>();
                authData.put("token", accessToken);
                return ResponseEntity.ok(authData);
            }
            throw new UnauthorizedException("error");

        } catch (Exception e) {
            log.warn(e.getMessage());
            throw new UnauthorizedException("error");
        }
    }

    /**
     * logout the user
     *
     * @param response the response to delete the refresh token cookie by a new one with max age 0
     * @return the cookie with max age 0 and an ok code
     */
    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        Cookie refreshTokenCookie = new Cookie("chatRefreshToken", null);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/api/auth/refresh");
        refreshTokenCookie.setMaxAge(0);
        response.addCookie(refreshTokenCookie);
        Map<String, Object> message = new HashMap<>();
        message.put("message", "ok");
        return ResponseEntity.ok(message);
    }

}
