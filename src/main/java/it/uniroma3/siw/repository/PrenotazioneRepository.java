package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;


import it.uniroma3.siw.model.Disponibilita;
import it.uniroma3.siw.model.Prenotazione;
import it.uniroma3.siw.model.Utente;

public interface PrenotazioneRepository extends CrudRepository<Prenotazione, Long> {
	public Iterable<Prenotazione> findByUtente(Utente utente);
	public Iterable<Prenotazione> findByDisponibilita(Disponibilita disponibilita);
}
