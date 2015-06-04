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
import Modelos.TbFuncionarioTurnoSemana;
import Modelos.TbTurnos;
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

@ManagedBean
@ViewScoped
public class TbTurnosJpaController implements Serializable {

    private EntityManagerFactory emf = null;
    private EntityManager em = null;
    private TbTurnos tbTurnos = new TbTurnos();

    public TbTurnosJpaController() {
        emf = Persistence.createEntityManagerFactory("ProjectManagementSystemPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public TbTurnos getTbTurnos() {
        return tbTurnos;
    }

    public void setTbTurnos(TbTurnos tbTurnos) {
        this.tbTurnos = tbTurnos;
    }

    public void create() throws Exception {

        if (this.validaDatas()) {
            try {
                em = getEntityManager();
                em.getTransaction().begin();

                if (this.tbTurnos.getHand() == null) {
                    Util utilitarios = new Util();
                    this.tbTurnos.setHand(utilitarios.contadorObjetos("TbTurnos"));
                    em.persist(this.tbTurnos);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Registro salvo com sucesso!"));
                } else {
                    em.merge(this.tbTurnos);
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

    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, Exception {

        try {
            em = getEntityManager();
            em.getTransaction().begin();

            tbTurnos = em.getReference(TbTurnos.class, id);
            tbTurnos.getHand();

            List<String> illegalOrphanMessages = null;
            
            Collection<TbFuncionarioTurnoSemana> tbFuncionarioTurnoSemanaCollection = tbTurnos.getTbFuncionarioTurnoSemanaCollection();
            for (TbFuncionarioTurnoSemana tbFuncionarioTurnoSemana : tbFuncionarioTurnoSemanaCollection) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<>();
                }
                illegalOrphanMessages.add("O Turno (" + tbTurnos.getDescricao() + ") não pode ser excluído pois esta sendo usado no Projeto "
                        + tbFuncionarioTurnoSemana.getHand() + " - "
                        + tbFuncionarioTurnoSemana.getTbDiaSemanaHand().getDescricao() + " - "
                        + tbFuncionarioTurnoSemana.getTbFuncionariosHand().getNome() + " - "
                        + tbFuncionarioTurnoSemana.getTbTurnosHand().getDescricao()
                        + ".");
            }
            
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            
            em.remove(tbTurnos);
        } catch (IllegalOrphanException ex) {
            em.getTransaction().rollback();
            FacesContext.getCurrentInstance().addMessage(ex.toString(),
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!",
                            "Registro sendo utilizado por outros cadastros."));
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

    public TbTurnos findTbTurnos(Integer id) {
        em = getEntityManager();
        try {
            return em.find(TbTurnos.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbTurnosCount() {
        em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TbTurnos> rt = cq.from(TbTurnos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public boolean validaDatas() {
        boolean retorno = true;
        String mensagem = "";
        Util util = new Util();

        //INICIO EXPEDIETE
        if (tbTurnos.getHorarioInicial().getTime() > tbTurnos.getAlmocoInicio().getTime()) {

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(
                            FacesMessage.SEVERITY_ERROR, "Error!", "Início do Expediente não pode ser superior o Início do Almoço!"));

            retorno = false;
        }

        if (tbTurnos.getHorarioInicial().getTime() > tbTurnos.getAlmocoFim().getTime()) {

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(
                            FacesMessage.SEVERITY_ERROR, "Error!", "Início do Expediente não pode ser superior o Fim do Almoço!"));

            retorno = false;
        }

        if (tbTurnos.getHorarioInicial().getTime() > tbTurnos.getHorarioFinal().getTime()) {

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(
                            FacesMessage.SEVERITY_ERROR, "Error!", "Início do Expediente não pode ser superior o Fim do Expediente!"));

            retorno = false;
        }

        // ALMOÇO INICIO
        if (tbTurnos.getAlmocoInicio().getTime() < tbTurnos.getHorarioInicial().getTime()) {

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(
                            FacesMessage.SEVERITY_ERROR, "Error!", "Início do Almoço não pode ser inferior o Início do Expediente!"));

            retorno = false;
        }

        if (tbTurnos.getAlmocoInicio().getTime() > tbTurnos.getAlmocoFim().getTime()) {

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(
                            FacesMessage.SEVERITY_ERROR, "Error!", "Início do Almoço não pode ser superior o Fim do Almoço!"));

            retorno = false;
        }

        if (tbTurnos.getAlmocoInicio().getTime() > tbTurnos.getHorarioFinal().getTime()) {

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(
                            FacesMessage.SEVERITY_ERROR, "Error!", "Início do Almoço não pode ser superior o Fim do Expediente!"));

            retorno = false;
        }

        //FIM ALMOÇO
        if (tbTurnos.getAlmocoFim().getTime() < tbTurnos.getHorarioInicial().getTime()) {

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(
                            FacesMessage.SEVERITY_ERROR, "Error!", "Fim do Almoço não pode ser inferior o Início do Expediente!"));

            retorno = false;
        }

        if (tbTurnos.getAlmocoFim().getTime() < tbTurnos.getAlmocoInicio().getTime()) {

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(
                            FacesMessage.SEVERITY_ERROR, "Error!", "Fim do Almoço não pode ser inferior o Início do Almoço!"));

            retorno = false;
        }

        if (tbTurnos.getAlmocoFim().getTime() > tbTurnos.getHorarioFinal().getTime()) {

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(
                            FacesMessage.SEVERITY_ERROR, "Error!", "Fim do Almoço não pode ser superior o Fim do Expediente!"));

            retorno = false;
        }

        //FIM EXPEDIENTE
        if (tbTurnos.getHorarioFinal().getTime() < tbTurnos.getHorarioInicial().getTime()) {

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(
                            FacesMessage.SEVERITY_ERROR, "Error!", "Fim do Expediente não pode ser inferior o Início do Expediente!"));

            retorno = false;
        }

        if (tbTurnos.getHorarioFinal().getTime() < tbTurnos.getAlmocoInicio().getTime()) {

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(
                            FacesMessage.SEVERITY_ERROR, "Error!", "Fim do Expediente não pode ser inferior o Início do Almoço!"));

            retorno = false;
        }

        if (tbTurnos.getHorarioFinal().getTime() < tbTurnos.getAlmocoFim().getTime()) {

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(
                            FacesMessage.SEVERITY_ERROR, "Error!", "Fim do Expediente não pode ser inferior o Fim do Almoço!"));

            retorno = false;
        }

        return retorno;

    }

    public List<TbTurnos> retornaCollectionTurnos() {
        em = getEntityManager();
        Query query = em.createNamedQuery("TbTurnos.findAll");
        return query.getResultList();
    }

}
