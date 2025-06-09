package it.uniroma3.siw.model;

import java.util.List;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("STUDENTE")
public class Studente extends Utente {


    @OneToMany(mappedBy = "studente")
    private List<Prenotazione> prenotazioni;

    public List<Prenotazione> getPrenotazioni() {
        return prenotazioni;
    }

    public void setPrenotazioni(List<Prenotazione> prenotazioni) {
        this.prenotazioni = prenotazioni;
    }


}
