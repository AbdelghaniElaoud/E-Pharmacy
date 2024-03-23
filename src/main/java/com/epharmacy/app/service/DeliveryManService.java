package com.epharmacy.app.service;

import com.epharmacy.app.model.Customer;
import com.epharmacy.app.model.DeliveryMan;
import com.epharmacy.app.repository.CustomerRepository;
import com.epharmacy.app.repository.DeliveryManRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeliveryManService {
    private final DeliveryManRepository repository;

    public DeliveryManService(DeliveryManRepository repository) {
        this.repository = repository;
    }

    public Optional<DeliveryMan> findById(Long id){
        return repository.findById(id);
    }
    public List<DeliveryMan> getAllCategories(){
        return repository.findAll();
    }

    public DeliveryMan save(DeliveryMan item){
        return repository.save(item);
    }
    public void delete(DeliveryMan item){
        repository.delete(item);
    }
    public void delete(Iterable<DeliveryMan> items){
        repository.deleteAll(items);
    }
    public void deleteById(Long id){
        repository.deleteById(id);
    }
}
