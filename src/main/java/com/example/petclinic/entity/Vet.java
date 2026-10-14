package com.example.petclinic.entity;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("VET")
public class Vet extends Person {
}