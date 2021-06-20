package it.uniroma3.siw.spring.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.spring.model.Account;
import it.uniroma3.siw.spring.service.AccountService;

@Component
public class AccountValidator implements Validator {

    @Autowired
    private AccountService accountService;

    final Integer MAX_USERNAME_LENGTH = 30;
    final Integer MIN_USERNAME_LENGTH = 4;
    final Integer MAX_PASSWORD_LENGTH = 30;
    final Integer MIN_PASSWORD_LENGTH = 4;

    @Override
    public void validate(Object o, Errors errors) {
    	Account account = (Account) o;
        String username = account.getUsername().trim();
        String password = account.getPassword().trim();

        if (username.isEmpty())
            errors.rejectValue("username", "required");
        else if (username.length() < MIN_USERNAME_LENGTH || username.length() > MAX_USERNAME_LENGTH)
            errors.rejectValue("username", "size");
        else if (this.accountService.getAccount(username) != null)
            errors.rejectValue("username", "duplicate");

        if (password.isEmpty())
            errors.rejectValue("password", "required");
        else if (password.length() < MIN_PASSWORD_LENGTH || password.length() > MAX_PASSWORD_LENGTH)
            errors.rejectValue("password", "size");
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Account.class.equals(clazz);
    }

}