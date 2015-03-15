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
import Model.TbFuncionarios;
import Model.TbProjetosRecursos;
import Model.TbOsFuncionariosTotal;
import Model.TbProjetoFuncionarios;
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
public class TbProjetoFuncionariosJpaController implements Serializable {

    public TbProjetoFuncionariosJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TbProjetoFuncionarios tbProjetoFuncionarios) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (tbProjetoFuncionarios.getTbOsFuncionariosTotalCollection() == null) {
            tbProjetoFuncionarios.setTbOsFuncionariosTotalCollection(new ArrayList<TbOsFuncionariosTotal>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbFuncionarios tbFuncionariosHand = tbProjetoFuncionarios.getTbFuncionariosHand();
            if (tbFuncionariosHand != null) {
                tbFuncionariosHand = em.getReference(tbFuncionariosHand.getClass(), tbFuncionariosHand.getHand());
                tbProjetoFuncionarios.setTbFuncionariosHand(tbFuncionariosHand);
            }
            TbProjetosRecursos tbProjetosRecursosHand = tbProjetoFuncionarios.getTbProjetosRecursosHand();
            if (tbProjetosRecursosHand != null) {
                tbProjetosRecursosHand = em.getReference(tbProjetosRecursosHand.getClass(), tbProjetosRecursosHand.getHand());
                tbProjetoFuncionarios.setTbProjetosRecursosHand(tbProjetosRecursosHand);
            }
            Collection<TbOsFuncionariosTotal> attachedTbOsFuncionariosTotalCollection = new ArrayList<TbOsFuncionariosTotal>();
            for (TbOsFuncionariosTotal tbOsFuncionariosTotalCollectionTbOsFuncionariosTotalToAttach : tbProjetoFuncionarios.getTbOsFuncionariosTotalCollection()) {
                tbOsFuncionariosTotalCollectionTbOsFuncionariosTotalToAttach = em.getReference(tbOsFuncionariosTotalCollectionTbOsFuncionariosTotalToAttach.getClass(), tbOsFuncionariosTotalCollectionTbOsFuncionariosTotalToAttach.getHand());
                attachedTbOsFuncionariosTotalCollection.add(tbOsFuncionariosTotalCollectionTbOsFuncionariosTotalToAttach);
            }
            tbProjetoFuncionarios.setTbOsFuncionariosTotalCollection(attachedTbOsFuncionariosTotalCollection);
            em.persist(tbProjetoFuncionarios);
            if (tbFuncionariosHand != null) {
                tbFuncionariosHand.getTbProjetoFuncionariosCollection().add(tbProjetoFuncionarios);
                tbFuncionariosHand = em.merge(tbFuncionariosHand);
            }
            if (tbProjetosRecursosHand != null) {
                tbProjetosRecursosHand.getTbProjetoFuncionariosCollection().add(tbProjetoFuncionarios);
                tbProjetosRecursosHand = em.merge(tbProjetosRecursosHand);
            }
            for (TbOsFuncionariosTotal tbOsFuncionariosTotalCollectionTbOsFuncionariosTotal : tbProjetoFuncionarios.getTbOsFuncionariosTotalCollection()) {
                TbProjetoFuncionarios oldTbProjetoFuncionariosHandOfTbOsFuncionariosTotalCollectionTbOsFuncionariosTotal = tbOsFuncionariosTotalCollectionTbOsFuncionariosTotal.getTbProjetoFuncionariosHand();
                tbOsFuncionariosTotalCollectionTbOsFuncionariosTotal.setTbProjetoFuncionariosHand(tbProjetoFuncionarios);
                tbOsFuncionariosTotalCollectionTbOsFuncionariosTotal = em.merge(tbOsFuncionariosTotalCollectionTbOsFuncionariosTotal);
                if (oldTbProjetoFuncionariosHandOfTbOsFuncionariosTotalCollectionTbOsFuncionariosTotal != null) {
                    oldTbProjetoFuncionariosHandOfTbOsFuncionariosTotalCollectionTbOsFuncionariosTotal.getTbOsFuncionariosTotalCollection().remove(tbOsFuncionariosTotalCollectionTbOsFuncionariosTotal);
                    oldTbProjetoFuncionariosHandOfTbOsFuncionariosTotalCollectionTbOsFuncionariosTotal = em.merge(oldTbProjetoFuncionariosHandOfTbOsFuncionariosTotalCollectionTbOsFuncionariosTotal);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTbProjetoFuncionarios(tbProjetoFuncionarios.getHand()) != null) {
                throw new PreexistingEntityException("TbProjetoFuncionarios " + tbProjetoFuncionarios + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TbProjetoFuncionarios tbProjetoFuncionarios) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbProjetoFuncionarios persistentTbProjetoFuncionarios = em.find(TbProjetoFuncionarios.class, tbProjetoFuncionarios.getHand());
            TbFuncionarios tbFuncionariosHandOld = persistentTbProjetoFuncionarios.getTbFuncionariosHand();
            TbFuncionarios tbFuncionariosHandNew = tbProjetoFuncionarios.getTbFuncionariosHand();
            TbProjetosRecursos tbProjetosRecursosHandOld = persistentTbProjetoFuncionarios.getTbProjetosRecursosHand();
            TbProjetosRecursos tbProjetosRecursosHandNew = tbProjetoFuncionarios.getTbProjetosRecursosHand();
            Collection<TbOsFuncionariosTotal> tbOsFuncionariosTotalCollectionOld = persistentTbProjetoFuncionarios.getTbOsFuncionariosTotalCollection();
            Collection<TbOsFuncionariosTotal> tbOsFuncionariosTotalCollectionNew = tbProjetoFuncionarios.getTbOsFuncionariosTotalCollection();
            List<String> illegalOrphanMessages = null;
            for (TbOsFuncionariosTotal tbOsFuncionariosTotalCollectionOldTbOsFuncionariosTotal : tbOsFuncionariosTotalCollectionOld) {
                if (!tbOsFuncionariosTotalCollectionNew.contains(tbOsFuncionariosTotalCollectionOldTbOsFuncionariosTotal)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TbOsFuncionariosTotal " + tbOsFuncionariosTotalCollectionOldTbOsFuncionariosTotal + " since its tbProjetoFuncionariosHand field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tbFuncionariosHandNew != null) {
                tbFuncionariosHandNew = em.getReference(tbFuncionariosHandNew.getClass(), tbFuncionariosHandNew.getHand());
                tbProjetoFuncionarios.setTbFuncionariosHand(tbFuncionariosHandNew);
            }
            if (tbProjetosRecursosHandNew != null) {
                tbProjetosRecursosHandNew = em.getReference(tbProjetosRecursosHandNew.getClass(), tbProjetosRecursosHandNew.getHand());
                tbProjetoFuncionarios.setTbProjetosRecursosHand(tbProjetosRecursosHandNew);
            }
            Collection<TbOsFuncionariosTotal> attachedTbOsFuncionariosTotalCollectionNew = new ArrayList<TbOsFuncionariosTotal>();
            for (TbOsFuncionariosTotal tbOsFuncionariosTotalCollectionNewTbOsFuncionariosTotalToAttach : tbOsFuncionariosTotalCollectionNew) {
                tbOsFuncionariosTotalCollectionNewTbOsFuncionariosTotalToAttach = em.getReference(tbOsFuncionariosTotalCollectionNewTbOsFuncionariosTotalToAttach.getClass(), tbOsFuncionariosTotalCollectionNewTbOsFuncionariosTotalToAttach.getHand());
                attachedTbOsFuncionariosTotalCollectionNew.add(tbOsFuncionariosTotalCollectionNewTbOsFuncionariosTotalToAttach);
            }
            tbOsFuncionariosTotalCollectionNew = attachedTbOsFuncionariosTotalCollectionNew;
            tbProjetoFuncionarios.setTbOsFuncionariosTotalCollection(tbOsFuncionariosTotalCollectionNew);
            tbProjetoFuncionarios = em.merge(tbProjetoFuncionarios);
            if (tbFuncionariosHandOld != null && !tbFuncionariosHandOld.equals(tbFuncionariosHandNew)) {
                tbFuncionariosHandOld.getTbProjetoFuncionariosCollection().remove(tbProjetoFuncionarios);
                tbFuncionariosHandOld = em.merge(tbFuncionariosHandOld);
            }
            if (tbFuncionariosHandNew != null && !tbFuncionariosHandNew.equals(tbFuncionariosHandOld)) {
                tbFuncionariosHandNew.getTbProjetoFuncionariosCollection().add(tbProjetoFuncionarios);
                tbFuncionariosHandNew = em.merge(tbFuncionariosHandNew);
            }
            if (tbProjetosRecursosHandOld != null && !tbProjetosRecursosHandOld.equals(tbProjetosRecursosHandNew)) {
                tbProjetosRecursosHandOld.getTbProjetoFuncionariosCollection().remove(tbProjetoFuncionarios);
                tbProjetosRecursosHandOld = em.merge(tbProjetosRecursosHandOld);
            }
            if (tbProjetosRecursosHandNew != null && !tbProjetosRecursosHandNew.equals(tbProjetosRecursosHandOld)) {
                tbProjetosRecursosHandNew.getTbProjetoFuncionariosCollection().add(tbProjetoFuncionarios);
                tbProjetosRecursosHandNew = em.merge(tbProjetosRecursosHandNew);
            }
            for (TbOsFuncionariosTotal tbOsFuncionariosTotalCollectionNewTbOsFuncionariosTotal : tbOsFuncionariosTotalCollectionNew) {
                if (!tbOsFuncionariosTotalCollectionOld.contains(tbOsFuncionariosTotalCollectionNewTbOsFuncionariosTotal)) {
                    TbProjetoFuncionarios oldTbProjetoFuncionariosHandOfTbOsFuncionariosTotalCollectionNewTbOsFuncionariosTotal = tbOsFuncionariosTotalCollectionNewTbOsFuncionariosTotal.getTbProjetoFuncionariosHand();
                    tbOsFuncionariosTotalCollectionNewTbOsFuncionariosTotal.setTbProjetoFuncionariosHand(tbProjetoFuncionarios);
                    tbOsFuncionariosTotalCollectionNewTbOsFuncionariosTotal = em.merge(tbOsFuncionariosTotalCollectionNewTbOsFuncionariosTotal);
                    if (oldTbProjetoFuncionariosHandOfTbOsFuncionariosTotalCollectionNewTbOsFuncionariosTotal != null && !oldTbProjetoFuncionariosHandOfTbOsFuncionariosTotalCollectionNewTbOsFuncionariosTotal.equals(tbProjetoFuncionarios)) {
                        oldTbProjetoFuncionariosHandOfTbOsFuncionariosTotalCollectionNewTbOsFuncionariosTotal.getTbOsFuncionariosTotalCollection().remove(tbOsFuncionariosTotalCollectionNewTbOsFuncionariosTotal);
                        oldTbProjetoFuncionariosHandOfTbOsFuncionariosTotalCollectionNewTbOsFuncionariosTotal = em.merge(oldTbProjetoFuncionariosHandOfTbOsFuncionariosTotalCollectionNewTbOsFuncionariosTotal);
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
                Integer id = tbProjetoFuncionarios.getHand();
                if (findTbProjetoFuncionarios(id) == null) {
                    throw new NonexistentEntityException("The tbProjetoFuncionarios with id " + id + " no longer exists.");
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
            TbProjetoFuncionarios tbProjetoFuncionarios;
            try {
                tbProjetoFuncionarios = em.getReference(TbProjetoFuncionarios.class, id);
                tbProjetoFuncionarios.getHand();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tbProjetoFuncionarios with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TbOsFuncionariosTotal> tbOsFuncionariosTotalCollectionOrphanCheck = tbProjetoFuncionarios.getTbOsFuncionariosTotalCollection();
            for (TbOsFuncionariosTotal tbOsFuncionariosTotalCollectionOrphanCheckTbOsFuncionariosTotal : tbOsFuncionariosTotalCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbProjetoFuncionarios (" + tbProjetoFuncionarios + ") cannot be destroyed since the TbOsFuncionariosTotal " + tbOsFuncionariosTotalCollectionOrphanCheckTbOsFuncionariosTotal + " in its tbOsFuncionariosTotalCollection field has a non-nullable tbProjetoFuncionariosHand field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            TbFuncionarios tbFuncionariosHand = tbProjetoFuncionarios.getTbFuncionariosHand();
            if (tbFuncionariosHand != null) {
                tbFuncionariosHand.getTbProjetoFuncionariosCollection().remove(tbProjetoFuncionarios);
                tbFuncionariosHand = em.merge(tbFuncionariosHand);
            }
            TbProjetosRecursos tbProjetosRecursosHand = tbProjetoFuncionarios.getTbProjetosRecursosHand();
            if (tbProjetosRecursosHand != null) {
                tbProjetosRecursosHand.getTbProjetoFuncionariosCollection().remove(tbProjetoFuncionarios);
                tbProjetosRecursosHand = em.merge(tbProjetosRecursosHand);
            }
            em.remove(tbProjetoFuncionarios);
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

    public List<TbProjetoFuncionarios> findTbProjetoFuncionariosEntities() {
        return findTbProjetoFuncionariosEntities(true, -1, -1);
    }

    public List<TbProjetoFuncionarios> findTbProjetoFuncionariosEntities(int maxResults, int firstResult) {
        return findTbProjetoFuncionariosEntities(false, maxResults, firstResult);
    }

    private List<TbProjetoFuncionarios> findTbProjetoFuncionariosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TbProjetoFuncionarios.class));
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

    public TbProjetoFuncionarios findTbProjetoFuncionarios(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbProjetoFuncionarios.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbProjetoFuncionariosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TbProjetoFuncionarios> rt = cq.from(TbProjetoFuncionarios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
