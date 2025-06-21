package it.uniroma3.siw.repository;

import it.uniroma3.siw.model.Availability;
import it.uniroma3.siw.model.Listing;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;


public interface AvailabilityRepository extends CrudRepository<Availability, Long>{
	public Iterable<Availability> findByListing(Listing listing);

	@Query("SELECT a FROM Availability a LEFT JOIN FETCH a.bookings WHERE a.id = :id")
	public Optional<Availability> findByIdWithBookings(@Param("id") Long id);

	@Query(nativeQuery = true,value = "SELECT * FROM availability a where a.id NOT IN (select b.availability_id FROM booking b) and a.listing_id= :id")
	List<Availability> findByIdAndNoBookings(Long id);

    Iterable<Availability> findByDate(LocalDate day);

	@Query(nativeQuery = true, value = "SELECT * FROM availability a WHERE a.date = :day AND a.start_time >= :start_time AND a.end_time <= :end_time")
	public List<Availability> findByDateAndTimeRangeWithin(
			@Param("day") LocalDate day,
			@Param("start_time") LocalTime startTime,
			@Param("end_time") LocalTime endTime);
}
