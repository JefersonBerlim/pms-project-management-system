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
import Modelos.TbMateriais;
import Modelos.TbUnidadeMedida;
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
public class TbUnidadeMedidaJpaController implements Serializable {

    private EntityManagerFactory emf = null;
    private EntityManager em = null;
    private TbUnidadeMedida tbUnidadeMedida = new TbUnidadeMedida();
    private List<TbUnidadeMedida> listTbUnidadeMedida = new ArrayList<>();

    public TbUnidadeMedidaJpaController() {
        emf = Persistence.createEntityManagerFactory("ProjectManagementSystemPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public TbUnidadeMedida getTbUnidadeMedida() {
        return tbUnidadeMedida;
    }

    public void setTbUnidadeMedida(TbUnidadeMedida TbUnidadeMedida) {
        this.tbUnidadeMedida = TbUnidadeMedida;
    }

    public List<TbUnidadeMedida> getListTbUnidadeMedida() {
        return listTbUnidadeMedida;
    }

    public void setListTbUnidadeMedida(List<TbUnidadeMedida> listTbUnidadeMedida) {
        this.listTbUnidadeMedida = listTbUnidadeMedida;
    }

    public void create() throws Exception {
        try {
            em = getEntityManager();
            em.getTransaction().begin();

            if (this.tbUnidadeMedida.getHand() == null) {

                Util utilitarios = new Util();
                this.tbUnidadeMedida.setHand(utilitarios.contadorObjetos("TbUnidadeMedida"));
                em.persist(this.tbUnidadeMedida);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Registro salvo com sucesso!"));
            } else {

                em.merge(this.tbUnidadeMedida);
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

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        try {
            em.getTransaction().begin();

            tbUnidadeMedida = em.getReference(TbUnidadeMedida.class, id);
            tbUnidadeMedida.getHand();

            List<String> illegalOrphanMessages = null;
            
            Collection<TbMateriais> tbMateriaisCollection = tbUnidadeMedida.getTbMateriaisCollection();
            for (TbMateriais tbMateriais : tbMateriaisCollection) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("A Unidade de Medida (" + tbUnidadeMedida.getDescricao() + ") não pode ser excluído pois esta vinculado no Material " + tbMateriais.getDescricao() + ".");
            }
            
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            
            em.remove(tbUnidadeMedida);
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

    public TbUnidadeMedida findTbUnidadeMedida(Integer id) {
        em = getEntityManager();
        try {
            return em.find(TbUnidadeMedida.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbUnidadeMedidaCount() {
        em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TbUnidadeMedida> rt = cq.from(TbUnidadeMedida.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<TbUnidadeMedida> retornaCollectionServicos() {
        em = getEntityManager();
        Query query = em.createNamedQuery("TbUnidadeMedida.findAll");
        return query.getResultList();
    }

}
