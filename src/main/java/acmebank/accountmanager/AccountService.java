package acmebank.accountmanager;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
public class AccountService {

    private AccountRepository repository;

    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    public BigDecimal getBalance(String accountId){
        return repository.findById(accountId).map( a -> a.getBalance()).orElseThrow(() -> CustomException.ACCOUNT_NOT_FOUND);
    }

    @Transactional
    public void transfer(TransferModel model){
        BigDecimal sourceBalance = getBalance(model.getSourceAccountId());
        if(sourceBalance.compareTo(model.getAmount())<0){
            throw CustomException.INSUFFICIENT_BALANCE;
        }
        BigDecimal targetBalance = getBalance(model.getTargetAccountId());
        repository.save(Account.builder().id(model.getSourceAccountId()).balance(sourceBalance.subtract(model.getAmount())).build());
        repository.save(Account.builder().id(model.getTargetAccountId()).balance(targetBalance.add(model.getAmount())).build());
    }
}
