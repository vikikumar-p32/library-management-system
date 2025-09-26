package com.harekrsna.lms.controller;

import com.harekrsna.lms.dto.LoginRequestDTO;
import com.harekrsna.lms.dto.LoginResponseDTO;
import com.harekrsna.lms.dto.RegisterRequestDTO;
import com.harekrsna.lms.dto.RegisterResponseDTO;
import com.harekrsna.lms.entity.User;
import com.harekrsna.lms.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final ModelMapper modelMapper;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> registerNormalUser(@RequestBody RegisterRequestDTO registerRequestDTO) {
        User registeredUser = authService.registerNormalUser(modelMapper.map(registerRequestDTO, User.class));
        RegisterResponseDTO responseDTO = modelMapper.map(registeredUser, RegisterResponseDTO.class);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        String token = authService.login(modelMapper.map(loginRequestDTO, User.class));
        LoginResponseDTO responseDTO = LoginResponseDTO.builder()
                .token(token)
                .username(loginRequestDTO.getUsername())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }
}
