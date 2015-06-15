/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
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

    public void create() throws Exception {
        try {
            em = getEntityManager();
            em.getTransaction().begin();

            if (this.tbMarcos.isTmpEhInativo()) {
                this.tbMarcos.setEhInativo("S");
            } else {
                this.tbMarcos.setEhInativo("N");
            }

            if (this.tbMarcos.getHand() == null) {

                Util utilitarios = new Util();
                this.tbMarcos.setHand(utilitarios.contadorObjetos("TbMarcos"));
                em.persist(this.tbMarcos);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro salvo com sucesso!", null));
            } else {

                em.merge(this.tbMarcos);
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
            tbMarcos = em.getReference(TbMarcos.class, id);
            tbMarcos.getHand();

            List<String> illegalOrphanMessages = null;

            Collection<TbMarcosServicos> tbMarcosServicosCollection = tbMarcos.getTbMarcosServicosCollection();
            for (TbMarcosServicos tbMarcosServicos : tbMarcosServicosCollection) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("O Marco (" + tbMarcos.getDescricao()
                        + ") não pode ser excluído pois esta sendo usado no vinculo com o serviço "
                        + tbMarcosServicos.getTbServicosHand().getDescricao() + ".");
            }

            Collection<TbProjetoMarcos> tbProjetoMarcosCollection = tbMarcos.getTbProjetoMarcosCollection();
            for (TbProjetoMarcos tbProjetoMarcos : tbProjetoMarcosCollection) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("O Marco (" + tbMarcos.getDescricao()
                        + ") não pode ser excluído pois esta sendo usado no vinculo com o Projeto "
                        + tbProjetoMarcos.getTbProjetoHand().getDecsricao() + ".");
            }

            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }

            em.remove(tbMarcos);
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
