package acmebank.accountmanager;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@Getter
public class TransferModel {

    private String sourceAccountId;
    private String targetAccountId;
    private BigDecimal amount;
}
