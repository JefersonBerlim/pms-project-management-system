package Controle;

import Controle.exceptions.PreexistingEntityException;
import Entidades.TbEstados;
import Entidades.TbPaises;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Named("tbEstadosController")
@ViewScoped
public class TbEstadosController implements Serializable {

    private EntityManagerFactory emf = null;
    private TbEstados tbEstados;
    private TbPaises tbPaises;
    private int handPais;
    private List<TbPaises> listTbPaises;

    // ************************ Início Métodos da Classe ***********************
    public TbEstadosController() {
        this.emf = Persistence.createEntityManagerFactory("ProjectManagementSystemPU");
        this.tbEstados = new TbEstados();
        this.tbPaises = new TbPaises();
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
    public void create( boolean ehAlteracao) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        TbPaisesController controle = new TbPaisesController();
        tbPaises = controle.findTbPaises(handPais);
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
            this.mensagemGravacao();

        } catch (Exception ex) {
            this.mensagemErroGravacao();
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void mensagemGravacao() {
        FacesMessage mensagem = new FacesMessage(" Estado salvo com sucesso! ");
        FacesContext.getCurrentInstance().addMessage(null, mensagem);
    }

    public void mensagemErroGravacao() {
        FacesMessage mensagem = new FacesMessage(" Problemas ao gravar o registro! ");
        FacesContext.getCurrentInstance().addMessage(null, mensagem);
    }

    public List<TbPaises> listaPaises() {
        List<TbPaises> lista = new ArrayList<>();
        TbPaisesController controlePais = new TbPaisesController();

        lista = controlePais.TbPaisesfindAll();
        return lista;
    }
}
