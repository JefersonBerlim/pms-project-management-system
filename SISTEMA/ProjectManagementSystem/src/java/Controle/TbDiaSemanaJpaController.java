/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Controle.exceptions.IllegalOrphanException;
import Controle.exceptions.NonexistentEntityException;
import Controle.exceptions.PreexistingEntityException;
import Controle.exceptions.RollbackFailureException;
import Entidades.TbDiaSemana;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import Entidades.TbFuncionarioTurnoSemana;
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
public class TbDiaSemanaJpaController implements Serializable {

    public TbDiaSemanaJpaController() {
        emf = Persistence.createEntityManagerFactory("ProjectManagementSystemPU");
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TbDiaSemana tbDiaSemana) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (tbDiaSemana.getTbFuncionarioTurnoSemanaCollection() == null) {
            tbDiaSemana.setTbFuncionarioTurnoSemanaCollection(new ArrayList<TbFuncionarioTurnoSemana>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<TbFuncionarioTurnoSemana> attachedTbFuncionarioTurnoSemanaCollection = new ArrayList<TbFuncionarioTurnoSemana>();
            for (TbFuncionarioTurnoSemana tbFuncionarioTurnoSemanaCollectionTbFuncionarioTurnoSemanaToAttach : tbDiaSemana.getTbFuncionarioTurnoSemanaCollection()) {
                tbFuncionarioTurnoSemanaCollectionTbFuncionarioTurnoSemanaToAttach = em.getReference(tbFuncionarioTurnoSemanaCollectionTbFuncionarioTurnoSemanaToAttach.getClass(), tbFuncionarioTurnoSemanaCollectionTbFuncionarioTurnoSemanaToAttach.getHand());
                attachedTbFuncionarioTurnoSemanaCollection.add(tbFuncionarioTurnoSemanaCollectionTbFuncionarioTurnoSemanaToAttach);
            }
            tbDiaSemana.setTbFuncionarioTurnoSemanaCollection(attachedTbFuncionarioTurnoSemanaCollection);
            em.persist(tbDiaSemana);
            for (TbFuncionarioTurnoSemana tbFuncionarioTurnoSemanaCollectionTbFuncionarioTurnoSemana : tbDiaSemana.getTbFuncionarioTurnoSemanaCollection()) {
                TbDiaSemana oldTbDiaSemanaHandOfTbFuncionarioTurnoSemanaCollectionTbFuncionarioTurnoSemana = tbFuncionarioTurnoSemanaCollectionTbFuncionarioTurnoSemana.getTbDiaSemanaHand();
                tbFuncionarioTurnoSemanaCollectionTbFuncionarioTurnoSemana.setTbDiaSemanaHand(tbDiaSemana);
                tbFuncionarioTurnoSemanaCollectionTbFuncionarioTurnoSemana = em.merge(tbFuncionarioTurnoSemanaCollectionTbFuncionarioTurnoSemana);
                if (oldTbDiaSemanaHandOfTbFuncionarioTurnoSemanaCollectionTbFuncionarioTurnoSemana != null) {
                    oldTbDiaSemanaHandOfTbFuncionarioTurnoSemanaCollectionTbFuncionarioTurnoSemana.getTbFuncionarioTurnoSemanaCollection().remove(tbFuncionarioTurnoSemanaCollectionTbFuncionarioTurnoSemana);
                    oldTbDiaSemanaHandOfTbFuncionarioTurnoSemanaCollectionTbFuncionarioTurnoSemana = em.merge(oldTbDiaSemanaHandOfTbFuncionarioTurnoSemanaCollectionTbFuncionarioTurnoSemana);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTbDiaSemana(tbDiaSemana.getHand()) != null) {
                throw new PreexistingEntityException("TbDiaSemana " + tbDiaSemana + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TbDiaSemana tbDiaSemana) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbDiaSemana persistentTbDiaSemana = em.find(TbDiaSemana.class, tbDiaSemana.getHand());
            Collection<TbFuncionarioTurnoSemana> tbFuncionarioTurnoSemanaCollectionOld = persistentTbDiaSemana.getTbFuncionarioTurnoSemanaCollection();
            Collection<TbFuncionarioTurnoSemana> tbFuncionarioTurnoSemanaCollectionNew = tbDiaSemana.getTbFuncionarioTurnoSemanaCollection();
            List<String> illegalOrphanMessages = null;
            for (TbFuncionarioTurnoSemana tbFuncionarioTurnoSemanaCollectionOldTbFuncionarioTurnoSemana : tbFuncionarioTurnoSemanaCollectionOld) {
                if (!tbFuncionarioTurnoSemanaCollectionNew.contains(tbFuncionarioTurnoSemanaCollectionOldTbFuncionarioTurnoSemana)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TbFuncionarioTurnoSemana " + tbFuncionarioTurnoSemanaCollectionOldTbFuncionarioTurnoSemana + " since its tbDiaSemanaHand field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<TbFuncionarioTurnoSemana> attachedTbFuncionarioTurnoSemanaCollectionNew = new ArrayList<TbFuncionarioTurnoSemana>();
            for (TbFuncionarioTurnoSemana tbFuncionarioTurnoSemanaCollectionNewTbFuncionarioTurnoSemanaToAttach : tbFuncionarioTurnoSemanaCollectionNew) {
                tbFuncionarioTurnoSemanaCollectionNewTbFuncionarioTurnoSemanaToAttach = em.getReference(tbFuncionarioTurnoSemanaCollectionNewTbFuncionarioTurnoSemanaToAttach.getClass(), tbFuncionarioTurnoSemanaCollectionNewTbFuncionarioTurnoSemanaToAttach.getHand());
                attachedTbFuncionarioTurnoSemanaCollectionNew.add(tbFuncionarioTurnoSemanaCollectionNewTbFuncionarioTurnoSemanaToAttach);
            }
            tbFuncionarioTurnoSemanaCollectionNew = attachedTbFuncionarioTurnoSemanaCollectionNew;
            tbDiaSemana.setTbFuncionarioTurnoSemanaCollection(tbFuncionarioTurnoSemanaCollectionNew);
            tbDiaSemana = em.merge(tbDiaSemana);
            for (TbFuncionarioTurnoSemana tbFuncionarioTurnoSemanaCollectionNewTbFuncionarioTurnoSemana : tbFuncionarioTurnoSemanaCollectionNew) {
                if (!tbFuncionarioTurnoSemanaCollectionOld.contains(tbFuncionarioTurnoSemanaCollectionNewTbFuncionarioTurnoSemana)) {
                    TbDiaSemana oldTbDiaSemanaHandOfTbFuncionarioTurnoSemanaCollectionNewTbFuncionarioTurnoSemana = tbFuncionarioTurnoSemanaCollectionNewTbFuncionarioTurnoSemana.getTbDiaSemanaHand();
                    tbFuncionarioTurnoSemanaCollectionNewTbFuncionarioTurnoSemana.setTbDiaSemanaHand(tbDiaSemana);
                    tbFuncionarioTurnoSemanaCollectionNewTbFuncionarioTurnoSemana = em.merge(tbFuncionarioTurnoSemanaCollectionNewTbFuncionarioTurnoSemana);
                    if (oldTbDiaSemanaHandOfTbFuncionarioTurnoSemanaCollectionNewTbFuncionarioTurnoSemana != null && !oldTbDiaSemanaHandOfTbFuncionarioTurnoSemanaCollectionNewTbFuncionarioTurnoSemana.equals(tbDiaSemana)) {
                        oldTbDiaSemanaHandOfTbFuncionarioTurnoSemanaCollectionNewTbFuncionarioTurnoSemana.getTbFuncionarioTurnoSemanaCollection().remove(tbFuncionarioTurnoSemanaCollectionNewTbFuncionarioTurnoSemana);
                        oldTbDiaSemanaHandOfTbFuncionarioTurnoSemanaCollectionNewTbFuncionarioTurnoSemana = em.merge(oldTbDiaSemanaHandOfTbFuncionarioTurnoSemanaCollectionNewTbFuncionarioTurnoSemana);
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
                Integer id = tbDiaSemana.getHand();
                if (findTbDiaSemana(id) == null) {
                    throw new NonexistentEntityException("The tbDiaSemana with id " + id + " no longer exists.");
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
            TbDiaSemana tbDiaSemana;
            try {
                tbDiaSemana = em.getReference(TbDiaSemana.class, id);
                tbDiaSemana.getHand();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tbDiaSemana with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TbFuncionarioTurnoSemana> tbFuncionarioTurnoSemanaCollectionOrphanCheck = tbDiaSemana.getTbFuncionarioTurnoSemanaCollection();
            for (TbFuncionarioTurnoSemana tbFuncionarioTurnoSemanaCollectionOrphanCheckTbFuncionarioTurnoSemana : tbFuncionarioTurnoSemanaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbDiaSemana (" + tbDiaSemana + ") cannot be destroyed since the TbFuncionarioTurnoSemana " + tbFuncionarioTurnoSemanaCollectionOrphanCheckTbFuncionarioTurnoSemana + " in its tbFuncionarioTurnoSemanaCollection field has a non-nullable tbDiaSemanaHand field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tbDiaSemana);
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

    public List<TbDiaSemana> findTbDiaSemanaEntities() {
        return findTbDiaSemanaEntities(true, -1, -1);
    }

    public List<TbDiaSemana> findTbDiaSemanaEntities(int maxResults, int firstResult) {
        return findTbDiaSemanaEntities(false, maxResults, firstResult);
    }

    private List<TbDiaSemana> findTbDiaSemanaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from TbDiaSemana as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public TbDiaSemana findTbDiaSemana(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbDiaSemana.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbDiaSemanaCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from TbDiaSemana as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
