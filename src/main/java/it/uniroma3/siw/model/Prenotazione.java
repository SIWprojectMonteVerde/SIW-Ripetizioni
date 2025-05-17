package it.uniroma3.siw.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Prenotazione {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private LocalDateTime Inizio;
    private LocalDateTime Fine;
    @ManyToOne
    private Utente utente;
    @ManyToOne
    private Disponibilita disponibilita;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getInizio() {
        return Inizio;
    }

    public void setInizio(LocalDateTime inizio) {
        Inizio = inizio;
    }

    public LocalDateTime getFine() {
        return Fine;
    }

    public void setFine(LocalDateTime fine) {
        Fine = fine;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public Disponibilita getDisponibilita() {
        return disponibilita;
    }

    public void setDisponibilita(Disponibilita disponibilita) {
        this.disponibilita = disponibilita;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((Inizio == null) ? 0 : Inizio.hashCode());
        result = prime * result + ((Fine == null) ? 0 : Fine.hashCode());
        result = prime * result + ((disponibilita == null) ? 0 : disponibilita.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Prenotazione other = (Prenotazione) obj;
        if (Inizio == null) {
            if (other.Inizio != null)
                return false;
        } else if (!Inizio.equals(other.Inizio))
            return false;
        if (Fine == null) {
            if (other.Fine != null)
                return false;
        } else if (!Fine.equals(other.Fine))
            return false;
        if (disponibilita == null) {
            if (other.disponibilita != null)
                return false;
        } else if (!disponibilita.equals(other.disponibilita))
            return false;
        return true;
    }

}
