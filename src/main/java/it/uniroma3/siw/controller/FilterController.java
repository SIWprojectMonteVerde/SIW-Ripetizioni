package it.uniroma3.siw.controller;

import it.uniroma3.siw.model.Listing;
import it.uniroma3.siw.service.ListingService;
import it.uniroma3.siw.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
class FilterController {
    @Autowired
    private SubjectService subjectService;

    @Autowired
    private ListingService listingService;

    @GetMapping("/listings")
    public String filterListings(
            @RequestParam(name = "subj", defaultValue = "-1") Long MateriaID,
            @RequestParam(required = false) String searchTerm,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate Day,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate StartHour,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate EndHour,
            @RequestParam(defaultValue = "DATE_DESC") String sortBy,
            Model model
    ) {
        Sort sort = this.buildSort(sortBy);
        Iterable<Listing> ALL=listingService.findAll();
        Iterable<Listing> Sub=listingService.findBySubject(subjectService.findById(MateriaID));

        return searchTerm;
    }
    private Sort buildSort(String sortBy) {
        return switch(sortBy) {
            case "DATE_ASC"   -> Sort.by("releaseDate").ascending();
            case "TITLE"      -> Sort.by("title").ascending();
            case "TITLE_DESC" -> Sort.by("title").descending();
            case "RATING"     -> Sort.by("popularity").descending();  // verifica che 'popularity' esista!
            default           -> Sort.by("releaseDate").descending();
        };
    }

}
