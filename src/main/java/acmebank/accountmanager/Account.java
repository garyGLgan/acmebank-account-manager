package acmebank.accountmanager;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Account {

    @Id
    private String id;
    private BigDecimal balance;
}
