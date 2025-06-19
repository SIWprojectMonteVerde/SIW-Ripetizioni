package it.uniroma3.siw.repository;

import it.uniroma3.siw.model.Availability;
import it.uniroma3.siw.model.Booking;
import it.uniroma3.siw.model.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingRepository extends CrudRepository<Booking, Long> {
	public Iterable<Booking> findByStudent(Student student);
	public Iterable<Booking> findByAvailability(Availability availability);

	@Query(nativeQuery = true,value = "SELECT EXISTS(SELECT 1 from booking b JOIN availability a ON b.availability_id=a.id WHERE a.listing_id= :listing_id)")
	public boolean listingHasActiveBookings(@Param("listing_id") Long listingId);

	List<Booking> findByStudentOrderByAvailabilityDate(Student student);
}
