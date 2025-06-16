package it.uniroma3.siw.repository;

import it.uniroma3.siw.model.Teacher;
import org.springframework.data.repository.CrudRepository;


public interface TeacherRepository extends CrudRepository<Teacher, Long>{
	public Iterable<Teacher> findByEmail(String email);
}
