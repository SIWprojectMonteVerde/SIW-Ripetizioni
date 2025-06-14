package it.uniroma3.siw.service;

import it.uniroma3.siw.model.Availability;
import it.uniroma3.siw.model.Booking;
import it.uniroma3.siw.model.Listing;
import it.uniroma3.siw.repository.AvailabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvaialabiltyService {
    @Autowired
    private AvailabilityRepository availabilityRepository;
    public void save(Availability availability) {
        availabilityRepository.save(availability);
    }
    public Availability findById(Long id) {
        return availabilityRepository.findById(id).orElse(null);
    }
    public Availability findByIdWithBookings(Long id) {
        return availabilityRepository.findByIdWithBookings(id).orElse(null);
    }

    public List<Availability> findByListingIdAndNoBookings(Long id) {
        return availabilityRepository.findByIdAndNoBookings(id);
    }
}
