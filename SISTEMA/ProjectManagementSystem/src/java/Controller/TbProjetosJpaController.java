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
import Model.TbPessoa;
import Model.TbStatus;
import Model.TbProjetoMarcos;
import java.util.ArrayList;
import java.util.Collection;
import Model.TbOrdemServico;
import Model.TbProjetos;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author BERLIM
 */
public class TbProjetosJpaController implements Serializable {

    public TbProjetosJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TbProjetos tbProjetos) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (tbProjetos.getTbProjetoMarcosCollection() == null) {
            tbProjetos.setTbProjetoMarcosCollection(new ArrayList<TbProjetoMarcos>());
        }
        if (tbProjetos.getTbOrdemServicoCollection() == null) {
            tbProjetos.setTbOrdemServicoCollection(new ArrayList<TbOrdemServico>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbFuncionarios tbFuncionariosHand = tbProjetos.getTbFuncionariosHand();
            if (tbFuncionariosHand != null) {
                tbFuncionariosHand = em.getReference(tbFuncionariosHand.getClass(), tbFuncionariosHand.getHand());
                tbProjetos.setTbFuncionariosHand(tbFuncionariosHand);
            }
            TbPessoa tbPessoaHand = tbProjetos.getTbPessoaHand();
            if (tbPessoaHand != null) {
                tbPessoaHand = em.getReference(tbPessoaHand.getClass(), tbPessoaHand.getHand());
                tbProjetos.setTbPessoaHand(tbPessoaHand);
            }
            TbStatus tbStatusHand = tbProjetos.getTbStatusHand();
            if (tbStatusHand != null) {
                tbStatusHand = em.getReference(tbStatusHand.getClass(), tbStatusHand.getHand());
                tbProjetos.setTbStatusHand(tbStatusHand);
            }
            Collection<TbProjetoMarcos> attachedTbProjetoMarcosCollection = new ArrayList<TbProjetoMarcos>();
            for (TbProjetoMarcos tbProjetoMarcosCollectionTbProjetoMarcosToAttach : tbProjetos.getTbProjetoMarcosCollection()) {
                tbProjetoMarcosCollectionTbProjetoMarcosToAttach = em.getReference(tbProjetoMarcosCollectionTbProjetoMarcosToAttach.getClass(), tbProjetoMarcosCollectionTbProjetoMarcosToAttach.getHand());
                attachedTbProjetoMarcosCollection.add(tbProjetoMarcosCollectionTbProjetoMarcosToAttach);
            }
            tbProjetos.setTbProjetoMarcosCollection(attachedTbProjetoMarcosCollection);
            Collection<TbOrdemServico> attachedTbOrdemServicoCollection = new ArrayList<TbOrdemServico>();
            for (TbOrdemServico tbOrdemServicoCollectionTbOrdemServicoToAttach : tbProjetos.getTbOrdemServicoCollection()) {
                tbOrdemServicoCollectionTbOrdemServicoToAttach = em.getReference(tbOrdemServicoCollectionTbOrdemServicoToAttach.getClass(), tbOrdemServicoCollectionTbOrdemServicoToAttach.getHand());
                attachedTbOrdemServicoCollection.add(tbOrdemServicoCollectionTbOrdemServicoToAttach);
            }
            tbProjetos.setTbOrdemServicoCollection(attachedTbOrdemServicoCollection);
            em.persist(tbProjetos);
            if (tbFuncionariosHand != null) {
                tbFuncionariosHand.getTbProjetosCollection().add(tbProjetos);
                tbFuncionariosHand = em.merge(tbFuncionariosHand);
            }
            if (tbPessoaHand != null) {
                tbPessoaHand.getTbProjetosCollection().add(tbProjetos);
                tbPessoaHand = em.merge(tbPessoaHand);
            }
            if (tbStatusHand != null) {
                tbStatusHand.getTbProjetosCollection().add(tbProjetos);
                tbStatusHand = em.merge(tbStatusHand);
            }
            for (TbProjetoMarcos tbProjetoMarcosCollectionTbProjetoMarcos : tbProjetos.getTbProjetoMarcosCollection()) {
                TbProjetos oldTbProjetoHandOfTbProjetoMarcosCollectionTbProjetoMarcos = tbProjetoMarcosCollectionTbProjetoMarcos.getTbProjetoHand();
                tbProjetoMarcosCollectionTbProjetoMarcos.setTbProjetoHand(tbProjetos);
                tbProjetoMarcosCollectionTbProjetoMarcos = em.merge(tbProjetoMarcosCollectionTbProjetoMarcos);
                if (oldTbProjetoHandOfTbProjetoMarcosCollectionTbProjetoMarcos != null) {
                    oldTbProjetoHandOfTbProjetoMarcosCollectionTbProjetoMarcos.getTbProjetoMarcosCollection().remove(tbProjetoMarcosCollectionTbProjetoMarcos);
                    oldTbProjetoHandOfTbProjetoMarcosCollectionTbProjetoMarcos = em.merge(oldTbProjetoHandOfTbProjetoMarcosCollectionTbProjetoMarcos);
                }
            }
            for (TbOrdemServico tbOrdemServicoCollectionTbOrdemServico : tbProjetos.getTbOrdemServicoCollection()) {
                TbProjetos oldTbProjetosHandOfTbOrdemServicoCollectionTbOrdemServico = tbOrdemServicoCollectionTbOrdemServico.getTbProjetosHand();
                tbOrdemServicoCollectionTbOrdemServico.setTbProjetosHand(tbProjetos);
                tbOrdemServicoCollectionTbOrdemServico = em.merge(tbOrdemServicoCollectionTbOrdemServico);
                if (oldTbProjetosHandOfTbOrdemServicoCollectionTbOrdemServico != null) {
                    oldTbProjetosHandOfTbOrdemServicoCollectionTbOrdemServico.getTbOrdemServicoCollection().remove(tbOrdemServicoCollectionTbOrdemServico);
                    oldTbProjetosHandOfTbOrdemServicoCollectionTbOrdemServico = em.merge(oldTbProjetosHandOfTbOrdemServicoCollectionTbOrdemServico);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTbProjetos(tbProjetos.getHand()) != null) {
                throw new PreexistingEntityException("TbProjetos " + tbProjetos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TbProjetos tbProjetos) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbProjetos persistentTbProjetos = em.find(TbProjetos.class, tbProjetos.getHand());
            TbFuncionarios tbFuncionariosHandOld = persistentTbProjetos.getTbFuncionariosHand();
            TbFuncionarios tbFuncionariosHandNew = tbProjetos.getTbFuncionariosHand();
            TbPessoa tbPessoaHandOld = persistentTbProjetos.getTbPessoaHand();
            TbPessoa tbPessoaHandNew = tbProjetos.getTbPessoaHand();
            TbStatus tbStatusHandOld = persistentTbProjetos.getTbStatusHand();
            TbStatus tbStatusHandNew = tbProjetos.getTbStatusHand();
            Collection<TbProjetoMarcos> tbProjetoMarcosCollectionOld = persistentTbProjetos.getTbProjetoMarcosCollection();
            Collection<TbProjetoMarcos> tbProjetoMarcosCollectionNew = tbProjetos.getTbProjetoMarcosCollection();
            Collection<TbOrdemServico> tbOrdemServicoCollectionOld = persistentTbProjetos.getTbOrdemServicoCollection();
            Collection<TbOrdemServico> tbOrdemServicoCollectionNew = tbProjetos.getTbOrdemServicoCollection();
            List<String> illegalOrphanMessages = null;
            for (TbProjetoMarcos tbProjetoMarcosCollectionOldTbProjetoMarcos : tbProjetoMarcosCollectionOld) {
                if (!tbProjetoMarcosCollectionNew.contains(tbProjetoMarcosCollectionOldTbProjetoMarcos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TbProjetoMarcos " + tbProjetoMarcosCollectionOldTbProjetoMarcos + " since its tbProjetoHand field is not nullable.");
                }
            }
            for (TbOrdemServico tbOrdemServicoCollectionOldTbOrdemServico : tbOrdemServicoCollectionOld) {
                if (!tbOrdemServicoCollectionNew.contains(tbOrdemServicoCollectionOldTbOrdemServico)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TbOrdemServico " + tbOrdemServicoCollectionOldTbOrdemServico + " since its tbProjetosHand field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tbFuncionariosHandNew != null) {
                tbFuncionariosHandNew = em.getReference(tbFuncionariosHandNew.getClass(), tbFuncionariosHandNew.getHand());
                tbProjetos.setTbFuncionariosHand(tbFuncionariosHandNew);
            }
            if (tbPessoaHandNew != null) {
                tbPessoaHandNew = em.getReference(tbPessoaHandNew.getClass(), tbPessoaHandNew.getHand());
                tbProjetos.setTbPessoaHand(tbPessoaHandNew);
            }
            if (tbStatusHandNew != null) {
                tbStatusHandNew = em.getReference(tbStatusHandNew.getClass(), tbStatusHandNew.getHand());
                tbProjetos.setTbStatusHand(tbStatusHandNew);
            }
            Collection<TbProjetoMarcos> attachedTbProjetoMarcosCollectionNew = new ArrayList<TbProjetoMarcos>();
            for (TbProjetoMarcos tbProjetoMarcosCollectionNewTbProjetoMarcosToAttach : tbProjetoMarcosCollectionNew) {
                tbProjetoMarcosCollectionNewTbProjetoMarcosToAttach = em.getReference(tbProjetoMarcosCollectionNewTbProjetoMarcosToAttach.getClass(), tbProjetoMarcosCollectionNewTbProjetoMarcosToAttach.getHand());
                attachedTbProjetoMarcosCollectionNew.add(tbProjetoMarcosCollectionNewTbProjetoMarcosToAttach);
            }
            tbProjetoMarcosCollectionNew = attachedTbProjetoMarcosCollectionNew;
            tbProjetos.setTbProjetoMarcosCollection(tbProjetoMarcosCollectionNew);
            Collection<TbOrdemServico> attachedTbOrdemServicoCollectionNew = new ArrayList<TbOrdemServico>();
            for (TbOrdemServico tbOrdemServicoCollectionNewTbOrdemServicoToAttach : tbOrdemServicoCollectionNew) {
                tbOrdemServicoCollectionNewTbOrdemServicoToAttach = em.getReference(tbOrdemServicoCollectionNewTbOrdemServicoToAttach.getClass(), tbOrdemServicoCollectionNewTbOrdemServicoToAttach.getHand());
                attachedTbOrdemServicoCollectionNew.add(tbOrdemServicoCollectionNewTbOrdemServicoToAttach);
            }
            tbOrdemServicoCollectionNew = attachedTbOrdemServicoCollectionNew;
            tbProjetos.setTbOrdemServicoCollection(tbOrdemServicoCollectionNew);
            tbProjetos = em.merge(tbProjetos);
            if (tbFuncionariosHandOld != null && !tbFuncionariosHandOld.equals(tbFuncionariosHandNew)) {
                tbFuncionariosHandOld.getTbProjetosCollection().remove(tbProjetos);
                tbFuncionariosHandOld = em.merge(tbFuncionariosHandOld);
            }
            if (tbFuncionariosHandNew != null && !tbFuncionariosHandNew.equals(tbFuncionariosHandOld)) {
                tbFuncionariosHandNew.getTbProjetosCollection().add(tbProjetos);
                tbFuncionariosHandNew = em.merge(tbFuncionariosHandNew);
            }
            if (tbPessoaHandOld != null && !tbPessoaHandOld.equals(tbPessoaHandNew)) {
                tbPessoaHandOld.getTbProjetosCollection().remove(tbProjetos);
                tbPessoaHandOld = em.merge(tbPessoaHandOld);
            }
            if (tbPessoaHandNew != null && !tbPessoaHandNew.equals(tbPessoaHandOld)) {
                tbPessoaHandNew.getTbProjetosCollection().add(tbProjetos);
                tbPessoaHandNew = em.merge(tbPessoaHandNew);
            }
            if (tbStatusHandOld != null && !tbStatusHandOld.equals(tbStatusHandNew)) {
                tbStatusHandOld.getTbProjetosCollection().remove(tbProjetos);
                tbStatusHandOld = em.merge(tbStatusHandOld);
            }
            if (tbStatusHandNew != null && !tbStatusHandNew.equals(tbStatusHandOld)) {
                tbStatusHandNew.getTbProjetosCollection().add(tbProjetos);
                tbStatusHandNew = em.merge(tbStatusHandNew);
            }
            for (TbProjetoMarcos tbProjetoMarcosCollectionNewTbProjetoMarcos : tbProjetoMarcosCollectionNew) {
                if (!tbProjetoMarcosCollectionOld.contains(tbProjetoMarcosCollectionNewTbProjetoMarcos)) {
                    TbProjetos oldTbProjetoHandOfTbProjetoMarcosCollectionNewTbProjetoMarcos = tbProjetoMarcosCollectionNewTbProjetoMarcos.getTbProjetoHand();
                    tbProjetoMarcosCollectionNewTbProjetoMarcos.setTbProjetoHand(tbProjetos);
                    tbProjetoMarcosCollectionNewTbProjetoMarcos = em.merge(tbProjetoMarcosCollectionNewTbProjetoMarcos);
                    if (oldTbProjetoHandOfTbProjetoMarcosCollectionNewTbProjetoMarcos != null && !oldTbProjetoHandOfTbProjetoMarcosCollectionNewTbProjetoMarcos.equals(tbProjetos)) {
                        oldTbProjetoHandOfTbProjetoMarcosCollectionNewTbProjetoMarcos.getTbProjetoMarcosCollection().remove(tbProjetoMarcosCollectionNewTbProjetoMarcos);
                        oldTbProjetoHandOfTbProjetoMarcosCollectionNewTbProjetoMarcos = em.merge(oldTbProjetoHandOfTbProjetoMarcosCollectionNewTbProjetoMarcos);
                    }
                }
            }
            for (TbOrdemServico tbOrdemServicoCollectionNewTbOrdemServico : tbOrdemServicoCollectionNew) {
                if (!tbOrdemServicoCollectionOld.contains(tbOrdemServicoCollectionNewTbOrdemServico)) {
                    TbProjetos oldTbProjetosHandOfTbOrdemServicoCollectionNewTbOrdemServico = tbOrdemServicoCollectionNewTbOrdemServico.getTbProjetosHand();
                    tbOrdemServicoCollectionNewTbOrdemServico.setTbProjetosHand(tbProjetos);
                    tbOrdemServicoCollectionNewTbOrdemServico = em.merge(tbOrdemServicoCollectionNewTbOrdemServico);
                    if (oldTbProjetosHandOfTbOrdemServicoCollectionNewTbOrdemServico != null && !oldTbProjetosHandOfTbOrdemServicoCollectionNewTbOrdemServico.equals(tbProjetos)) {
                        oldTbProjetosHandOfTbOrdemServicoCollectionNewTbOrdemServico.getTbOrdemServicoCollection().remove(tbOrdemServicoCollectionNewTbOrdemServico);
                        oldTbProjetosHandOfTbOrdemServicoCollectionNewTbOrdemServico = em.merge(oldTbProjetosHandOfTbOrdemServicoCollectionNewTbOrdemServico);
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
                Integer id = tbProjetos.getHand();
                if (findTbProjetos(id) == null) {
                    throw new NonexistentEntityException("The tbProjetos with id " + id + " no longer exists.");
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
            TbProjetos tbProjetos;
            try {
                tbProjetos = em.getReference(TbProjetos.class, id);
                tbProjetos.getHand();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tbProjetos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TbProjetoMarcos> tbProjetoMarcosCollectionOrphanCheck = tbProjetos.getTbProjetoMarcosCollection();
            for (TbProjetoMarcos tbProjetoMarcosCollectionOrphanCheckTbProjetoMarcos : tbProjetoMarcosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbProjetos (" + tbProjetos + ") cannot be destroyed since the TbProjetoMarcos " + tbProjetoMarcosCollectionOrphanCheckTbProjetoMarcos + " in its tbProjetoMarcosCollection field has a non-nullable tbProjetoHand field.");
            }
            Collection<TbOrdemServico> tbOrdemServicoCollectionOrphanCheck = tbProjetos.getTbOrdemServicoCollection();
            for (TbOrdemServico tbOrdemServicoCollectionOrphanCheckTbOrdemServico : tbOrdemServicoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbProjetos (" + tbProjetos + ") cannot be destroyed since the TbOrdemServico " + tbOrdemServicoCollectionOrphanCheckTbOrdemServico + " in its tbOrdemServicoCollection field has a non-nullable tbProjetosHand field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            TbFuncionarios tbFuncionariosHand = tbProjetos.getTbFuncionariosHand();
            if (tbFuncionariosHand != null) {
                tbFuncionariosHand.getTbProjetosCollection().remove(tbProjetos);
                tbFuncionariosHand = em.merge(tbFuncionariosHand);
            }
            TbPessoa tbPessoaHand = tbProjetos.getTbPessoaHand();
            if (tbPessoaHand != null) {
                tbPessoaHand.getTbProjetosCollection().remove(tbProjetos);
                tbPessoaHand = em.merge(tbPessoaHand);
            }
            TbStatus tbStatusHand = tbProjetos.getTbStatusHand();
            if (tbStatusHand != null) {
                tbStatusHand.getTbProjetosCollection().remove(tbProjetos);
                tbStatusHand = em.merge(tbStatusHand);
            }
            em.remove(tbProjetos);
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

    public List<TbProjetos> findTbProjetosEntities() {
        return findTbProjetosEntities(true, -1, -1);
    }

    public List<TbProjetos> findTbProjetosEntities(int maxResults, int firstResult) {
        return findTbProjetosEntities(false, maxResults, firstResult);
    }

    private List<TbProjetos> findTbProjetosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TbProjetos.class));
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

    public TbProjetos findTbProjetos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbProjetos.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbProjetosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TbProjetos> rt = cq.from(TbProjetos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
