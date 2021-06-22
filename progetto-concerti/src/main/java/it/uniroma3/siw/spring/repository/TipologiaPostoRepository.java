package it.uniroma3.siw.spring.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.model.Concerto;
import it.uniroma3.siw.spring.model.TipologiaPosto;

public interface TipologiaPostoRepository extends CrudRepository<TipologiaPosto, Long> {
	public List<TipologiaPosto> findByConcerto(Concerto concerto);
}