package acmebank.accountmanager;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/account")
public class AccountController {

    private AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @GetMapping(value = "/balance/{accountId}")
    public BigDecimal getBalance(@PathVariable String accountId){
        return service.getBalance(accountId);
    }

    @PutMapping(value = "/transfer", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void transfer(@RequestBody TransferModel model){
        service.transfer(model);
    }
}
