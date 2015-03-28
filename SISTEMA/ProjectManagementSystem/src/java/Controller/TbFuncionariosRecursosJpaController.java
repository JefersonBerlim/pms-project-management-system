/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.exceptions.NonexistentEntityException;
import Controller.exceptions.PreexistingEntityException;
import Controller.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Model.TbFuncionarios;
import Model.TbFuncionariosRecursos;
import Model.TbRecursos;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author BERLIM
 */
public class TbFuncionariosRecursosJpaController implements Serializable {

    public TbFuncionariosRecursosJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TbFuncionariosRecursos tbFuncionariosRecursos) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbFuncionarios tbFuncionariosHand = tbFuncionariosRecursos.getTbFuncionariosHand();
            if (tbFuncionariosHand != null) {
                tbFuncionariosHand = em.getReference(tbFuncionariosHand.getClass(), tbFuncionariosHand.getHand());
                tbFuncionariosRecursos.setTbFuncionariosHand(tbFuncionariosHand);
            }
            TbRecursos tbRecursosHand = tbFuncionariosRecursos.getTbRecursosHand();
            if (tbRecursosHand != null) {
                tbRecursosHand = em.getReference(tbRecursosHand.getClass(), tbRecursosHand.getHand());
                tbFuncionariosRecursos.setTbRecursosHand(tbRecursosHand);
            }
            em.persist(tbFuncionariosRecursos);
            if (tbFuncionariosHand != null) {
                tbFuncionariosHand.getTbFuncionariosRecursosCollection().add(tbFuncionariosRecursos);
                tbFuncionariosHand = em.merge(tbFuncionariosHand);
            }
            if (tbRecursosHand != null) {
                tbRecursosHand.getTbFuncionariosRecursosCollection().add(tbFuncionariosRecursos);
                tbRecursosHand = em.merge(tbRecursosHand);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTbFuncionariosRecursos(tbFuncionariosRecursos.getHand()) != null) {
                throw new PreexistingEntityException("TbFuncionariosRecursos " + tbFuncionariosRecursos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TbFuncionariosRecursos tbFuncionariosRecursos) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbFuncionariosRecursos persistentTbFuncionariosRecursos = em.find(TbFuncionariosRecursos.class, tbFuncionariosRecursos.getHand());
            TbFuncionarios tbFuncionariosHandOld = persistentTbFuncionariosRecursos.getTbFuncionariosHand();
            TbFuncionarios tbFuncionariosHandNew = tbFuncionariosRecursos.getTbFuncionariosHand();
            TbRecursos tbRecursosHandOld = persistentTbFuncionariosRecursos.getTbRecursosHand();
            TbRecursos tbRecursosHandNew = tbFuncionariosRecursos.getTbRecursosHand();
            if (tbFuncionariosHandNew != null) {
                tbFuncionariosHandNew = em.getReference(tbFuncionariosHandNew.getClass(), tbFuncionariosHandNew.getHand());
                tbFuncionariosRecursos.setTbFuncionariosHand(tbFuncionariosHandNew);
            }
            if (tbRecursosHandNew != null) {
                tbRecursosHandNew = em.getReference(tbRecursosHandNew.getClass(), tbRecursosHandNew.getHand());
                tbFuncionariosRecursos.setTbRecursosHand(tbRecursosHandNew);
            }
            tbFuncionariosRecursos = em.merge(tbFuncionariosRecursos);
            if (tbFuncionariosHandOld != null && !tbFuncionariosHandOld.equals(tbFuncionariosHandNew)) {
                tbFuncionariosHandOld.getTbFuncionariosRecursosCollection().remove(tbFuncionariosRecursos);
                tbFuncionariosHandOld = em.merge(tbFuncionariosHandOld);
            }
            if (tbFuncionariosHandNew != null && !tbFuncionariosHandNew.equals(tbFuncionariosHandOld)) {
                tbFuncionariosHandNew.getTbFuncionariosRecursosCollection().add(tbFuncionariosRecursos);
                tbFuncionariosHandNew = em.merge(tbFuncionariosHandNew);
            }
            if (tbRecursosHandOld != null && !tbRecursosHandOld.equals(tbRecursosHandNew)) {
                tbRecursosHandOld.getTbFuncionariosRecursosCollection().remove(tbFuncionariosRecursos);
                tbRecursosHandOld = em.merge(tbRecursosHandOld);
            }
            if (tbRecursosHandNew != null && !tbRecursosHandNew.equals(tbRecursosHandOld)) {
                tbRecursosHandNew.getTbFuncionariosRecursosCollection().add(tbFuncionariosRecursos);
                tbRecursosHandNew = em.merge(tbRecursosHandNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tbFuncionariosRecursos.getHand();
                if (findTbFuncionariosRecursos(id) == null) {
                    throw new NonexistentEntityException("The tbFuncionariosRecursos with id " + id + " no longer exists.");
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
            TbFuncionariosRecursos tbFuncionariosRecursos;
            try {
                tbFuncionariosRecursos = em.getReference(TbFuncionariosRecursos.class, id);
                tbFuncionariosRecursos.getHand();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tbFuncionariosRecursos with id " + id + " no longer exists.", enfe);
            }
            TbFuncionarios tbFuncionariosHand = tbFuncionariosRecursos.getTbFuncionariosHand();
            if (tbFuncionariosHand != null) {
                tbFuncionariosHand.getTbFuncionariosRecursosCollection().remove(tbFuncionariosRecursos);
                tbFuncionariosHand = em.merge(tbFuncionariosHand);
            }
            TbRecursos tbRecursosHand = tbFuncionariosRecursos.getTbRecursosHand();
            if (tbRecursosHand != null) {
                tbRecursosHand.getTbFuncionariosRecursosCollection().remove(tbFuncionariosRecursos);
                tbRecursosHand = em.merge(tbRecursosHand);
            }
            em.remove(tbFuncionariosRecursos);
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

    public List<TbFuncionariosRecursos> findTbFuncionariosRecursosEntities() {
        return findTbFuncionariosRecursosEntities(true, -1, -1);
    }

    public List<TbFuncionariosRecursos> findTbFuncionariosRecursosEntities(int maxResults, int firstResult) {
        return findTbFuncionariosRecursosEntities(false, maxResults, firstResult);
    }

    private List<TbFuncionariosRecursos> findTbFuncionariosRecursosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TbFuncionariosRecursos.class));
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

    public TbFuncionariosRecursos findTbFuncionariosRecursos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbFuncionariosRecursos.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbFuncionariosRecursosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TbFuncionariosRecursos> rt = cq.from(TbFuncionariosRecursos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
