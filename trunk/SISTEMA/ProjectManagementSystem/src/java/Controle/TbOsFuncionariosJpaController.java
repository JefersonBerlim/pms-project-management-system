/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Controle.exceptions.NonexistentEntityException;
import Controle.exceptions.PreexistingEntityException;
import Controle.exceptions.RollbackFailureException;
import Entidades.TbOsFuncionarios;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import Entidades.TbProjetoFuncionarios;
import Entidades.TbOsRecursos;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;

/**
 *
 * @author berlim
 */
public class TbOsFuncionariosJpaController implements Serializable {

    public TbOsFuncionariosJpaController() {
        emf = Persistence.createEntityManagerFactory("ProjectManagementSystemPU");
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TbOsFuncionarios tbOsFuncionarios) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbProjetoFuncionarios tbProjetoFuncionariosHand = tbOsFuncionarios.getTbProjetoFuncionariosHand();
            if (tbProjetoFuncionariosHand != null) {
                tbProjetoFuncionariosHand = em.getReference(tbProjetoFuncionariosHand.getClass(), tbProjetoFuncionariosHand.getHand());
                tbOsFuncionarios.setTbProjetoFuncionariosHand(tbProjetoFuncionariosHand);
            }
            TbOsRecursos tbOsRecursosHand = tbOsFuncionarios.getTbOsRecursosHand();
            if (tbOsRecursosHand != null) {
                tbOsRecursosHand = em.getReference(tbOsRecursosHand.getClass(), tbOsRecursosHand.getHand());
                tbOsFuncionarios.setTbOsRecursosHand(tbOsRecursosHand);
            }
            em.persist(tbOsFuncionarios);
            if (tbProjetoFuncionariosHand != null) {
                tbProjetoFuncionariosHand.getTbOsFuncionariosCollection().add(tbOsFuncionarios);
                tbProjetoFuncionariosHand = em.merge(tbProjetoFuncionariosHand);
            }
            if (tbOsRecursosHand != null) {
                tbOsRecursosHand.getTbOsFuncionariosCollection().add(tbOsFuncionarios);
                tbOsRecursosHand = em.merge(tbOsRecursosHand);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTbOsFuncionarios(tbOsFuncionarios.getHand()) != null) {
                throw new PreexistingEntityException("TbOsFuncionarios " + tbOsFuncionarios + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TbOsFuncionarios tbOsFuncionarios) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbOsFuncionarios persistentTbOsFuncionarios = em.find(TbOsFuncionarios.class, tbOsFuncionarios.getHand());
            TbProjetoFuncionarios tbProjetoFuncionariosHandOld = persistentTbOsFuncionarios.getTbProjetoFuncionariosHand();
            TbProjetoFuncionarios tbProjetoFuncionariosHandNew = tbOsFuncionarios.getTbProjetoFuncionariosHand();
            TbOsRecursos tbOsRecursosHandOld = persistentTbOsFuncionarios.getTbOsRecursosHand();
            TbOsRecursos tbOsRecursosHandNew = tbOsFuncionarios.getTbOsRecursosHand();
            if (tbProjetoFuncionariosHandNew != null) {
                tbProjetoFuncionariosHandNew = em.getReference(tbProjetoFuncionariosHandNew.getClass(), tbProjetoFuncionariosHandNew.getHand());
                tbOsFuncionarios.setTbProjetoFuncionariosHand(tbProjetoFuncionariosHandNew);
            }
            if (tbOsRecursosHandNew != null) {
                tbOsRecursosHandNew = em.getReference(tbOsRecursosHandNew.getClass(), tbOsRecursosHandNew.getHand());
                tbOsFuncionarios.setTbOsRecursosHand(tbOsRecursosHandNew);
            }
            tbOsFuncionarios = em.merge(tbOsFuncionarios);
            if (tbProjetoFuncionariosHandOld != null && !tbProjetoFuncionariosHandOld.equals(tbProjetoFuncionariosHandNew)) {
                tbProjetoFuncionariosHandOld.getTbOsFuncionariosCollection().remove(tbOsFuncionarios);
                tbProjetoFuncionariosHandOld = em.merge(tbProjetoFuncionariosHandOld);
            }
            if (tbProjetoFuncionariosHandNew != null && !tbProjetoFuncionariosHandNew.equals(tbProjetoFuncionariosHandOld)) {
                tbProjetoFuncionariosHandNew.getTbOsFuncionariosCollection().add(tbOsFuncionarios);
                tbProjetoFuncionariosHandNew = em.merge(tbProjetoFuncionariosHandNew);
            }
            if (tbOsRecursosHandOld != null && !tbOsRecursosHandOld.equals(tbOsRecursosHandNew)) {
                tbOsRecursosHandOld.getTbOsFuncionariosCollection().remove(tbOsFuncionarios);
                tbOsRecursosHandOld = em.merge(tbOsRecursosHandOld);
            }
            if (tbOsRecursosHandNew != null && !tbOsRecursosHandNew.equals(tbOsRecursosHandOld)) {
                tbOsRecursosHandNew.getTbOsFuncionariosCollection().add(tbOsFuncionarios);
                tbOsRecursosHandNew = em.merge(tbOsRecursosHandNew);
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
                Integer id = tbOsFuncionarios.getHand();
                if (findTbOsFuncionarios(id) == null) {
                    throw new NonexistentEntityException("The tbOsFuncionarios with id " + id + " no longer exists.");
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
            TbOsFuncionarios tbOsFuncionarios;
            try {
                tbOsFuncionarios = em.getReference(TbOsFuncionarios.class, id);
                tbOsFuncionarios.getHand();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tbOsFuncionarios with id " + id + " no longer exists.", enfe);
            }
            TbProjetoFuncionarios tbProjetoFuncionariosHand = tbOsFuncionarios.getTbProjetoFuncionariosHand();
            if (tbProjetoFuncionariosHand != null) {
                tbProjetoFuncionariosHand.getTbOsFuncionariosCollection().remove(tbOsFuncionarios);
                tbProjetoFuncionariosHand = em.merge(tbProjetoFuncionariosHand);
            }
            TbOsRecursos tbOsRecursosHand = tbOsFuncionarios.getTbOsRecursosHand();
            if (tbOsRecursosHand != null) {
                tbOsRecursosHand.getTbOsFuncionariosCollection().remove(tbOsFuncionarios);
                tbOsRecursosHand = em.merge(tbOsRecursosHand);
            }
            em.remove(tbOsFuncionarios);
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

    public List<TbOsFuncionarios> findTbOsFuncionariosEntities() {
        return findTbOsFuncionariosEntities(true, -1, -1);
    }

    public List<TbOsFuncionarios> findTbOsFuncionariosEntities(int maxResults, int firstResult) {
        return findTbOsFuncionariosEntities(false, maxResults, firstResult);
    }

    private List<TbOsFuncionarios> findTbOsFuncionariosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from TbOsFuncionarios as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public TbOsFuncionarios findTbOsFuncionarios(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbOsFuncionarios.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbOsFuncionariosCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from TbOsFuncionarios as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
