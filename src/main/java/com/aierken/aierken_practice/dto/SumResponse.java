package com.aierken.aierken_practice.dto;

import lombok.Data;

@Data
public class SumResponse {
    public SumResponse(double balance) {
        this.totalBalance = balance;
    }
    private double totalBalance;
}
