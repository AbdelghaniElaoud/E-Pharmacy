package com.epharmacy.app.service;

import com.epharmacy.app.enums.UserRole;
import com.epharmacy.app.model.Role;
import com.epharmacy.app.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository repository;

    public RoleService(RoleRepository repository) {
        this.repository = repository;
    }

    public Optional<Role> findById(Long id){
        return repository.findById(id);
    }
    public List<Role> getAllRoles(){
        return repository.findAll();
    }

    public Role save(Role item){
        return repository.save(item);
    }
    public void delete(Role item){
        repository.delete(item);
    }
    public void delete(Iterable<Role> items){
        repository.deleteAll(items);
    }
    public void deleteById(Long id){
        repository.deleteById(id);
    }

    public Optional<Role> findByName(UserRole userRole) {
        return repository.findByName(userRole);
    }
}
