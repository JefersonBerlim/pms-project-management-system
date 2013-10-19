/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Controle.exceptions.NonexistentEntityException;
import Controle.exceptions.PreexistingEntityException;
import Controle.exceptions.RollbackFailureException;
import Entidades.TbApontamentosFuncionarios;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import Entidades.TbFuncionarios;
import Entidades.TbApontamentosRecursos;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;

/**
 *
 * @author berlim
 */
public class TbApontamentosFuncionariosJpaController implements Serializable {

    public TbApontamentosFuncionariosJpaController() {
        emf = Persistence.createEntityManagerFactory("ProjectManagementSystemPU");
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TbApontamentosFuncionarios tbApontamentosFuncionarios) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbFuncionarios tbFuncionariosHand = tbApontamentosFuncionarios.getTbFuncionariosHand();
            if (tbFuncionariosHand != null) {
                tbFuncionariosHand = em.getReference(tbFuncionariosHand.getClass(), tbFuncionariosHand.getHand());
                tbApontamentosFuncionarios.setTbFuncionariosHand(tbFuncionariosHand);
            }
            TbApontamentosRecursos tbApontamentosRecursosHand = tbApontamentosFuncionarios.getTbApontamentosRecursosHand();
            if (tbApontamentosRecursosHand != null) {
                tbApontamentosRecursosHand = em.getReference(tbApontamentosRecursosHand.getClass(), tbApontamentosRecursosHand.getHand());
                tbApontamentosFuncionarios.setTbApontamentosRecursosHand(tbApontamentosRecursosHand);
            }
            em.persist(tbApontamentosFuncionarios);
            if (tbFuncionariosHand != null) {
                tbFuncionariosHand.getTbApontamentosFuncionariosCollection().add(tbApontamentosFuncionarios);
                tbFuncionariosHand = em.merge(tbFuncionariosHand);
            }
            if (tbApontamentosRecursosHand != null) {
                tbApontamentosRecursosHand.getTbApontamentosFuncionariosCollection().add(tbApontamentosFuncionarios);
                tbApontamentosRecursosHand = em.merge(tbApontamentosRecursosHand);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTbApontamentosFuncionarios(tbApontamentosFuncionarios.getHand()) != null) {
                throw new PreexistingEntityException("TbApontamentosFuncionarios " + tbApontamentosFuncionarios + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TbApontamentosFuncionarios tbApontamentosFuncionarios) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbApontamentosFuncionarios persistentTbApontamentosFuncionarios = em.find(TbApontamentosFuncionarios.class, tbApontamentosFuncionarios.getHand());
            TbFuncionarios tbFuncionariosHandOld = persistentTbApontamentosFuncionarios.getTbFuncionariosHand();
            TbFuncionarios tbFuncionariosHandNew = tbApontamentosFuncionarios.getTbFuncionariosHand();
            TbApontamentosRecursos tbApontamentosRecursosHandOld = persistentTbApontamentosFuncionarios.getTbApontamentosRecursosHand();
            TbApontamentosRecursos tbApontamentosRecursosHandNew = tbApontamentosFuncionarios.getTbApontamentosRecursosHand();
            if (tbFuncionariosHandNew != null) {
                tbFuncionariosHandNew = em.getReference(tbFuncionariosHandNew.getClass(), tbFuncionariosHandNew.getHand());
                tbApontamentosFuncionarios.setTbFuncionariosHand(tbFuncionariosHandNew);
            }
            if (tbApontamentosRecursosHandNew != null) {
                tbApontamentosRecursosHandNew = em.getReference(tbApontamentosRecursosHandNew.getClass(), tbApontamentosRecursosHandNew.getHand());
                tbApontamentosFuncionarios.setTbApontamentosRecursosHand(tbApontamentosRecursosHandNew);
            }
            tbApontamentosFuncionarios = em.merge(tbApontamentosFuncionarios);
            if (tbFuncionariosHandOld != null && !tbFuncionariosHandOld.equals(tbFuncionariosHandNew)) {
                tbFuncionariosHandOld.getTbApontamentosFuncionariosCollection().remove(tbApontamentosFuncionarios);
                tbFuncionariosHandOld = em.merge(tbFuncionariosHandOld);
            }
            if (tbFuncionariosHandNew != null && !tbFuncionariosHandNew.equals(tbFuncionariosHandOld)) {
                tbFuncionariosHandNew.getTbApontamentosFuncionariosCollection().add(tbApontamentosFuncionarios);
                tbFuncionariosHandNew = em.merge(tbFuncionariosHandNew);
            }
            if (tbApontamentosRecursosHandOld != null && !tbApontamentosRecursosHandOld.equals(tbApontamentosRecursosHandNew)) {
                tbApontamentosRecursosHandOld.getTbApontamentosFuncionariosCollection().remove(tbApontamentosFuncionarios);
                tbApontamentosRecursosHandOld = em.merge(tbApontamentosRecursosHandOld);
            }
            if (tbApontamentosRecursosHandNew != null && !tbApontamentosRecursosHandNew.equals(tbApontamentosRecursosHandOld)) {
                tbApontamentosRecursosHandNew.getTbApontamentosFuncionariosCollection().add(tbApontamentosFuncionarios);
                tbApontamentosRecursosHandNew = em.merge(tbApontamentosRecursosHandNew);
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
                Integer id = tbApontamentosFuncionarios.getHand();
                if (findTbApontamentosFuncionarios(id) == null) {
                    throw new NonexistentEntityException("The tbApontamentosFuncionarios with id " + id + " no longer exists.");
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
            TbApontamentosFuncionarios tbApontamentosFuncionarios;
            try {
                tbApontamentosFuncionarios = em.getReference(TbApontamentosFuncionarios.class, id);
                tbApontamentosFuncionarios.getHand();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tbApontamentosFuncionarios with id " + id + " no longer exists.", enfe);
            }
            TbFuncionarios tbFuncionariosHand = tbApontamentosFuncionarios.getTbFuncionariosHand();
            if (tbFuncionariosHand != null) {
                tbFuncionariosHand.getTbApontamentosFuncionariosCollection().remove(tbApontamentosFuncionarios);
                tbFuncionariosHand = em.merge(tbFuncionariosHand);
            }
            TbApontamentosRecursos tbApontamentosRecursosHand = tbApontamentosFuncionarios.getTbApontamentosRecursosHand();
            if (tbApontamentosRecursosHand != null) {
                tbApontamentosRecursosHand.getTbApontamentosFuncionariosCollection().remove(tbApontamentosFuncionarios);
                tbApontamentosRecursosHand = em.merge(tbApontamentosRecursosHand);
            }
            em.remove(tbApontamentosFuncionarios);
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

    public List<TbApontamentosFuncionarios> findTbApontamentosFuncionariosEntities() {
        return findTbApontamentosFuncionariosEntities(true, -1, -1);
    }

    public List<TbApontamentosFuncionarios> findTbApontamentosFuncionariosEntities(int maxResults, int firstResult) {
        return findTbApontamentosFuncionariosEntities(false, maxResults, firstResult);
    }

    private List<TbApontamentosFuncionarios> findTbApontamentosFuncionariosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from TbApontamentosFuncionarios as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public TbApontamentosFuncionarios findTbApontamentosFuncionarios(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbApontamentosFuncionarios.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbApontamentosFuncionariosCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from TbApontamentosFuncionarios as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
