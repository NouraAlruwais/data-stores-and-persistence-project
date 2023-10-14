package com.udacity.jdnd.course3.critter.pet.controller;

import com.udacity.jdnd.course3.critter.pet.dto.PetDTO;
import com.udacity.jdnd.course3.critter.pet.entity.Pet;
import com.udacity.jdnd.course3.critter.pet.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    PetService petService;
    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet=convertPetDTOToEntity(petDTO);
        return convertToPetDTO(petService.savePet(pet, petDTO.getOwnerId()));
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return convertToPetDTO(petService.getPet(petId));
    }

    @GetMapping
    public List<PetDTO> getPets(){
        return convertToPetDTOList(petService.getPets());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        return convertToPetDTOList(petService.getPetsByOwner(ownerId));
    }

    private static PetDTO convertToPetDTO(Pet pet){
        PetDTO petDTO=new PetDTO();
        petDTO.setId(pet.getId());
        petDTO.setName(pet.getName());
        petDTO.setType(pet.getType());
        petDTO.setOwnerId(pet.getOwner().getId());
        petDTO.setNotes(pet.getNotes());
        petDTO.setBirthDate(pet.getBirthDate());
        return petDTO;
    }
    private static Pet convertPetDTOToEntity(PetDTO petDTO){
        Pet pet=new Pet();
        pet.setName(petDTO.getName());
        pet.setNotes(petDTO.getNotes());
        pet.setBirthDate(petDTO.getBirthDate());
        pet.setType(petDTO.getType());
        return pet;
    }
    private static List<PetDTO> convertToPetDTOList(List<Pet> pets){
        List<PetDTO> petDTOS=new ArrayList<>();
        pets.forEach(pet -> {
            PetDTO petDTO=convertToPetDTO(pet);
            petDTOS.add(petDTO);
        });
        return petDTOS;
    }
}
