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
import Modelos.TbUnidadeMedida;
import Modelos.TbMaterialMarcoSvc;
import java.util.ArrayList;
import java.util.Collection;
import Modelos.TbApontamentosMateriais;
import Modelos.TbMateriais;
import Modelos.TbProjetosMateriais;
import Modelos.TbOsMateriaisTotal;
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
 * @author BERLIM
 */
@ManagedBean
@ViewScoped
public class TbMateriaisJpaController implements Serializable {

    private EntityManagerFactory emf = null;
    private EntityManager em = null;
    private TbMateriais tbMateriais = new TbMateriais();
    private List<TbUnidadeMedida> listTbUnidadeMedida = new ArrayList<>();

    public TbMateriaisJpaController() {
        emf = Persistence.createEntityManagerFactory("ProjectManagementSystemPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public TbMateriais getTbMateriais() {
        return tbMateriais;
    }

    public void setTbMateriais(TbMateriais tbMateriais) {
        this.tbMateriais = tbMateriais;
    }

    public List<TbUnidadeMedida> getListTbUnidadeMedida() {
        TbUnidadeMedidaJpaController controle = new TbUnidadeMedidaJpaController();
        this.listTbUnidadeMedida = controle.retornaCollectionUnidadeMedida();

        return listTbUnidadeMedida;
    }

    public void setListTbUnidadeMedida(List<TbUnidadeMedida> listTbUnidadeMedida) {
        this.listTbUnidadeMedida = listTbUnidadeMedida;
    }

    public void create() throws Exception {
        try {
            em = getEntityManager();
            em.getTransaction().begin();

            if (this.tbMateriais.isTmpEhInativo()) {
                this.tbMateriais.setEhInativo("S");
            } else {
                this.tbMateriais.setEhInativo(null);
            }

            if (this.tbMateriais.getHand() == null) {

                Util utilitarios = new Util();
                this.tbMateriais.setHand(utilitarios.contadorObjetos("TbMateriais"));
                em.persist(this.tbMateriais);
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro salvo com sucesso!", null));
            } else {

                em.merge(this.tbMateriais);
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro atualizado com sucesso!", null));
            }

            em.getTransaction().commit();

        } catch (Exception ex) {

            em.getTransaction().rollback();
            FacesContext.getCurrentInstance().addMessage(ex.toString(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Problemas ao persistir o regitsto.", null));

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
            tbMateriais = em.getReference(TbMateriais.class, id);
            tbMateriais.getHand();

            List<String> illegalOrphanMessages = null;

            Collection<TbMaterialMarcoSvc> tbMaterialMarcoSvcCollection = tbMateriais.getTbMaterialMarcoSvcCollection();
            for (TbMaterialMarcoSvc tbMaterialMarcoSvc : tbMaterialMarcoSvcCollection) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("O Material (" + tbMaterialMarcoSvc.getTbMateriaisHand().getDescricao()
                        + ") não pode ser excluído pois esta sendo usado no vinculo com o Serviço e Material "
                        + tbMaterialMarcoSvc.getTbMarcosServicosHand().getTbServicosHand().getDescricao()
                        + " - " + tbMaterialMarcoSvc.getTbMateriaisHand().getDescricao() + ".");
            }

            Collection<TbApontamentosMateriais> tbApontamentosMateriaisCollection = tbMateriais.getTbApontamentosMateriaisCollection();
            for (TbApontamentosMateriais tbApontamentosMateriais : tbApontamentosMateriaisCollection) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("O Material (" + tbApontamentosMateriais.getTbMateriaisHand().getDescricao()
                        + ") não pode ser excluído pois esta sendo usado no vinculo com os Apontamentos de Materiais da OS.");
            }

            Collection<TbProjetosMateriais> tbProjetosMateriaisCollection = tbMateriais.getTbProjetosMateriaisCollection();
            for (TbProjetosMateriais tbProjetosMateriais : tbProjetosMateriaisCollection) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("O Material (" + tbProjetosMateriais.getTbMateriaisHand().getDescricao()
                        + ") não pode ser excluído pois esta sendo usado no vinculo com os Projetos.");
            }

            Collection<TbOsMateriaisTotal> tbOsMateriaisTotalCollection = tbMateriais.getTbOsMateriaisTotalCollection();
            for (TbOsMateriaisTotal tbOsMateriaisTotal : tbOsMateriaisTotalCollection) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("O Material (" + tbOsMateriaisTotal.getTbMateriaisHand().getDescricao()
                        + ") não pode ser excluído pois esta sendo usado no vinculo com os Totalizadores das OS.");

                if (illegalOrphanMessages != null) {
                    throw new IllegalOrphanException(illegalOrphanMessages);
                }

                em.remove(tbMateriais);

            }
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

    public TbMateriais findTbMateriais(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbMateriais.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbMateriaisCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TbMateriais> rt = cq.from(TbMateriais.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
