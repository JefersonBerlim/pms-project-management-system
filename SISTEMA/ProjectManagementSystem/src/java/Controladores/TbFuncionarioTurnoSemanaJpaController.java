/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.NonexistentEntityException;
import Controladores.exceptions.PreexistingEntityException;
import Controladores.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelos.TbTurnos;
import Modelos.TbFuncionarios;
import Modelos.TbDiaSemana;
import Modelos.TbFuncionarioTurnoSemana;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author jeferson
 */
public class TbFuncionarioTurnoSemanaJpaController implements Serializable {

    public TbFuncionarioTurnoSemanaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TbFuncionarioTurnoSemana tbFuncionarioTurnoSemana) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbTurnos tbTurnosHand = tbFuncionarioTurnoSemana.getTbTurnosHand();
            if (tbTurnosHand != null) {
                tbTurnosHand = em.getReference(tbTurnosHand.getClass(), tbTurnosHand.getHand());
                tbFuncionarioTurnoSemana.setTbTurnosHand(tbTurnosHand);
            }
            TbFuncionarios tbFuncionariosHand = tbFuncionarioTurnoSemana.getTbFuncionariosHand();
            if (tbFuncionariosHand != null) {
                tbFuncionariosHand = em.getReference(tbFuncionariosHand.getClass(), tbFuncionariosHand.getHand());
                tbFuncionarioTurnoSemana.setTbFuncionariosHand(tbFuncionariosHand);
            }
            TbDiaSemana tbDiaSemanaHand = tbFuncionarioTurnoSemana.getTbDiaSemanaHand();
            if (tbDiaSemanaHand != null) {
                tbDiaSemanaHand = em.getReference(tbDiaSemanaHand.getClass(), tbDiaSemanaHand.getHand());
                tbFuncionarioTurnoSemana.setTbDiaSemanaHand(tbDiaSemanaHand);
            }
            em.persist(tbFuncionarioTurnoSemana);
            if (tbTurnosHand != null) {
                tbTurnosHand.getTbFuncionarioTurnoSemanaCollection().add(tbFuncionarioTurnoSemana);
                tbTurnosHand = em.merge(tbTurnosHand);
            }
            if (tbFuncionariosHand != null) {
                tbFuncionariosHand.getTbFuncionarioTurnoSemanaCollection().add(tbFuncionarioTurnoSemana);
                tbFuncionariosHand = em.merge(tbFuncionariosHand);
            }
            if (tbDiaSemanaHand != null) {
                tbDiaSemanaHand.getTbFuncionarioTurnoSemanaCollection().add(tbFuncionarioTurnoSemana);
                tbDiaSemanaHand = em.merge(tbDiaSemanaHand);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTbFuncionarioTurnoSemana(tbFuncionarioTurnoSemana.getHand()) != null) {
                throw new PreexistingEntityException("TbFuncionarioTurnoSemana " + tbFuncionarioTurnoSemana + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TbFuncionarioTurnoSemana tbFuncionarioTurnoSemana) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbFuncionarioTurnoSemana persistentTbFuncionarioTurnoSemana = em.find(TbFuncionarioTurnoSemana.class, tbFuncionarioTurnoSemana.getHand());
            TbTurnos tbTurnosHandOld = persistentTbFuncionarioTurnoSemana.getTbTurnosHand();
            TbTurnos tbTurnosHandNew = tbFuncionarioTurnoSemana.getTbTurnosHand();
            TbFuncionarios tbFuncionariosHandOld = persistentTbFuncionarioTurnoSemana.getTbFuncionariosHand();
            TbFuncionarios tbFuncionariosHandNew = tbFuncionarioTurnoSemana.getTbFuncionariosHand();
            TbDiaSemana tbDiaSemanaHandOld = persistentTbFuncionarioTurnoSemana.getTbDiaSemanaHand();
            TbDiaSemana tbDiaSemanaHandNew = tbFuncionarioTurnoSemana.getTbDiaSemanaHand();
            if (tbTurnosHandNew != null) {
                tbTurnosHandNew = em.getReference(tbTurnosHandNew.getClass(), tbTurnosHandNew.getHand());
                tbFuncionarioTurnoSemana.setTbTurnosHand(tbTurnosHandNew);
            }
            if (tbFuncionariosHandNew != null) {
                tbFuncionariosHandNew = em.getReference(tbFuncionariosHandNew.getClass(), tbFuncionariosHandNew.getHand());
                tbFuncionarioTurnoSemana.setTbFuncionariosHand(tbFuncionariosHandNew);
            }
            if (tbDiaSemanaHandNew != null) {
                tbDiaSemanaHandNew = em.getReference(tbDiaSemanaHandNew.getClass(), tbDiaSemanaHandNew.getHand());
                tbFuncionarioTurnoSemana.setTbDiaSemanaHand(tbDiaSemanaHandNew);
            }
            tbFuncionarioTurnoSemana = em.merge(tbFuncionarioTurnoSemana);
            if (tbTurnosHandOld != null && !tbTurnosHandOld.equals(tbTurnosHandNew)) {
                tbTurnosHandOld.getTbFuncionarioTurnoSemanaCollection().remove(tbFuncionarioTurnoSemana);
                tbTurnosHandOld = em.merge(tbTurnosHandOld);
            }
            if (tbTurnosHandNew != null && !tbTurnosHandNew.equals(tbTurnosHandOld)) {
                tbTurnosHandNew.getTbFuncionarioTurnoSemanaCollection().add(tbFuncionarioTurnoSemana);
                tbTurnosHandNew = em.merge(tbTurnosHandNew);
            }
            if (tbFuncionariosHandOld != null && !tbFuncionariosHandOld.equals(tbFuncionariosHandNew)) {
                tbFuncionariosHandOld.getTbFuncionarioTurnoSemanaCollection().remove(tbFuncionarioTurnoSemana);
                tbFuncionariosHandOld = em.merge(tbFuncionariosHandOld);
            }
            if (tbFuncionariosHandNew != null && !tbFuncionariosHandNew.equals(tbFuncionariosHandOld)) {
                tbFuncionariosHandNew.getTbFuncionarioTurnoSemanaCollection().add(tbFuncionarioTurnoSemana);
                tbFuncionariosHandNew = em.merge(tbFuncionariosHandNew);
            }
            if (tbDiaSemanaHandOld != null && !tbDiaSemanaHandOld.equals(tbDiaSemanaHandNew)) {
                tbDiaSemanaHandOld.getTbFuncionarioTurnoSemanaCollection().remove(tbFuncionarioTurnoSemana);
                tbDiaSemanaHandOld = em.merge(tbDiaSemanaHandOld);
            }
            if (tbDiaSemanaHandNew != null && !tbDiaSemanaHandNew.equals(tbDiaSemanaHandOld)) {
                tbDiaSemanaHandNew.getTbFuncionarioTurnoSemanaCollection().add(tbFuncionarioTurnoSemana);
                tbDiaSemanaHandNew = em.merge(tbDiaSemanaHandNew);
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
                Integer id = tbFuncionarioTurnoSemana.getHand();
                if (findTbFuncionarioTurnoSemana(id) == null) {
                    throw new NonexistentEntityException("The tbFuncionarioTurnoSemana with id " + id + " no longer exists.");
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
            TbFuncionarioTurnoSemana tbFuncionarioTurnoSemana;
            try {
                tbFuncionarioTurnoSemana = em.getReference(TbFuncionarioTurnoSemana.class, id);
                tbFuncionarioTurnoSemana.getHand();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tbFuncionarioTurnoSemana with id " + id + " no longer exists.", enfe);
            }
            TbTurnos tbTurnosHand = tbFuncionarioTurnoSemana.getTbTurnosHand();
            if (tbTurnosHand != null) {
                tbTurnosHand.getTbFuncionarioTurnoSemanaCollection().remove(tbFuncionarioTurnoSemana);
                tbTurnosHand = em.merge(tbTurnosHand);
            }
            TbFuncionarios tbFuncionariosHand = tbFuncionarioTurnoSemana.getTbFuncionariosHand();
            if (tbFuncionariosHand != null) {
                tbFuncionariosHand.getTbFuncionarioTurnoSemanaCollection().remove(tbFuncionarioTurnoSemana);
                tbFuncionariosHand = em.merge(tbFuncionariosHand);
            }
            TbDiaSemana tbDiaSemanaHand = tbFuncionarioTurnoSemana.getTbDiaSemanaHand();
            if (tbDiaSemanaHand != null) {
                tbDiaSemanaHand.getTbFuncionarioTurnoSemanaCollection().remove(tbFuncionarioTurnoSemana);
                tbDiaSemanaHand = em.merge(tbDiaSemanaHand);
            }
            em.remove(tbFuncionarioTurnoSemana);
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

    public List<TbFuncionarioTurnoSemana> findTbFuncionarioTurnoSemanaEntities() {
        return findTbFuncionarioTurnoSemanaEntities(true, -1, -1);
    }

    public List<TbFuncionarioTurnoSemana> findTbFuncionarioTurnoSemanaEntities(int maxResults, int firstResult) {
        return findTbFuncionarioTurnoSemanaEntities(false, maxResults, firstResult);
    }

    private List<TbFuncionarioTurnoSemana> findTbFuncionarioTurnoSemanaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TbFuncionarioTurnoSemana.class));
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

    public TbFuncionarioTurnoSemana findTbFuncionarioTurnoSemana(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbFuncionarioTurnoSemana.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbFuncionarioTurnoSemanaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TbFuncionarioTurnoSemana> rt = cq.from(TbFuncionarioTurnoSemana.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
