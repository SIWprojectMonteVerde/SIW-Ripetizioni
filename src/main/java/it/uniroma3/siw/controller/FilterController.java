package it.uniroma3.siw.controller;

import it.uniroma3.siw.model.Listing;
import it.uniroma3.siw.service.AvaialabiltyService;
import it.uniroma3.siw.service.ListingService;
import it.uniroma3.siw.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
class FilterController {
    @Autowired
    private SubjectService subjectService;

    @Autowired
    private ListingService listingService;

    @Autowired
    private AvaialabiltyService availabilityService;



}
