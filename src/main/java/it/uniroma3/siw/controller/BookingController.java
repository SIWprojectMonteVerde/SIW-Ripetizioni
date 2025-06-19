package it.uniroma3.siw.controller;


import it.uniroma3.siw.model.Availability;
import it.uniroma3.siw.model.Booking;
import it.uniroma3.siw.model.Student;
import it.uniroma3.siw.service.AvaialabiltyService;
import it.uniroma3.siw.service.BookingService;
import it.uniroma3.siw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller

public class BookingController {
    @Autowired
    private BookingService bookingService;
    @Autowired
    private AvaialabiltyService avaialabiltyService;
    @Autowired
    private UserService userService;

    @GetMapping("/student/newBooking/{availabilty_id}")
    public String newBooking(@PathVariable("availabilty_id") Long availabiltyId, Model model) {
        Availability availability = avaialabiltyService.findByIdWithBookings(availabiltyId); //SERVE PER CARICARE LE PRENOTAZIONI SENZA USARE EAGER
        if(availability.getBookings().isEmpty()) {//TODO PROBABILMENTE NON SARA' PIU' UNA LISTA
            Booking booking = new Booking();
            booking.setStudent((Student) userService.getCurrentUser());//CAST POSSIBILE PERCHE' L'URL è protetto
            booking.setDisponibilita(availability);
            availability.getBookings().add(booking);
            avaialabiltyService.save(availability);
            return "redirect:/student/myBookings";
        }
        return "redirect:/listings/" + availability.getListing().getId();
    }
    @GetMapping("/student/deleteBooking/{booking_id}")
    public String deleteBooking(@PathVariable("booking_id") Long bookingId, Model model) {
        Booking booking = bookingService.findById(bookingId);
        if(booking.getStudent().getId().equals(userService.getCurrentUser().getId())) {
            bookingService.delete(booking);
        }
        return "redirect:/student/myBookings";
    }
    @GetMapping("/student/myBookings")
    public String myBookings(Model model) {
        model.addAttribute("bookings", bookingService.findAll());
        return "student/myBookings";
    }

}

