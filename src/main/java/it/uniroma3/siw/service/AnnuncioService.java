package it.uniroma3.siw.service;

import it.uniroma3.siw.model.Annuncio;
import it.uniroma3.siw.repository.AnnuncioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnnuncioService {

    @Autowired
    private AnnuncioRepository annuncioRepository;

    public Iterable<Annuncio> findAll() {
        return annuncioRepository.findAll();
    }
    public Annuncio save(Annuncio annuncio) {
        return annuncioRepository.save(annuncio);
    }
    public Annuncio findById(Long id) {
        return annuncioRepository.findById(id).orElse(null);
    }
    public Iterable<Annuncio> findByInsegnante(Long id) {
        return annuncioRepository.findByInsegnante_Id(id);
    }
}
