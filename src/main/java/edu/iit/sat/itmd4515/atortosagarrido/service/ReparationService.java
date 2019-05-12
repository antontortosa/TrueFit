/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.atortosagarrido.service;

import edu.iit.sat.itmd4515.atortosagarrido.domain.EqStatus;
import edu.iit.sat.itmd4515.atortosagarrido.domain.Equipment;
import edu.iit.sat.itmd4515.atortosagarrido.domain.Reparation;
import edu.iit.sat.itmd4515.atortosagarrido.domain.Technician;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author antoniotortosa
 */
@Stateless
public class ReparationService extends AbstractService<Reparation> {

    public ReparationService() {
        super(Reparation.class);
    }

    @Override
    public void create(Reparation r) {
        super.create(r);
        Technician techDB = em.getReference(Technician.class, r.getTechnician().getId());
        Equipment eqDB = em.getReference(Equipment.class, r.getEquipment().getId());
        techDB.addReparation(r);
        eqDB.addReparation(r);
        eqDB.setStatus(EqStatus.FIXING);
        em.flush();
    }

    @Override
    public void update(Reparation r) {
        Reparation repDB = em.getReference(Reparation.class, r.getId());
        if (r.getDateFinish() != null) {
            repDB.setDateFinish(r.getDateFinish());
            Equipment eqDB = em.getReference(Equipment.class, repDB.getEquipment().getId());
            eqDB.setStatus(EqStatus.ONSERVICE);
        }
        em.flush();
    }

    @Override
    public void remove(Reparation r) {
        Reparation repDB = em.getReference(Reparation.class, r.getId());
        em.getReference(Technician.class, repDB.getTechnician().getId()).removeReparation(repDB);
        em.getReference(Equipment.class, repDB.getEquipment().getId()).removeReparation(repDB);
        em.remove(repDB);
    }

    public List<Reparation> findAllByTec(Long id) {
        return em.createNamedQuery("Reparation.findByTechId", Reparation.class)
                .setParameter("id", id)
                .getResultList();
    }

    public List<Reparation> findAllByTecOnGoing(Long id) {
        return em.createNamedQuery("Reparation.findByTechIdOnGoing", Reparation.class)
                .setParameter("id", id)
                .getResultList();
    }

    @Override
    public List<Reparation> findAll() {
        return em.createNamedQuery("Reparation.findAll", Reparation.class)
                .getResultList();
    }
}
