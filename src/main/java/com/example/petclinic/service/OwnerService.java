package com.example.petclinic.service;

import com.example.petclinic.entity.Owner;
import com.example.petclinic.repository.OwnerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
public class OwnerService {

    private final OwnerRepository ownerRepository;

    public OwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    public List<Owner> findOwners() {
        return ownerRepository.findAll();
    }

    public Owner findOwner(Long ownerId) {
        return verifyOwnerExists(ownerId);
    }

    private Owner verifyOwnerExists(Long ownerId) {
        return ownerRepository.findById(ownerId).orElseThrow(
                () -> new NoSuchElementException("Owner does not exist")
        );
    }
}
