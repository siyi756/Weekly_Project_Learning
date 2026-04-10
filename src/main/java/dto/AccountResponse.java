package dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountResponse {
    private Long id;
    private Long transactionId;
    private double balance;

    public AccountResponse(Long id, Long transactionId, double balance) {
        this.id = id;
        this.transactionId = transactionId;
        this.balance = balance;
    }
}
