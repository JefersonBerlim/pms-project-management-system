/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.exceptions.NonexistentEntityException;
import Controller.exceptions.PreexistingEntityException;
import Controller.exceptions.RollbackFailureException;
import Model.TbApontamentosMateriais;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Model.TbFuncionarios;
import Model.TbMateriais;
import Model.TbOsServico;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author BERLIM
 */
@ManagedBean
public class TbApontamentosMateriaisJpaController implements Serializable {

    public TbApontamentosMateriaisJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
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
            TbFuncionarios tbFuncionariosHand = tbApontamentosMateriais.getTbFuncionariosHand();
            if (tbFuncionariosHand != null) {
                tbFuncionariosHand = em.getReference(tbFuncionariosHand.getClass(), tbFuncionariosHand.getHand());
                tbApontamentosMateriais.setTbFuncionariosHand(tbFuncionariosHand);
            }
            TbMateriais tbMateriaisHand = tbApontamentosMateriais.getTbMateriaisHand();
            if (tbMateriaisHand != null) {
                tbMateriaisHand = em.getReference(tbMateriaisHand.getClass(), tbMateriaisHand.getHand());
                tbApontamentosMateriais.setTbMateriaisHand(tbMateriaisHand);
            }
            TbOsServico tbOsServicoHand = tbApontamentosMateriais.getTbOsServicoHand();
            if (tbOsServicoHand != null) {
                tbOsServicoHand = em.getReference(tbOsServicoHand.getClass(), tbOsServicoHand.getHand());
                tbApontamentosMateriais.setTbOsServicoHand(tbOsServicoHand);
            }
            em.persist(tbApontamentosMateriais);
            if (tbFuncionariosHand != null) {
                tbFuncionariosHand.getTbApontamentosMateriaisCollection().add(tbApontamentosMateriais);
                tbFuncionariosHand = em.merge(tbFuncionariosHand);
            }
            if (tbMateriaisHand != null) {
                tbMateriaisHand.getTbApontamentosMateriaisCollection().add(tbApontamentosMateriais);
                tbMateriaisHand = em.merge(tbMateriaisHand);
            }
            if (tbOsServicoHand != null) {
                tbOsServicoHand.getTbApontamentosMateriaisCollection().add(tbApontamentosMateriais);
                tbOsServicoHand = em.merge(tbOsServicoHand);
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
            TbFuncionarios tbFuncionariosHandOld = persistentTbApontamentosMateriais.getTbFuncionariosHand();
            TbFuncionarios tbFuncionariosHandNew = tbApontamentosMateriais.getTbFuncionariosHand();
            TbMateriais tbMateriaisHandOld = persistentTbApontamentosMateriais.getTbMateriaisHand();
            TbMateriais tbMateriaisHandNew = tbApontamentosMateriais.getTbMateriaisHand();
            TbOsServico tbOsServicoHandOld = persistentTbApontamentosMateriais.getTbOsServicoHand();
            TbOsServico tbOsServicoHandNew = tbApontamentosMateriais.getTbOsServicoHand();
            if (tbFuncionariosHandNew != null) {
                tbFuncionariosHandNew = em.getReference(tbFuncionariosHandNew.getClass(), tbFuncionariosHandNew.getHand());
                tbApontamentosMateriais.setTbFuncionariosHand(tbFuncionariosHandNew);
            }
            if (tbMateriaisHandNew != null) {
                tbMateriaisHandNew = em.getReference(tbMateriaisHandNew.getClass(), tbMateriaisHandNew.getHand());
                tbApontamentosMateriais.setTbMateriaisHand(tbMateriaisHandNew);
            }
            if (tbOsServicoHandNew != null) {
                tbOsServicoHandNew = em.getReference(tbOsServicoHandNew.getClass(), tbOsServicoHandNew.getHand());
                tbApontamentosMateriais.setTbOsServicoHand(tbOsServicoHandNew);
            }
            tbApontamentosMateriais = em.merge(tbApontamentosMateriais);
            if (tbFuncionariosHandOld != null && !tbFuncionariosHandOld.equals(tbFuncionariosHandNew)) {
                tbFuncionariosHandOld.getTbApontamentosMateriaisCollection().remove(tbApontamentosMateriais);
                tbFuncionariosHandOld = em.merge(tbFuncionariosHandOld);
            }
            if (tbFuncionariosHandNew != null && !tbFuncionariosHandNew.equals(tbFuncionariosHandOld)) {
                tbFuncionariosHandNew.getTbApontamentosMateriaisCollection().add(tbApontamentosMateriais);
                tbFuncionariosHandNew = em.merge(tbFuncionariosHandNew);
            }
            if (tbMateriaisHandOld != null && !tbMateriaisHandOld.equals(tbMateriaisHandNew)) {
                tbMateriaisHandOld.getTbApontamentosMateriaisCollection().remove(tbApontamentosMateriais);
                tbMateriaisHandOld = em.merge(tbMateriaisHandOld);
            }
            if (tbMateriaisHandNew != null && !tbMateriaisHandNew.equals(tbMateriaisHandOld)) {
                tbMateriaisHandNew.getTbApontamentosMateriaisCollection().add(tbApontamentosMateriais);
                tbMateriaisHandNew = em.merge(tbMateriaisHandNew);
            }
            if (tbOsServicoHandOld != null && !tbOsServicoHandOld.equals(tbOsServicoHandNew)) {
                tbOsServicoHandOld.getTbApontamentosMateriaisCollection().remove(tbApontamentosMateriais);
                tbOsServicoHandOld = em.merge(tbOsServicoHandOld);
            }
            if (tbOsServicoHandNew != null && !tbOsServicoHandNew.equals(tbOsServicoHandOld)) {
                tbOsServicoHandNew.getTbApontamentosMateriaisCollection().add(tbApontamentosMateriais);
                tbOsServicoHandNew = em.merge(tbOsServicoHandNew);
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
            TbFuncionarios tbFuncionariosHand = tbApontamentosMateriais.getTbFuncionariosHand();
            if (tbFuncionariosHand != null) {
                tbFuncionariosHand.getTbApontamentosMateriaisCollection().remove(tbApontamentosMateriais);
                tbFuncionariosHand = em.merge(tbFuncionariosHand);
            }
            TbMateriais tbMateriaisHand = tbApontamentosMateriais.getTbMateriaisHand();
            if (tbMateriaisHand != null) {
                tbMateriaisHand.getTbApontamentosMateriaisCollection().remove(tbApontamentosMateriais);
                tbMateriaisHand = em.merge(tbMateriaisHand);
            }
            TbOsServico tbOsServicoHand = tbApontamentosMateriais.getTbOsServicoHand();
            if (tbOsServicoHand != null) {
                tbOsServicoHand.getTbApontamentosMateriaisCollection().remove(tbApontamentosMateriais);
                tbOsServicoHand = em.merge(tbOsServicoHand);
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
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TbApontamentosMateriais.class));
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
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TbApontamentosMateriais> rt = cq.from(TbApontamentosMateriais.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
