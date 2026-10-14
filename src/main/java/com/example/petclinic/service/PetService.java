package com.example.petclinic.service;

import com.example.petclinic.entity.Pet;
import com.example.petclinic.repository.PetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
public class PetService {

    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public List<Pet> findPets() {
        return petRepository.findAll();
    }

    public List<Pet> findPetsYoungerThan(Integer year, String type) {
        if (year == null && (type == null || type.isBlank())) {
            return findPets();
        }
        if (year == null || type == null || type.isBlank()) {
            throw new IllegalArgumentException("year and type must be provided together");
        }
        return petRepository.findByTypeNameIgnoreCaseAndBirthDateAfter(
                type, LocalDateTime.now().minusYears(year));
    }

    public Pet findPet(Long petId) {
        return verifyPetExists(petId);
    }

    private Pet verifyPetExists(Long petId) {
        return petRepository.findById(petId).orElseThrow(
                () -> new NoSuchElementException("Pet does not exist")
        );
    }
}
