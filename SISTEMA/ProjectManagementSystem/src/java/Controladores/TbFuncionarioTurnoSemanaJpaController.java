/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelos.TbTurnos;
import Modelos.TbFuncionarios;
import Modelos.TbDiaSemana;
import Modelos.TbFuncionarioTurnoSemana;
import Utilitarios.Util;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;

/**
 *
 * @author jeferson
 */
@ManagedBean
@ViewScoped
public class TbFuncionarioTurnoSemanaJpaController implements Serializable {

    private EntityManagerFactory emf = null;
    private EntityManager em = null;
    private TbFuncionarioTurnoSemana tbFuncionarioTurnoSemana = new TbFuncionarioTurnoSemana();
    private List<TbFuncionarios> listTbFuncionarios = new ArrayList<>();
    private List<TbDiaSemana> listTbDiaSemana = new ArrayList<>();
    private List<TbTurnos> listTbTurnos = new ArrayList<>();

    public TbFuncionarioTurnoSemanaJpaController() {
        emf = Persistence.createEntityManagerFactory("ProjectManagementSystemPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public TbFuncionarioTurnoSemana getTbFuncionarioTurnoSemana() {
        return tbFuncionarioTurnoSemana;
    }

    public void setTbFuncionarioTurnoSemana(TbFuncionarioTurnoSemana tbFuncionarioTurnoSemana) {
        this.tbFuncionarioTurnoSemana = tbFuncionarioTurnoSemana;
    }

    public List<TbFuncionarios> getListTbFuncionarios() {
        TbFuncionariosJpaController controle = new TbFuncionariosJpaController();
        this.listTbFuncionarios = controle.retornaCollectionFuncionarios();

        return listTbFuncionarios;
    }

    public void setListTbFuncionarios(List<TbFuncionarios> listTbFuncionarios) {
        this.listTbFuncionarios = listTbFuncionarios;
    }

    public List<TbDiaSemana> getListTbDiaSemana() {
        TbDiaSemanaJpaController controle = new TbDiaSemanaJpaController();
        this.listTbDiaSemana = controle.retornaCollectionDiaSemana();

        return listTbDiaSemana;
    }

    public void setListTbDiaSemana(List<TbDiaSemana> listTbDiaSemana) {
        this.listTbDiaSemana = listTbDiaSemana;
    }

    public List<TbTurnos> getListTbTurnos() {
        TbTurnosJpaController controle = new TbTurnosJpaController();
        this.listTbTurnos = controle.retornaCollectionTurnos();

        return listTbTurnos;
    }

    public void setListTbTurnos(List<TbTurnos> listTbTurnos) {
        this.listTbTurnos = listTbTurnos;
    }

    public void create() throws Exception {

        try {
            em = getEntityManager();
            em.getTransaction().begin();
            
            if (this.tbFuncionarioTurnoSemana.getHand() == null) {
                
                Util utilitarios = new Util();
                this.tbFuncionarioTurnoSemana.setHand(utilitarios.contadorObjetos("TbFuncionarioTurnoSemana"));
                em.persist(this.tbFuncionarioTurnoSemana);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Registro salvo com sucesso!"));
            } else {
                
                em.merge(this.tbFuncionarioTurnoSemana);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Registro atualizado com sucesso!"));
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            
            em.getTransaction().rollback();
            FacesContext.getCurrentInstance().addMessage(ex.toString(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Problemas ao persistir o regitsto."));
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
            tbFuncionarioTurnoSemana = em.getReference(TbFuncionarioTurnoSemana.class, id);
            tbFuncionarioTurnoSemana.getHand();
            em.remove(tbFuncionarioTurnoSemana);
            
        } catch (EntityNotFoundException enfe) {
            em.getTransaction().rollback();
            FacesContext.getCurrentInstance().addMessage(enfe.toString(),
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!",
                            "Este registro não existe."));
        } catch (Exception re) {
            em.getTransaction().rollback();
            FacesContext.getCurrentInstance().addMessage(re.toString(),
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!",
                            "Um erro ocorreu ao tentar reverter a transação."));
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public TbFuncionarioTurnoSemana findTbFuncionarioTurnoSemana(Integer id) {

        try {
            em = getEntityManager();
            return em.find(TbFuncionarioTurnoSemana.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbFuncionarioTurnoSemanaCount() {

        try {
            em = getEntityManager();
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
