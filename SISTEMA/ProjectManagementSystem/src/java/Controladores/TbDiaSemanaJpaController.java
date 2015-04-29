/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Modelos.TbDiaSemana;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author jeferson
 */
public class TbDiaSemanaJpaController implements Serializable {

    private EntityManagerFactory emf = null;
    private EntityManager em = null;
    private TbDiaSemana tbDiaDaSemana = new TbDiaSemana();

    public TbDiaSemanaJpaController() {
        emf = Persistence.createEntityManagerFactory("ProjectManagementSystemPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public TbDiaSemana getTbDiaDaSemana() {
        return tbDiaDaSemana;
    }

    public void setTbDiaDaSemana(TbDiaSemana tbDiaDaSemana) {
        this.tbDiaDaSemana = tbDiaDaSemana;
    }

    public TbDiaSemana findTbDiaSemana(Integer id) {
        em = getEntityManager();
        try {
            return em.find(TbDiaSemana.class, id);
        } finally {
            em.close();
        }
    }

    public List<TbDiaSemana> retornaCollectionDiaSemana() {
        em = getEntityManager();
        Query query = em.createNamedQuery("TbDiaSemana.findAll");
        return query.getResultList();
    }

}
