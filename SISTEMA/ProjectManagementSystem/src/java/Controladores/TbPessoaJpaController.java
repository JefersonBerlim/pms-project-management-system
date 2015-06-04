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
import Modelos.TbTipopessoa;
import Modelos.TbCidades;
import Modelos.TbPessoa;
import Modelos.TbProjetos;
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
public class TbPessoaJpaController implements Serializable {

    private EntityManagerFactory emf = null;
    private EntityManager em = null;
    private TbPessoa tbPessoa = new TbPessoa();
    private List<TbTipopessoa> listTbTipoPessoa = new ArrayList<>();
    private List<TbCidades> listTbCidades = new ArrayList<>();

    public TbPessoaJpaController() {
        emf = Persistence.createEntityManagerFactory("ProjectManagementSystemPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public TbPessoa getTbPessoa() {
        return tbPessoa;
    }

    public void setTbPessoa(TbPessoa tbPessoa) {
        this.tbPessoa = tbPessoa;
    }

    public List<TbTipopessoa> getListTbTipoPessoa() {
        TbTipopessoaJpaController controle = new TbTipopessoaJpaController();
        this.listTbTipoPessoa = controle.retornaCollectionTipoPessoa();

        return listTbTipoPessoa;
    }

    public void setListTbTipoPessoa(List<TbTipopessoa> listTbTipoPessoa) {
        this.listTbTipoPessoa = listTbTipoPessoa;
    }

    public List<TbCidades> getListTbCidades() {

        TbCidadesJpaController controle = new TbCidadesJpaController();
        this.listTbCidades = controle.retornaCollectionCidades();

        return listTbCidades;
    }

    public void setListTbCidades(List<TbCidades> listTbCidades) {
        this.listTbCidades = listTbCidades;
    }

    public void create() throws Exception {
        try {
            em = getEntityManager();
            em.getTransaction().begin();

            if (this.tbPessoa.isTmpEhCliente()) {
                this.tbPessoa.setEhcliente("S");
            } else {
                this.tbPessoa.setEhcliente(null);
            }

            if (this.tbPessoa.isTmpEhFornecedor()) {
                this.tbPessoa.setEhfornecedor("S");
            } else {
                this.tbPessoa.setEhfornecedor(null);
            }

            if (this.tbPessoa.isTmpEhInativo()) {
                this.tbPessoa.setEhInativo("S");
            } else {
                this.tbPessoa.setEhInativo(null);
            }

            if (this.tbPessoa.getHand() == null) {

                Util utilitarios = new Util();
                this.tbPessoa.setHand(utilitarios.contadorObjetos("TbPessoa"));
                em.persist(this.tbPessoa);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Registro salvo com sucesso!"));
            } else {

                em.merge(this.tbPessoa);
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

            tbPessoa = em.getReference(TbPessoa.class, id);
            tbPessoa.getHand();

            List<String> illegalOrphanMessages = null;
            
            Collection<TbProjetos> tbProjetosCollection = tbPessoa.getTbProjetosCollection();
            for (TbProjetos tbProjetos : tbProjetosCollection) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("A Pessoa (" + tbPessoa + ") não pode ser excluído pois esta sendo usado no Projeto "
                        + tbProjetos.getDecsricao() + ".");
            }
            
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
                        
            em.remove(tbPessoa);
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

    public TbPessoa findTbPessoa(Integer id) {
        em = getEntityManager();
        try {
            return em.find(TbPessoa.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbPessoaCount() {
        em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TbPessoa> rt = cq.from(TbPessoa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
