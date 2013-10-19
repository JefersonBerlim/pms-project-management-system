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
import Entidades.TbProjetosRecursos;
import Entidades.TbOsServico;
import Entidades.TbOsFuncionarios;
import Entidades.TbOsRecursos;
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
public class TbOsRecursosJpaController implements Serializable {

    public TbOsRecursosJpaController() {
        emf = Persistence.createEntityManagerFactory("ProjectManagementSystemPU");
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TbOsRecursos tbOsRecursos) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (tbOsRecursos.getTbOsFuncionariosCollection() == null) {
            tbOsRecursos.setTbOsFuncionariosCollection(new ArrayList<TbOsFuncionarios>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbProjetosRecursos tbProjetosRecursosHand = tbOsRecursos.getTbProjetosRecursosHand();
            if (tbProjetosRecursosHand != null) {
                tbProjetosRecursosHand = em.getReference(tbProjetosRecursosHand.getClass(), tbProjetosRecursosHand.getHand());
                tbOsRecursos.setTbProjetosRecursosHand(tbProjetosRecursosHand);
            }
            TbOsServico tbOsServicoHand = tbOsRecursos.getTbOsServicoHand();
            if (tbOsServicoHand != null) {
                tbOsServicoHand = em.getReference(tbOsServicoHand.getClass(), tbOsServicoHand.getHand());
                tbOsRecursos.setTbOsServicoHand(tbOsServicoHand);
            }
            Collection<TbOsFuncionarios> attachedTbOsFuncionariosCollection = new ArrayList<TbOsFuncionarios>();
            for (TbOsFuncionarios tbOsFuncionariosCollectionTbOsFuncionariosToAttach : tbOsRecursos.getTbOsFuncionariosCollection()) {
                tbOsFuncionariosCollectionTbOsFuncionariosToAttach = em.getReference(tbOsFuncionariosCollectionTbOsFuncionariosToAttach.getClass(), tbOsFuncionariosCollectionTbOsFuncionariosToAttach.getHand());
                attachedTbOsFuncionariosCollection.add(tbOsFuncionariosCollectionTbOsFuncionariosToAttach);
            }
            tbOsRecursos.setTbOsFuncionariosCollection(attachedTbOsFuncionariosCollection);
            em.persist(tbOsRecursos);
            if (tbProjetosRecursosHand != null) {
                tbProjetosRecursosHand.getTbOsRecursosCollection().add(tbOsRecursos);
                tbProjetosRecursosHand = em.merge(tbProjetosRecursosHand);
            }
            if (tbOsServicoHand != null) {
                tbOsServicoHand.getTbOsRecursosCollection().add(tbOsRecursos);
                tbOsServicoHand = em.merge(tbOsServicoHand);
            }
            for (TbOsFuncionarios tbOsFuncionariosCollectionTbOsFuncionarios : tbOsRecursos.getTbOsFuncionariosCollection()) {
                TbOsRecursos oldTbOsRecursosHandOfTbOsFuncionariosCollectionTbOsFuncionarios = tbOsFuncionariosCollectionTbOsFuncionarios.getTbOsRecursosHand();
                tbOsFuncionariosCollectionTbOsFuncionarios.setTbOsRecursosHand(tbOsRecursos);
                tbOsFuncionariosCollectionTbOsFuncionarios = em.merge(tbOsFuncionariosCollectionTbOsFuncionarios);
                if (oldTbOsRecursosHandOfTbOsFuncionariosCollectionTbOsFuncionarios != null) {
                    oldTbOsRecursosHandOfTbOsFuncionariosCollectionTbOsFuncionarios.getTbOsFuncionariosCollection().remove(tbOsFuncionariosCollectionTbOsFuncionarios);
                    oldTbOsRecursosHandOfTbOsFuncionariosCollectionTbOsFuncionarios = em.merge(oldTbOsRecursosHandOfTbOsFuncionariosCollectionTbOsFuncionarios);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTbOsRecursos(tbOsRecursos.getHand()) != null) {
                throw new PreexistingEntityException("TbOsRecursos " + tbOsRecursos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TbOsRecursos tbOsRecursos) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbOsRecursos persistentTbOsRecursos = em.find(TbOsRecursos.class, tbOsRecursos.getHand());
            TbProjetosRecursos tbProjetosRecursosHandOld = persistentTbOsRecursos.getTbProjetosRecursosHand();
            TbProjetosRecursos tbProjetosRecursosHandNew = tbOsRecursos.getTbProjetosRecursosHand();
            TbOsServico tbOsServicoHandOld = persistentTbOsRecursos.getTbOsServicoHand();
            TbOsServico tbOsServicoHandNew = tbOsRecursos.getTbOsServicoHand();
            Collection<TbOsFuncionarios> tbOsFuncionariosCollectionOld = persistentTbOsRecursos.getTbOsFuncionariosCollection();
            Collection<TbOsFuncionarios> tbOsFuncionariosCollectionNew = tbOsRecursos.getTbOsFuncionariosCollection();
            List<String> illegalOrphanMessages = null;
            for (TbOsFuncionarios tbOsFuncionariosCollectionOldTbOsFuncionarios : tbOsFuncionariosCollectionOld) {
                if (!tbOsFuncionariosCollectionNew.contains(tbOsFuncionariosCollectionOldTbOsFuncionarios)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TbOsFuncionarios " + tbOsFuncionariosCollectionOldTbOsFuncionarios + " since its tbOsRecursosHand field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tbProjetosRecursosHandNew != null) {
                tbProjetosRecursosHandNew = em.getReference(tbProjetosRecursosHandNew.getClass(), tbProjetosRecursosHandNew.getHand());
                tbOsRecursos.setTbProjetosRecursosHand(tbProjetosRecursosHandNew);
            }
            if (tbOsServicoHandNew != null) {
                tbOsServicoHandNew = em.getReference(tbOsServicoHandNew.getClass(), tbOsServicoHandNew.getHand());
                tbOsRecursos.setTbOsServicoHand(tbOsServicoHandNew);
            }
            Collection<TbOsFuncionarios> attachedTbOsFuncionariosCollectionNew = new ArrayList<TbOsFuncionarios>();
            for (TbOsFuncionarios tbOsFuncionariosCollectionNewTbOsFuncionariosToAttach : tbOsFuncionariosCollectionNew) {
                tbOsFuncionariosCollectionNewTbOsFuncionariosToAttach = em.getReference(tbOsFuncionariosCollectionNewTbOsFuncionariosToAttach.getClass(), tbOsFuncionariosCollectionNewTbOsFuncionariosToAttach.getHand());
                attachedTbOsFuncionariosCollectionNew.add(tbOsFuncionariosCollectionNewTbOsFuncionariosToAttach);
            }
            tbOsFuncionariosCollectionNew = attachedTbOsFuncionariosCollectionNew;
            tbOsRecursos.setTbOsFuncionariosCollection(tbOsFuncionariosCollectionNew);
            tbOsRecursos = em.merge(tbOsRecursos);
            if (tbProjetosRecursosHandOld != null && !tbProjetosRecursosHandOld.equals(tbProjetosRecursosHandNew)) {
                tbProjetosRecursosHandOld.getTbOsRecursosCollection().remove(tbOsRecursos);
                tbProjetosRecursosHandOld = em.merge(tbProjetosRecursosHandOld);
            }
            if (tbProjetosRecursosHandNew != null && !tbProjetosRecursosHandNew.equals(tbProjetosRecursosHandOld)) {
                tbProjetosRecursosHandNew.getTbOsRecursosCollection().add(tbOsRecursos);
                tbProjetosRecursosHandNew = em.merge(tbProjetosRecursosHandNew);
            }
            if (tbOsServicoHandOld != null && !tbOsServicoHandOld.equals(tbOsServicoHandNew)) {
                tbOsServicoHandOld.getTbOsRecursosCollection().remove(tbOsRecursos);
                tbOsServicoHandOld = em.merge(tbOsServicoHandOld);
            }
            if (tbOsServicoHandNew != null && !tbOsServicoHandNew.equals(tbOsServicoHandOld)) {
                tbOsServicoHandNew.getTbOsRecursosCollection().add(tbOsRecursos);
                tbOsServicoHandNew = em.merge(tbOsServicoHandNew);
            }
            for (TbOsFuncionarios tbOsFuncionariosCollectionNewTbOsFuncionarios : tbOsFuncionariosCollectionNew) {
                if (!tbOsFuncionariosCollectionOld.contains(tbOsFuncionariosCollectionNewTbOsFuncionarios)) {
                    TbOsRecursos oldTbOsRecursosHandOfTbOsFuncionariosCollectionNewTbOsFuncionarios = tbOsFuncionariosCollectionNewTbOsFuncionarios.getTbOsRecursosHand();
                    tbOsFuncionariosCollectionNewTbOsFuncionarios.setTbOsRecursosHand(tbOsRecursos);
                    tbOsFuncionariosCollectionNewTbOsFuncionarios = em.merge(tbOsFuncionariosCollectionNewTbOsFuncionarios);
                    if (oldTbOsRecursosHandOfTbOsFuncionariosCollectionNewTbOsFuncionarios != null && !oldTbOsRecursosHandOfTbOsFuncionariosCollectionNewTbOsFuncionarios.equals(tbOsRecursos)) {
                        oldTbOsRecursosHandOfTbOsFuncionariosCollectionNewTbOsFuncionarios.getTbOsFuncionariosCollection().remove(tbOsFuncionariosCollectionNewTbOsFuncionarios);
                        oldTbOsRecursosHandOfTbOsFuncionariosCollectionNewTbOsFuncionarios = em.merge(oldTbOsRecursosHandOfTbOsFuncionariosCollectionNewTbOsFuncionarios);
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
                Integer id = tbOsRecursos.getHand();
                if (findTbOsRecursos(id) == null) {
                    throw new NonexistentEntityException("The tbOsRecursos with id " + id + " no longer exists.");
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
            TbOsRecursos tbOsRecursos;
            try {
                tbOsRecursos = em.getReference(TbOsRecursos.class, id);
                tbOsRecursos.getHand();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tbOsRecursos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TbOsFuncionarios> tbOsFuncionariosCollectionOrphanCheck = tbOsRecursos.getTbOsFuncionariosCollection();
            for (TbOsFuncionarios tbOsFuncionariosCollectionOrphanCheckTbOsFuncionarios : tbOsFuncionariosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbOsRecursos (" + tbOsRecursos + ") cannot be destroyed since the TbOsFuncionarios " + tbOsFuncionariosCollectionOrphanCheckTbOsFuncionarios + " in its tbOsFuncionariosCollection field has a non-nullable tbOsRecursosHand field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            TbProjetosRecursos tbProjetosRecursosHand = tbOsRecursos.getTbProjetosRecursosHand();
            if (tbProjetosRecursosHand != null) {
                tbProjetosRecursosHand.getTbOsRecursosCollection().remove(tbOsRecursos);
                tbProjetosRecursosHand = em.merge(tbProjetosRecursosHand);
            }
            TbOsServico tbOsServicoHand = tbOsRecursos.getTbOsServicoHand();
            if (tbOsServicoHand != null) {
                tbOsServicoHand.getTbOsRecursosCollection().remove(tbOsRecursos);
                tbOsServicoHand = em.merge(tbOsServicoHand);
            }
            em.remove(tbOsRecursos);
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

    public List<TbOsRecursos> findTbOsRecursosEntities() {
        return findTbOsRecursosEntities(true, -1, -1);
    }

    public List<TbOsRecursos> findTbOsRecursosEntities(int maxResults, int firstResult) {
        return findTbOsRecursosEntities(false, maxResults, firstResult);
    }

    private List<TbOsRecursos> findTbOsRecursosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from TbOsRecursos as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public TbOsRecursos findTbOsRecursos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbOsRecursos.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbOsRecursosCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from TbOsRecursos as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
