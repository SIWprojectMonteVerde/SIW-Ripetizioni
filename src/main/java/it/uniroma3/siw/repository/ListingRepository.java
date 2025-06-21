package it.uniroma3.siw.repository;

import it.uniroma3.siw.model.Listing;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Subject;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


public interface ListingRepository extends CrudRepository<Listing, Long> {


	@Query("SELECT l FROM Listing l LEFT JOIN FETCH l.availabilities")
	Iterable<Listing> findAllWithAvailabilities();

	public Iterable<Listing> findBySubject(Subject subject);

	public  Iterable<Listing> findByTeacher_Id(Long id);
	public Long countBySubject(Subject subject);
	@Query("SELECT l FROM Listing l LEFT JOIN FETCH l.availabilities WHERE l.id = :id")
	public Optional<Listing> findByIdWithAvailability(@Param("id") Long id);

	@Transactional
	@Modifying
	@Query(nativeQuery=true, value = "DELETE FROM availability a where a.id= :availability_id and a.listing_id = :listing_id")
	public void removeAvailabilityFromListing(@Param("availability_id") Long availabilityId, @Param("listing_id") Long listingId);

	@Query("SELECT l FROM Listing l LEFT JOIN FETCH l.availabilities WHERE l.subject.id = :subject_id")
	Iterable<Listing> findBySubjectIdWithAvailabilities( @Param("subject_id")Long subjectId);
}
