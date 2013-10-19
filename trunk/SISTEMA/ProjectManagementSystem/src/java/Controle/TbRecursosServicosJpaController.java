/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Controle.exceptions.NonexistentEntityException;
import Controle.exceptions.PreexistingEntityException;
import Controle.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import Entidades.TbServicos;
import Entidades.TbRecursos;
import Entidades.TbRecursosServicos;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;

/**
 *
 * @author berlim
 */
public class TbRecursosServicosJpaController implements Serializable {

    public TbRecursosServicosJpaController() {
        emf = Persistence.createEntityManagerFactory("ProjectManagementSystemPU");
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TbRecursosServicos tbRecursosServicos) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbServicos tbServicosHand = tbRecursosServicos.getTbServicosHand();
            if (tbServicosHand != null) {
                tbServicosHand = em.getReference(tbServicosHand.getClass(), tbServicosHand.getHand());
                tbRecursosServicos.setTbServicosHand(tbServicosHand);
            }
            TbRecursos tbRecursosHand = tbRecursosServicos.getTbRecursosHand();
            if (tbRecursosHand != null) {
                tbRecursosHand = em.getReference(tbRecursosHand.getClass(), tbRecursosHand.getHand());
                tbRecursosServicos.setTbRecursosHand(tbRecursosHand);
            }
            em.persist(tbRecursosServicos);
            if (tbServicosHand != null) {
                tbServicosHand.getTbRecursosServicosCollection().add(tbRecursosServicos);
                tbServicosHand = em.merge(tbServicosHand);
            }
            if (tbRecursosHand != null) {
                tbRecursosHand.getTbRecursosServicosCollection().add(tbRecursosServicos);
                tbRecursosHand = em.merge(tbRecursosHand);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTbRecursosServicos(tbRecursosServicos.getHand()) != null) {
                throw new PreexistingEntityException("TbRecursosServicos " + tbRecursosServicos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TbRecursosServicos tbRecursosServicos) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbRecursosServicos persistentTbRecursosServicos = em.find(TbRecursosServicos.class, tbRecursosServicos.getHand());
            TbServicos tbServicosHandOld = persistentTbRecursosServicos.getTbServicosHand();
            TbServicos tbServicosHandNew = tbRecursosServicos.getTbServicosHand();
            TbRecursos tbRecursosHandOld = persistentTbRecursosServicos.getTbRecursosHand();
            TbRecursos tbRecursosHandNew = tbRecursosServicos.getTbRecursosHand();
            if (tbServicosHandNew != null) {
                tbServicosHandNew = em.getReference(tbServicosHandNew.getClass(), tbServicosHandNew.getHand());
                tbRecursosServicos.setTbServicosHand(tbServicosHandNew);
            }
            if (tbRecursosHandNew != null) {
                tbRecursosHandNew = em.getReference(tbRecursosHandNew.getClass(), tbRecursosHandNew.getHand());
                tbRecursosServicos.setTbRecursosHand(tbRecursosHandNew);
            }
            tbRecursosServicos = em.merge(tbRecursosServicos);
            if (tbServicosHandOld != null && !tbServicosHandOld.equals(tbServicosHandNew)) {
                tbServicosHandOld.getTbRecursosServicosCollection().remove(tbRecursosServicos);
                tbServicosHandOld = em.merge(tbServicosHandOld);
            }
            if (tbServicosHandNew != null && !tbServicosHandNew.equals(tbServicosHandOld)) {
                tbServicosHandNew.getTbRecursosServicosCollection().add(tbRecursosServicos);
                tbServicosHandNew = em.merge(tbServicosHandNew);
            }
            if (tbRecursosHandOld != null && !tbRecursosHandOld.equals(tbRecursosHandNew)) {
                tbRecursosHandOld.getTbRecursosServicosCollection().remove(tbRecursosServicos);
                tbRecursosHandOld = em.merge(tbRecursosHandOld);
            }
            if (tbRecursosHandNew != null && !tbRecursosHandNew.equals(tbRecursosHandOld)) {
                tbRecursosHandNew.getTbRecursosServicosCollection().add(tbRecursosServicos);
                tbRecursosHandNew = em.merge(tbRecursosHandNew);
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
                Integer id = tbRecursosServicos.getHand();
                if (findTbRecursosServicos(id) == null) {
                    throw new NonexistentEntityException("The tbRecursosServicos with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbRecursosServicos tbRecursosServicos;
            try {
                tbRecursosServicos = em.getReference(TbRecursosServicos.class, id);
                tbRecursosServicos.getHand();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tbRecursosServicos with id " + id + " no longer exists.", enfe);
            }
            TbServicos tbServicosHand = tbRecursosServicos.getTbServicosHand();
            if (tbServicosHand != null) {
                tbServicosHand.getTbRecursosServicosCollection().remove(tbRecursosServicos);
                tbServicosHand = em.merge(tbServicosHand);
            }
            TbRecursos tbRecursosHand = tbRecursosServicos.getTbRecursosHand();
            if (tbRecursosHand != null) {
                tbRecursosHand.getTbRecursosServicosCollection().remove(tbRecursosServicos);
                tbRecursosHand = em.merge(tbRecursosHand);
            }
            em.remove(tbRecursosServicos);
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

    public List<TbRecursosServicos> findTbRecursosServicosEntities() {
        return findTbRecursosServicosEntities(true, -1, -1);
    }

    public List<TbRecursosServicos> findTbRecursosServicosEntities(int maxResults, int firstResult) {
        return findTbRecursosServicosEntities(false, maxResults, firstResult);
    }

    private List<TbRecursosServicos> findTbRecursosServicosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from TbRecursosServicos as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public TbRecursosServicos findTbRecursosServicos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbRecursosServicos.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbRecursosServicosCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from TbRecursosServicos as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
