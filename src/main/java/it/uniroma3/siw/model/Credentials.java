package it.uniroma3.siw.model;

import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Credentials {

    public static final String STUDENT_ROLE = "STUDENT";
    public static final String ADMIN_ROLE = "ADMIN";
    public static final String TEACHER_ROLE = "TEACHER";


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;
    private String role;
    private boolean oauthUser = false;
    private boolean registrationComplete = true;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Student student;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Teacher teacher;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isOauthUser() {
        return oauthUser;
    }

    public void setOauthUser(boolean oauthUser) {
        this.oauthUser = oauthUser;
    }

    public boolean isRegistrationComplete() {
        return registrationComplete;
    }

    public void setRegistrationComplete(boolean registrationComplete) {
        this.registrationComplete = registrationComplete;
    }

    public User getUser() {
        if (student == null) {
            return teacher;
        }
        return student;
    }

    public void setUser(User user) {
        if (user instanceof Student) {
            this.student = (Student) user;
            this.teacher = null;
        } else if (user instanceof Teacher) {
            this.teacher = (Teacher) user;
            this.student = null;
        }
    }
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Credentials other = (Credentials) obj;
        return Objects.equals(username, other.username);
    }

}