package it.uniroma3.siw.repository;

import it.uniroma3.siw.model.Availability;
import it.uniroma3.siw.model.Listing;
import org.springframework.data.repository.CrudRepository;


public interface AvailabilityRepository extends CrudRepository<Availability, Long>{
	public Iterable<Availability> findByListing(Listing listing);
}
