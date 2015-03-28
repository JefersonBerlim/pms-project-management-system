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
import Model.TbEstados;
import Model.TbPaises;
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
public class TbPaisesJpaController implements Serializable {

    public TbPaisesJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TbPaises tbPaises) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (tbPaises.getTbEstadosCollection() == null) {
            tbPaises.setTbEstadosCollection(new ArrayList<TbEstados>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<TbEstados> attachedTbEstadosCollection = new ArrayList<TbEstados>();
            for (TbEstados tbEstadosCollectionTbEstadosToAttach : tbPaises.getTbEstadosCollection()) {
                tbEstadosCollectionTbEstadosToAttach = em.getReference(tbEstadosCollectionTbEstadosToAttach.getClass(), tbEstadosCollectionTbEstadosToAttach.getHand());
                attachedTbEstadosCollection.add(tbEstadosCollectionTbEstadosToAttach);
            }
            tbPaises.setTbEstadosCollection(attachedTbEstadosCollection);
            em.persist(tbPaises);
            for (TbEstados tbEstadosCollectionTbEstados : tbPaises.getTbEstadosCollection()) {
                TbPaises oldTbPaisesHandOfTbEstadosCollectionTbEstados = tbEstadosCollectionTbEstados.getTbPaisesHand();
                tbEstadosCollectionTbEstados.setTbPaisesHand(tbPaises);
                tbEstadosCollectionTbEstados = em.merge(tbEstadosCollectionTbEstados);
                if (oldTbPaisesHandOfTbEstadosCollectionTbEstados != null) {
                    oldTbPaisesHandOfTbEstadosCollectionTbEstados.getTbEstadosCollection().remove(tbEstadosCollectionTbEstados);
                    oldTbPaisesHandOfTbEstadosCollectionTbEstados = em.merge(oldTbPaisesHandOfTbEstadosCollectionTbEstados);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTbPaises(tbPaises.getHand()) != null) {
                throw new PreexistingEntityException("TbPaises " + tbPaises + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TbPaises tbPaises) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbPaises persistentTbPaises = em.find(TbPaises.class, tbPaises.getHand());
            Collection<TbEstados> tbEstadosCollectionOld = persistentTbPaises.getTbEstadosCollection();
            Collection<TbEstados> tbEstadosCollectionNew = tbPaises.getTbEstadosCollection();
            List<String> illegalOrphanMessages = null;
            for (TbEstados tbEstadosCollectionOldTbEstados : tbEstadosCollectionOld) {
                if (!tbEstadosCollectionNew.contains(tbEstadosCollectionOldTbEstados)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TbEstados " + tbEstadosCollectionOldTbEstados + " since its tbPaisesHand field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<TbEstados> attachedTbEstadosCollectionNew = new ArrayList<TbEstados>();
            for (TbEstados tbEstadosCollectionNewTbEstadosToAttach : tbEstadosCollectionNew) {
                tbEstadosCollectionNewTbEstadosToAttach = em.getReference(tbEstadosCollectionNewTbEstadosToAttach.getClass(), tbEstadosCollectionNewTbEstadosToAttach.getHand());
                attachedTbEstadosCollectionNew.add(tbEstadosCollectionNewTbEstadosToAttach);
            }
            tbEstadosCollectionNew = attachedTbEstadosCollectionNew;
            tbPaises.setTbEstadosCollection(tbEstadosCollectionNew);
            tbPaises = em.merge(tbPaises);
            for (TbEstados tbEstadosCollectionNewTbEstados : tbEstadosCollectionNew) {
                if (!tbEstadosCollectionOld.contains(tbEstadosCollectionNewTbEstados)) {
                    TbPaises oldTbPaisesHandOfTbEstadosCollectionNewTbEstados = tbEstadosCollectionNewTbEstados.getTbPaisesHand();
                    tbEstadosCollectionNewTbEstados.setTbPaisesHand(tbPaises);
                    tbEstadosCollectionNewTbEstados = em.merge(tbEstadosCollectionNewTbEstados);
                    if (oldTbPaisesHandOfTbEstadosCollectionNewTbEstados != null && !oldTbPaisesHandOfTbEstadosCollectionNewTbEstados.equals(tbPaises)) {
                        oldTbPaisesHandOfTbEstadosCollectionNewTbEstados.getTbEstadosCollection().remove(tbEstadosCollectionNewTbEstados);
                        oldTbPaisesHandOfTbEstadosCollectionNewTbEstados = em.merge(oldTbPaisesHandOfTbEstadosCollectionNewTbEstados);
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
                Integer id = tbPaises.getHand();
                if (findTbPaises(id) == null) {
                    throw new NonexistentEntityException("The tbPaises with id " + id + " no longer exists.");
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
            TbPaises tbPaises;
            try {
                tbPaises = em.getReference(TbPaises.class, id);
                tbPaises.getHand();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tbPaises with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TbEstados> tbEstadosCollectionOrphanCheck = tbPaises.getTbEstadosCollection();
            for (TbEstados tbEstadosCollectionOrphanCheckTbEstados : tbEstadosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbPaises (" + tbPaises + ") cannot be destroyed since the TbEstados " + tbEstadosCollectionOrphanCheckTbEstados + " in its tbEstadosCollection field has a non-nullable tbPaisesHand field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tbPaises);
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

    public List<TbPaises> findTbPaisesEntities() {
        return findTbPaisesEntities(true, -1, -1);
    }

    public List<TbPaises> findTbPaisesEntities(int maxResults, int firstResult) {
        return findTbPaisesEntities(false, maxResults, firstResult);
    }

    private List<TbPaises> findTbPaisesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TbPaises.class));
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

    public TbPaises findTbPaises(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbPaises.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbPaisesCount() {
        EntityManager em = getEntityManager();
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
