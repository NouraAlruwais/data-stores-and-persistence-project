package com.udacity.jdnd.course3.critter.user.service.impl;

import com.udacity.jdnd.course3.critter.exception.PetException;
import com.udacity.jdnd.course3.critter.pet.entity.Pet;
import com.udacity.jdnd.course3.critter.pet.repository.PetRepository;
import com.udacity.jdnd.course3.critter.user.entity.Customer;
import com.udacity.jdnd.course3.critter.user.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.user.service.CustomerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

   private final CustomerRepository customerRepository;
    private final PetRepository petRepository;
    public CustomerServiceImpl(CustomerRepository customerRepository, PetRepository petRepository) {
        this.customerRepository = customerRepository;
        this.petRepository = petRepository;
    }

    @Override
    public Customer saveCustomer(Customer customer,List<Long> petIds) {
        List<Pet> pets=new ArrayList<>();
        if (petIds !=null && !petIds.isEmpty()){
        petIds.forEach(id->{
            Pet pet=petRepository.findById(id).orElseThrow(() -> new PetException("Pet with id: "+ id+" not found"));
            pets.add(pet);
        });}
        customer.setPets(pets);
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getOwnerByPet(Long petId) {
        return petRepository.findById(petId).orElseThrow(() -> new PetException("Pet with id: "+ petId+" not found"))
                .getOwner();
    }

}
