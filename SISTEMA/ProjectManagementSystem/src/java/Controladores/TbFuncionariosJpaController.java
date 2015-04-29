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
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;

/**
 *
 * @author BERLIM
 */
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

    public void create(TbFuncionarios tbFuncionarios) throws PreexistingEntityException, RollbackFailureException, Exception {
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            if (this.tbFuncionarios.getHand() == null) {
                Util utilitarios = new Util();
                this.tbFuncionarios.setHand(utilitarios.contadorObjetos("TbFuncionarios"));
                em.persist(this.tbFuncionarios);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Registro salvo com sucesso!"));
            } else {
                em.merge(this.tbFuncionarios);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Registro atualizado com sucesso!"));
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Problemas ao persistir o regitsto."));
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
            try {
                tbFuncionarios = em.getReference(TbFuncionarios.class, id);
                tbFuncionarios.getHand();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("Este registro não existe.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TbApontamentosFuncionarios> tbApontamentosFuncionariosCollectionOrphanCheck = tbFuncionarios.getTbApontamentosFuncionariosCollection();
            for (TbApontamentosFuncionarios tbApontamentosFuncionariosCollectionOrphanCheckTbApontamentosFuncionarios : tbApontamentosFuncionariosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("O Funcionário (" + tbFuncionarios.getNome() + ") não pode ser excluído pois esta sendo usado nos Apontamentos de Funcionários.");
            }
            Collection<TbProjetoFuncionarios> tbProjetoFuncionariosCollectionOrphanCheck = tbFuncionarios.getTbProjetoFuncionariosCollection();
            for (TbProjetoFuncionarios tbProjetoFuncionariosCollectionOrphanCheckTbProjetoFuncionarios : tbProjetoFuncionariosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("O Funcionário (" + tbFuncionarios.getNome() + ") não pode ser excluído pois esta vinculado á Projetos.");
            }
            Collection<TbApontamentosMateriais> tbApontamentosMateriaisCollectionOrphanCheck = tbFuncionarios.getTbApontamentosMateriaisCollection();
            for (TbApontamentosMateriais tbApontamentosMateriaisCollectionOrphanCheckTbApontamentosMateriais : tbApontamentosMateriaisCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("O Funcionário (" + tbFuncionarios.getNome() + ") não pode ser excluído pois existem apontamentos de materiais.");
            }
            Collection<TbProjetos> tbProjetosCollectionOrphanCheck = tbFuncionarios.getTbProjetosCollection();
            for (TbProjetos tbProjetosCollectionOrphanCheckTbProjetos : tbProjetosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("O Funcionário (" + tbFuncionarios.getNome() + ") não pode ser excluído pois esta vinculado á Projetos.");
            }
            Collection<TbFuncionariosRecursos> tbFuncionariosRecursosCollectionOrphanCheck = tbFuncionarios.getTbFuncionariosRecursosCollection();
            for (TbFuncionariosRecursos tbFuncionariosRecursosCollectionOrphanCheckTbFuncionariosRecursos : tbFuncionariosRecursosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("O Funcionário (" + tbFuncionarios.getNome() + ") não pode ser excluído pois esta vinculado á Recursos do Projeto.");
            }
            Collection<TbFuncionarioTurnoSemana> tbFuncionarioTurnoSemanaCollectionOrphanCheck = tbFuncionarios.getTbFuncionarioTurnoSemanaCollection();
            for (TbFuncionarioTurnoSemana tbFuncionarioTurnoSemanaCollectionOrphanCheckTbFuncionarioTurnoSemana : tbFuncionarioTurnoSemanaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("O Funcionário (" + tbFuncionarios.getNome() + ") não pode ser excluído pois esta vinculado á Turnos de Funcionários.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tbFuncionarios);
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

    public TbFuncionarios findTbFuncionarios(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbFuncionarios.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbFuncionariosCount() {
        EntityManager em = getEntityManager();
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
