package it.uniroma3.siw.controller;






import it.uniroma3.siw.authentication.CustomUserPrincipal;
import it.uniroma3.siw.controller.validator.CredentialsValidator;
import it.uniroma3.siw.controller.validator.UserValidator;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Teacher;
import it.uniroma3.siw.model.Student;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.CredentialsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Collections;
import java.util.Optional;


@Controller
public class AuthenticationController {
    @Autowired
    private CredentialsService credentialsService;
    @Autowired
    private CredentialsValidator credentialsValidator;
    @Autowired
    private UserValidator userValidator;

    @GetMapping("/register")
    public String register(Model model) {
        return "registerChoice";
    }
    @GetMapping("/register/student")
    public String registerStudent(Model model) {
        model.addAttribute("credentials", new Credentials());
        model.addAttribute("user", new Student());
        model.addAttribute("userType", "student");
        return "student/registerStudent";
    }
    @GetMapping("/register/teacher")
    public String registerTeacher(Model model) {
        Credentials newCredentials = new Credentials();
        model.addAttribute("credentials",newCredentials);
        model.addAttribute("user", new Teacher());
        return "teacher/registerTeacher";
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


    @PostMapping("/register/student")
    public String saveStudent(@Valid @ModelAttribute("credentials") Credentials credentials,BindingResult credentialsBindingResult,@Valid  @ModelAttribute("user") Student student, BindingResult userBindingResult, @ModelAttribute("confirmPassword") String confirmPassword,Model model) {
        credentials.setUser(student);
        return validateNewUser(credentials, student, confirmPassword, credentialsBindingResult, userBindingResult, model);
    }



    @PostMapping("/register/teacher")
    public String registerTeacher(@Valid @ModelAttribute("credentials") Credentials credentials, BindingResult credentialsBindingResult,@Valid @ModelAttribute("user") Teacher teacher,BindingResult userBindingResult,Model model , @ModelAttribute("confirmPassword") String confirmPassword) {
        credentials.setUser(teacher);
        return validateNewUser(credentials,teacher,confirmPassword,credentialsBindingResult,userBindingResult,model);
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

    @GetMapping("/selectRole")
    public String selectRole() {
        return "selectRole";
    }

    @PostMapping("/selectRole")
    public String processRoleSelection(@RequestParam("role") String role,Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserPrincipal) {
            CustomUserPrincipal principal = (CustomUserPrincipal) authentication.getPrincipal();
            Credentials credentials = credentialsService.getCredentialsByUsername(principal.getName());
            if(credentials.isRegistrationComplete()) {
                return "redirect:/";
            }
            if (credentials!=null) {
                // Verifica che il ruolo sia valido (STUDENT o TEACHER)
                if ("STUDENT".equals(role) || "TEACHER".equals(role)) {
                    if("STUDENT".equals(role)){
                        credentials.setRole(Credentials.STUDENT_ROLE);
                    }else if("TEACHER".equals(role)){
                        Teacher teacher = new Teacher(); //DI DEFAULT USER E' DI TIPO STUDENTE, QUI SERVE EFFETTUARE IL CAMBIO E COPIARE I DATI
                        teacher.setFirstName(credentials.getUser().getFirstName());
                        teacher.setLastName(credentials.getUser().getLastName());
                        teacher.setEmail(credentials.getUser().getEmail());
                        credentials.setUser(teacher);
                        credentials.setRole(Credentials.TEACHER_ROLE);
                        User oldUser = credentials.getUser();
                        System.out.println("firstName='" + oldUser.getFirstName() + "'");
                        System.out.println("lastName='" + oldUser.getLastName() + "'");
                        System.out.println("email='" + oldUser.getEmail() + "'");
                    }

                    credentials.setRegistrationComplete(true);
                    credentialsService.saveCredentials(credentials);
                    reloadRole(credentials,principal,authentication); // ALTRIMENTI SI RISCHIA DI RIMANERE CON IL RUOLO SBAGLIATO
                    return "redirect:/";
                }
            }
        }

        return "redirect:/";
    }



    private String validateNewUser(Credentials credentials, User user, String confirmPassword, BindingResult credentialsBindingResult, BindingResult userBindingResult, Model model) {
        this.userValidator.validate(user, userBindingResult);
        if(!confirmPassword.equals(credentials.getPassword())) {
            model.addAttribute("passwordError", "Le password non corrispondono");
        }

        this.credentialsValidator.validate(credentials, credentialsBindingResult);
        if(credentialsBindingResult.hasErrors() || model.containsAttribute("passwordError")||userBindingResult.hasErrors()) {
            return user.getClass().getSimpleName().toLowerCase()+"/register"+user.getClass().getSimpleName(); //RITORNO ALLA PAGINA DI REGISTRAZIONE OPPORTUNA (ES con Teacher -> teacher/registerTeacher)
        }

        credentialsService.saveCredentials(credentials);
        return "redirect:login";
    }
    private  void reloadRole(Credentials credentials, CustomUserPrincipal principal,Authentication authentication) {
        // RELOAD DEL RUOLO
        CustomUserPrincipal newPrincipal = new CustomUserPrincipal(
                credentials.getUsername(),
                Collections.singleton(new SimpleGrantedAuthority(credentials.getRole())),
                principal.getAttributes(),
                credentials.isRegistrationComplete()
        );

        // RICREA l'Authentication aggiornata
        UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(
                newPrincipal,
                authentication.getCredentials(),
                newPrincipal.getAuthorities()
        );

        // Sostituisci quella corrente nel SecurityContext senza pulire
        SecurityContextHolder.getContext().setAuthentication(newAuth);

    }
}


