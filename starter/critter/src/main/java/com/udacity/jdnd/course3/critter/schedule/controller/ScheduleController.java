package com.udacity.jdnd.course3.critter.schedule.controller;

import com.udacity.jdnd.course3.critter.schedule.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.schedule.entity.Schedule;
import com.udacity.jdnd.course3.critter.schedule.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;
    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule=convertScheduleDTOToEntity(scheduleDTO);
        return convertToScheduleDTO(scheduleService.createSchedule(schedule,scheduleDTO.getPetIds(),scheduleDTO.getEmployeeIds()));
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        return convertToScheduleDTOList(scheduleService.getAllSchedules());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        return convertToScheduleDTOList(scheduleService.getScheduleForPet(petId));
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        return convertToScheduleDTOList(scheduleService.getScheduleForEmployee(employeeId));
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        return convertToScheduleDTOList(scheduleService.getScheduleForCustomer(customerId));
    }

    private static Schedule convertScheduleDTOToEntity(ScheduleDTO scheduleDTO){
        Schedule schedule=new Schedule();
        schedule.setActivities(scheduleDTO.getActivities());
        schedule.setDate(scheduleDTO.getDate());
        return schedule;
    }
    private static ScheduleDTO convertToScheduleDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setId(schedule.getId());
        scheduleDTO.setDate(schedule.getDate());
        scheduleDTO.setActivities(schedule.getActivities());
        List<Long> petIds = new ArrayList<>();
        if (!schedule.getPets().isEmpty()){
        schedule.getPets().forEach(pet -> {
            petIds.add(pet.getId());
        });}
        scheduleDTO.setPetIds(petIds);

        List<Long> employeeIds = new ArrayList<>();
        if (!schedule.getEmployees().isEmpty()){
        schedule.getEmployees().forEach(employee -> {
            employeeIds.add(employee.getId());
        });}
        scheduleDTO.setEmployeeIds(employeeIds);
        return scheduleDTO;
    }
    private static List<ScheduleDTO> convertToScheduleDTOList(List<Schedule> schedules){
        List<ScheduleDTO> scheduleDTOS=new ArrayList<>();
        schedules.forEach(schedule -> {
            ScheduleDTO scheduleDTO=convertToScheduleDTO(schedule);
            scheduleDTOS.add(scheduleDTO);
        });
        return scheduleDTOS;
    }
}
