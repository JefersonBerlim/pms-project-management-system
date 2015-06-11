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
import Modelos.TbPaises;
import Modelos.TbCidades;
import Modelos.TbEstados;
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
public class TbEstadosJpaController implements Serializable {

    private EntityManagerFactory emf = null;
    private EntityManager em = null;
    private TbEstados tbEstado = new TbEstados();
    private List<TbPaises> listTbPaises = new ArrayList<>();

    public TbEstadosJpaController() {
        emf = Persistence.createEntityManagerFactory("ProjectManagementSystemPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public TbEstados getTbEstado() {
        return tbEstado;
    }

    public void setTbEstado(TbEstados tbEstado) {
        this.tbEstado = tbEstado;
    }

    public List<TbPaises> getListTbPaises() {

        TbPaisesJpaController controle = new TbPaisesJpaController();
        this.listTbPaises = controle.retornaCollectionPaises();

        return listTbPaises;
    }

    public void create() throws Exception {
        try {
            em = getEntityManager();
            em.getTransaction().begin();

            if (this.tbEstado.getHand() == null) {

                Util utilitarios = new Util();
                this.tbEstado.setHand(utilitarios.contadorObjetos("TbEstados"));
                em.persist(this.tbEstado);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Registro salvo com sucesso!", null));
            } else {

                em.merge(this.tbEstado);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Registro atualizado com sucesso!", null));
            }

            em.getTransaction().commit();

        } catch (Exception ex) {

            em.getTransaction().rollback();
            FacesContext.getCurrentInstance().addMessage(ex.toString(),
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Problemas ao persistir o regitsto.", null));
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
            tbEstado = em.getReference(TbEstados.class, id);
            tbEstado.getHand();

            List<String> illegalOrphanMessages = null;

            Collection<TbCidades> tbCidadesCollection = tbEstado.getTbCidadesCollection();
            for (TbCidades tbCidades : tbCidadesCollection) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<>();
                }
                illegalOrphanMessages.add("O Estado (" + tbEstado.getEstado()
                        + ") não pode ser excluído pois esta sendo usado na Cidade "
                        + tbCidades.getCidade() + ".");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }

            em.remove(tbEstado);
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

    public TbEstados findTbEstados(Integer id) {
        em = getEntityManager();
        try {
            return em.find(TbEstados.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbEstadosCount() {
        em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TbEstados> rt = cq.from(TbEstados.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<TbEstados> retornaCollectionEstados() {

        em = getEntityManager();
        Query query = em.createNamedQuery("TbEstados.findAll");
        return query.getResultList();
    }

}
