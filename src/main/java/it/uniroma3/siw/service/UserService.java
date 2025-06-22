package it.uniroma3.siw.service;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CredentialsService credentialsService;


    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public Boolean userExistsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return null;
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return credentialsService.getCredentialsByUsername(userDetails.getUsername()).getUser();
    }

    public Credentials getCurrentUserCredentials() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return null;
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return credentialsService.getCredentialsByUsername(userDetails.getUsername());
    }
    public void updateUserPicture(User user) {
        User userToUpdate = this.getUserById(user.getId());
        userToUpdate.setPicture(user.getPicture());
        userRepository.save(userToUpdate);
    }
}
