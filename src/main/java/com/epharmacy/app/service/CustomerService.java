package com.epharmacy.app.service;

import com.epharmacy.app.model.Customer;
import com.epharmacy.app.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public Optional<Customer> findById(Long id){
        return repository.findById(id);
    }
    public List<Customer> getAllCategories(){
        return repository.findAll();
    }

    public Customer save(Customer item){
        return repository.save(item);
    }
    public void delete(Customer item){
        repository.delete(item);
    }
    public void delete(Iterable<Customer> items){
        repository.deleteAll(items);
    }
    public void deleteById(Long id){
        repository.deleteById(id);
    }
}
