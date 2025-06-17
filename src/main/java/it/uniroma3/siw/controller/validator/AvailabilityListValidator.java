package it.uniroma3.siw.controller.validator;

import it.uniroma3.siw.model.Availability;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import java.time.LocalDate;
import org.springframework.validation.Validator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component
public class AvailabilityListValidator implements Validator {

    @Autowired
    private AvailabiltyValidator availabilityValidator;

    @Override
    public boolean supports(Class<?> clazz) {
        return List.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        @SuppressWarnings("unchecked")
        List<Availability> availabilities = (List<Availability>) target;

        // Validazione della lista nel suo insieme
        if (availabilities == null || availabilities.isEmpty()) {
            errors.reject( "availabilities.empty");
            return;
        }

        // Filtro solo le availability con dati non nulli/vuoti
        List<Availability> validAvailabilities = availabilities.stream()
                .filter(av -> av.getDate() != null && av.getStartTime() != null && av.getEndTime() != null)
                .collect(Collectors.toList());

        if (validAvailabilities.isEmpty()) {
            errors.rejectValue("availabilities", "availabilities.empty", "Devi inserire almeno una disponibilità valida");
            return;
        }

        // Validazione di ogni singola availability
        for (int i = 0; i < availabilities.size(); i++) {
            Availability availability = availabilities.get(i);

            // Salta le availability vuote
            if (availability.getDate() == null && availability.getStartTime() == null && availability.getEndTime() == null) {
                continue;
            }

            // Valida la singola availability
            try {
                errors.pushNestedPath("availabilities[" + i + "]");
                ValidationUtils.invokeValidator(availabilityValidator, availability, errors);
            } finally {
                errors.popNestedPath();
            }
        }


    }

    private void validateNoOverlappingTimes(List<Availability> availabilities, Errors errors) {
        for (int i = 0; i < availabilities.size(); i++) {
            for (int j = i + 1; j < availabilities.size(); j++) {
                Availability a1 = availabilities.get(i);
                Availability a2 = availabilities.get(j);

                if (a1.getDate().equals(a2.getDate()) && timesOverlap(a1, a2)) {
                    errors.rejectValue("availabilities", "availability.overlap",
                            "Sovrapposizione oraria il giorno " + a1.getDate());
                }
            }
        }
    }

    private void validateMaxAvailabilitiesPerDay(List<Availability> availabilities, Errors errors) {
        Map<LocalDate, Long> countPerDay = availabilities.stream()
                .collect(Collectors.groupingBy(
                        Availability::getDate,
                        Collectors.counting()));

        countPerDay.forEach((date, count) -> {
            if (count > 5) { // Esempio: max 5 slot per giorno
                errors.rejectValue("availabilities", "availabilities.tooMany",
                        "Massimo 5 disponibilità per giorno (" + date + ")");
            }
        });
    }

    private boolean timesOverlap(Availability a1, Availability a2) {
        return a1.getStartTime().isBefore(a2.getEndTime()) &&
                a2.getStartTime().isBefore(a1.getEndTime());
    }
}