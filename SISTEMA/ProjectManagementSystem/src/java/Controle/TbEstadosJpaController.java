/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Controle.exceptions.IllegalOrphanException;
import Controle.exceptions.NonexistentEntityException;
import Controle.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.EntityNotFoundException;
import Entidades.TbEstados;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import static javax.faces.component.UIInput.isEmpty;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

@ManagedBean
@ViewScoped
public class TbEstadosJpaController implements Serializable {

    private TbEstados tbEstados;
    private TbPaisesJpaController controleTbPaises;
    private final EntityManagerFactory emf;
    private String mensagem;
    private List<TbEstados> estados;
    private Integer paisSelecionado;
    public boolean inclusao;

    public TbEstadosJpaController() {
        emf = Persistence.createEntityManagerFactory("ProjectManagementSystemPU");
        tbEstados = new TbEstados();
        controleTbPaises = new TbPaisesJpaController();

    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public boolean isInclusao() {
        return inclusao;
    }

    public void setInclusao(boolean inclusao) {
        this.inclusao = inclusao;
    }

    public Integer getPaisSelecionado() {
        return paisSelecionado;
    }

    public void setPaisSelecionado(Integer paisSelecionado) {
        this.paisSelecionado = paisSelecionado;
    }

    public TbEstados getTbEstados() {
        if (tbEstados.getHand() == null) {
            tbEstados.setHand(preparaInclusao());
            inclusao = true;
        }
        return tbEstados;
    }

    public void setTbEstados(TbEstados tbEstados) {
        this.tbEstados = tbEstados;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public List<TbEstados> getEstados() {
        return estados;
    }

    public void setEstados(List<TbEstados> estados) {
        this.estados = estados;
    }

    // Método Usado para Popular a Grid de Estados
    public List<TbEstados> getEstadosLista() {
        estados = findEstados();
        return estados;
    }

    public void create() throws PreexistingEntityException, Exception {

        //obtendo o EntityManager
        EntityManager em = getEntityManager();
        try {
            //inicia o processo de transacao
            em.getTransaction().begin();
            tbEstados.setTbPaisesHand(this.controleTbPaises.findTbPaises(this.paisSelecionado));

            if ((tbEstados.getHand() == null && tbEstados.getTbPaisesHand() != null) || inclusao) {
                //faz a persistencia
                em.persist(tbEstados);
            } else {
                //faz a persistencia
                em.merge(tbEstados);
            }
            //manda bala para o BD
            em.getTransaction().commit();
        } catch (Exception ex) {
            //se der algo de errado vem parar aqui, onde eh cancelado
            System.out.println(ex);
            em.getTransaction().rollback();
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy() throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = getEntityManager();
        Integer id;
        id = tbEstados.getHand();

        try {
            em.getTransaction().begin();
            try {
                tbEstados = em.getReference(TbEstados.class, id);
                tbEstados.getHand();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tbEstados with id " + id + " no longer exists.", enfe);
            }
            em.remove(tbEstados);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public TbEstados findTbEstados(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbEstados.class, id);
        } finally {
            em.close();
        }
    }

    // Método Usado para a Abertura da Tela de Estados
    public TbEstados findEstadosHand() {
        EntityManager em = getEntityManager();

        Map< String, String> params = FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap();
        String hand = params.get("hand");

        if (!isEmpty(hand)) {
            this.tbEstados = findTbEstados(Integer.parseInt(hand));
            this.setTbEstados(tbEstados);
            this.paisSelecionado = this.tbEstados.getTbPaisesHand().getHand();
            inclusao = false;
        }
        return tbEstados;
    }

    public List<TbEstados> findEstados() {
        EntityManager em = getEntityManager();
        Query estados = em.createNamedQuery("TbEstados.findAll");
        return estados.getResultList();
    }

    public Integer preparaInclusao() {
        EntityManager em = getEntityManager();
        Integer proxCod = 0;
        List<TbEstados> listaEstados = new ArrayList<TbEstados>();

        listaEstados = findEstados();

        for (Integer i = 0; i < listaEstados.size(); i++) {
            if (listaEstados.get(i).getHand() > proxCod) {
                proxCod = listaEstados.get(i).getHand();
            }
        }

        return proxCod + 1;
    }

    public void save() {
        try {
            this.create();
            mensagem = "Registro salvo com sucesso";
        } catch (Exception ex) {
            mensagem = "Problemas ao Salvar o registro";
        }
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Atenção", mensagem));
    }
}
