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
import Model.TbMarcos;
import Model.TbMarcosServicos;
import Model.TbServicos;
import Model.TbMaterialMarcoSvc;
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
public class TbMarcosServicosJpaController implements Serializable {

    public TbMarcosServicosJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TbMarcosServicos tbMarcosServicos) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (tbMarcosServicos.getTbMaterialMarcoSvcCollection() == null) {
            tbMarcosServicos.setTbMaterialMarcoSvcCollection(new ArrayList<TbMaterialMarcoSvc>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbMarcos tbMarcosHand = tbMarcosServicos.getTbMarcosHand();
            if (tbMarcosHand != null) {
                tbMarcosHand = em.getReference(tbMarcosHand.getClass(), tbMarcosHand.getHand());
                tbMarcosServicos.setTbMarcosHand(tbMarcosHand);
            }
            TbServicos tbServicosHand = tbMarcosServicos.getTbServicosHand();
            if (tbServicosHand != null) {
                tbServicosHand = em.getReference(tbServicosHand.getClass(), tbServicosHand.getHand());
                tbMarcosServicos.setTbServicosHand(tbServicosHand);
            }
            Collection<TbMaterialMarcoSvc> attachedTbMaterialMarcoSvcCollection = new ArrayList<TbMaterialMarcoSvc>();
            for (TbMaterialMarcoSvc tbMaterialMarcoSvcCollectionTbMaterialMarcoSvcToAttach : tbMarcosServicos.getTbMaterialMarcoSvcCollection()) {
                tbMaterialMarcoSvcCollectionTbMaterialMarcoSvcToAttach = em.getReference(tbMaterialMarcoSvcCollectionTbMaterialMarcoSvcToAttach.getClass(), tbMaterialMarcoSvcCollectionTbMaterialMarcoSvcToAttach.getHand());
                attachedTbMaterialMarcoSvcCollection.add(tbMaterialMarcoSvcCollectionTbMaterialMarcoSvcToAttach);
            }
            tbMarcosServicos.setTbMaterialMarcoSvcCollection(attachedTbMaterialMarcoSvcCollection);
            em.persist(tbMarcosServicos);
            if (tbMarcosHand != null) {
                tbMarcosHand.getTbMarcosServicosCollection().add(tbMarcosServicos);
                tbMarcosHand = em.merge(tbMarcosHand);
            }
            if (tbServicosHand != null) {
                tbServicosHand.getTbMarcosServicosCollection().add(tbMarcosServicos);
                tbServicosHand = em.merge(tbServicosHand);
            }
            for (TbMaterialMarcoSvc tbMaterialMarcoSvcCollectionTbMaterialMarcoSvc : tbMarcosServicos.getTbMaterialMarcoSvcCollection()) {
                TbMarcosServicos oldTbMarcosServicosHandOfTbMaterialMarcoSvcCollectionTbMaterialMarcoSvc = tbMaterialMarcoSvcCollectionTbMaterialMarcoSvc.getTbMarcosServicosHand();
                tbMaterialMarcoSvcCollectionTbMaterialMarcoSvc.setTbMarcosServicosHand(tbMarcosServicos);
                tbMaterialMarcoSvcCollectionTbMaterialMarcoSvc = em.merge(tbMaterialMarcoSvcCollectionTbMaterialMarcoSvc);
                if (oldTbMarcosServicosHandOfTbMaterialMarcoSvcCollectionTbMaterialMarcoSvc != null) {
                    oldTbMarcosServicosHandOfTbMaterialMarcoSvcCollectionTbMaterialMarcoSvc.getTbMaterialMarcoSvcCollection().remove(tbMaterialMarcoSvcCollectionTbMaterialMarcoSvc);
                    oldTbMarcosServicosHandOfTbMaterialMarcoSvcCollectionTbMaterialMarcoSvc = em.merge(oldTbMarcosServicosHandOfTbMaterialMarcoSvcCollectionTbMaterialMarcoSvc);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTbMarcosServicos(tbMarcosServicos.getHand()) != null) {
                throw new PreexistingEntityException("TbMarcosServicos " + tbMarcosServicos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TbMarcosServicos tbMarcosServicos) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbMarcosServicos persistentTbMarcosServicos = em.find(TbMarcosServicos.class, tbMarcosServicos.getHand());
            TbMarcos tbMarcosHandOld = persistentTbMarcosServicos.getTbMarcosHand();
            TbMarcos tbMarcosHandNew = tbMarcosServicos.getTbMarcosHand();
            TbServicos tbServicosHandOld = persistentTbMarcosServicos.getTbServicosHand();
            TbServicos tbServicosHandNew = tbMarcosServicos.getTbServicosHand();
            Collection<TbMaterialMarcoSvc> tbMaterialMarcoSvcCollectionOld = persistentTbMarcosServicos.getTbMaterialMarcoSvcCollection();
            Collection<TbMaterialMarcoSvc> tbMaterialMarcoSvcCollectionNew = tbMarcosServicos.getTbMaterialMarcoSvcCollection();
            List<String> illegalOrphanMessages = null;
            for (TbMaterialMarcoSvc tbMaterialMarcoSvcCollectionOldTbMaterialMarcoSvc : tbMaterialMarcoSvcCollectionOld) {
                if (!tbMaterialMarcoSvcCollectionNew.contains(tbMaterialMarcoSvcCollectionOldTbMaterialMarcoSvc)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TbMaterialMarcoSvc " + tbMaterialMarcoSvcCollectionOldTbMaterialMarcoSvc + " since its tbMarcosServicosHand field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tbMarcosHandNew != null) {
                tbMarcosHandNew = em.getReference(tbMarcosHandNew.getClass(), tbMarcosHandNew.getHand());
                tbMarcosServicos.setTbMarcosHand(tbMarcosHandNew);
            }
            if (tbServicosHandNew != null) {
                tbServicosHandNew = em.getReference(tbServicosHandNew.getClass(), tbServicosHandNew.getHand());
                tbMarcosServicos.setTbServicosHand(tbServicosHandNew);
            }
            Collection<TbMaterialMarcoSvc> attachedTbMaterialMarcoSvcCollectionNew = new ArrayList<TbMaterialMarcoSvc>();
            for (TbMaterialMarcoSvc tbMaterialMarcoSvcCollectionNewTbMaterialMarcoSvcToAttach : tbMaterialMarcoSvcCollectionNew) {
                tbMaterialMarcoSvcCollectionNewTbMaterialMarcoSvcToAttach = em.getReference(tbMaterialMarcoSvcCollectionNewTbMaterialMarcoSvcToAttach.getClass(), tbMaterialMarcoSvcCollectionNewTbMaterialMarcoSvcToAttach.getHand());
                attachedTbMaterialMarcoSvcCollectionNew.add(tbMaterialMarcoSvcCollectionNewTbMaterialMarcoSvcToAttach);
            }
            tbMaterialMarcoSvcCollectionNew = attachedTbMaterialMarcoSvcCollectionNew;
            tbMarcosServicos.setTbMaterialMarcoSvcCollection(tbMaterialMarcoSvcCollectionNew);
            tbMarcosServicos = em.merge(tbMarcosServicos);
            if (tbMarcosHandOld != null && !tbMarcosHandOld.equals(tbMarcosHandNew)) {
                tbMarcosHandOld.getTbMarcosServicosCollection().remove(tbMarcosServicos);
                tbMarcosHandOld = em.merge(tbMarcosHandOld);
            }
            if (tbMarcosHandNew != null && !tbMarcosHandNew.equals(tbMarcosHandOld)) {
                tbMarcosHandNew.getTbMarcosServicosCollection().add(tbMarcosServicos);
                tbMarcosHandNew = em.merge(tbMarcosHandNew);
            }
            if (tbServicosHandOld != null && !tbServicosHandOld.equals(tbServicosHandNew)) {
                tbServicosHandOld.getTbMarcosServicosCollection().remove(tbMarcosServicos);
                tbServicosHandOld = em.merge(tbServicosHandOld);
            }
            if (tbServicosHandNew != null && !tbServicosHandNew.equals(tbServicosHandOld)) {
                tbServicosHandNew.getTbMarcosServicosCollection().add(tbMarcosServicos);
                tbServicosHandNew = em.merge(tbServicosHandNew);
            }
            for (TbMaterialMarcoSvc tbMaterialMarcoSvcCollectionNewTbMaterialMarcoSvc : tbMaterialMarcoSvcCollectionNew) {
                if (!tbMaterialMarcoSvcCollectionOld.contains(tbMaterialMarcoSvcCollectionNewTbMaterialMarcoSvc)) {
                    TbMarcosServicos oldTbMarcosServicosHandOfTbMaterialMarcoSvcCollectionNewTbMaterialMarcoSvc = tbMaterialMarcoSvcCollectionNewTbMaterialMarcoSvc.getTbMarcosServicosHand();
                    tbMaterialMarcoSvcCollectionNewTbMaterialMarcoSvc.setTbMarcosServicosHand(tbMarcosServicos);
                    tbMaterialMarcoSvcCollectionNewTbMaterialMarcoSvc = em.merge(tbMaterialMarcoSvcCollectionNewTbMaterialMarcoSvc);
                    if (oldTbMarcosServicosHandOfTbMaterialMarcoSvcCollectionNewTbMaterialMarcoSvc != null && !oldTbMarcosServicosHandOfTbMaterialMarcoSvcCollectionNewTbMaterialMarcoSvc.equals(tbMarcosServicos)) {
                        oldTbMarcosServicosHandOfTbMaterialMarcoSvcCollectionNewTbMaterialMarcoSvc.getTbMaterialMarcoSvcCollection().remove(tbMaterialMarcoSvcCollectionNewTbMaterialMarcoSvc);
                        oldTbMarcosServicosHandOfTbMaterialMarcoSvcCollectionNewTbMaterialMarcoSvc = em.merge(oldTbMarcosServicosHandOfTbMaterialMarcoSvcCollectionNewTbMaterialMarcoSvc);
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
                Integer id = tbMarcosServicos.getHand();
                if (findTbMarcosServicos(id) == null) {
                    throw new NonexistentEntityException("The tbMarcosServicos with id " + id + " no longer exists.");
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
            TbMarcosServicos tbMarcosServicos;
            try {
                tbMarcosServicos = em.getReference(TbMarcosServicos.class, id);
                tbMarcosServicos.getHand();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tbMarcosServicos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TbMaterialMarcoSvc> tbMaterialMarcoSvcCollectionOrphanCheck = tbMarcosServicos.getTbMaterialMarcoSvcCollection();
            for (TbMaterialMarcoSvc tbMaterialMarcoSvcCollectionOrphanCheckTbMaterialMarcoSvc : tbMaterialMarcoSvcCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbMarcosServicos (" + tbMarcosServicos + ") cannot be destroyed since the TbMaterialMarcoSvc " + tbMaterialMarcoSvcCollectionOrphanCheckTbMaterialMarcoSvc + " in its tbMaterialMarcoSvcCollection field has a non-nullable tbMarcosServicosHand field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            TbMarcos tbMarcosHand = tbMarcosServicos.getTbMarcosHand();
            if (tbMarcosHand != null) {
                tbMarcosHand.getTbMarcosServicosCollection().remove(tbMarcosServicos);
                tbMarcosHand = em.merge(tbMarcosHand);
            }
            TbServicos tbServicosHand = tbMarcosServicos.getTbServicosHand();
            if (tbServicosHand != null) {
                tbServicosHand.getTbMarcosServicosCollection().remove(tbMarcosServicos);
                tbServicosHand = em.merge(tbServicosHand);
            }
            em.remove(tbMarcosServicos);
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

    public List<TbMarcosServicos> findTbMarcosServicosEntities() {
        return findTbMarcosServicosEntities(true, -1, -1);
    }

    public List<TbMarcosServicos> findTbMarcosServicosEntities(int maxResults, int firstResult) {
        return findTbMarcosServicosEntities(false, maxResults, firstResult);
    }

    private List<TbMarcosServicos> findTbMarcosServicosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TbMarcosServicos.class));
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

    public TbMarcosServicos findTbMarcosServicos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbMarcosServicos.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbMarcosServicosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TbMarcosServicos> rt = cq.from(TbMarcosServicos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
