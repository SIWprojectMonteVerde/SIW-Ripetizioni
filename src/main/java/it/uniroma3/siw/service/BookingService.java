package it.uniroma3.siw.service;

import it.uniroma3.siw.model.Booking;
import it.uniroma3.siw.model.Student;
import it.uniroma3.siw.model.Teacher;
import it.uniroma3.siw.repository.BookingRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    public Booking save(Booking booking) {
        return bookingRepository.save(booking);
    }
    public Iterable<Booking> findAll() {
        return bookingRepository.findAll();
    }
    public void delete(Booking booking) {
        bookingRepository.delete(booking);
    }
    public Booking findById(Long id) {
        return bookingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Prenotazione con ID " + id + " non trovato"));
    }
    public Iterable<Booking> findByStudent(Student student) {
        return bookingRepository.findByStudent(student);
    }
    public Iterable<Booking> findByStudentOrderedByDate(Student student) {
        return bookingRepository.findByStudentOrderByAvailabilityDate(student);
    }
    public boolean listingHasActiveBookings(Long listingId){
        return bookingRepository.listingHasActiveBookings(listingId);
    }
    public Iterable<Booking> findByTeacher(Teacher teacher) {
        return bookingRepository.findOrdByAvailabilityListingTeacherOrderByAvailabilityDate(teacher);
    }
}
