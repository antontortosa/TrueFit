/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.atortosagarrido.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;

/**
 *
 * @author antoniotortosa
 */
@MappedSuperclass
public class AbstractIdentifiedEntity implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;

    @Version
    private long version;
    
    private LocalDateTime lasUpdatedTimestamp;
    
    private LocalDateTime createdTimestamp;
    
    public AbstractIdentifiedEntity() {
    }
    
    @PrePersist
    public void prePersist(){
        createdTimestamp = LocalDateTime.now();
        lasUpdatedTimestamp = LocalDateTime.now();
    }
    
    @PreUpdate
    public void preUpdate(){
        lasUpdatedTimestamp = LocalDateTime.now(); 
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * Get the value of version
     *
     * @return the value of version
     */
    public long getVersion() {
        return version;
    }

    /**
     * Set the value of version
     *
     * @param version new value of version
     */
    public void setVersion(long version) {
        this.version = version;
    }
    
    /**
     * Get the value of lasUpdatedTimestamp
     *
     * @return the value of lasUpdatedTimestamp
     */
    public LocalDateTime getLasUpdatedTimestamp() {
        return lasUpdatedTimestamp;
    }

    /**
     * Set the value of lasUpdatedTimestamp
     *
     * @param lasUpdatedTimestamp new value of lasUpdatedTimestamp
     */
    public void setLasUpdatedTimestamp(LocalDateTime lasUpdatedTimestamp) {
        this.lasUpdatedTimestamp = lasUpdatedTimestamp;
    }
    
    /**
     * Get the value of createdTimestamp
     *
     * @return the value of createdTimestamp
     */
    public LocalDateTime getCreatedTimestamp() {
        return createdTimestamp;
    }

    /**
     * Set the value of createdTimestamp
     *
     * @param createdTimestamp new value of createdTimestamp
     */
    public void setCreatedTimestamp(LocalDateTime createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

}
