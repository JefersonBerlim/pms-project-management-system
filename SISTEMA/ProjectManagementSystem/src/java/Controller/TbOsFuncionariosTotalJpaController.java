/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.exceptions.NonexistentEntityException;
import Controller.exceptions.PreexistingEntityException;
import Controller.exceptions.RollbackFailureException;
import Model.TbOsFuncionariosTotal;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Model.TbOsRecursosTotal;
import Model.TbProjetoFuncionarios;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author BERLIM
 */
@ManagedBean
public class TbOsFuncionariosTotalJpaController implements Serializable {

    public TbOsFuncionariosTotalJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TbOsFuncionariosTotal tbOsFuncionariosTotal) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbOsRecursosTotal tbOsRecursosTotalHand = tbOsFuncionariosTotal.getTbOsRecursosTotalHand();
            if (tbOsRecursosTotalHand != null) {
                tbOsRecursosTotalHand = em.getReference(tbOsRecursosTotalHand.getClass(), tbOsRecursosTotalHand.getHand());
                tbOsFuncionariosTotal.setTbOsRecursosTotalHand(tbOsRecursosTotalHand);
            }
            TbProjetoFuncionarios tbProjetoFuncionariosHand = tbOsFuncionariosTotal.getTbProjetoFuncionariosHand();
            if (tbProjetoFuncionariosHand != null) {
                tbProjetoFuncionariosHand = em.getReference(tbProjetoFuncionariosHand.getClass(), tbProjetoFuncionariosHand.getHand());
                tbOsFuncionariosTotal.setTbProjetoFuncionariosHand(tbProjetoFuncionariosHand);
            }
            em.persist(tbOsFuncionariosTotal);
            if (tbOsRecursosTotalHand != null) {
                tbOsRecursosTotalHand.getTbOsFuncionariosTotalCollection().add(tbOsFuncionariosTotal);
                tbOsRecursosTotalHand = em.merge(tbOsRecursosTotalHand);
            }
            if (tbProjetoFuncionariosHand != null) {
                tbProjetoFuncionariosHand.getTbOsFuncionariosTotalCollection().add(tbOsFuncionariosTotal);
                tbProjetoFuncionariosHand = em.merge(tbProjetoFuncionariosHand);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTbOsFuncionariosTotal(tbOsFuncionariosTotal.getHand()) != null) {
                throw new PreexistingEntityException("TbOsFuncionariosTotal " + tbOsFuncionariosTotal + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TbOsFuncionariosTotal tbOsFuncionariosTotal) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbOsFuncionariosTotal persistentTbOsFuncionariosTotal = em.find(TbOsFuncionariosTotal.class, tbOsFuncionariosTotal.getHand());
            TbOsRecursosTotal tbOsRecursosTotalHandOld = persistentTbOsFuncionariosTotal.getTbOsRecursosTotalHand();
            TbOsRecursosTotal tbOsRecursosTotalHandNew = tbOsFuncionariosTotal.getTbOsRecursosTotalHand();
            TbProjetoFuncionarios tbProjetoFuncionariosHandOld = persistentTbOsFuncionariosTotal.getTbProjetoFuncionariosHand();
            TbProjetoFuncionarios tbProjetoFuncionariosHandNew = tbOsFuncionariosTotal.getTbProjetoFuncionariosHand();
            if (tbOsRecursosTotalHandNew != null) {
                tbOsRecursosTotalHandNew = em.getReference(tbOsRecursosTotalHandNew.getClass(), tbOsRecursosTotalHandNew.getHand());
                tbOsFuncionariosTotal.setTbOsRecursosTotalHand(tbOsRecursosTotalHandNew);
            }
            if (tbProjetoFuncionariosHandNew != null) {
                tbProjetoFuncionariosHandNew = em.getReference(tbProjetoFuncionariosHandNew.getClass(), tbProjetoFuncionariosHandNew.getHand());
                tbOsFuncionariosTotal.setTbProjetoFuncionariosHand(tbProjetoFuncionariosHandNew);
            }
            tbOsFuncionariosTotal = em.merge(tbOsFuncionariosTotal);
            if (tbOsRecursosTotalHandOld != null && !tbOsRecursosTotalHandOld.equals(tbOsRecursosTotalHandNew)) {
                tbOsRecursosTotalHandOld.getTbOsFuncionariosTotalCollection().remove(tbOsFuncionariosTotal);
                tbOsRecursosTotalHandOld = em.merge(tbOsRecursosTotalHandOld);
            }
            if (tbOsRecursosTotalHandNew != null && !tbOsRecursosTotalHandNew.equals(tbOsRecursosTotalHandOld)) {
                tbOsRecursosTotalHandNew.getTbOsFuncionariosTotalCollection().add(tbOsFuncionariosTotal);
                tbOsRecursosTotalHandNew = em.merge(tbOsRecursosTotalHandNew);
            }
            if (tbProjetoFuncionariosHandOld != null && !tbProjetoFuncionariosHandOld.equals(tbProjetoFuncionariosHandNew)) {
                tbProjetoFuncionariosHandOld.getTbOsFuncionariosTotalCollection().remove(tbOsFuncionariosTotal);
                tbProjetoFuncionariosHandOld = em.merge(tbProjetoFuncionariosHandOld);
            }
            if (tbProjetoFuncionariosHandNew != null && !tbProjetoFuncionariosHandNew.equals(tbProjetoFuncionariosHandOld)) {
                tbProjetoFuncionariosHandNew.getTbOsFuncionariosTotalCollection().add(tbOsFuncionariosTotal);
                tbProjetoFuncionariosHandNew = em.merge(tbProjetoFuncionariosHandNew);
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
                Integer id = tbOsFuncionariosTotal.getHand();
                if (findTbOsFuncionariosTotal(id) == null) {
                    throw new NonexistentEntityException("The tbOsFuncionariosTotal with id " + id + " no longer exists.");
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
            TbOsFuncionariosTotal tbOsFuncionariosTotal;
            try {
                tbOsFuncionariosTotal = em.getReference(TbOsFuncionariosTotal.class, id);
                tbOsFuncionariosTotal.getHand();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tbOsFuncionariosTotal with id " + id + " no longer exists.", enfe);
            }
            TbOsRecursosTotal tbOsRecursosTotalHand = tbOsFuncionariosTotal.getTbOsRecursosTotalHand();
            if (tbOsRecursosTotalHand != null) {
                tbOsRecursosTotalHand.getTbOsFuncionariosTotalCollection().remove(tbOsFuncionariosTotal);
                tbOsRecursosTotalHand = em.merge(tbOsRecursosTotalHand);
            }
            TbProjetoFuncionarios tbProjetoFuncionariosHand = tbOsFuncionariosTotal.getTbProjetoFuncionariosHand();
            if (tbProjetoFuncionariosHand != null) {
                tbProjetoFuncionariosHand.getTbOsFuncionariosTotalCollection().remove(tbOsFuncionariosTotal);
                tbProjetoFuncionariosHand = em.merge(tbProjetoFuncionariosHand);
            }
            em.remove(tbOsFuncionariosTotal);
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

    public List<TbOsFuncionariosTotal> findTbOsFuncionariosTotalEntities() {
        return findTbOsFuncionariosTotalEntities(true, -1, -1);
    }

    public List<TbOsFuncionariosTotal> findTbOsFuncionariosTotalEntities(int maxResults, int firstResult) {
        return findTbOsFuncionariosTotalEntities(false, maxResults, firstResult);
    }

    private List<TbOsFuncionariosTotal> findTbOsFuncionariosTotalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TbOsFuncionariosTotal.class));
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

    public TbOsFuncionariosTotal findTbOsFuncionariosTotal(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbOsFuncionariosTotal.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbOsFuncionariosTotalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TbOsFuncionariosTotal> rt = cq.from(TbOsFuncionariosTotal.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
