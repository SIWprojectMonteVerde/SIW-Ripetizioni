package it.uniroma3.siw.repository;

import it.uniroma3.siw.model.Listing;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Subject;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface ListingRepository extends CrudRepository<Listing, Long> {
	public Iterable<Listing> findBySubject(Subject subject);

	public  Iterable<Listing> findByTeacher_Id(Long id);

	@Query("SELECT l FROM Listing l LEFT JOIN FETCH l.availability WHERE l.id = :id")
	public Optional<Listing> findByIdWithAvailability(@Param("id") Long id);
}
