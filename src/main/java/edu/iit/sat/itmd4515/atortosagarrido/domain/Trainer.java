/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.atortosagarrido.domain;

import edu.iit.sat.itmd4515.atortosagarrido.domain.security.User;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Positive;

/**
 *
 * @author antoniotortosa
 */
@Entity
@Table(
        name="trainer"
)
public class Trainer extends Employee{
    
    @OneToMany(mappedBy = "trainer")
    private List<Client> clients = new ArrayList<>();
    
    @Column(nullable = false, name = "cost_per_hour")
    @Positive (message = "The cost must be a positive real number")
    private double costPerHour;
    
    @OneToOne
    @JoinColumn(name = "username")    
    private User user;

    public Trainer() {
    }

    public Trainer(String name, String surname, Date birthDate,double costPerHour) {
        super(name,surname,birthDate);
        this.costPerHour = costPerHour;
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
     * Get the value of costPerHour
     *
     * @return the value of costPerHour
     */
    public double getCostPerHour() {
        return costPerHour;
    }

    /**
     * Set the value of costPerHour
     *
     * @param costPerHour new value of costPerHour
     */
    public void setCostPerHour(double costPerHour) {
        this.costPerHour = costPerHour;
    }


    /**
     * Get the value of clients
     *
     * @return the value of clients
     */
    public List<Client> getClients() {
        return clients;
    }

    /**
     * Set the value of clients
     *
     * @param clients new value of clients
     */
    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    
}
