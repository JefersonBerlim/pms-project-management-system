/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import Controladores.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelos.TbServicos;
import Modelos.TbMarcos;
import Modelos.TbMarcosServicos;
import Modelos.TbMaterialMarcoSvc;
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
public class TbMarcosServicosJpaController implements Serializable {

    private EntityManagerFactory emf = null;
    private EntityManager em = null;
    private TbMarcosServicos tbMarcosServicos = new TbMarcosServicos();
    private List<TbMarcos> listTbMarcos = new ArrayList<>();
    private List<TbServicos> listTbServicos = new ArrayList<>();

    public TbMarcosServicosJpaController() {
        emf = Persistence.createEntityManagerFactory("ProjectManagementSystemPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public TbMarcosServicos getTbMarcosServicos() {
        return tbMarcosServicos;
    }

    public void setTbMarcos(TbMarcosServicos tbMarcosServicos) {
        this.tbMarcosServicos = tbMarcosServicos;
    }

    public List<TbMarcos> getListTbMarcos() {

        TbMarcosJpaController controle = new TbMarcosJpaController();
        this.listTbMarcos = controle.retornaCollectionMarcos();

        return listTbMarcos;
    }

    public void setListTbMarcosServicos(List<TbMarcos> listTbMarcosServicos) {
        this.listTbMarcos = listTbMarcosServicos;
    }

    public List<TbServicos> getListTbServicos() {

        TbServicosJpaController controle = new TbServicosJpaController();
        this.listTbServicos = controle.retornaCollectionServicos();

        return listTbServicos;
    }

    public void setListTbServicos(List<TbServicos> listTbServicos) {
        this.listTbServicos = listTbServicos;
    }

    public void create() throws Exception {
        try {
            em = getEntityManager();
            em.getTransaction().begin();

            if (this.tbMarcosServicos.getHand() == null) {

                Util utilitarios = new Util();
                this.tbMarcosServicos.setHand(utilitarios.contadorObjetos("TbMarcosServicos"));
                em.persist(this.tbMarcosServicos);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Registro salvo com sucesso!", null));
            } else {

                em.merge(this.tbMarcosServicos);
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

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            tbMarcosServicos = em.getReference(TbMarcosServicos.class, id);
            tbMarcosServicos.getHand();

            List<String> illegalOrphanMessages = null;

            Collection<TbMaterialMarcoSvc> tbMaterialMarcoSvcCollection = tbMarcosServicos.getTbMaterialMarcoSvcCollection();
            for (TbMaterialMarcoSvc tbMaterialMarcoSvc : tbMaterialMarcoSvcCollection) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("Este registro não pode ser excluído pois está sendo usado no vínculo "
                        + "Material X Marco X Serviço");
            }

            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }

            em.remove(tbMarcosServicos);
        } catch (IllegalOrphanException ex) {
            em.getTransaction().rollback();
            FacesContext.getCurrentInstance().addMessage(ex.toString(),
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Registro sendo utilizado por outros cadastros.", null));
            em.getTransaction().rollback();
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

    public TbMarcosServicos findTbMarcosServicos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbMarcosServicos.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbMarcosServicosCount() {
        em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TbMarcosServicos> rt = cq.from(TbMarcosServicos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
