/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.NonexistentEntityException;
import Modelos.TbMarcosServicos;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelos.TbMateriais;
import Modelos.TbMaterialMarcoSvc;
import Utilitarios.Util;
import java.util.ArrayList;
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
 * @author BERLIM
 */
@ManagedBean
@ViewScoped
public class TbMaterialMarcoSvcJpaController implements Serializable {

    private EntityManagerFactory emf = null;
    private EntityManager em = null;
    private TbMaterialMarcoSvc tbMaterialMarcoSvc = new TbMaterialMarcoSvc();
    private List<TbMarcosServicos> listTbMarcosServicos = new ArrayList<>();
    private List<TbMateriais> listTbMateriaisNaoVinculados = new ArrayList<>();
    private List<TbMateriais> listTbMateriaisVinculados = new ArrayList<>();
    private List retornaRegistros = new ArrayList<>();

    public TbMaterialMarcoSvcJpaController() {
        emf = Persistence.createEntityManagerFactory("ProjectManagementSystemPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public TbMaterialMarcoSvc getTbMaterialMarcoSvc() {
        return tbMaterialMarcoSvc;
    }

    public void setTbMaterialMarcoSvc(TbMaterialMarcoSvc tbMaterialMarcoSvc) {
        this.tbMaterialMarcoSvc = tbMaterialMarcoSvc;
    }

    public List<TbMarcosServicos> getListTbMarcosServicos() {

        TbMarcosServicosJpaController controle = new TbMarcosServicosJpaController();
        this.listTbMarcosServicos = controle.retornaCollectionAtivos();

        return listTbMarcosServicos;
    }

    public void setListTbMarcosServicos(List<TbMarcosServicos> listTbMarcosServicos) {
        this.listTbMarcosServicos = listTbMarcosServicos;
    }

    public List<TbMateriais> getListTbMateriaisNaoVinculados() {

        em = getEntityManager();
        listTbMateriaisNaoVinculados = new ArrayList<>();

        if (tbMaterialMarcoSvc.getTbMarcosServicosHand() != null) {
            Query vinculos = em.createNamedQuery("TbMateriais.materiaisNaoVinculados")
                    .setParameter("marco_servico", tbMaterialMarcoSvc.getTbMarcosServicosHand().getHand());
            listTbMateriaisNaoVinculados = vinculos.getResultList();

        }
        if (em != null) {
            em.close();
        }

        return listTbMateriaisNaoVinculados;
    }

    public void setListTbMateriaisNaoVinculados(List<TbMateriais> listTbMateriaisNaoVinculados) {
        this.listTbMateriaisNaoVinculados = listTbMateriaisNaoVinculados;
    }

    public List<TbMateriais> getListTbMateriaisVinculados() {

        em = getEntityManager();
        listTbMateriaisVinculados = new ArrayList<>();

        if (tbMaterialMarcoSvc.getTbMarcosServicosHand() != null) {
            Query vinculos = em.createNamedQuery("TbMateriais.materiaisVinculados")
                    .setParameter("marco_servico", tbMaterialMarcoSvc.getTbMarcosServicosHand().getHand());
            listTbMateriaisVinculados = vinculos.getResultList();

        }
        if (em != null) {
            em.close();
        }

        return listTbMateriaisVinculados;
    }

    public void setListTbMateriaisVinculados(List<TbMateriais> listTbMateriaisVinculados) {
        this.listTbMateriaisVinculados = listTbMateriaisVinculados;
    }

    public void create() throws Exception {
        try {
            em = getEntityManager();
            em.getTransaction().begin();

            if (this.tbMaterialMarcoSvc.isTmpAutomatizaProcesso()) {
                tbMaterialMarcoSvc.setAutomatizaProcesso("S");
            } else {
                tbMaterialMarcoSvc.setAutomatizaProcesso("N");
            }

            if (validaInclusao()) {
                Util utilitarios = new Util();
                this.tbMaterialMarcoSvc.setHand(utilitarios.contadorObjetos("TbMaterialMarcoSvc"));

                em.persist(this.tbMaterialMarcoSvc);
                em.getTransaction().commit();

                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Registro salvo com sucesso!", null));
            } else {
                em.merge(this.tbMaterialMarcoSvc);
                em.getTransaction().commit();

                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Registro atualizado com sucesso!", null));
            }
        } catch (Exception ex) {
            em.getTransaction().rollback();
            FacesContext.getCurrentInstance().addMessage(ex.toString(),
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Problemas ao persistir o regitsto.", ex.toString()));
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, Exception {
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            tbMaterialMarcoSvc = em.getReference(TbMaterialMarcoSvc.class, id);
            tbMaterialMarcoSvc.getHand();

            em.remove(tbMaterialMarcoSvc);
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

    public TbMaterialMarcoSvc findTbMaterialMarcoSvc(Integer id) {
        try {
            em = getEntityManager();
            return em.find(TbMaterialMarcoSvc.class, id);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public int getTbMaterialMarcoSvcCount() {
        try {
            em = getEntityManager();
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TbMaterialMarcoSvc> rt = cq.from(TbMaterialMarcoSvc.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    private boolean validaInclusao() {

        Query vinculos = em.createNamedQuery("TbMaterialMarcoSvc.retornaRegistros")
                .setParameter("marco_servico", tbMaterialMarcoSvc.getTbMarcosServicosHand())
                .setParameter("material", tbMaterialMarcoSvc.getTbMateriaisHand());
        retornaRegistros = vinculos.getResultList();

        return retornaRegistros.isEmpty();

    }

}
