package it.uniroma3.siw.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type")
@Table(name = "users")
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotEmpty
    private String email;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String nome) {
        this.firstName = nome;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String cognome) {
        this.lastName = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
