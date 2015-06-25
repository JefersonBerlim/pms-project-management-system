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
import javax.annotation.PostConstruct;
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
    private List<TbServicos> listTbServicosNaoVinculados = new ArrayList<>();
    private List<TbServicos> listTbServicosVinculados = new ArrayList<>();
    private List retornaRegistros = new ArrayList<>();

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
        this.listTbServicos = controle.retornaCollectionServicosAtivos();

        return listTbServicos;
    }

    public void setListTbServicos(List<TbServicos> listTbServicos) {
        this.listTbServicos = listTbServicos;
    }

    public List<TbServicos> getListTbServicosNaoVinculados() {

        em = getEntityManager();
        listTbServicosNaoVinculados = new ArrayList<>();

        if (tbMarcosServicos.getTbMarcosHand() != null) {
            Query vinculos = em.createNamedQuery("TbServicos.servicosNaoVinculados")
                    .setParameter("marco", tbMarcosServicos.getTbMarcosHand().getHand());
            listTbServicosNaoVinculados = vinculos.getResultList();

        }
        if (em != null) {
            em.close();
        }

        return listTbServicosNaoVinculados;
    }

    public void setListTbServicosNaoVinculados(List<TbServicos> listTbServicosNaoVinculados) {
        this.listTbServicosNaoVinculados = listTbServicosNaoVinculados;
    }

    public List<TbServicos> getListTbServicosVinculados() {

        em = getEntityManager();
        listTbServicosVinculados = new ArrayList<>();

        if (tbMarcosServicos.getTbMarcosHand() != null) {
            Query vinculos = em.createNamedQuery("TbServicos.servicosVinculados")
                    .setParameter("marco", tbMarcosServicos.getTbMarcosHand().getHand());
            listTbServicosVinculados = vinculos.getResultList();
        }
        if (em != null) {
            em.close();
        }

        return listTbServicosVinculados;
    }

    public void setListTbServicosVinculados(List<TbServicos> listTbServicosVinculados) {
        this.listTbServicosVinculados = listTbServicosVinculados;
    }

    public void create() throws Exception {
        try {
            em = getEntityManager();
            em.getTransaction().begin();

            if (this.tbMarcosServicos.isTmpAutomatizaProcesso()) {
                tbMarcosServicos.setAutomatizaProcesso("S");
            } else {
                tbMarcosServicos.setAutomatizaProcesso("N");
            }

            if (validaInclusao()) {
                Util utilitarios = new Util();
                this.tbMarcosServicos.setHand(utilitarios.contadorObjetos("TbMarcosServicos"));
                em.persist(this.tbMarcosServicos);
                em.getTransaction().commit();

                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Registro salvo com sucesso!", null));
            } else {
                em.merge(this.tbMarcosServicos);
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
        try {
            em = getEntityManager();
            return em.find(TbMarcosServicos.class, id);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public int getTbMarcosServicosCount() {
        try {
            em = getEntityManager();
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TbMarcosServicos> rt = cq.from(TbMarcosServicos.class);
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

        retornaRegistros = new ArrayList<>();

        Query vinculos = em.createNamedQuery("TbMarcosServicos.retornaRegistros")
                .setParameter("marco", tbMarcosServicos.getTbMarcosHand())
                .setParameter("servico", tbMarcosServicos.getTbServicosHand());
        retornaRegistros = vinculos.getResultList();

        return retornaRegistros.isEmpty();

    }

    public List<TbMarcosServicos> retornaCollectionAtivos() {

        List<TbMarcosServicos> listVinculos = new ArrayList<>();
        try {
            em = getEntityManager();
            Query vinculos = em.createNamedQuery("TbMarcosServicos.retornaRegistrosAtivos");
            listVinculos = vinculos.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }

        return listVinculos;

    }
}
