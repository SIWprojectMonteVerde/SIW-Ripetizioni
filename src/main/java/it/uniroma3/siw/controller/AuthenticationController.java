package it.uniroma3.siw.controller;






import it.uniroma3.siw.controller.validator.CredentialsValidator;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Insegnante;
import it.uniroma3.siw.model.Studente;
import it.uniroma3.siw.service.CredentialsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class AuthenticationController {
    @Autowired
    private CredentialsService credentialsService;
    @Autowired
    private CredentialsValidator credentialsValidator;

    @GetMapping("/register")
    public String register(Model model) {
        return "registerChoice";
    }
    @GetMapping("/register/studente")
    public String registerStudent(Model model) {
        Credentials newCredentials = new Credentials();
        newCredentials.setUser(new Studente());;
        model.addAttribute("credentials",newCredentials);
        return "register";
    }
    @GetMapping("/register/insegnante")
    public String registerInsegnante(Model model) {
        Credentials newCredentials = new Credentials();
        newCredentials.setUser(new Insegnante());;
        model.addAttribute("credentials",newCredentials);
        return "register";
    }
    @GetMapping("/login")
    public String login(Model model) {
        return "login.html";
    }


    @GetMapping("/success")
    public String defaultAfterLogin(Model model) {
        UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Credentials credentials = credentialsService.getCredentialsByUsername(userDetails.getUsername());
        return "redirect:/";
    }
    @PostMapping("/register/studente")
    public String registerPost(@Valid @ModelAttribute("credentials") Credentials credentials, BindingResult credentialsBindingResult, Model model , @ModelAttribute("confirm") String confirmPassword) {
        // TODO VALIDAZIONE

        //TODO VALUTARE SE DIVIDERE USER E CREDENTIALS
        if(!confirmPassword.equals(credentials.getPassword())) {
            model.addAttribute("passwordError", "Le password non corrispondono");
        }

        this.credentialsValidator.validate(credentials, credentialsBindingResult);
        if(credentialsBindingResult.hasErrors() || model.containsAttribute("passwordError")) {
            return "register";
        }

        credentialsService.saveCredentials(credentials);
        return "redirect:login";
    }
    @GetMapping(value = "/")
    public String index(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return "index.html";
        }
        else {
            UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Credentials credentials = credentialsService.getCredentialsByUsername(userDetails.getUsername());
            if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
                return "admin/indexAdmin.html";
            }
        }
        return "index.html";
    }

}


