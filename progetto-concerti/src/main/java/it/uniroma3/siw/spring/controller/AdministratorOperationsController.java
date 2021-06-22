package it.uniroma3.siw.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.spring.controller.validator.BandValidator;
import it.uniroma3.siw.spring.model.Account;
import it.uniroma3.siw.spring.model.Band;
import it.uniroma3.siw.spring.model.User;
import it.uniroma3.siw.spring.service.BandService;

/** Responsabile del mapping delle pagine dedicate alle operazioni
 * che l'amministratore può effettuare nella sua area personale:
 * - crea una Band
 * - 
 * - 
 * -  **/
@Controller
public class AdministratorOperationsController {
	
	@Autowired
	private BandValidator bandValidator;
	
	@Autowired
	private BandService bandService;
	
	@RequestMapping(value = {"/admin/addBand"}, method = RequestMethod.GET)
	public String addBand(Model model) {
		model.addAttribute("band", new Band());
		return "addBand";
	}
	
    @RequestMapping(value = { "/admin/addBand" }, method = RequestMethod.POST)
    public String addBand(@ModelAttribute("band") Band band,
                 				BindingResult bandBindingResult,
                 									Model model) {

        // validate user and credentials fields
        this.bandValidator.validate(band, bandBindingResult);

        // if neither of them had invalid contents, store the User and the Credentials into the DB
        if(!bandBindingResult.hasErrors()) {
            bandService.saveBand(band);
            return "successfulAdminOperation";
        }
        return "addBand";
    }
}
