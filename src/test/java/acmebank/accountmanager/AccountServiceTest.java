package acmebank.accountmanager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import sun.jvm.hotspot.utilities.Assert;

import javax.validation.constraints.AssertTrue;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

class AccountServiceTest {

    private AccountService service;

    @Mock
    private AccountRepository repository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        service = new AccountService(repository);
    }

    @Test
    public void shouldGetBalance(){
        Account a = Account.builder().id("11111111").balance(BigDecimal.valueOf(1000l)).build();
        Mockito.when(repository.findById(a.getId())).thenReturn(java.util.Optional.of(a));

        assertEquals(service.getBalance(a.getId()), BigDecimal.valueOf(1000l));
    }

    @Test
    public void shoudThrowAcccountNotFoundException(){
        Mockito.when(repository.findById("11111111")).thenReturn(Optional.empty());

        CustomException exception = assertThrows(CustomException.class, () -> service.getBalance("11111111"));

        assertEquals(exception, CustomException.ACCOUNT_NOT_FOUND);
    }

    @Test
    public void shouldTransfer(){
        ArgumentCaptor<Account> captor = ArgumentCaptor.forClass(Account.class);
        Account a = Account.builder().id("11111111").balance(BigDecimal.valueOf(10000l)).build();
        Account b = Account.builder().id("22222222").balance(BigDecimal.valueOf(20000l)).build();

        Mockito.when(repository.findById("11111111")).thenReturn(Optional.of(a));
        Mockito.when(repository.findById("22222222")).thenReturn(Optional.of(b));

        TransferModel model = TransferModel.builder().sourceAccountId(a.getId()).targetAccountId(b.getId()).amount(BigDecimal.valueOf(1000l)).build();
        service.transfer(model);

        Mockito.verify(repository, times(2)).save(captor.capture());
        List<Account> values = captor.getAllValues();

        assertEquals(values.get(0).getId(),"11111111");
        assertEquals(values.get(0).getBalance(),BigDecimal.valueOf(9000l));

        assertEquals(values.get(1).getId(),"22222222");
        assertEquals(values.get(1).getBalance(),BigDecimal.valueOf(21000l));
    }

    @Test
    public void shouldThrowInsufficientBalanceException(){
        ArgumentCaptor<Account> captor = ArgumentCaptor.forClass(Account.class);
        Account a = Account.builder().id("11111111").balance(BigDecimal.valueOf(1000l)).build();
        Account b = Account.builder().id("22222222").balance(BigDecimal.valueOf(2000l)).build();

        Mockito.when(repository.findById("11111111")).thenReturn(Optional.of(a));
        Mockito.when(repository.findById("22222222")).thenReturn(Optional.of(b));

        TransferModel model = TransferModel.builder().sourceAccountId(a.getId()).targetAccountId(b.getId()).amount(BigDecimal.valueOf(2000l)).build();
        CustomException exception = assertThrows(CustomException.class, () -> service.transfer(model));

        assertEquals(exception, CustomException.INSUFFICIENT_BALANCE);
    }
}