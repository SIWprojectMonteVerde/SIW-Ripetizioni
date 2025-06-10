package it.uniroma3.siw.model;

import java.util.List;
import java.util.Objects;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Listing {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotEmpty
	private String title;
	@NotEmpty
	private String description;
	@NotNull
	private Float hourlyRate;
	@ManyToOne
	private Teacher teacher;
	@OneToMany(mappedBy = "listing")
	@Cascade(CascadeType.REMOVE)
	private List<Availability> availability;
	@ManyToOne
	private Subject subject;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Float getHourlyRate() {
		return hourlyRate;
	}

	public void setHourlyRate(Float prezzoOrario) {
		this.hourlyRate = prezzoOrario;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public List<Availability> getAvailability() {
		return availability;
	}

	public void setAvailability(List<Availability> availability) {
		this.availability = availability;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	@Override
	public int hashCode() {
		return Objects.hash(teacher, title);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Listing other = (Listing) obj;
		return Objects.equals(teacher, other.teacher) && Objects.equals(title, other.title);
	}
	
	
	
}
