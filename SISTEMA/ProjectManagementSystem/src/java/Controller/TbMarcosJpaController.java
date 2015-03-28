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
import Model.TbMarcos;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Model.TbProjetoMarcos;
import java.util.ArrayList;
import java.util.Collection;
import Model.TbMarcosServicos;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author BERLIM
 */
public class TbMarcosJpaController implements Serializable {

    public TbMarcosJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TbMarcos tbMarcos) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (tbMarcos.getTbProjetoMarcosCollection() == null) {
            tbMarcos.setTbProjetoMarcosCollection(new ArrayList<TbProjetoMarcos>());
        }
        if (tbMarcos.getTbMarcosServicosCollection() == null) {
            tbMarcos.setTbMarcosServicosCollection(new ArrayList<TbMarcosServicos>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<TbProjetoMarcos> attachedTbProjetoMarcosCollection = new ArrayList<TbProjetoMarcos>();
            for (TbProjetoMarcos tbProjetoMarcosCollectionTbProjetoMarcosToAttach : tbMarcos.getTbProjetoMarcosCollection()) {
                tbProjetoMarcosCollectionTbProjetoMarcosToAttach = em.getReference(tbProjetoMarcosCollectionTbProjetoMarcosToAttach.getClass(), tbProjetoMarcosCollectionTbProjetoMarcosToAttach.getHand());
                attachedTbProjetoMarcosCollection.add(tbProjetoMarcosCollectionTbProjetoMarcosToAttach);
            }
            tbMarcos.setTbProjetoMarcosCollection(attachedTbProjetoMarcosCollection);
            Collection<TbMarcosServicos> attachedTbMarcosServicosCollection = new ArrayList<TbMarcosServicos>();
            for (TbMarcosServicos tbMarcosServicosCollectionTbMarcosServicosToAttach : tbMarcos.getTbMarcosServicosCollection()) {
                tbMarcosServicosCollectionTbMarcosServicosToAttach = em.getReference(tbMarcosServicosCollectionTbMarcosServicosToAttach.getClass(), tbMarcosServicosCollectionTbMarcosServicosToAttach.getHand());
                attachedTbMarcosServicosCollection.add(tbMarcosServicosCollectionTbMarcosServicosToAttach);
            }
            tbMarcos.setTbMarcosServicosCollection(attachedTbMarcosServicosCollection);
            em.persist(tbMarcos);
            for (TbProjetoMarcos tbProjetoMarcosCollectionTbProjetoMarcos : tbMarcos.getTbProjetoMarcosCollection()) {
                TbMarcos oldTbMarcosHandOfTbProjetoMarcosCollectionTbProjetoMarcos = tbProjetoMarcosCollectionTbProjetoMarcos.getTbMarcosHand();
                tbProjetoMarcosCollectionTbProjetoMarcos.setTbMarcosHand(tbMarcos);
                tbProjetoMarcosCollectionTbProjetoMarcos = em.merge(tbProjetoMarcosCollectionTbProjetoMarcos);
                if (oldTbMarcosHandOfTbProjetoMarcosCollectionTbProjetoMarcos != null) {
                    oldTbMarcosHandOfTbProjetoMarcosCollectionTbProjetoMarcos.getTbProjetoMarcosCollection().remove(tbProjetoMarcosCollectionTbProjetoMarcos);
                    oldTbMarcosHandOfTbProjetoMarcosCollectionTbProjetoMarcos = em.merge(oldTbMarcosHandOfTbProjetoMarcosCollectionTbProjetoMarcos);
                }
            }
            for (TbMarcosServicos tbMarcosServicosCollectionTbMarcosServicos : tbMarcos.getTbMarcosServicosCollection()) {
                TbMarcos oldTbMarcosHandOfTbMarcosServicosCollectionTbMarcosServicos = tbMarcosServicosCollectionTbMarcosServicos.getTbMarcosHand();
                tbMarcosServicosCollectionTbMarcosServicos.setTbMarcosHand(tbMarcos);
                tbMarcosServicosCollectionTbMarcosServicos = em.merge(tbMarcosServicosCollectionTbMarcosServicos);
                if (oldTbMarcosHandOfTbMarcosServicosCollectionTbMarcosServicos != null) {
                    oldTbMarcosHandOfTbMarcosServicosCollectionTbMarcosServicos.getTbMarcosServicosCollection().remove(tbMarcosServicosCollectionTbMarcosServicos);
                    oldTbMarcosHandOfTbMarcosServicosCollectionTbMarcosServicos = em.merge(oldTbMarcosHandOfTbMarcosServicosCollectionTbMarcosServicos);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTbMarcos(tbMarcos.getHand()) != null) {
                throw new PreexistingEntityException("TbMarcos " + tbMarcos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TbMarcos tbMarcos) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbMarcos persistentTbMarcos = em.find(TbMarcos.class, tbMarcos.getHand());
            Collection<TbProjetoMarcos> tbProjetoMarcosCollectionOld = persistentTbMarcos.getTbProjetoMarcosCollection();
            Collection<TbProjetoMarcos> tbProjetoMarcosCollectionNew = tbMarcos.getTbProjetoMarcosCollection();
            Collection<TbMarcosServicos> tbMarcosServicosCollectionOld = persistentTbMarcos.getTbMarcosServicosCollection();
            Collection<TbMarcosServicos> tbMarcosServicosCollectionNew = tbMarcos.getTbMarcosServicosCollection();
            List<String> illegalOrphanMessages = null;
            for (TbProjetoMarcos tbProjetoMarcosCollectionOldTbProjetoMarcos : tbProjetoMarcosCollectionOld) {
                if (!tbProjetoMarcosCollectionNew.contains(tbProjetoMarcosCollectionOldTbProjetoMarcos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TbProjetoMarcos " + tbProjetoMarcosCollectionOldTbProjetoMarcos + " since its tbMarcosHand field is not nullable.");
                }
            }
            for (TbMarcosServicos tbMarcosServicosCollectionOldTbMarcosServicos : tbMarcosServicosCollectionOld) {
                if (!tbMarcosServicosCollectionNew.contains(tbMarcosServicosCollectionOldTbMarcosServicos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TbMarcosServicos " + tbMarcosServicosCollectionOldTbMarcosServicos + " since its tbMarcosHand field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<TbProjetoMarcos> attachedTbProjetoMarcosCollectionNew = new ArrayList<TbProjetoMarcos>();
            for (TbProjetoMarcos tbProjetoMarcosCollectionNewTbProjetoMarcosToAttach : tbProjetoMarcosCollectionNew) {
                tbProjetoMarcosCollectionNewTbProjetoMarcosToAttach = em.getReference(tbProjetoMarcosCollectionNewTbProjetoMarcosToAttach.getClass(), tbProjetoMarcosCollectionNewTbProjetoMarcosToAttach.getHand());
                attachedTbProjetoMarcosCollectionNew.add(tbProjetoMarcosCollectionNewTbProjetoMarcosToAttach);
            }
            tbProjetoMarcosCollectionNew = attachedTbProjetoMarcosCollectionNew;
            tbMarcos.setTbProjetoMarcosCollection(tbProjetoMarcosCollectionNew);
            Collection<TbMarcosServicos> attachedTbMarcosServicosCollectionNew = new ArrayList<TbMarcosServicos>();
            for (TbMarcosServicos tbMarcosServicosCollectionNewTbMarcosServicosToAttach : tbMarcosServicosCollectionNew) {
                tbMarcosServicosCollectionNewTbMarcosServicosToAttach = em.getReference(tbMarcosServicosCollectionNewTbMarcosServicosToAttach.getClass(), tbMarcosServicosCollectionNewTbMarcosServicosToAttach.getHand());
                attachedTbMarcosServicosCollectionNew.add(tbMarcosServicosCollectionNewTbMarcosServicosToAttach);
            }
            tbMarcosServicosCollectionNew = attachedTbMarcosServicosCollectionNew;
            tbMarcos.setTbMarcosServicosCollection(tbMarcosServicosCollectionNew);
            tbMarcos = em.merge(tbMarcos);
            for (TbProjetoMarcos tbProjetoMarcosCollectionNewTbProjetoMarcos : tbProjetoMarcosCollectionNew) {
                if (!tbProjetoMarcosCollectionOld.contains(tbProjetoMarcosCollectionNewTbProjetoMarcos)) {
                    TbMarcos oldTbMarcosHandOfTbProjetoMarcosCollectionNewTbProjetoMarcos = tbProjetoMarcosCollectionNewTbProjetoMarcos.getTbMarcosHand();
                    tbProjetoMarcosCollectionNewTbProjetoMarcos.setTbMarcosHand(tbMarcos);
                    tbProjetoMarcosCollectionNewTbProjetoMarcos = em.merge(tbProjetoMarcosCollectionNewTbProjetoMarcos);
                    if (oldTbMarcosHandOfTbProjetoMarcosCollectionNewTbProjetoMarcos != null && !oldTbMarcosHandOfTbProjetoMarcosCollectionNewTbProjetoMarcos.equals(tbMarcos)) {
                        oldTbMarcosHandOfTbProjetoMarcosCollectionNewTbProjetoMarcos.getTbProjetoMarcosCollection().remove(tbProjetoMarcosCollectionNewTbProjetoMarcos);
                        oldTbMarcosHandOfTbProjetoMarcosCollectionNewTbProjetoMarcos = em.merge(oldTbMarcosHandOfTbProjetoMarcosCollectionNewTbProjetoMarcos);
                    }
                }
            }
            for (TbMarcosServicos tbMarcosServicosCollectionNewTbMarcosServicos : tbMarcosServicosCollectionNew) {
                if (!tbMarcosServicosCollectionOld.contains(tbMarcosServicosCollectionNewTbMarcosServicos)) {
                    TbMarcos oldTbMarcosHandOfTbMarcosServicosCollectionNewTbMarcosServicos = tbMarcosServicosCollectionNewTbMarcosServicos.getTbMarcosHand();
                    tbMarcosServicosCollectionNewTbMarcosServicos.setTbMarcosHand(tbMarcos);
                    tbMarcosServicosCollectionNewTbMarcosServicos = em.merge(tbMarcosServicosCollectionNewTbMarcosServicos);
                    if (oldTbMarcosHandOfTbMarcosServicosCollectionNewTbMarcosServicos != null && !oldTbMarcosHandOfTbMarcosServicosCollectionNewTbMarcosServicos.equals(tbMarcos)) {
                        oldTbMarcosHandOfTbMarcosServicosCollectionNewTbMarcosServicos.getTbMarcosServicosCollection().remove(tbMarcosServicosCollectionNewTbMarcosServicos);
                        oldTbMarcosHandOfTbMarcosServicosCollectionNewTbMarcosServicos = em.merge(oldTbMarcosHandOfTbMarcosServicosCollectionNewTbMarcosServicos);
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
                Integer id = tbMarcos.getHand();
                if (findTbMarcos(id) == null) {
                    throw new NonexistentEntityException("The tbMarcos with id " + id + " no longer exists.");
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
            TbMarcos tbMarcos;
            try {
                tbMarcos = em.getReference(TbMarcos.class, id);
                tbMarcos.getHand();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tbMarcos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TbProjetoMarcos> tbProjetoMarcosCollectionOrphanCheck = tbMarcos.getTbProjetoMarcosCollection();
            for (TbProjetoMarcos tbProjetoMarcosCollectionOrphanCheckTbProjetoMarcos : tbProjetoMarcosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbMarcos (" + tbMarcos + ") cannot be destroyed since the TbProjetoMarcos " + tbProjetoMarcosCollectionOrphanCheckTbProjetoMarcos + " in its tbProjetoMarcosCollection field has a non-nullable tbMarcosHand field.");
            }
            Collection<TbMarcosServicos> tbMarcosServicosCollectionOrphanCheck = tbMarcos.getTbMarcosServicosCollection();
            for (TbMarcosServicos tbMarcosServicosCollectionOrphanCheckTbMarcosServicos : tbMarcosServicosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbMarcos (" + tbMarcos + ") cannot be destroyed since the TbMarcosServicos " + tbMarcosServicosCollectionOrphanCheckTbMarcosServicos + " in its tbMarcosServicosCollection field has a non-nullable tbMarcosHand field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tbMarcos);
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

    public List<TbMarcos> findTbMarcosEntities() {
        return findTbMarcosEntities(true, -1, -1);
    }

    public List<TbMarcos> findTbMarcosEntities(int maxResults, int firstResult) {
        return findTbMarcosEntities(false, maxResults, firstResult);
    }

    private List<TbMarcos> findTbMarcosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TbMarcos.class));
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

    public TbMarcos findTbMarcos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbMarcos.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbMarcosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TbMarcos> rt = cq.from(TbMarcos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
