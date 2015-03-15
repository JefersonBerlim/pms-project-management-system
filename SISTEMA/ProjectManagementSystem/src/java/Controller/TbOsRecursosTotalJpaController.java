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
import Model.TbOsServico;
import Model.TbProjetosRecursos;
import Model.TbOsFuncionariosTotal;
import Model.TbOsRecursosTotal;
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
public class TbOsRecursosTotalJpaController implements Serializable {

    public TbOsRecursosTotalJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TbOsRecursosTotal tbOsRecursosTotal) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (tbOsRecursosTotal.getTbOsFuncionariosTotalCollection() == null) {
            tbOsRecursosTotal.setTbOsFuncionariosTotalCollection(new ArrayList<TbOsFuncionariosTotal>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbOsServico tbOsServicoHand = tbOsRecursosTotal.getTbOsServicoHand();
            if (tbOsServicoHand != null) {
                tbOsServicoHand = em.getReference(tbOsServicoHand.getClass(), tbOsServicoHand.getHand());
                tbOsRecursosTotal.setTbOsServicoHand(tbOsServicoHand);
            }
            TbProjetosRecursos tbProjetosRecursosHand = tbOsRecursosTotal.getTbProjetosRecursosHand();
            if (tbProjetosRecursosHand != null) {
                tbProjetosRecursosHand = em.getReference(tbProjetosRecursosHand.getClass(), tbProjetosRecursosHand.getHand());
                tbOsRecursosTotal.setTbProjetosRecursosHand(tbProjetosRecursosHand);
            }
            Collection<TbOsFuncionariosTotal> attachedTbOsFuncionariosTotalCollection = new ArrayList<TbOsFuncionariosTotal>();
            for (TbOsFuncionariosTotal tbOsFuncionariosTotalCollectionTbOsFuncionariosTotalToAttach : tbOsRecursosTotal.getTbOsFuncionariosTotalCollection()) {
                tbOsFuncionariosTotalCollectionTbOsFuncionariosTotalToAttach = em.getReference(tbOsFuncionariosTotalCollectionTbOsFuncionariosTotalToAttach.getClass(), tbOsFuncionariosTotalCollectionTbOsFuncionariosTotalToAttach.getHand());
                attachedTbOsFuncionariosTotalCollection.add(tbOsFuncionariosTotalCollectionTbOsFuncionariosTotalToAttach);
            }
            tbOsRecursosTotal.setTbOsFuncionariosTotalCollection(attachedTbOsFuncionariosTotalCollection);
            em.persist(tbOsRecursosTotal);
            if (tbOsServicoHand != null) {
                tbOsServicoHand.getTbOsRecursosTotalCollection().add(tbOsRecursosTotal);
                tbOsServicoHand = em.merge(tbOsServicoHand);
            }
            if (tbProjetosRecursosHand != null) {
                tbProjetosRecursosHand.getTbOsRecursosTotalCollection().add(tbOsRecursosTotal);
                tbProjetosRecursosHand = em.merge(tbProjetosRecursosHand);
            }
            for (TbOsFuncionariosTotal tbOsFuncionariosTotalCollectionTbOsFuncionariosTotal : tbOsRecursosTotal.getTbOsFuncionariosTotalCollection()) {
                TbOsRecursosTotal oldTbOsRecursosTotalHandOfTbOsFuncionariosTotalCollectionTbOsFuncionariosTotal = tbOsFuncionariosTotalCollectionTbOsFuncionariosTotal.getTbOsRecursosTotalHand();
                tbOsFuncionariosTotalCollectionTbOsFuncionariosTotal.setTbOsRecursosTotalHand(tbOsRecursosTotal);
                tbOsFuncionariosTotalCollectionTbOsFuncionariosTotal = em.merge(tbOsFuncionariosTotalCollectionTbOsFuncionariosTotal);
                if (oldTbOsRecursosTotalHandOfTbOsFuncionariosTotalCollectionTbOsFuncionariosTotal != null) {
                    oldTbOsRecursosTotalHandOfTbOsFuncionariosTotalCollectionTbOsFuncionariosTotal.getTbOsFuncionariosTotalCollection().remove(tbOsFuncionariosTotalCollectionTbOsFuncionariosTotal);
                    oldTbOsRecursosTotalHandOfTbOsFuncionariosTotalCollectionTbOsFuncionariosTotal = em.merge(oldTbOsRecursosTotalHandOfTbOsFuncionariosTotalCollectionTbOsFuncionariosTotal);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTbOsRecursosTotal(tbOsRecursosTotal.getHand()) != null) {
                throw new PreexistingEntityException("TbOsRecursosTotal " + tbOsRecursosTotal + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TbOsRecursosTotal tbOsRecursosTotal) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbOsRecursosTotal persistentTbOsRecursosTotal = em.find(TbOsRecursosTotal.class, tbOsRecursosTotal.getHand());
            TbOsServico tbOsServicoHandOld = persistentTbOsRecursosTotal.getTbOsServicoHand();
            TbOsServico tbOsServicoHandNew = tbOsRecursosTotal.getTbOsServicoHand();
            TbProjetosRecursos tbProjetosRecursosHandOld = persistentTbOsRecursosTotal.getTbProjetosRecursosHand();
            TbProjetosRecursos tbProjetosRecursosHandNew = tbOsRecursosTotal.getTbProjetosRecursosHand();
            Collection<TbOsFuncionariosTotal> tbOsFuncionariosTotalCollectionOld = persistentTbOsRecursosTotal.getTbOsFuncionariosTotalCollection();
            Collection<TbOsFuncionariosTotal> tbOsFuncionariosTotalCollectionNew = tbOsRecursosTotal.getTbOsFuncionariosTotalCollection();
            List<String> illegalOrphanMessages = null;
            for (TbOsFuncionariosTotal tbOsFuncionariosTotalCollectionOldTbOsFuncionariosTotal : tbOsFuncionariosTotalCollectionOld) {
                if (!tbOsFuncionariosTotalCollectionNew.contains(tbOsFuncionariosTotalCollectionOldTbOsFuncionariosTotal)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TbOsFuncionariosTotal " + tbOsFuncionariosTotalCollectionOldTbOsFuncionariosTotal + " since its tbOsRecursosTotalHand field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tbOsServicoHandNew != null) {
                tbOsServicoHandNew = em.getReference(tbOsServicoHandNew.getClass(), tbOsServicoHandNew.getHand());
                tbOsRecursosTotal.setTbOsServicoHand(tbOsServicoHandNew);
            }
            if (tbProjetosRecursosHandNew != null) {
                tbProjetosRecursosHandNew = em.getReference(tbProjetosRecursosHandNew.getClass(), tbProjetosRecursosHandNew.getHand());
                tbOsRecursosTotal.setTbProjetosRecursosHand(tbProjetosRecursosHandNew);
            }
            Collection<TbOsFuncionariosTotal> attachedTbOsFuncionariosTotalCollectionNew = new ArrayList<TbOsFuncionariosTotal>();
            for (TbOsFuncionariosTotal tbOsFuncionariosTotalCollectionNewTbOsFuncionariosTotalToAttach : tbOsFuncionariosTotalCollectionNew) {
                tbOsFuncionariosTotalCollectionNewTbOsFuncionariosTotalToAttach = em.getReference(tbOsFuncionariosTotalCollectionNewTbOsFuncionariosTotalToAttach.getClass(), tbOsFuncionariosTotalCollectionNewTbOsFuncionariosTotalToAttach.getHand());
                attachedTbOsFuncionariosTotalCollectionNew.add(tbOsFuncionariosTotalCollectionNewTbOsFuncionariosTotalToAttach);
            }
            tbOsFuncionariosTotalCollectionNew = attachedTbOsFuncionariosTotalCollectionNew;
            tbOsRecursosTotal.setTbOsFuncionariosTotalCollection(tbOsFuncionariosTotalCollectionNew);
            tbOsRecursosTotal = em.merge(tbOsRecursosTotal);
            if (tbOsServicoHandOld != null && !tbOsServicoHandOld.equals(tbOsServicoHandNew)) {
                tbOsServicoHandOld.getTbOsRecursosTotalCollection().remove(tbOsRecursosTotal);
                tbOsServicoHandOld = em.merge(tbOsServicoHandOld);
            }
            if (tbOsServicoHandNew != null && !tbOsServicoHandNew.equals(tbOsServicoHandOld)) {
                tbOsServicoHandNew.getTbOsRecursosTotalCollection().add(tbOsRecursosTotal);
                tbOsServicoHandNew = em.merge(tbOsServicoHandNew);
            }
            if (tbProjetosRecursosHandOld != null && !tbProjetosRecursosHandOld.equals(tbProjetosRecursosHandNew)) {
                tbProjetosRecursosHandOld.getTbOsRecursosTotalCollection().remove(tbOsRecursosTotal);
                tbProjetosRecursosHandOld = em.merge(tbProjetosRecursosHandOld);
            }
            if (tbProjetosRecursosHandNew != null && !tbProjetosRecursosHandNew.equals(tbProjetosRecursosHandOld)) {
                tbProjetosRecursosHandNew.getTbOsRecursosTotalCollection().add(tbOsRecursosTotal);
                tbProjetosRecursosHandNew = em.merge(tbProjetosRecursosHandNew);
            }
            for (TbOsFuncionariosTotal tbOsFuncionariosTotalCollectionNewTbOsFuncionariosTotal : tbOsFuncionariosTotalCollectionNew) {
                if (!tbOsFuncionariosTotalCollectionOld.contains(tbOsFuncionariosTotalCollectionNewTbOsFuncionariosTotal)) {
                    TbOsRecursosTotal oldTbOsRecursosTotalHandOfTbOsFuncionariosTotalCollectionNewTbOsFuncionariosTotal = tbOsFuncionariosTotalCollectionNewTbOsFuncionariosTotal.getTbOsRecursosTotalHand();
                    tbOsFuncionariosTotalCollectionNewTbOsFuncionariosTotal.setTbOsRecursosTotalHand(tbOsRecursosTotal);
                    tbOsFuncionariosTotalCollectionNewTbOsFuncionariosTotal = em.merge(tbOsFuncionariosTotalCollectionNewTbOsFuncionariosTotal);
                    if (oldTbOsRecursosTotalHandOfTbOsFuncionariosTotalCollectionNewTbOsFuncionariosTotal != null && !oldTbOsRecursosTotalHandOfTbOsFuncionariosTotalCollectionNewTbOsFuncionariosTotal.equals(tbOsRecursosTotal)) {
                        oldTbOsRecursosTotalHandOfTbOsFuncionariosTotalCollectionNewTbOsFuncionariosTotal.getTbOsFuncionariosTotalCollection().remove(tbOsFuncionariosTotalCollectionNewTbOsFuncionariosTotal);
                        oldTbOsRecursosTotalHandOfTbOsFuncionariosTotalCollectionNewTbOsFuncionariosTotal = em.merge(oldTbOsRecursosTotalHandOfTbOsFuncionariosTotalCollectionNewTbOsFuncionariosTotal);
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
                Integer id = tbOsRecursosTotal.getHand();
                if (findTbOsRecursosTotal(id) == null) {
                    throw new NonexistentEntityException("The tbOsRecursosTotal with id " + id + " no longer exists.");
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
            TbOsRecursosTotal tbOsRecursosTotal;
            try {
                tbOsRecursosTotal = em.getReference(TbOsRecursosTotal.class, id);
                tbOsRecursosTotal.getHand();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tbOsRecursosTotal with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TbOsFuncionariosTotal> tbOsFuncionariosTotalCollectionOrphanCheck = tbOsRecursosTotal.getTbOsFuncionariosTotalCollection();
            for (TbOsFuncionariosTotal tbOsFuncionariosTotalCollectionOrphanCheckTbOsFuncionariosTotal : tbOsFuncionariosTotalCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbOsRecursosTotal (" + tbOsRecursosTotal + ") cannot be destroyed since the TbOsFuncionariosTotal " + tbOsFuncionariosTotalCollectionOrphanCheckTbOsFuncionariosTotal + " in its tbOsFuncionariosTotalCollection field has a non-nullable tbOsRecursosTotalHand field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            TbOsServico tbOsServicoHand = tbOsRecursosTotal.getTbOsServicoHand();
            if (tbOsServicoHand != null) {
                tbOsServicoHand.getTbOsRecursosTotalCollection().remove(tbOsRecursosTotal);
                tbOsServicoHand = em.merge(tbOsServicoHand);
            }
            TbProjetosRecursos tbProjetosRecursosHand = tbOsRecursosTotal.getTbProjetosRecursosHand();
            if (tbProjetosRecursosHand != null) {
                tbProjetosRecursosHand.getTbOsRecursosTotalCollection().remove(tbOsRecursosTotal);
                tbProjetosRecursosHand = em.merge(tbProjetosRecursosHand);
            }
            em.remove(tbOsRecursosTotal);
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

    public List<TbOsRecursosTotal> findTbOsRecursosTotalEntities() {
        return findTbOsRecursosTotalEntities(true, -1, -1);
    }

    public List<TbOsRecursosTotal> findTbOsRecursosTotalEntities(int maxResults, int firstResult) {
        return findTbOsRecursosTotalEntities(false, maxResults, firstResult);
    }

    private List<TbOsRecursosTotal> findTbOsRecursosTotalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TbOsRecursosTotal.class));
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

    public TbOsRecursosTotal findTbOsRecursosTotal(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbOsRecursosTotal.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbOsRecursosTotalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TbOsRecursosTotal> rt = cq.from(TbOsRecursosTotal.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
