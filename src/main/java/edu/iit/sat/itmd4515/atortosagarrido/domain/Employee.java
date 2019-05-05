/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.atortosagarrido.domain;

import edu.iit.sat.itmd4515.atortosagarrido.domain.security.User;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;

/**
 *
 * @author antoniotortosa
 */

@Entity
@NamedQuery(name = "Employee.findByFullName", query = "SELECT e FROM Employee e WHERE e.name = :name AND e.surname = :surname")
@NamedQuery(name = "Employee.findAll", query = "SELECT e FROM Employee e")
@NamedQuery(name = "Employee.findByUsername", query = "SELECT e FROM Employee e WHERE e.user.userName = :username")
@Table(
        name="employee",
        uniqueConstraints = @UniqueConstraint(columnNames = {"name","surname_emp"})
        
)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type_emp",length = 21)
public class Employee extends AbstractNamedEntity{
    
    @NotNull (message = "The surname can't be a null value")
    @NotBlank (message = "The surname can't be blank")
    @Column(nullable = false, length = 50, name = "surname_emp")
    protected String surname;
    
    @NotNull (message = "The birth date can't be a null value")
    @Past (message = "The birthdate has to be in the past")
    @Column(nullable = false, name = "birthdate_emp")
    @Temporal(TemporalType.DATE)
    protected Date birthDate;
    
    @NotNull (message = "The singing date can't be a null value")
    @PastOrPresent (message = "The signing date has to be either in the past or at the current time")
    @Column(nullable = false, name = "signdate_emp")
    @Temporal(TemporalType.DATE)
    protected Date signDate;
    
    @ManyToOne
    @JoinColumn(name = "location_id")
    protected Location location;
    
    @ManyToOne
    @JoinColumn(name = "position_id")
    protected Position position;
    
    @OneToOne
    @JoinColumn(name = "username")    
    private User user;

    public Employee() {
        this.signDate = Date.from(Instant.now());
    }
    
    public Employee(String name,String surname, Date birthDate) {
        super(name);
        this.surname = surname;
        this.birthDate = birthDate;
        this.signDate = Date.from(Instant.now());
    }

      /**
     * Get the value of user
     *
     * @return the value of user
     */
    public User getUser() {
        return user;
    }

    /**
     * Set the value of user
     *
     * @param user new value of user
     */
    public void setUser(User user) {
        this.user = user;
    }
    
    
    /**
     * Get the value of surname
     *
     * @return the value of surname
     */
    public String getSurname() {
        return surname;
    }

    
    /**
     * Set the value of surname
     *
     * @param surname new value of surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }
    
    /**
     * Get the value of birthDate
     *
     * @return the value of birthDate
     */
    public Date getBirthDate() {
        return birthDate;
    }

    
    /**
     * Set the value of birthDate
     *
     * @param birthDate new value of birthDate
     */
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * Get the value of signDate
     *
     * @return the value of signDate
     */
    public Date getSignDate() {
        return signDate;
    }

    
    /**
     * Set the value of signDate
     *
     * @param signDate new value of signDate
     */
    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    /**
     * Get the value of locationId
     *
     * @return the value of locationId
     */
    public Location getLocation() {
        return location;
    }

    
    /**
     * Set the value of location
     *
     * @param location new value of location
     */
    public void setLocation(Location location) {
        if(!location.equals(this.location)){
            if(this.location != null){
             this.location.removeEmployee(this);
            }
            this.location = location;
            this.location.addEmployee(this);
        }   
    }
    
    public void removeLocation(Location location) {
        if(this.location != null){
           this.location=null;
           location.removeEmployee(this);
       }
    }

    /**
     * Get the value of positionId
     *
     * @return the value of positionId
     */
    public Position getPosition() {
        return position;
    }

    
    /**
     * Set the value of positionId
     *
     * @param position new value of positionId
     */
    public void setPosition(Position position) {
        if(this.position != position){
            if(this.position != null){
                this.position.removeEmployee(this);
            }
            this.position = position;
            this.position.addEmployee(this);
        }
    }
    
    public void removePosition(Position position) {
        if(this.position != null){
           this.position=null;
           position.removeEmployee(this);
       }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.name);
        hash = 89 * hash + Objects.hashCode(this.surname);
        hash = 89 * hash + Objects.hashCode(this.birthDate);
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
        final Employee other = (Employee) obj;
        if(!other.name.equals(this.name)){
            return false;
        }
        if(!other.surname.equals(this.surname)){
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String toRet = "Employee {" +
                "\nid=" + id + 
                "\n name=" + getFullName() + 
                "\n birthDate=" + birthDate + 
                "\n signDate=" + signDate;       
        if(user!=null){toRet+="\n user=" + user.getUserName();}
        if(location!=null){toRet+="\n location= " + location.getName();}
        if(position!=null){toRet+="\n position= " + position.getName();}
        toRet += "\n}";
        return toRet;
    }
    
    /**
     * Convenient method for retrieving the full name of the employee
     * 
     * @return name + surname properly formated
     */
    public String getFullName(){
        String n = this.name.trim();
        String s = this.surname.trim();
        return n + " " + s;
    }
   
 }
