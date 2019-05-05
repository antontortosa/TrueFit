/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.atortosagarrido.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 *
 * @author antoniotortosa
 */

@Entity
@NamedQuery(name = "Location.findByName", query = "SELECT l FROM Location l WHERE l.name = :name")
@NamedQuery(name = "Location.findAll", query = "SELECT l FROM Location l")
@Table(
        name="location",
        uniqueConstraints = @UniqueConstraint(columnNames = {"name"})
)
public class Location extends AbstractNamedEntity{
    
    @NotNull (message = "The address can't be a null value")
    @NotBlank (message = "The address can't be blank")
    @Column(nullable = false, length = 255, name = "address_loc")
    private String address;

    @NotNull (message = "The zip can't be a null value")
    @Positive
    @Column(nullable = false, name = "zip_loc")
    private int zip;
    
    @OneToMany(mappedBy = "mainLocation", cascade = { CascadeType.MERGE })
    private List<Client> clients = new ArrayList<>();
    
    @OneToMany(mappedBy = "location", cascade = { CascadeType.MERGE })
    private List<Employee> employees = new ArrayList<>();

    public Location(){}

    public Location(String name, String address, int zip) {
        super(name);
        this.address = address;
        this.zip = zip;
    }
    
    /**
     * Get the value of zip
     *
     * @return the value of zip
     */
    public int getZip() {
        return zip;
    }

    /**
     * Set the value of zip
     *
     * @param zip new value of zip
     */
    public void setZip(int zip) {
        this.zip = zip;
    }


    /**
     * Get the value of address
     *
     * @return the value of address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Set the value of address
     *
     * @param address new value of address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public void addClient(Client cl){
        if(!clients.contains(cl)){
            clients.add(cl);
        }
    }
    public void removeClient(Client cl){
       if(clients.contains(cl)){
           clients.remove(cl);
       }
    }
    
    public void addEmployee(Employee em){
        if(!employees.contains(em)){
            employees.add(em);
        }
    }
    public void removeEmployee(Employee em){
       if(employees.contains(em)){
           employees.remove(em);
       }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.id);
        hash = 67 * hash + Objects.hashCode(this.name);
        hash = 67 * hash + Objects.hashCode(this.address);
        hash = 67 * hash + this.zip;
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
        final Location other = (Location) obj;
        if (this.zip != other.zip) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Location{" + "id=" + id + 
                "\n name=" + name + 
                "\n address=" + address + 
                "\n zip=" + zip + 
                printClients() + 
                printEmployees() +'}';
    }

    private String printClients() {
        String toRet = "[\n";
        toRet = clients.stream().map((c) -> c.getFullName() + "\n").reduce(toRet, String::concat);
        toRet += "]";
        return toRet;
    }
    
    private String printEmployees() {
        String toRet = "[\n";
        toRet = employees.stream().map((c) -> c.getFullName() + "\n").reduce(toRet, String::concat);
        toRet += "]";
        return toRet;
    }
    
    
    
}
