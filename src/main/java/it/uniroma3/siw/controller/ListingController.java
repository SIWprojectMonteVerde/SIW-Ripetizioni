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
    public String mostraAnnunci(Model model) {
        model.addAttribute("listings", listingService.findAll());
        return "listings";
    }
    @GetMapping("/listings/{id}")
    public String mostraAnnuncio(@PathVariable("id") Long id, Model model) {
        model.addAttribute("listing", listingService.findById(id));
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
    public String addListing(@ModelAttribute("listing") Listing listing,HttpSession session) {
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
    public String addAvailabilty(Model model,HttpSession session) {
        Listing listing = (Listing) session.getAttribute("listing");
        model.addAttribute("availability", new Availability());
        model.addAttribute("listing", listing);
        return "teacher/formNewAvailabilty";
    }
    @PostMapping("/teacher/formNewListing/addAvailability")
    public String addAvailabilty(@ModelAttribute("availability") Availability availability, HttpSession session, Model model) {
        Listing listing = (Listing) session.getAttribute("listing");
        listing.getAvailability().add(availability);
        availability.setListing(listing);
        session.setAttribute("listing", listing); // AGGIUNGI QUESTA RIGA
        model.addAttribute("listing", listing);
        model.addAttribute("subjects", subjectService.getAll());
        return "teacher/formNewListing";
    }
    @GetMapping("/teacher/formNewListing/removeAvailability/{index}")
    public String removeAvailabilityFromSession(@PathVariable("index") int index, HttpSession session, Model model) {
        Listing listing = (Listing) session.getAttribute("listing");
        if (listing != null && index >= 0 && index < listing.getAvailability().size()) {
            listing.getAvailability().remove(index);
        }
        session.setAttribute("listing", listing); //salva il listing, non availability
        model.addAttribute("listing", listing);
        model.addAttribute("subjects", subjectService.getAll());
        return "teacher/formNewListing";
    }
    @PostMapping("/teacher/formNewListing/saveToSession")
    public String saveToSession(@ModelAttribute("listing") Listing formListing, HttpSession session) {
        // Ora formListing contiene TUTTI i dati dal form!
        session.setAttribute("listing", formListing);
        return "redirect:/teacher/formNewListing/addAvailability";
    }


    //MODIFICA ANNUNCI
    @GetMapping("/teacher/formUpdateListing/{id}")
    public String updateListing(@PathVariable("id") Long id, Model model) {
        model.addAttribute("subjects", subjectService.getAll());
        model.addAttribute("listing", listingService.findById(id)); //TODO VERIFICARE CHE L'INSEGNANTE SIA IL PROPIETARIO DELL'ANNUANCIO
        return "teacher/formUpdateListing";
    }
    @PostMapping("/teacher/modificaAnnuncio")
    public String modificaAnnuncio(@ModelAttribute("annuncio") Listing listing){
        System.out.println(listing.getId());
        Listing old = listingService.findById(listing.getId());
        if(!old.getTeacher().getId().equals(userService.getCurrentUser().getId())) {//VERIFICO CHE SIA IL PROPIETARIO
            return "redirect:/teacher/myListings";
        }
        listingService.update(listing);
        return "redirect:/listings/"+ listing.getId();
    }

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

            // Ripristina le availability;

            session.setAttribute("listing", sessionListing);
        }
        return ResponseEntity.ok("Updated");
    }

}
