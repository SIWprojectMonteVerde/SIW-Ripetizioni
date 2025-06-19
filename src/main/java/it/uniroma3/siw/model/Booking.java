package it.uniroma3.siw.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private LocalDateTime Inizio;
    private LocalDateTime Fine; //TODO VALUTARE SE EFFFETIVAMENTE UTILI
    @ManyToOne
    private Student student;
    @ManyToOne
    private Availability availability;

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

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Availability getAvailability() {
        return availability;
    }

    public void setDisponibilita(Availability availability) {
        this.availability = availability;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((Inizio == null) ? 0 : Inizio.hashCode());
        result = prime * result + ((Fine == null) ? 0 : Fine.hashCode());
        result = prime * result + ((availability == null) ? 0 : availability.hashCode());
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
        Booking other = (Booking) obj;
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
        if (availability == null) {
            if (other.availability != null)
                return false;
        } else if (!availability.equals(other.availability))
            return false;
        return true;
    }

}
