package com.example.petclinic.repository;

import com.example.petclinic.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {

    List<Pet> findByTypeNameIgnoreCaseAndBirthDateAfter(String typeName, LocalDateTime birthDate);
}
