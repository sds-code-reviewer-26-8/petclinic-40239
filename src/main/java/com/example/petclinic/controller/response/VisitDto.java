package com.example.petclinic.controller.response;

import java.time.LocalDateTime;

public class VisitDto {

    private Long id;
    private LocalDateTime date;
    private String description;
    private PetDto pet;

    public VisitDto() {
    }

    public VisitDto(Long id,
            LocalDateTime date,
            String description,
            PetDto pet) {
        this.id = id;
        this.date = date;
        this.description = description;
        this.pet = pet;
    }

    /* ----- Getter / Setter ----- */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PetDto getPet() {
        return pet;
    }

    public void setPet(PetDto pet) {
        this.pet = pet;
    }
}