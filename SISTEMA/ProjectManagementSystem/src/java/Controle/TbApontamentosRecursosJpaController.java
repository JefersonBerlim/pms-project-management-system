/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Controle.exceptions.IllegalOrphanException;
import Controle.exceptions.NonexistentEntityException;
import Controle.exceptions.PreexistingEntityException;
import Controle.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import Entidades.TbRecursos;
import Entidades.TbOsServico;
import Entidades.TbApontamentosFuncionarios;
import Entidades.TbApontamentosRecursos;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author berlim
 */
public class TbApontamentosRecursosJpaController implements Serializable {

    public TbApontamentosRecursosJpaController() {
        emf = Persistence.createEntityManagerFactory("ProjectManagementSystemPU");
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TbApontamentosRecursos tbApontamentosRecursos) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {

            em = getEntityManager();
            em.getTransaction().begin();

            if (tbApontamentosRecursos.getHand() == null) {
                em.persist(tbApontamentosRecursos);
            } else {
                em.merge(tbApontamentosRecursos);
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

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            
            em = getEntityManager();
            em.getTransaction().begin();
            
            TbApontamentosRecursos tbApontamentosRecursos;
            try {
                tbApontamentosRecursos = em.getReference(TbApontamentosRecursos.class, id);
                tbApontamentosRecursos.getHand();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tbApontamentosRecursos with id " + id + " no longer exists.", enfe);
            }
           
            em.remove(tbApontamentosRecursos);
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

    public List<TbApontamentosRecursos> findTbApontamentosRecursosEntities() {
        return findTbApontamentosRecursosEntities(true, -1, -1);
    }

    public List<TbApontamentosRecursos> findTbApontamentosRecursosEntities(int maxResults, int firstResult) {
        return findTbApontamentosRecursosEntities(false, maxResults, firstResult);
    }

    private List<TbApontamentosRecursos> findTbApontamentosRecursosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from TbApontamentosRecursos as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public TbApontamentosRecursos findTbApontamentosRecursos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbApontamentosRecursos.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbApontamentosRecursosCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from TbApontamentosRecursos as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
