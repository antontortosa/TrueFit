/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.atortosagarrido.model;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero; 
/**
 *
 * @author antoniotortosa
 */

@Entity
@NamedQuery(name = "Client.findByFullName", query = "SELECT c FROM Client c WHERE c.name = :name AND c.surname = :surname")
@Table(
        name="client", 
        uniqueConstraints = @UniqueConstraint(columnNames = {"name","surname_cl"})
)
public class Client extends AbstractNamedEntity {
    
    @NotNull (message = "The surname can't be a null value")
    @NotBlank (message = "The surname can't be blank")
    @Column(nullable = false, length = 50, name = "surname_cl")
    private String surname;
    
    @NotNull (message = "The birth date can't be a null value")
    @Past (message = "The birthdate has to be in the past")
    @Column(nullable = false, name = "birthdate_cl")
    @Temporal(TemporalType.DATE)
    private Date birthDate;
    
    @NotNull (message = "The singing date can't be a null value")
    @PastOrPresent (message = "The signing date has to be either in the past or at the current time")
    @Column(nullable = false, name = "signdate_cl")
    @Temporal(TemporalType.DATE)
    private Date signDate;
    
    @ManyToOne
    @JoinColumn(name = "membership_id")
    private Membership membership;
    
    @Positive (message = "The height must be a positive real number")
    @Column(nullable = false, name = "height_cl")
    private double height;
    
    @Positive (message = "The weight must be a positive real number")
    @Column(nullable = false, name = "weight_cl")
    private double weight;
    
    @PositiveOrZero (message = "The bodyfat percentage must be a positive real number")
    @Max(100)
    @Column(name = "bodyfat_cl")
    private double bodyFatPercentage;
    
    @ManyToOne
    @JoinColumn(name = "main_location_id")
    private Location mainLocation;
    
    @PositiveOrZero (message = "The training focus id has to be a positive integer")
    @Column(name = "focus_id")
    //@ForeignKey(TODO)
    private int trainingFocusId;
    
    @PositiveOrZero (message = "The trainer id has to be a positive integer")
    @Column(name = "trainer_id")
    //@ForeignKey(TODO)
    private int trainerId;
    
    
    public Client() {
    }

    public Client(String name, String surname, Date birthDate, double height, double weight) {
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.height = height;
        this.weight = weight;
        this.signDate = Date.from(Instant.now());
        if(membership!=null){
            membership.addClient(this);
        }
    }
    
    /**
     * Get the value of trainerId
     *
     * @return the value of trainerId
     */
    public int getTrainerId() {
        return trainerId;
    }

    /**
     * Set the value of trainerId
     *
     * @param trainerId new value of trainerId
     */
    public void setTrainerId(int trainerId) {
        this.trainerId = trainerId;
    }
    
    /**
     * Get the value of bodyFatPercentage
     *
     * @return the value of bodyFatPercentage
     */
    public double getBodyFatPercentage() {
        return bodyFatPercentage;
    }

    /**
     * Set the value of bodyFatPercentage
     *
     * @param bodyFatPercentage new value of bodyFatPercentage
     */
    public void setBodyFatPercentage(double bodyFatPercentage) {
        this.bodyFatPercentage = bodyFatPercentage;
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
     * Get the value of membershipType
     *
     * @return the value of membershipType
     */
    public Membership getMembership() {
        return membership;
    }

    /**
     * Get the value of trainingFocusId
     *
     * @return the value of trainingFocusId
     */
    public int getTrainingFocusId() {
        return trainingFocusId;
    }

    /**
     * Set the value of trainingFocusId
     *
     * @param trainingFocusId new value of trainingFocusId
     */
    public void setTrainingFocusId(int trainingFocusId) {
        this.trainingFocusId = trainingFocusId;
    }


    /**
     * Get the value of weight
     *
     * @return the value of weight
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Set the value of weight
     *
     * @param weight new value of weight
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }


    /**
     * Get the value of height
     *
     * @return the value of height
     */
    public double getHeight() {
        return height;
    }

    /**
     * Set the value of height
     *
     * @param height new value of height
     */
    public void setHeight(double height) {
        this.height = height;
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
     * Convenient method for retrieving the full name of the client
     * 
     * @return name + surname properly formated
     */
    public String getFullName(){
        String n = this.name.trim();
        String s = this.surname.trim();
        return n + " " + s;
    }
    
    /**
     * Get the value of mainLocation
     *
     * @return the value of mainLocation
     */
    public Location getMainLocation() {
        return mainLocation;
    }

    /**
     * Set the value of mainLocation
     *
     * @param mainLocation new value of mainLocation
     */
    public void setMainLocation(Location mainLocation) {
        if(!mainLocation.equals(this.mainLocation)){
            if(this.mainLocation != null){
                this.mainLocation.removeClient(this);
            }
        this.mainLocation = mainLocation;
        mainLocation.addClient(this);
        }
    }
    
    public void removeMainLocation(Location mainLocation) {
        if(this.mainLocation != null){
           this.mainLocation=null;
           mainLocation.removeClient(this);
       }
    }
    
    /**
     * Set the value of membership
     *
     * @param membership new value of mainLocation
     */
    public void setMembership(Membership membership) {
        if(!membership.equals(this.membership)){
            if(this.membership != null){
                this.membership.removeClient(this);
            }
        this.membership = membership;
        membership.addClient(this);
        }
    }
    public void removeMembership(Membership membership) {
        if(this.membership != null){
           this.membership=null;
           membership.removeClient(this);
       }
    }

    @Override
    public String toString() {
        return "Client "+ id +"{" + 
                "\t\nname=" + name + 
                "\t\nsurname=" + surname + 
                "\t\nbirthDate=" + birthDate + 
                "\t\nsignDate=" + signDate + 
                "\t\nmembershipType=" + membership + 
                "\t\nheight=" + height + 
                "\t\nweight=" + weight + 
                "\t\nbodyFatPercentage=" + bodyFatPercentage + 
                "\t\ntrainingFocusId=" + trainingFocusId + 
                "\t\ntrainerId=" + trainerId + '}';
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
        final Client other = (Client) obj;
        return this.id == other.id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.name);
        hash = 47 * hash + Objects.hashCode(this.surname);
        hash = 47 * hash + Objects.hashCode(this.birthDate);
        hash = 47 * hash + Objects.hashCode(this.signDate);
        return hash;
    } 
}
