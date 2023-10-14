package com.udacity.jdnd.course3.critter.user.service.impl;

import com.udacity.jdnd.course3.critter.exception.EmployeeException;
import com.udacity.jdnd.course3.critter.user.dto.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.user.entity.Employee;
import com.udacity.jdnd.course3.critter.user.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.service.EmployeeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee getEmployee(long employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeException("Employee with id: "+employeeId+" not found"));
    }

    @Override
    public void setAvailability(Set<DayOfWeek> daysAvailable, long employeeId) {
        employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeException("Employee with id: "+employeeId+" not found"))
                .setDaysAvailable(daysAvailable);
    }

    @Override
    public List<Employee> findEmployeesForService(EmployeeRequestDTO employeeRequestDTO) {

        List<Employee> employees=employeeRepository.findAll();
        return employees.stream()
                .filter(employee -> employee.getSkill().containsAll(employeeRequestDTO.getSkills()))
                .collect(Collectors.toList());
    }

}
