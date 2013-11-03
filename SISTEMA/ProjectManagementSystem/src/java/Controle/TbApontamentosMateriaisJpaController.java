/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Controle.exceptions.NonexistentEntityException;
import Controle.exceptions.PreexistingEntityException;
import Controle.exceptions.RollbackFailureException;
import Entidades.TbApontamentosMateriais;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import Entidades.TbOsServico;
import Entidades.TbMateriais;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author berlim
 */
public class TbApontamentosMateriaisJpaController implements Serializable {

    public TbApontamentosMateriaisJpaController() {
        emf = Persistence.createEntityManagerFactory("ProjectManagementSystemPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TbApontamentosMateriais tbApontamentosMateriais) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {

            em = getEntityManager();
            em.getTransaction().begin();

            if (tbApontamentosMateriais.getHand() == null) {
                em.persist(tbApontamentosMateriais);
            } else {
                em.merge(tbApontamentosMateriais);
            }

            em.getTransaction().commit();

        } catch (Exception ex) {
            try {
                em.getTransaction().rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            
            em = getEntityManager();
            em.getTransaction().begin();
            
            TbApontamentosMateriais tbApontamentosMateriais;
            
            try {
                tbApontamentosMateriais = em.getReference(TbApontamentosMateriais.class, id);
                tbApontamentosMateriais.getHand();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tbApontamentosMateriais with id " + id + " no longer exists.", enfe);
            }
            
            em.remove(tbApontamentosMateriais);
            em.getTransaction().commit();
        } catch (Exception ex) {
            try {
                em.getTransaction().rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TbApontamentosMateriais> findTbApontamentosMateriaisEntities() {
        return findTbApontamentosMateriaisEntities(true, -1, -1);
    }

    public List<TbApontamentosMateriais> findTbApontamentosMateriaisEntities(int maxResults, int firstResult) {
        return findTbApontamentosMateriaisEntities(false, maxResults, firstResult);
    }

    private List<TbApontamentosMateriais> findTbApontamentosMateriaisEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from TbApontamentosMateriais as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public TbApontamentosMateriais findTbApontamentosMateriais(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbApontamentosMateriais.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbApontamentosMateriaisCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from TbApontamentosMateriais as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
