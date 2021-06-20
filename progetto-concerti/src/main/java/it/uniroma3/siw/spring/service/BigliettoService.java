package it.uniroma3.siw.spring.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.spring.model.Biglietto;
import it.uniroma3.siw.spring.repository.BigliettoRepository;

@Service
public class BigliettoService {

    @Autowired
    protected BigliettoRepository bigliettoRepository;

    @Transactional
    public Biglietto getBiglietto(Long id) {
        Optional<Biglietto> result = this.bigliettoRepository.findById(id);
        return result.orElse(null);
    }

    @Transactional
    public Biglietto saveBiglietto(Biglietto biglietto) {
        return this.bigliettoRepository.save(biglietto);
    }

    @Transactional
    public List<Biglietto> getAllBiglietti() {
        List<Biglietto> result = new ArrayList<>();
        Iterable<Biglietto> iterable = this.bigliettoRepository.findAll();
        for(Biglietto biglietto : iterable)
            result.add(biglietto);
        return result;
    }

    /** Non è @Transactional in quanto lo è la funzione che invoca,
     * ovvero getBiglietto(Long id) **/
	public boolean alreadyExists(Biglietto biglietto) {
		if(this.getBiglietto(biglietto.getId()) != null) return true;
		return false;
	}
}