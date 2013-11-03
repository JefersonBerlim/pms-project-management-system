/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Controle.exceptions.IllegalOrphanException;
import Controle.exceptions.NonexistentEntityException;
import Entidades.TbPaises;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;

@ManagedBean
@RequestScoped
public class TbPaisesJpaController implements Serializable {

    private TbPaises tbPaises;
    private final EntityManagerFactory emf;
    private String mensagem;
    private List<TbPaises> paises;

    public TbPaisesJpaController() {
        emf = Persistence.createEntityManagerFactory("ProjectManagementSystemPU");
        tbPaises = new TbPaises();
        paises = new ArrayList<TbPaises>();
    }

    public List<TbPaises> getPaises() {
        paises = findPaises();
        return paises;
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

    public String getText() {
        return mensagem;
    }

    public void setText(String text) {
        this.mensagem = text;
    }

    public void create() throws Exception {
        //obtendo o EntityManager
        EntityManager em = getEntityManager();
        try {
            //inicia o processo de transacao
            em.getTransaction().begin();
            if (tbPaises.getHand() == null) {
                //faz a persistencia
                em.persist(tbPaises);
            } else {
                //faz a persistencia
                em.merge(tbPaises);
            }
            //manda bala para o BD
            em.getTransaction().commit();
        } catch (Exception ex) {
            //se der algo de errado vem parar aqui, onde eh cancelado
            em.getTransaction().rollback();
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy() throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        Integer id = null;
        id = tbPaises.getHand();

        try {
            em.getTransaction().begin();
            try {
                tbPaises = em.getReference(TbPaises.class, id);
                tbPaises.getHand();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tbPaises with id " + id + " no longer exists.", enfe);
            }
            em.remove(tbPaises);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public TbPaises findTbPaises(Integer id) {
        EntityManager em = null;
        try {
            return em.find(TbPaises.class, id);
        } finally {
            em.close();
        }
    }

    public void findTbPaisesPage() {

        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) context.getExternalContext().getRequest();
        String teste = req.getParameter("hand");
        
        tbPaises = findTbPaises( Integer.parseInt(teste) );

    }

    public List<TbPaises> findPaises() {
        EntityManager em = getEntityManager();
        Query pais = em.createNamedQuery("TbPaises.findAll");
        return pais.getResultList();
    }

    public void save(ActionEvent actionEvent) {

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
