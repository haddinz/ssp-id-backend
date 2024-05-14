package com.security.jwt.spring.service.auth;

import com.security.jwt.spring.dto.request.LoginRequest;
import com.security.jwt.spring.dto.request.RegisterRequest;
import com.security.jwt.spring.dto.response.AuthenticationResponse;
import com.security.jwt.spring.models.entity.Role;
import com.security.jwt.spring.models.entity.Token;
import com.security.jwt.spring.models.entity.User;
import com.security.jwt.spring.models.repository.TokenRepository;
import com.security.jwt.spring.models.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenRepository tokenRepository;

    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {
        boolean userExist = userRepository.findByEmail(request.getEmail()).isPresent();
        if(userExist) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "email already exist");
        }
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setRole(Role.USER);

        user = userRepository.save(user);
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        //save generate token
        saveUserToken(accessToken, refreshToken, user);

        return authenticationResponse(accessToken, refreshToken, "register successfully");
    }

    public AuthenticationResponse authenticate(LoginRequest request) {
        boolean userExist = userRepository.findByEmail(request.getEmail()).isPresent();
        if(!userExist) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "email is not exist");
        }

        authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword())
        );

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "user not authenticated"));

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        revokeAllTokenByUser(user);
        saveUserToken(accessToken, refreshToken, user);

        return authenticationResponse(accessToken, refreshToken, "authenticate successfully");
    }

    public AuthenticationResponse refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "user not authenticated");
        }

        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("username not found"));

        if(jwtService.isValidRefreshToken(token, user)) {
            String accessToken = jwtService.generateAccessToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);

            revokeAllTokenByUser(user);
            saveUserToken(accessToken, refreshToken, user);

            return authenticationResponse(accessToken, refreshToken, "refresh token successfully");
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "refresh token is not valid");
    }

    private void revokeAllTokenByUser(User user) {
        List<Token> tokenList = tokenRepository.findAllAccessTokenByUser(user.getId());
        if(!tokenList.isEmpty()) {
            tokenList.forEach(token -> {
                token.setLoggedOut(true);
            });
        }
        tokenRepository.saveAll(tokenList);
    }

    private void saveUserToken(String accessToken, String refreshToken, User user) {
        Token token = new Token();
        token.setId(UUID.randomUUID().toString());
        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);
        token.setLoggedOut(false);
        token.setUser(user);
        tokenRepository.save(token);
    }

    private AuthenticationResponse authenticationResponse(String accessToken, String refreshToken, String message) {
        return AuthenticationResponse
                .builder()
                .message(message)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @PostConstruct
    public void createAdminAccount() {
        User findAdmin = userRepository.findByRole(Role.ADMIN);
        if(findAdmin == null) {
            User user = new User();
            user.setId(UUID.randomUUID().toString());
            user.setFirstName("Admin");
            user.setLastName("Only");
            user.setUsername("@admin");
            user.setPassword(passwordEncoder.encode("admin"));
            user.setEmail("admin@gmail.com");
            user.setRole(Role.ADMIN);
            userRepository.save(user);
        }
    }
}
