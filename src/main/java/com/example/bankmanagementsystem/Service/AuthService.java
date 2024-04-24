package com.example.bankmanagementsystem.Service;

import com.example.bankmanagementsystem.Api.ApiException;
import com.example.bankmanagementsystem.DTO.CustomerRegistrationDTO;
import com.example.bankmanagementsystem.Model.Customer;
import com.example.bankmanagementsystem.Model.User;
import com.example.bankmanagementsystem.Repository.AuthRepository;
import com.example.bankmanagementsystem.Repository.CustomerRepository;
import com.example.bankmanagementsystem.Repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthRepository authRepository;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;

    public void registerCustomer(CustomerRegistrationDTO customerRegistrationDTO){
        String hashPassword = new BCryptPasswordEncoder().encode(customerRegistrationDTO.getPassword());
        User user = new User(null, customerRegistrationDTO.getUsername(), hashPassword,
                customerRegistrationDTO.getName(), customerRegistrationDTO.getEmail(),"CUSTOMER",null,null);
        Customer customer = new Customer(null, customerRegistrationDTO.getPhoneNumber(), user,null);
        user.setCustomer(customer);
        customerRepository.save(customer);
        authRepository.save(user);
    }

//    public void registerEmployee(EmployeeRegistrationDTO employeeRegistrationDTO){
//        String hashPassword = new BCryptPasswordEncoder().encode(employeeRegistrationDTO.getPassword());
//        User user = new User(null, employeeRegistrationDTO.getUsername(), hashPassword, employeeRegistrationDTO.getName(),
//                employeeRegistrationDTO.getEmail(), "Employee", null, null);
//        Employee employee = new Employee(null, employeeRegistrationDTO.getPosition(), employeeRegistrationDTO.getSalary(), user);
//        user.setEmployee(employee);
//        authRepository.save(user);
//        employeeRepository.save(employee);
//    }

    public List<User> getAllUser(){
        return authRepository.findAll();
    }



    public void updateUser(String username,User user){
        User u = authRepository.findUserByUsername(username);
        if(u==null){
            throw new ApiException("user not found");
        }
        u.setUsername(user.getUsername());
        u.setPassword(user.getPassword());
        u.setRole(user.getRole());
        u.setEmail(user.getEmail());
        authRepository.save(u);
    }

    public void deleteUser(String username){
        User user = authRepository.findUserByUsername(username);

        if(user==null){
            throw new ApiException("user not found");
        }
        authRepository.delete(user);
    }

}
