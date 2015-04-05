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
import Modelos.TbCidades;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelos.TbEstados;
import Modelos.TbPaises;
import Modelos.TbPessoa;
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
public class TbCidadesJpaController implements Serializable {

    private EntityManagerFactory emf = null;
    private EntityManager em = null;
    private TbCidades tbCidade = new TbCidades();
    private List<TbEstados> listTbEstados = new ArrayList<>();

    public TbCidadesJpaController() {
        emf = Persistence.createEntityManagerFactory("ProjectManagementSystemPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public TbCidades getTbCidade() {
        return tbCidade;
    }

    public void setTbCidade(TbCidades tbCidade) {
        this.tbCidade = tbCidade;
    }

    public List<TbEstados> getListTbEstados() {

        TbEstadosJpaController controle = new TbEstadosJpaController();
        this.listTbEstados = controle.retornaCollectionEstados();

        return listTbEstados;
    }

    public void setListTbEstados(List<TbEstados> listTbEstados) {
        this.listTbEstados = listTbEstados;
    }

    public void create() throws PreexistingEntityException, RollbackFailureException, Exception {
        try {
            em = getEntityManager();
            em.getTransaction().begin();

            if (this.tbCidade.getHand() == null) {
                Util utilitarios = new Util();
                this.tbCidade.setHand(utilitarios.contadorObjetos("TbCidades"));
                em.persist(this.tbCidade);
                FacesMessage fm = new FacesMessage("Registro salvo com sucesso!");
                FacesContext.getCurrentInstance().addMessage(null, fm);
            } else {
                em.merge(this.tbCidade);
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
            TbCidades tbCidades;
            try {
                tbCidades = em.getReference(TbCidades.class, id);
                tbCidades.getHand();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("Este registro não existe.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TbPessoa> tbPessoaCollectionOrphanCheck = tbCidades.getTbPessoaCollection();
            for (TbPessoa tbPessoaCollectionOrphanCheckTbPessoa : tbPessoaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("A Cidade (" + tbCidades + ") não pode ser excluído pois esta sendo usado no Cliente/Fornecedor " + tbPessoaCollectionOrphanCheckTbPessoa.getNomeFantasia() + ".");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            TbEstados tbEstadosHand = tbCidades.getTbEstadosHand();
            if (tbEstadosHand != null) {
                tbEstadosHand.getTbCidadesCollection().remove(tbCidades);
                tbEstadosHand = em.merge(tbEstadosHand);
            }
            em.remove(tbCidades);
            em.getTransaction().begin();
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

    public TbCidades findTbCidades(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbCidades.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbCidadesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TbCidades> rt = cq.from(TbCidades.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<TbCidades> retornaCollectionCidades() {

        em = getEntityManager();
        Query query = em.createNamedQuery("TbCidades.findAll");
        return query.getResultList();
    }

}
