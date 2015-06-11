/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.NonexistentEntityException;
import Controladores.exceptions.PreexistingEntityException;
import Controladores.exceptions.RollbackFailureException;
import Modelos.TbUsuario;
import Utilitarios.Util;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author BERLIM
 */
@ManagedBean
@ViewScoped
public class TbUsuarioJpaController implements Serializable {

    private EntityManagerFactory emf = null;
    private EntityManager em = null;
    private TbUsuario tbUsuario = new TbUsuario();

    public TbUsuarioJpaController() {
        emf = Persistence.createEntityManagerFactory("ProjectManagementSystemPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public TbUsuario getTbUsuario() {
        return tbUsuario;
    }

    public void setTbUsuario(TbUsuario tbUsuario) {
        this.tbUsuario = tbUsuario;
    }

    public void create() throws Exception {
        if (validaUsuario(this.tbUsuario.getNome())) {

            try {
                em = getEntityManager();
                em.getTransaction().begin();

                if (this.tbUsuario.getHand() == null) {
                    Util utilitarios = new Util();
                    this.tbUsuario.setHand(utilitarios.contadorObjetos("TbUsuario"));
                    em.persist(this.tbUsuario);
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro salvo com sucesso!", null));
                } else {
                    em.merge(this.tbUsuario);
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

        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Usuário já cadastrado.", null));
        }

    }

    public void destroy(Integer id) throws NonexistentEntityException, Exception {

        try {
            em = getEntityManager();
            em.getTransaction().begin();
            tbUsuario = em.getReference(TbUsuario.class, id);
            tbUsuario.getHand();

            em.remove(tbUsuario);
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

    public TbUsuario findTbUsuario(Integer id) {
        em.getTransaction();
        try {
            return em.find(TbUsuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbUsuarioCount() {
        em.getTransaction();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TbUsuario> rt = cq.from(TbUsuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<TbUsuario> retornaCollectionUsuarios() {

        em = getEntityManager();
        Query query = em.createNamedQuery("TbUsuario.findAll");
        return query.getResultList();

    }

    public boolean validaUsuario(String nome) {

        em = getEntityManager();
        Query usuario = em.createNamedQuery("TbUsuario.findNome").setParameter("nome", nome);

        return usuario.getResultList().isEmpty();

    }

}
