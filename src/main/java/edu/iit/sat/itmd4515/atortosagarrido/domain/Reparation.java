/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.atortosagarrido.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;

/**
 *
 * @author antoniotortosa
 */
@Entity
@Table(
        name="reparation"
)
@NamedQuery(name = "Reparation.findAll", query = "SELECT r FROM Reparation r")
@NamedQuery(name = "Reparation.findByTechId", query = "SELECT r FROM Reparation r where r.technician.id = :id")
@NamedQuery(name = "Reparation.findByTechIdOnGoing", query = "SELECT r FROM Reparation r where r.technician.id = :id and r.dateFinish IS NULL")
public class Reparation extends AbstractIdentifiedEntity{
    
    @NotNull(message = "The technician can not be null")
    @ManyToOne
    @JoinColumn(name = "technician", nullable = false)
    private Technician technician;
    
    @NotNull(message = "The equipment can not be null")
    @ManyToOne
    @JoinColumn(name = "equipment")
    private Equipment equipment;
    
    @Column(name = "date_start", nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateStart;
    
    @Column(name = "date_finish")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateFinish;

    public Reparation() {
    }

    public Reparation(Technician technician, Equipment equipment, Date dateStart, Date dateFinish) {
        this.technician = technician;
        this.equipment = equipment;
        this.dateStart = dateStart;
        this.dateFinish = dateFinish;
    }
    
    

    /**
     * Get the value of dateFinish
     *
     * @return the value of dateFinish
     */
    public Date getDateFinish() {
        return dateFinish;
    }

    /**
     * Set the value of dateFinish
     *
     * @param dateFinish new value of dateFinish
     */
    public void setDateFinish(Date dateFinish) {
        this.dateFinish = dateFinish;
    }


    /**
     * Get the value of date
     *
     * @return the value of date
     */
    public Date getDateStart() {
        return dateStart;
    }

    /**
     * Set the value of date
     *
     * @param date new value of date
     */
    public void setDateStart(Date date) {
        this.dateStart = date;
    }


    /**
     * Get the value of equipment
     *
     * @return the value of equipment
     */
    public Equipment getEquipment() {
        return equipment;
    }

    /**
     * Set the value of equipment
     *
     * @param equipment new value of equipment
     */
    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }


    /**
     * Get the value of technician
     *
     * @return the value of technician
     */
    public Technician getTechnician() {
        return technician;
    }

    /**
     * Set the value of technician
     *
     * @param technician new value of technician
     */
    public void setTechnician(Technician technician) {
        this.technician = technician;
    }

    @Override
    public String toString() {
        String toRet = "Reparation " + this.id + "{"+
                "\ntechnician: " + technician.getFullName() +
                "\nequipment: " + equipment.getName() +
                "\nstart date: " + dateStart.toString();
        if(dateFinish!=null){
            toRet += "\nstart date: " + dateFinish.toString();
        }
        return toRet+"}";
    }
    
    

}
