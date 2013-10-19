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
import Entidades.TbMarcos;
import Entidades.TbMarcosServicos;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;

/**
 *
 * @author berlim
 */
public class TbMarcosServicosJpaController implements Serializable {

    public TbMarcosServicosJpaController() {
        emf = Persistence.createEntityManagerFactory("ProjectManagementSystemPU");
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TbMarcosServicos tbMarcosServicos) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbServicos tbServicosHand = tbMarcosServicos.getTbServicosHand();
            if (tbServicosHand != null) {
                tbServicosHand = em.getReference(tbServicosHand.getClass(), tbServicosHand.getHand());
                tbMarcosServicos.setTbServicosHand(tbServicosHand);
            }
            TbMarcos tbMarcosHand = tbMarcosServicos.getTbMarcosHand();
            if (tbMarcosHand != null) {
                tbMarcosHand = em.getReference(tbMarcosHand.getClass(), tbMarcosHand.getHand());
                tbMarcosServicos.setTbMarcosHand(tbMarcosHand);
            }
            em.persist(tbMarcosServicos);
            if (tbServicosHand != null) {
                tbServicosHand.getTbMarcosServicosCollection().add(tbMarcosServicos);
                tbServicosHand = em.merge(tbServicosHand);
            }
            if (tbMarcosHand != null) {
                tbMarcosHand.getTbMarcosServicosCollection().add(tbMarcosServicos);
                tbMarcosHand = em.merge(tbMarcosHand);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTbMarcosServicos(tbMarcosServicos.getHand()) != null) {
                throw new PreexistingEntityException("TbMarcosServicos " + tbMarcosServicos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TbMarcosServicos tbMarcosServicos) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbMarcosServicos persistentTbMarcosServicos = em.find(TbMarcosServicos.class, tbMarcosServicos.getHand());
            TbServicos tbServicosHandOld = persistentTbMarcosServicos.getTbServicosHand();
            TbServicos tbServicosHandNew = tbMarcosServicos.getTbServicosHand();
            TbMarcos tbMarcosHandOld = persistentTbMarcosServicos.getTbMarcosHand();
            TbMarcos tbMarcosHandNew = tbMarcosServicos.getTbMarcosHand();
            if (tbServicosHandNew != null) {
                tbServicosHandNew = em.getReference(tbServicosHandNew.getClass(), tbServicosHandNew.getHand());
                tbMarcosServicos.setTbServicosHand(tbServicosHandNew);
            }
            if (tbMarcosHandNew != null) {
                tbMarcosHandNew = em.getReference(tbMarcosHandNew.getClass(), tbMarcosHandNew.getHand());
                tbMarcosServicos.setTbMarcosHand(tbMarcosHandNew);
            }
            tbMarcosServicos = em.merge(tbMarcosServicos);
            if (tbServicosHandOld != null && !tbServicosHandOld.equals(tbServicosHandNew)) {
                tbServicosHandOld.getTbMarcosServicosCollection().remove(tbMarcosServicos);
                tbServicosHandOld = em.merge(tbServicosHandOld);
            }
            if (tbServicosHandNew != null && !tbServicosHandNew.equals(tbServicosHandOld)) {
                tbServicosHandNew.getTbMarcosServicosCollection().add(tbMarcosServicos);
                tbServicosHandNew = em.merge(tbServicosHandNew);
            }
            if (tbMarcosHandOld != null && !tbMarcosHandOld.equals(tbMarcosHandNew)) {
                tbMarcosHandOld.getTbMarcosServicosCollection().remove(tbMarcosServicos);
                tbMarcosHandOld = em.merge(tbMarcosHandOld);
            }
            if (tbMarcosHandNew != null && !tbMarcosHandNew.equals(tbMarcosHandOld)) {
                tbMarcosHandNew.getTbMarcosServicosCollection().add(tbMarcosServicos);
                tbMarcosHandNew = em.merge(tbMarcosHandNew);
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
                Integer id = tbMarcosServicos.getHand();
                if (findTbMarcosServicos(id) == null) {
                    throw new NonexistentEntityException("The tbMarcosServicos with id " + id + " no longer exists.");
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
            TbMarcosServicos tbMarcosServicos;
            try {
                tbMarcosServicos = em.getReference(TbMarcosServicos.class, id);
                tbMarcosServicos.getHand();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tbMarcosServicos with id " + id + " no longer exists.", enfe);
            }
            TbServicos tbServicosHand = tbMarcosServicos.getTbServicosHand();
            if (tbServicosHand != null) {
                tbServicosHand.getTbMarcosServicosCollection().remove(tbMarcosServicos);
                tbServicosHand = em.merge(tbServicosHand);
            }
            TbMarcos tbMarcosHand = tbMarcosServicos.getTbMarcosHand();
            if (tbMarcosHand != null) {
                tbMarcosHand.getTbMarcosServicosCollection().remove(tbMarcosServicos);
                tbMarcosHand = em.merge(tbMarcosHand);
            }
            em.remove(tbMarcosServicos);
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

    public List<TbMarcosServicos> findTbMarcosServicosEntities() {
        return findTbMarcosServicosEntities(true, -1, -1);
    }

    public List<TbMarcosServicos> findTbMarcosServicosEntities(int maxResults, int firstResult) {
        return findTbMarcosServicosEntities(false, maxResults, firstResult);
    }

    private List<TbMarcosServicos> findTbMarcosServicosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from TbMarcosServicos as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public TbMarcosServicos findTbMarcosServicos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbMarcosServicos.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbMarcosServicosCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from TbMarcosServicos as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
