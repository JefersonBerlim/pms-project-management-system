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
import Model.TbCidades;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Model.TbEstados;
import Model.TbPessoa;
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
public class TbCidadesJpaController implements Serializable {

    public TbCidadesJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TbCidades tbCidades) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (tbCidades.getTbPessoaCollection() == null) {
            tbCidades.setTbPessoaCollection(new ArrayList<TbPessoa>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbEstados tbEstadosHand = tbCidades.getTbEstadosHand();
            if (tbEstadosHand != null) {
                tbEstadosHand = em.getReference(tbEstadosHand.getClass(), tbEstadosHand.getHand());
                tbCidades.setTbEstadosHand(tbEstadosHand);
            }
            Collection<TbPessoa> attachedTbPessoaCollection = new ArrayList<TbPessoa>();
            for (TbPessoa tbPessoaCollectionTbPessoaToAttach : tbCidades.getTbPessoaCollection()) {
                tbPessoaCollectionTbPessoaToAttach = em.getReference(tbPessoaCollectionTbPessoaToAttach.getClass(), tbPessoaCollectionTbPessoaToAttach.getHand());
                attachedTbPessoaCollection.add(tbPessoaCollectionTbPessoaToAttach);
            }
            tbCidades.setTbPessoaCollection(attachedTbPessoaCollection);
            em.persist(tbCidades);
            if (tbEstadosHand != null) {
                tbEstadosHand.getTbCidadesCollection().add(tbCidades);
                tbEstadosHand = em.merge(tbEstadosHand);
            }
            for (TbPessoa tbPessoaCollectionTbPessoa : tbCidades.getTbPessoaCollection()) {
                TbCidades oldTbCidadesHandOfTbPessoaCollectionTbPessoa = tbPessoaCollectionTbPessoa.getTbCidadesHand();
                tbPessoaCollectionTbPessoa.setTbCidadesHand(tbCidades);
                tbPessoaCollectionTbPessoa = em.merge(tbPessoaCollectionTbPessoa);
                if (oldTbCidadesHandOfTbPessoaCollectionTbPessoa != null) {
                    oldTbCidadesHandOfTbPessoaCollectionTbPessoa.getTbPessoaCollection().remove(tbPessoaCollectionTbPessoa);
                    oldTbCidadesHandOfTbPessoaCollectionTbPessoa = em.merge(oldTbCidadesHandOfTbPessoaCollectionTbPessoa);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTbCidades(tbCidades.getHand()) != null) {
                throw new PreexistingEntityException("TbCidades " + tbCidades + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TbCidades tbCidades) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbCidades persistentTbCidades = em.find(TbCidades.class, tbCidades.getHand());
            TbEstados tbEstadosHandOld = persistentTbCidades.getTbEstadosHand();
            TbEstados tbEstadosHandNew = tbCidades.getTbEstadosHand();
            Collection<TbPessoa> tbPessoaCollectionOld = persistentTbCidades.getTbPessoaCollection();
            Collection<TbPessoa> tbPessoaCollectionNew = tbCidades.getTbPessoaCollection();
            List<String> illegalOrphanMessages = null;
            for (TbPessoa tbPessoaCollectionOldTbPessoa : tbPessoaCollectionOld) {
                if (!tbPessoaCollectionNew.contains(tbPessoaCollectionOldTbPessoa)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TbPessoa " + tbPessoaCollectionOldTbPessoa + " since its tbCidadesHand field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tbEstadosHandNew != null) {
                tbEstadosHandNew = em.getReference(tbEstadosHandNew.getClass(), tbEstadosHandNew.getHand());
                tbCidades.setTbEstadosHand(tbEstadosHandNew);
            }
            Collection<TbPessoa> attachedTbPessoaCollectionNew = new ArrayList<TbPessoa>();
            for (TbPessoa tbPessoaCollectionNewTbPessoaToAttach : tbPessoaCollectionNew) {
                tbPessoaCollectionNewTbPessoaToAttach = em.getReference(tbPessoaCollectionNewTbPessoaToAttach.getClass(), tbPessoaCollectionNewTbPessoaToAttach.getHand());
                attachedTbPessoaCollectionNew.add(tbPessoaCollectionNewTbPessoaToAttach);
            }
            tbPessoaCollectionNew = attachedTbPessoaCollectionNew;
            tbCidades.setTbPessoaCollection(tbPessoaCollectionNew);
            tbCidades = em.merge(tbCidades);
            if (tbEstadosHandOld != null && !tbEstadosHandOld.equals(tbEstadosHandNew)) {
                tbEstadosHandOld.getTbCidadesCollection().remove(tbCidades);
                tbEstadosHandOld = em.merge(tbEstadosHandOld);
            }
            if (tbEstadosHandNew != null && !tbEstadosHandNew.equals(tbEstadosHandOld)) {
                tbEstadosHandNew.getTbCidadesCollection().add(tbCidades);
                tbEstadosHandNew = em.merge(tbEstadosHandNew);
            }
            for (TbPessoa tbPessoaCollectionNewTbPessoa : tbPessoaCollectionNew) {
                if (!tbPessoaCollectionOld.contains(tbPessoaCollectionNewTbPessoa)) {
                    TbCidades oldTbCidadesHandOfTbPessoaCollectionNewTbPessoa = tbPessoaCollectionNewTbPessoa.getTbCidadesHand();
                    tbPessoaCollectionNewTbPessoa.setTbCidadesHand(tbCidades);
                    tbPessoaCollectionNewTbPessoa = em.merge(tbPessoaCollectionNewTbPessoa);
                    if (oldTbCidadesHandOfTbPessoaCollectionNewTbPessoa != null && !oldTbCidadesHandOfTbPessoaCollectionNewTbPessoa.equals(tbCidades)) {
                        oldTbCidadesHandOfTbPessoaCollectionNewTbPessoa.getTbPessoaCollection().remove(tbPessoaCollectionNewTbPessoa);
                        oldTbCidadesHandOfTbPessoaCollectionNewTbPessoa = em.merge(oldTbCidadesHandOfTbPessoaCollectionNewTbPessoa);
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
                Integer id = tbCidades.getHand();
                if (findTbCidades(id) == null) {
                    throw new NonexistentEntityException("The tbCidades with id " + id + " no longer exists.");
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
            TbCidades tbCidades;
            try {
                tbCidades = em.getReference(TbCidades.class, id);
                tbCidades.getHand();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tbCidades with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TbPessoa> tbPessoaCollectionOrphanCheck = tbCidades.getTbPessoaCollection();
            for (TbPessoa tbPessoaCollectionOrphanCheckTbPessoa : tbPessoaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbCidades (" + tbCidades + ") cannot be destroyed since the TbPessoa " + tbPessoaCollectionOrphanCheckTbPessoa + " in its tbPessoaCollection field has a non-nullable tbCidadesHand field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            TbEstados tbEstadosHand = tbCidades.getTbEstadosHand();
            if (tbEstadosHand != null) {
                tbEstadosHand.getTbCidadesCollection().remove(tbCidades);
                tbEstadosHand = em.merge(tbEstadosHand);
            }
            em.remove(tbCidades);
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

    public List<TbCidades> findTbCidadesEntities() {
        return findTbCidadesEntities(true, -1, -1);
    }

    public List<TbCidades> findTbCidadesEntities(int maxResults, int firstResult) {
        return findTbCidadesEntities(false, maxResults, firstResult);
    }

    private List<TbCidades> findTbCidadesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TbCidades.class));
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

    public TbCidades findTbCidades(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbCidades.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbCidadesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TbCidades> rt = cq.from(TbCidades.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
