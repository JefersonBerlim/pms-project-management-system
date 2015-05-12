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
import Modelos.TbMarcos;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelos.TbMarcosServicos;
import java.util.ArrayList;
import java.util.Collection;
import Modelos.TbProjetoMarcos;
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
public class TbMarcosJpaController implements Serializable {

    private EntityManagerFactory emf = null;
    private EntityManager em = null;
    private TbMarcos tbMarcos = new TbMarcos();
    private List<TbMarcos> listTbMarcos = new ArrayList<>();

    public TbMarcosJpaController() {
        emf = Persistence.createEntityManagerFactory("ProjectManagementSystemPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public TbMarcos getTbMarcos() {
        return tbMarcos;
    }

    public void setTbMarcos(TbMarcos tbMarcos) {
        this.tbMarcos = tbMarcos;
    }

    public List<TbMarcos> getListTbMarcos() {
        return listTbMarcos;
    }

    public void setListTbMarcos(List<TbMarcos> listTbMarcos) {
        this.listTbMarcos = listTbMarcos;
    }

    public void create() throws PreexistingEntityException, RollbackFailureException, Exception {
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            
            if (this.tbMarcos.isTmpEhInativo()) {
                this.tbMarcos.setEhInativo("S");
            }else{
                this.tbMarcos.setEhInativo(null);
            }

            if (this.tbMarcos.getHand() == null) {
                Util utilitarios = new Util();
                this.tbMarcos.setHand(utilitarios.contadorObjetos("TbMarcos"));
                em.persist(this.tbMarcos);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Registro salvo com sucesso!"));
            } else {
                em.merge(this.tbMarcos);
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
                tbMarcos = em.getReference(TbMarcos.class, id);
                tbMarcos.getHand();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("Este registro não existe.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TbMarcosServicos> tbMarcosServicosCollectionOrphanCheck = tbMarcos.getTbMarcosServicosCollection();
            for (TbMarcosServicos tbMarcosServicosCollectionOrphanCheckTbMarcosServicos : tbMarcosServicosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("O Marco (" + tbMarcos.getDescricao() + ") não pode ser excluído pois esta sendo usado no vinculo com o serviço " + tbMarcosServicosCollectionOrphanCheckTbMarcosServicos.getTbServicosHand().getDescricao() + ".");
            }
            Collection<TbProjetoMarcos> tbProjetoMarcosCollectionOrphanCheck = tbMarcos.getTbProjetoMarcosCollection();
            for (TbProjetoMarcos tbProjetoMarcosCollectionOrphanCheckTbProjetoMarcos : tbProjetoMarcosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("O Marco (" + tbMarcos.getDescricao() + ") não pode ser excluído pois esta sendo usado no vinculo com o Projeto " + tbProjetoMarcosCollectionOrphanCheckTbProjetoMarcos.getTbProjetoHand().getDecsricao() + ".");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tbMarcos);
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

    public TbMarcos findTbMarcos(Integer id) {
        em = getEntityManager();
        try {
            return em.find(TbMarcos.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbMarcosCount() {
        em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TbMarcos> rt = cq.from(TbMarcos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<TbMarcos> retornaCollectionMarcos() {
        em = getEntityManager();
        Query query = em.createNamedQuery("TbMarcos.findAll");
        return query.getResultList();
    }

}
