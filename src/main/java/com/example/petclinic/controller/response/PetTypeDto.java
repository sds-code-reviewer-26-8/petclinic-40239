package com.example.petclinic.controller.response;

public class PetTypeDto {

    private Long id;
    private String name;

    /** 직렬화·프레임워크용 기본 생성자 */
    public PetTypeDto() {
    }

    public PetTypeDto(Long id, String name) {
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