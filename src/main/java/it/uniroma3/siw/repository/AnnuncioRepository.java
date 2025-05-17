package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Annuncio;
import it.uniroma3.siw.model.Insegnante;
import it.uniroma3.siw.model.Materia;

import java.util.List;


public interface AnnuncioRepository extends CrudRepository<Annuncio, Long> {
	public Iterable<Annuncio> findByMateria(Materia materia);

	public  Iterable<Annuncio> findByInsegnante_Id(Long id);

}
