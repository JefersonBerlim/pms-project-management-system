/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controles;

import Controles.exceptions.NonexistentEntityException;
import Controles.exceptions.PreexistingEntityException;
import Entidades.TbUsuario;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author JEFERSON
 */
@ManagedBean
public class TbUsuarioJpaController implements Serializable {

    public TbUsuarioJpaController() {
        emf = Persistence.createEntityManagerFactory("ProjectManagementSystemPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TbUsuario tbUsuario) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();

            if (tbUsuario.getHand() == null) {
                em.persist(tbUsuario);

            } else {
                em.merge(tbUsuario);
            }

            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTbUsuario(tbUsuario.getHand()) != null) {
                throw new PreexistingEntityException("TbUsuario " + tbUsuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TbUsuario tbUsuario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            tbUsuario = em.merge(tbUsuario);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tbUsuario.getHand();
                if (findTbUsuario(id) == null) {
                    throw new NonexistentEntityException("The tbUsuario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TbUsuario tbUsuario;
            try {
                tbUsuario = em.getReference(TbUsuario.class, id);
                tbUsuario.getHand();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tbUsuario with id " + id + " no longer exists.", enfe);
            }
            em.remove(tbUsuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TbUsuario> findTbUsuarioEntities() {
        return findTbUsuarioEntities(true, -1, -1);
    }

    public List<TbUsuario> findTbUsuarioEntities(int maxResults, int firstResult) {
        return findTbUsuarioEntities(false, maxResults, firstResult);
    }

    private List<TbUsuario> findTbUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TbUsuario.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public TbUsuario findTbUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbUsuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbUsuarioCount() {
        EntityManager em = getEntityManager();
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

    //Método usado para efetuar o login logo na inicialização do sistema
    public String loginUsuario( TbUsuario tbUsuario ) {
        
        EntityManager em = getEntityManager();
        //Busca o usuário pelo nome e pela senha
        Query usuario = em.createNamedQuery("TbUsuario.login").setParameter("nome", tbUsuario.getNome()).setParameter("senha", tbUsuario.getSenha());        
               
        FacesContext context = FacesContext.getCurrentInstance();                    
        
        //Caso esteja vazio retorna null, caso contrário retorna String com "OK"
        if ( usuario.getResultList().isEmpty()  ){
            return "null";
        }else{
            return "ok";
        }
    }
}
