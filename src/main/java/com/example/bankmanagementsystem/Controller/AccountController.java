package com.example.bankmanagementsystem.Controller;

import com.example.bankmanagementsystem.Api.ApiResponse;
import com.example.bankmanagementsystem.Model.Account;
import com.example.bankmanagementsystem.Model.User;
import com.example.bankmanagementsystem.Service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    Logger logger = LoggerFactory.getLogger(AccountController.class);

    //customerx
    @PostMapping("/add")
    public ResponseEntity addAccount(@AuthenticationPrincipal User user, @RequestBody @Valid Account account){
        accountService.addAccount(user.getId(),account);
        logger.info("account added");
        return ResponseEntity.ok(new ApiResponse("account added"));
    }

    //adminx
    @GetMapping("/accounts")
    public ResponseEntity getAllAccounts(){
        logger.info("accounts requested");
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    //adminx
    @PutMapping("/update/{account_id}")
    public ResponseEntity updateAccount(@PathVariable Integer account_id, @RequestBody @Valid Account account){
        accountService.updateAccount(account_id, account);
        logger.info("account updated");
        return ResponseEntity.ok(new ApiResponse("account updated"));
    }

    //adminx
    @DeleteMapping("/delete/{account_id}")
    public ResponseEntity deleteAccount(@PathVariable Integer account_id){
        accountService.deleteAccount(account_id);
        logger.info("account deleted");
        return ResponseEntity.ok(new ApiResponse("account deleted"));
    }

    //customerx
    @PutMapping("/activate/{account_id}")
    public ResponseEntity activateAccount(@AuthenticationPrincipal User user, @PathVariable Integer account_id){
        accountService.activateAccount(user.getId(), account_id);
        logger.info("account activated");
        return ResponseEntity.ok(new ApiResponse("account activated"));
    }

    //customerx
    @GetMapping("/get/{account_id}")
    public ResponseEntity viewAccount(@AuthenticationPrincipal User user, @PathVariable Integer account_id){
        Account account = accountService.viewAccount(user.getId(), account_id);
        logger.info("user requested account details");
        return ResponseEntity.ok(account);
    }

    //customerx
    @GetMapping("/get-all")
    public ResponseEntity getAllCustomerAccounts(@AuthenticationPrincipal User user){
        Set<Account> accounts = accountService.viewCustomerAccounts(user.getId());
        logger.info("user requested all accounts");
        return ResponseEntity.ok(accounts);
    }

    //customerx
    @PutMapping("/deposit/{account_id}/{amount}")
    public ResponseEntity deposit(@AuthenticationPrincipal User user, @PathVariable Integer account_id, @PathVariable Double amount){
        accountService.deposit(user.getId(), account_id, amount);
        logger.info("user deposited");
        return ResponseEntity.ok(new ApiResponse("deposit successful"));
    }

    //customerx
    @PutMapping("/withdraw/{account_id}/{amount}")
    public ResponseEntity withdraw(@AuthenticationPrincipal User user, @PathVariable Integer account_id, @PathVariable Double amount){
        accountService.withdraw(user.getId(), account_id, amount);
        logger.info("user withdraw");
        return ResponseEntity.ok(new ApiResponse("withdraw successful"));
    }

    //customer
    @PutMapping("/transfer/{from_account_id}/{to_account_id}/{amount}")
    public ResponseEntity transferFundsBetweenAccounts(@AuthenticationPrincipal User user, @PathVariable Integer from_account_id,
                                                       @PathVariable Integer to_account_id, @PathVariable Double amount){
        accountService.transferFundsBetweenAccounts(user.getId(),from_account_id,to_account_id,amount);
        logger.info("fund transfer");
        return ResponseEntity.ok(new ApiResponse("user transferred funds"));
    }


    //customer this endpoint is similar to deposit and withdraw, but takes a variable "type" to change the transaction to deposit or withdraw.
    @PutMapping("/transaction/{account_id}/{type}/{amount}")
    public ResponseEntity transaction(@AuthenticationPrincipal User user, @PathVariable Integer account_id,
                                      @PathVariable String type, @PathVariable Double amount){
        accountService.transactions(user.getId(), account_id,type,amount);
        logger.info("transaction");
        return ResponseEntity.ok(new ApiResponse("transaction complete"));
    }

    @PutMapping("/block/{account_id}")
    public ResponseEntity blocAccount(@AuthenticationPrincipal User user, @PathVariable Integer account_id){
        accountService.blockAccount(user.getId(), account_id);
        logger.info("account blocked");
        return ResponseEntity.ok(new ApiResponse("account blocked"));
    }
}
