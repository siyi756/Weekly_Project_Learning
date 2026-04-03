package com.aierken.aierken_practice.Service;

public class AccountService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    public double withdraw(Long accountId, double amount){
        Account account1 = accountRepository.findById(accountId);
        if(account1 == null){
            throw new RuntimeException("Account not found");
        }
        if(account1.)
    }
}
