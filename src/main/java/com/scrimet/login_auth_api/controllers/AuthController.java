package com.scrimet.login_auth_api.controllers;

import com.scrimet.login_auth_api.DTO.LoginRequestDTO;
import com.scrimet.login_auth_api.DTO.RegisterRequestDTO;
import com.scrimet.login_auth_api.DTO.ResponseDTO;
import com.scrimet.login_auth_api.domain.user.User;
import com.scrimet.login_auth_api.infra.security.TokenService;
import com.scrimet.login_auth_api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity login(@RequestBody LoginRequestDTO body) {
    User user = this.repository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("User not found"));
    if(encoder.matches(body.password(), user.getPassword())){
        String token = this.tokenService.generateToken(user);
        return ResponseEntity.ok(new ResponseDTO(user.getName(), token));
    }
    return ResponseEntity.ok().body(user);
    }
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDTO body) {
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