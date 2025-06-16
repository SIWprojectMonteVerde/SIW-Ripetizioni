package it.uniroma3.siw.repository;

import it.uniroma3.siw.model.Subject;
import org.springframework.data.repository.CrudRepository;

public interface SubjectRepository extends CrudRepository<Subject, Long> {
	
}
