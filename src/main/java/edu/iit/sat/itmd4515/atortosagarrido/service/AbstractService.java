/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.atortosagarrido.service;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author antoniotortosa
 */
public abstract class AbstractService<T> {
    
    @PersistenceContext(name = "itmd4515PU")
    protected EntityManager em;
    
    protected Class<T> entityClass;
    
    protected AbstractService(Class entityClass){
        this.entityClass = entityClass;
    }
    
    public void create(T obj){
        em.persist(obj);
    }
    
    public void update(T obj){
        em.merge(obj);
    }
    
    public void remove(T obj){
        em.remove(obj);
    }  
    
    /**
     * Find a client
     * 
     * @param id
     * @return the Client associated with that id
     */
    public  T find(long id) {
        return em.find(entityClass, id);
    }
    
    /**
     * Find all clients
     * 
     * @return all the Client in the Data Base
     */
    public abstract List<T> findAll();
}
