package it.uniroma3.siw.model;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Annuncio {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String titolo;
	private String descrizione;
	private Float prezzoOrario;
	@ManyToOne
	private Insegnante insegnante;
	@OneToMany(mappedBy = "annuncio")
	private List<Disponibilita> disponibilita;
	@ManyToOne
	private Materia materia;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public Float getPrezzoOrario() {
		return prezzoOrario;
	}
	public void setPrezzoOrario(Float prezzoOrario) {
		this.prezzoOrario = prezzoOrario;
	}
	public Insegnante getInsegnante() {
		return insegnante;
	}
	public void setInsegnante(Insegnante insegnante) {
		this.insegnante = insegnante;
	}
	public List<Disponibilita> getDisponibilita() {
		return disponibilita;
	}
	public void setDisponibilita(List<Disponibilita> disponibilita) {
		this.disponibilita = disponibilita;
	}
	public Materia getMateria() {
		return materia;
	}
	public void setMateria(Materia materia) {
		this.materia = materia;
	}
	
	
	@Override
	public int hashCode() {
		return Objects.hash(insegnante, titolo);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Annuncio other = (Annuncio) obj;
		return Objects.equals(insegnante, other.insegnante) && Objects.equals(titolo, other.titolo);
	}
	
	
	
}
