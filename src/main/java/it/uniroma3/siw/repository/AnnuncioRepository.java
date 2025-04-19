package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Annuncio;

public interface AnnuncioRepository extends CrudRepository<Annuncio, Long> {

}
