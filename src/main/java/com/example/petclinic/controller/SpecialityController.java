package com.example.petclinic.controller;

import com.example.petclinic.controller.response.SpecialityDto;
import com.example.petclinic.entity.Speciality;
import com.example.petclinic.service.SpecialityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/specialities")
public class SpecialityController {

    private final SpecialityService specialityService;

    public SpecialityController(SpecialityService specialityService) {
        this.specialityService = specialityService;
    }

    @GetMapping
    public List<SpecialityDto> list() {
        return specialityService.findSpecialities().stream()
                .map(s -> new SpecialityDto(
                        s.getId(),
                        s.getName()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public SpecialityDto detail(@PathVariable Long id) {
        Speciality s = specialityService.findSpeciality(id);
        return new SpecialityDto(
                s.getId(),
                s.getName());
    }
}
