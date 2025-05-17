package it.uniroma3.siw.controller;

import it.uniroma3.siw.service.PrenotazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioneController {
    @Autowired
    private PrenotazioneService prenotazioneService;
    @GetMapping("/")
    public String prenotazioniHome(Model model) {
        return "prenotazioniHome";
    }

}
