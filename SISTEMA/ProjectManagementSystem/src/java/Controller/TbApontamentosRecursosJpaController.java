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
import Model.TbRecursos;
import Model.TbApontamentosFuncionarios;
import Model.TbApontamentosRecursos;
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
public class TbApontamentosRecursosJpaController implements Serializable {

    public TbApontamentosRecursosJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TbApontamentosRecursos tbApontamentosRecursos) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (tbApontamentosRecursos.getTbApontamentosFuncionariosCollection() == null) {
            tbApontamentosRecursos.setTbApontamentosFuncionariosCollection(new ArrayList<TbApontamentosFuncionarios>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbOsServico tbOsServicoHand = tbApontamentosRecursos.getTbOsServicoHand();
            if (tbOsServicoHand != null) {
                tbOsServicoHand = em.getReference(tbOsServicoHand.getClass(), tbOsServicoHand.getHand());
                tbApontamentosRecursos.setTbOsServicoHand(tbOsServicoHand);
            }
            TbRecursos tbRecursosHand = tbApontamentosRecursos.getTbRecursosHand();
            if (tbRecursosHand != null) {
                tbRecursosHand = em.getReference(tbRecursosHand.getClass(), tbRecursosHand.getHand());
                tbApontamentosRecursos.setTbRecursosHand(tbRecursosHand);
            }
            Collection<TbApontamentosFuncionarios> attachedTbApontamentosFuncionariosCollection = new ArrayList<TbApontamentosFuncionarios>();
            for (TbApontamentosFuncionarios tbApontamentosFuncionariosCollectionTbApontamentosFuncionariosToAttach : tbApontamentosRecursos.getTbApontamentosFuncionariosCollection()) {
                tbApontamentosFuncionariosCollectionTbApontamentosFuncionariosToAttach = em.getReference(tbApontamentosFuncionariosCollectionTbApontamentosFuncionariosToAttach.getClass(), tbApontamentosFuncionariosCollectionTbApontamentosFuncionariosToAttach.getHand());
                attachedTbApontamentosFuncionariosCollection.add(tbApontamentosFuncionariosCollectionTbApontamentosFuncionariosToAttach);
            }
            tbApontamentosRecursos.setTbApontamentosFuncionariosCollection(attachedTbApontamentosFuncionariosCollection);
            em.persist(tbApontamentosRecursos);
            if (tbOsServicoHand != null) {
                tbOsServicoHand.getTbApontamentosRecursosCollection().add(tbApontamentosRecursos);
                tbOsServicoHand = em.merge(tbOsServicoHand);
            }
            if (tbRecursosHand != null) {
                tbRecursosHand.getTbApontamentosRecursosCollection().add(tbApontamentosRecursos);
                tbRecursosHand = em.merge(tbRecursosHand);
            }
            for (TbApontamentosFuncionarios tbApontamentosFuncionariosCollectionTbApontamentosFuncionarios : tbApontamentosRecursos.getTbApontamentosFuncionariosCollection()) {
                TbApontamentosRecursos oldTbApontamentosRecursosHandOfTbApontamentosFuncionariosCollectionTbApontamentosFuncionarios = tbApontamentosFuncionariosCollectionTbApontamentosFuncionarios.getTbApontamentosRecursosHand();
                tbApontamentosFuncionariosCollectionTbApontamentosFuncionarios.setTbApontamentosRecursosHand(tbApontamentosRecursos);
                tbApontamentosFuncionariosCollectionTbApontamentosFuncionarios = em.merge(tbApontamentosFuncionariosCollectionTbApontamentosFuncionarios);
                if (oldTbApontamentosRecursosHandOfTbApontamentosFuncionariosCollectionTbApontamentosFuncionarios != null) {
                    oldTbApontamentosRecursosHandOfTbApontamentosFuncionariosCollectionTbApontamentosFuncionarios.getTbApontamentosFuncionariosCollection().remove(tbApontamentosFuncionariosCollectionTbApontamentosFuncionarios);
                    oldTbApontamentosRecursosHandOfTbApontamentosFuncionariosCollectionTbApontamentosFuncionarios = em.merge(oldTbApontamentosRecursosHandOfTbApontamentosFuncionariosCollectionTbApontamentosFuncionarios);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTbApontamentosRecursos(tbApontamentosRecursos.getHand()) != null) {
                throw new PreexistingEntityException("TbApontamentosRecursos " + tbApontamentosRecursos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TbApontamentosRecursos tbApontamentosRecursos) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbApontamentosRecursos persistentTbApontamentosRecursos = em.find(TbApontamentosRecursos.class, tbApontamentosRecursos.getHand());
            TbOsServico tbOsServicoHandOld = persistentTbApontamentosRecursos.getTbOsServicoHand();
            TbOsServico tbOsServicoHandNew = tbApontamentosRecursos.getTbOsServicoHand();
            TbRecursos tbRecursosHandOld = persistentTbApontamentosRecursos.getTbRecursosHand();
            TbRecursos tbRecursosHandNew = tbApontamentosRecursos.getTbRecursosHand();
            Collection<TbApontamentosFuncionarios> tbApontamentosFuncionariosCollectionOld = persistentTbApontamentosRecursos.getTbApontamentosFuncionariosCollection();
            Collection<TbApontamentosFuncionarios> tbApontamentosFuncionariosCollectionNew = tbApontamentosRecursos.getTbApontamentosFuncionariosCollection();
            List<String> illegalOrphanMessages = null;
            for (TbApontamentosFuncionarios tbApontamentosFuncionariosCollectionOldTbApontamentosFuncionarios : tbApontamentosFuncionariosCollectionOld) {
                if (!tbApontamentosFuncionariosCollectionNew.contains(tbApontamentosFuncionariosCollectionOldTbApontamentosFuncionarios)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TbApontamentosFuncionarios " + tbApontamentosFuncionariosCollectionOldTbApontamentosFuncionarios + " since its tbApontamentosRecursosHand field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tbOsServicoHandNew != null) {
                tbOsServicoHandNew = em.getReference(tbOsServicoHandNew.getClass(), tbOsServicoHandNew.getHand());
                tbApontamentosRecursos.setTbOsServicoHand(tbOsServicoHandNew);
            }
            if (tbRecursosHandNew != null) {
                tbRecursosHandNew = em.getReference(tbRecursosHandNew.getClass(), tbRecursosHandNew.getHand());
                tbApontamentosRecursos.setTbRecursosHand(tbRecursosHandNew);
            }
            Collection<TbApontamentosFuncionarios> attachedTbApontamentosFuncionariosCollectionNew = new ArrayList<TbApontamentosFuncionarios>();
            for (TbApontamentosFuncionarios tbApontamentosFuncionariosCollectionNewTbApontamentosFuncionariosToAttach : tbApontamentosFuncionariosCollectionNew) {
                tbApontamentosFuncionariosCollectionNewTbApontamentosFuncionariosToAttach = em.getReference(tbApontamentosFuncionariosCollectionNewTbApontamentosFuncionariosToAttach.getClass(), tbApontamentosFuncionariosCollectionNewTbApontamentosFuncionariosToAttach.getHand());
                attachedTbApontamentosFuncionariosCollectionNew.add(tbApontamentosFuncionariosCollectionNewTbApontamentosFuncionariosToAttach);
            }
            tbApontamentosFuncionariosCollectionNew = attachedTbApontamentosFuncionariosCollectionNew;
            tbApontamentosRecursos.setTbApontamentosFuncionariosCollection(tbApontamentosFuncionariosCollectionNew);
            tbApontamentosRecursos = em.merge(tbApontamentosRecursos);
            if (tbOsServicoHandOld != null && !tbOsServicoHandOld.equals(tbOsServicoHandNew)) {
                tbOsServicoHandOld.getTbApontamentosRecursosCollection().remove(tbApontamentosRecursos);
                tbOsServicoHandOld = em.merge(tbOsServicoHandOld);
            }
            if (tbOsServicoHandNew != null && !tbOsServicoHandNew.equals(tbOsServicoHandOld)) {
                tbOsServicoHandNew.getTbApontamentosRecursosCollection().add(tbApontamentosRecursos);
                tbOsServicoHandNew = em.merge(tbOsServicoHandNew);
            }
            if (tbRecursosHandOld != null && !tbRecursosHandOld.equals(tbRecursosHandNew)) {
                tbRecursosHandOld.getTbApontamentosRecursosCollection().remove(tbApontamentosRecursos);
                tbRecursosHandOld = em.merge(tbRecursosHandOld);
            }
            if (tbRecursosHandNew != null && !tbRecursosHandNew.equals(tbRecursosHandOld)) {
                tbRecursosHandNew.getTbApontamentosRecursosCollection().add(tbApontamentosRecursos);
                tbRecursosHandNew = em.merge(tbRecursosHandNew);
            }
            for (TbApontamentosFuncionarios tbApontamentosFuncionariosCollectionNewTbApontamentosFuncionarios : tbApontamentosFuncionariosCollectionNew) {
                if (!tbApontamentosFuncionariosCollectionOld.contains(tbApontamentosFuncionariosCollectionNewTbApontamentosFuncionarios)) {
                    TbApontamentosRecursos oldTbApontamentosRecursosHandOfTbApontamentosFuncionariosCollectionNewTbApontamentosFuncionarios = tbApontamentosFuncionariosCollectionNewTbApontamentosFuncionarios.getTbApontamentosRecursosHand();
                    tbApontamentosFuncionariosCollectionNewTbApontamentosFuncionarios.setTbApontamentosRecursosHand(tbApontamentosRecursos);
                    tbApontamentosFuncionariosCollectionNewTbApontamentosFuncionarios = em.merge(tbApontamentosFuncionariosCollectionNewTbApontamentosFuncionarios);
                    if (oldTbApontamentosRecursosHandOfTbApontamentosFuncionariosCollectionNewTbApontamentosFuncionarios != null && !oldTbApontamentosRecursosHandOfTbApontamentosFuncionariosCollectionNewTbApontamentosFuncionarios.equals(tbApontamentosRecursos)) {
                        oldTbApontamentosRecursosHandOfTbApontamentosFuncionariosCollectionNewTbApontamentosFuncionarios.getTbApontamentosFuncionariosCollection().remove(tbApontamentosFuncionariosCollectionNewTbApontamentosFuncionarios);
                        oldTbApontamentosRecursosHandOfTbApontamentosFuncionariosCollectionNewTbApontamentosFuncionarios = em.merge(oldTbApontamentosRecursosHandOfTbApontamentosFuncionariosCollectionNewTbApontamentosFuncionarios);
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
                Integer id = tbApontamentosRecursos.getHand();
                if (findTbApontamentosRecursos(id) == null) {
                    throw new NonexistentEntityException("The tbApontamentosRecursos with id " + id + " no longer exists.");
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
            TbApontamentosRecursos tbApontamentosRecursos;
            try {
                tbApontamentosRecursos = em.getReference(TbApontamentosRecursos.class, id);
                tbApontamentosRecursos.getHand();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tbApontamentosRecursos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TbApontamentosFuncionarios> tbApontamentosFuncionariosCollectionOrphanCheck = tbApontamentosRecursos.getTbApontamentosFuncionariosCollection();
            for (TbApontamentosFuncionarios tbApontamentosFuncionariosCollectionOrphanCheckTbApontamentosFuncionarios : tbApontamentosFuncionariosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbApontamentosRecursos (" + tbApontamentosRecursos + ") cannot be destroyed since the TbApontamentosFuncionarios " + tbApontamentosFuncionariosCollectionOrphanCheckTbApontamentosFuncionarios + " in its tbApontamentosFuncionariosCollection field has a non-nullable tbApontamentosRecursosHand field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            TbOsServico tbOsServicoHand = tbApontamentosRecursos.getTbOsServicoHand();
            if (tbOsServicoHand != null) {
                tbOsServicoHand.getTbApontamentosRecursosCollection().remove(tbApontamentosRecursos);
                tbOsServicoHand = em.merge(tbOsServicoHand);
            }
            TbRecursos tbRecursosHand = tbApontamentosRecursos.getTbRecursosHand();
            if (tbRecursosHand != null) {
                tbRecursosHand.getTbApontamentosRecursosCollection().remove(tbApontamentosRecursos);
                tbRecursosHand = em.merge(tbRecursosHand);
            }
            em.remove(tbApontamentosRecursos);
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

    public List<TbApontamentosRecursos> findTbApontamentosRecursosEntities() {
        return findTbApontamentosRecursosEntities(true, -1, -1);
    }

    public List<TbApontamentosRecursos> findTbApontamentosRecursosEntities(int maxResults, int firstResult) {
        return findTbApontamentosRecursosEntities(false, maxResults, firstResult);
    }

    private List<TbApontamentosRecursos> findTbApontamentosRecursosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TbApontamentosRecursos.class));
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

    public TbApontamentosRecursos findTbApontamentosRecursos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbApontamentosRecursos.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbApontamentosRecursosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TbApontamentosRecursos> rt = cq.from(TbApontamentosRecursos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
