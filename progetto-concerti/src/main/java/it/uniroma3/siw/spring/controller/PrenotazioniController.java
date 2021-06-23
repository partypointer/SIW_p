package it.uniroma3.siw.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.spring.controller.validator.AccountValidator;
import it.uniroma3.siw.spring.controller.validator.ConcertoValidator;
import it.uniroma3.siw.spring.model.Account;
import it.uniroma3.siw.spring.model.Concerto;
import it.uniroma3.siw.spring.service.AccountService;
import it.uniroma3.siw.spring.service.BigliettoService;
import it.uniroma3.siw.spring.service.ConcertoService;
import it.uniroma3.siw.spring.service.TipologiaPostoService;

@Controller
public class PrenotazioniController{

	@Autowired
	private ConcertoService concertoService;

	@Autowired
	private BigliettoService bigliettoService;
	
	@Autowired
	private TipologiaPostoService tipologiaPostoService;
	
	@Autowired
	private AccountService accountService;
	
	private static final Logger logger = LoggerFactory.getLogger(ConcertoValidator.class);

	@RequestMapping(value = {"/prenotazioni"}, method = RequestMethod.GET)
	public String prenotazioni(@ModelAttribute("accountCorrente") Account accountCorrente, Model model) {
		model.addAttribute("concerti", concertoService.getAllConcertiBeforeToday());
		model.addAttribute("biglietti", bigliettoService.getBigliettiFromAccount(accountCorrente.getId()));
		return "prenotazioni";
	}

	@RequestMapping(value = {"/addConcerto/{id}"}, method = RequestMethod.GET)
	public String addConcerto(@PathVariable("id") Long idConcerto, Model model) {
		Concerto concertoScelto = concertoService.getConcerto(idConcerto);
		model.addAttribute("concertoScelto", concertoScelto);
		model.addAttribute("tipologiePosti", tipologiaPostoService.getTipologiaPostoByConcerto(concertoScelto));
		
		return "addConcerto";
	}

	@RequestMapping(value = {"/addBiglietto/{id}&{quant}"}, method = RequestMethod.GET)
	public String addBiglietto(@PathVariable("id") Long idTipologiaPosto, @PathVariable("quant") int quant, @ModelAttribute("accountCorrente") Account accountCorrente, Model model) {
		//model.addAttribute("concerto", concertoService.getConcerto(idConcerto));
		//...
		return "prenotazioni";
	}
	
}