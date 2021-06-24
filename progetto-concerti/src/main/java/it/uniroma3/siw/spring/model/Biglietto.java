package it.uniroma3.siw.spring.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
public @Data class Biglietto {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private	Long id;
	
	@Column(nullable=false)
	LocalDateTime dataAcquisizione;

	@ManyToOne
	private TipologiaPosto tipologiaPosto;
	
	@ManyToOne
	private Account proprietario;

	private String code;
	
	
	public Biglietto() {
		this.setCode(this.generateCode());
		this.dataAcquisizione = LocalDateTime.now();
	}
	
	
	/** Genera il codice da utilizzare per pagare il biglietto al ticket office
	 * Vengono utilizzati l'id del concerto, l'id della tipologia del posto,
	 * l'id dell'account proprietario del biglietto e infine l'id
	 * auto-incrementale del biglietto **/
	public String generateCode() {

		String idConcerto = "E";

		String idTipologiaPosto = "E";
		TipologiaPosto tp = this.getTipologiaPosto();
		if(tp != null) {
			idTipologiaPosto = String.valueOf(tp.getId());
			
			Concerto c = tp.getConcerto();
			if(c != null) idConcerto = String.valueOf(c.getId());
		} 
		
		String idAccountProprietario = "E";
		Account ap = this.getProprietario();
		if(ap != null) idAccountProprietario = String.valueOf(ap.getId());
		
		String myId = String.valueOf(this.getId());
		
		return idConcerto + idTipologiaPosto + idAccountProprietario + myId;
	}
	
}
