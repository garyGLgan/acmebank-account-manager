package acmebank.accountmanager;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class RestError {

    public String errorCode;
    private String errorMessage;
}
