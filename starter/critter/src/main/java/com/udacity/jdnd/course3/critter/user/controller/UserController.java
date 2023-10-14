package com.udacity.jdnd.course3.critter.user.controller;

import com.udacity.jdnd.course3.critter.user.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.dto.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.dto.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.user.entity.Customer;
import com.udacity.jdnd.course3.critter.user.entity.Employee;
import com.udacity.jdnd.course3.critter.user.service.CustomerService;
import com.udacity.jdnd.course3.critter.user.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    CustomerService customerService;
    @Autowired
    EmployeeService employeeService;
    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer customer=convertCustomerDTOToEntity(customerDTO);
        return convertToCustomerDTO(customerService.saveCustomer(customer,customerDTO.getPetIds()));
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
       return convertToListCustomerDTO(customerService.getAllCustomers());
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
       return convertToCustomerDTO(customerService.getOwnerByPet(petId));
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee=convertEmployeeDTOToEntity(employeeDTO);
        return convertToEmployeeDTO(employeeService.saveEmployee(employee));
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        return convertToEmployeeDTO(employeeService.getEmployee(employeeId));
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        employeeService.setAvailability(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        return convertToListEmployeeDTO(employeeService.findEmployeesForService(employeeDTO));
    }

    private static Customer convertCustomerDTOToEntity(CustomerDTO customerDTO){
        Customer customer=new Customer();
        customer.setName(customerDTO.getName());
        customer.setNotes(customerDTO.getNotes());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        return customer;
    }
    private static CustomerDTO convertToCustomerDTO(Customer customer){
        CustomerDTO customerDTO=new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setNotes(customer.getNotes());
        customerDTO.setPhoneNumber(customer.getPhoneNumber());
        List<Long> petIds=new ArrayList<>();
        if (!customer.getPets().isEmpty()){
        customer.getPets().forEach(pet -> {
            petIds.add(pet.getId());
        });}
        customerDTO.setPetIds(petIds);
        return customerDTO;
    }
    private List<CustomerDTO> convertToListCustomerDTO(List<Customer> customers){
        List<CustomerDTO> customerDTOS=new ArrayList<>();
        customers.forEach(customer -> {
            CustomerDTO customerDTO=convertToCustomerDTO(customer);
            customerDTOS.add(customerDTO);
        });
        return customerDTOS;
    }
    private static EmployeeDTO convertToEmployeeDTO(Employee employee){
        EmployeeDTO employeeDTO=new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setName(employee.getName());
        employeeDTO.setDaysAvailable(employee.getDaysAvailable());
        employeeDTO.setSkills(employee.getSkill());
        return employeeDTO;
    }
    private static Employee convertEmployeeDTOToEntity(EmployeeDTO employeeDTO){
        Employee employee=new Employee();
        employee.setName(employeeDTO.getName());
        employee.setSkill(employeeDTO.getSkills());
        employee.setDaysAvailable(employeeDTO.getDaysAvailable());
        return employee;
    }
    private static List<EmployeeDTO> convertToListEmployeeDTO(List<Employee> employees){
        List<EmployeeDTO> employeeDTOS=new ArrayList<>();
        employees.forEach(employee -> {
            EmployeeDTO employeeDTO=convertToEmployeeDTO(employee);
            employeeDTOS.add(employeeDTO);
        });
        return employeeDTOS;
    }

}
