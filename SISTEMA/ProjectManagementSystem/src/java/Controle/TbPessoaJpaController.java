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
import Entidades.TbTipopessoa;
import Entidades.TbCidades;
import Entidades.TbPessoa;
import Entidades.TbPessoaProjeto;
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
public class TbPessoaJpaController implements Serializable {

    public TbPessoaJpaController() {
        emf = Persistence.createEntityManagerFactory("ProjectManagementSystemPU");
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TbPessoa tbPessoa) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (tbPessoa.getTbPessoaProjetoCollection() == null) {
            tbPessoa.setTbPessoaProjetoCollection(new ArrayList<TbPessoaProjeto>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbTipopessoa tbTipopessoaHand = tbPessoa.getTbTipopessoaHand();
            if (tbTipopessoaHand != null) {
                tbTipopessoaHand = em.getReference(tbTipopessoaHand.getClass(), tbTipopessoaHand.getHand());
                tbPessoa.setTbTipopessoaHand(tbTipopessoaHand);
            }
            TbCidades tbCidadesHand = tbPessoa.getTbCidadesHand();
            if (tbCidadesHand != null) {
                tbCidadesHand = em.getReference(tbCidadesHand.getClass(), tbCidadesHand.getHand());
                tbPessoa.setTbCidadesHand(tbCidadesHand);
            }
            Collection<TbPessoaProjeto> attachedTbPessoaProjetoCollection = new ArrayList<TbPessoaProjeto>();
            for (TbPessoaProjeto tbPessoaProjetoCollectionTbPessoaProjetoToAttach : tbPessoa.getTbPessoaProjetoCollection()) {
                tbPessoaProjetoCollectionTbPessoaProjetoToAttach = em.getReference(tbPessoaProjetoCollectionTbPessoaProjetoToAttach.getClass(), tbPessoaProjetoCollectionTbPessoaProjetoToAttach.getHand());
                attachedTbPessoaProjetoCollection.add(tbPessoaProjetoCollectionTbPessoaProjetoToAttach);
            }
            tbPessoa.setTbPessoaProjetoCollection(attachedTbPessoaProjetoCollection);
            em.persist(tbPessoa);
            if (tbTipopessoaHand != null) {
                tbTipopessoaHand.getTbPessoaCollection().add(tbPessoa);
                tbTipopessoaHand = em.merge(tbTipopessoaHand);
            }
            if (tbCidadesHand != null) {
                tbCidadesHand.getTbPessoaCollection().add(tbPessoa);
                tbCidadesHand = em.merge(tbCidadesHand);
            }
            for (TbPessoaProjeto tbPessoaProjetoCollectionTbPessoaProjeto : tbPessoa.getTbPessoaProjetoCollection()) {
                TbPessoa oldTbPessoaHandOfTbPessoaProjetoCollectionTbPessoaProjeto = tbPessoaProjetoCollectionTbPessoaProjeto.getTbPessoaHand();
                tbPessoaProjetoCollectionTbPessoaProjeto.setTbPessoaHand(tbPessoa);
                tbPessoaProjetoCollectionTbPessoaProjeto = em.merge(tbPessoaProjetoCollectionTbPessoaProjeto);
                if (oldTbPessoaHandOfTbPessoaProjetoCollectionTbPessoaProjeto != null) {
                    oldTbPessoaHandOfTbPessoaProjetoCollectionTbPessoaProjeto.getTbPessoaProjetoCollection().remove(tbPessoaProjetoCollectionTbPessoaProjeto);
                    oldTbPessoaHandOfTbPessoaProjetoCollectionTbPessoaProjeto = em.merge(oldTbPessoaHandOfTbPessoaProjetoCollectionTbPessoaProjeto);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTbPessoa(tbPessoa.getHand()) != null) {
                throw new PreexistingEntityException("TbPessoa " + tbPessoa + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TbPessoa tbPessoa) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbPessoa persistentTbPessoa = em.find(TbPessoa.class, tbPessoa.getHand());
            TbTipopessoa tbTipopessoaHandOld = persistentTbPessoa.getTbTipopessoaHand();
            TbTipopessoa tbTipopessoaHandNew = tbPessoa.getTbTipopessoaHand();
            TbCidades tbCidadesHandOld = persistentTbPessoa.getTbCidadesHand();
            TbCidades tbCidadesHandNew = tbPessoa.getTbCidadesHand();
            Collection<TbPessoaProjeto> tbPessoaProjetoCollectionOld = persistentTbPessoa.getTbPessoaProjetoCollection();
            Collection<TbPessoaProjeto> tbPessoaProjetoCollectionNew = tbPessoa.getTbPessoaProjetoCollection();
            List<String> illegalOrphanMessages = null;
            for (TbPessoaProjeto tbPessoaProjetoCollectionOldTbPessoaProjeto : tbPessoaProjetoCollectionOld) {
                if (!tbPessoaProjetoCollectionNew.contains(tbPessoaProjetoCollectionOldTbPessoaProjeto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TbPessoaProjeto " + tbPessoaProjetoCollectionOldTbPessoaProjeto + " since its tbPessoaHand field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tbTipopessoaHandNew != null) {
                tbTipopessoaHandNew = em.getReference(tbTipopessoaHandNew.getClass(), tbTipopessoaHandNew.getHand());
                tbPessoa.setTbTipopessoaHand(tbTipopessoaHandNew);
            }
            if (tbCidadesHandNew != null) {
                tbCidadesHandNew = em.getReference(tbCidadesHandNew.getClass(), tbCidadesHandNew.getHand());
                tbPessoa.setTbCidadesHand(tbCidadesHandNew);
            }
            Collection<TbPessoaProjeto> attachedTbPessoaProjetoCollectionNew = new ArrayList<TbPessoaProjeto>();
            for (TbPessoaProjeto tbPessoaProjetoCollectionNewTbPessoaProjetoToAttach : tbPessoaProjetoCollectionNew) {
                tbPessoaProjetoCollectionNewTbPessoaProjetoToAttach = em.getReference(tbPessoaProjetoCollectionNewTbPessoaProjetoToAttach.getClass(), tbPessoaProjetoCollectionNewTbPessoaProjetoToAttach.getHand());
                attachedTbPessoaProjetoCollectionNew.add(tbPessoaProjetoCollectionNewTbPessoaProjetoToAttach);
            }
            tbPessoaProjetoCollectionNew = attachedTbPessoaProjetoCollectionNew;
            tbPessoa.setTbPessoaProjetoCollection(tbPessoaProjetoCollectionNew);
            tbPessoa = em.merge(tbPessoa);
            if (tbTipopessoaHandOld != null && !tbTipopessoaHandOld.equals(tbTipopessoaHandNew)) {
                tbTipopessoaHandOld.getTbPessoaCollection().remove(tbPessoa);
                tbTipopessoaHandOld = em.merge(tbTipopessoaHandOld);
            }
            if (tbTipopessoaHandNew != null && !tbTipopessoaHandNew.equals(tbTipopessoaHandOld)) {
                tbTipopessoaHandNew.getTbPessoaCollection().add(tbPessoa);
                tbTipopessoaHandNew = em.merge(tbTipopessoaHandNew);
            }
            if (tbCidadesHandOld != null && !tbCidadesHandOld.equals(tbCidadesHandNew)) {
                tbCidadesHandOld.getTbPessoaCollection().remove(tbPessoa);
                tbCidadesHandOld = em.merge(tbCidadesHandOld);
            }
            if (tbCidadesHandNew != null && !tbCidadesHandNew.equals(tbCidadesHandOld)) {
                tbCidadesHandNew.getTbPessoaCollection().add(tbPessoa);
                tbCidadesHandNew = em.merge(tbCidadesHandNew);
            }
            for (TbPessoaProjeto tbPessoaProjetoCollectionNewTbPessoaProjeto : tbPessoaProjetoCollectionNew) {
                if (!tbPessoaProjetoCollectionOld.contains(tbPessoaProjetoCollectionNewTbPessoaProjeto)) {
                    TbPessoa oldTbPessoaHandOfTbPessoaProjetoCollectionNewTbPessoaProjeto = tbPessoaProjetoCollectionNewTbPessoaProjeto.getTbPessoaHand();
                    tbPessoaProjetoCollectionNewTbPessoaProjeto.setTbPessoaHand(tbPessoa);
                    tbPessoaProjetoCollectionNewTbPessoaProjeto = em.merge(tbPessoaProjetoCollectionNewTbPessoaProjeto);
                    if (oldTbPessoaHandOfTbPessoaProjetoCollectionNewTbPessoaProjeto != null && !oldTbPessoaHandOfTbPessoaProjetoCollectionNewTbPessoaProjeto.equals(tbPessoa)) {
                        oldTbPessoaHandOfTbPessoaProjetoCollectionNewTbPessoaProjeto.getTbPessoaProjetoCollection().remove(tbPessoaProjetoCollectionNewTbPessoaProjeto);
                        oldTbPessoaHandOfTbPessoaProjetoCollectionNewTbPessoaProjeto = em.merge(oldTbPessoaHandOfTbPessoaProjetoCollectionNewTbPessoaProjeto);
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
                Integer id = tbPessoa.getHand();
                if (findTbPessoa(id) == null) {
                    throw new NonexistentEntityException("The tbPessoa with id " + id + " no longer exists.");
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
            TbPessoa tbPessoa;
            try {
                tbPessoa = em.getReference(TbPessoa.class, id);
                tbPessoa.getHand();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tbPessoa with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TbPessoaProjeto> tbPessoaProjetoCollectionOrphanCheck = tbPessoa.getTbPessoaProjetoCollection();
            for (TbPessoaProjeto tbPessoaProjetoCollectionOrphanCheckTbPessoaProjeto : tbPessoaProjetoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbPessoa (" + tbPessoa + ") cannot be destroyed since the TbPessoaProjeto " + tbPessoaProjetoCollectionOrphanCheckTbPessoaProjeto + " in its tbPessoaProjetoCollection field has a non-nullable tbPessoaHand field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            TbTipopessoa tbTipopessoaHand = tbPessoa.getTbTipopessoaHand();
            if (tbTipopessoaHand != null) {
                tbTipopessoaHand.getTbPessoaCollection().remove(tbPessoa);
                tbTipopessoaHand = em.merge(tbTipopessoaHand);
            }
            TbCidades tbCidadesHand = tbPessoa.getTbCidadesHand();
            if (tbCidadesHand != null) {
                tbCidadesHand.getTbPessoaCollection().remove(tbPessoa);
                tbCidadesHand = em.merge(tbCidadesHand);
            }
            em.remove(tbPessoa);
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

    public List<TbPessoa> findTbPessoaEntities() {
        return findTbPessoaEntities(true, -1, -1);
    }

    public List<TbPessoa> findTbPessoaEntities(int maxResults, int firstResult) {
        return findTbPessoaEntities(false, maxResults, firstResult);
    }

    private List<TbPessoa> findTbPessoaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from TbPessoa as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public TbPessoa findTbPessoa(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbPessoa.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbPessoaCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from TbPessoa as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
