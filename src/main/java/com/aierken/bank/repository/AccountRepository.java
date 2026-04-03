package com.aierken.bank.repository;


import com.aierken.bank.entity.Account;
import com.aierken.bank.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class AccountRepository {

    private final Map<Long, Account> accountStore;
    private final Map<Long, User> userStore;

    @Autowired
    public AccountRepository(Map<Long, Account> accountStore, Map<Long, User> userStore) {
        this.accountStore = accountStore;
        this.userStore = userStore;
    }

    public Account findById(Long id) {
        return accountStore.get(id);
    }


    public List<Account> findAllByUserId(Long userId) throws Exception{
        if(!userStore.containsKey(userId)) {
            throw new Exception("User not found");
        }
        User user = userStore.get(userId);
        return user.getAccounts();


    }

    public void save(Account account) {
        accountStore.put(account.getId(), account);
    }

}
