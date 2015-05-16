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
import Modelos.TbApontamentosRecursos;
import Modelos.TbFuncionariosRecursos;
import Modelos.TbProjetosRecursos;
import Modelos.TbRecursos;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelos.TbRecursosServicos;
import java.util.ArrayList;
import java.util.Collection;
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
 * @author jeferson
 */
@ManagedBean
@ViewScoped
public class TbRecursosJpaController implements Serializable {

    private EntityManagerFactory emf = null;
    private EntityManager em = null;
    private TbRecursos tbRecursos = new TbRecursos();
    private List<TbRecursos> listTbRecursos = new ArrayList<>();

    public TbRecursosJpaController() {
        emf = Persistence.createEntityManagerFactory("ProjectManagementSystemPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public TbRecursos getTbRecursos() {
        return tbRecursos;
    }

    public void setTbRecursos(TbRecursos tbRecursos) {
        this.tbRecursos = tbRecursos;
    }

    public List<TbRecursos> getListTbRecursos() {
        return listTbRecursos;
    }

    public void setListTbRecursos(List<TbRecursos> listTbRecursos) {
        this.listTbRecursos = listTbRecursos;
    }

    public void create() throws PreexistingEntityException, RollbackFailureException, Exception {
        try {
            em = getEntityManager();
            em.getTransaction().begin();

            if (this.tbRecursos.isTmpEhInativo()) {
                this.tbRecursos.setEhInativo("S");
            } else {
                this.tbRecursos.setEhInativo(null);
            }

            if (this.tbRecursos.isTmpFuncionarioSimultaneo()) {
                this.tbRecursos.setFuncionarioSimultaneo("S");
            } else {
                this.tbRecursos.setFuncionarioSimultaneo(null);
            }

            if (this.tbRecursos.getHand() == null) {
                Util utilitarios = new Util();
                this.tbRecursos.setHand(utilitarios.contadorObjetos("TbRecursos"));
                em.persist(this.tbRecursos);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Registro salvo com sucesso!"));
            } else {
                em.merge(this.tbRecursos);
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
                tbRecursos = em.getReference(TbRecursos.class, id);
                tbRecursos.getHand();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("Este registro não existe.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TbProjetosRecursos> tbProjetosRecursosCollectionOrphanCheck = tbRecursos.getTbProjetosRecursosCollection();
            for (TbProjetosRecursos tbProjetosRecursosCollectionOrphanCheckTbProjetosRecursos : tbProjetosRecursosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("O Recurso (" + tbRecursos.getDescricao() + ") não pode ser excluído pois esta sendo usado nos projetos.");
            }
            Collection<TbRecursosServicos> tbRecursosServicosCollectionOrphanCheck = tbRecursos.getTbRecursosServicosCollection();
            for (TbRecursosServicos tbRecursosServicosCollectionOrphanCheckTbRecursosServicos : tbRecursosServicosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("O Recurso (" + tbRecursos.getDescricao() + ") não pode ser excluído pois esta sendo usado nos projetos.");
            }
            Collection<TbApontamentosRecursos> tbApontamentosRecursosCollectionOrphanCheck = tbRecursos.getTbApontamentosRecursosCollection();
            for (TbApontamentosRecursos tbApontamentosRecursosCollectionOrphanCheckTbApontamentosRecursos : tbApontamentosRecursosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("O Recurso (" + tbRecursos.getDescricao() + ") não pode ser excluído pois esta sendo usado nos apontamentos dos Funcionários.");
            }
            Collection<TbFuncionariosRecursos> tbFuncionariosRecursosCollectionOrphanCheck = tbRecursos.getTbFuncionariosRecursosCollection();
            for (TbFuncionariosRecursos tbFuncionariosRecursosCollectionOrphanCheckTbFuncionariosRecursos : tbFuncionariosRecursosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("O Recurso (" + tbRecursos.getDescricao() + ") não pode ser excluído pois esta sendo usado nos apontamentos dos Funcionários.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tbRecursos);
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

    public TbRecursos findTbRecursos(Integer id) {
        em = getEntityManager();
        try {
            return em.find(TbRecursos.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbRecursosCount() {
        em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TbRecursos> rt = cq.from(TbRecursos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<TbRecursos> retornaCollectionRecursos() {
        em = getEntityManager();
        Query query = em.createNamedQuery("TbRecursos.findAll");
        return query.getResultList();
    }

}
