/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.atortosagarrido.domain;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author antoniotortosa
 */
@Entity
@Table(
        name="administrative"
)
@NamedQuery(name = "Administrative.findAll", query = "SELECT a FROM Administrative a")
public class Administrative extends Employee{
    
    public Administrative() {
        super();
    }
    
    public Administrative(String name,String surname, Date birthDate){
        super(name, surname, birthDate);
    }
}
