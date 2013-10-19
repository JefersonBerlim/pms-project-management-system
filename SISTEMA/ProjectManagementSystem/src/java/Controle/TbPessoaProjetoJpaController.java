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
import Entidades.TbProjetos;
import Entidades.TbPessoa;
import Entidades.TbPessoaProjeto;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;

/**
 *
 * @author berlim
 */
public class TbPessoaProjetoJpaController implements Serializable {

    public TbPessoaProjetoJpaController() {
        emf = Persistence.createEntityManagerFactory("ProjectManagementSystemPU");
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TbPessoaProjeto tbPessoaProjeto) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbProjetos tbProjetoHand = tbPessoaProjeto.getTbProjetoHand();
            if (tbProjetoHand != null) {
                tbProjetoHand = em.getReference(tbProjetoHand.getClass(), tbProjetoHand.getHand());
                tbPessoaProjeto.setTbProjetoHand(tbProjetoHand);
            }
            TbPessoa tbPessoaHand = tbPessoaProjeto.getTbPessoaHand();
            if (tbPessoaHand != null) {
                tbPessoaHand = em.getReference(tbPessoaHand.getClass(), tbPessoaHand.getHand());
                tbPessoaProjeto.setTbPessoaHand(tbPessoaHand);
            }
            em.persist(tbPessoaProjeto);
            if (tbProjetoHand != null) {
                tbProjetoHand.getTbPessoaProjetoCollection().add(tbPessoaProjeto);
                tbProjetoHand = em.merge(tbProjetoHand);
            }
            if (tbPessoaHand != null) {
                tbPessoaHand.getTbPessoaProjetoCollection().add(tbPessoaProjeto);
                tbPessoaHand = em.merge(tbPessoaHand);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTbPessoaProjeto(tbPessoaProjeto.getHand()) != null) {
                throw new PreexistingEntityException("TbPessoaProjeto " + tbPessoaProjeto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TbPessoaProjeto tbPessoaProjeto) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbPessoaProjeto persistentTbPessoaProjeto = em.find(TbPessoaProjeto.class, tbPessoaProjeto.getHand());
            TbProjetos tbProjetoHandOld = persistentTbPessoaProjeto.getTbProjetoHand();
            TbProjetos tbProjetoHandNew = tbPessoaProjeto.getTbProjetoHand();
            TbPessoa tbPessoaHandOld = persistentTbPessoaProjeto.getTbPessoaHand();
            TbPessoa tbPessoaHandNew = tbPessoaProjeto.getTbPessoaHand();
            if (tbProjetoHandNew != null) {
                tbProjetoHandNew = em.getReference(tbProjetoHandNew.getClass(), tbProjetoHandNew.getHand());
                tbPessoaProjeto.setTbProjetoHand(tbProjetoHandNew);
            }
            if (tbPessoaHandNew != null) {
                tbPessoaHandNew = em.getReference(tbPessoaHandNew.getClass(), tbPessoaHandNew.getHand());
                tbPessoaProjeto.setTbPessoaHand(tbPessoaHandNew);
            }
            tbPessoaProjeto = em.merge(tbPessoaProjeto);
            if (tbProjetoHandOld != null && !tbProjetoHandOld.equals(tbProjetoHandNew)) {
                tbProjetoHandOld.getTbPessoaProjetoCollection().remove(tbPessoaProjeto);
                tbProjetoHandOld = em.merge(tbProjetoHandOld);
            }
            if (tbProjetoHandNew != null && !tbProjetoHandNew.equals(tbProjetoHandOld)) {
                tbProjetoHandNew.getTbPessoaProjetoCollection().add(tbPessoaProjeto);
                tbProjetoHandNew = em.merge(tbProjetoHandNew);
            }
            if (tbPessoaHandOld != null && !tbPessoaHandOld.equals(tbPessoaHandNew)) {
                tbPessoaHandOld.getTbPessoaProjetoCollection().remove(tbPessoaProjeto);
                tbPessoaHandOld = em.merge(tbPessoaHandOld);
            }
            if (tbPessoaHandNew != null && !tbPessoaHandNew.equals(tbPessoaHandOld)) {
                tbPessoaHandNew.getTbPessoaProjetoCollection().add(tbPessoaProjeto);
                tbPessoaHandNew = em.merge(tbPessoaHandNew);
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
                Integer id = tbPessoaProjeto.getHand();
                if (findTbPessoaProjeto(id) == null) {
                    throw new NonexistentEntityException("The tbPessoaProjeto with id " + id + " no longer exists.");
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
            TbPessoaProjeto tbPessoaProjeto;
            try {
                tbPessoaProjeto = em.getReference(TbPessoaProjeto.class, id);
                tbPessoaProjeto.getHand();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tbPessoaProjeto with id " + id + " no longer exists.", enfe);
            }
            TbProjetos tbProjetoHand = tbPessoaProjeto.getTbProjetoHand();
            if (tbProjetoHand != null) {
                tbProjetoHand.getTbPessoaProjetoCollection().remove(tbPessoaProjeto);
                tbProjetoHand = em.merge(tbProjetoHand);
            }
            TbPessoa tbPessoaHand = tbPessoaProjeto.getTbPessoaHand();
            if (tbPessoaHand != null) {
                tbPessoaHand.getTbPessoaProjetoCollection().remove(tbPessoaProjeto);
                tbPessoaHand = em.merge(tbPessoaHand);
            }
            em.remove(tbPessoaProjeto);
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

    public List<TbPessoaProjeto> findTbPessoaProjetoEntities() {
        return findTbPessoaProjetoEntities(true, -1, -1);
    }

    public List<TbPessoaProjeto> findTbPessoaProjetoEntities(int maxResults, int firstResult) {
        return findTbPessoaProjetoEntities(false, maxResults, firstResult);
    }

    private List<TbPessoaProjeto> findTbPessoaProjetoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from TbPessoaProjeto as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public TbPessoaProjeto findTbPessoaProjeto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbPessoaProjeto.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbPessoaProjetoCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from TbPessoaProjeto as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
