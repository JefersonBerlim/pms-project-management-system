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
import Entidades.TbOsServico;
import java.util.ArrayList;
import java.util.Collection;
import Entidades.TbProjetos;
import Entidades.TbStatus;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;

/**
 *
 * @author berlim
 */
public class TbStatusJpaController implements Serializable {

    public TbStatusJpaController() {
        emf = Persistence.createEntityManagerFactory("ProjectManagementSystemPU");
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TbStatus tbStatus) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (tbStatus.getTbOsServicoCollection() == null) {
            tbStatus.setTbOsServicoCollection(new ArrayList<TbOsServico>());
        }
        if (tbStatus.getTbProjetosCollection() == null) {
            tbStatus.setTbProjetosCollection(new ArrayList<TbProjetos>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<TbOsServico> attachedTbOsServicoCollection = new ArrayList<TbOsServico>();
            for (TbOsServico tbOsServicoCollectionTbOsServicoToAttach : tbStatus.getTbOsServicoCollection()) {
                tbOsServicoCollectionTbOsServicoToAttach = em.getReference(tbOsServicoCollectionTbOsServicoToAttach.getClass(), tbOsServicoCollectionTbOsServicoToAttach.getHand());
                attachedTbOsServicoCollection.add(tbOsServicoCollectionTbOsServicoToAttach);
            }
            tbStatus.setTbOsServicoCollection(attachedTbOsServicoCollection);
            Collection<TbProjetos> attachedTbProjetosCollection = new ArrayList<TbProjetos>();
            for (TbProjetos tbProjetosCollectionTbProjetosToAttach : tbStatus.getTbProjetosCollection()) {
                tbProjetosCollectionTbProjetosToAttach = em.getReference(tbProjetosCollectionTbProjetosToAttach.getClass(), tbProjetosCollectionTbProjetosToAttach.getHand());
                attachedTbProjetosCollection.add(tbProjetosCollectionTbProjetosToAttach);
            }
            tbStatus.setTbProjetosCollection(attachedTbProjetosCollection);
            em.persist(tbStatus);
            for (TbOsServico tbOsServicoCollectionTbOsServico : tbStatus.getTbOsServicoCollection()) {
                TbStatus oldTbStatusHandOfTbOsServicoCollectionTbOsServico = tbOsServicoCollectionTbOsServico.getTbStatusHand();
                tbOsServicoCollectionTbOsServico.setTbStatusHand(tbStatus);
                tbOsServicoCollectionTbOsServico = em.merge(tbOsServicoCollectionTbOsServico);
                if (oldTbStatusHandOfTbOsServicoCollectionTbOsServico != null) {
                    oldTbStatusHandOfTbOsServicoCollectionTbOsServico.getTbOsServicoCollection().remove(tbOsServicoCollectionTbOsServico);
                    oldTbStatusHandOfTbOsServicoCollectionTbOsServico = em.merge(oldTbStatusHandOfTbOsServicoCollectionTbOsServico);
                }
            }
            for (TbProjetos tbProjetosCollectionTbProjetos : tbStatus.getTbProjetosCollection()) {
                TbStatus oldTbStatusHandOfTbProjetosCollectionTbProjetos = tbProjetosCollectionTbProjetos.getTbStatusHand();
                tbProjetosCollectionTbProjetos.setTbStatusHand(tbStatus);
                tbProjetosCollectionTbProjetos = em.merge(tbProjetosCollectionTbProjetos);
                if (oldTbStatusHandOfTbProjetosCollectionTbProjetos != null) {
                    oldTbStatusHandOfTbProjetosCollectionTbProjetos.getTbProjetosCollection().remove(tbProjetosCollectionTbProjetos);
                    oldTbStatusHandOfTbProjetosCollectionTbProjetos = em.merge(oldTbStatusHandOfTbProjetosCollectionTbProjetos);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTbStatus(tbStatus.getHand()) != null) {
                throw new PreexistingEntityException("TbStatus " + tbStatus + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TbStatus tbStatus) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbStatus persistentTbStatus = em.find(TbStatus.class, tbStatus.getHand());
            Collection<TbOsServico> tbOsServicoCollectionOld = persistentTbStatus.getTbOsServicoCollection();
            Collection<TbOsServico> tbOsServicoCollectionNew = tbStatus.getTbOsServicoCollection();
            Collection<TbProjetos> tbProjetosCollectionOld = persistentTbStatus.getTbProjetosCollection();
            Collection<TbProjetos> tbProjetosCollectionNew = tbStatus.getTbProjetosCollection();
            List<String> illegalOrphanMessages = null;
            for (TbOsServico tbOsServicoCollectionOldTbOsServico : tbOsServicoCollectionOld) {
                if (!tbOsServicoCollectionNew.contains(tbOsServicoCollectionOldTbOsServico)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TbOsServico " + tbOsServicoCollectionOldTbOsServico + " since its tbStatusHand field is not nullable.");
                }
            }
            for (TbProjetos tbProjetosCollectionOldTbProjetos : tbProjetosCollectionOld) {
                if (!tbProjetosCollectionNew.contains(tbProjetosCollectionOldTbProjetos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TbProjetos " + tbProjetosCollectionOldTbProjetos + " since its tbStatusHand field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<TbOsServico> attachedTbOsServicoCollectionNew = new ArrayList<TbOsServico>();
            for (TbOsServico tbOsServicoCollectionNewTbOsServicoToAttach : tbOsServicoCollectionNew) {
                tbOsServicoCollectionNewTbOsServicoToAttach = em.getReference(tbOsServicoCollectionNewTbOsServicoToAttach.getClass(), tbOsServicoCollectionNewTbOsServicoToAttach.getHand());
                attachedTbOsServicoCollectionNew.add(tbOsServicoCollectionNewTbOsServicoToAttach);
            }
            tbOsServicoCollectionNew = attachedTbOsServicoCollectionNew;
            tbStatus.setTbOsServicoCollection(tbOsServicoCollectionNew);
            Collection<TbProjetos> attachedTbProjetosCollectionNew = new ArrayList<TbProjetos>();
            for (TbProjetos tbProjetosCollectionNewTbProjetosToAttach : tbProjetosCollectionNew) {
                tbProjetosCollectionNewTbProjetosToAttach = em.getReference(tbProjetosCollectionNewTbProjetosToAttach.getClass(), tbProjetosCollectionNewTbProjetosToAttach.getHand());
                attachedTbProjetosCollectionNew.add(tbProjetosCollectionNewTbProjetosToAttach);
            }
            tbProjetosCollectionNew = attachedTbProjetosCollectionNew;
            tbStatus.setTbProjetosCollection(tbProjetosCollectionNew);
            tbStatus = em.merge(tbStatus);
            for (TbOsServico tbOsServicoCollectionNewTbOsServico : tbOsServicoCollectionNew) {
                if (!tbOsServicoCollectionOld.contains(tbOsServicoCollectionNewTbOsServico)) {
                    TbStatus oldTbStatusHandOfTbOsServicoCollectionNewTbOsServico = tbOsServicoCollectionNewTbOsServico.getTbStatusHand();
                    tbOsServicoCollectionNewTbOsServico.setTbStatusHand(tbStatus);
                    tbOsServicoCollectionNewTbOsServico = em.merge(tbOsServicoCollectionNewTbOsServico);
                    if (oldTbStatusHandOfTbOsServicoCollectionNewTbOsServico != null && !oldTbStatusHandOfTbOsServicoCollectionNewTbOsServico.equals(tbStatus)) {
                        oldTbStatusHandOfTbOsServicoCollectionNewTbOsServico.getTbOsServicoCollection().remove(tbOsServicoCollectionNewTbOsServico);
                        oldTbStatusHandOfTbOsServicoCollectionNewTbOsServico = em.merge(oldTbStatusHandOfTbOsServicoCollectionNewTbOsServico);
                    }
                }
            }
            for (TbProjetos tbProjetosCollectionNewTbProjetos : tbProjetosCollectionNew) {
                if (!tbProjetosCollectionOld.contains(tbProjetosCollectionNewTbProjetos)) {
                    TbStatus oldTbStatusHandOfTbProjetosCollectionNewTbProjetos = tbProjetosCollectionNewTbProjetos.getTbStatusHand();
                    tbProjetosCollectionNewTbProjetos.setTbStatusHand(tbStatus);
                    tbProjetosCollectionNewTbProjetos = em.merge(tbProjetosCollectionNewTbProjetos);
                    if (oldTbStatusHandOfTbProjetosCollectionNewTbProjetos != null && !oldTbStatusHandOfTbProjetosCollectionNewTbProjetos.equals(tbStatus)) {
                        oldTbStatusHandOfTbProjetosCollectionNewTbProjetos.getTbProjetosCollection().remove(tbProjetosCollectionNewTbProjetos);
                        oldTbStatusHandOfTbProjetosCollectionNewTbProjetos = em.merge(oldTbStatusHandOfTbProjetosCollectionNewTbProjetos);
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
                Integer id = tbStatus.getHand();
                if (findTbStatus(id) == null) {
                    throw new NonexistentEntityException("The tbStatus with id " + id + " no longer exists.");
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
            TbStatus tbStatus;
            try {
                tbStatus = em.getReference(TbStatus.class, id);
                tbStatus.getHand();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tbStatus with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TbOsServico> tbOsServicoCollectionOrphanCheck = tbStatus.getTbOsServicoCollection();
            for (TbOsServico tbOsServicoCollectionOrphanCheckTbOsServico : tbOsServicoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbStatus (" + tbStatus + ") cannot be destroyed since the TbOsServico " + tbOsServicoCollectionOrphanCheckTbOsServico + " in its tbOsServicoCollection field has a non-nullable tbStatusHand field.");
            }
            Collection<TbProjetos> tbProjetosCollectionOrphanCheck = tbStatus.getTbProjetosCollection();
            for (TbProjetos tbProjetosCollectionOrphanCheckTbProjetos : tbProjetosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbStatus (" + tbStatus + ") cannot be destroyed since the TbProjetos " + tbProjetosCollectionOrphanCheckTbProjetos + " in its tbProjetosCollection field has a non-nullable tbStatusHand field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tbStatus);
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

    public List<TbStatus> findTbStatusEntities() {
        return findTbStatusEntities(true, -1, -1);
    }

    public List<TbStatus> findTbStatusEntities(int maxResults, int firstResult) {
        return findTbStatusEntities(false, maxResults, firstResult);
    }

    private List<TbStatus> findTbStatusEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from TbStatus as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public TbStatus findTbStatus(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbStatus.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbStatusCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from TbStatus as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
