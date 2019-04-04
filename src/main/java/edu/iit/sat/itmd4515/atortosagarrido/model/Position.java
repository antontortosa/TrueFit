/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.atortosagarrido.model;


import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 *
 * @author antoniotortosa
 */

@Entity
@NamedQuery(name = "Position.findByName", query = "SELECT p FROM Position p WHERE p.name = :name")
@Table(
        name="position"
)
public class Position extends AbstractNamedEntity{
    
    @NotNull (message = "The salary date can't be a null value")
    @Positive (message = "The salary type has to be a positive number")
    @Column(nullable = false, name = "salary_pos")
    private double salary;

    @OneToMany(mappedBy = "position")
    private Set<Employee> employees = new HashSet<>();

    public Position() {
    }

    public Position(double salary, String name) {
        super(name);
        this.salary = salary;
    }
    
    /**
     * Get the value of salary
     *
     * @return the value of salary
     */
    public double getSalary() {
        return salary;
    }

    /**
     * Set the value of salary
     *
     * @param salary new value of salary
     */
    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }
    
    public void addEmployee(Employee em){
        if(!employees.contains(em)){
            employees.add(em);
            em.setPosition(this);
        }
    }
    public void removeEmployee(Employee em){
       if(employees.contains(em)){
           employees.remove(em);
           em.removePosition(this);
       }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.id);
        hash = 23 * hash + Objects.hashCode(this.name);
        hash = 23 * hash + (int) (Double.doubleToLongBits(this.salary) ^ (Double.doubleToLongBits(this.salary) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Position other = (Position) obj;
        if (Double.doubleToLongBits(this.salary) != Double.doubleToLongBits(other.salary)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Position{" + "id=" + id + 
                "\n name=" + name + 
                "\n salary=" + salary + '}';
    }
    
    
}
