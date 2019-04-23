/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.atortosagarrido.service;

import edu.iit.sat.itmd4515.atortosagarrido.domain.Membership;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author antoniotortosa
 */
@Stateless
public class MembershipService extends AbstractService<Membership>{

    public MembershipService() {
        super(Membership.class);
    }
    
    public Membership findByName(String name){
        return em.createNamedQuery("Membership.findByName",Membership.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    @Override
    public List<Membership> findAll() {
        return em.createNamedQuery("Membership.findAll",Membership.class)
                .getResultList();
    }
    
    
}
