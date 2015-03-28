/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.exceptions.IllegalOrphanException;
import Controller.exceptions.NonexistentEntityException;
import Controller.exceptions.PreexistingEntityException;
import Controller.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Model.TbMateriais;
import Model.TbProjetosServicos;
import Model.TbOsMateriaisTotal;
import Model.TbProjetosMateriais;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author BERLIM
 */
public class TbProjetosMateriaisJpaController implements Serializable {

    public TbProjetosMateriaisJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TbProjetosMateriais tbProjetosMateriais) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (tbProjetosMateriais.getTbOsMateriaisTotalCollection() == null) {
            tbProjetosMateriais.setTbOsMateriaisTotalCollection(new ArrayList<TbOsMateriaisTotal>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbMateriais tbMateriaisHand = tbProjetosMateriais.getTbMateriaisHand();
            if (tbMateriaisHand != null) {
                tbMateriaisHand = em.getReference(tbMateriaisHand.getClass(), tbMateriaisHand.getHand());
                tbProjetosMateriais.setTbMateriaisHand(tbMateriaisHand);
            }
            TbProjetosServicos tbProjetosServicosHand = tbProjetosMateriais.getTbProjetosServicosHand();
            if (tbProjetosServicosHand != null) {
                tbProjetosServicosHand = em.getReference(tbProjetosServicosHand.getClass(), tbProjetosServicosHand.getHand());
                tbProjetosMateriais.setTbProjetosServicosHand(tbProjetosServicosHand);
            }
            Collection<TbOsMateriaisTotal> attachedTbOsMateriaisTotalCollection = new ArrayList<TbOsMateriaisTotal>();
            for (TbOsMateriaisTotal tbOsMateriaisTotalCollectionTbOsMateriaisTotalToAttach : tbProjetosMateriais.getTbOsMateriaisTotalCollection()) {
                tbOsMateriaisTotalCollectionTbOsMateriaisTotalToAttach = em.getReference(tbOsMateriaisTotalCollectionTbOsMateriaisTotalToAttach.getClass(), tbOsMateriaisTotalCollectionTbOsMateriaisTotalToAttach.getHand());
                attachedTbOsMateriaisTotalCollection.add(tbOsMateriaisTotalCollectionTbOsMateriaisTotalToAttach);
            }
            tbProjetosMateriais.setTbOsMateriaisTotalCollection(attachedTbOsMateriaisTotalCollection);
            em.persist(tbProjetosMateriais);
            if (tbMateriaisHand != null) {
                tbMateriaisHand.getTbProjetosMateriaisCollection().add(tbProjetosMateriais);
                tbMateriaisHand = em.merge(tbMateriaisHand);
            }
            if (tbProjetosServicosHand != null) {
                tbProjetosServicosHand.getTbProjetosMateriaisCollection().add(tbProjetosMateriais);
                tbProjetosServicosHand = em.merge(tbProjetosServicosHand);
            }
            for (TbOsMateriaisTotal tbOsMateriaisTotalCollectionTbOsMateriaisTotal : tbProjetosMateriais.getTbOsMateriaisTotalCollection()) {
                TbProjetosMateriais oldTbProjetosMateriaisHandOfTbOsMateriaisTotalCollectionTbOsMateriaisTotal = tbOsMateriaisTotalCollectionTbOsMateriaisTotal.getTbProjetosMateriaisHand();
                tbOsMateriaisTotalCollectionTbOsMateriaisTotal.setTbProjetosMateriaisHand(tbProjetosMateriais);
                tbOsMateriaisTotalCollectionTbOsMateriaisTotal = em.merge(tbOsMateriaisTotalCollectionTbOsMateriaisTotal);
                if (oldTbProjetosMateriaisHandOfTbOsMateriaisTotalCollectionTbOsMateriaisTotal != null) {
                    oldTbProjetosMateriaisHandOfTbOsMateriaisTotalCollectionTbOsMateriaisTotal.getTbOsMateriaisTotalCollection().remove(tbOsMateriaisTotalCollectionTbOsMateriaisTotal);
                    oldTbProjetosMateriaisHandOfTbOsMateriaisTotalCollectionTbOsMateriaisTotal = em.merge(oldTbProjetosMateriaisHandOfTbOsMateriaisTotalCollectionTbOsMateriaisTotal);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTbProjetosMateriais(tbProjetosMateriais.getHand()) != null) {
                throw new PreexistingEntityException("TbProjetosMateriais " + tbProjetosMateriais + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TbProjetosMateriais tbProjetosMateriais) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbProjetosMateriais persistentTbProjetosMateriais = em.find(TbProjetosMateriais.class, tbProjetosMateriais.getHand());
            TbMateriais tbMateriaisHandOld = persistentTbProjetosMateriais.getTbMateriaisHand();
            TbMateriais tbMateriaisHandNew = tbProjetosMateriais.getTbMateriaisHand();
            TbProjetosServicos tbProjetosServicosHandOld = persistentTbProjetosMateriais.getTbProjetosServicosHand();
            TbProjetosServicos tbProjetosServicosHandNew = tbProjetosMateriais.getTbProjetosServicosHand();
            Collection<TbOsMateriaisTotal> tbOsMateriaisTotalCollectionOld = persistentTbProjetosMateriais.getTbOsMateriaisTotalCollection();
            Collection<TbOsMateriaisTotal> tbOsMateriaisTotalCollectionNew = tbProjetosMateriais.getTbOsMateriaisTotalCollection();
            List<String> illegalOrphanMessages = null;
            for (TbOsMateriaisTotal tbOsMateriaisTotalCollectionOldTbOsMateriaisTotal : tbOsMateriaisTotalCollectionOld) {
                if (!tbOsMateriaisTotalCollectionNew.contains(tbOsMateriaisTotalCollectionOldTbOsMateriaisTotal)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TbOsMateriaisTotal " + tbOsMateriaisTotalCollectionOldTbOsMateriaisTotal + " since its tbProjetosMateriaisHand field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tbMateriaisHandNew != null) {
                tbMateriaisHandNew = em.getReference(tbMateriaisHandNew.getClass(), tbMateriaisHandNew.getHand());
                tbProjetosMateriais.setTbMateriaisHand(tbMateriaisHandNew);
            }
            if (tbProjetosServicosHandNew != null) {
                tbProjetosServicosHandNew = em.getReference(tbProjetosServicosHandNew.getClass(), tbProjetosServicosHandNew.getHand());
                tbProjetosMateriais.setTbProjetosServicosHand(tbProjetosServicosHandNew);
            }
            Collection<TbOsMateriaisTotal> attachedTbOsMateriaisTotalCollectionNew = new ArrayList<TbOsMateriaisTotal>();
            for (TbOsMateriaisTotal tbOsMateriaisTotalCollectionNewTbOsMateriaisTotalToAttach : tbOsMateriaisTotalCollectionNew) {
                tbOsMateriaisTotalCollectionNewTbOsMateriaisTotalToAttach = em.getReference(tbOsMateriaisTotalCollectionNewTbOsMateriaisTotalToAttach.getClass(), tbOsMateriaisTotalCollectionNewTbOsMateriaisTotalToAttach.getHand());
                attachedTbOsMateriaisTotalCollectionNew.add(tbOsMateriaisTotalCollectionNewTbOsMateriaisTotalToAttach);
            }
            tbOsMateriaisTotalCollectionNew = attachedTbOsMateriaisTotalCollectionNew;
            tbProjetosMateriais.setTbOsMateriaisTotalCollection(tbOsMateriaisTotalCollectionNew);
            tbProjetosMateriais = em.merge(tbProjetosMateriais);
            if (tbMateriaisHandOld != null && !tbMateriaisHandOld.equals(tbMateriaisHandNew)) {
                tbMateriaisHandOld.getTbProjetosMateriaisCollection().remove(tbProjetosMateriais);
                tbMateriaisHandOld = em.merge(tbMateriaisHandOld);
            }
            if (tbMateriaisHandNew != null && !tbMateriaisHandNew.equals(tbMateriaisHandOld)) {
                tbMateriaisHandNew.getTbProjetosMateriaisCollection().add(tbProjetosMateriais);
                tbMateriaisHandNew = em.merge(tbMateriaisHandNew);
            }
            if (tbProjetosServicosHandOld != null && !tbProjetosServicosHandOld.equals(tbProjetosServicosHandNew)) {
                tbProjetosServicosHandOld.getTbProjetosMateriaisCollection().remove(tbProjetosMateriais);
                tbProjetosServicosHandOld = em.merge(tbProjetosServicosHandOld);
            }
            if (tbProjetosServicosHandNew != null && !tbProjetosServicosHandNew.equals(tbProjetosServicosHandOld)) {
                tbProjetosServicosHandNew.getTbProjetosMateriaisCollection().add(tbProjetosMateriais);
                tbProjetosServicosHandNew = em.merge(tbProjetosServicosHandNew);
            }
            for (TbOsMateriaisTotal tbOsMateriaisTotalCollectionNewTbOsMateriaisTotal : tbOsMateriaisTotalCollectionNew) {
                if (!tbOsMateriaisTotalCollectionOld.contains(tbOsMateriaisTotalCollectionNewTbOsMateriaisTotal)) {
                    TbProjetosMateriais oldTbProjetosMateriaisHandOfTbOsMateriaisTotalCollectionNewTbOsMateriaisTotal = tbOsMateriaisTotalCollectionNewTbOsMateriaisTotal.getTbProjetosMateriaisHand();
                    tbOsMateriaisTotalCollectionNewTbOsMateriaisTotal.setTbProjetosMateriaisHand(tbProjetosMateriais);
                    tbOsMateriaisTotalCollectionNewTbOsMateriaisTotal = em.merge(tbOsMateriaisTotalCollectionNewTbOsMateriaisTotal);
                    if (oldTbProjetosMateriaisHandOfTbOsMateriaisTotalCollectionNewTbOsMateriaisTotal != null && !oldTbProjetosMateriaisHandOfTbOsMateriaisTotalCollectionNewTbOsMateriaisTotal.equals(tbProjetosMateriais)) {
                        oldTbProjetosMateriaisHandOfTbOsMateriaisTotalCollectionNewTbOsMateriaisTotal.getTbOsMateriaisTotalCollection().remove(tbOsMateriaisTotalCollectionNewTbOsMateriaisTotal);
                        oldTbProjetosMateriaisHandOfTbOsMateriaisTotalCollectionNewTbOsMateriaisTotal = em.merge(oldTbProjetosMateriaisHandOfTbOsMateriaisTotalCollectionNewTbOsMateriaisTotal);
                    }
                }
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
                Integer id = tbProjetosMateriais.getHand();
                if (findTbProjetosMateriais(id) == null) {
                    throw new NonexistentEntityException("The tbProjetosMateriais with id " + id + " no longer exists.");
                }
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
            utx.begin();
            em = getEntityManager();
            TbProjetosMateriais tbProjetosMateriais;
            try {
                tbProjetosMateriais = em.getReference(TbProjetosMateriais.class, id);
                tbProjetosMateriais.getHand();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tbProjetosMateriais with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TbOsMateriaisTotal> tbOsMateriaisTotalCollectionOrphanCheck = tbProjetosMateriais.getTbOsMateriaisTotalCollection();
            for (TbOsMateriaisTotal tbOsMateriaisTotalCollectionOrphanCheckTbOsMateriaisTotal : tbOsMateriaisTotalCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbProjetosMateriais (" + tbProjetosMateriais + ") cannot be destroyed since the TbOsMateriaisTotal " + tbOsMateriaisTotalCollectionOrphanCheckTbOsMateriaisTotal + " in its tbOsMateriaisTotalCollection field has a non-nullable tbProjetosMateriaisHand field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            TbMateriais tbMateriaisHand = tbProjetosMateriais.getTbMateriaisHand();
            if (tbMateriaisHand != null) {
                tbMateriaisHand.getTbProjetosMateriaisCollection().remove(tbProjetosMateriais);
                tbMateriaisHand = em.merge(tbMateriaisHand);
            }
            TbProjetosServicos tbProjetosServicosHand = tbProjetosMateriais.getTbProjetosServicosHand();
            if (tbProjetosServicosHand != null) {
                tbProjetosServicosHand.getTbProjetosMateriaisCollection().remove(tbProjetosMateriais);
                tbProjetosServicosHand = em.merge(tbProjetosServicosHand);
            }
            em.remove(tbProjetosMateriais);
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

    public List<TbProjetosMateriais> findTbProjetosMateriaisEntities() {
        return findTbProjetosMateriaisEntities(true, -1, -1);
    }

    public List<TbProjetosMateriais> findTbProjetosMateriaisEntities(int maxResults, int firstResult) {
        return findTbProjetosMateriaisEntities(false, maxResults, firstResult);
    }

    private List<TbProjetosMateriais> findTbProjetosMateriaisEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TbProjetosMateriais.class));
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

    public TbProjetosMateriais findTbProjetosMateriais(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbProjetosMateriais.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbProjetosMateriaisCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TbProjetosMateriais> rt = cq.from(TbProjetosMateriais.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
