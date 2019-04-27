/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.atortosagarrido.service;

import edu.iit.sat.itmd4515.atortosagarrido.domain.security.Group;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author antoniotortosa
 */
@Stateless
public class GroupService extends AbstractService<Group>{

    public GroupService() {
        super(Group.class);
    }

    @Override
    public List<Group> findAll() {
        return em.createNamedQuery("Group.findAll",Group.class)
                .getResultList();
    }

    public Group findByName(String groupname) {
        return em.createNamedQuery("Group.findByName",Group.class)
                .setParameter("groupName", groupname)
                .getSingleResult();
    }
    
}
