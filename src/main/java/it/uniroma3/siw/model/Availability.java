package it.uniroma3.siw.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Availability {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@FutureOrPresent
	private LocalDate date;
	@NotNull
	private LocalTime startTime;
	@NotNull

	private LocalTime endTime;
	@ManyToOne
	private Listing listing;

	@OneToMany(mappedBy = "availability", cascade = {CascadeType.ALL})
	private List<Booking> bookings;


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Listing getListing() {
		return listing;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public void setListing(Listing listing) {
		this.listing = listing;
	}

	public List<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}

	@Override
	public int hashCode() {
		return Objects.hash(listing, startTime);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Availability other = (Availability) obj;
		return Objects.equals(listing, other.listing) && Objects.equals(startTime, other.startTime);
	}



}
