package dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BalanceResponse {
    private double totalBalance;
    public BalanceResponse(double totalBalance) {
        this.totalBalance = totalBalance;
    }
}
