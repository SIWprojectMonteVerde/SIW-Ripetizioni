package it.uniroma3.siw.controller;


import it.uniroma3.siw.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/prenotazioni")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @GetMapping("/")
    public String prenotazioniHome(Model model) {
        return "prenotazioniHome";
    }
}

