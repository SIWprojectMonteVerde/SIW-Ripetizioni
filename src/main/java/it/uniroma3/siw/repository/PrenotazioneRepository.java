package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Annuncio;
import it.uniroma3.siw.model.Utente;

public interface PrenotazioneRepository extends CrudRepository<Annuncio, Long> {
	public Iterable<Annuncio> findByUtente(Utente utente);
}
