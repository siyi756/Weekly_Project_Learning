package controller;

import dto.AccountResponse;
import dto.WithdrawRequest;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/accounts/{accountId}/withdraw")
    public ResponseEntity<List<AccountResponse>> withdraw(@PathVariable Long accountId, @RequestBody WithdrawRequest request) {

        return ResponseEntity.ok();
    }

    @GetMapping("/users/{userId}/accounts/rich")
    public ResponseEntity<List<AccountResponse>> rich(@PathVariable Long userId) {

        return ResponseEntity.ok();
    }

    @GetMapping("/users/{userId}/accounts/sum")
    public ResponseEntity<List<AccountResponse>> sum(@PathVariable Long userId) {

    }

    private AccountResponse toResponse(Account account) {
        return new AccountResponse(
                account.getId(),
                account.getUser().getId(),
                account.getBalance()
        );
    }
}
