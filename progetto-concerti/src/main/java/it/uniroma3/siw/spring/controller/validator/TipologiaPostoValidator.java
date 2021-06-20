package it.uniroma3.siw.spring.controller.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import it.uniroma3.siw.spring.model.TipologiaPosto;
import it.uniroma3.siw.spring.service.TipologiaPostoService;

@Component
public class TipologiaPostoValidator implements Validator {
	
	@Autowired
	private TipologiaPostoService tipologiaPostoService;
	
    private static final Logger logger = LoggerFactory.getLogger(TipologiaPostoValidator.class);

	@Override
	public void validate(Object o, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nome", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "maxPostiRealiDisponibili", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "percentualeOverbooking", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "prezzoUnitario", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "concerto", "required");
		
		if (!errors.hasErrors()) {
			logger.debug("confermato: valori non nulli");
			if (this.tipologiaPostoService.alreadyExists((TipologiaPosto)o)) {
				logger.debug("e' un duplicato");
				errors.reject("duplicato");
			}
		}
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return TipologiaPosto.class.equals(aClass);
	}
}