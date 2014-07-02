package Controle;

import Controle.exceptions.PreexistingEntityException;
import Entidades.TbPaises;
import Utilitarios.Mensagens;

import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

@Named("tbPaisesController")
@ViewScoped
public class TbPaisesController implements Serializable {

    private TbPaises tbPaises;
    private EntityManagerFactory emf = null;

    // ************************ Início Métodos da Classe ***********************
    public TbPaisesController() {
        this.emf = Persistence.createEntityManagerFactory("ProjectManagementSystemPU");
        this.tbPaises = new TbPaises();
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public TbPaises getTbPaises() {
        return tbPaises;
    }

    public void setTbPaises(TbPaises tbPaises) {
        this.tbPaises = tbPaises;
    }

    // ************************ Fim Métodos da Classe ***********************
    // ************************ Início Métodos Gerais ***********************
    public void create() throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();

            if (this.tbPaises.getHand() == null) {
                em.persist(this.tbPaises);
            } else {
                em.merge(this.tbPaises);
            }

            em.getTransaction().commit();
            Mensagens.mensagemGravacao();

        } catch (Exception ex) {
            Mensagens.mensagemErroGravacao();
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }

    }

    public TbPaises findTbPaises(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbPaises.class, id);
        } finally {
            em.close();
        }
    }

    public List<TbPaises> TbPaisesfindAll() {
        EntityManager em = getEntityManager();
        Query pais = em.createNamedQuery("TbPaises.findAll");
        return pais.getResultList();
    }

}
