/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Controle.exceptions.IllegalOrphanException;
import Controle.exceptions.NonexistentEntityException;
import Controle.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.TbApontamentosFuncionarios;
import java.util.ArrayList;
import java.util.Collection;
import Entidades.TbProjetos;
import Entidades.TbFuncionariosRecursos;
import Entidades.TbFuncionarioTurnoSemana;
import Entidades.TbFuncionarios;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ManagedBean
@ViewScoped
public class TbFuncionariosJpaController implements Serializable {

    private TbFuncionarios tbFuncionarios;
    private final EntityManagerFactory emf;
    private boolean inclusao;

    public TbFuncionariosJpaController() {
        emf = Persistence.createEntityManagerFactory("ProjectManagementSystemPU");
        tbFuncionarios = new TbFuncionarios();
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public TbFuncionarios getTbFuncionarios() {
        return tbFuncionarios;
    }

    public void setTbFuncionarios(TbFuncionarios TbFuncionarios) {
        this.tbFuncionarios = TbFuncionarios;
    }

    public boolean isInclusao() {
        return inclusao;
    }

    public void setInclusao(boolean inclusao) {
        this.inclusao = inclusao;
    }

    public void create() throws Exception {
        //obtendo o EntityManager
        EntityManager em = getEntityManager();
        try {
            //inicia o processo de transacao
            em.getTransaction().begin();
            if (tbFuncionarios.getHand() == null || inclusao) {
                //faz a persistencia
                em.persist(tbFuncionarios);
            } else {
                //faz a persistencia
                em.merge(tbFuncionarios);
            }
            //manda bala para o BD
            em.getTransaction().commit();
        } catch (Exception ex) {
            //se der algo de errado vem parar aqui, onde eh cancelado
            em.getTransaction().rollback();
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TbFuncionarios tbFuncionarios;
            try {
                tbFuncionarios = em.getReference(TbFuncionarios.class, id);
                tbFuncionarios.getHand();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tbFuncionarios with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TbApontamentosFuncionarios> tbApontamentosFuncionariosCollectionOrphanCheck = tbFuncionarios.getTbApontamentosFuncionariosCollection();
            for (TbApontamentosFuncionarios tbApontamentosFuncionariosCollectionOrphanCheckTbApontamentosFuncionarios : tbApontamentosFuncionariosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbFuncionarios (" + tbFuncionarios + ") cannot be destroyed since the TbApontamentosFuncionarios " + tbApontamentosFuncionariosCollectionOrphanCheckTbApontamentosFuncionarios + " in its tbApontamentosFuncionariosCollection field has a non-nullable tbFuncionariosHand field.");
            }
            Collection<TbProjetos> tbProjetosCollectionOrphanCheck = tbFuncionarios.getTbProjetosCollection();
            for (TbProjetos tbProjetosCollectionOrphanCheckTbProjetos : tbProjetosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbFuncionarios (" + tbFuncionarios + ") cannot be destroyed since the TbProjetos " + tbProjetosCollectionOrphanCheckTbProjetos + " in its tbProjetosCollection field has a non-nullable tbFuncionariosHand field.");
            }
            Collection<TbFuncionariosRecursos> tbFuncionariosRecursosCollectionOrphanCheck = tbFuncionarios.getTbFuncionariosRecursosCollection();
            for (TbFuncionariosRecursos tbFuncionariosRecursosCollectionOrphanCheckTbFuncionariosRecursos : tbFuncionariosRecursosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbFuncionarios (" + tbFuncionarios + ") cannot be destroyed since the TbFuncionariosRecursos " + tbFuncionariosRecursosCollectionOrphanCheckTbFuncionariosRecursos + " in its tbFuncionariosRecursosCollection field has a non-nullable tbFuncionariosHand field.");
            }
            Collection<TbFuncionarioTurnoSemana> tbFuncionarioTurnoSemanaCollectionOrphanCheck = tbFuncionarios.getTbFuncionarioTurnoSemanaCollection();
            for (TbFuncionarioTurnoSemana tbFuncionarioTurnoSemanaCollectionOrphanCheckTbFuncionarioTurnoSemana : tbFuncionarioTurnoSemanaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbFuncionarios (" + tbFuncionarios + ") cannot be destroyed since the TbFuncionarioTurnoSemana " + tbFuncionarioTurnoSemanaCollectionOrphanCheckTbFuncionarioTurnoSemana + " in its tbFuncionarioTurnoSemanaCollection field has a non-nullable tbFuncionariosHand field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tbFuncionarios);
            em.getTransaction().commit();
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

}
