/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.atortosagarrido.service;

import edu.iit.sat.itmd4515.atortosagarrido.domain.Administrative;
import edu.iit.sat.itmd4515.atortosagarrido.domain.Employee;
import edu.iit.sat.itmd4515.atortosagarrido.domain.Equipment;
import edu.iit.sat.itmd4515.atortosagarrido.domain.Location;
import edu.iit.sat.itmd4515.atortosagarrido.domain.Position;
import edu.iit.sat.itmd4515.atortosagarrido.domain.Technician;
import edu.iit.sat.itmd4515.atortosagarrido.domain.Trainer;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;

/**
 *
 * @author antoniotortosa
 */
@Stateless
public class EmployeeService extends AbstractService<Employee> {

    private static final Logger LOG = Logger.getLogger(EmployeeService.class.getName());
    private Employee eDB;

    public EmployeeService() {
        super(Employee.class);
    }

    @Override
    public void update(Employee e) {
        eDB = em.getReference(Employee.class, e.getId());
        LOG.log(Level.INFO, "Employee in DB (before) is: {0}", eDB.toString());
        if (e.getName() != null) {
            eDB.setName(e.getName());
        }
        if (e.getSurname() != null) {
            eDB.setSurname(e.getSurname());
        }
        if (e.getBirthDate() != null) {
            eDB.setBirthDate(e.getBirthDate());
        }
        if (e.getSignDate() != null) {
            eDB.setSignDate(e.getSignDate());
        }
        if (e.getLocation() != null) {
            eDB.setLocation(e.getLocation());
            em.merge(eDB.getLocation());
        }
        if (e.getPosition() != null) {
            eDB.setPosition(e.getPosition());
            em.merge(eDB.getPosition());
        }
        if (e.getUser() != null) {
            LOG.log(Level.INFO, "the user brought from JSF is: {0}", e.getUser().getUserName());
            eDB.setUser(e.getUser());
        }
        if (eDB.getClass().getSimpleName().equals("Technician")) {
            if (((Technician) e).getEquipments() != null) {
                refactorEquipments((Technician) e);
            }
        }
        LOG.log(Level.INFO, "Employee in DB (after) is: {0}", eDB.toString());
        em.flush();
    }

    @Override
    public void remove(Employee e) {
        eDB = em.getReference(Employee.class, e.getId());
        Location loc = eDB.getLocation();
        Position pos = eDB.getPosition();
        if (loc != null) {
            loc.removeEmployee(eDB);
            em.merge(loc);
        }
        if (pos != null) {
            pos.removeEmployee(eDB);
            em.merge(pos);
        }
        em.remove(eDB);
    }

    /**
     * Find all employees
     *
     * @return all the Emmployees in the Data Base
     */
    @Override
    public List<Employee> findAll() {
        return em.createNamedQuery("Employee.findAll", Employee.class)
                .getResultList();
    }

    public List<Technician> findAllTechnicians() {
        return em.createNamedQuery("Technician.findAll", Technician.class)
                .getResultList();
    }

    public Employee findByUsername(String username) {
        return em.createNamedQuery("Employee.findByUsername", Employee.class)
                .setParameter("username", username)
                .getSingleResult();
    }

    public List<Administrative> findAllAdmins() {
        return em.createNamedQuery("Administrative.findAll", Administrative.class)
                .getResultList();
    }

    public List<Trainer> findAllTrainers() {
        return em.createNamedQuery("Trainer.findAll", Trainer.class)
                .getResultList();
    }

    //Utility Methods
    /**
     *This method manages the relation Technician-Equipment
     * First the former relations are erased with removeEquipment()
     * Then they are rebuilt from the ones brought by the JSF form
     */
    private void refactorEquipments(Technician t) {
        Equipment eqAux;
        Set<Equipment> setDB = new HashSet<>();
        removeEquipment();
        for (Equipment eq : t.getEquipments()) {
            LOG.log(Level.INFO, "the new equipment is: {0}", eq.toString());
            eqAux = em.getReference(Equipment.class, eq.getId());
            setDB.add(eqAux);
            eqAux.addTechnician((Technician)eDB);
        }
        ((Technician)eDB).setEquipments(setDB);
    }
    /**
     * Get all the equipments from a Technician 
     * and removes the technican from them
     */
    private void removeEquipment() {
        Set<Equipment> setDB = ((Technician)eDB).getEquipments();
        setDB.forEach((eq) -> {
            eq.removeTechTechnician((Technician)eDB);
        });
    }

}
