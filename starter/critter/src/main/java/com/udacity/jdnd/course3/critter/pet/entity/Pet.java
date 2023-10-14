package com.udacity.jdnd.course3.critter.pet.entity;


import com.udacity.jdnd.course3.critter.pet.PetType;
import com.udacity.jdnd.course3.critter.user.entity.Customer;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private PetType type;
    private String name;
    private LocalDate birthDate;
    private String notes;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Customer owner;

    public Customer getOwner() {
        return owner;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PetType getType() {
        return type;
    }

    public void setType(PetType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
