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
import Model.TbCidades;
import Model.TbPessoa;
import Model.TbTipopessoa;
import Model.TbProjetos;
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
public class TbPessoaJpaController implements Serializable {

    public TbPessoaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TbPessoa tbPessoa) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (tbPessoa.getTbProjetosCollection() == null) {
            tbPessoa.setTbProjetosCollection(new ArrayList<TbProjetos>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbCidades tbCidadesHand = tbPessoa.getTbCidadesHand();
            if (tbCidadesHand != null) {
                tbCidadesHand = em.getReference(tbCidadesHand.getClass(), tbCidadesHand.getHand());
                tbPessoa.setTbCidadesHand(tbCidadesHand);
            }
            TbTipopessoa tbTipopessoaHand = tbPessoa.getTbTipopessoaHand();
            if (tbTipopessoaHand != null) {
                tbTipopessoaHand = em.getReference(tbTipopessoaHand.getClass(), tbTipopessoaHand.getHand());
                tbPessoa.setTbTipopessoaHand(tbTipopessoaHand);
            }
            Collection<TbProjetos> attachedTbProjetosCollection = new ArrayList<TbProjetos>();
            for (TbProjetos tbProjetosCollectionTbProjetosToAttach : tbPessoa.getTbProjetosCollection()) {
                tbProjetosCollectionTbProjetosToAttach = em.getReference(tbProjetosCollectionTbProjetosToAttach.getClass(), tbProjetosCollectionTbProjetosToAttach.getHand());
                attachedTbProjetosCollection.add(tbProjetosCollectionTbProjetosToAttach);
            }
            tbPessoa.setTbProjetosCollection(attachedTbProjetosCollection);
            em.persist(tbPessoa);
            if (tbCidadesHand != null) {
                tbCidadesHand.getTbPessoaCollection().add(tbPessoa);
                tbCidadesHand = em.merge(tbCidadesHand);
            }
            if (tbTipopessoaHand != null) {
                tbTipopessoaHand.getTbPessoaCollection().add(tbPessoa);
                tbTipopessoaHand = em.merge(tbTipopessoaHand);
            }
            for (TbProjetos tbProjetosCollectionTbProjetos : tbPessoa.getTbProjetosCollection()) {
                TbPessoa oldTbPessoaHandOfTbProjetosCollectionTbProjetos = tbProjetosCollectionTbProjetos.getTbPessoaHand();
                tbProjetosCollectionTbProjetos.setTbPessoaHand(tbPessoa);
                tbProjetosCollectionTbProjetos = em.merge(tbProjetosCollectionTbProjetos);
                if (oldTbPessoaHandOfTbProjetosCollectionTbProjetos != null) {
                    oldTbPessoaHandOfTbProjetosCollectionTbProjetos.getTbProjetosCollection().remove(tbProjetosCollectionTbProjetos);
                    oldTbPessoaHandOfTbProjetosCollectionTbProjetos = em.merge(oldTbPessoaHandOfTbProjetosCollectionTbProjetos);
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
            TbCidades tbCidadesHandOld = persistentTbPessoa.getTbCidadesHand();
            TbCidades tbCidadesHandNew = tbPessoa.getTbCidadesHand();
            TbTipopessoa tbTipopessoaHandOld = persistentTbPessoa.getTbTipopessoaHand();
            TbTipopessoa tbTipopessoaHandNew = tbPessoa.getTbTipopessoaHand();
            Collection<TbProjetos> tbProjetosCollectionOld = persistentTbPessoa.getTbProjetosCollection();
            Collection<TbProjetos> tbProjetosCollectionNew = tbPessoa.getTbProjetosCollection();
            List<String> illegalOrphanMessages = null;
            for (TbProjetos tbProjetosCollectionOldTbProjetos : tbProjetosCollectionOld) {
                if (!tbProjetosCollectionNew.contains(tbProjetosCollectionOldTbProjetos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TbProjetos " + tbProjetosCollectionOldTbProjetos + " since its tbPessoaHand field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tbCidadesHandNew != null) {
                tbCidadesHandNew = em.getReference(tbCidadesHandNew.getClass(), tbCidadesHandNew.getHand());
                tbPessoa.setTbCidadesHand(tbCidadesHandNew);
            }
            if (tbTipopessoaHandNew != null) {
                tbTipopessoaHandNew = em.getReference(tbTipopessoaHandNew.getClass(), tbTipopessoaHandNew.getHand());
                tbPessoa.setTbTipopessoaHand(tbTipopessoaHandNew);
            }
            Collection<TbProjetos> attachedTbProjetosCollectionNew = new ArrayList<TbProjetos>();
            for (TbProjetos tbProjetosCollectionNewTbProjetosToAttach : tbProjetosCollectionNew) {
                tbProjetosCollectionNewTbProjetosToAttach = em.getReference(tbProjetosCollectionNewTbProjetosToAttach.getClass(), tbProjetosCollectionNewTbProjetosToAttach.getHand());
                attachedTbProjetosCollectionNew.add(tbProjetosCollectionNewTbProjetosToAttach);
            }
            tbProjetosCollectionNew = attachedTbProjetosCollectionNew;
            tbPessoa.setTbProjetosCollection(tbProjetosCollectionNew);
            tbPessoa = em.merge(tbPessoa);
            if (tbCidadesHandOld != null && !tbCidadesHandOld.equals(tbCidadesHandNew)) {
                tbCidadesHandOld.getTbPessoaCollection().remove(tbPessoa);
                tbCidadesHandOld = em.merge(tbCidadesHandOld);
            }
            if (tbCidadesHandNew != null && !tbCidadesHandNew.equals(tbCidadesHandOld)) {
                tbCidadesHandNew.getTbPessoaCollection().add(tbPessoa);
                tbCidadesHandNew = em.merge(tbCidadesHandNew);
            }
            if (tbTipopessoaHandOld != null && !tbTipopessoaHandOld.equals(tbTipopessoaHandNew)) {
                tbTipopessoaHandOld.getTbPessoaCollection().remove(tbPessoa);
                tbTipopessoaHandOld = em.merge(tbTipopessoaHandOld);
            }
            if (tbTipopessoaHandNew != null && !tbTipopessoaHandNew.equals(tbTipopessoaHandOld)) {
                tbTipopessoaHandNew.getTbPessoaCollection().add(tbPessoa);
                tbTipopessoaHandNew = em.merge(tbTipopessoaHandNew);
            }
            for (TbProjetos tbProjetosCollectionNewTbProjetos : tbProjetosCollectionNew) {
                if (!tbProjetosCollectionOld.contains(tbProjetosCollectionNewTbProjetos)) {
                    TbPessoa oldTbPessoaHandOfTbProjetosCollectionNewTbProjetos = tbProjetosCollectionNewTbProjetos.getTbPessoaHand();
                    tbProjetosCollectionNewTbProjetos.setTbPessoaHand(tbPessoa);
                    tbProjetosCollectionNewTbProjetos = em.merge(tbProjetosCollectionNewTbProjetos);
                    if (oldTbPessoaHandOfTbProjetosCollectionNewTbProjetos != null && !oldTbPessoaHandOfTbProjetosCollectionNewTbProjetos.equals(tbPessoa)) {
                        oldTbPessoaHandOfTbProjetosCollectionNewTbProjetos.getTbProjetosCollection().remove(tbProjetosCollectionNewTbProjetos);
                        oldTbPessoaHandOfTbProjetosCollectionNewTbProjetos = em.merge(oldTbPessoaHandOfTbProjetosCollectionNewTbProjetos);
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
            Collection<TbProjetos> tbProjetosCollectionOrphanCheck = tbPessoa.getTbProjetosCollection();
            for (TbProjetos tbProjetosCollectionOrphanCheckTbProjetos : tbProjetosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbPessoa (" + tbPessoa + ") cannot be destroyed since the TbProjetos " + tbProjetosCollectionOrphanCheckTbProjetos + " in its tbProjetosCollection field has a non-nullable tbPessoaHand field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            TbCidades tbCidadesHand = tbPessoa.getTbCidadesHand();
            if (tbCidadesHand != null) {
                tbCidadesHand.getTbPessoaCollection().remove(tbPessoa);
                tbCidadesHand = em.merge(tbCidadesHand);
            }
            TbTipopessoa tbTipopessoaHand = tbPessoa.getTbTipopessoaHand();
            if (tbTipopessoaHand != null) {
                tbTipopessoaHand.getTbPessoaCollection().remove(tbPessoa);
                tbTipopessoaHand = em.merge(tbTipopessoaHand);
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
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TbPessoa.class));
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
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TbPessoa> rt = cq.from(TbPessoa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
