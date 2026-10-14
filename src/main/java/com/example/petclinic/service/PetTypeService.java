package com.example.petclinic.service;

import com.example.petclinic.entity.PetType;
import com.example.petclinic.repository.PetTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
public class PetTypeService {

    private final PetTypeRepository petTypeRepository;

    public PetTypeService(PetTypeRepository petTypeRepository) {
        this.petTypeRepository = petTypeRepository;
    }

    public List<PetType> findPetTypes() {
        return petTypeRepository.findAll();
    }

    public PetType findPetType(Long petTypeId) {
        return verifyPetTypeExists(petTypeId);
    }

    private PetType verifyPetTypeExists(Long petTypeId) {
        return petTypeRepository.findById(petTypeId).orElseThrow(
                () -> new NoSuchElementException("PetType does not exist")
        );
    }
}
