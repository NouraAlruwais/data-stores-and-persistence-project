package com.udacity.jdnd.course3.critter.schedule.repository;

import com.udacity.jdnd.course3.critter.pet.entity.Pet;
import com.udacity.jdnd.course3.critter.schedule.entity.Schedule;
import com.udacity.jdnd.course3.critter.user.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule,Long> {

    List<Schedule> getAllByPetsIn(List<Pet> pets);

    List<Schedule> getAllByEmployeesContains(Employee employee);

    List<Schedule> getAllByPetsContains(Pet pet);
}
