package it.uniroma3.siw.model;

import java.util.List;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("STUDENT")
public class Student extends User {


    @OneToMany(mappedBy = "student")
    private List<Booking> bookings;

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> prenotazioni) {
        this.bookings = prenotazioni;
    }


}
