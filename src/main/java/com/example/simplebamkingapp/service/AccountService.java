package com.example.simplebamkingapp.service;

import com.example.simplebamkingapp.exception.AccountNotFoundException;
import com.example.simplebamkingapp.exception.InsufficientFundsException;
import com.example.simplebamkingapp.exception.InvalidChangeAmountException;
import com.example.simplebamkingapp.exception.UserNotFoundException;
import com.example.simplebamkingapp.model.Account;
import com.example.simplebamkingapp.model.User;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final UserService userService;

    public AccountService(UserService userService) {
        this.userService = userService;
    }
    public Account changeBalance(
            String username,String accountNumber, Operation operation, double amount ){ // смена баланса
        if (amount<=0){
            throw new InvalidChangeAmountException();
        }
       User user= userService.getUser(username);

       Account account = user.getAccounts().stream()
               .filter(acc->acc.getAccountNumber().equals(accountNumber))
               .findFirst()
               .orElseThrow(AccountNotFoundException::new);
if (operation.equals(Operation.DEPOSIT)){
    return depositOnAccount(account,amount);
}
else {
    return withdrawFromAccount(account,amount);
}
    }
    private Account withdrawFromAccount(Account account, double amount){ //списание средств с аккаунта
        if (account.getBalance()<amount){
            throw new InsufficientFundsException();
        }
       account.setBalance(account.getBalance()-amount);
        return account;

    }
    private Account depositOnAccount(Account account, double amount){  //  начисление средств на аккаунт
    account.setBalance(account.getBalance()+amount);
    return account;
    }
}
