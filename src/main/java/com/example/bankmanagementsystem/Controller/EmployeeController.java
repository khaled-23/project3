package com.example.bankmanagementsystem.Controller;

import com.example.bankmanagementsystem.Api.ApiResponse;
import com.example.bankmanagementsystem.Model.Employee;
import com.example.bankmanagementsystem.Model.User;
import com.example.bankmanagementsystem.Service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    Logger logger = LoggerFactory.getLogger(EmployeeController.class);


    @PostMapping("/add/{user_id}")
    public ResponseEntity addEmployee(@PathVariable Integer user_id, @RequestBody @Valid Employee employee){
        employeeService.addEmployee(user_id,employee);
        logger.info("employee added");
        return ResponseEntity.ok(new ApiResponse("employee added"));
    }

    @GetMapping("/employees")
    public ResponseEntity getAllEmployee(){
        logger.info("employees requested");
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @PutMapping("/update/{employee_id}")
    public ResponseEntity updateEmployee(@PathVariable Integer employee_id,
                                         @RequestBody @Valid Employee employee){
        employeeService.updateEmployee(employee_id,employee);
        logger.info("employee updated");
        return ResponseEntity.ok(new ApiResponse("employee updated"));
    }

    @DeleteMapping("/delete/{employee_id}")
    public ResponseEntity deleteEmployee(@PathVariable Integer employee_id){
        employeeService.deleteEmployee(employee_id);
        logger.info("employee deleted");
        return ResponseEntity.ok(new ApiResponse("employee deleted"));
    }


}
