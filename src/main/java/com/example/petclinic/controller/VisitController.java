package com.example.petclinic.controller;

import com.example.petclinic.controller.response.OwnerDto;
import com.example.petclinic.controller.response.PetDto;
import com.example.petclinic.controller.response.PetTypeDto;
import com.example.petclinic.controller.response.VisitDto;
import com.example.petclinic.entity.Visit;
import com.example.petclinic.service.VisitService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/visits")
public class VisitController {

        private final VisitService visitService;

        public VisitController(VisitService visitService) {
                this.visitService = visitService;
        }

        @GetMapping
        public List<VisitDto> list() {
                return visitService.findVisits().stream()
                                .map(v -> {
                                        PetTypeDto type = new PetTypeDto(
                                                        v.getPet().getType().getId(),
                                                        v.getPet().getType().getName());
                                        OwnerDto owner = new OwnerDto(
                                                        v.getPet().getOwner().getId(),
                                                        v.getPet().getOwner().getFirstName(),
                                                        v.getPet().getOwner().getLastName(),
                                                        v.getPet().getOwner().getAddress(),
                                                        v.getPet().getOwner().getCity(),
                                                        v.getPet().getOwner().getTelephone());
                                        PetDto pet = new PetDto(
                                                        v.getPet().getId(),
                                                        v.getPet().getName(),
                                                        v.getPet().getBirthDate(),
                                                        type,
                                                        owner);
                                        return new VisitDto(
                                                        v.getId(),
                                                        v.getDate(),
                                                        v.getDescription(),
                                                        pet);
                                })
                                .collect(Collectors.toList());
        }

        @GetMapping("/{id}")
        public VisitDto detail(@PathVariable Long id) {
                Visit v = visitService.findVisit(id);

                PetTypeDto type = new PetTypeDto(
                                v.getPet().getType().getId(),
                                v.getPet().getType().getName());

                OwnerDto owner = new OwnerDto(
                                v.getPet().getOwner().getId(),
                                v.getPet().getOwner().getFirstName(),
                                v.getPet().getOwner().getLastName(),
                                v.getPet().getOwner().getAddress(),
                                v.getPet().getOwner().getCity(),
                                v.getPet().getOwner().getTelephone());
                PetDto pet = new PetDto(
                                v.getPet().getId(),
                                v.getPet().getName(),
                                v.getPet().getBirthDate(),
                                type,
                                owner);

                return new VisitDto(
                                v.getId(),
                                v.getDate(),
                                v.getDescription(),
                                pet);
        }
}
