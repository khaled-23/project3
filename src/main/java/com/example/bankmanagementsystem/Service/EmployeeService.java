package com.example.bankmanagementsystem.Service;

import com.example.bankmanagementsystem.Api.ApiException;
import com.example.bankmanagementsystem.Model.Employee;
import com.example.bankmanagementsystem.Model.User;
import com.example.bankmanagementsystem.Repository.AuthRepository;
import com.example.bankmanagementsystem.Repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final AuthRepository authRepository;

    //admin only
    public void addEmployee(Integer userId, Employee employee){
        User user = authRepository.findUserById(userId);
        if(user == null){
            throw new ApiException("user does not exists");
        }
        employee.setUser(user);
        user.setEmployee(employee);
        user.setRole("EMPLOYEE");
        employeeRepository.save(employee);
        authRepository.save(user);
    }

    //admin only
    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    //admin only
    public void updateEmployee(Integer employee_id ,Employee employee){
        Employee e = employeeRepository.findEmployeeById(employee_id);
        if(e == null){
            throw new ApiException("employee not found");
        }
        e.setPosition(employee.getPosition());
        e.setSalary(employee.getSalary());
        employeeRepository.save(e);
    }

    //admin only
    public void deleteEmployee(Integer employee_id){
        Employee employee = employeeRepository.findEmployeeById(employee_id);
        if(employee == null){
            throw new ApiException("employee not found");
        }
        employeeRepository.delete(employee);
    }
}
