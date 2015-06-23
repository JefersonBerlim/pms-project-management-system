/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import Modelos.TbServicos;
import Modelos.TbRecursos;
import Modelos.TbRecursosServicos;
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
public class TbRecursosServicosJpaController implements Serializable {

    private EntityManagerFactory emf = null;
    private EntityManager em = null;
    private TbRecursosServicos tbRecursosServicos = new TbRecursosServicos();
    private List<TbServicos> listTbServicos = new ArrayList<>();
    private List<TbRecursos> listTbRecursosNaoVinculados = new ArrayList<>();
    private List<TbRecursos> listTbRecursosVinculados = new ArrayList<>();
    private List retornaRegistros = new ArrayList<>();

    public TbRecursosServicosJpaController() {
        emf = Persistence.createEntityManagerFactory("ProjectManagementSystemPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public TbRecursosServicos getTbRecursosServicos() {
        return tbRecursosServicos;
    }

    public void setTbRecursosServicos(TbRecursosServicos tbRecursosServicos) {
        this.tbRecursosServicos = tbRecursosServicos;
    }

    public List<TbServicos> getListTbServicos() {
        TbServicosJpaController controle = new TbServicosJpaController();
        this.listTbServicos = controle.retornaCollectionServicosAtivos();

        return listTbServicos;
    }

    public void setListTbServicos(List<TbServicos> listTbServicos) {
        this.listTbServicos = listTbServicos;
    }

    public List<TbRecursos> getListTbRecursosNaoVinculados() {

        em = getEntityManager();
        listTbRecursosNaoVinculados = new ArrayList<>();

        if (tbRecursosServicos.getTbServicosHand()!= null) {
            Query vinculos = em.createNamedQuery("TbRecursos.recursosNaoVinculados")
                    .setParameter("servico", tbRecursosServicos.getTbServicosHand().getHand());
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

        if (tbRecursosServicos.getTbServicosHand() != null) {
            Query vinculos = em.createNamedQuery("TbRecursos.recursosVinculados")
                    .setParameter("servico", tbRecursosServicos.getTbServicosHand().getHand());
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

            if (this.tbRecursosServicos.isTmpAutomatizaProcesso()) {
                tbRecursosServicos.setAutomatizaProcesso("S");
            } else {
                tbRecursosServicos.setAutomatizaProcesso("N");
            }

            if (validaInclusao()) {
                Util utilitarios = new Util();
                this.tbRecursosServicos.setHand(utilitarios.contadorObjetos("TbRecursosServicos"));
                em.persist(this.tbRecursosServicos);
                em.getTransaction().commit();

                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Registro salvo com sucesso!", null));
            } else {
                em.merge(this.tbRecursosServicos);
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
            tbRecursosServicos = em.getReference(TbRecursosServicos.class, id);
            tbRecursosServicos.getHand();

            em.remove(tbRecursosServicos);
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

    public TbRecursosServicos findTbRecursosServicos(Integer id) {
        try {
            em = getEntityManager();
            return em.find(TbRecursosServicos.class, id);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    private boolean validaInclusao() {

        Query vinculos = em.createNamedQuery("TbRecursosServicos.retornaRegistros")
                .setParameter("servico", tbRecursosServicos.getTbServicosHand())
                .setParameter("recurso", tbRecursosServicos.getTbRecursosHand());
        retornaRegistros = vinculos.getResultList();

        return retornaRegistros.isEmpty();

    }

}
