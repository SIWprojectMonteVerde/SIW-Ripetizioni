package it.uniroma3.siw.controller;

import it.uniroma3.siw.model.Availability;
import it.uniroma3.siw.model.Listing;
import it.uniroma3.siw.model.Teacher;
import it.uniroma3.siw.service.ListingService;
import it.uniroma3.siw.service.SubjectService;
import it.uniroma3.siw.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ListingController {


    @Autowired
    private ListingService listingService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private UserService userService;
    //VISUALIZZAZIONE ANNUNCI
    @GetMapping("/listings")
    public String showListings(Model model) {
        model.addAttribute("listings", listingService.findAll());
        return "listings";
    }
    @GetMapping("/listings/{id}")
    public String showListing(@PathVariable("id") Long id, Model model) {
        model.addAttribute("listing", listingService.findByIdWithAvailability(id)); //USO PER CARICARE TUTTE LE DISPONIBILTA' ALTTRIMENTI ESSSENDO DI DEFAULT LAZY DAREBBE PROBLEMI
        return "listing";
    }

    @GetMapping("/teacher/myListings")
    public String showUserListings(Model model) {
        model.addAttribute("listings", listingService.findByTeacher(userService.getCurrentUser().getId()));
        return "teacher/myListings";
    }

    //CREAZIONE ANNUNCI
    @GetMapping("/teacher/formNewListing")
    public String createNewListing(Model model, HttpSession session) {
        Listing listing = new Listing();
        session.setAttribute("listing", listing);
        model.addAttribute("listing", listing);
        model.addAttribute("subjects", subjectService.getAll());
        return "teacher/formNewListing";
    }

    @PostMapping("/teacher/addListing")
    public String addListing(@ModelAttribute("listing") Listing listing, HttpSession session) {
        //TODO VALIDAZIONE
        //TODO settare insgnante(devo un attimo capire come organizzare le classi)
        listing.setId(null); //PER QUESTIONI DI SICUREZZA (NEL FORM C'E' UN CAMPO HIDDEN ID, L'UTENTE POTREBBE MODIFICARLO)
        listing.setTeacher((Teacher) userService.getCurrentUser());
        Listing sessionListing =(Listing)session.getAttribute("listing");//TODO VERIFICARE CHE SIA UN BUON APPROCCIO, VALUTARE SE MANTENERE IN SESSIONE SOLO LA LISTA DELLE DISPNIBILTA'
        sessionListing.getAvailability()
                .forEach(listing::addAvailability);
        listingService.save(listing);
        session.removeAttribute("listing");
        return "redirect:/listings/"+ listing.getId();
    }


    @GetMapping("/teacher/formNewListing/addAvailability")
    public String addAvailabilityToNewListing(Model model, HttpSession session) {
        prepareNewAvailability(model, session);
        return "teacher/formNewAvailability";
    }

    private void prepareNewAvailability(Model model, HttpSession session) {
        Listing listing = (Listing) session.getAttribute("listing");
        model.addAttribute("availability", new Availability());
        model.addAttribute("listing", listing);
    }


    @PostMapping("/teacher/formNewListing/addAvailability")
    public String addAvailabilityToNewListing(@ModelAttribute("availability") Availability availability, HttpSession session, Model model) {
        addAvailabilityToCurrentListing(availability, session, model);
        model.addAttribute("subjects", subjectService.getAll());
        return "teacher/formNewListing";
    }




    @GetMapping("/teacher/formNewListing/removeAvailability/{index}")
    public String removeAvailabilityFromNewListing(@PathVariable("index") int index, HttpSession session, Model model) {
        removeAvailabilityFromCurrentListing(index, session, model);
        model.addAttribute("subjects", subjectService.getAll());
        return "teacher/formNewListing";
    }




    //MODIFICA ANNUNCI
    @GetMapping("/teacher/formUpdateListing/{id}")
    public String updateListing(@PathVariable("id") Long id, HttpSession session,Model model) {
        Listing currentListing = listingService.findByIdWithAvailability(id);
        if(session.getAttribute("listing") == null) {
            System.out.println("Listing not found");
            session.setAttribute("listing", currentListing);
        }else{
            currentListing = (Listing) session.getAttribute("listing");
        }
        model.addAttribute("subjects", subjectService.getAll());
        model.addAttribute("listing", currentListing); //TODO VERIFICARE CHE L'INSEGNANTE SIA IL PROPIETARIO DELL'ANNUANCIO
        return "teacher/formUpdateListing";
    }
    @PostMapping("/teacher/updateListing")
    public String updateListing(@ModelAttribute("listing") Listing listing,HttpSession session){ //TODO VALUTARE SE USARE PARAMETRO ID
        Listing old = listingService.findById(listing.getId());
        if(!old.getTeacher().getId().equals(userService.getCurrentUser().getId())) {//VERIFICO CHE SIA IL PROPIETARIO
            return "redirect:/teacher/myListings";
        }
        Listing sessionListing = (Listing)session.getAttribute("listing");
        listing.setAvailability(sessionListing.getAvailability());
        listingService.update(listing);
        session.removeAttribute("listing");
        return "redirect:/listings/"+ listing.getId();
    }

    @GetMapping("/teacher/formUpdateListing/addAvailability")
    public String addAvailabilityToListing(Model model, HttpSession session) {
        prepareNewAvailability(model, session);
        return "teacher/formNewAvailability";
    }
    @PostMapping("/teacher/formUpdateListing/addAvailability")
    public String addAvailabilityToListing(@ModelAttribute("availability") Availability availability, HttpSession session, Model model) {
        addAvailabilityToCurrentListing(availability, session, model);
        model.addAttribute("subjects", subjectService.getAll());
        return "redirect:/teacher/formUpdateListing/"+availability.getListing().getId();
    }

    @GetMapping("/teacher/formUpdateListing/removeAvailability/{index}")
    public String removeAvailabilityFromListing(@PathVariable("index") int index, HttpSession session, Model model) {
        removeAvailabilityFromCurrentListing(index, session, model);
        model.addAttribute("subjects", subjectService.getAll());
        return "teacher/formUpdateListing"; //TODO AGGIORNARE PER REDIRECT
    }

    //METODI SESSIONE

    @PostMapping("/teacher/updateSessionListing")
    @ResponseBody
    public ResponseEntity<String> updateSession(@ModelAttribute("listing") Listing formListing, HttpSession session) {
        Listing sessionListing = (Listing) session.getAttribute("listing");
        if (sessionListing != null) {
            // Preserva le availability esistenti

            // Aggiorna i dati dal form
            sessionListing.setTitle(formListing.getTitle());
            sessionListing.setDescription(formListing.getDescription());
            sessionListing.setSubject(formListing.getSubject());
            sessionListing.setHourlyRate(formListing.getHourlyRate());

            session.setAttribute("listing", sessionListing);
        }
        return ResponseEntity.ok("Updated");
    }


    //UTILITIES
    private void removeAvailabilityFromCurrentListing(int index, HttpSession session, Model model) {
        Listing listing = (Listing) session.getAttribute("listing");
        if (listing != null && index >= 0 && index < listing.getAvailability().size()) {
            listing.getAvailability().remove(index);
        }
        session.setAttribute("listing", listing);
        model.addAttribute("listing", listing);
    }

    private void addAvailabilityToCurrentListing(Availability availability, HttpSession session, Model model) {
        Listing listing = (Listing) session.getAttribute("listing");
        listing.getAvailability().add(availability);
        availability.setListing(listing);
        session.setAttribute("listing", listing); // AGGIUNGI QUESTA RIGA
        model.addAttribute("listing", listing);
    }
}
