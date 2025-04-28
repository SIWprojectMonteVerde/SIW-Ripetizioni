package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Disponibilita;

import it.uniroma3.siw.model.Annuncio;


public interface DisponibilitaRepository extends CrudRepository<Disponibilita, Long>{
	public Iterable<Disponibilita> findByAnnuncio(Annuncio annuncio);
}
