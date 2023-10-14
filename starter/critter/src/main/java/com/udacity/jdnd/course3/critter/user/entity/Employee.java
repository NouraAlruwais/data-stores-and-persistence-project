package com.udacity.jdnd.course3.critter.user.entity;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.Set;

@Entity
@Table
public class Employee {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ElementCollection
    private Set<EmployeeSkill> skill;
    @ElementCollection
    private Set<DayOfWeek> daysAvailable;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<EmployeeSkill> getSkill() {
        return skill;
    }

    public void setSkill(Set<EmployeeSkill> skill) {
        this.skill = skill;
    }

    public Set<DayOfWeek> getDaysAvailable() {
        return daysAvailable;
    }

    public void setDaysAvailable(Set<DayOfWeek> daysAvailable) {
        this.daysAvailable = daysAvailable;
    }
}
