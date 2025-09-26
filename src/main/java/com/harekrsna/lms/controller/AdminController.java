package com.harekrsna.lms.controller;

import com.harekrsna.lms.dto.LoginRequestDTO;
import com.harekrsna.lms.dto.LoginResponseDTO;
import com.harekrsna.lms.dto.RegisterRequestDTO;
import com.harekrsna.lms.dto.RegisterResponseDTO;
import com.harekrsna.lms.entity.User;
import com.harekrsna.lms.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminController {

    private final AuthService authService;
    private final ModelMapper modelMapper;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> registerAdminUser(@RequestBody RegisterRequestDTO registerRequestDTO) {
        User registeredUser = authService.registerAdminUser(modelMapper.map(registerRequestDTO, User.class));
        RegisterResponseDTO responseDTO = modelMapper.map(registeredUser, RegisterResponseDTO.class);
        return ResponseEntity.ok(responseDTO);
    }
}
