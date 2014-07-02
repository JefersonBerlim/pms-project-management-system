package Controle;

import Controle.exceptions.PreexistingEntityException;
import Entidades.TbEstados;
import Entidades.TbPaises;
import Utilitarios.Mensagens;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

@Named("tbEstadosController")
@ViewScoped
public class TbEstadosController implements Serializable {

    private final TbPaisesController controlePais = new TbPaisesController();

    private EntityManagerFactory emf = null;
    private TbEstados tbEstados;
    private TbPaises tbPaises;
    private int handPais;
    private List<TbPaises> listTbPaises;

    // ************************ Início Métodos da Classe ***********************
    public TbEstadosController() {
        this.emf = Persistence.createEntityManagerFactory("ProjectManagementSystemPU");
        tbEstados = new TbEstados();
        tbPaises = new TbPaises();
    }

    @PostConstruct
    public void init() {
        listTbPaises = this.listaPaises();
    }

    public EntityManager getEntityManager() {
        return this.emf.createEntityManager();
    }

    public TbEstados getTbEstados() {
        return tbEstados;
    }

    public void setTbEstados(TbEstados tbPaises) {
        this.tbEstados = tbPaises;
    }

    public TbPaises getTbPaises() {
        return tbPaises;
    }

    public void setTbPaises(TbPaises tbPaises) {
        this.tbPaises = tbPaises;
    }

    public List<TbPaises> getListTbPaises() {
        return listTbPaises;
    }

    public void setListTbPaises(List<TbPaises> listTbPaises) {
        this.listTbPaises = listTbPaises;
    }

    public int getHandPais() {
        return handPais;
    }

    public void setHandPais(int handPais) {
        this.handPais = handPais;
    }

    // ************************ Fim Métodos da Classe ***********************
    // ************************ Início Métodos Gerais ***********************
    public void create(boolean ehAlteracao) throws PreexistingEntityException, Exception {
        EntityManager em = null;

        tbPaises = controlePais.findTbPaises(handPais);
        tbEstados.setTbPaisesHand(tbPaises);
        try {
            em = getEntityManager();
            em.getTransaction().begin();

            if (!ehAlteracao) {
                em.persist(tbEstados);
            } else {
                em.merge(tbEstados);
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

    public List<TbPaises> listaPaises() {
        List<TbPaises> lista;

        lista = controlePais.TbPaisesfindAll();
        return lista;
    }

    public List<TbEstados> TbEstadosfindAll() {
        EntityManager em = getEntityManager();
        Query estado = em.createNamedQuery("TbEstados.findAll");
        return estado.getResultList();
    }

    public TbEstados findEstados(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbEstados.class, id);
        } finally {
            em.close();
        }
    }
}
