package it.uniroma3.siw.spring.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.model.Biglietto;

public interface BigliettoRepository extends CrudRepository<Biglietto, Long> {
	
}
