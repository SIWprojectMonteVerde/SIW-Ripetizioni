package it.uniroma3.siw.service;

import it.uniroma3.siw.model.Subject;
import it.uniroma3.siw.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubjectService {
    @Autowired
    private SubjectRepository repository;
    public Iterable<Subject> getAll() {
        return repository.findAll();
    }
    public Integer count() {
        return (int) repository.count();
    }

}
