package it.uniroma3.siw.model;

import java.util.List;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("TEACHER")
public class Teacher extends User {

	@OneToMany(mappedBy = "teacher")
	private List<Listing> listings;

	public List<Listing> getListings() {
		return listings;
	}
	public void setListings(List<Listing> annunci) {
		this.listings = annunci;
	}
	
}
