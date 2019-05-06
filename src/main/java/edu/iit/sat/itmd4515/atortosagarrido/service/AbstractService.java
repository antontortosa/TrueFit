/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.atortosagarrido.service;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 *
 * @author antoniotortosa
 */
public abstract class AbstractService<T> {

    private static final Logger LOG = Logger.getLogger(AbstractService.class.getName());
    
    @PersistenceContext(name = "itmd4515PU")
    protected EntityManager em;

    protected Class<T> entityClass;

    protected AbstractService(Class entityClass) {
        this.entityClass = entityClass;
    }

    public void create(T obj) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(obj);
        if (constraintViolations.size() > 0) {
            Iterator<ConstraintViolation<T>> iterator = constraintViolations.iterator();
            while (iterator.hasNext()) {
                ConstraintViolation<T> cv = iterator.next();
                
                LOG.log(Level.INFO, "{0}.{1} {2}", new Object[]{cv.getRootBeanClass().getName(), cv.getPropertyPath(), cv.getMessage()});
            }
        } else {
            em.persist(obj);
        }
        //em.persist(obj);
    }

    public void update(T obj) {
        em.merge(obj);
    }

    public void remove(T obj) {
        em.remove(obj);
    }

    /**
     * Find a client
     *
     * @param id
     * @return the Client associated with that id
     */
    public T find(long id) {
        return em.find(entityClass, id);
    }

    /**
     * Find all clients
     *
     * @return all the Client in the Data Base
     */
    public abstract List<T> findAll();
}
