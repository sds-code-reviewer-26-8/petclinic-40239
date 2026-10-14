package com.example.petclinic.controller.response;

public class SpecialityDto {

    private Long id;
    private String name;

    public SpecialityDto() {
    }

    public SpecialityDto(Long id, String name) {
        this.id = id;
        this.name = name;
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
}