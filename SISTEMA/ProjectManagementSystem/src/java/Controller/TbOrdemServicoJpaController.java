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
import Model.TbOrdemServico;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Model.TbProjetos;
import Model.TbOsServico;
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
public class TbOrdemServicoJpaController implements Serializable {

    public TbOrdemServicoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TbOrdemServico tbOrdemServico) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (tbOrdemServico.getTbOsServicoCollection() == null) {
            tbOrdemServico.setTbOsServicoCollection(new ArrayList<TbOsServico>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbProjetos tbProjetosHand = tbOrdemServico.getTbProjetosHand();
            if (tbProjetosHand != null) {
                tbProjetosHand = em.getReference(tbProjetosHand.getClass(), tbProjetosHand.getHand());
                tbOrdemServico.setTbProjetosHand(tbProjetosHand);
            }
            Collection<TbOsServico> attachedTbOsServicoCollection = new ArrayList<TbOsServico>();
            for (TbOsServico tbOsServicoCollectionTbOsServicoToAttach : tbOrdemServico.getTbOsServicoCollection()) {
                tbOsServicoCollectionTbOsServicoToAttach = em.getReference(tbOsServicoCollectionTbOsServicoToAttach.getClass(), tbOsServicoCollectionTbOsServicoToAttach.getHand());
                attachedTbOsServicoCollection.add(tbOsServicoCollectionTbOsServicoToAttach);
            }
            tbOrdemServico.setTbOsServicoCollection(attachedTbOsServicoCollection);
            em.persist(tbOrdemServico);
            if (tbProjetosHand != null) {
                tbProjetosHand.getTbOrdemServicoCollection().add(tbOrdemServico);
                tbProjetosHand = em.merge(tbProjetosHand);
            }
            for (TbOsServico tbOsServicoCollectionTbOsServico : tbOrdemServico.getTbOsServicoCollection()) {
                TbOrdemServico oldTbOrdemServicoHandOfTbOsServicoCollectionTbOsServico = tbOsServicoCollectionTbOsServico.getTbOrdemServicoHand();
                tbOsServicoCollectionTbOsServico.setTbOrdemServicoHand(tbOrdemServico);
                tbOsServicoCollectionTbOsServico = em.merge(tbOsServicoCollectionTbOsServico);
                if (oldTbOrdemServicoHandOfTbOsServicoCollectionTbOsServico != null) {
                    oldTbOrdemServicoHandOfTbOsServicoCollectionTbOsServico.getTbOsServicoCollection().remove(tbOsServicoCollectionTbOsServico);
                    oldTbOrdemServicoHandOfTbOsServicoCollectionTbOsServico = em.merge(oldTbOrdemServicoHandOfTbOsServicoCollectionTbOsServico);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTbOrdemServico(tbOrdemServico.getHand()) != null) {
                throw new PreexistingEntityException("TbOrdemServico " + tbOrdemServico + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TbOrdemServico tbOrdemServico) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbOrdemServico persistentTbOrdemServico = em.find(TbOrdemServico.class, tbOrdemServico.getHand());
            TbProjetos tbProjetosHandOld = persistentTbOrdemServico.getTbProjetosHand();
            TbProjetos tbProjetosHandNew = tbOrdemServico.getTbProjetosHand();
            Collection<TbOsServico> tbOsServicoCollectionOld = persistentTbOrdemServico.getTbOsServicoCollection();
            Collection<TbOsServico> tbOsServicoCollectionNew = tbOrdemServico.getTbOsServicoCollection();
            List<String> illegalOrphanMessages = null;
            for (TbOsServico tbOsServicoCollectionOldTbOsServico : tbOsServicoCollectionOld) {
                if (!tbOsServicoCollectionNew.contains(tbOsServicoCollectionOldTbOsServico)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TbOsServico " + tbOsServicoCollectionOldTbOsServico + " since its tbOrdemServicoHand field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tbProjetosHandNew != null) {
                tbProjetosHandNew = em.getReference(tbProjetosHandNew.getClass(), tbProjetosHandNew.getHand());
                tbOrdemServico.setTbProjetosHand(tbProjetosHandNew);
            }
            Collection<TbOsServico> attachedTbOsServicoCollectionNew = new ArrayList<TbOsServico>();
            for (TbOsServico tbOsServicoCollectionNewTbOsServicoToAttach : tbOsServicoCollectionNew) {
                tbOsServicoCollectionNewTbOsServicoToAttach = em.getReference(tbOsServicoCollectionNewTbOsServicoToAttach.getClass(), tbOsServicoCollectionNewTbOsServicoToAttach.getHand());
                attachedTbOsServicoCollectionNew.add(tbOsServicoCollectionNewTbOsServicoToAttach);
            }
            tbOsServicoCollectionNew = attachedTbOsServicoCollectionNew;
            tbOrdemServico.setTbOsServicoCollection(tbOsServicoCollectionNew);
            tbOrdemServico = em.merge(tbOrdemServico);
            if (tbProjetosHandOld != null && !tbProjetosHandOld.equals(tbProjetosHandNew)) {
                tbProjetosHandOld.getTbOrdemServicoCollection().remove(tbOrdemServico);
                tbProjetosHandOld = em.merge(tbProjetosHandOld);
            }
            if (tbProjetosHandNew != null && !tbProjetosHandNew.equals(tbProjetosHandOld)) {
                tbProjetosHandNew.getTbOrdemServicoCollection().add(tbOrdemServico);
                tbProjetosHandNew = em.merge(tbProjetosHandNew);
            }
            for (TbOsServico tbOsServicoCollectionNewTbOsServico : tbOsServicoCollectionNew) {
                if (!tbOsServicoCollectionOld.contains(tbOsServicoCollectionNewTbOsServico)) {
                    TbOrdemServico oldTbOrdemServicoHandOfTbOsServicoCollectionNewTbOsServico = tbOsServicoCollectionNewTbOsServico.getTbOrdemServicoHand();
                    tbOsServicoCollectionNewTbOsServico.setTbOrdemServicoHand(tbOrdemServico);
                    tbOsServicoCollectionNewTbOsServico = em.merge(tbOsServicoCollectionNewTbOsServico);
                    if (oldTbOrdemServicoHandOfTbOsServicoCollectionNewTbOsServico != null && !oldTbOrdemServicoHandOfTbOsServicoCollectionNewTbOsServico.equals(tbOrdemServico)) {
                        oldTbOrdemServicoHandOfTbOsServicoCollectionNewTbOsServico.getTbOsServicoCollection().remove(tbOsServicoCollectionNewTbOsServico);
                        oldTbOrdemServicoHandOfTbOsServicoCollectionNewTbOsServico = em.merge(oldTbOrdemServicoHandOfTbOsServicoCollectionNewTbOsServico);
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
                Integer id = tbOrdemServico.getHand();
                if (findTbOrdemServico(id) == null) {
                    throw new NonexistentEntityException("The tbOrdemServico with id " + id + " no longer exists.");
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
            TbOrdemServico tbOrdemServico;
            try {
                tbOrdemServico = em.getReference(TbOrdemServico.class, id);
                tbOrdemServico.getHand();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tbOrdemServico with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TbOsServico> tbOsServicoCollectionOrphanCheck = tbOrdemServico.getTbOsServicoCollection();
            for (TbOsServico tbOsServicoCollectionOrphanCheckTbOsServico : tbOsServicoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbOrdemServico (" + tbOrdemServico + ") cannot be destroyed since the TbOsServico " + tbOsServicoCollectionOrphanCheckTbOsServico + " in its tbOsServicoCollection field has a non-nullable tbOrdemServicoHand field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            TbProjetos tbProjetosHand = tbOrdemServico.getTbProjetosHand();
            if (tbProjetosHand != null) {
                tbProjetosHand.getTbOrdemServicoCollection().remove(tbOrdemServico);
                tbProjetosHand = em.merge(tbProjetosHand);
            }
            em.remove(tbOrdemServico);
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

    public List<TbOrdemServico> findTbOrdemServicoEntities() {
        return findTbOrdemServicoEntities(true, -1, -1);
    }

    public List<TbOrdemServico> findTbOrdemServicoEntities(int maxResults, int firstResult) {
        return findTbOrdemServicoEntities(false, maxResults, firstResult);
    }

    private List<TbOrdemServico> findTbOrdemServicoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TbOrdemServico.class));
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

    public TbOrdemServico findTbOrdemServico(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbOrdemServico.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbOrdemServicoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TbOrdemServico> rt = cq.from(TbOrdemServico.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
