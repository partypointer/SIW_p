package it.uniroma3.siw.spring.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
public @Data class Concerto {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private	Long id;

	@Column(nullable=false)
	private String nome;
	
	private String descrizione;
	
	@Column(nullable=false)
	private LocalDateTime dataOraInizio;

	@Column(nullable=false)
	private String indirizzoLocation;
	
	@OneToMany(mappedBy="concerto")
	private List<TipologiaPosto> tipologiaPosti;
	
	@OneToMany(mappedBy="concerto")
	private List<Partecipazione> partecipazioni;

}