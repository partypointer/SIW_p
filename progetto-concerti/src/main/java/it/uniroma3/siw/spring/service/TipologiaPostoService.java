package it.uniroma3.siw.spring.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.spring.model.Account;
import it.uniroma3.siw.spring.model.Concerto;
import it.uniroma3.siw.spring.model.TipologiaPosto;
import it.uniroma3.siw.spring.repository.TipologiaPostoRepository;

@Service
public class TipologiaPostoService {

    @Autowired
    protected TipologiaPostoRepository tipologiaPostoRepository;

    @Transactional
    public TipologiaPosto getTipologiaPosto(Long id) {
        Optional<TipologiaPosto> result = this.tipologiaPostoRepository.findById(id);
        return result.orElse(null);
    }

    @Transactional
    public List<TipologiaPosto> getTipologiaPostoByConcerto(Concerto concerto) {
    	return this.tipologiaPostoRepository.findByConcerto(concerto);
    }

    @Transactional
    public TipologiaPosto saveTipologiaPosto(TipologiaPosto tipologiaPosto) {
        return this.tipologiaPostoRepository.save(tipologiaPosto);
    }

    @Transactional
    public List<TipologiaPosto> getAllTipologiePosti() {
        List<TipologiaPosto> result = new ArrayList<>();
        Iterable<TipologiaPosto> iterable = this.tipologiaPostoRepository.findAll();
        for(TipologiaPosto tipologiaPosto : iterable)
            result.add(tipologiaPosto);
        return result;
    }

    /** Non è @Transactional in quanto lo è la funzione che invoca **/
	public boolean alreadyExists(TipologiaPosto tipologiaPosto) {
		if(this.getTipologiaPosto(tipologiaPosto.getId()) != null) return true;
		return false;
	}
}