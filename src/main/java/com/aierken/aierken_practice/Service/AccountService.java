package com.aierken.aierken_practice.Service;

import com.aierken.aierken_practice.entity.Account;
import com.aierken.aierken_practice.repository.AccountRepository;
import com.aierken.aierken_practice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    public AccountService(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

<<<<<<< AierkenDatabaseSetup
    public double withdraw(Long accountId, double amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (account.getBalance() < amount) {
=======
    public double withdraw(Long userId, Long accountId, double amount){
        Account account1 = accountRepository.findById(accountId);
        if(account1 == null){
            throw new RuntimeException("Account not found");
        }
        if(!account1.getUserId().equals(userId)){
            throw new RuntimeException("User is not allowed to withdraw");
        }
        if(account1.getBalance() < amount){
>>>>>>> master
            throw new RuntimeException("Insufficient balance");
        }

<<<<<<< AierkenDatabaseSetup
        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);

        return account.getBalance();
=======

        return account1.getBalance();
>>>>>>> master
    }

    public List<Account> filterAccountsOver1000(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return accountRepository.findByUser_Id(userId).stream()
                .filter(account -> account.getBalance() > 1000)
                .collect(Collectors.toList());
    }

<<<<<<< AierkenDatabaseSetup
    public double sumBalancesOver1000(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return accountRepository.findByUser_Id(userId).stream()
                .filter(account -> account.getBalance() > 1000)
=======
    public double sumBalancesOver1000(Long userId) throws Exception {
        double totalresult = accountRepository.findAccountsByUserId(userId).stream()
>>>>>>> master
                .mapToDouble(Account::getBalance)
                .sum();
        return totalresult > 1000?totalresult:0;
    }
}
