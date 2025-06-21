package it.uniroma3.siw.controller;

import it.uniroma3.siw.controller.validator.AvailabilityListValidator;
import it.uniroma3.siw.model.Availability;
import it.uniroma3.siw.model.Dto.MateriaDto;
import it.uniroma3.siw.model.Listing;
import it.uniroma3.siw.model.Subject;
import it.uniroma3.siw.model.Teacher;
import it.uniroma3.siw.service.*;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ListingController {


    @Autowired
    private ListingService listingService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private UserService userService;
    @Autowired
    private AvaialabiltyService availabilityService;
    @Autowired
    private BookingService bookingService;

    @Autowired
    private AvailabilityListValidator availabilityListValidator;


    //VISUALIZZAZIONE ANNUNCI
    @GetMapping("/listings")
    public String showListings(@RequestParam(name = "subj") Long subjectId, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                               @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
                               @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime endTime, Model model) {



        Iterable<Listing> listings = listingService.findByCriteria(date, startTime, endTime, subjectId);

        Iterable<Listing> listings = listingService.findAll(); //sostituire con get all

        model.addAttribute("listings", listings);

        model.addAttribute("genere", buildGenreList(subjectId));
        return "listings";
    }

    @GetMapping("/listings/{id}")
    public String showListing(@PathVariable("id") Long id, Model model) {
        model.addAttribute("listing", listingService.findById(id));
        model.addAttribute("availabilities", availabilityService.findByListingIdAndNoBookings(id));

        //model.addAttribute("listing", listingService.findByIdWithAvailability(id)); //USO PER CARICARE TUTTE LE DISPONIBILTA' ALTTRIMENTI ESSSENDO DI DEFAULT LAZY DAREBBE PROBLEMI
        return "listing";
    }

    @GetMapping("/teacher/myListings")
    public String showUserListings(Model model) {
        model.addAttribute("listings", listingService.findByTeacher(userService.getCurrentUser().getId()));
        return "teacher/myListings";
    }

    //CREAZIONE ANNUNCI
    @GetMapping("/teacher/formNewListing")
    public String createNewListing(Model model) {
        Listing listing = new Listing();
        model.addAttribute("listing", listing);
        model.addAttribute("subjects", subjectService.getAll());
        return "teacher/formNewListing";
    }

    @PostMapping("/teacher/addListing")
    public String addListing(@Valid @ModelAttribute("listing") Listing listing, BindingResult bindingResult, Model model) {
        //TODO VALIDAZIONE
        //TODO settare insgnante(devo un attimo capire come organizzare le classi)
        availabilityListValidator.validate(listing.getAvailabilities(), bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("subjects", subjectService.getAll());
            model.addAttribute("listing", listing);
            return "teacher/formNewListing";
        }

        listing.setId(null); //PER QUESTIONI DI SICUREZZA (NEL FORM C'E' UN CAMPO HIDDEN ID, L'UTENTE POTREBBE MODIFICARLO)
        listing.setTeacher((Teacher) userService.getCurrentUser());
        //TODO VERIFICARE CHE SIA UN BUON APPROCCIO, VALUTARE SE MANTENERE IN SESSIONE SOLO LA LISTA DELLE DISPNIBILTA'
        listing.getAvailabilities().forEach(availability -> availability.setListing(listing));
        listingService.save(listing);

        return "redirect:/listings/" + listing.getId();
    }


    //MODIFICA ANNUNCI
    @GetMapping("/teacher/formUpdateListing/{id}")
    public String updateListing(@PathVariable("id") Long id, Model model) {
        Listing currentListing = listingService.findByIdWithAvailability(id);
        model.addAttribute("subjects", subjectService.getAll());
        model.addAttribute("listing", currentListing); //TODO VERIFICARE CHE L'INSEGNANTE SIA IL PROPIETARIO DELL'ANNUANCIO
        return "teacher/formUpdateListing";
    }

    @PostMapping("/teacher/updateListing")
    public String updateListing(@Valid @ModelAttribute("listing") Listing listing, BindingResult bindingResult, Model model) { //TODO VALUTARE SE USARE PARAMETRO ID
        Listing old = listingService.findById(listing.getId());
        if (!old.getTeacher().getId().equals(userService.getCurrentUser().getId())) {//VERIFICO CHE SIA IL PROPIETARIO
            return "redirect:/teacher/myListings";
        }
        availabilityListValidator.validate(listing.getAvailabilities(), bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("subjects", subjectService.getAll());
            model.addAttribute("listing", listing);
            return "teacher/formUpdateListing";
        }
        listingService.update(listing);
        return "redirect:/listings/" + listing.getId();
    }

    @PostMapping("/teacher/removeAvailabilityFromListing/{availability_id}/{listing_id}")
    public String removeAvailabilityFromListing(@ModelAttribute("listing") Listing temporaryListing, @PathVariable("availability_id") Long availabilityId, @PathVariable("listing_id") Long listingId, Model model) {
        Listing actual = listingService.findByIdWithAvailability(listingId);
        if (!actual.getTeacher().getId().equals(userService.getCurrentUser().getId())) { //NON CONTROLLO CHE LA DISPONIBILITA' SIA RELATIVA ALL'ANNUNCIO PERCHE' PER COME E' STRUTTURATA LA QUERY NON E' UN PROBLEMA(NON VIENE CANCELLATO NULLA)
            return "redirect:/teacher/myListings";
        }
        if (!availabilityService.findByIdWithBookings(availabilityId).getBookings().isEmpty()) {
            model.addAttribute("removeError", "Non puoi eliminare una disponibilità con prenotazioni attive.");
        } else {
            listingService.removeAvailabilityFromListing(availabilityId, listingId); //RITORNA L'OGGETTO RIMOSSO
            actual = listingService.findByIdWithAvailability(listingId); //AGGIORNO DATI PASSATI ALLA VIEW

        }
        copyTemporaryListing(temporaryListing, actual);
        model.addAttribute("subjects", subjectService.getAll());
        model.addAttribute("listing", actual);
        return "teacher/formUpdateListing";
    }

    //RIMOZIONE ANNUNCI
    @GetMapping("/teacher/removeListing/{id}")
    public String removeListing(@PathVariable("id") Long id, RedirectAttributes redirectAttributes, Model model) {
        Listing temporaryListing = listingService.findByIdWithAvailability(id);
        if (!temporaryListing.getTeacher().getId().equals(userService.getCurrentUser().getId())) {
            return "redirect:/teacher/myListings";
        }
        if (bookingService.listingHasActiveBookings(id)) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Non è possibile eliminare un annuncio che ha prenotazioni attive"); //Uso RredirectAttributes cosi il messaggio non si perde nel redirect
        } else {
            listingService.removeListing(id);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Annuncio eliminato con successo"); //Uso RredirectAttributes cosi il messaggio non si perde nel redirect
        }

        return "redirect:/teacher/myListings";
    }

    //UTILITIES
    private void copyTemporaryListing(Listing temporaryListing, Listing listing) {
        List<Availability> newAvailabilities = temporaryListing.getAvailabilities().stream()
                .filter(a -> a.getId() == null).toList();
        listing.getAvailabilities().addAll(newAvailabilities); //aggiungo solo quelle temporanee
        listing.setTitle(temporaryListing.getTitle());
        listing.setDescription(temporaryListing.getDescription());
        listing.setHourlyRate(temporaryListing.getHourlyRate());

    }

    private List<MateriaDto> buildGenreList(Long selectedGenreId) {
        Iterable<Subject> all = subjectService.getAll();
        Iterator<Subject> iterator = all.iterator();

        List<MateriaDto> result = new ArrayList<>();
        while (iterator.hasNext()) {
            Subject g = iterator.next();
            result.add(new MateriaDto(
                    g.getId().equals(selectedGenreId),
                    listingService.countBySubject(g),
                    g.getName(),
                    g.getId()
            ));
        }
        return result;
    }


}
