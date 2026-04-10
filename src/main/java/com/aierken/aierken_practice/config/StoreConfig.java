package com.aierken.aierken_practice.config;

import com.aierken.aierken_practice.entity.Account;
import com.aierken.aierken_practice.entity.User;
import com.aierken.aierken_practice.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class StoreConfig {

    @Bean
    CommandLineRunner initData(UserRepository userRepository) {
        return args -> {
            if (userRepository.count() > 0) {
                return;
            }

            User user1 = new User();
            user1.setName("Alice Johnson");
            user1.setEmail("alice@example.com");
            user1.setPassword("alice123");
            user1.setPhone("408-111-1111");
            user1.setAddress("101 Main St, San Jose, CA");
            user1.setRole("CUSTOMER");
            user1.setStatus("ACTIVE");

            Account a1 = new Account();
            a1.setUser(user1);
            a1.setAccountNumber("ACC-1001");
            a1.setAccountType("CHECKING");
            a1.setBalance(500.0);
            a1.setActive(true);

            Account a2 = new Account();
            a2.setUser(user1);
            a2.setAccountNumber("ACC-1002");
            a2.setAccountType("SAVINGS");
            a2.setBalance(1500.0);
            a2.setActive(true);

            Account a3 = new Account();
            a3.setUser(user1);
            a3.setAccountNumber("ACC-1003");
            a3.setAccountType("CHECKING");
            a3.setBalance(2500.0);
            a3.setActive(false);

            user1.setAccounts(Arrays.asList(a1, a2, a3));

            User user2 = new User();
            user2.setName("Bob Smith");
            user2.setEmail("bob@example.com");
            user2.setPassword("bob123");
            user2.setPhone("408-222-2222");
            user2.setAddress("202 Market St, Santa Clara, CA");
            user2.setRole("CUSTOMER");
            user2.setStatus("ACTIVE");

            Account b1 = new Account();
            b1.setUser(user2);
            b1.setAccountNumber("ACC-2001");
            b1.setAccountType("SAVINGS");
            b1.setBalance(800.0);
            b1.setActive(true);

            Account b2 = new Account();
            b2.setUser(user2);
            b2.setAccountNumber("ACC-2002");
            b2.setAccountType("CHECKING");
            b2.setBalance(2200.0);
            b2.setActive(true);

            user2.setAccounts(Arrays.asList(b1, b2));

            User user3 = new User();
            user3.setName("Charlie Brown");
            user3.setEmail("charlie@example.com");
            user3.setPassword("charlie123");
            user3.setPhone("408-333-3333");
            user3.setAddress("303 El Camino Real, Sunnyvale, CA");
            user3.setRole("CUSTOMER");
            user3.setStatus("INACTIVE");

            Account c1 = new Account();
            c1.setUser(user3);
            c1.setAccountNumber("ACC-3001");
            c1.setAccountType("SAVINGS");
            c1.setBalance(3000.0);
            c1.setActive(true);

            Account c2 = new Account();
            c2.setUser(user3);
            c2.setAccountNumber("ACC-3002");
            c2.setAccountType("CHECKING");
            c2.setBalance(200.0);
            c2.setActive(false);

            Account c3 = new Account();
            c3.setUser(user3);
            c3.setAccountNumber("ACC-3003");
            c3.setAccountType("SAVINGS");
            c3.setBalance(1100.0);
            c3.setActive(true);

            user3.setAccounts(Arrays.asList(c1, c2, c3));

            userRepository.saveAll(Arrays.asList(user1, user2, user3));
        };
    }
}
