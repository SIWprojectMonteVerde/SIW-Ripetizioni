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
    public Subject save(Subject subject) {
        return repository.save(subject);
    }
    public Subject findById(Long id) {
        return repository.findById(id).orElse(null);
    }
    public void delete(Subject subject) {
        repository.delete(subject);
    }

}
