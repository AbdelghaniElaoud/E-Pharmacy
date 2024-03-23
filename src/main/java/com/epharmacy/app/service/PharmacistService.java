package com.epharmacy.app.service;

import com.epharmacy.app.model.Pharmacist;
import com.epharmacy.app.repository.PharmacistRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PharmacistService {
    private final PharmacistRepository repository;

    public PharmacistService(PharmacistRepository repository) {
        this.repository = repository;
    }

    public Optional<Pharmacist> findById(Long id){
        return repository.findById(id);
    }
    public List<Pharmacist> getAllCategories(){
        return repository.findAll();
    }

    public Pharmacist save(Pharmacist item){
        return repository.save(item);
    }
    public void delete(Pharmacist item){
        repository.delete(item);
    }
    public void delete(Iterable<Pharmacist> items){
        repository.deleteAll(items);
    }
    public void deleteById(Long id){
        repository.deleteById(id);
    }
}
