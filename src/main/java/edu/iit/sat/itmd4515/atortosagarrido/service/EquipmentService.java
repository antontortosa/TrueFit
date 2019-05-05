/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.atortosagarrido.service;

import edu.iit.sat.itmd4515.atortosagarrido.domain.Equipment;
import edu.iit.sat.itmd4515.atortosagarrido.domain.Technician;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author antoniotortosa
 */

@Stateless
public class EquipmentService extends AbstractService<Equipment>{

    public EquipmentService() {
        super(Equipment.class);
    }
    
    @Override
    public void remove(Equipment e){
        Equipment eDB = em.getReference(Equipment.class, e.getId());
        for(Iterator<Technician> it = eDB.getTechnicians().iterator(); it.hasNext(); ){
            Technician t = it.next();
            t.removeEquipment(e);
            em.merge(t);
        }
        em.remove(eDB);
    }
    
    /**
     * Find all clients
     * 
     * @return all the Client in the Data Base
     */
    @Override
    public List<Equipment> findAll(){
        return em.createNamedQuery("Equipment.findAll",Equipment.class)
                .getResultList();
    }

    public Equipment findByName(String name){
        return em.createNamedQuery("Equipment.findByName",Equipment.class)
                .setParameter("name", name)
                .getSingleResult();
    }
}
