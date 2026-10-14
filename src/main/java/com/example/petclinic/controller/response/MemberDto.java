package com.example.petclinic.controller.response;

public class MemberDto {

    private String username;
    private Boolean enabled;

    public MemberDto() {
    }

    public MemberDto(String username, Boolean enabled) {
        this.username = username;
        this.enabled = enabled;
    }

    /* ----- Getter / Setter ----- */
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}