/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Controle.exceptions.IllegalOrphanException;
import Controle.exceptions.NonexistentEntityException;
import Controle.exceptions.PreexistingEntityException;
import Controle.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import Entidades.TbPessoa;
import Entidades.TbTipopessoa;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;

/**
 *
 * @author berlim
 */
public class TbTipopessoaJpaController implements Serializable {

    public TbTipopessoaJpaController() {
        emf = Persistence.createEntityManagerFactory("ProjectManagementSystemPU");
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TbTipopessoa tbTipopessoa) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (tbTipopessoa.getTbPessoaCollection() == null) {
            tbTipopessoa.setTbPessoaCollection(new ArrayList<TbPessoa>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<TbPessoa> attachedTbPessoaCollection = new ArrayList<TbPessoa>();
            for (TbPessoa tbPessoaCollectionTbPessoaToAttach : tbTipopessoa.getTbPessoaCollection()) {
                tbPessoaCollectionTbPessoaToAttach = em.getReference(tbPessoaCollectionTbPessoaToAttach.getClass(), tbPessoaCollectionTbPessoaToAttach.getHand());
                attachedTbPessoaCollection.add(tbPessoaCollectionTbPessoaToAttach);
            }
            tbTipopessoa.setTbPessoaCollection(attachedTbPessoaCollection);
            em.persist(tbTipopessoa);
            for (TbPessoa tbPessoaCollectionTbPessoa : tbTipopessoa.getTbPessoaCollection()) {
                TbTipopessoa oldTbTipopessoaHandOfTbPessoaCollectionTbPessoa = tbPessoaCollectionTbPessoa.getTbTipopessoaHand();
                tbPessoaCollectionTbPessoa.setTbTipopessoaHand(tbTipopessoa);
                tbPessoaCollectionTbPessoa = em.merge(tbPessoaCollectionTbPessoa);
                if (oldTbTipopessoaHandOfTbPessoaCollectionTbPessoa != null) {
                    oldTbTipopessoaHandOfTbPessoaCollectionTbPessoa.getTbPessoaCollection().remove(tbPessoaCollectionTbPessoa);
                    oldTbTipopessoaHandOfTbPessoaCollectionTbPessoa = em.merge(oldTbTipopessoaHandOfTbPessoaCollectionTbPessoa);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTbTipopessoa(tbTipopessoa.getHand()) != null) {
                throw new PreexistingEntityException("TbTipopessoa " + tbTipopessoa + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TbTipopessoa tbTipopessoa) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbTipopessoa persistentTbTipopessoa = em.find(TbTipopessoa.class, tbTipopessoa.getHand());
            Collection<TbPessoa> tbPessoaCollectionOld = persistentTbTipopessoa.getTbPessoaCollection();
            Collection<TbPessoa> tbPessoaCollectionNew = tbTipopessoa.getTbPessoaCollection();
            List<String> illegalOrphanMessages = null;
            for (TbPessoa tbPessoaCollectionOldTbPessoa : tbPessoaCollectionOld) {
                if (!tbPessoaCollectionNew.contains(tbPessoaCollectionOldTbPessoa)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TbPessoa " + tbPessoaCollectionOldTbPessoa + " since its tbTipopessoaHand field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<TbPessoa> attachedTbPessoaCollectionNew = new ArrayList<TbPessoa>();
            for (TbPessoa tbPessoaCollectionNewTbPessoaToAttach : tbPessoaCollectionNew) {
                tbPessoaCollectionNewTbPessoaToAttach = em.getReference(tbPessoaCollectionNewTbPessoaToAttach.getClass(), tbPessoaCollectionNewTbPessoaToAttach.getHand());
                attachedTbPessoaCollectionNew.add(tbPessoaCollectionNewTbPessoaToAttach);
            }
            tbPessoaCollectionNew = attachedTbPessoaCollectionNew;
            tbTipopessoa.setTbPessoaCollection(tbPessoaCollectionNew);
            tbTipopessoa = em.merge(tbTipopessoa);
            for (TbPessoa tbPessoaCollectionNewTbPessoa : tbPessoaCollectionNew) {
                if (!tbPessoaCollectionOld.contains(tbPessoaCollectionNewTbPessoa)) {
                    TbTipopessoa oldTbTipopessoaHandOfTbPessoaCollectionNewTbPessoa = tbPessoaCollectionNewTbPessoa.getTbTipopessoaHand();
                    tbPessoaCollectionNewTbPessoa.setTbTipopessoaHand(tbTipopessoa);
                    tbPessoaCollectionNewTbPessoa = em.merge(tbPessoaCollectionNewTbPessoa);
                    if (oldTbTipopessoaHandOfTbPessoaCollectionNewTbPessoa != null && !oldTbTipopessoaHandOfTbPessoaCollectionNewTbPessoa.equals(tbTipopessoa)) {
                        oldTbTipopessoaHandOfTbPessoaCollectionNewTbPessoa.getTbPessoaCollection().remove(tbPessoaCollectionNewTbPessoa);
                        oldTbTipopessoaHandOfTbPessoaCollectionNewTbPessoa = em.merge(oldTbTipopessoaHandOfTbPessoaCollectionNewTbPessoa);
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
                Integer id = tbTipopessoa.getHand();
                if (findTbTipopessoa(id) == null) {
                    throw new NonexistentEntityException("The tbTipopessoa with id " + id + " no longer exists.");
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
            TbTipopessoa tbTipopessoa;
            try {
                tbTipopessoa = em.getReference(TbTipopessoa.class, id);
                tbTipopessoa.getHand();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tbTipopessoa with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TbPessoa> tbPessoaCollectionOrphanCheck = tbTipopessoa.getTbPessoaCollection();
            for (TbPessoa tbPessoaCollectionOrphanCheckTbPessoa : tbPessoaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbTipopessoa (" + tbTipopessoa + ") cannot be destroyed since the TbPessoa " + tbPessoaCollectionOrphanCheckTbPessoa + " in its tbPessoaCollection field has a non-nullable tbTipopessoaHand field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tbTipopessoa);
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

    public List<TbTipopessoa> findTbTipopessoaEntities() {
        return findTbTipopessoaEntities(true, -1, -1);
    }

    public List<TbTipopessoa> findTbTipopessoaEntities(int maxResults, int firstResult) {
        return findTbTipopessoaEntities(false, maxResults, firstResult);
    }

    private List<TbTipopessoa> findTbTipopessoaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from TbTipopessoa as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public TbTipopessoa findTbTipopessoa(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbTipopessoa.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbTipopessoaCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from TbTipopessoa as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
