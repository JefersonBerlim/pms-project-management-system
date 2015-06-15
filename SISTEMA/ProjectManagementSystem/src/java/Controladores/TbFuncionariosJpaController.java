/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelos.TbApontamentosFuncionarios;
import java.util.ArrayList;
import java.util.Collection;
import Modelos.TbProjetoFuncionarios;
import Modelos.TbApontamentosMateriais;
import Modelos.TbProjetos;
import Modelos.TbFuncionariosRecursos;
import Modelos.TbFuncionarioTurnoSemana;
import Modelos.TbFuncionarios;
import Utilitarios.Util;
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
 * @author BERLIM
 */
@ManagedBean
@ViewScoped
public class TbFuncionariosJpaController implements Serializable {

    private EntityManagerFactory emf = null;
    private EntityManager em = null;
    private TbFuncionarios tbFuncionarios = new TbFuncionarios();

    public TbFuncionariosJpaController() {
        emf = Persistence.createEntityManagerFactory("ProjectManagementSystemPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public TbFuncionarios getTbFuncionarios() {
        return tbFuncionarios;
    }

    public void setTbFuncionarios(TbFuncionarios tbFuncionarios) {
        this.tbFuncionarios = tbFuncionarios;
    }

    public void create() throws Exception {
        try {
            em = getEntityManager();
            em.getTransaction().begin();

            if (this.tbFuncionarios.isTmpEhInativo()) {
                this.tbFuncionarios.setEhInativo("S");
            } else {
                this.tbFuncionarios.setEhInativo("N");
            }

            if (this.tbFuncionarios.isTmpEhPlanejador()) {
                this.tbFuncionarios.setEhPlanejador("S");
            } else {
                this.tbFuncionarios.setEhPlanejador("N");
            }

            if (this.tbFuncionarios.getHand() == null) {

                Util utilitarios = new Util();
                this.tbFuncionarios.setHand(utilitarios.contadorObjetos("TbFuncionarios"));
                em.persist(this.tbFuncionarios);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro salvo com sucesso!", null));
            } else {

                em.merge(this.tbFuncionarios);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro atualizado com sucesso!", null));
            }

            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            FacesContext.getCurrentInstance().addMessage(ex.toString(),
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Problemas ao persistir o regitsto.", null));
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, Exception {

        try {
            em = getEntityManager();
            em.getTransaction().begin();
            tbFuncionarios = em.getReference(TbFuncionarios.class, id);
            tbFuncionarios.getHand();

            List<String> illegalOrphanMessages = null;

            Collection<TbApontamentosFuncionarios> tbApontamentosFuncionariosCollection = tbFuncionarios.getTbApontamentosFuncionariosCollection();
            for (TbApontamentosFuncionarios tbApontamentosFuncionarios : tbApontamentosFuncionariosCollection) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("O Funcionário (" + tbFuncionarios.getNome()
                        + ") não pode ser excluído pois esta sendo usado nos Apontamentos de Funcionários.");
            }

            Collection<TbProjetoFuncionarios> tbProjetoFuncionariosCollection = tbFuncionarios.getTbProjetoFuncionariosCollection();
            for (TbProjetoFuncionarios tbProjetoFuncionarios : tbProjetoFuncionariosCollection) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("O Funcionário (" + tbFuncionarios.getNome()
                        + ") não pode ser excluído pois esta vinculado á Projetos.");
            }

            Collection<TbApontamentosMateriais> tbApontamentosMateriaisCollection = tbFuncionarios.getTbApontamentosMateriaisCollection();
            for (TbApontamentosMateriais tbApontamentosMateriais : tbApontamentosMateriaisCollection) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("O Funcionário (" + tbFuncionarios.getNome()
                        + ") não pode ser excluído pois existem apontamentos de materiais.");
            }

            Collection<TbProjetos> tbProjetosCollection = tbFuncionarios.getTbProjetosCollection();
            for (TbProjetos tbProjetos : tbProjetosCollection) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("O Funcionário (" + tbFuncionarios.getNome()
                        + ") não pode ser excluído pois esta vinculado á Projetos.");
            }

            Collection<TbFuncionariosRecursos> tbFuncionariosRecursosCollection = tbFuncionarios.getTbFuncionariosRecursosCollection();
            for (TbFuncionariosRecursos tbFuncionariosRecursos : tbFuncionariosRecursosCollection) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("O Funcionário (" + tbFuncionarios.getNome()
                        + ") não pode ser excluído pois esta vinculado á Recursos do Projeto.");
            }

            Collection<TbFuncionarioTurnoSemana> tbFuncionarioTurnoSemanaCollection = tbFuncionarios.getTbFuncionarioTurnoSemanaCollection();
            for (TbFuncionarioTurnoSemana tbFuncionarioTurnoSemana : tbFuncionarioTurnoSemanaCollection) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("O Funcionário (" + tbFuncionarios.getNome()
                        + ") não pode ser excluído pois esta vinculado á Turnos de Funcionários.");
            }

            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }

            em.remove(tbFuncionarios);
        } catch (IllegalOrphanException ex) {
            em.getTransaction().rollback();
            FacesContext.getCurrentInstance().addMessage(ex.toString(),
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Registro sendo utilizado por outros cadastros.", null));
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

    public TbFuncionarios findTbFuncionarios(Integer id) {
        em = getEntityManager();
        try {
            return em.find(TbFuncionarios.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbFuncionariosCount() {
        em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TbFuncionarios> rt = cq.from(TbFuncionarios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<TbFuncionarios> retornaCollectionFuncionarios() {
        em = getEntityManager();
        Query query = em.createNamedQuery("TbFuncionarios.findAll");
        return query.getResultList();
    }

}
