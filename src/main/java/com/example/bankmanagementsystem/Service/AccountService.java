package com.example.bankmanagementsystem.Service;

import com.example.bankmanagementsystem.Api.ApiException;
import com.example.bankmanagementsystem.Model.Account;
import com.example.bankmanagementsystem.Model.Customer;
import com.example.bankmanagementsystem.Model.User;
import com.example.bankmanagementsystem.Repository.AccountRepository;
import com.example.bankmanagementsystem.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;


    //customer can add accounts
    public void addAccount(Integer userId, Account account){
        Customer customer = customerRepository.findCustomerById(userId);
        account.setBalance(0.0);
        account.setIsActive(false);
        account.setCustomer(customer);
        customer.getAccounts().add(account);
        accountRepository.save(account);
        customerRepository.save(customer);
    }

    //only admin can access
    public List<Account> getAllAccounts(){
        return accountRepository.findAll();
    }

    //only admin
    public void updateAccount(Integer account_id, Account account){
        Account a = accountRepository.findAccountById(account_id);
        if(a==null){
            throw new ApiException("account does not exists");
        }
        a.setAccountNumber(account.getAccountNumber());
        a.setBalance(account.getBalance());
        a.setIsActive(account.getIsActive());
        accountRepository.save(a);
    }

    //only admin
    public void deleteAccount(Integer account_id){
        Account account = accountRepository.findAccountById(account_id);
        if(account == null){
            throw new ApiException("account not found");
        }
        accountRepository.delete(account);
    }

    public void activateAccount(Integer customer_id,Integer account_id){
        Customer customer = customerRepository.findCustomerById(customer_id);
        Account account = accountRepository.findAccountById(account_id);
        if(account == null){
            throw new ApiException("account not found");
        }
        if(account.getCustomer().getId() != customer_id){
            throw new ApiException("error"); // account does not belong to customer
        }
        account.setIsActive(true);
        accountRepository.save(account);
    }

    //customer
    public Account viewAccount(Integer customer_id, Integer account_id){
        Account account = accountRepository.findAccountById(account_id);
        if(account == null){
            throw new ApiException("account not found");
        }
        if(account.getCustomer().getId() != customer_id){
            throw new ApiException("error"); // account does not belong to customer
        }
        return account;
    }

    //customer
    public Set<Account> viewCustomerAccounts(Integer customer_id){
        Customer customer = customerRepository.findCustomerById(customer_id);
        return customer.getAccounts();
    }

    //customer
    public void deposit(Integer customerId, Integer accountId, Double amount){
        Account account = accountRepository.findAccountById(accountId);
        if(account == null){
            throw new ApiException("account not found");
        }else if(account.getCustomer().getId() != customerId){
            throw new ApiException("error"); // account does not belong to customer
        }else if(!account.getIsActive()){
            throw new ApiException("account disabled");
        }

        if(amount<1){
            throw new ApiException("invalid amount");
        }
        account.setBalance(account.getBalance()+amount);
        accountRepository.save(account);
    }

    public void withdraw(Integer customerId, Integer accountId, Double amount){
        Account account = accountRepository.findAccountById(accountId);
        if(account == null){
            throw new ApiException("account not found");
        }else if(account.getCustomer().getId() != customerId){
            throw new ApiException("error");
        }else if(!account.getIsActive()){
            throw new ApiException("account disabled");
        }
        if(amount < 1 || amount>account.getBalance()){
            throw new ApiException("invalid amount");
        }
        account.setBalance(account.getBalance()-amount);
        accountRepository.save(account);
    }

    //customer
    public void transferFundsBetweenAccounts(Integer customerId, Integer fromAccountId, Integer toAccountId, Double amount){
        Account fromAccount = accountRepository.findAccountById(fromAccountId);
        Account toAccount = accountRepository.findAccountById(toAccountId);
        if(fromAccount == null || toAccount == null){
            throw new ApiException("account not found");
        }else if(fromAccount.getCustomer().getId() != customerId || toAccount.getCustomer().getId() != customerId){
            throw new ApiException("error");
        }else if(!fromAccount.getIsActive()){
            throw new ApiException("account disabled");
        }

        if(amount<1 || amount>fromAccount.getBalance()){
            throw new ApiException("insufficient funds");
        }
        fromAccount.setBalance(fromAccount.getBalance()-amount);
        toAccount.setBalance(toAccount.getBalance()+amount);
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }

    //endpoint that does both withdraw and deposit
    public void transactions(Integer customerId, Integer accountId, String type, Double amount){
        Account account = accountRepository.findAccountById(accountId);
        if(account == null){
            throw new ApiException("account not found");
        }else if(account.getCustomer().getId() != customerId){
            throw new ApiException("error"); // account does not belong to customer
        }else if(!account.getIsActive()){
            throw new ApiException("account disabled");
        }
        if(type.equalsIgnoreCase("deposit")){
            if(amount<1){
                throw new ApiException("invalid amount");
            }
            account.setBalance(account.getBalance()+amount);
            accountRepository.save(account);
        }else if(type.equalsIgnoreCase("withdraw")){
            if(amount < 1 || amount>account.getBalance()){
                throw new ApiException("invalid amount");
            }
            account.setBalance(account.getBalance()-amount);
            accountRepository.save(account);
        }else throw new ApiException("invalid type");
    }

    public void blockAccount(Integer customerId, Integer accountId){
        Account account = accountRepository.findAccountById(accountId);
        if(account == null){
            throw new ApiException("account not found");
        }else if(account.getCustomer().getId() != customerId);
        account.setIsActive(false);
        accountRepository.save(account);
    }

}
