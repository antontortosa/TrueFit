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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author antoniotortosa
 */

@Entity
@NamedQuery(name = "Equipment.findByName", query = "SELECT e FROM Equipment e WHERE e.name = :name")
@NamedQuery(name = "Equipment.findAll", query = "SELECT e FROM Equipment e")
@Table(
        name="equipment"
)
public class Equipment extends AbstractNamedEntity{
    
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status_eq")
    private EqStatus status;
    
    @ManyToMany(mappedBy = "equipments")
    private Set<Technician> technicians = new HashSet<>();

    public Equipment() {
    }

    public Equipment(EqStatus status, String name) {
        super(name);
        this.status = status;
    }
    
    /**
     * Get the value of status
     *
     * @return the value of status
     */
    public EqStatus getStatus() {
        return status;
    }

    /**
     * Set the value of status
     *
     * @param status new value of status
     */
    public void setStatus(EqStatus status) {
        this.status = status;
    }

    public Set<Technician> getTechnicians() {
        return technicians;
    }

    public void setTechnicians(Set<Technician> technicians) {
        this.technicians = technicians;
    }
    
    public void addTechnician(Technician tc){
        if(!technicians.contains(tc)){
            technicians.add(tc);
            tc.addEquipment(this);
        }
    }
    
    public void removeTechTechnician(Technician tc){
        if(technicians.contains(tc)){
            technicians.remove(tc);
            tc.removeEquipment(this); 
        }
    }
    
    public void remove() {
        technicians.forEach((tc) -> {
            removeTechTechnician(tc);
        });
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.id);
        hash = 79 * hash + Objects.hashCode(this.name);
        hash = 79 * hash + Objects.hashCode(this.status);
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
        final Equipment other = (Equipment) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.status, other.status)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Equipment{" + "id=" + id + 
                "\n name=" + name + 
                "\n status=" + status + '}';
    }
    
    
}
