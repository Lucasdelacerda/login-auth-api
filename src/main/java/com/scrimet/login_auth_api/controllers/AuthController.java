package com.scrimet.login_auth_api.controllers;

import com.scrimet.login_auth_api.DTO.LoginRequestDTO;
import com.scrimet.login_auth_api.DTO.RegisterRequestDTO;
import com.scrimet.login_auth_api.DTO.ResponseDTO;
import com.scrimet.login_auth_api.domain.user.User;
import com.scrimet.login_auth_api.infra.security.TokenService;
import com.scrimet.login_auth_api.repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody LoginRequestDTO body) {

    Optional<User> user = repository.findByEmail(body.email());
    if (user.isEmpty()) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    if(encoder.matches(body.password(), user.get().getPassword())){
        String token = tokenService.generateToken(user.get());
        return ResponseEntity.ok(new ResponseDTO(user.get().getName(), token));
    }
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody RegisterRequestDTO body) {
       Optional <User> user = this.repository.findByEmail(body.email());

        if(user.isEmpty()){
          User newUser = new User();
          newUser.setEmail(body.email());
          newUser.setPassword(encoder.encode(body.password()));
          newUser.setName(body.name());
          this.repository.save(newUser);

          String token = this.tokenService.generateToken(newUser);
          return ResponseEntity.ok(new ResponseDTO(newUser.getName(), token));
        }
        return ResponseEntity.badRequest().build();
}
    }