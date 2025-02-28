package com.example.userservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RequestMemberDto{
        @NotNull(message = "Email cannot be null")
        @Size(min = 2, message = "Email not be less than two characters")
        private String email;

        @NotNull(message = "Name cannot be null")
        @Size(min = 2, message = "Name not be less than two characters")
        private String name;

        @NotNull(message = "Password cannot be null")
        @Size(min = 2, message = "Password must be equal or greater than 8 characters")
        private String pwd;
}
