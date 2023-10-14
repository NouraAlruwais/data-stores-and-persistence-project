package com.udacity.jdnd.course3.critter.schedule.service;

import com.udacity.jdnd.course3.critter.schedule.entity.Schedule;

import java.util.List;

public interface ScheduleService {
    Schedule createSchedule(Schedule schedule, List<Long> petIds, List<Long> employeeIds);

    List<Schedule> getAllSchedules();

    List<Schedule> getScheduleForCustomer(long customerId);

    List<Schedule> getScheduleForEmployee(long employeeId);

    List<Schedule> getScheduleForPet(long petId);
}
