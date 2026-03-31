package com.example.ecommapp.dto;

import com.example.ecommapp.model.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.List;

@Data
public class RegisterRequest {

    @Email
    private String email;

    @NotBlank
    private String password;

    private String name;
    private String phone;
    private List<Address> addresses;
}