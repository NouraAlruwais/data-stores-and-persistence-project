package com.udacity.jdnd.course3.critter.user.service;

import com.udacity.jdnd.course3.critter.user.entity.Customer;

import java.util.List;


public interface CustomerService {
     Customer saveCustomer(Customer customer, List<Long> petIds);
     List<Customer> getAllCustomers();
     Customer getOwnerByPet(Long petId);
}
