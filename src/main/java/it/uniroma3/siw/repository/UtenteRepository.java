package it.uniroma3.siw.repository;

import it.uniroma3.siw.model.Studente;
import it.uniroma3.siw.model.Utente;
import org.springframework.data.repository.CrudRepository;


public interface UtenteRepository extends CrudRepository<Utente, Long> {
	Iterable<Utente> findByEmail(String email);
}
