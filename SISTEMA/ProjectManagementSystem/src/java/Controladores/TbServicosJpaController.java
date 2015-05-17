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
import Modelos.TbMarcosServicos;
import Modelos.TbProjetosServicos;
import Modelos.TbRecursosServicos;
import Modelos.TbServicos;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
public class TbServicosJpaController implements Serializable {

    private EntityManagerFactory emf = null;
    private EntityManager em = null;
    private TbServicos tbServicos = new TbServicos();
    private List<TbServicos> listTbServicos = new ArrayList<>();

    public TbServicosJpaController() {
        emf = Persistence.createEntityManagerFactory("ProjectManagementSystemPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public TbServicos getTbServicos() {
        return tbServicos;
    }

    public void setTbServicos(TbServicos tbServicos) {
        this.tbServicos = tbServicos;
    }

    public List<TbServicos> getListTbServicos() {
        return listTbServicos;
    }

    public void setListTbServicos(List<TbServicos> listTbServicos) {
        this.listTbServicos = listTbServicos;
    }

    public void create() throws PreexistingEntityException, RollbackFailureException, Exception {
        try {
            em = getEntityManager();
            em.getTransaction().begin();

            if (this.tbServicos.isTmpEhInativo()) {
                this.tbServicos.setEhInativo("S");
            } else {
                this.tbServicos.setEhInativo(null);
            }

            if (this.tbServicos.getHand() == null) {
                Util utilitarios = new Util();
                this.tbServicos.setHand(utilitarios.contadorObjetos("TbServicos"));
                em.persist(this.tbServicos);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Registro salvo com sucesso!"));
            } else {
                em.merge(this.tbServicos);
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
                tbServicos = em.getReference(TbServicos.class, id);
                tbServicos.getHand();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("Este registro não existe.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TbMarcosServicos> tbMarcosServicosCollectionOrphanCheck = tbServicos.getTbMarcosServicosCollection();
            for (TbMarcosServicos tbMarcosServicosCollectionOrphanCheckTbMarcosServicos : tbMarcosServicosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("O Serviço (" + tbServicos.getDescricao() + ") não pode ser excluído pois esta vinculado ao Marco " + tbMarcosServicosCollectionOrphanCheckTbMarcosServicos.getTbMarcosHand().getDescricao() + ".");
            }
            Collection<TbProjetosServicos> tbProjetosServicosCollection1OrphanCheck = tbServicos.getTbProjetosServicosCollection1();
            for (TbProjetosServicos tbProjetosServicosCollection1OrphanCheckTbProjetosServicos : tbProjetosServicosCollection1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("O Serviço (" + tbServicos.getDescricao() + ") não pode ser excluído pois esta vinculado ao Projeto " + tbProjetosServicosCollection1OrphanCheckTbProjetosServicos.getObservacao() + ".");
            }
            Collection<TbRecursosServicos> tbRecursosServicosCollectionOrphanCheck = tbServicos.getTbRecursosServicosCollection();
            for (TbRecursosServicos tbRecursosServicosCollectionOrphanCheckTbRecursosServicos : tbRecursosServicosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("O Serviço (" + tbServicos.getDescricao() + ") não pode ser excluído pois esta vinculado ao Recurso " + tbRecursosServicosCollectionOrphanCheckTbRecursosServicos.getTbServicosHand().getDescricao() + ".");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<TbProjetosServicos> tbProjetosServicosCollection = tbServicos.getTbProjetosServicosCollection();
            for (TbProjetosServicos tbProjetosServicosCollectionTbProjetosServicos : tbProjetosServicosCollection) {
                tbProjetosServicosCollectionTbProjetosServicos.setServicoDependente(null);
                tbProjetosServicosCollectionTbProjetosServicos = em.merge(tbProjetosServicosCollectionTbProjetosServicos);
            }
            em.remove(tbServicos);
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

    public TbServicos findTbServicos(Integer id) {
        em = getEntityManager();
        try {
            return em.find(TbServicos.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbServicosCount() {
        em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TbServicos> rt = cq.from(TbServicos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<TbServicos> retornaCollectionServicos() {
        em = getEntityManager();
        Query query = em.createNamedQuery("TbServicos.findAll");
        return query.getResultList();
    }

}
