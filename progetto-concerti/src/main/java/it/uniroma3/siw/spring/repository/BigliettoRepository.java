package it.uniroma3.siw.spring.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.model.Account;
import it.uniroma3.siw.spring.model.Biglietto;

public interface BigliettoRepository extends CrudRepository<Biglietto, Long> {
	//public List<Biglietto> findByProprietario(Account proprietario);
	public List<Biglietto> findByProprietario(Long proprietario);
}
