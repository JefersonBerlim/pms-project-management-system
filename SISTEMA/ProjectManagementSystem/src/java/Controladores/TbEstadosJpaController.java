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
import Modelos.TbPaises;
import Modelos.TbCidades;
import Modelos.TbEstados;
import Utilitarios.Util;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author jeferson
 */
@ManagedBean
@ViewScoped
public class TbEstadosJpaController implements Serializable {

    private EntityManagerFactory emf = null;
    private EntityManager em = null;
    private TbEstados tbEstado = new TbEstados();
    private List<TbPaises> listTbPaises = new ArrayList<>();

    public TbEstadosJpaController() {
        emf = Persistence.createEntityManagerFactory("ProjectManagementSystemPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public TbEstados getTbEstado() {
        return tbEstado;
    }

    public void setTbEstado(TbEstados tbEstado) {
        this.tbEstado = tbEstado;
    }

    public List<TbPaises> getListTbPaises() {

        TbPaisesJpaController controle = new TbPaisesJpaController();
        this.listTbPaises = controle.retornaCollectionPaises();

        return listTbPaises;
    }

    public void create() throws PreexistingEntityException, RollbackFailureException, Exception {
        try {
            em = getEntityManager();
            em.getTransaction().begin();

            if (this.tbEstado.getHand() == null) {
                Util utilitarios = new Util();
                this.tbEstado.setHand(utilitarios.contadorObjetos("TbEstados"));
                em.persist(this.tbEstado);
                FacesMessage fm = new FacesMessage("Registro salvo com sucesso!");
                FacesContext.getCurrentInstance().addMessage(null, fm);
            } else {
                em.merge(this.tbEstado);
                FacesMessage fm = new FacesMessage("Registro atualizado com sucesso!");
                FacesContext.getCurrentInstance().addMessage(null, fm);
            }

            em.getTransaction().commit();

        } catch (Exception ex) {
            FacesMessage fm = new FacesMessage("Problemas ao persistir o regitsto.");
            FacesContext.getCurrentInstance().addMessage(null, fm);
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {

        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TbEstados tbEstados;
            try {
                tbEstados = em.getReference(TbEstados.class, id);
                tbEstados.getHand();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("Este registro não existe.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TbCidades> tbCidadesCollectionOrphanCheck = tbEstados.getTbCidadesCollection();
            for (TbCidades tbCidadesCollectionOrphanCheckTbCidades : tbCidadesCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<>();
                }
                illegalOrphanMessages.add("O Estado (" + tbEstado + ") não pode ser excluído pois esta sendo usado na Cidade " + tbCidadesCollectionOrphanCheckTbCidades.getCidade() + ".");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            TbPaises tbPaisesHand = tbEstados.getTbPaisesHand();
            if (tbPaisesHand != null) {
                tbPaisesHand.getTbEstadosCollection().remove(tbEstados);
                tbPaisesHand = em.merge(tbPaisesHand);
            }
            em.remove(tbEstados);
            em.getTransaction().commit();
        } catch (NonexistentEntityException | IllegalOrphanException ex) {
            try {
                em.getTransaction().rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("Um erro ocorreu ao tentar reverter a transação.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public TbEstados findTbEstados(Integer id) {
        try {
            return em.find(TbEstados.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbEstadosCount() {
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TbEstados> rt = cq.from(TbEstados.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<TbEstados> retornaCollectionEstados() {

        em = getEntityManager();
        Query query = em.createNamedQuery("TbEstados.findAll");
        return query.getResultList();
    }

}
