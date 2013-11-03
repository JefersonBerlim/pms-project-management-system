/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Controle.exceptions.NonexistentEntityException;
import Controle.exceptions.PreexistingEntityException;
import Controle.exceptions.RollbackFailureException;
import Entidades.TbApontamentosFuncionarios;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import Entidades.TbFuncionarios;
import Entidades.TbApontamentosRecursos;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author berlim
 */
public class TbApontamentosFuncionariosJpaController implements Serializable {

    public TbApontamentosFuncionariosJpaController() {
        emf = Persistence.createEntityManagerFactory("ProjectManagementSystemPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TbApontamentosFuncionarios tbApontamentosFuncionarios) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            
            em = getEntityManager();
            em.getTransaction().begin();

            if (tbApontamentosFuncionarios.getHand() == null) {
                em.persist(tbApontamentosFuncionarios);
            } else {
                em.merge(tbApontamentosFuncionarios);
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
            TbApontamentosFuncionarios tbApontamentosFuncionarios;
            try {
                tbApontamentosFuncionarios = em.getReference(TbApontamentosFuncionarios.class, id);
                tbApontamentosFuncionarios.getHand();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tbApontamentosFuncionarios with id " + id + " no longer exists.", enfe);
            }
            em.remove(tbApontamentosFuncionarios);
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

    public List<TbApontamentosFuncionarios> findTbApontamentosFuncionariosEntities() {
        return findTbApontamentosFuncionariosEntities(true, -1, -1);
    }

    public List<TbApontamentosFuncionarios> findTbApontamentosFuncionariosEntities(int maxResults, int firstResult) {
        return findTbApontamentosFuncionariosEntities(false, maxResults, firstResult);
    }

    private List<TbApontamentosFuncionarios> findTbApontamentosFuncionariosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from TbApontamentosFuncionarios as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public TbApontamentosFuncionarios findTbApontamentosFuncionarios(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbApontamentosFuncionarios.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbApontamentosFuncionariosCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from TbApontamentosFuncionarios as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
