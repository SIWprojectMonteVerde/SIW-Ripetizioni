package it.uniroma3.siw.model;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("INSEGNANTE")
public class Insegnante extends Utente {

	@OneToMany(mappedBy = "insegnante")
	private List<Annuncio> annunci;

	public List<Annuncio> getAnnunci() {
		return annunci;
	}
	public void setAnnunci(List<Annuncio> annunci) {
		this.annunci = annunci;
	}
	
}
