package it.uniroma3.siw.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelExtensionsKt;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import it.uniroma3.siw.spring.controller.validator.AccountValidator;
import it.uniroma3.siw.spring.controller.validator.UserValidator;
import it.uniroma3.siw.spring.model.Account;
import it.uniroma3.siw.spring.model.User;
import it.uniroma3.siw.spring.service.AccountService;
import it.uniroma3.siw.spring.service.UserService;

@Controller
@SessionAttributes("accountCorrente")
public class AuthenticationController extends SessionCheckerController{
	
	@Autowired
	private AccountService accountService;
	@Autowired
	private AccountValidator accountValidator;

	@Autowired
	private UserService userService;
	@Autowired
	private UserValidator userValidator;
	
	private static final Logger logger = LoggerFactory.getLogger(AccountValidator.class);

	@RequestMapping(value = "/logout", method = RequestMethod.GET) 
	public String logout(Model model) {
		return "index";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET) 
	public String showRegisterForm (Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("account", new Account());
		return "registrationForm";
	}
	
	/** Invocata quando un utente che ha visualizzato la pagina di registrazione
	 * ha digitato i propri dati personali nel form e preme conferma.
	 * A questo punto, se i dati sono "corretti" verrà restituita una pagina di
	 * conferma della registrazione, altrimenti **/
    @RequestMapping(value = { "/register" }, method = RequestMethod.POST)
    public String registerUser(@ModelAttribute("user") User user,
                 				BindingResult userBindingResult,
                 	@ModelAttribute("account") Account account,
                 	BindingResult accountBindingResult,
                 Model model) {

        // validate user and credentials fields
        this.userValidator.validate(user, userBindingResult);
        this.accountValidator.validate(account, accountBindingResult);

        // if neither of them had invalid contents, store the User and the Credentials into the DB
        if(!userBindingResult.hasErrors() && !accountBindingResult.hasErrors()) {
        	account.setUser(user);
        	user.setAccount(account);
        	
            accountService.saveAccount(account);
            return "registrationSuccessful";
        }
        return "registrationForm";
    }
    
    @RequestMapping(value = "/login", method = RequestMethod.GET) 
	public String showLoginForm(Model model) {
    	Account accountCorrente = (Account) model.getAttribute("accountCorrente");

    	if(accountCorrente != null) {
			if(checkIfIsAccountIsAdministrator(accountCorrente)) return "admin/home";
			if(checkIfIsAccountIsDefault(accountCorrente)) return "default/home";
    	}
    	
		return "loginForm";
	}

	/* Se ha successo il login dell'utente, verrà controllato il suo ruolo */
	@RequestMapping(value = "/default", method = RequestMethod.GET)
    public String defaultAfterLogin(Model model) {
        
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Account accountPersistente = accountService.getAccount(userDetails.getUsername());

    	if(accountPersistente != null) {
        	model.addAttribute("accountCorrente", accountPersistente);
    		if(this.checkIfIsAccountIsAdministrator(accountPersistente)) {
    			logger.debug("\nMYINFO) ADMIN accountCorrente.username: " + accountPersistente.getUsername() + "\n");
    			return "admin/home";
        	}
        	else {
    			logger.debug("\nMYINFO) DEFAULT accountCorrente.username: " + accountPersistente.getUsername() + "\n");
    			return "default/home";
        	}
    	}
    	
        return "loginFail";
    }
	
	/** Controlla se l'account ha il ruolo di ADMIN **/
	public boolean checkIfIsAccountIsAdministrator(Account account) {
		if(account.getRuolo().equals(Account.ADMIN_RUOLO)) return true;
		return false;
	}
	
	/** Controlla se l'account ha il ruolo di DEFAULT USER **/
	public boolean checkIfIsAccountIsDefault(Account account) {
		if(account.getRuolo().equals(Account.DEFAULT_RUOLO)) return true;
		return false;
	}
	
	
}
