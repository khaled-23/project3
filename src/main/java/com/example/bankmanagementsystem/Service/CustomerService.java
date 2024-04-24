package com.example.bankmanagementsystem.Service;

import com.example.bankmanagementsystem.Api.ApiException;
import com.example.bankmanagementsystem.Model.Customer;
import com.example.bankmanagementsystem.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    //admin only
    public List<Customer> getAllCustomer(){
        return customerRepository.findAll();
    }

    //customer only
    public void updateCustomer(Integer customer_id, Customer customer){
        Customer c = customerRepository.findCustomerById(customer_id);
        if(c == null){
            throw new ApiException("customer not found");
        }
        c.setPhoneNumber(customer.getPhoneNumber());
        customerRepository.save(c);
    }

    //admin only
    public void deleteCustomer(Integer customer_id){
        Customer customer = customerRepository.findCustomerById(customer_id);
        if(customer == null){
            throw new ApiException("customer not found");
        }
        customerRepository.delete(customer);
    }


}
