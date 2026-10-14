package com.example.petclinic.controller.response;

import java.time.LocalDateTime;

public class PetDto {

    private Long id;
    private String name;
    private LocalDateTime birthDate;
    private PetTypeDto type;
    private OwnerDto owner;

    public PetDto() {
    }

    public PetDto(Long id,
            String name,
            LocalDateTime birthDate,
            PetTypeDto type,
            OwnerDto owner) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.type = type;
        this.owner = owner;
    }

    /* ----- Getter / Setter ----- */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDateTime birthDate) {
        this.birthDate = birthDate;
    }

    public PetTypeDto getType() {
        return type;
    }

    public void setType(PetTypeDto type) {
        this.type = type;
    }

    public OwnerDto getOwner() {
        return owner;
    }

    public void setOwner(OwnerDto owner) {
        this.owner = owner;
    }
}