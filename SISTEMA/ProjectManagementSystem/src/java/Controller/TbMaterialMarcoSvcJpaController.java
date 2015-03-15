/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.exceptions.NonexistentEntityException;
import Controller.exceptions.PreexistingEntityException;
import Controller.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Model.TbMarcosServicos;
import Model.TbMateriais;
import Model.TbMaterialMarcoSvc;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author BERLIM
 */
public class TbMaterialMarcoSvcJpaController implements Serializable {

    public TbMaterialMarcoSvcJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TbMaterialMarcoSvc tbMaterialMarcoSvc) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbMarcosServicos tbMarcosServicosHand = tbMaterialMarcoSvc.getTbMarcosServicosHand();
            if (tbMarcosServicosHand != null) {
                tbMarcosServicosHand = em.getReference(tbMarcosServicosHand.getClass(), tbMarcosServicosHand.getHand());
                tbMaterialMarcoSvc.setTbMarcosServicosHand(tbMarcosServicosHand);
            }
            TbMateriais tbMateriaisHand = tbMaterialMarcoSvc.getTbMateriaisHand();
            if (tbMateriaisHand != null) {
                tbMateriaisHand = em.getReference(tbMateriaisHand.getClass(), tbMateriaisHand.getHand());
                tbMaterialMarcoSvc.setTbMateriaisHand(tbMateriaisHand);
            }
            em.persist(tbMaterialMarcoSvc);
            if (tbMarcosServicosHand != null) {
                tbMarcosServicosHand.getTbMaterialMarcoSvcCollection().add(tbMaterialMarcoSvc);
                tbMarcosServicosHand = em.merge(tbMarcosServicosHand);
            }
            if (tbMateriaisHand != null) {
                tbMateriaisHand.getTbMaterialMarcoSvcCollection().add(tbMaterialMarcoSvc);
                tbMateriaisHand = em.merge(tbMateriaisHand);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTbMaterialMarcoSvc(tbMaterialMarcoSvc.getHand()) != null) {
                throw new PreexistingEntityException("TbMaterialMarcoSvc " + tbMaterialMarcoSvc + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TbMaterialMarcoSvc tbMaterialMarcoSvc) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbMaterialMarcoSvc persistentTbMaterialMarcoSvc = em.find(TbMaterialMarcoSvc.class, tbMaterialMarcoSvc.getHand());
            TbMarcosServicos tbMarcosServicosHandOld = persistentTbMaterialMarcoSvc.getTbMarcosServicosHand();
            TbMarcosServicos tbMarcosServicosHandNew = tbMaterialMarcoSvc.getTbMarcosServicosHand();
            TbMateriais tbMateriaisHandOld = persistentTbMaterialMarcoSvc.getTbMateriaisHand();
            TbMateriais tbMateriaisHandNew = tbMaterialMarcoSvc.getTbMateriaisHand();
            if (tbMarcosServicosHandNew != null) {
                tbMarcosServicosHandNew = em.getReference(tbMarcosServicosHandNew.getClass(), tbMarcosServicosHandNew.getHand());
                tbMaterialMarcoSvc.setTbMarcosServicosHand(tbMarcosServicosHandNew);
            }
            if (tbMateriaisHandNew != null) {
                tbMateriaisHandNew = em.getReference(tbMateriaisHandNew.getClass(), tbMateriaisHandNew.getHand());
                tbMaterialMarcoSvc.setTbMateriaisHand(tbMateriaisHandNew);
            }
            tbMaterialMarcoSvc = em.merge(tbMaterialMarcoSvc);
            if (tbMarcosServicosHandOld != null && !tbMarcosServicosHandOld.equals(tbMarcosServicosHandNew)) {
                tbMarcosServicosHandOld.getTbMaterialMarcoSvcCollection().remove(tbMaterialMarcoSvc);
                tbMarcosServicosHandOld = em.merge(tbMarcosServicosHandOld);
            }
            if (tbMarcosServicosHandNew != null && !tbMarcosServicosHandNew.equals(tbMarcosServicosHandOld)) {
                tbMarcosServicosHandNew.getTbMaterialMarcoSvcCollection().add(tbMaterialMarcoSvc);
                tbMarcosServicosHandNew = em.merge(tbMarcosServicosHandNew);
            }
            if (tbMateriaisHandOld != null && !tbMateriaisHandOld.equals(tbMateriaisHandNew)) {
                tbMateriaisHandOld.getTbMaterialMarcoSvcCollection().remove(tbMaterialMarcoSvc);
                tbMateriaisHandOld = em.merge(tbMateriaisHandOld);
            }
            if (tbMateriaisHandNew != null && !tbMateriaisHandNew.equals(tbMateriaisHandOld)) {
                tbMateriaisHandNew.getTbMaterialMarcoSvcCollection().add(tbMaterialMarcoSvc);
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
                Integer id = tbMaterialMarcoSvc.getHand();
                if (findTbMaterialMarcoSvc(id) == null) {
                    throw new NonexistentEntityException("The tbMaterialMarcoSvc with id " + id + " no longer exists.");
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
            TbMaterialMarcoSvc tbMaterialMarcoSvc;
            try {
                tbMaterialMarcoSvc = em.getReference(TbMaterialMarcoSvc.class, id);
                tbMaterialMarcoSvc.getHand();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tbMaterialMarcoSvc with id " + id + " no longer exists.", enfe);
            }
            TbMarcosServicos tbMarcosServicosHand = tbMaterialMarcoSvc.getTbMarcosServicosHand();
            if (tbMarcosServicosHand != null) {
                tbMarcosServicosHand.getTbMaterialMarcoSvcCollection().remove(tbMaterialMarcoSvc);
                tbMarcosServicosHand = em.merge(tbMarcosServicosHand);
            }
            TbMateriais tbMateriaisHand = tbMaterialMarcoSvc.getTbMateriaisHand();
            if (tbMateriaisHand != null) {
                tbMateriaisHand.getTbMaterialMarcoSvcCollection().remove(tbMaterialMarcoSvc);
                tbMateriaisHand = em.merge(tbMateriaisHand);
            }
            em.remove(tbMaterialMarcoSvc);
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

    public List<TbMaterialMarcoSvc> findTbMaterialMarcoSvcEntities() {
        return findTbMaterialMarcoSvcEntities(true, -1, -1);
    }

    public List<TbMaterialMarcoSvc> findTbMaterialMarcoSvcEntities(int maxResults, int firstResult) {
        return findTbMaterialMarcoSvcEntities(false, maxResults, firstResult);
    }

    private List<TbMaterialMarcoSvc> findTbMaterialMarcoSvcEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TbMaterialMarcoSvc.class));
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

    public TbMaterialMarcoSvc findTbMaterialMarcoSvc(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbMaterialMarcoSvc.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbMaterialMarcoSvcCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TbMaterialMarcoSvc> rt = cq.from(TbMaterialMarcoSvc.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
