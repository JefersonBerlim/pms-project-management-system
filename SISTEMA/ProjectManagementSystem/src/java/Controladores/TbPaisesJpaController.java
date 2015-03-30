/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import Controladores.exceptions.PreexistingEntityException;
import Controladores.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelos.TbEstados;
import Modelos.TbPaises;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author jeferson
 */
@ManagedBean
public class TbPaisesJpaController implements Serializable {

    private EntityManagerFactory emf = null;
    private EntityManager em = null;
    public TbPaises tbPais;

    public TbPaisesJpaController() {
        emf = Persistence.createEntityManagerFactory("ProjectManagementSystemPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public TbPaises getTbPais() {
        return tbPais;
    }

    public void setTbPais(TbPaises tbPais) {
        this.tbPais = tbPais;
    }

    public void create(TbPaises tbPaises) throws PreexistingEntityException, RollbackFailureException, Exception {
        try {
            em = getEntityManager();
            em.getTransaction().begin();

            if (tbPaises.getHand() == null) {
                em.persist(tbPaises);
            } else {
                em.merge(tbPaises);
            }

            em.getTransaction().commit();

        } catch (Exception ex) {
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void remove(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {

        try {
            em.getTransaction().begin();
            em = getEntityManager();
            TbPaises tbPaises;
            try {
                tbPaises = em.getReference(TbPaises.class, id);
                tbPaises.getHand();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("Este registro não existe.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TbEstados> tbEstadosCollectionOrphanCheck = tbPaises.getTbEstadosCollection();
            for (TbEstados tbEstadosCollectionOrphanCheckTbEstados : tbEstadosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("O País (" + tbPaises + ") não pode ser excluído pois esta sendo usado no estado " + tbEstadosCollectionOrphanCheckTbEstados.getEstado() + ".");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tbPaises);
            em.getTransaction().commit();
        } catch (NonexistentEntityException | IllegalOrphanException ex) {
            try {
                em.getTransaction().rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("Um erro ocorreu ao tentar rverter a transação.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
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
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TbPaises> rt = cq.from(TbPaises.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
