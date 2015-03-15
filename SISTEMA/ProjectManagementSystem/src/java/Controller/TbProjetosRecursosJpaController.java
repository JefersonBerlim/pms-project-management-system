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
import Model.TbProjetosServicos;
import Model.TbRecursos;
import Model.TbOsRecursosTotal;
import java.util.ArrayList;
import java.util.Collection;
import Model.TbProjetoFuncionarios;
import Model.TbProjetosRecursos;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author BERLIM
 */
public class TbProjetosRecursosJpaController implements Serializable {

    public TbProjetosRecursosJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TbProjetosRecursos tbProjetosRecursos) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (tbProjetosRecursos.getTbOsRecursosTotalCollection() == null) {
            tbProjetosRecursos.setTbOsRecursosTotalCollection(new ArrayList<TbOsRecursosTotal>());
        }
        if (tbProjetosRecursos.getTbProjetoFuncionariosCollection() == null) {
            tbProjetosRecursos.setTbProjetoFuncionariosCollection(new ArrayList<TbProjetoFuncionarios>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbProjetosServicos tbProjetosServicosHand = tbProjetosRecursos.getTbProjetosServicosHand();
            if (tbProjetosServicosHand != null) {
                tbProjetosServicosHand = em.getReference(tbProjetosServicosHand.getClass(), tbProjetosServicosHand.getHand());
                tbProjetosRecursos.setTbProjetosServicosHand(tbProjetosServicosHand);
            }
            TbRecursos tbRecursosHand = tbProjetosRecursos.getTbRecursosHand();
            if (tbRecursosHand != null) {
                tbRecursosHand = em.getReference(tbRecursosHand.getClass(), tbRecursosHand.getHand());
                tbProjetosRecursos.setTbRecursosHand(tbRecursosHand);
            }
            Collection<TbOsRecursosTotal> attachedTbOsRecursosTotalCollection = new ArrayList<TbOsRecursosTotal>();
            for (TbOsRecursosTotal tbOsRecursosTotalCollectionTbOsRecursosTotalToAttach : tbProjetosRecursos.getTbOsRecursosTotalCollection()) {
                tbOsRecursosTotalCollectionTbOsRecursosTotalToAttach = em.getReference(tbOsRecursosTotalCollectionTbOsRecursosTotalToAttach.getClass(), tbOsRecursosTotalCollectionTbOsRecursosTotalToAttach.getHand());
                attachedTbOsRecursosTotalCollection.add(tbOsRecursosTotalCollectionTbOsRecursosTotalToAttach);
            }
            tbProjetosRecursos.setTbOsRecursosTotalCollection(attachedTbOsRecursosTotalCollection);
            Collection<TbProjetoFuncionarios> attachedTbProjetoFuncionariosCollection = new ArrayList<TbProjetoFuncionarios>();
            for (TbProjetoFuncionarios tbProjetoFuncionariosCollectionTbProjetoFuncionariosToAttach : tbProjetosRecursos.getTbProjetoFuncionariosCollection()) {
                tbProjetoFuncionariosCollectionTbProjetoFuncionariosToAttach = em.getReference(tbProjetoFuncionariosCollectionTbProjetoFuncionariosToAttach.getClass(), tbProjetoFuncionariosCollectionTbProjetoFuncionariosToAttach.getHand());
                attachedTbProjetoFuncionariosCollection.add(tbProjetoFuncionariosCollectionTbProjetoFuncionariosToAttach);
            }
            tbProjetosRecursos.setTbProjetoFuncionariosCollection(attachedTbProjetoFuncionariosCollection);
            em.persist(tbProjetosRecursos);
            if (tbProjetosServicosHand != null) {
                tbProjetosServicosHand.getTbProjetosRecursosCollection().add(tbProjetosRecursos);
                tbProjetosServicosHand = em.merge(tbProjetosServicosHand);
            }
            if (tbRecursosHand != null) {
                tbRecursosHand.getTbProjetosRecursosCollection().add(tbProjetosRecursos);
                tbRecursosHand = em.merge(tbRecursosHand);
            }
            for (TbOsRecursosTotal tbOsRecursosTotalCollectionTbOsRecursosTotal : tbProjetosRecursos.getTbOsRecursosTotalCollection()) {
                TbProjetosRecursos oldTbProjetosRecursosHandOfTbOsRecursosTotalCollectionTbOsRecursosTotal = tbOsRecursosTotalCollectionTbOsRecursosTotal.getTbProjetosRecursosHand();
                tbOsRecursosTotalCollectionTbOsRecursosTotal.setTbProjetosRecursosHand(tbProjetosRecursos);
                tbOsRecursosTotalCollectionTbOsRecursosTotal = em.merge(tbOsRecursosTotalCollectionTbOsRecursosTotal);
                if (oldTbProjetosRecursosHandOfTbOsRecursosTotalCollectionTbOsRecursosTotal != null) {
                    oldTbProjetosRecursosHandOfTbOsRecursosTotalCollectionTbOsRecursosTotal.getTbOsRecursosTotalCollection().remove(tbOsRecursosTotalCollectionTbOsRecursosTotal);
                    oldTbProjetosRecursosHandOfTbOsRecursosTotalCollectionTbOsRecursosTotal = em.merge(oldTbProjetosRecursosHandOfTbOsRecursosTotalCollectionTbOsRecursosTotal);
                }
            }
            for (TbProjetoFuncionarios tbProjetoFuncionariosCollectionTbProjetoFuncionarios : tbProjetosRecursos.getTbProjetoFuncionariosCollection()) {
                TbProjetosRecursos oldTbProjetosRecursosHandOfTbProjetoFuncionariosCollectionTbProjetoFuncionarios = tbProjetoFuncionariosCollectionTbProjetoFuncionarios.getTbProjetosRecursosHand();
                tbProjetoFuncionariosCollectionTbProjetoFuncionarios.setTbProjetosRecursosHand(tbProjetosRecursos);
                tbProjetoFuncionariosCollectionTbProjetoFuncionarios = em.merge(tbProjetoFuncionariosCollectionTbProjetoFuncionarios);
                if (oldTbProjetosRecursosHandOfTbProjetoFuncionariosCollectionTbProjetoFuncionarios != null) {
                    oldTbProjetosRecursosHandOfTbProjetoFuncionariosCollectionTbProjetoFuncionarios.getTbProjetoFuncionariosCollection().remove(tbProjetoFuncionariosCollectionTbProjetoFuncionarios);
                    oldTbProjetosRecursosHandOfTbProjetoFuncionariosCollectionTbProjetoFuncionarios = em.merge(oldTbProjetosRecursosHandOfTbProjetoFuncionariosCollectionTbProjetoFuncionarios);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTbProjetosRecursos(tbProjetosRecursos.getHand()) != null) {
                throw new PreexistingEntityException("TbProjetosRecursos " + tbProjetosRecursos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TbProjetosRecursos tbProjetosRecursos) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbProjetosRecursos persistentTbProjetosRecursos = em.find(TbProjetosRecursos.class, tbProjetosRecursos.getHand());
            TbProjetosServicos tbProjetosServicosHandOld = persistentTbProjetosRecursos.getTbProjetosServicosHand();
            TbProjetosServicos tbProjetosServicosHandNew = tbProjetosRecursos.getTbProjetosServicosHand();
            TbRecursos tbRecursosHandOld = persistentTbProjetosRecursos.getTbRecursosHand();
            TbRecursos tbRecursosHandNew = tbProjetosRecursos.getTbRecursosHand();
            Collection<TbOsRecursosTotal> tbOsRecursosTotalCollectionOld = persistentTbProjetosRecursos.getTbOsRecursosTotalCollection();
            Collection<TbOsRecursosTotal> tbOsRecursosTotalCollectionNew = tbProjetosRecursos.getTbOsRecursosTotalCollection();
            Collection<TbProjetoFuncionarios> tbProjetoFuncionariosCollectionOld = persistentTbProjetosRecursos.getTbProjetoFuncionariosCollection();
            Collection<TbProjetoFuncionarios> tbProjetoFuncionariosCollectionNew = tbProjetosRecursos.getTbProjetoFuncionariosCollection();
            List<String> illegalOrphanMessages = null;
            for (TbOsRecursosTotal tbOsRecursosTotalCollectionOldTbOsRecursosTotal : tbOsRecursosTotalCollectionOld) {
                if (!tbOsRecursosTotalCollectionNew.contains(tbOsRecursosTotalCollectionOldTbOsRecursosTotal)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TbOsRecursosTotal " + tbOsRecursosTotalCollectionOldTbOsRecursosTotal + " since its tbProjetosRecursosHand field is not nullable.");
                }
            }
            for (TbProjetoFuncionarios tbProjetoFuncionariosCollectionOldTbProjetoFuncionarios : tbProjetoFuncionariosCollectionOld) {
                if (!tbProjetoFuncionariosCollectionNew.contains(tbProjetoFuncionariosCollectionOldTbProjetoFuncionarios)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TbProjetoFuncionarios " + tbProjetoFuncionariosCollectionOldTbProjetoFuncionarios + " since its tbProjetosRecursosHand field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tbProjetosServicosHandNew != null) {
                tbProjetosServicosHandNew = em.getReference(tbProjetosServicosHandNew.getClass(), tbProjetosServicosHandNew.getHand());
                tbProjetosRecursos.setTbProjetosServicosHand(tbProjetosServicosHandNew);
            }
            if (tbRecursosHandNew != null) {
                tbRecursosHandNew = em.getReference(tbRecursosHandNew.getClass(), tbRecursosHandNew.getHand());
                tbProjetosRecursos.setTbRecursosHand(tbRecursosHandNew);
            }
            Collection<TbOsRecursosTotal> attachedTbOsRecursosTotalCollectionNew = new ArrayList<TbOsRecursosTotal>();
            for (TbOsRecursosTotal tbOsRecursosTotalCollectionNewTbOsRecursosTotalToAttach : tbOsRecursosTotalCollectionNew) {
                tbOsRecursosTotalCollectionNewTbOsRecursosTotalToAttach = em.getReference(tbOsRecursosTotalCollectionNewTbOsRecursosTotalToAttach.getClass(), tbOsRecursosTotalCollectionNewTbOsRecursosTotalToAttach.getHand());
                attachedTbOsRecursosTotalCollectionNew.add(tbOsRecursosTotalCollectionNewTbOsRecursosTotalToAttach);
            }
            tbOsRecursosTotalCollectionNew = attachedTbOsRecursosTotalCollectionNew;
            tbProjetosRecursos.setTbOsRecursosTotalCollection(tbOsRecursosTotalCollectionNew);
            Collection<TbProjetoFuncionarios> attachedTbProjetoFuncionariosCollectionNew = new ArrayList<TbProjetoFuncionarios>();
            for (TbProjetoFuncionarios tbProjetoFuncionariosCollectionNewTbProjetoFuncionariosToAttach : tbProjetoFuncionariosCollectionNew) {
                tbProjetoFuncionariosCollectionNewTbProjetoFuncionariosToAttach = em.getReference(tbProjetoFuncionariosCollectionNewTbProjetoFuncionariosToAttach.getClass(), tbProjetoFuncionariosCollectionNewTbProjetoFuncionariosToAttach.getHand());
                attachedTbProjetoFuncionariosCollectionNew.add(tbProjetoFuncionariosCollectionNewTbProjetoFuncionariosToAttach);
            }
            tbProjetoFuncionariosCollectionNew = attachedTbProjetoFuncionariosCollectionNew;
            tbProjetosRecursos.setTbProjetoFuncionariosCollection(tbProjetoFuncionariosCollectionNew);
            tbProjetosRecursos = em.merge(tbProjetosRecursos);
            if (tbProjetosServicosHandOld != null && !tbProjetosServicosHandOld.equals(tbProjetosServicosHandNew)) {
                tbProjetosServicosHandOld.getTbProjetosRecursosCollection().remove(tbProjetosRecursos);
                tbProjetosServicosHandOld = em.merge(tbProjetosServicosHandOld);
            }
            if (tbProjetosServicosHandNew != null && !tbProjetosServicosHandNew.equals(tbProjetosServicosHandOld)) {
                tbProjetosServicosHandNew.getTbProjetosRecursosCollection().add(tbProjetosRecursos);
                tbProjetosServicosHandNew = em.merge(tbProjetosServicosHandNew);
            }
            if (tbRecursosHandOld != null && !tbRecursosHandOld.equals(tbRecursosHandNew)) {
                tbRecursosHandOld.getTbProjetosRecursosCollection().remove(tbProjetosRecursos);
                tbRecursosHandOld = em.merge(tbRecursosHandOld);
            }
            if (tbRecursosHandNew != null && !tbRecursosHandNew.equals(tbRecursosHandOld)) {
                tbRecursosHandNew.getTbProjetosRecursosCollection().add(tbProjetosRecursos);
                tbRecursosHandNew = em.merge(tbRecursosHandNew);
            }
            for (TbOsRecursosTotal tbOsRecursosTotalCollectionNewTbOsRecursosTotal : tbOsRecursosTotalCollectionNew) {
                if (!tbOsRecursosTotalCollectionOld.contains(tbOsRecursosTotalCollectionNewTbOsRecursosTotal)) {
                    TbProjetosRecursos oldTbProjetosRecursosHandOfTbOsRecursosTotalCollectionNewTbOsRecursosTotal = tbOsRecursosTotalCollectionNewTbOsRecursosTotal.getTbProjetosRecursosHand();
                    tbOsRecursosTotalCollectionNewTbOsRecursosTotal.setTbProjetosRecursosHand(tbProjetosRecursos);
                    tbOsRecursosTotalCollectionNewTbOsRecursosTotal = em.merge(tbOsRecursosTotalCollectionNewTbOsRecursosTotal);
                    if (oldTbProjetosRecursosHandOfTbOsRecursosTotalCollectionNewTbOsRecursosTotal != null && !oldTbProjetosRecursosHandOfTbOsRecursosTotalCollectionNewTbOsRecursosTotal.equals(tbProjetosRecursos)) {
                        oldTbProjetosRecursosHandOfTbOsRecursosTotalCollectionNewTbOsRecursosTotal.getTbOsRecursosTotalCollection().remove(tbOsRecursosTotalCollectionNewTbOsRecursosTotal);
                        oldTbProjetosRecursosHandOfTbOsRecursosTotalCollectionNewTbOsRecursosTotal = em.merge(oldTbProjetosRecursosHandOfTbOsRecursosTotalCollectionNewTbOsRecursosTotal);
                    }
                }
            }
            for (TbProjetoFuncionarios tbProjetoFuncionariosCollectionNewTbProjetoFuncionarios : tbProjetoFuncionariosCollectionNew) {
                if (!tbProjetoFuncionariosCollectionOld.contains(tbProjetoFuncionariosCollectionNewTbProjetoFuncionarios)) {
                    TbProjetosRecursos oldTbProjetosRecursosHandOfTbProjetoFuncionariosCollectionNewTbProjetoFuncionarios = tbProjetoFuncionariosCollectionNewTbProjetoFuncionarios.getTbProjetosRecursosHand();
                    tbProjetoFuncionariosCollectionNewTbProjetoFuncionarios.setTbProjetosRecursosHand(tbProjetosRecursos);
                    tbProjetoFuncionariosCollectionNewTbProjetoFuncionarios = em.merge(tbProjetoFuncionariosCollectionNewTbProjetoFuncionarios);
                    if (oldTbProjetosRecursosHandOfTbProjetoFuncionariosCollectionNewTbProjetoFuncionarios != null && !oldTbProjetosRecursosHandOfTbProjetoFuncionariosCollectionNewTbProjetoFuncionarios.equals(tbProjetosRecursos)) {
                        oldTbProjetosRecursosHandOfTbProjetoFuncionariosCollectionNewTbProjetoFuncionarios.getTbProjetoFuncionariosCollection().remove(tbProjetoFuncionariosCollectionNewTbProjetoFuncionarios);
                        oldTbProjetosRecursosHandOfTbProjetoFuncionariosCollectionNewTbProjetoFuncionarios = em.merge(oldTbProjetosRecursosHandOfTbProjetoFuncionariosCollectionNewTbProjetoFuncionarios);
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
                Integer id = tbProjetosRecursos.getHand();
                if (findTbProjetosRecursos(id) == null) {
                    throw new NonexistentEntityException("The tbProjetosRecursos with id " + id + " no longer exists.");
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
            TbProjetosRecursos tbProjetosRecursos;
            try {
                tbProjetosRecursos = em.getReference(TbProjetosRecursos.class, id);
                tbProjetosRecursos.getHand();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tbProjetosRecursos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TbOsRecursosTotal> tbOsRecursosTotalCollectionOrphanCheck = tbProjetosRecursos.getTbOsRecursosTotalCollection();
            for (TbOsRecursosTotal tbOsRecursosTotalCollectionOrphanCheckTbOsRecursosTotal : tbOsRecursosTotalCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbProjetosRecursos (" + tbProjetosRecursos + ") cannot be destroyed since the TbOsRecursosTotal " + tbOsRecursosTotalCollectionOrphanCheckTbOsRecursosTotal + " in its tbOsRecursosTotalCollection field has a non-nullable tbProjetosRecursosHand field.");
            }
            Collection<TbProjetoFuncionarios> tbProjetoFuncionariosCollectionOrphanCheck = tbProjetosRecursos.getTbProjetoFuncionariosCollection();
            for (TbProjetoFuncionarios tbProjetoFuncionariosCollectionOrphanCheckTbProjetoFuncionarios : tbProjetoFuncionariosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbProjetosRecursos (" + tbProjetosRecursos + ") cannot be destroyed since the TbProjetoFuncionarios " + tbProjetoFuncionariosCollectionOrphanCheckTbProjetoFuncionarios + " in its tbProjetoFuncionariosCollection field has a non-nullable tbProjetosRecursosHand field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            TbProjetosServicos tbProjetosServicosHand = tbProjetosRecursos.getTbProjetosServicosHand();
            if (tbProjetosServicosHand != null) {
                tbProjetosServicosHand.getTbProjetosRecursosCollection().remove(tbProjetosRecursos);
                tbProjetosServicosHand = em.merge(tbProjetosServicosHand);
            }
            TbRecursos tbRecursosHand = tbProjetosRecursos.getTbRecursosHand();
            if (tbRecursosHand != null) {
                tbRecursosHand.getTbProjetosRecursosCollection().remove(tbProjetosRecursos);
                tbRecursosHand = em.merge(tbRecursosHand);
            }
            em.remove(tbProjetosRecursos);
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

    public List<TbProjetosRecursos> findTbProjetosRecursosEntities() {
        return findTbProjetosRecursosEntities(true, -1, -1);
    }

    public List<TbProjetosRecursos> findTbProjetosRecursosEntities(int maxResults, int firstResult) {
        return findTbProjetosRecursosEntities(false, maxResults, firstResult);
    }

    private List<TbProjetosRecursos> findTbProjetosRecursosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TbProjetosRecursos.class));
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

    public TbProjetosRecursos findTbProjetosRecursos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbProjetosRecursos.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbProjetosRecursosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TbProjetosRecursos> rt = cq.from(TbProjetosRecursos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
