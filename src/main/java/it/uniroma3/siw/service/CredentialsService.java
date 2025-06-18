package it.uniroma3.siw.service;




import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Student;
import it.uniroma3.siw.repository.CredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class CredentialsService {

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    private CredentialsRepository credentialsRepository;


    public Credentials getCredentialsByUsername(String username) {
        return credentialsRepository.findByUsername(username).orElse(null);
    }
    public void saveCredentials(Credentials credentials) {
        if(credentials.getUser().getClass().equals(Student.class)) {
            credentials.setRole(Credentials.STUDENT_ROLE);
        }else{
            credentials.setRole(Credentials.TEACHER_ROLE);
        }

        credentials.setPassword(this.passwordEncoder.encode(credentials.getPassword()));
        credentialsRepository.save(credentials);
    }
    public Boolean credentialsExistsByUsername(String username) {
        return credentialsRepository.findByUsername(username).isPresent();
    }
}
