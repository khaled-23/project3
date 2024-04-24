package com.example.bankmanagementsystem.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class CustomerRegistrationDTO {
    @NotEmpty(message = "user name cn not be empty")
    @Size(min = 4, max = 10, message = "username should range between 4 to 10")
    private String username;
    @NotEmpty(message = "password should not be empty")
    @Size(min = 6, message = "password should be at least 6 characters")
    private String password;
    @NotEmpty(message = "name should not be empty")
    @Size(min = 2,max = 20, message = "name should range between 2 and 20")
    private String name;
    @NotEmpty(message = "email should not be empty")
    @Email(message = "please provide an valid email")
    private String email;
    @NotEmpty(message = "phone number should not be empty")
    @Size(min = 10,max = 10, message = "phone number should not be empty")
    @Pattern(regexp = "^(05)[0-9]+$")
    private String phoneNumber;

}
