/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.exceptions.NonexistentEntityException;
import Controller.exceptions.PreexistingEntityException;
import Controller.exceptions.RollbackFailureException;
import Model.TbDiaNaoUtil;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;

/**
 *
 * @author BERLIM
 */
public class TbDiaNaoUtilJpaController implements Serializable {

    public TbDiaNaoUtilJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TbDiaNaoUtil tbDiaNaoUtil) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(tbDiaNaoUtil);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTbDiaNaoUtil(tbDiaNaoUtil.getHand()) != null) {
                throw new PreexistingEntityException("TbDiaNaoUtil " + tbDiaNaoUtil + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TbDiaNaoUtil tbDiaNaoUtil) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            tbDiaNaoUtil = em.merge(tbDiaNaoUtil);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tbDiaNaoUtil.getHand();
                if (findTbDiaNaoUtil(id) == null) {
                    throw new NonexistentEntityException("The tbDiaNaoUtil with id " + id + " no longer exists.");
                }
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
            utx.begin();
            em = getEntityManager();
            TbDiaNaoUtil tbDiaNaoUtil;
            try {
                tbDiaNaoUtil = em.getReference(TbDiaNaoUtil.class, id);
                tbDiaNaoUtil.getHand();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tbDiaNaoUtil with id " + id + " no longer exists.", enfe);
            }
            em.remove(tbDiaNaoUtil);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
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

    public List<TbDiaNaoUtil> findTbDiaNaoUtilEntities() {
        return findTbDiaNaoUtilEntities(true, -1, -1);
    }

    public List<TbDiaNaoUtil> findTbDiaNaoUtilEntities(int maxResults, int firstResult) {
        return findTbDiaNaoUtilEntities(false, maxResults, firstResult);
    }

    private List<TbDiaNaoUtil> findTbDiaNaoUtilEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TbDiaNaoUtil.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public TbDiaNaoUtil findTbDiaNaoUtil(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbDiaNaoUtil.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbDiaNaoUtilCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TbDiaNaoUtil> rt = cq.from(TbDiaNaoUtil.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
