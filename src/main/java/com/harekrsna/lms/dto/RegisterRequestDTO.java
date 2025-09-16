package com.harekrsna.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class RegisterRequestDTO {
    private String username;
    private String email;
    private String password;
}
