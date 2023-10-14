package com.udacity.jdnd.course3.critter.pet.service.impl;

import com.udacity.jdnd.course3.critter.exception.CustomerException;
import com.udacity.jdnd.course3.critter.exception.PetException;
import com.udacity.jdnd.course3.critter.pet.dto.PetDTO;
import com.udacity.jdnd.course3.critter.pet.entity.Pet;
import com.udacity.jdnd.course3.critter.pet.repository.PetRepository;
import com.udacity.jdnd.course3.critter.pet.service.PetService;
import com.udacity.jdnd.course3.critter.user.entity.Customer;
import com.udacity.jdnd.course3.critter.user.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PetServiceImpl implements PetService {
    private final PetRepository petRepository;
    private final CustomerRepository customerRepository;

    public PetServiceImpl(PetRepository petRepository, CustomerRepository customerRepository) {
        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public Pet savePet(Pet pet, long ownerId) {
        Customer customer=customerRepository.findById(ownerId).orElseThrow(() ->
                new CustomerException("Owner with id: "+ ownerId+" not found"));

        pet.setOwner(customer);
        Pet pet1=petRepository.save(pet);

        List<Pet> petList=new ArrayList<>();
        petList.add(pet1);
        customer.setPets(petList);
        customerRepository.save(customer);

        return pet1;
    }

    @Override
    public Pet getPet(long petId) {
        return petRepository.findById(petId).orElseThrow(() -> new PetException("Pet with id: "+ petId+" not found"));
    }

    @Override
    public List<Pet> getPets() {
        return petRepository.findAll();
    }

    @Override
    public List<Pet> getPetsByOwner(long ownerId) {
        customerRepository.findById(ownerId).orElseThrow(() -> new CustomerException("Owner with id: "+ ownerId+" not found"));
        return petRepository.getAllByOwnerId(ownerId);
    }

}
