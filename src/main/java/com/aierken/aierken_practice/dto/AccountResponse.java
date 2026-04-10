package com.aierken.aierken_practice.dto;

import lombok.Data;

@Data
public class AccountResponse {

    private Long id;
    private double balance;

    public AccountResponse(Long id, double balance) {
        this.id = id;
        this.balance = balance;
    }
}
