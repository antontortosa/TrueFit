/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.atortosagarrido.domain;


import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author antoniotortosa
 */

@Entity
@Table(
        name="technician"
)
@NamedQuery(name = "Technician.findAll", query = "SELECT t FROM Technician t")
public class Technician extends Employee{
    
    @ManyToMany(cascade = { /*CascadeType.PERSIST*/ })
    @JoinTable(
        name = "Technician_Equipment", 
        joinColumns = { @JoinColumn(name = "technician_id") }, 
        inverseJoinColumns = { @JoinColumn(name = "equipment_id") }
    )
    Set<Equipment> equipments = new HashSet<>();
    
    @OneToMany(mappedBy = "technician")
    private List<Reparation> reparations;
    
    public Technician(){
        super();
    }
    
    public Technician(String name, String surname, Date birthDate) {
        super(name,surname,birthDate);
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
    
    /**
     * Get the value of reparations
     *
     * @return the value of reparations
     */
    public List<Reparation> getReparations() {
        return reparations;
    }

    /**
     * Set the value of reparations
     *
     * @param reparations new value of reparations
     */
    public void setReparations(List<Reparation> reparations) {
        this.reparations = reparations;
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
    
    public void addReparation(Reparation r) {
        if (!this.reparations.contains(r)) {
            this.reparations.add(r);
        }
    }

    public void removeReparation(Reparation r) {
        if (this.reparations.contains(r)) {
            this.reparations.remove(r);
        }
    }

    @Override
    public String toString() {
        return "Technician{" + "equipments=" + aux_print_equip() + '}';
    }
    
    private String aux_print_equip(){
        String toRet="[\n";
        toRet = equipments.stream().map((eq) -> eq.getId() + ": " + eq.getName() + "\n").reduce(toRet, String::concat);
        toRet+="]";
        return toRet;
    }
    
    
}
