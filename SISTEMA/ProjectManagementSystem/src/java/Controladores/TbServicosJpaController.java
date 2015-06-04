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

    public void create() throws Exception {
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
            em.getTransaction().rollback();
            FacesContext.getCurrentInstance().addMessage(ex.toString(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Problemas ao persistir o regitsto."));
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

            tbServicos = em.getReference(TbServicos.class, id);
            tbServicos.getHand();

            List<String> illegalOrphanMessages = null;
            
            Collection<TbMarcosServicos> tbMarcosServicosCollection = tbServicos.getTbMarcosServicosCollection();
            for (TbMarcosServicos tbMarcosServicos : tbMarcosServicosCollection) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("O Serviço (" + tbServicos.getDescricao() + ") não pode ser excluído pois esta vinculado ao Marco " + tbMarcosServicos.getTbMarcosHand().getDescricao() + ".");
            }
            
            Collection<TbProjetosServicos> tbProjetosServicosCollection = tbServicos.getTbProjetosServicosCollection1();
            for (TbProjetosServicos tbProjetosServicos : tbProjetosServicosCollection) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("O Serviço (" + tbServicos.getDescricao() + ") não pode ser excluído pois esta vinculado ao Projeto " + tbProjetosServicos.getObservacao() + ".");
            }
            
            Collection<TbRecursosServicos> tbRecursosServicosCollection = tbServicos.getTbRecursosServicosCollection();
            for (TbRecursosServicos tbRecursosServicos : tbRecursosServicosCollection) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("O Serviço (" + tbServicos.getDescricao() + ") não pode ser excluído pois esta vinculado ao Recurso " + tbRecursosServicos.getTbServicosHand().getDescricao() + ".");
            }
            
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }          
            
            em.remove(tbServicos);
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
