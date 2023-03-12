package com.klash.deliverydispatcher.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "name cannot be blank")
    @NotNull(message = "name cannot be null")
    private String name;
    @NotBlank(message = "email cannot be blank")
    @NotNull(message = "email cannot be null")
    @Email(message = "email is required")
    private String email;
    @NotBlank(message = "password cannot be blank")
    @NotNull(message = "password cannot be null")
    private String password;
}
