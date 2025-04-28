package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Insegnante;
import java.util.List;


public interface InsegnanteRepository extends CrudRepository<Insegnante, Long>{
	
	public Iterable<Insegnante> findByEmail(String email);
}
