package it.uniroma3.siw.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Subject {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private String descrizione;//TODO VALUTARE SE SERVE VERAMENTE
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String nome) {
		this.name = nome;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrzione) {
		this.descrizione = descrzione;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		Subject subject = (Subject) o;
		return Objects.equals(name, subject.name);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(name);
	}
}
	

