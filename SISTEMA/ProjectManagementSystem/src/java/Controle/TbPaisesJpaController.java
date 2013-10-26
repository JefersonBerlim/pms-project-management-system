/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Controle.exceptions.IllegalOrphanException;
import Controle.exceptions.NonexistentEntityException;
import Controle.exceptions.PreexistingEntityException;
import Controle.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import Entidades.TbEstados;
import Entidades.TbPaises;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author berlim
 */
@ManagedBean
public class TbPaisesJpaController implements Serializable {

    public TbPaisesJpaController() {
        emf = Persistence.createEntityManagerFactory("ProjectManagementSystemPU");
    }
    
    private EntityManagerFactory emf = null;
    private TbPaises tbPaises = new TbPaises();
    private List<TbPaises> tbPaisesLista = new ArrayList<TbPaises>();

    public void setTbPaisesLista(List<TbPaises> tbPaisesLista) {
        this.tbPaisesLista = tbPaisesLista;
    }    

    public void setTbPaises(TbPaises tbPaises) {
        this.tbPaises = tbPaises;
    }

    public TbPaises getTbPaises() {
        return tbPaises;
    }

    public List<TbPaises> getTbPaisesLista() {
        return tbPaisesLista;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create() throws PreexistingEntityException, RollbackFailureException, Exception {

        if (this.tbPaises.getTbEstadosCollection() == null) {
            this.tbPaises.setTbEstadosCollection(new ArrayList<TbEstados>());
        }

        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<TbEstados> attachedTbEstadosCollection = new ArrayList<TbEstados>();

            this.tbPaises.setTbEstadosCollection(attachedTbEstadosCollection);

            if (this.tbPaises.getHand() == null) {
                em.persist(this.tbPaises);
            } else {
                em.merge(this.tbPaises);
            }

            em.getTransaction().commit();
        } catch (Exception ex) {
            try {
                em.getTransaction().rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("Problemas ao salvar o registro.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em.getTransaction().begin();
            em = getEntityManager();
            TbPaises tbPaises;
            try {
                tbPaises = em.getReference(TbPaises.class, id);
                tbPaises.getHand();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tbPaises with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TbEstados> tbEstadosCollectionOrphanCheck = tbPaises.getTbEstadosCollection();
            for (TbEstados tbEstadosCollectionOrphanCheckTbEstados : tbEstadosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbPaises (" + tbPaises + ") cannot be destroyed since the TbEstados " + tbEstadosCollectionOrphanCheckTbEstados + " in its tbEstadosCollection field has a non-nullable tbPaisesHand field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tbPaises);
            em.getTransaction().commit();
        } catch (Exception ex) {
            try {
                em.getTransaction().rollback();
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

    private List<TbPaises> findTbPaisesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from TbPaises as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public TbPaises findTbPaises(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbPaises.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbPaisesCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from TbPaises as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
