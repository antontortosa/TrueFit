/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.atortosagarrido.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 *
 * @author antoniotortosa
 */
@Entity
@NamedQuery(name = "Membership.findByName", query = "SELECT m FROM Membership m WHERE m.name = :name")
@NamedQuery(name = "Membership.findAll", query = "SELECT m FROM Membership m")
@Table(name="membership",
        uniqueConstraints = @UniqueConstraint(columnNames = {"name"})
)
public class Membership extends AbstractNamedEntity{
    
    @Column(nullable = false, name = "monthly_price")
    @Positive (message = "The monthly price must be a positive real number")
    @NotNull
    private double monthlyPrice;
    
    @OneToMany(mappedBy = "membership", cascade = { CascadeType.MERGE,CascadeType.PERSIST })
    private List<Client> clients = new ArrayList<>();;
    
    public Membership() {
    }

    public Membership(String name, double monthlyPrice) {
        super(name);
        this.monthlyPrice = monthlyPrice;
    }
    
     /**
     * Get the value of monthlyPrice
     *
     * @return the value of monthlyPrice
     */
    public double getMonthlyPrice() {
        return monthlyPrice;
    }

    /**
     * Set the value of monthlyPrice
     *
     * @param monthlyPrice new value of monthlyPrice
     */
    public void setMonthlyPrice(double monthlyPrice) {
        this.monthlyPrice = monthlyPrice;
    }
    
    /**
     * Get the value of clients1
     *
     * @return the value of clients1
     */
    public List<Client> getClients() {
        return clients;
    }

    /**
     * Set the value of clients1
     *
     * @param clients new value of clients1
     */
    public void setClients(ArrayList clients) {
        this.clients = clients;
    }
    
    public void addClient(Client cl){
        if(!clients.contains(cl)){
            clients.add(cl);
            cl.setMembership(this);
        }
    }
    public void removeClient(Client cl){
       if(clients.contains(cl)){
           clients.remove(cl);
           cl.removeMembership(this);
       }
    }
    
    private String printClients(){
        String toRes = "{";
        toRes = clients.stream().map((c) -> c.getFullName()).reduce(toRes, String::concat);
        toRes += "}";
        return toRes;
    }

    @Override
    public String toString() {
        return "Membership{" + "name: " + name +", monthlyPrice=" + monthlyPrice + ", clients=" + printClients() + '}';
    }
    
    
}
