package it.uniroma3.siw.repository;

import it.uniroma3.siw.model.Listing;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Subject;


public interface ListingRepository extends CrudRepository<Listing, Long> {
	public Iterable<Listing> findBySubject(Subject subject);

	public  Iterable<Listing> findByTeacher_Id(Long id);

}
