package com.example.petclinic.service;

import com.example.petclinic.entity.Vet;
import com.example.petclinic.repository.VetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
public class VetService {

    private final VetRepository vetRepository;

    public VetService(VetRepository vetRepository) {
        this.vetRepository = vetRepository;
    }

    public List<Vet> findVets() {
        return vetRepository.findAll();
    }

    public Vet findVet(Long vetId) {
        return verifyVetExists(vetId);
    }

    private Vet verifyVetExists(Long vetId) {
        return vetRepository.findById(vetId).orElseThrow(
                () -> new NoSuchElementException("Vet does not exist")
        );
    }
}
