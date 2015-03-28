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
import Model.TbFuncionarioTurnoSemana;
import Model.TbTurnos;
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
public class TbTurnosJpaController implements Serializable {

    public TbTurnosJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TbTurnos tbTurnos) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (tbTurnos.getTbFuncionarioTurnoSemanaCollection() == null) {
            tbTurnos.setTbFuncionarioTurnoSemanaCollection(new ArrayList<TbFuncionarioTurnoSemana>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<TbFuncionarioTurnoSemana> attachedTbFuncionarioTurnoSemanaCollection = new ArrayList<TbFuncionarioTurnoSemana>();
            for (TbFuncionarioTurnoSemana tbFuncionarioTurnoSemanaCollectionTbFuncionarioTurnoSemanaToAttach : tbTurnos.getTbFuncionarioTurnoSemanaCollection()) {
                tbFuncionarioTurnoSemanaCollectionTbFuncionarioTurnoSemanaToAttach = em.getReference(tbFuncionarioTurnoSemanaCollectionTbFuncionarioTurnoSemanaToAttach.getClass(), tbFuncionarioTurnoSemanaCollectionTbFuncionarioTurnoSemanaToAttach.getHand());
                attachedTbFuncionarioTurnoSemanaCollection.add(tbFuncionarioTurnoSemanaCollectionTbFuncionarioTurnoSemanaToAttach);
            }
            tbTurnos.setTbFuncionarioTurnoSemanaCollection(attachedTbFuncionarioTurnoSemanaCollection);
            em.persist(tbTurnos);
            for (TbFuncionarioTurnoSemana tbFuncionarioTurnoSemanaCollectionTbFuncionarioTurnoSemana : tbTurnos.getTbFuncionarioTurnoSemanaCollection()) {
                TbTurnos oldTbTurnosHandOfTbFuncionarioTurnoSemanaCollectionTbFuncionarioTurnoSemana = tbFuncionarioTurnoSemanaCollectionTbFuncionarioTurnoSemana.getTbTurnosHand();
                tbFuncionarioTurnoSemanaCollectionTbFuncionarioTurnoSemana.setTbTurnosHand(tbTurnos);
                tbFuncionarioTurnoSemanaCollectionTbFuncionarioTurnoSemana = em.merge(tbFuncionarioTurnoSemanaCollectionTbFuncionarioTurnoSemana);
                if (oldTbTurnosHandOfTbFuncionarioTurnoSemanaCollectionTbFuncionarioTurnoSemana != null) {
                    oldTbTurnosHandOfTbFuncionarioTurnoSemanaCollectionTbFuncionarioTurnoSemana.getTbFuncionarioTurnoSemanaCollection().remove(tbFuncionarioTurnoSemanaCollectionTbFuncionarioTurnoSemana);
                    oldTbTurnosHandOfTbFuncionarioTurnoSemanaCollectionTbFuncionarioTurnoSemana = em.merge(oldTbTurnosHandOfTbFuncionarioTurnoSemanaCollectionTbFuncionarioTurnoSemana);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTbTurnos(tbTurnos.getHand()) != null) {
                throw new PreexistingEntityException("TbTurnos " + tbTurnos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TbTurnos tbTurnos) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbTurnos persistentTbTurnos = em.find(TbTurnos.class, tbTurnos.getHand());
            Collection<TbFuncionarioTurnoSemana> tbFuncionarioTurnoSemanaCollectionOld = persistentTbTurnos.getTbFuncionarioTurnoSemanaCollection();
            Collection<TbFuncionarioTurnoSemana> tbFuncionarioTurnoSemanaCollectionNew = tbTurnos.getTbFuncionarioTurnoSemanaCollection();
            List<String> illegalOrphanMessages = null;
            for (TbFuncionarioTurnoSemana tbFuncionarioTurnoSemanaCollectionOldTbFuncionarioTurnoSemana : tbFuncionarioTurnoSemanaCollectionOld) {
                if (!tbFuncionarioTurnoSemanaCollectionNew.contains(tbFuncionarioTurnoSemanaCollectionOldTbFuncionarioTurnoSemana)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TbFuncionarioTurnoSemana " + tbFuncionarioTurnoSemanaCollectionOldTbFuncionarioTurnoSemana + " since its tbTurnosHand field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<TbFuncionarioTurnoSemana> attachedTbFuncionarioTurnoSemanaCollectionNew = new ArrayList<TbFuncionarioTurnoSemana>();
            for (TbFuncionarioTurnoSemana tbFuncionarioTurnoSemanaCollectionNewTbFuncionarioTurnoSemanaToAttach : tbFuncionarioTurnoSemanaCollectionNew) {
                tbFuncionarioTurnoSemanaCollectionNewTbFuncionarioTurnoSemanaToAttach = em.getReference(tbFuncionarioTurnoSemanaCollectionNewTbFuncionarioTurnoSemanaToAttach.getClass(), tbFuncionarioTurnoSemanaCollectionNewTbFuncionarioTurnoSemanaToAttach.getHand());
                attachedTbFuncionarioTurnoSemanaCollectionNew.add(tbFuncionarioTurnoSemanaCollectionNewTbFuncionarioTurnoSemanaToAttach);
            }
            tbFuncionarioTurnoSemanaCollectionNew = attachedTbFuncionarioTurnoSemanaCollectionNew;
            tbTurnos.setTbFuncionarioTurnoSemanaCollection(tbFuncionarioTurnoSemanaCollectionNew);
            tbTurnos = em.merge(tbTurnos);
            for (TbFuncionarioTurnoSemana tbFuncionarioTurnoSemanaCollectionNewTbFuncionarioTurnoSemana : tbFuncionarioTurnoSemanaCollectionNew) {
                if (!tbFuncionarioTurnoSemanaCollectionOld.contains(tbFuncionarioTurnoSemanaCollectionNewTbFuncionarioTurnoSemana)) {
                    TbTurnos oldTbTurnosHandOfTbFuncionarioTurnoSemanaCollectionNewTbFuncionarioTurnoSemana = tbFuncionarioTurnoSemanaCollectionNewTbFuncionarioTurnoSemana.getTbTurnosHand();
                    tbFuncionarioTurnoSemanaCollectionNewTbFuncionarioTurnoSemana.setTbTurnosHand(tbTurnos);
                    tbFuncionarioTurnoSemanaCollectionNewTbFuncionarioTurnoSemana = em.merge(tbFuncionarioTurnoSemanaCollectionNewTbFuncionarioTurnoSemana);
                    if (oldTbTurnosHandOfTbFuncionarioTurnoSemanaCollectionNewTbFuncionarioTurnoSemana != null && !oldTbTurnosHandOfTbFuncionarioTurnoSemanaCollectionNewTbFuncionarioTurnoSemana.equals(tbTurnos)) {
                        oldTbTurnosHandOfTbFuncionarioTurnoSemanaCollectionNewTbFuncionarioTurnoSemana.getTbFuncionarioTurnoSemanaCollection().remove(tbFuncionarioTurnoSemanaCollectionNewTbFuncionarioTurnoSemana);
                        oldTbTurnosHandOfTbFuncionarioTurnoSemanaCollectionNewTbFuncionarioTurnoSemana = em.merge(oldTbTurnosHandOfTbFuncionarioTurnoSemanaCollectionNewTbFuncionarioTurnoSemana);
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
                Integer id = tbTurnos.getHand();
                if (findTbTurnos(id) == null) {
                    throw new NonexistentEntityException("The tbTurnos with id " + id + " no longer exists.");
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
            TbTurnos tbTurnos;
            try {
                tbTurnos = em.getReference(TbTurnos.class, id);
                tbTurnos.getHand();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tbTurnos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TbFuncionarioTurnoSemana> tbFuncionarioTurnoSemanaCollectionOrphanCheck = tbTurnos.getTbFuncionarioTurnoSemanaCollection();
            for (TbFuncionarioTurnoSemana tbFuncionarioTurnoSemanaCollectionOrphanCheckTbFuncionarioTurnoSemana : tbFuncionarioTurnoSemanaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbTurnos (" + tbTurnos + ") cannot be destroyed since the TbFuncionarioTurnoSemana " + tbFuncionarioTurnoSemanaCollectionOrphanCheckTbFuncionarioTurnoSemana + " in its tbFuncionarioTurnoSemanaCollection field has a non-nullable tbTurnosHand field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tbTurnos);
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

    public List<TbTurnos> findTbTurnosEntities() {
        return findTbTurnosEntities(true, -1, -1);
    }

    public List<TbTurnos> findTbTurnosEntities(int maxResults, int firstResult) {
        return findTbTurnosEntities(false, maxResults, firstResult);
    }

    private List<TbTurnos> findTbTurnosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TbTurnos.class));
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

    public TbTurnos findTbTurnos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbTurnos.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbTurnosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TbTurnos> rt = cq.from(TbTurnos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
