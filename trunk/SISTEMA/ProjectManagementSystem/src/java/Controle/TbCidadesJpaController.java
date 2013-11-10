/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Controle.exceptions.IllegalOrphanException;
import Controle.exceptions.NonexistentEntityException;
import Controle.exceptions.PreexistingEntityException;
import Entidades.TbCidades;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
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

@ManagedBean
@ViewScoped
public class TbCidadesJpaController implements Serializable {

    private TbCidades tbCidades;
    private TbEstadosJpaController controleTbEstados;
    private final EntityManagerFactory emf;
    private String mensagem;
    private List<TbCidades> cidades;
    private Integer estadoSelecionado;
    public boolean inclusao;

    public TbCidadesJpaController() {

        emf = Persistence.createEntityManagerFactory("ProjectManagementSystemPU");
        tbCidades = new TbCidades();
        controleTbEstados = new TbEstadosJpaController();
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public TbCidades getTbCidades() {
        if (tbCidades.getHand() == null) {
            tbCidades.setHand(preparaInclusao());
            inclusao = true;
        }
        return tbCidades;
    }

    public void setTbCidades(TbCidades tbCidades) {
        this.tbCidades = tbCidades;
    }

    public TbEstadosJpaController getControleTbEstados() {
        return controleTbEstados;
    }

    public void setControleTbEstados(TbEstadosJpaController controleTbEstados) {
        this.controleTbEstados = controleTbEstados;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public List<TbCidades> getCidades() {
        return cidades;
    }

    public void setCidades(List<TbCidades> cidades) {
        this.cidades = cidades;
    }

    public Integer getEstadoSelecionado() {
        return estadoSelecionado;
    }

    public void setEstadoSelecionado(Integer estadoSelecionado) {
        this.estadoSelecionado = estadoSelecionado;
    }

    public boolean isInclusao() {
        return inclusao;
    }

    public void setInclusao(boolean inclusao) {
        this.inclusao = inclusao;
    }

    // Método Usado para Popular a Grid de Cidades
    public List<TbCidades> getCidadesLista() {
        cidades = findCidades();
        return cidades;
    }

    public void create() throws PreexistingEntityException, Exception {

        //obtendo o EntityManager
        EntityManager em = getEntityManager();
        try {
            //inicia o processo de transacao
            em.getTransaction().begin();
            tbCidades.setTbEstadosHand(this.controleTbEstados.findTbEstados(this.estadoSelecionado));

            if ((tbCidades.getHand() == null && tbCidades.getTbEstadosHand() != null) || inclusao) {
                //faz a persistencia
                em.persist(tbCidades);
            } else {
                //faz a persistencia
                em.merge(tbCidades);
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
        id = tbCidades.getHand();

        try {
            em.getTransaction().begin();
            try {
                tbCidades = em.getReference(TbCidades.class, id);
                tbCidades.getHand();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tbEstados with id " + id + " no longer exists.", enfe);
            }
            em.remove(tbCidades);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public TbCidades findTbCidades(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbCidades.class, id);
        } finally {
            em.close();
        }
    }

    // Método Usado para a Abertura da Tela de Cidades
    public TbCidades findCidadesHand() {
        EntityManager em = getEntityManager();

        Map< String, String> params = FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap();
        String hand = params.get("hand");

        if (!isEmpty(hand)) {
            this.tbCidades = findTbCidades(Integer.parseInt(hand));
            this.setTbCidades(tbCidades);
            this.estadoSelecionado = this.tbCidades.getTbEstadosHand().getHand();
            inclusao = false;
        }
        return tbCidades;
    }

    public List<TbCidades> findCidades() {
        EntityManager em = getEntityManager();
        Query cidades = em.createNamedQuery("TbCidades.findAll");
        return cidades.getResultList();
    }

    public Integer preparaInclusao() {
        EntityManager em = getEntityManager();
        Integer proxCod = 0;
        List<TbCidades> listaCidades = new ArrayList<TbCidades>();

        listaCidades = findCidades();

        for (Integer i = 0; i < listaCidades.size(); i++) {
            if (listaCidades.get(i).getHand() > proxCod) {
                proxCod = listaCidades.get(i).getHand();
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
