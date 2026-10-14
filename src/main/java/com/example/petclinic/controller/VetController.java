package com.example.petclinic.controller;

import com.example.petclinic.controller.response.VetDto;
import com.example.petclinic.entity.Vet;
import com.example.petclinic.service.VetService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/vets")
public class VetController {

    private final VetService vetService;

    public VetController(VetService vetService) {
        this.vetService = vetService;
    }

    @GetMapping
    public List<VetDto> list() {
        return vetService.findVets().stream()
                .map(v -> new VetDto(
                        v.getId(),
                        v.getFirstName(),
                        v.getLastName()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public VetDto detail(@PathVariable Long id) {
        Vet v = vetService.findVet(id);
        return new VetDto(
                v.getId(),
                v.getFirstName(),
                v.getLastName());
    }
}
