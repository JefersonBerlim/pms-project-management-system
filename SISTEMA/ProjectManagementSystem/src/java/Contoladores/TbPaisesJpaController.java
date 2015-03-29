/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Contoladores;

import Contoladores.exceptions.IllegalOrphanException;
import Contoladores.exceptions.NonexistentEntityException;
import Contoladores.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelos.TbEstados;
import Modelos.TbPaises;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author jeferson
 */
@ManagedBean
public class TbPaisesJpaController implements Serializable {

    private EntityManagerFactory emf = null;
    private EntityManager em = null;

    public TbPaisesJpaController() {
        emf = Persistence.createEntityManagerFactory("ProjectManagementSystemPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TbPaises tbPaises) throws Exception {
        try {
            em = getEntityManager();
            em.getTransaction().begin();

            if (tbPaises.getHand() == null) {
                em.persist(tbPaises);
            } else {
                em.merge(tbPaises);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void remove(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TbPaises tbPaises;
            try {
                tbPaises = em.getReference(TbPaises.class, id);
                tbPaises.getHand();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("Este pais não existe mais!", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TbEstados> tbEstadosCollectionOrphanCheck = tbPaises.getTbEstadosCollection();
            for (TbEstados tbEstadosCollectionOrphanCheckTbEstados : tbEstadosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<>();
                }
                illegalOrphanMessages.add("Este país (" + tbPaises.toString() + ") não pode ser excluída pois está sendo usado no estado " + tbEstadosCollectionOrphanCheckTbEstados.getEstado() + ".");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tbPaises);
            em.getTransaction().commit();
        } catch (NonexistentEntityException | IllegalOrphanException ex) {
            try {
                em.getTransaction().rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("Ocorreu um erro ao tentar reverter a transação.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public TbPaises findTbPaises(Integer id) {

        try {
            return em.find(TbPaises.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbPaisesCount() {
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TbPaises> rt = cq.from(TbPaises.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
