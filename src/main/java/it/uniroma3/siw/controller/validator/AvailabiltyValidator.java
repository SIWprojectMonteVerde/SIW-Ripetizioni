package it.uniroma3.siw.controller.validator;





import it.uniroma3.siw.model.Availability;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.Duration;
import java.util.Set;

@Component
public class AvailabiltyValidator implements Validator {


    @Override
    public boolean supports(Class<?> clazz) {
        return Availability.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Availability availability = (Availability) target;
        if(availability.getStartTime()!=null && availability.getEndTime()!=null && availability.getStartTime().isAfter(availability.getEndTime())) {
            errors.rejectValue("startTime", "availability.startTime.after");
        }
        if(availability.getStartTime()!=null && availability.getEndTime()!=null && Duration.between(availability.getStartTime(), availability.getEndTime()).toMinutes() < 30) {
            errors.reject("availability.lessThan30minutes");
        }
        if(availability.getStartTime()!=null && availability.getEndTime()!=null && Duration.between(availability.getStartTime(), availability.getEndTime()).toHours() > 6) {
            errors.reject("availability.moreThan6hours");
        }
    }
}
