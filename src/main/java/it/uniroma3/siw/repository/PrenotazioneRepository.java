package it.uniroma3.siw.repository;

import it.uniroma3.siw.model.Studente;
import org.springframework.data.repository.CrudRepository;


import it.uniroma3.siw.model.Disponibilita;
import it.uniroma3.siw.model.Prenotazione;

public interface PrenotazioneRepository extends CrudRepository<Prenotazione, Long> {
	public Iterable<Prenotazione> findByStudente(Studente studente);
	public Iterable<Prenotazione> findByDisponibilita(Disponibilita disponibilita);
}
