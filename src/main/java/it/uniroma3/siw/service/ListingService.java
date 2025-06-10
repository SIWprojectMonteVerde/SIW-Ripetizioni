package it.uniroma3.siw.service;

import it.uniroma3.siw.model.Listing;
import it.uniroma3.siw.repository.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListingService {

    @Autowired
    private ListingRepository listingRepository;

    public Iterable<Listing> findAll() {
        return listingRepository.findAll();
    }
    public Listing save(Listing listing) {
        return listingRepository.save(listing);
    }
    public Listing findById(Long id) {
        return listingRepository.findById(id).orElse(null);
    }
    public Iterable<Listing> findByTeacher(Long id) {
        return listingRepository.findByTeacher_Id(id);
    }
}
