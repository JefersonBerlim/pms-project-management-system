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
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import static javax.faces.component.UIInput.isEmpty;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class TbPaisesJpaController implements Serializable {

    private TbPaises tbPaises;
    private final EntityManagerFactory emf;
    private String mensagem;
    private List<TbPaises> paises;
    private boolean inclusao;

    public TbPaisesJpaController() {
        emf = Persistence.createEntityManagerFactory("ProjectManagementSystemPU");
        tbPaises = new TbPaises();
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public TbPaises getTbPaises() {
        if (tbPaises.getHand() == null) {
            tbPaises.setHand(preparaInclusao());
            inclusao = true;
        }
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

    public List<TbPaises> getPaises() {
        return paises;
    }

    public void setPaises(List<TbPaises> paises) {
        this.paises = paises;
    }

    // Método Usado para Popular a Grid de Países
    public List<TbPaises> getPaisesLista() {
        paises = findPaises();
        return paises;
    }

    // Método Usado para a Abertura da Tela de Paises
    public TbPaises findPaisHand() {
        EntityManager em = getEntityManager();

        Map< String, String> params = FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap();
        String hand = params.get("hand");

        if ( !isEmpty(hand)) {
            tbPaises = findTbPaises(Integer.parseInt(hand));
            this.setTbPaises(tbPaises);
            inclusao = false;
        }

        return tbPaises;
    }

    public void create() throws Exception {
        //obtendo o EntityManager
        EntityManager em = getEntityManager();
        try {
            //inicia o processo de transacao
            em.getTransaction().begin();
            if (tbPaises.getHand() == null || inclusao ) {
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
        EntityManager em = getEntityManager();
        Integer id;
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
        EntityManager em = getEntityManager();
        try {
            return em.find(TbPaises.class, id);
        } finally {
            em.close();
        }
    }

    public List<TbPaises> findPaises() {
        EntityManager em = getEntityManager();
        Query pais = em.createNamedQuery("TbPaises.findAll");
        return pais.getResultList();
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

    public Integer preparaInclusao() {
        EntityManager em = getEntityManager();
        Integer proxCod = 0;
        List<TbPaises> listaPaises = new ArrayList<TbPaises>();
        
        listaPaises = findPaises();
        
        for (Integer i = 0; i < listaPaises.size(); i++) {
            if ( listaPaises.get(i).getHand() > proxCod ) {
                proxCod = listaPaises.get(i).getHand();
            }
        }
        
        return proxCod + 1;
    }
}
