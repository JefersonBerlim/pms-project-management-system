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
import Model.TbUnidadeMedida;
import java.util.ArrayList;
import java.util.Collection;
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
public class TbUnidadeMedidaJpaController implements Serializable {

    public TbUnidadeMedidaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TbUnidadeMedida tbUnidadeMedida) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (tbUnidadeMedida.getTbMateriaisCollection() == null) {
            tbUnidadeMedida.setTbMateriaisCollection(new ArrayList<TbMateriais>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<TbMateriais> attachedTbMateriaisCollection = new ArrayList<TbMateriais>();
            for (TbMateriais tbMateriaisCollectionTbMateriaisToAttach : tbUnidadeMedida.getTbMateriaisCollection()) {
                tbMateriaisCollectionTbMateriaisToAttach = em.getReference(tbMateriaisCollectionTbMateriaisToAttach.getClass(), tbMateriaisCollectionTbMateriaisToAttach.getHand());
                attachedTbMateriaisCollection.add(tbMateriaisCollectionTbMateriaisToAttach);
            }
            tbUnidadeMedida.setTbMateriaisCollection(attachedTbMateriaisCollection);
            em.persist(tbUnidadeMedida);
            for (TbMateriais tbMateriaisCollectionTbMateriais : tbUnidadeMedida.getTbMateriaisCollection()) {
                TbUnidadeMedida oldTbUnidadeMedidaHandOfTbMateriaisCollectionTbMateriais = tbMateriaisCollectionTbMateriais.getTbUnidadeMedidaHand();
                tbMateriaisCollectionTbMateriais.setTbUnidadeMedidaHand(tbUnidadeMedida);
                tbMateriaisCollectionTbMateriais = em.merge(tbMateriaisCollectionTbMateriais);
                if (oldTbUnidadeMedidaHandOfTbMateriaisCollectionTbMateriais != null) {
                    oldTbUnidadeMedidaHandOfTbMateriaisCollectionTbMateriais.getTbMateriaisCollection().remove(tbMateriaisCollectionTbMateriais);
                    oldTbUnidadeMedidaHandOfTbMateriaisCollectionTbMateriais = em.merge(oldTbUnidadeMedidaHandOfTbMateriaisCollectionTbMateriais);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTbUnidadeMedida(tbUnidadeMedida.getHand()) != null) {
                throw new PreexistingEntityException("TbUnidadeMedida " + tbUnidadeMedida + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TbUnidadeMedida tbUnidadeMedida) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbUnidadeMedida persistentTbUnidadeMedida = em.find(TbUnidadeMedida.class, tbUnidadeMedida.getHand());
            Collection<TbMateriais> tbMateriaisCollectionOld = persistentTbUnidadeMedida.getTbMateriaisCollection();
            Collection<TbMateriais> tbMateriaisCollectionNew = tbUnidadeMedida.getTbMateriaisCollection();
            List<String> illegalOrphanMessages = null;
            for (TbMateriais tbMateriaisCollectionOldTbMateriais : tbMateriaisCollectionOld) {
                if (!tbMateriaisCollectionNew.contains(tbMateriaisCollectionOldTbMateriais)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TbMateriais " + tbMateriaisCollectionOldTbMateriais + " since its tbUnidadeMedidaHand field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<TbMateriais> attachedTbMateriaisCollectionNew = new ArrayList<TbMateriais>();
            for (TbMateriais tbMateriaisCollectionNewTbMateriaisToAttach : tbMateriaisCollectionNew) {
                tbMateriaisCollectionNewTbMateriaisToAttach = em.getReference(tbMateriaisCollectionNewTbMateriaisToAttach.getClass(), tbMateriaisCollectionNewTbMateriaisToAttach.getHand());
                attachedTbMateriaisCollectionNew.add(tbMateriaisCollectionNewTbMateriaisToAttach);
            }
            tbMateriaisCollectionNew = attachedTbMateriaisCollectionNew;
            tbUnidadeMedida.setTbMateriaisCollection(tbMateriaisCollectionNew);
            tbUnidadeMedida = em.merge(tbUnidadeMedida);
            for (TbMateriais tbMateriaisCollectionNewTbMateriais : tbMateriaisCollectionNew) {
                if (!tbMateriaisCollectionOld.contains(tbMateriaisCollectionNewTbMateriais)) {
                    TbUnidadeMedida oldTbUnidadeMedidaHandOfTbMateriaisCollectionNewTbMateriais = tbMateriaisCollectionNewTbMateriais.getTbUnidadeMedidaHand();
                    tbMateriaisCollectionNewTbMateriais.setTbUnidadeMedidaHand(tbUnidadeMedida);
                    tbMateriaisCollectionNewTbMateriais = em.merge(tbMateriaisCollectionNewTbMateriais);
                    if (oldTbUnidadeMedidaHandOfTbMateriaisCollectionNewTbMateriais != null && !oldTbUnidadeMedidaHandOfTbMateriaisCollectionNewTbMateriais.equals(tbUnidadeMedida)) {
                        oldTbUnidadeMedidaHandOfTbMateriaisCollectionNewTbMateriais.getTbMateriaisCollection().remove(tbMateriaisCollectionNewTbMateriais);
                        oldTbUnidadeMedidaHandOfTbMateriaisCollectionNewTbMateriais = em.merge(oldTbUnidadeMedidaHandOfTbMateriaisCollectionNewTbMateriais);
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
                Integer id = tbUnidadeMedida.getHand();
                if (findTbUnidadeMedida(id) == null) {
                    throw new NonexistentEntityException("The tbUnidadeMedida with id " + id + " no longer exists.");
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
            TbUnidadeMedida tbUnidadeMedida;
            try {
                tbUnidadeMedida = em.getReference(TbUnidadeMedida.class, id);
                tbUnidadeMedida.getHand();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tbUnidadeMedida with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TbMateriais> tbMateriaisCollectionOrphanCheck = tbUnidadeMedida.getTbMateriaisCollection();
            for (TbMateriais tbMateriaisCollectionOrphanCheckTbMateriais : tbMateriaisCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbUnidadeMedida (" + tbUnidadeMedida + ") cannot be destroyed since the TbMateriais " + tbMateriaisCollectionOrphanCheckTbMateriais + " in its tbMateriaisCollection field has a non-nullable tbUnidadeMedidaHand field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tbUnidadeMedida);
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

    public List<TbUnidadeMedida> findTbUnidadeMedidaEntities() {
        return findTbUnidadeMedidaEntities(true, -1, -1);
    }

    public List<TbUnidadeMedida> findTbUnidadeMedidaEntities(int maxResults, int firstResult) {
        return findTbUnidadeMedidaEntities(false, maxResults, firstResult);
    }

    private List<TbUnidadeMedida> findTbUnidadeMedidaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TbUnidadeMedida.class));
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

    public TbUnidadeMedida findTbUnidadeMedida(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbUnidadeMedida.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbUnidadeMedidaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TbUnidadeMedida> rt = cq.from(TbUnidadeMedida.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
