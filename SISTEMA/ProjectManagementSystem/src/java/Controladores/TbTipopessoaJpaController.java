/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import java.io.Serializable;
import javax.persistence.Query;
import Modelos.TbTipopessoa;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author jeferson
 */
public class TbTipopessoaJpaController implements Serializable {

    private EntityManagerFactory emf = null;
    private EntityManager em = null;
    private TbTipopessoa tbTipoPessoa = new TbTipopessoa();

    public TbTipopessoaJpaController() {
        emf = Persistence.createEntityManagerFactory("ProjectManagementSystemPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public TbTipopessoa getTbtipoPessoa() {
        return tbTipoPessoa;
    }

    public void setTbtipoPessoa(TbTipopessoa tbTipoPessoa) {
        this.tbTipoPessoa = tbTipoPessoa;
    }

    public TbTipopessoa findTbTipopessoa(Integer id) {
        em = getEntityManager();
        try {
            return em.find(TbTipopessoa.class, id);
        } finally {
            em.close();
        }
    }

    public List<TbTipopessoa> retornaCollectionTipoPessoa() {

        em = getEntityManager();
        Query query = em.createNamedQuery("TbTipopessoa.findAll");
        return query.getResultList();

    }

}
