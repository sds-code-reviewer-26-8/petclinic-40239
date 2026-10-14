package com.example.petclinic.repository;

import com.example.petclinic.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OwnerRepository extends JpaRepository<Owner, Long> {

    List<Owner> findByLastNameStartingWithIgnoreCase(String lastName);
}
