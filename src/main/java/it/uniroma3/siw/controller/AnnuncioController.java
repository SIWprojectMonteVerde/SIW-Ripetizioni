package it.uniroma3.siw.controller;

import it.uniroma3.siw.model.Annuncio;
import it.uniroma3.siw.service.AnnuncioService;
import it.uniroma3.siw.service.MateriaService;
import jakarta.validation.Valid;
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
    @Autowired
    private MateriaService materiaService;

    @GetMapping("/annunci")
    public String mostraAnnunci(Model model) {
        model.addAttribute("annunci", annuncioService.findAll());
        return "annunci";
    }
    @GetMapping("/annunci/{id}")
    public String mostraAnnuncio(@PathVariable("id") Long id, Model model) {
        model.addAttribute("annuncio", annuncioService.findById(id));
        return "annuncio";
    }
    @GetMapping("/formAnnuncio")
    public String formAnnuncio(Model model) {
        model.addAttribute("annuncio", new Annuncio());
        return "formAnnuncio";
    }

    @PostMapping("/annunci")
    public String aggiungiAnnuncio(@ModelAttribute("annuncio") Annuncio annuncio, BindingResult bindingResult) {
        //TODO VALIDAZIONE
        annuncio.setId(null);//PER SICUREZZA NON CI SI PUO' FIDARE DELL'INPUT UTENTE
        annuncioService.save(annuncio);
        return "redirect:annunci/"+annuncio.getId();
    }

    @GetMapping("/gestisciAnnunci")
    public String getGestisciAnnunci(Model model) {
        //TODO
      return "gestisciAnnunci";
    }
    @GetMapping("iMieiAnnunci")
    public String showAnnunciUtente(Model model) {
        //TODO trovare l'id utente tramite il prinicipal
        Long id = 0L;
        model.addAttribute("annunci", annuncioService.findByInsegnante(id));
        return "iMieiAnnunci";
    }
    @GetMapping("/teacher/creaNuovoAnnuncio")
    public String creaNuovoAnnuncio(Model model) {
        model.addAttribute("annuncio", new Annuncio());
        model.addAttribute("materie", materiaService.getAll());
        return "insegnante/formNuovoAnnuncio";
    }
    @PostMapping("/teacher/aggiungiAnnuncio")
    public String aggiungiAnnuncio(@ModelAttribute("annuncio") Annuncio annuncio){
        //TODO VALIDAZIONE
        //TODO settare insgnante(devo un attimo capire come organizzare le classi)
        annuncioService.save(annuncio);
        return "redirect:/annunci/"+annuncio.getId();
    }
    @GetMapping("/teacher/aggiornaAnnuncio/{id}")
    public String aggiornaAnnuncio(@PathVariable("id") Long id,Model model) {
        model.addAttribute("materie", materiaService.getAll());
        model.addAttribute("annuncio", annuncioService.findById(id)); //TODO VERIFICARE CHE L'INSEGNANTE SIA IL PROPIETARIO DELL'ANNUANCIO
        return "/insegnante/formAggiornaAnnuncio";
    }
    @PostMapping("/teacher/modificaAnnuncio")
    public String modificaAnnuncio(@ModelAttribute("annuncio") Annuncio annuncio){
        annuncioService.save(annuncio);//TODO VERIFICARE CHE L'INSEGNANTE SIA PROPIETARIO DELL'ANNUNCIO
        return "redirect:/annunci/"+annuncio.getId();
    }

}
