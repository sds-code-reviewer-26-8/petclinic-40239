package com.example.petclinic.service;

import com.example.petclinic.entity.Visit;
import com.example.petclinic.repository.VisitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
public class VisitService {

    private final VisitRepository visitRepository;

    public VisitService(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    public List<Visit> findVisits() {
        return visitRepository.findAll();
    }

    public Visit findVisit(Long visitId) {
        return verifyVisitExists(visitId);
    }

    private Visit verifyVisitExists(Long visitId) {
        return visitRepository.findById(visitId).orElseThrow(
                () -> new NoSuchElementException("Visit does not exist")
        );
    }
}
