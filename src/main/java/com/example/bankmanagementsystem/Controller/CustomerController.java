package com.example.bankmanagementsystem.Controller;

import com.example.bankmanagementsystem.Api.ApiResponse;
import com.example.bankmanagementsystem.Model.Customer;
import com.example.bankmanagementsystem.Model.User;
import com.example.bankmanagementsystem.Service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @GetMapping("/customers")
    public ResponseEntity getAllCustomer(){
        logger.info("customers requested");
        return ResponseEntity.ok(customerService.getAllCustomer());
    }

    @PutMapping("/update")
    public ResponseEntity updateCustomer(@AuthenticationPrincipal User user, @RequestBody @Valid Customer customer){
        customerService.updateCustomer(user.getId(),customer);
        logger.info("customer updated");
        return ResponseEntity.ok(new ApiResponse("customer updated"));
    }

    @DeleteMapping("/delete/{customer_id}")
    public ResponseEntity deleteCustomer(@PathVariable Integer customer_id){
        customerService.deleteCustomer(customer_id);
        logger.info("customer deleted");
        return ResponseEntity.ok(new ApiResponse("customer deleted"));
    }

}
