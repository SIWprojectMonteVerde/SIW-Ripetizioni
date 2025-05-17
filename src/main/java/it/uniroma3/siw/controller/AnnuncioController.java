package it.uniroma3.siw.controller;

import it.uniroma3.siw.model.Annuncio;
import it.uniroma3.siw.service.AnnuncioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AnnuncioController {
    @Autowired
    private AnnuncioService annuncioService;


    @GetMapping("/annunci")
    public String mostraAnnunci(Model model) {
        model.addAttribute("annunci", annuncioService.findAll());
        return "annunci";
    }
    @GetMapping("/annunci/{id}")
    public String mostraAnnuncio(@PathVariable("id") Long id, Model model) {
        model.addAttribute("annunci", annuncioService.findById(id));
        return "annuncio";
    }
    @GetMapping("/formAnnuncio")
    public String formAnnuncio(Model model) {
        model.addAttribute("annuncio", new Annuncio());
        return "formAnnuncio";
    }

    @PostMapping("/annunci")
    public String aggiungiAnnuncio(@ModelAttribute("annuncio") Annuncio annuncio, BindingResult bindingResult) {

        return "redirect:annunci/"+annuncio.getId();
    }
}
