package it.uniroma3.siw.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.spring.service.ConcertoService;

@Controller
public class PrenotazioniController {
	
	@Autowired
	private ConcertoService concertoService;

	@RequestMapping(value = {"/prenotazioni"}, method = RequestMethod.GET)
	public String index(Model model) {
			model.addAttribute("concerti", concertoService.getAllConcerti());
			return "prenotazioni";
	}
}