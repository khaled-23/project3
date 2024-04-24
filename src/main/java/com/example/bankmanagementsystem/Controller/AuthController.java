package com.example.bankmanagementsystem.Controller;

import com.example.bankmanagementsystem.Api.ApiResponse;
import com.example.bankmanagementsystem.DTO.CustomerRegistrationDTO;
import com.example.bankmanagementsystem.Model.User;
import com.example.bankmanagementsystem.Service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/register")
    public ResponseEntity customerRegistration(@RequestBody @Valid CustomerRegistrationDTO customerRegistrationDTO){
        authService.registerCustomer(customerRegistrationDTO);
        logger.info("customer added");
        return ResponseEntity.ok(new ApiResponse("user registered"));
    }

    @GetMapping("/users")
    public ResponseEntity getAllUsers(){
        logger.info("users requested");
        return ResponseEntity.ok(authService.getAllUser());
    }

    @PutMapping("/update/{username}")
    public ResponseEntity updateUser(@PathVariable String username, @RequestBody @Valid User user){
        authService.updateUser(username,user);
        logger.info("user updated");
        return ResponseEntity.ok(new ApiResponse("user updated"));
    }

    @DeleteMapping("/delete/{username}")
    public ResponseEntity deleteUser(@PathVariable String username){
        authService.deleteUser(username);
        logger.info("user deleted");
        return ResponseEntity.ok(new ApiResponse("user deleted"));
    }

}
