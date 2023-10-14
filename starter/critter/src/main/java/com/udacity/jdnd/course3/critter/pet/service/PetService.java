package com.udacity.jdnd.course3.critter.pet.service;

import com.udacity.jdnd.course3.critter.pet.entity.Pet;

import java.util.List;

public interface PetService {
    Pet savePet(Pet pet, long ownerId);

    Pet getPet(long petId);

    List<Pet> getPets();

    List<Pet> getPetsByOwner(long ownerId);
}
