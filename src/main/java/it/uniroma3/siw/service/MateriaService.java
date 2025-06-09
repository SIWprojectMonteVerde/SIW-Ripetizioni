package it.uniroma3.siw.service;

import it.uniroma3.siw.model.Materia;
import it.uniroma3.siw.repository.MateriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MateriaService {
    @Autowired
    private MateriaRepository repository;
    public Iterable<Materia> getAll() {
        return repository.findAll();
    }
}
