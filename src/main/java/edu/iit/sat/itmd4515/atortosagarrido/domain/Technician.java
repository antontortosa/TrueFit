/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.atortosagarrido.domain;


import edu.iit.sat.itmd4515.atortosagarrido.domain.security.User;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author antoniotortosa
 */

@Entity
@Table(
        name="technician"
)
public class Technician extends Employee{
    
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
        name = "Technician_Equipment", 
        joinColumns = { @JoinColumn(name = "technician_id") }, 
        inverseJoinColumns = { @JoinColumn(name = "equipment_id") }
    )
    Set<Equipment> equipments = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "username")    
    private User user;
    
    public Technician(){
    }
    
    public Technician(String name, String surname, Date birthDate) {
        super(name,surname,birthDate);
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
     * Get the value of equipments
     *
     * @return the value of equipments
     */
    public Set<Equipment> getEquipments() {
        return equipments;
    }
    
    /**
     * Set the value of equipments
     *
     * @param equipments new value of equipments
     */
    public void setEquipments(Set<Equipment> equipments) {
        this.equipments = equipments;
    }
    
     public void addEquipment(Equipment eq) {
        if(!equipments.contains(eq)){
            equipments.add(eq);
            eq.addTechnician(this);
        }
        
    }
 
    public void removeEquipment(Equipment eq) {
        if(equipments.contains(eq)){
            equipments.remove(eq);
            eq.removeTechTechnician(this);
        }
    }
 
    public void remove() {
        equipments.forEach((eq) -> {
            removeEquipment(eq);
        });
    }

    @Override
    public String toString() {
        return "Technician{" + "equipments=" + aux_print_equip() + '}';
    }
    
    private String aux_print_equip(){
        String toRet="";
        toRet = equipments.stream().map((eq) -> eq.getId() + ": " + eq.getName() + "\n").reduce(toRet, String::concat);
        return toRet;
    }
    
    
}
