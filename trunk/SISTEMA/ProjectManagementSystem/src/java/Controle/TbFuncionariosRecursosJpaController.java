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
import Entidades.TbRecursos;
import Entidades.TbFuncionarios;
import Entidades.TbFuncionariosRecursos;
import Entidades.TbProjetoFuncionarios;
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
public class TbFuncionariosRecursosJpaController implements Serializable {

    public TbFuncionariosRecursosJpaController() {
        emf = Persistence.createEntityManagerFactory("ProjectManagementSystemPU");
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TbFuncionariosRecursos tbFuncionariosRecursos) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (tbFuncionariosRecursos.getTbProjetoFuncionariosCollection() == null) {
            tbFuncionariosRecursos.setTbProjetoFuncionariosCollection(new ArrayList<TbProjetoFuncionarios>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbRecursos tbRecursosHand = tbFuncionariosRecursos.getTbRecursosHand();
            if (tbRecursosHand != null) {
                tbRecursosHand = em.getReference(tbRecursosHand.getClass(), tbRecursosHand.getHand());
                tbFuncionariosRecursos.setTbRecursosHand(tbRecursosHand);
            }
            TbFuncionarios tbFuncionariosHand = tbFuncionariosRecursos.getTbFuncionariosHand();
            if (tbFuncionariosHand != null) {
                tbFuncionariosHand = em.getReference(tbFuncionariosHand.getClass(), tbFuncionariosHand.getHand());
                tbFuncionariosRecursos.setTbFuncionariosHand(tbFuncionariosHand);
            }
            Collection<TbProjetoFuncionarios> attachedTbProjetoFuncionariosCollection = new ArrayList<TbProjetoFuncionarios>();
            for (TbProjetoFuncionarios tbProjetoFuncionariosCollectionTbProjetoFuncionariosToAttach : tbFuncionariosRecursos.getTbProjetoFuncionariosCollection()) {
                tbProjetoFuncionariosCollectionTbProjetoFuncionariosToAttach = em.getReference(tbProjetoFuncionariosCollectionTbProjetoFuncionariosToAttach.getClass(), tbProjetoFuncionariosCollectionTbProjetoFuncionariosToAttach.getHand());
                attachedTbProjetoFuncionariosCollection.add(tbProjetoFuncionariosCollectionTbProjetoFuncionariosToAttach);
            }
            tbFuncionariosRecursos.setTbProjetoFuncionariosCollection(attachedTbProjetoFuncionariosCollection);
            em.persist(tbFuncionariosRecursos);
            if (tbRecursosHand != null) {
                tbRecursosHand.getTbFuncionariosRecursosCollection().add(tbFuncionariosRecursos);
                tbRecursosHand = em.merge(tbRecursosHand);
            }
            if (tbFuncionariosHand != null) {
                tbFuncionariosHand.getTbFuncionariosRecursosCollection().add(tbFuncionariosRecursos);
                tbFuncionariosHand = em.merge(tbFuncionariosHand);
            }
            for (TbProjetoFuncionarios tbProjetoFuncionariosCollectionTbProjetoFuncionarios : tbFuncionariosRecursos.getTbProjetoFuncionariosCollection()) {
                TbFuncionariosRecursos oldTbFuncionariosRecursosHandOfTbProjetoFuncionariosCollectionTbProjetoFuncionarios = tbProjetoFuncionariosCollectionTbProjetoFuncionarios.getTbFuncionariosRecursosHand();
                tbProjetoFuncionariosCollectionTbProjetoFuncionarios.setTbFuncionariosRecursosHand(tbFuncionariosRecursos);
                tbProjetoFuncionariosCollectionTbProjetoFuncionarios = em.merge(tbProjetoFuncionariosCollectionTbProjetoFuncionarios);
                if (oldTbFuncionariosRecursosHandOfTbProjetoFuncionariosCollectionTbProjetoFuncionarios != null) {
                    oldTbFuncionariosRecursosHandOfTbProjetoFuncionariosCollectionTbProjetoFuncionarios.getTbProjetoFuncionariosCollection().remove(tbProjetoFuncionariosCollectionTbProjetoFuncionarios);
                    oldTbFuncionariosRecursosHandOfTbProjetoFuncionariosCollectionTbProjetoFuncionarios = em.merge(oldTbFuncionariosRecursosHandOfTbProjetoFuncionariosCollectionTbProjetoFuncionarios);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTbFuncionariosRecursos(tbFuncionariosRecursos.getHand()) != null) {
                throw new PreexistingEntityException("TbFuncionariosRecursos " + tbFuncionariosRecursos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TbFuncionariosRecursos tbFuncionariosRecursos) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbFuncionariosRecursos persistentTbFuncionariosRecursos = em.find(TbFuncionariosRecursos.class, tbFuncionariosRecursos.getHand());
            TbRecursos tbRecursosHandOld = persistentTbFuncionariosRecursos.getTbRecursosHand();
            TbRecursos tbRecursosHandNew = tbFuncionariosRecursos.getTbRecursosHand();
            TbFuncionarios tbFuncionariosHandOld = persistentTbFuncionariosRecursos.getTbFuncionariosHand();
            TbFuncionarios tbFuncionariosHandNew = tbFuncionariosRecursos.getTbFuncionariosHand();
            Collection<TbProjetoFuncionarios> tbProjetoFuncionariosCollectionOld = persistentTbFuncionariosRecursos.getTbProjetoFuncionariosCollection();
            Collection<TbProjetoFuncionarios> tbProjetoFuncionariosCollectionNew = tbFuncionariosRecursos.getTbProjetoFuncionariosCollection();
            List<String> illegalOrphanMessages = null;
            for (TbProjetoFuncionarios tbProjetoFuncionariosCollectionOldTbProjetoFuncionarios : tbProjetoFuncionariosCollectionOld) {
                if (!tbProjetoFuncionariosCollectionNew.contains(tbProjetoFuncionariosCollectionOldTbProjetoFuncionarios)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TbProjetoFuncionarios " + tbProjetoFuncionariosCollectionOldTbProjetoFuncionarios + " since its tbFuncionariosRecursosHand field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tbRecursosHandNew != null) {
                tbRecursosHandNew = em.getReference(tbRecursosHandNew.getClass(), tbRecursosHandNew.getHand());
                tbFuncionariosRecursos.setTbRecursosHand(tbRecursosHandNew);
            }
            if (tbFuncionariosHandNew != null) {
                tbFuncionariosHandNew = em.getReference(tbFuncionariosHandNew.getClass(), tbFuncionariosHandNew.getHand());
                tbFuncionariosRecursos.setTbFuncionariosHand(tbFuncionariosHandNew);
            }
            Collection<TbProjetoFuncionarios> attachedTbProjetoFuncionariosCollectionNew = new ArrayList<TbProjetoFuncionarios>();
            for (TbProjetoFuncionarios tbProjetoFuncionariosCollectionNewTbProjetoFuncionariosToAttach : tbProjetoFuncionariosCollectionNew) {
                tbProjetoFuncionariosCollectionNewTbProjetoFuncionariosToAttach = em.getReference(tbProjetoFuncionariosCollectionNewTbProjetoFuncionariosToAttach.getClass(), tbProjetoFuncionariosCollectionNewTbProjetoFuncionariosToAttach.getHand());
                attachedTbProjetoFuncionariosCollectionNew.add(tbProjetoFuncionariosCollectionNewTbProjetoFuncionariosToAttach);
            }
            tbProjetoFuncionariosCollectionNew = attachedTbProjetoFuncionariosCollectionNew;
            tbFuncionariosRecursos.setTbProjetoFuncionariosCollection(tbProjetoFuncionariosCollectionNew);
            tbFuncionariosRecursos = em.merge(tbFuncionariosRecursos);
            if (tbRecursosHandOld != null && !tbRecursosHandOld.equals(tbRecursosHandNew)) {
                tbRecursosHandOld.getTbFuncionariosRecursosCollection().remove(tbFuncionariosRecursos);
                tbRecursosHandOld = em.merge(tbRecursosHandOld);
            }
            if (tbRecursosHandNew != null && !tbRecursosHandNew.equals(tbRecursosHandOld)) {
                tbRecursosHandNew.getTbFuncionariosRecursosCollection().add(tbFuncionariosRecursos);
                tbRecursosHandNew = em.merge(tbRecursosHandNew);
            }
            if (tbFuncionariosHandOld != null && !tbFuncionariosHandOld.equals(tbFuncionariosHandNew)) {
                tbFuncionariosHandOld.getTbFuncionariosRecursosCollection().remove(tbFuncionariosRecursos);
                tbFuncionariosHandOld = em.merge(tbFuncionariosHandOld);
            }
            if (tbFuncionariosHandNew != null && !tbFuncionariosHandNew.equals(tbFuncionariosHandOld)) {
                tbFuncionariosHandNew.getTbFuncionariosRecursosCollection().add(tbFuncionariosRecursos);
                tbFuncionariosHandNew = em.merge(tbFuncionariosHandNew);
            }
            for (TbProjetoFuncionarios tbProjetoFuncionariosCollectionNewTbProjetoFuncionarios : tbProjetoFuncionariosCollectionNew) {
                if (!tbProjetoFuncionariosCollectionOld.contains(tbProjetoFuncionariosCollectionNewTbProjetoFuncionarios)) {
                    TbFuncionariosRecursos oldTbFuncionariosRecursosHandOfTbProjetoFuncionariosCollectionNewTbProjetoFuncionarios = tbProjetoFuncionariosCollectionNewTbProjetoFuncionarios.getTbFuncionariosRecursosHand();
                    tbProjetoFuncionariosCollectionNewTbProjetoFuncionarios.setTbFuncionariosRecursosHand(tbFuncionariosRecursos);
                    tbProjetoFuncionariosCollectionNewTbProjetoFuncionarios = em.merge(tbProjetoFuncionariosCollectionNewTbProjetoFuncionarios);
                    if (oldTbFuncionariosRecursosHandOfTbProjetoFuncionariosCollectionNewTbProjetoFuncionarios != null && !oldTbFuncionariosRecursosHandOfTbProjetoFuncionariosCollectionNewTbProjetoFuncionarios.equals(tbFuncionariosRecursos)) {
                        oldTbFuncionariosRecursosHandOfTbProjetoFuncionariosCollectionNewTbProjetoFuncionarios.getTbProjetoFuncionariosCollection().remove(tbProjetoFuncionariosCollectionNewTbProjetoFuncionarios);
                        oldTbFuncionariosRecursosHandOfTbProjetoFuncionariosCollectionNewTbProjetoFuncionarios = em.merge(oldTbFuncionariosRecursosHandOfTbProjetoFuncionariosCollectionNewTbProjetoFuncionarios);
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
                Integer id = tbFuncionariosRecursos.getHand();
                if (findTbFuncionariosRecursos(id) == null) {
                    throw new NonexistentEntityException("The tbFuncionariosRecursos with id " + id + " no longer exists.");
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
            TbFuncionariosRecursos tbFuncionariosRecursos;
            try {
                tbFuncionariosRecursos = em.getReference(TbFuncionariosRecursos.class, id);
                tbFuncionariosRecursos.getHand();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tbFuncionariosRecursos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TbProjetoFuncionarios> tbProjetoFuncionariosCollectionOrphanCheck = tbFuncionariosRecursos.getTbProjetoFuncionariosCollection();
            for (TbProjetoFuncionarios tbProjetoFuncionariosCollectionOrphanCheckTbProjetoFuncionarios : tbProjetoFuncionariosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbFuncionariosRecursos (" + tbFuncionariosRecursos + ") cannot be destroyed since the TbProjetoFuncionarios " + tbProjetoFuncionariosCollectionOrphanCheckTbProjetoFuncionarios + " in its tbProjetoFuncionariosCollection field has a non-nullable tbFuncionariosRecursosHand field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            TbRecursos tbRecursosHand = tbFuncionariosRecursos.getTbRecursosHand();
            if (tbRecursosHand != null) {
                tbRecursosHand.getTbFuncionariosRecursosCollection().remove(tbFuncionariosRecursos);
                tbRecursosHand = em.merge(tbRecursosHand);
            }
            TbFuncionarios tbFuncionariosHand = tbFuncionariosRecursos.getTbFuncionariosHand();
            if (tbFuncionariosHand != null) {
                tbFuncionariosHand.getTbFuncionariosRecursosCollection().remove(tbFuncionariosRecursos);
                tbFuncionariosHand = em.merge(tbFuncionariosHand);
            }
            em.remove(tbFuncionariosRecursos);
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

    public List<TbFuncionariosRecursos> findTbFuncionariosRecursosEntities() {
        return findTbFuncionariosRecursosEntities(true, -1, -1);
    }

    public List<TbFuncionariosRecursos> findTbFuncionariosRecursosEntities(int maxResults, int firstResult) {
        return findTbFuncionariosRecursosEntities(false, maxResults, firstResult);
    }

    private List<TbFuncionariosRecursos> findTbFuncionariosRecursosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from TbFuncionariosRecursos as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public TbFuncionariosRecursos findTbFuncionariosRecursos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbFuncionariosRecursos.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbFuncionariosRecursosCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from TbFuncionariosRecursos as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
