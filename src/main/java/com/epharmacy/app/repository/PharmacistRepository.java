package com.epharmacy.app.repository;

import com.epharmacy.app.model.Pharmacist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PharmacistRepository extends JpaRepository<Pharmacist, Long> {
}
