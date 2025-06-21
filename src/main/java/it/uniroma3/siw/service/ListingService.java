package it.uniroma3.siw.service;

import it.uniroma3.siw.model.Availability;
import it.uniroma3.siw.model.Listing;
import it.uniroma3.siw.model.Subject;
import it.uniroma3.siw.repository.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
@Service
public class ListingService {

    @Autowired
    private ListingRepository listingRepository;

    public Iterable<Listing> findAll() {
        return listingRepository.findAll();
    }
    public Iterable<Listing> findAllWithAvailabilities() {
        return listingRepository.findAllWithAvailabilities();
    }
    public Listing save(Listing listing) {
        return listingRepository.save(listing);
    }
    public Listing findById(Long id) {
        return listingRepository.findById(id).orElse(null);
    }
    public Listing findByIdWithAvailability(Long id) {
        return listingRepository.findByIdWithAvailability(id).orElse(null);
    }
    public Iterable<Listing> findByTeacher(Long id) {
        return listingRepository.findByTeacher_Id(id);
    }
    public Listing update(Listing listing) {
        Listing existing = findById(listing.getId());
        listing.getAvailabilities().forEach(availability -> {if (availability.getId()==null){
            availability.setListing(listing);
        }}); //configuro corettamente le nuove disponibilità
        existing.setAvailabilities(listing.getAvailabilities());
        existing.setTitle(listing.getTitle());
        existing.setDescription(listing.getDescription());
        existing.setHourlyRate(listing.getHourlyRate());
        return listingRepository.save(existing);
    }
    public void removeAvailabilityFromListing(Long availabilityId, Long listingId) {
         listingRepository.removeAvailabilityFromListing(availabilityId, listingId);
    }
    public Iterable<Listing> findByCriteria(LocalDate day, LocalTime startTime, LocalTime endTime) {
        return listingRepository.findByCriteria(day,startTime,endTime);
    }
    public void removeListing(Long listingId) {
        listingRepository.deleteById(listingId);
    }
    public Iterable<Listing> findBySubject(Subject subject) {return listingRepository.findBySubject(subject);}
    public Long countBySubject(Subject subject) {return listingRepository.countBySubject(subject);}
    public List<Listing> findByAvailabilitiesIn(List<Availability> availabilities) {return listingRepository.findByAvailabilitiesIn(availabilities);}
    public Iterable<Listing> findBySubjectWithAvailabilities(Long subjectId) {return listingRepository.findBySubjectIdWithAvailabilities(subjectId);}
}
