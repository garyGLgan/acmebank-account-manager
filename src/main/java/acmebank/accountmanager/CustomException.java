package acmebank.accountmanager;

import javax.security.auth.login.AccountNotFoundException;

public class CustomException extends RuntimeException {

    private String code;
    private CustomException(String code, String message){
        super(message);
        this.code = code;
    }

    public String getErrorCode(){
        return this.code;
    }

    public static final CustomException  ACCOUNT_NOT_FOUND = new CustomException("AccountNotFound","Account not found");

    public static final CustomException  INSUFFICIENT_BALANCE = new CustomException("InsufficientBalance","Insufficient Balance");
}
