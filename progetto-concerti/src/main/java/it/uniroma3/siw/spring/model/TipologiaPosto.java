package it.uniroma3.siw.spring.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
public @Data class TipologiaPosto {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private	Long id;

	@Column(nullable=false)
	private String nome;

	@Column(length = 500)
	private String descrizione;

	/* Numero di posti realmente disponibili */
	@Column(nullable=false)
	private Integer maxPostiRealiDisponibili;

	/* Indica la percentuale additiva di posti prenotabili rispetto al totale
	 * reale dei postiDisponibili */
	@Column(nullable=false)
	private Integer percentualeOverbooking;

	/* Numero di posti aggiuntivi
	 * Sono calcolati in base ad una percentuale dei posti reali disponibili */
	private Integer postiOverbooking;

	/* Il numero di posti attualmente prenotabili (disponibili + overbooking) */
	private Integer postiAttualmentePrenotabili;
	
	@Column(nullable=false)
	private Long prezzoUnitario;

	@ManyToOne
	private Concerto concerto;
	
	@OneToMany(mappedBy="tipologiaPosto")
	private List<Biglietto> biglietti;
	
	/** Il costruttore vuoto di Lombok non basta, in quanto occorre utilizzare il
	 * metodo setPosti() per calcolare ed impostare appropriatamente degli attributi
	 * molto importanti della classe **/
	public TipologiaPosto() {
		this.biglietti = new ArrayList<Biglietto>();
		this.setPosti();
	}
	
	
	/* Imposta i posti di overbooking e i posti attualmente disponibili dati
	 * i campi obbligatori della classe (percentuale di overbooking e il numero
	 * massimo di posti disponibili) */
	private void setPosti() {
		this.setPostiOverbooking(this.calcolaPostiOverbooking());
		this.setPostiAttualmentePrenotabili(this.calcolaPostiAttualmentePrenotabili());
	}

	/** Restituisce il prezzo totale in base alla quantità di posti.
	 * Se la quantità di posti è minore di 1, restituisce il long pari a zero. (0L) **/
	public Long prezzoTotale(int numeroPosti){
		if( this.isValidNumeroDiPosti(numeroPosti) ) return this.prezzoUnitario * numeroPosti;
		return 0L; // il valore nullo di Long
	}
	
	
	/** Decrementa il numero di posti prenotabili se vi è disponibilità ritornando
	 * true, altrimenti ritorna solo false **/
	private boolean riduciPosti(int numeroPosti){
		if( this.checkDisponibilitaPrenotazione(numeroPosti) ) {
			this.setPostiAttualmentePrenotabili(this.getPostiAttualmentePrenotabili() - numeroPosti);
			return true;
		}
		return false;
	}
	
	/** Controlla se c'è disponibilità per prenotare "numeroPosti" posti **/
	private boolean checkDisponibilitaPrenotazione(int numeroPosti){
		if( numeroPosti <= this.getPostiAttualmentePrenotabili() && isValidNumeroDiPosti(numeroPosti) ) {
			return true;
		}
		return false;
	}

	
	/** Calcola il numero di posti attualmente prenotabili sommando ai posti
	 * attualmente disponibili quelli di overbooking **/
	private int calcolaPostiAttualmentePrenotabili(){
		Integer postiOverbooking = this.calcolaPostiOverbooking();
		Integer maxPostiRealiDisponibili = this.getMaxPostiRealiDisponibili();
		if(postiOverbooking != null && maxPostiRealiDisponibili != null) return postiOverbooking + maxPostiRealiDisponibili;
		return 0;
	}
	
	/** Calcola i posti di overbooking data una percentuale sui posti attualmente
	 * disponibili **/
	private int calcolaPostiOverbooking(){
		Integer percentualeOverbooking = this.getPercentualeOverbooking();
		Integer maxPostiRealiDisponibili = this.getMaxPostiRealiDisponibili();
		if(percentualeOverbooking != null && maxPostiRealiDisponibili != null) return ( percentualeOverbooking * maxPostiRealiDisponibili )/100;
		return 0;
	}

	/** Controlla se il numero di posti richiesti è un numero realistico, ovvero
	 * maggiore di zero **/
	private boolean isValidNumeroDiPosti(int numeroPosti) {
		if(numeroPosti > 0) return true;
		return false;
	}

}
