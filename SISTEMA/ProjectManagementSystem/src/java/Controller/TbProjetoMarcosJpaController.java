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
import Model.TbProjetoMarcos;
import Model.TbProjetos;
import Model.TbProjetosServicos;
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
public class TbProjetoMarcosJpaController implements Serializable {

    public TbProjetoMarcosJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TbProjetoMarcos tbProjetoMarcos) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (tbProjetoMarcos.getTbProjetosServicosCollection() == null) {
            tbProjetoMarcos.setTbProjetosServicosCollection(new ArrayList<TbProjetosServicos>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbMarcos tbMarcosHand = tbProjetoMarcos.getTbMarcosHand();
            if (tbMarcosHand != null) {
                tbMarcosHand = em.getReference(tbMarcosHand.getClass(), tbMarcosHand.getHand());
                tbProjetoMarcos.setTbMarcosHand(tbMarcosHand);
            }
            TbProjetos tbProjetoHand = tbProjetoMarcos.getTbProjetoHand();
            if (tbProjetoHand != null) {
                tbProjetoHand = em.getReference(tbProjetoHand.getClass(), tbProjetoHand.getHand());
                tbProjetoMarcos.setTbProjetoHand(tbProjetoHand);
            }
            Collection<TbProjetosServicos> attachedTbProjetosServicosCollection = new ArrayList<TbProjetosServicos>();
            for (TbProjetosServicos tbProjetosServicosCollectionTbProjetosServicosToAttach : tbProjetoMarcos.getTbProjetosServicosCollection()) {
                tbProjetosServicosCollectionTbProjetosServicosToAttach = em.getReference(tbProjetosServicosCollectionTbProjetosServicosToAttach.getClass(), tbProjetosServicosCollectionTbProjetosServicosToAttach.getHand());
                attachedTbProjetosServicosCollection.add(tbProjetosServicosCollectionTbProjetosServicosToAttach);
            }
            tbProjetoMarcos.setTbProjetosServicosCollection(attachedTbProjetosServicosCollection);
            em.persist(tbProjetoMarcos);
            if (tbMarcosHand != null) {
                tbMarcosHand.getTbProjetoMarcosCollection().add(tbProjetoMarcos);
                tbMarcosHand = em.merge(tbMarcosHand);
            }
            if (tbProjetoHand != null) {
                tbProjetoHand.getTbProjetoMarcosCollection().add(tbProjetoMarcos);
                tbProjetoHand = em.merge(tbProjetoHand);
            }
            for (TbProjetosServicos tbProjetosServicosCollectionTbProjetosServicos : tbProjetoMarcos.getTbProjetosServicosCollection()) {
                TbProjetoMarcos oldTbProjetoMarcosHandOfTbProjetosServicosCollectionTbProjetosServicos = tbProjetosServicosCollectionTbProjetosServicos.getTbProjetoMarcosHand();
                tbProjetosServicosCollectionTbProjetosServicos.setTbProjetoMarcosHand(tbProjetoMarcos);
                tbProjetosServicosCollectionTbProjetosServicos = em.merge(tbProjetosServicosCollectionTbProjetosServicos);
                if (oldTbProjetoMarcosHandOfTbProjetosServicosCollectionTbProjetosServicos != null) {
                    oldTbProjetoMarcosHandOfTbProjetosServicosCollectionTbProjetosServicos.getTbProjetosServicosCollection().remove(tbProjetosServicosCollectionTbProjetosServicos);
                    oldTbProjetoMarcosHandOfTbProjetosServicosCollectionTbProjetosServicos = em.merge(oldTbProjetoMarcosHandOfTbProjetosServicosCollectionTbProjetosServicos);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTbProjetoMarcos(tbProjetoMarcos.getHand()) != null) {
                throw new PreexistingEntityException("TbProjetoMarcos " + tbProjetoMarcos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TbProjetoMarcos tbProjetoMarcos) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbProjetoMarcos persistentTbProjetoMarcos = em.find(TbProjetoMarcos.class, tbProjetoMarcos.getHand());
            TbMarcos tbMarcosHandOld = persistentTbProjetoMarcos.getTbMarcosHand();
            TbMarcos tbMarcosHandNew = tbProjetoMarcos.getTbMarcosHand();
            TbProjetos tbProjetoHandOld = persistentTbProjetoMarcos.getTbProjetoHand();
            TbProjetos tbProjetoHandNew = tbProjetoMarcos.getTbProjetoHand();
            Collection<TbProjetosServicos> tbProjetosServicosCollectionOld = persistentTbProjetoMarcos.getTbProjetosServicosCollection();
            Collection<TbProjetosServicos> tbProjetosServicosCollectionNew = tbProjetoMarcos.getTbProjetosServicosCollection();
            List<String> illegalOrphanMessages = null;
            for (TbProjetosServicos tbProjetosServicosCollectionOldTbProjetosServicos : tbProjetosServicosCollectionOld) {
                if (!tbProjetosServicosCollectionNew.contains(tbProjetosServicosCollectionOldTbProjetosServicos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TbProjetosServicos " + tbProjetosServicosCollectionOldTbProjetosServicos + " since its tbProjetoMarcosHand field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tbMarcosHandNew != null) {
                tbMarcosHandNew = em.getReference(tbMarcosHandNew.getClass(), tbMarcosHandNew.getHand());
                tbProjetoMarcos.setTbMarcosHand(tbMarcosHandNew);
            }
            if (tbProjetoHandNew != null) {
                tbProjetoHandNew = em.getReference(tbProjetoHandNew.getClass(), tbProjetoHandNew.getHand());
                tbProjetoMarcos.setTbProjetoHand(tbProjetoHandNew);
            }
            Collection<TbProjetosServicos> attachedTbProjetosServicosCollectionNew = new ArrayList<TbProjetosServicos>();
            for (TbProjetosServicos tbProjetosServicosCollectionNewTbProjetosServicosToAttach : tbProjetosServicosCollectionNew) {
                tbProjetosServicosCollectionNewTbProjetosServicosToAttach = em.getReference(tbProjetosServicosCollectionNewTbProjetosServicosToAttach.getClass(), tbProjetosServicosCollectionNewTbProjetosServicosToAttach.getHand());
                attachedTbProjetosServicosCollectionNew.add(tbProjetosServicosCollectionNewTbProjetosServicosToAttach);
            }
            tbProjetosServicosCollectionNew = attachedTbProjetosServicosCollectionNew;
            tbProjetoMarcos.setTbProjetosServicosCollection(tbProjetosServicosCollectionNew);
            tbProjetoMarcos = em.merge(tbProjetoMarcos);
            if (tbMarcosHandOld != null && !tbMarcosHandOld.equals(tbMarcosHandNew)) {
                tbMarcosHandOld.getTbProjetoMarcosCollection().remove(tbProjetoMarcos);
                tbMarcosHandOld = em.merge(tbMarcosHandOld);
            }
            if (tbMarcosHandNew != null && !tbMarcosHandNew.equals(tbMarcosHandOld)) {
                tbMarcosHandNew.getTbProjetoMarcosCollection().add(tbProjetoMarcos);
                tbMarcosHandNew = em.merge(tbMarcosHandNew);
            }
            if (tbProjetoHandOld != null && !tbProjetoHandOld.equals(tbProjetoHandNew)) {
                tbProjetoHandOld.getTbProjetoMarcosCollection().remove(tbProjetoMarcos);
                tbProjetoHandOld = em.merge(tbProjetoHandOld);
            }
            if (tbProjetoHandNew != null && !tbProjetoHandNew.equals(tbProjetoHandOld)) {
                tbProjetoHandNew.getTbProjetoMarcosCollection().add(tbProjetoMarcos);
                tbProjetoHandNew = em.merge(tbProjetoHandNew);
            }
            for (TbProjetosServicos tbProjetosServicosCollectionNewTbProjetosServicos : tbProjetosServicosCollectionNew) {
                if (!tbProjetosServicosCollectionOld.contains(tbProjetosServicosCollectionNewTbProjetosServicos)) {
                    TbProjetoMarcos oldTbProjetoMarcosHandOfTbProjetosServicosCollectionNewTbProjetosServicos = tbProjetosServicosCollectionNewTbProjetosServicos.getTbProjetoMarcosHand();
                    tbProjetosServicosCollectionNewTbProjetosServicos.setTbProjetoMarcosHand(tbProjetoMarcos);
                    tbProjetosServicosCollectionNewTbProjetosServicos = em.merge(tbProjetosServicosCollectionNewTbProjetosServicos);
                    if (oldTbProjetoMarcosHandOfTbProjetosServicosCollectionNewTbProjetosServicos != null && !oldTbProjetoMarcosHandOfTbProjetosServicosCollectionNewTbProjetosServicos.equals(tbProjetoMarcos)) {
                        oldTbProjetoMarcosHandOfTbProjetosServicosCollectionNewTbProjetosServicos.getTbProjetosServicosCollection().remove(tbProjetosServicosCollectionNewTbProjetosServicos);
                        oldTbProjetoMarcosHandOfTbProjetosServicosCollectionNewTbProjetosServicos = em.merge(oldTbProjetoMarcosHandOfTbProjetosServicosCollectionNewTbProjetosServicos);
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
                Integer id = tbProjetoMarcos.getHand();
                if (findTbProjetoMarcos(id) == null) {
                    throw new NonexistentEntityException("The tbProjetoMarcos with id " + id + " no longer exists.");
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
            TbProjetoMarcos tbProjetoMarcos;
            try {
                tbProjetoMarcos = em.getReference(TbProjetoMarcos.class, id);
                tbProjetoMarcos.getHand();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tbProjetoMarcos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TbProjetosServicos> tbProjetosServicosCollectionOrphanCheck = tbProjetoMarcos.getTbProjetosServicosCollection();
            for (TbProjetosServicos tbProjetosServicosCollectionOrphanCheckTbProjetosServicos : tbProjetosServicosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbProjetoMarcos (" + tbProjetoMarcos + ") cannot be destroyed since the TbProjetosServicos " + tbProjetosServicosCollectionOrphanCheckTbProjetosServicos + " in its tbProjetosServicosCollection field has a non-nullable tbProjetoMarcosHand field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            TbMarcos tbMarcosHand = tbProjetoMarcos.getTbMarcosHand();
            if (tbMarcosHand != null) {
                tbMarcosHand.getTbProjetoMarcosCollection().remove(tbProjetoMarcos);
                tbMarcosHand = em.merge(tbMarcosHand);
            }
            TbProjetos tbProjetoHand = tbProjetoMarcos.getTbProjetoHand();
            if (tbProjetoHand != null) {
                tbProjetoHand.getTbProjetoMarcosCollection().remove(tbProjetoMarcos);
                tbProjetoHand = em.merge(tbProjetoHand);
            }
            em.remove(tbProjetoMarcos);
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

    public List<TbProjetoMarcos> findTbProjetoMarcosEntities() {
        return findTbProjetoMarcosEntities(true, -1, -1);
    }

    public List<TbProjetoMarcos> findTbProjetoMarcosEntities(int maxResults, int firstResult) {
        return findTbProjetoMarcosEntities(false, maxResults, firstResult);
    }

    private List<TbProjetoMarcos> findTbProjetoMarcosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TbProjetoMarcos.class));
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

    public TbProjetoMarcos findTbProjetoMarcos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbProjetoMarcos.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbProjetoMarcosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TbProjetoMarcos> rt = cq.from(TbProjetoMarcos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
