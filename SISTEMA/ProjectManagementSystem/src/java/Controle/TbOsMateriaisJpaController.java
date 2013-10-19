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
import Entidades.TbProjetosMateriais;
import Entidades.TbOsServico;
import Entidades.TbMateriais;
import Entidades.TbOsMateriais;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;

/**
 *
 * @author berlim
 */
public class TbOsMateriaisJpaController implements Serializable {

    public TbOsMateriaisJpaController() {
        emf = Persistence.createEntityManagerFactory("ProjectManagementSystemPU");
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TbOsMateriais tbOsMateriais) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbProjetosMateriais tbProjetosMateriaisHand = tbOsMateriais.getTbProjetosMateriaisHand();
            if (tbProjetosMateriaisHand != null) {
                tbProjetosMateriaisHand = em.getReference(tbProjetosMateriaisHand.getClass(), tbProjetosMateriaisHand.getHand());
                tbOsMateriais.setTbProjetosMateriaisHand(tbProjetosMateriaisHand);
            }
            TbOsServico tbOsServicoHand = tbOsMateriais.getTbOsServicoHand();
            if (tbOsServicoHand != null) {
                tbOsServicoHand = em.getReference(tbOsServicoHand.getClass(), tbOsServicoHand.getHand());
                tbOsMateriais.setTbOsServicoHand(tbOsServicoHand);
            }
            TbMateriais tbMateriaisHand = tbOsMateriais.getTbMateriaisHand();
            if (tbMateriaisHand != null) {
                tbMateriaisHand = em.getReference(tbMateriaisHand.getClass(), tbMateriaisHand.getHand());
                tbOsMateriais.setTbMateriaisHand(tbMateriaisHand);
            }
            em.persist(tbOsMateriais);
            if (tbProjetosMateriaisHand != null) {
                tbProjetosMateriaisHand.getTbOsMateriaisCollection().add(tbOsMateriais);
                tbProjetosMateriaisHand = em.merge(tbProjetosMateriaisHand);
            }
            if (tbOsServicoHand != null) {
                tbOsServicoHand.getTbOsMateriaisCollection().add(tbOsMateriais);
                tbOsServicoHand = em.merge(tbOsServicoHand);
            }
            if (tbMateriaisHand != null) {
                tbMateriaisHand.getTbOsMateriaisCollection().add(tbOsMateriais);
                tbMateriaisHand = em.merge(tbMateriaisHand);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTbOsMateriais(tbOsMateriais.getHand()) != null) {
                throw new PreexistingEntityException("TbOsMateriais " + tbOsMateriais + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TbOsMateriais tbOsMateriais) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbOsMateriais persistentTbOsMateriais = em.find(TbOsMateriais.class, tbOsMateriais.getHand());
            TbProjetosMateriais tbProjetosMateriaisHandOld = persistentTbOsMateriais.getTbProjetosMateriaisHand();
            TbProjetosMateriais tbProjetosMateriaisHandNew = tbOsMateriais.getTbProjetosMateriaisHand();
            TbOsServico tbOsServicoHandOld = persistentTbOsMateriais.getTbOsServicoHand();
            TbOsServico tbOsServicoHandNew = tbOsMateriais.getTbOsServicoHand();
            TbMateriais tbMateriaisHandOld = persistentTbOsMateriais.getTbMateriaisHand();
            TbMateriais tbMateriaisHandNew = tbOsMateriais.getTbMateriaisHand();
            if (tbProjetosMateriaisHandNew != null) {
                tbProjetosMateriaisHandNew = em.getReference(tbProjetosMateriaisHandNew.getClass(), tbProjetosMateriaisHandNew.getHand());
                tbOsMateriais.setTbProjetosMateriaisHand(tbProjetosMateriaisHandNew);
            }
            if (tbOsServicoHandNew != null) {
                tbOsServicoHandNew = em.getReference(tbOsServicoHandNew.getClass(), tbOsServicoHandNew.getHand());
                tbOsMateriais.setTbOsServicoHand(tbOsServicoHandNew);
            }
            if (tbMateriaisHandNew != null) {
                tbMateriaisHandNew = em.getReference(tbMateriaisHandNew.getClass(), tbMateriaisHandNew.getHand());
                tbOsMateriais.setTbMateriaisHand(tbMateriaisHandNew);
            }
            tbOsMateriais = em.merge(tbOsMateriais);
            if (tbProjetosMateriaisHandOld != null && !tbProjetosMateriaisHandOld.equals(tbProjetosMateriaisHandNew)) {
                tbProjetosMateriaisHandOld.getTbOsMateriaisCollection().remove(tbOsMateriais);
                tbProjetosMateriaisHandOld = em.merge(tbProjetosMateriaisHandOld);
            }
            if (tbProjetosMateriaisHandNew != null && !tbProjetosMateriaisHandNew.equals(tbProjetosMateriaisHandOld)) {
                tbProjetosMateriaisHandNew.getTbOsMateriaisCollection().add(tbOsMateriais);
                tbProjetosMateriaisHandNew = em.merge(tbProjetosMateriaisHandNew);
            }
            if (tbOsServicoHandOld != null && !tbOsServicoHandOld.equals(tbOsServicoHandNew)) {
                tbOsServicoHandOld.getTbOsMateriaisCollection().remove(tbOsMateriais);
                tbOsServicoHandOld = em.merge(tbOsServicoHandOld);
            }
            if (tbOsServicoHandNew != null && !tbOsServicoHandNew.equals(tbOsServicoHandOld)) {
                tbOsServicoHandNew.getTbOsMateriaisCollection().add(tbOsMateriais);
                tbOsServicoHandNew = em.merge(tbOsServicoHandNew);
            }
            if (tbMateriaisHandOld != null && !tbMateriaisHandOld.equals(tbMateriaisHandNew)) {
                tbMateriaisHandOld.getTbOsMateriaisCollection().remove(tbOsMateriais);
                tbMateriaisHandOld = em.merge(tbMateriaisHandOld);
            }
            if (tbMateriaisHandNew != null && !tbMateriaisHandNew.equals(tbMateriaisHandOld)) {
                tbMateriaisHandNew.getTbOsMateriaisCollection().add(tbOsMateriais);
                tbMateriaisHandNew = em.merge(tbMateriaisHandNew);
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
                Integer id = tbOsMateriais.getHand();
                if (findTbOsMateriais(id) == null) {
                    throw new NonexistentEntityException("The tbOsMateriais with id " + id + " no longer exists.");
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
            TbOsMateriais tbOsMateriais;
            try {
                tbOsMateriais = em.getReference(TbOsMateriais.class, id);
                tbOsMateriais.getHand();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tbOsMateriais with id " + id + " no longer exists.", enfe);
            }
            TbProjetosMateriais tbProjetosMateriaisHand = tbOsMateriais.getTbProjetosMateriaisHand();
            if (tbProjetosMateriaisHand != null) {
                tbProjetosMateriaisHand.getTbOsMateriaisCollection().remove(tbOsMateriais);
                tbProjetosMateriaisHand = em.merge(tbProjetosMateriaisHand);
            }
            TbOsServico tbOsServicoHand = tbOsMateriais.getTbOsServicoHand();
            if (tbOsServicoHand != null) {
                tbOsServicoHand.getTbOsMateriaisCollection().remove(tbOsMateriais);
                tbOsServicoHand = em.merge(tbOsServicoHand);
            }
            TbMateriais tbMateriaisHand = tbOsMateriais.getTbMateriaisHand();
            if (tbMateriaisHand != null) {
                tbMateriaisHand.getTbOsMateriaisCollection().remove(tbOsMateriais);
                tbMateriaisHand = em.merge(tbMateriaisHand);
            }
            em.remove(tbOsMateriais);
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

    public List<TbOsMateriais> findTbOsMateriaisEntities() {
        return findTbOsMateriaisEntities(true, -1, -1);
    }

    public List<TbOsMateriais> findTbOsMateriaisEntities(int maxResults, int firstResult) {
        return findTbOsMateriaisEntities(false, maxResults, firstResult);
    }

    private List<TbOsMateriais> findTbOsMateriaisEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from TbOsMateriais as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public TbOsMateriais findTbOsMateriais(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbOsMateriais.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbOsMateriaisCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from TbOsMateriais as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
