package it.uniroma3.siw.controller.validator;

import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if (user.getEmail()!= null && userService.userExistsByEmail(user.getEmail())) {
            errors.rejectValue("email", "email.alreadyUsed");
        }
    }
}
