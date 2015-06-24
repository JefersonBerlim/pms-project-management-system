/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.NonexistentEntityException;
import Modelos.TbFuncionarios;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelos.TbRecursos;
import Modelos.TbFuncionariosRecursos;
import Utilitarios.Util;
import java.util.ArrayList;
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
public class TbFuncionariosRecursosJpaController implements Serializable {

    private EntityManagerFactory emf = null;
    private EntityManager em = null;
    private TbFuncionariosRecursos tbFuncionariosRecursos = new TbFuncionariosRecursos();
    private List<TbFuncionarios> listTbFuncionarios = new ArrayList<>();
    private List<TbRecursos> listTbRecursosNaoVinculados = new ArrayList<>();
    private List<TbRecursos> listTbRecursosVinculados = new ArrayList<>();
    private List retornaRegistros = new ArrayList<>();

    public TbFuncionariosRecursosJpaController() {
        emf = Persistence.createEntityManagerFactory("ProjectManagementSystemPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public TbFuncionariosRecursos getTbFuncionariosRecursos() {
        return tbFuncionariosRecursos;
    }

    public void setTbFuncionariosRecursos(TbFuncionariosRecursos tbFuncionariosRecursos) {
        this.tbFuncionariosRecursos = tbFuncionariosRecursos;
    }

    public List<TbFuncionarios> getListTbFuncionarios() {

        TbFuncionariosJpaController controle = new TbFuncionariosJpaController();
        this.listTbFuncionarios = controle.retornaCollectionFuncionariosAtivos();

        return listTbFuncionarios;
    }

    public void setListTbFuncionarios(List<TbFuncionarios> listTbFuncionarios) {
        this.listTbFuncionarios = listTbFuncionarios;
    }

    public List<TbRecursos> getListTbRecursosNaoVinculados() {

        em = getEntityManager();
        listTbRecursosNaoVinculados = new ArrayList<>();

        if (tbFuncionariosRecursos.getTbFuncionariosHand() != null) {
            Query vinculos = em.createNamedQuery("TbRecursos.recursosNaoVinculadosFuncionario")
                    .setParameter("funcionario", tbFuncionariosRecursos.getTbFuncionariosHand().getHand());
            listTbRecursosNaoVinculados = vinculos.getResultList();
        }
        if (em != null) {
            em.close();
        }

        return listTbRecursosNaoVinculados;

    }

    public void setListTbRecursosNaoVinculados(List<TbRecursos> listTbRecursosNaoVinculados) {
        this.listTbRecursosNaoVinculados = listTbRecursosNaoVinculados;
    }

    public List<TbRecursos> getListTbRecursosVinculados() {

        em = getEntityManager();
        listTbRecursosVinculados = new ArrayList<>();

        if (tbFuncionariosRecursos.getTbFuncionariosHand() != null) {
            Query vinculos = em.createNamedQuery("TbRecursos.recursosVinculadosFuncionario")
                    .setParameter("funcionario", tbFuncionariosRecursos.getTbFuncionariosHand().getHand());
            listTbRecursosVinculados = vinculos.getResultList();
        }
        if (em != null) {
            em.close();
        }

        return listTbRecursosVinculados;
    }

    public void setListTbRecursosVinculados(List<TbRecursos> listTbRecursosVinculados) {
        this.listTbRecursosVinculados = listTbRecursosVinculados;
    }

    public void create() throws Exception {
        try {
            em = getEntityManager();
            em.getTransaction().begin();

            if (this.tbFuncionariosRecursos.isTmpAutomatizaProcesso()) {
                tbFuncionariosRecursos.setAutomatizaProcesso("S");
            } else {
                tbFuncionariosRecursos.setAutomatizaProcesso("N");
            }

            if (validaInclusao()) {
                Util utilitarios = new Util();
                this.tbFuncionariosRecursos.setHand(utilitarios.contadorObjetos("TbFuncionariosRecursos"));
                em.persist(this.tbFuncionariosRecursos);
                em.getTransaction().commit();

                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Registro salvo com sucesso!", null));
            } else {
                em.merge(this.tbFuncionariosRecursos);
                em.getTransaction().commit();

                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Registro atualizado com sucesso!", null));
            }
        } catch (Exception ex) {
            em.getTransaction().rollback();
            FacesContext.getCurrentInstance().addMessage(ex.toString(),
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Problemas ao persistir o regitsto.", ex.toString()));
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, Exception {
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            tbFuncionariosRecursos = em.getReference(TbFuncionariosRecursos.class, id);
            tbFuncionariosRecursos.getHand();

            em.remove(tbFuncionariosRecursos);
        } catch (EntityNotFoundException enfe) {
            em.getTransaction().rollback();
            FacesContext.getCurrentInstance().addMessage(enfe.toString(),
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Este registro não existe.", null));
        } catch (Exception re) {
            em.getTransaction().rollback();
            FacesContext.getCurrentInstance().addMessage(re.toString(),
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Um erro ocorreu ao tentar reverter a transação.", null));
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public TbFuncionariosRecursos findTbFuncionariosRecursos(Integer id) {
        try {
            em = getEntityManager();
            return em.find(TbFuncionariosRecursos.class, id);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public int getTbFuncionariosRecursosCount() {
        try {
            em = getEntityManager();
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TbFuncionariosRecursos> rt = cq.from(TbFuncionariosRecursos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    private boolean validaInclusao() {

        Query vinculos = em.createNamedQuery("TbFuncionariosRecursos.retornaRegistros")
                .setParameter("recurso", tbFuncionariosRecursos.getTbRecursosHand())
                .setParameter("funcionario", tbFuncionariosRecursos.getTbFuncionariosHand());
        retornaRegistros = vinculos.getResultList();

        return retornaRegistros.isEmpty();

    }

}
