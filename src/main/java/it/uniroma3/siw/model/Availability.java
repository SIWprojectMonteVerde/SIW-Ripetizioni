package it.uniroma3.siw.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
@Entity
public class Availability {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	@ManyToOne
	private Listing listing;

	@OneToMany(mappedBy = "availability", cascade = jakarta.persistence.CascadeType.REMOVE)
	private List<Booking> listings;


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Listing getListing() {
		return listing;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public void setListing(Listing listing) {
		this.listing = listing;
	}

	public List<Booking> getListings() {
		return listings;
	}
	public void setListings(List<Booking> prenotazioni) {
		this.listings = prenotazioni;
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
