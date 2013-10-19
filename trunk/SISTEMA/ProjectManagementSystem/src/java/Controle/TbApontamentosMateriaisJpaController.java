/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Controle.exceptions.NonexistentEntityException;
import Controle.exceptions.PreexistingEntityException;
import Controle.exceptions.RollbackFailureException;
import Entidades.TbApontamentosMateriais;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import Entidades.TbOsServico;
import Entidades.TbMateriais;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;

/**
 *
 * @author berlim
 */
public class TbApontamentosMateriaisJpaController implements Serializable {

    public TbApontamentosMateriaisJpaController() {
        emf = Persistence.createEntityManagerFactory("ProjectManagementSystemPU");
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TbApontamentosMateriais tbApontamentosMateriais) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbOsServico tbOsServicoHand = tbApontamentosMateriais.getTbOsServicoHand();
            if (tbOsServicoHand != null) {
                tbOsServicoHand = em.getReference(tbOsServicoHand.getClass(), tbOsServicoHand.getHand());
                tbApontamentosMateriais.setTbOsServicoHand(tbOsServicoHand);
            }
            TbMateriais tbMateriaisHand = tbApontamentosMateriais.getTbMateriaisHand();
            if (tbMateriaisHand != null) {
                tbMateriaisHand = em.getReference(tbMateriaisHand.getClass(), tbMateriaisHand.getHand());
                tbApontamentosMateriais.setTbMateriaisHand(tbMateriaisHand);
            }
            em.persist(tbApontamentosMateriais);
            if (tbOsServicoHand != null) {
                tbOsServicoHand.getTbApontamentosMateriaisCollection().add(tbApontamentosMateriais);
                tbOsServicoHand = em.merge(tbOsServicoHand);
            }
            if (tbMateriaisHand != null) {
                tbMateriaisHand.getTbApontamentosMateriaisCollection().add(tbApontamentosMateriais);
                tbMateriaisHand = em.merge(tbMateriaisHand);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTbApontamentosMateriais(tbApontamentosMateriais.getHand()) != null) {
                throw new PreexistingEntityException("TbApontamentosMateriais " + tbApontamentosMateriais + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TbApontamentosMateriais tbApontamentosMateriais) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbApontamentosMateriais persistentTbApontamentosMateriais = em.find(TbApontamentosMateriais.class, tbApontamentosMateriais.getHand());
            TbOsServico tbOsServicoHandOld = persistentTbApontamentosMateriais.getTbOsServicoHand();
            TbOsServico tbOsServicoHandNew = tbApontamentosMateriais.getTbOsServicoHand();
            TbMateriais tbMateriaisHandOld = persistentTbApontamentosMateriais.getTbMateriaisHand();
            TbMateriais tbMateriaisHandNew = tbApontamentosMateriais.getTbMateriaisHand();
            if (tbOsServicoHandNew != null) {
                tbOsServicoHandNew = em.getReference(tbOsServicoHandNew.getClass(), tbOsServicoHandNew.getHand());
                tbApontamentosMateriais.setTbOsServicoHand(tbOsServicoHandNew);
            }
            if (tbMateriaisHandNew != null) {
                tbMateriaisHandNew = em.getReference(tbMateriaisHandNew.getClass(), tbMateriaisHandNew.getHand());
                tbApontamentosMateriais.setTbMateriaisHand(tbMateriaisHandNew);
            }
            tbApontamentosMateriais = em.merge(tbApontamentosMateriais);
            if (tbOsServicoHandOld != null && !tbOsServicoHandOld.equals(tbOsServicoHandNew)) {
                tbOsServicoHandOld.getTbApontamentosMateriaisCollection().remove(tbApontamentosMateriais);
                tbOsServicoHandOld = em.merge(tbOsServicoHandOld);
            }
            if (tbOsServicoHandNew != null && !tbOsServicoHandNew.equals(tbOsServicoHandOld)) {
                tbOsServicoHandNew.getTbApontamentosMateriaisCollection().add(tbApontamentosMateriais);
                tbOsServicoHandNew = em.merge(tbOsServicoHandNew);
            }
            if (tbMateriaisHandOld != null && !tbMateriaisHandOld.equals(tbMateriaisHandNew)) {
                tbMateriaisHandOld.getTbApontamentosMateriaisCollection().remove(tbApontamentosMateriais);
                tbMateriaisHandOld = em.merge(tbMateriaisHandOld);
            }
            if (tbMateriaisHandNew != null && !tbMateriaisHandNew.equals(tbMateriaisHandOld)) {
                tbMateriaisHandNew.getTbApontamentosMateriaisCollection().add(tbApontamentosMateriais);
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
                Integer id = tbApontamentosMateriais.getHand();
                if (findTbApontamentosMateriais(id) == null) {
                    throw new NonexistentEntityException("The tbApontamentosMateriais with id " + id + " no longer exists.");
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
            TbApontamentosMateriais tbApontamentosMateriais;
            try {
                tbApontamentosMateriais = em.getReference(TbApontamentosMateriais.class, id);
                tbApontamentosMateriais.getHand();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tbApontamentosMateriais with id " + id + " no longer exists.", enfe);
            }
            TbOsServico tbOsServicoHand = tbApontamentosMateriais.getTbOsServicoHand();
            if (tbOsServicoHand != null) {
                tbOsServicoHand.getTbApontamentosMateriaisCollection().remove(tbApontamentosMateriais);
                tbOsServicoHand = em.merge(tbOsServicoHand);
            }
            TbMateriais tbMateriaisHand = tbApontamentosMateriais.getTbMateriaisHand();
            if (tbMateriaisHand != null) {
                tbMateriaisHand.getTbApontamentosMateriaisCollection().remove(tbApontamentosMateriais);
                tbMateriaisHand = em.merge(tbMateriaisHand);
            }
            em.remove(tbApontamentosMateriais);
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

    public List<TbApontamentosMateriais> findTbApontamentosMateriaisEntities() {
        return findTbApontamentosMateriaisEntities(true, -1, -1);
    }

    public List<TbApontamentosMateriais> findTbApontamentosMateriaisEntities(int maxResults, int firstResult) {
        return findTbApontamentosMateriaisEntities(false, maxResults, firstResult);
    }

    private List<TbApontamentosMateriais> findTbApontamentosMateriaisEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from TbApontamentosMateriais as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public TbApontamentosMateriais findTbApontamentosMateriais(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbApontamentosMateriais.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbApontamentosMateriaisCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from TbApontamentosMateriais as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
