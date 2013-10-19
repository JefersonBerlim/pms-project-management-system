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
import Entidades.TbStatus;
import Entidades.TbFuncionarios;
import Entidades.TbOrdemServico;
import java.util.ArrayList;
import java.util.Collection;
import Entidades.TbProjetoMarcos;
import Entidades.TbPessoaProjeto;
import Entidades.TbProjetos;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;

/**
 *
 * @author berlim
 */
public class TbProjetosJpaController implements Serializable {

    public TbProjetosJpaController() {
        emf = Persistence.createEntityManagerFactory("ProjectManagementSystemPU");
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TbProjetos tbProjetos) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (tbProjetos.getTbOrdemServicoCollection() == null) {
            tbProjetos.setTbOrdemServicoCollection(new ArrayList<TbOrdemServico>());
        }
        if (tbProjetos.getTbProjetoMarcosCollection() == null) {
            tbProjetos.setTbProjetoMarcosCollection(new ArrayList<TbProjetoMarcos>());
        }
        if (tbProjetos.getTbPessoaProjetoCollection() == null) {
            tbProjetos.setTbPessoaProjetoCollection(new ArrayList<TbPessoaProjeto>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbStatus tbStatusHand = tbProjetos.getTbStatusHand();
            if (tbStatusHand != null) {
                tbStatusHand = em.getReference(tbStatusHand.getClass(), tbStatusHand.getHand());
                tbProjetos.setTbStatusHand(tbStatusHand);
            }
            TbFuncionarios tbFuncionariosHand = tbProjetos.getTbFuncionariosHand();
            if (tbFuncionariosHand != null) {
                tbFuncionariosHand = em.getReference(tbFuncionariosHand.getClass(), tbFuncionariosHand.getHand());
                tbProjetos.setTbFuncionariosHand(tbFuncionariosHand);
            }
            Collection<TbOrdemServico> attachedTbOrdemServicoCollection = new ArrayList<TbOrdemServico>();
            for (TbOrdemServico tbOrdemServicoCollectionTbOrdemServicoToAttach : tbProjetos.getTbOrdemServicoCollection()) {
                tbOrdemServicoCollectionTbOrdemServicoToAttach = em.getReference(tbOrdemServicoCollectionTbOrdemServicoToAttach.getClass(), tbOrdemServicoCollectionTbOrdemServicoToAttach.getHand());
                attachedTbOrdemServicoCollection.add(tbOrdemServicoCollectionTbOrdemServicoToAttach);
            }
            tbProjetos.setTbOrdemServicoCollection(attachedTbOrdemServicoCollection);
            Collection<TbProjetoMarcos> attachedTbProjetoMarcosCollection = new ArrayList<TbProjetoMarcos>();
            for (TbProjetoMarcos tbProjetoMarcosCollectionTbProjetoMarcosToAttach : tbProjetos.getTbProjetoMarcosCollection()) {
                tbProjetoMarcosCollectionTbProjetoMarcosToAttach = em.getReference(tbProjetoMarcosCollectionTbProjetoMarcosToAttach.getClass(), tbProjetoMarcosCollectionTbProjetoMarcosToAttach.getHand());
                attachedTbProjetoMarcosCollection.add(tbProjetoMarcosCollectionTbProjetoMarcosToAttach);
            }
            tbProjetos.setTbProjetoMarcosCollection(attachedTbProjetoMarcosCollection);
            Collection<TbPessoaProjeto> attachedTbPessoaProjetoCollection = new ArrayList<TbPessoaProjeto>();
            for (TbPessoaProjeto tbPessoaProjetoCollectionTbPessoaProjetoToAttach : tbProjetos.getTbPessoaProjetoCollection()) {
                tbPessoaProjetoCollectionTbPessoaProjetoToAttach = em.getReference(tbPessoaProjetoCollectionTbPessoaProjetoToAttach.getClass(), tbPessoaProjetoCollectionTbPessoaProjetoToAttach.getHand());
                attachedTbPessoaProjetoCollection.add(tbPessoaProjetoCollectionTbPessoaProjetoToAttach);
            }
            tbProjetos.setTbPessoaProjetoCollection(attachedTbPessoaProjetoCollection);
            em.persist(tbProjetos);
            if (tbStatusHand != null) {
                tbStatusHand.getTbProjetosCollection().add(tbProjetos);
                tbStatusHand = em.merge(tbStatusHand);
            }
            if (tbFuncionariosHand != null) {
                tbFuncionariosHand.getTbProjetosCollection().add(tbProjetos);
                tbFuncionariosHand = em.merge(tbFuncionariosHand);
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
            for (TbProjetoMarcos tbProjetoMarcosCollectionTbProjetoMarcos : tbProjetos.getTbProjetoMarcosCollection()) {
                TbProjetos oldTbProjetoHandOfTbProjetoMarcosCollectionTbProjetoMarcos = tbProjetoMarcosCollectionTbProjetoMarcos.getTbProjetoHand();
                tbProjetoMarcosCollectionTbProjetoMarcos.setTbProjetoHand(tbProjetos);
                tbProjetoMarcosCollectionTbProjetoMarcos = em.merge(tbProjetoMarcosCollectionTbProjetoMarcos);
                if (oldTbProjetoHandOfTbProjetoMarcosCollectionTbProjetoMarcos != null) {
                    oldTbProjetoHandOfTbProjetoMarcosCollectionTbProjetoMarcos.getTbProjetoMarcosCollection().remove(tbProjetoMarcosCollectionTbProjetoMarcos);
                    oldTbProjetoHandOfTbProjetoMarcosCollectionTbProjetoMarcos = em.merge(oldTbProjetoHandOfTbProjetoMarcosCollectionTbProjetoMarcos);
                }
            }
            for (TbPessoaProjeto tbPessoaProjetoCollectionTbPessoaProjeto : tbProjetos.getTbPessoaProjetoCollection()) {
                TbProjetos oldTbProjetoHandOfTbPessoaProjetoCollectionTbPessoaProjeto = tbPessoaProjetoCollectionTbPessoaProjeto.getTbProjetoHand();
                tbPessoaProjetoCollectionTbPessoaProjeto.setTbProjetoHand(tbProjetos);
                tbPessoaProjetoCollectionTbPessoaProjeto = em.merge(tbPessoaProjetoCollectionTbPessoaProjeto);
                if (oldTbProjetoHandOfTbPessoaProjetoCollectionTbPessoaProjeto != null) {
                    oldTbProjetoHandOfTbPessoaProjetoCollectionTbPessoaProjeto.getTbPessoaProjetoCollection().remove(tbPessoaProjetoCollectionTbPessoaProjeto);
                    oldTbProjetoHandOfTbPessoaProjetoCollectionTbPessoaProjeto = em.merge(oldTbProjetoHandOfTbPessoaProjetoCollectionTbPessoaProjeto);
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
            TbStatus tbStatusHandOld = persistentTbProjetos.getTbStatusHand();
            TbStatus tbStatusHandNew = tbProjetos.getTbStatusHand();
            TbFuncionarios tbFuncionariosHandOld = persistentTbProjetos.getTbFuncionariosHand();
            TbFuncionarios tbFuncionariosHandNew = tbProjetos.getTbFuncionariosHand();
            Collection<TbOrdemServico> tbOrdemServicoCollectionOld = persistentTbProjetos.getTbOrdemServicoCollection();
            Collection<TbOrdemServico> tbOrdemServicoCollectionNew = tbProjetos.getTbOrdemServicoCollection();
            Collection<TbProjetoMarcos> tbProjetoMarcosCollectionOld = persistentTbProjetos.getTbProjetoMarcosCollection();
            Collection<TbProjetoMarcos> tbProjetoMarcosCollectionNew = tbProjetos.getTbProjetoMarcosCollection();
            Collection<TbPessoaProjeto> tbPessoaProjetoCollectionOld = persistentTbProjetos.getTbPessoaProjetoCollection();
            Collection<TbPessoaProjeto> tbPessoaProjetoCollectionNew = tbProjetos.getTbPessoaProjetoCollection();
            List<String> illegalOrphanMessages = null;
            for (TbOrdemServico tbOrdemServicoCollectionOldTbOrdemServico : tbOrdemServicoCollectionOld) {
                if (!tbOrdemServicoCollectionNew.contains(tbOrdemServicoCollectionOldTbOrdemServico)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TbOrdemServico " + tbOrdemServicoCollectionOldTbOrdemServico + " since its tbProjetosHand field is not nullable.");
                }
            }
            for (TbProjetoMarcos tbProjetoMarcosCollectionOldTbProjetoMarcos : tbProjetoMarcosCollectionOld) {
                if (!tbProjetoMarcosCollectionNew.contains(tbProjetoMarcosCollectionOldTbProjetoMarcos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TbProjetoMarcos " + tbProjetoMarcosCollectionOldTbProjetoMarcos + " since its tbProjetoHand field is not nullable.");
                }
            }
            for (TbPessoaProjeto tbPessoaProjetoCollectionOldTbPessoaProjeto : tbPessoaProjetoCollectionOld) {
                if (!tbPessoaProjetoCollectionNew.contains(tbPessoaProjetoCollectionOldTbPessoaProjeto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TbPessoaProjeto " + tbPessoaProjetoCollectionOldTbPessoaProjeto + " since its tbProjetoHand field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tbStatusHandNew != null) {
                tbStatusHandNew = em.getReference(tbStatusHandNew.getClass(), tbStatusHandNew.getHand());
                tbProjetos.setTbStatusHand(tbStatusHandNew);
            }
            if (tbFuncionariosHandNew != null) {
                tbFuncionariosHandNew = em.getReference(tbFuncionariosHandNew.getClass(), tbFuncionariosHandNew.getHand());
                tbProjetos.setTbFuncionariosHand(tbFuncionariosHandNew);
            }
            Collection<TbOrdemServico> attachedTbOrdemServicoCollectionNew = new ArrayList<TbOrdemServico>();
            for (TbOrdemServico tbOrdemServicoCollectionNewTbOrdemServicoToAttach : tbOrdemServicoCollectionNew) {
                tbOrdemServicoCollectionNewTbOrdemServicoToAttach = em.getReference(tbOrdemServicoCollectionNewTbOrdemServicoToAttach.getClass(), tbOrdemServicoCollectionNewTbOrdemServicoToAttach.getHand());
                attachedTbOrdemServicoCollectionNew.add(tbOrdemServicoCollectionNewTbOrdemServicoToAttach);
            }
            tbOrdemServicoCollectionNew = attachedTbOrdemServicoCollectionNew;
            tbProjetos.setTbOrdemServicoCollection(tbOrdemServicoCollectionNew);
            Collection<TbProjetoMarcos> attachedTbProjetoMarcosCollectionNew = new ArrayList<TbProjetoMarcos>();
            for (TbProjetoMarcos tbProjetoMarcosCollectionNewTbProjetoMarcosToAttach : tbProjetoMarcosCollectionNew) {
                tbProjetoMarcosCollectionNewTbProjetoMarcosToAttach = em.getReference(tbProjetoMarcosCollectionNewTbProjetoMarcosToAttach.getClass(), tbProjetoMarcosCollectionNewTbProjetoMarcosToAttach.getHand());
                attachedTbProjetoMarcosCollectionNew.add(tbProjetoMarcosCollectionNewTbProjetoMarcosToAttach);
            }
            tbProjetoMarcosCollectionNew = attachedTbProjetoMarcosCollectionNew;
            tbProjetos.setTbProjetoMarcosCollection(tbProjetoMarcosCollectionNew);
            Collection<TbPessoaProjeto> attachedTbPessoaProjetoCollectionNew = new ArrayList<TbPessoaProjeto>();
            for (TbPessoaProjeto tbPessoaProjetoCollectionNewTbPessoaProjetoToAttach : tbPessoaProjetoCollectionNew) {
                tbPessoaProjetoCollectionNewTbPessoaProjetoToAttach = em.getReference(tbPessoaProjetoCollectionNewTbPessoaProjetoToAttach.getClass(), tbPessoaProjetoCollectionNewTbPessoaProjetoToAttach.getHand());
                attachedTbPessoaProjetoCollectionNew.add(tbPessoaProjetoCollectionNewTbPessoaProjetoToAttach);
            }
            tbPessoaProjetoCollectionNew = attachedTbPessoaProjetoCollectionNew;
            tbProjetos.setTbPessoaProjetoCollection(tbPessoaProjetoCollectionNew);
            tbProjetos = em.merge(tbProjetos);
            if (tbStatusHandOld != null && !tbStatusHandOld.equals(tbStatusHandNew)) {
                tbStatusHandOld.getTbProjetosCollection().remove(tbProjetos);
                tbStatusHandOld = em.merge(tbStatusHandOld);
            }
            if (tbStatusHandNew != null && !tbStatusHandNew.equals(tbStatusHandOld)) {
                tbStatusHandNew.getTbProjetosCollection().add(tbProjetos);
                tbStatusHandNew = em.merge(tbStatusHandNew);
            }
            if (tbFuncionariosHandOld != null && !tbFuncionariosHandOld.equals(tbFuncionariosHandNew)) {
                tbFuncionariosHandOld.getTbProjetosCollection().remove(tbProjetos);
                tbFuncionariosHandOld = em.merge(tbFuncionariosHandOld);
            }
            if (tbFuncionariosHandNew != null && !tbFuncionariosHandNew.equals(tbFuncionariosHandOld)) {
                tbFuncionariosHandNew.getTbProjetosCollection().add(tbProjetos);
                tbFuncionariosHandNew = em.merge(tbFuncionariosHandNew);
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
            for (TbPessoaProjeto tbPessoaProjetoCollectionNewTbPessoaProjeto : tbPessoaProjetoCollectionNew) {
                if (!tbPessoaProjetoCollectionOld.contains(tbPessoaProjetoCollectionNewTbPessoaProjeto)) {
                    TbProjetos oldTbProjetoHandOfTbPessoaProjetoCollectionNewTbPessoaProjeto = tbPessoaProjetoCollectionNewTbPessoaProjeto.getTbProjetoHand();
                    tbPessoaProjetoCollectionNewTbPessoaProjeto.setTbProjetoHand(tbProjetos);
                    tbPessoaProjetoCollectionNewTbPessoaProjeto = em.merge(tbPessoaProjetoCollectionNewTbPessoaProjeto);
                    if (oldTbProjetoHandOfTbPessoaProjetoCollectionNewTbPessoaProjeto != null && !oldTbProjetoHandOfTbPessoaProjetoCollectionNewTbPessoaProjeto.equals(tbProjetos)) {
                        oldTbProjetoHandOfTbPessoaProjetoCollectionNewTbPessoaProjeto.getTbPessoaProjetoCollection().remove(tbPessoaProjetoCollectionNewTbPessoaProjeto);
                        oldTbProjetoHandOfTbPessoaProjetoCollectionNewTbPessoaProjeto = em.merge(oldTbProjetoHandOfTbPessoaProjetoCollectionNewTbPessoaProjeto);
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
            Collection<TbOrdemServico> tbOrdemServicoCollectionOrphanCheck = tbProjetos.getTbOrdemServicoCollection();
            for (TbOrdemServico tbOrdemServicoCollectionOrphanCheckTbOrdemServico : tbOrdemServicoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbProjetos (" + tbProjetos + ") cannot be destroyed since the TbOrdemServico " + tbOrdemServicoCollectionOrphanCheckTbOrdemServico + " in its tbOrdemServicoCollection field has a non-nullable tbProjetosHand field.");
            }
            Collection<TbProjetoMarcos> tbProjetoMarcosCollectionOrphanCheck = tbProjetos.getTbProjetoMarcosCollection();
            for (TbProjetoMarcos tbProjetoMarcosCollectionOrphanCheckTbProjetoMarcos : tbProjetoMarcosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbProjetos (" + tbProjetos + ") cannot be destroyed since the TbProjetoMarcos " + tbProjetoMarcosCollectionOrphanCheckTbProjetoMarcos + " in its tbProjetoMarcosCollection field has a non-nullable tbProjetoHand field.");
            }
            Collection<TbPessoaProjeto> tbPessoaProjetoCollectionOrphanCheck = tbProjetos.getTbPessoaProjetoCollection();
            for (TbPessoaProjeto tbPessoaProjetoCollectionOrphanCheckTbPessoaProjeto : tbPessoaProjetoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbProjetos (" + tbProjetos + ") cannot be destroyed since the TbPessoaProjeto " + tbPessoaProjetoCollectionOrphanCheckTbPessoaProjeto + " in its tbPessoaProjetoCollection field has a non-nullable tbProjetoHand field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            TbStatus tbStatusHand = tbProjetos.getTbStatusHand();
            if (tbStatusHand != null) {
                tbStatusHand.getTbProjetosCollection().remove(tbProjetos);
                tbStatusHand = em.merge(tbStatusHand);
            }
            TbFuncionarios tbFuncionariosHand = tbProjetos.getTbFuncionariosHand();
            if (tbFuncionariosHand != null) {
                tbFuncionariosHand.getTbProjetosCollection().remove(tbProjetos);
                tbFuncionariosHand = em.merge(tbFuncionariosHand);
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
            Query q = em.createQuery("select object(o) from TbProjetos as o");
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
            Query q = em.createQuery("select count(o) from TbProjetos as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
