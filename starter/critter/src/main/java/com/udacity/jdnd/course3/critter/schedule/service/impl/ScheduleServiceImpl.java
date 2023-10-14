package com.udacity.jdnd.course3.critter.schedule.service.impl;

import com.udacity.jdnd.course3.critter.exception.CustomerException;
import com.udacity.jdnd.course3.critter.exception.EmployeeException;
import com.udacity.jdnd.course3.critter.exception.PetException;
import com.udacity.jdnd.course3.critter.pet.entity.Pet;
import com.udacity.jdnd.course3.critter.pet.repository.PetRepository;
import com.udacity.jdnd.course3.critter.schedule.entity.Schedule;
import com.udacity.jdnd.course3.critter.schedule.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.schedule.service.ScheduleService;
import com.udacity.jdnd.course3.critter.user.entity.Employee;
import com.udacity.jdnd.course3.critter.user.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.user.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Service
@Transactional
public class ScheduleServiceImpl implements ScheduleService {
   private final ScheduleRepository scheduleRepository;
   private final EmployeeRepository employeeRepository;
   private final PetRepository petRepository;
   private final CustomerRepository customerRepository;
    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, EmployeeRepository employeeRepository, PetRepository petRepository, CustomerRepository customerRepository) {
        this.scheduleRepository = scheduleRepository;
        this.employeeRepository = employeeRepository;
        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public Schedule createSchedule(Schedule schedule, List<Long> petIds, List<Long> employeeIds) {
        List<Pet> pets=new ArrayList<>();
        if (petIds !=null && !petIds.isEmpty()){
            petIds.forEach(id->{
                Pet pet=petRepository.findById(id).orElseThrow(() -> new PetException("Pet with id: "+ id+" not found"));
                pets.add(pet);
            });}
        schedule.setPets(pets);

        List<Employee> employees=new ArrayList<>();
        if (employeeIds!=null && !employeeIds.isEmpty()){
            employeeIds.forEach(id->{
                Employee employee=employeeRepository.findById(id).orElseThrow(() -> new EmployeeException("Employee with id: "+id+" not found"));
                employees.add(employee);
            });}
        schedule.setEmployees(employees);

        return scheduleRepository.save(schedule);
    }

    @Override
    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    @Override
    public List<Schedule> getScheduleForCustomer(long customerId) {
        customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerException("Customer with id: "+ customerId+" not found"));

        List<Pet> pets=petRepository.getAllByOwnerId(customerId);
        return scheduleRepository.getAllByPetsIn(pets);
    }

    @Override
    public List<Schedule> getScheduleForEmployee(long employeeId) {
        Employee employee=employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeException("Employee with id: "+employeeId+" not found"));

        return scheduleRepository.getAllByEmployeesContains(employee);
    }

    @Override
    public List<Schedule> getScheduleForPet(long petId) {
        Pet pet=petRepository.findById(petId)
                .orElseThrow(() -> new PetException("Pet with id: "+ petId+" not found"));
        return scheduleRepository.getAllByPetsContains(pet);
    }

}
