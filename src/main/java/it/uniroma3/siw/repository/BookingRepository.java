package it.uniroma3.siw.repository;

import it.uniroma3.siw.model.Availability;
import it.uniroma3.siw.model.Booking;
import it.uniroma3.siw.model.Student;
import org.springframework.data.repository.CrudRepository;

public interface BookingRepository extends CrudRepository<Booking, Long> {
	public Iterable<Booking> findByStudent(Student student);
	public Iterable<Booking> findByAvailability(Availability availability);
}
