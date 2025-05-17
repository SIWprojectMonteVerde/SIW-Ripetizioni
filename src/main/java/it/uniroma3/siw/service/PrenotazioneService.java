package it.uniroma3.siw.service;

import it.uniroma3.siw.model.Prenotazione;
import it.uniroma3.siw.repository.PrenotazioneRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrenotazioneService {
    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    public Prenotazione save(Prenotazione prenotazione) {
        return prenotazioneRepository.save(prenotazione);
    }
    public Iterable<Prenotazione> findAll() {
        return prenotazioneRepository.findAll();
    }
    public void delete(Prenotazione prenotazione) {
        prenotazioneRepository.delete(prenotazione);
    }
    public Prenotazione findById(Long id) {
        return prenotazioneRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Prenotazione con ID " + id + " non trovato"));
    }
}
