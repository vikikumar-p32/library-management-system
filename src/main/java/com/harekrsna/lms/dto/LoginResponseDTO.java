package com.harekrsna.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
public class LoginResponseDTO {
    private String token;
    private String username;
}
