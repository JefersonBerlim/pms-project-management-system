/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.NonexistentEntityException;
import Modelos.TbDiaNaoUtil;
import Utilitarios.Util;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;

/**
 *
 * @author BERLIM
 */
@ManagedBean
@ViewScoped
public class TbDiaNaoUtilJpaController implements Serializable {

    private EntityManagerFactory emf = null;
    private EntityManager em = null;
    private TbDiaNaoUtil tbDiaNaoUtil = new TbDiaNaoUtil();

    public TbDiaNaoUtilJpaController() {
        emf = Persistence.createEntityManagerFactory("ProjectManagementSystemPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public TbDiaNaoUtil getTbDiaNaoUtil() {
        return tbDiaNaoUtil;
    }

    public void setTbDiaNaoUtil(TbDiaNaoUtil tbDiaNaoUtil) {
        this.tbDiaNaoUtil = tbDiaNaoUtil;
    }

    public void create() throws Exception {
        try {
            em = getEntityManager();
            em.getTransaction().begin();

            if (this.tbDiaNaoUtil.getHand() == null) {

                Util utilitarios = new Util();
                this.tbDiaNaoUtil.setHand(utilitarios.contadorObjetos("TbDiaNaoUtil"));
                em.persist(this.tbDiaNaoUtil);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Registro salvo com sucesso!", null));
            } else {

                em.merge(this.tbDiaNaoUtil);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Registro atualizado com sucesso!", null));
            }

            em.getTransaction().commit();

        } catch (Exception ex) {

            em.getTransaction().rollback();
            FacesContext.getCurrentInstance().addMessage(ex.toString(),
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Problemas ao persistir o regitsto.", null));

        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, Exception {
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            tbDiaNaoUtil = em.getReference(TbDiaNaoUtil.class, id);
            tbDiaNaoUtil.getHand();

            em.remove(tbDiaNaoUtil);
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

    public TbDiaNaoUtil findTbDiaNaoUtil(Integer id) {
        em = getEntityManager();
        try {
            return em.find(TbDiaNaoUtil.class, id);
        } finally {
            em.close();
        }
    }

}
