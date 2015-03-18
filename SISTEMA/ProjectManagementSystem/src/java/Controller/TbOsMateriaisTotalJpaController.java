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
import Model.TbMateriais;
import Model.TbOsMateriaisTotal;
import Model.TbOsServico;
import Model.TbProjetosMateriais;
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
public class TbOsMateriaisTotalJpaController implements Serializable {

    public TbOsMateriaisTotalJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TbOsMateriaisTotal tbOsMateriaisTotal) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbMateriais tbMateriaisHand = tbOsMateriaisTotal.getTbMateriaisHand();
            if (tbMateriaisHand != null) {
                tbMateriaisHand = em.getReference(tbMateriaisHand.getClass(), tbMateriaisHand.getHand());
                tbOsMateriaisTotal.setTbMateriaisHand(tbMateriaisHand);
            }
            TbOsServico tbOsServicoHand = tbOsMateriaisTotal.getTbOsServicoHand();
            if (tbOsServicoHand != null) {
                tbOsServicoHand = em.getReference(tbOsServicoHand.getClass(), tbOsServicoHand.getHand());
                tbOsMateriaisTotal.setTbOsServicoHand(tbOsServicoHand);
            }
            TbProjetosMateriais tbProjetosMateriaisHand = tbOsMateriaisTotal.getTbProjetosMateriaisHand();
            if (tbProjetosMateriaisHand != null) {
                tbProjetosMateriaisHand = em.getReference(tbProjetosMateriaisHand.getClass(), tbProjetosMateriaisHand.getHand());
                tbOsMateriaisTotal.setTbProjetosMateriaisHand(tbProjetosMateriaisHand);
            }
            em.persist(tbOsMateriaisTotal);
            if (tbMateriaisHand != null) {
                tbMateriaisHand.getTbOsMateriaisTotalCollection().add(tbOsMateriaisTotal);
                tbMateriaisHand = em.merge(tbMateriaisHand);
            }
            if (tbOsServicoHand != null) {
                tbOsServicoHand.getTbOsMateriaisTotalCollection().add(tbOsMateriaisTotal);
                tbOsServicoHand = em.merge(tbOsServicoHand);
            }
            if (tbProjetosMateriaisHand != null) {
                tbProjetosMateriaisHand.getTbOsMateriaisTotalCollection().add(tbOsMateriaisTotal);
                tbProjetosMateriaisHand = em.merge(tbProjetosMateriaisHand);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTbOsMateriaisTotal(tbOsMateriaisTotal.getHand()) != null) {
                throw new PreexistingEntityException("TbOsMateriaisTotal " + tbOsMateriaisTotal + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TbOsMateriaisTotal tbOsMateriaisTotal) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbOsMateriaisTotal persistentTbOsMateriaisTotal = em.find(TbOsMateriaisTotal.class, tbOsMateriaisTotal.getHand());
            TbMateriais tbMateriaisHandOld = persistentTbOsMateriaisTotal.getTbMateriaisHand();
            TbMateriais tbMateriaisHandNew = tbOsMateriaisTotal.getTbMateriaisHand();
            TbOsServico tbOsServicoHandOld = persistentTbOsMateriaisTotal.getTbOsServicoHand();
            TbOsServico tbOsServicoHandNew = tbOsMateriaisTotal.getTbOsServicoHand();
            TbProjetosMateriais tbProjetosMateriaisHandOld = persistentTbOsMateriaisTotal.getTbProjetosMateriaisHand();
            TbProjetosMateriais tbProjetosMateriaisHandNew = tbOsMateriaisTotal.getTbProjetosMateriaisHand();
            if (tbMateriaisHandNew != null) {
                tbMateriaisHandNew = em.getReference(tbMateriaisHandNew.getClass(), tbMateriaisHandNew.getHand());
                tbOsMateriaisTotal.setTbMateriaisHand(tbMateriaisHandNew);
            }
            if (tbOsServicoHandNew != null) {
                tbOsServicoHandNew = em.getReference(tbOsServicoHandNew.getClass(), tbOsServicoHandNew.getHand());
                tbOsMateriaisTotal.setTbOsServicoHand(tbOsServicoHandNew);
            }
            if (tbProjetosMateriaisHandNew != null) {
                tbProjetosMateriaisHandNew = em.getReference(tbProjetosMateriaisHandNew.getClass(), tbProjetosMateriaisHandNew.getHand());
                tbOsMateriaisTotal.setTbProjetosMateriaisHand(tbProjetosMateriaisHandNew);
            }
            tbOsMateriaisTotal = em.merge(tbOsMateriaisTotal);
            if (tbMateriaisHandOld != null && !tbMateriaisHandOld.equals(tbMateriaisHandNew)) {
                tbMateriaisHandOld.getTbOsMateriaisTotalCollection().remove(tbOsMateriaisTotal);
                tbMateriaisHandOld = em.merge(tbMateriaisHandOld);
            }
            if (tbMateriaisHandNew != null && !tbMateriaisHandNew.equals(tbMateriaisHandOld)) {
                tbMateriaisHandNew.getTbOsMateriaisTotalCollection().add(tbOsMateriaisTotal);
                tbMateriaisHandNew = em.merge(tbMateriaisHandNew);
            }
            if (tbOsServicoHandOld != null && !tbOsServicoHandOld.equals(tbOsServicoHandNew)) {
                tbOsServicoHandOld.getTbOsMateriaisTotalCollection().remove(tbOsMateriaisTotal);
                tbOsServicoHandOld = em.merge(tbOsServicoHandOld);
            }
            if (tbOsServicoHandNew != null && !tbOsServicoHandNew.equals(tbOsServicoHandOld)) {
                tbOsServicoHandNew.getTbOsMateriaisTotalCollection().add(tbOsMateriaisTotal);
                tbOsServicoHandNew = em.merge(tbOsServicoHandNew);
            }
            if (tbProjetosMateriaisHandOld != null && !tbProjetosMateriaisHandOld.equals(tbProjetosMateriaisHandNew)) {
                tbProjetosMateriaisHandOld.getTbOsMateriaisTotalCollection().remove(tbOsMateriaisTotal);
                tbProjetosMateriaisHandOld = em.merge(tbProjetosMateriaisHandOld);
            }
            if (tbProjetosMateriaisHandNew != null && !tbProjetosMateriaisHandNew.equals(tbProjetosMateriaisHandOld)) {
                tbProjetosMateriaisHandNew.getTbOsMateriaisTotalCollection().add(tbOsMateriaisTotal);
                tbProjetosMateriaisHandNew = em.merge(tbProjetosMateriaisHandNew);
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
                Integer id = tbOsMateriaisTotal.getHand();
                if (findTbOsMateriaisTotal(id) == null) {
                    throw new NonexistentEntityException("The tbOsMateriaisTotal with id " + id + " no longer exists.");
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
            TbOsMateriaisTotal tbOsMateriaisTotal;
            try {
                tbOsMateriaisTotal = em.getReference(TbOsMateriaisTotal.class, id);
                tbOsMateriaisTotal.getHand();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tbOsMateriaisTotal with id " + id + " no longer exists.", enfe);
            }
            TbMateriais tbMateriaisHand = tbOsMateriaisTotal.getTbMateriaisHand();
            if (tbMateriaisHand != null) {
                tbMateriaisHand.getTbOsMateriaisTotalCollection().remove(tbOsMateriaisTotal);
                tbMateriaisHand = em.merge(tbMateriaisHand);
            }
            TbOsServico tbOsServicoHand = tbOsMateriaisTotal.getTbOsServicoHand();
            if (tbOsServicoHand != null) {
                tbOsServicoHand.getTbOsMateriaisTotalCollection().remove(tbOsMateriaisTotal);
                tbOsServicoHand = em.merge(tbOsServicoHand);
            }
            TbProjetosMateriais tbProjetosMateriaisHand = tbOsMateriaisTotal.getTbProjetosMateriaisHand();
            if (tbProjetosMateriaisHand != null) {
                tbProjetosMateriaisHand.getTbOsMateriaisTotalCollection().remove(tbOsMateriaisTotal);
                tbProjetosMateriaisHand = em.merge(tbProjetosMateriaisHand);
            }
            em.remove(tbOsMateriaisTotal);
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

    public List<TbOsMateriaisTotal> findTbOsMateriaisTotalEntities() {
        return findTbOsMateriaisTotalEntities(true, -1, -1);
    }

    public List<TbOsMateriaisTotal> findTbOsMateriaisTotalEntities(int maxResults, int firstResult) {
        return findTbOsMateriaisTotalEntities(false, maxResults, firstResult);
    }

    private List<TbOsMateriaisTotal> findTbOsMateriaisTotalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TbOsMateriaisTotal.class));
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

    public TbOsMateriaisTotal findTbOsMateriaisTotal(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbOsMateriaisTotal.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbOsMateriaisTotalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TbOsMateriaisTotal> rt = cq.from(TbOsMateriaisTotal.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
