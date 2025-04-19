package it.uniroma3.siw.model;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
@Entity
public class Disponibilita {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private LocalDateTime inizio;
	private LocalDateTime fine;
	@ManyToOne
	private Annuncio annuncio;
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDateTime getInizio() {
		return inizio;
	}
	public void setInizio(LocalDateTime inizio) {
		this.inizio = inizio;
	}
	public LocalDateTime getFine() {
		return fine;
	}
	public void setFine(LocalDateTime fine) {
		this.fine = fine;
	}
	public Annuncio getAnnuncio() {
		return annuncio;
	}
	public void setAnnuncio(Annuncio annuncio) {
		this.annuncio = annuncio;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(annuncio, inizio);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Disponibilita other = (Disponibilita) obj;
		return Objects.equals(annuncio, other.annuncio) && Objects.equals(inizio, other.inizio);
	}
	
	
	
}
