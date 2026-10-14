package com.example.petclinic.service;

import com.example.petclinic.entity.Speciality;
import com.example.petclinic.repository.SpecialityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
public class SpecialityService {

    private final SpecialityRepository specialityRepository;

    public SpecialityService(SpecialityRepository specialityRepository) {
        this.specialityRepository = specialityRepository;
    }

    public List<Speciality> findSpecialities() {
        return specialityRepository.findAll();
    }

    public Speciality findSpeciality(Long specialityId) {
        return verifySpecialityExists(specialityId);
    }

    private Speciality verifySpecialityExists(Long specialityId) {
        return specialityRepository.findById(specialityId).orElseThrow(
                () -> new NoSuchElementException("Speciality does not exist")
        );
    }
}
