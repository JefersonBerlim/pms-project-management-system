/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.exceptions.IllegalOrphanException;
import Controller.exceptions.NonexistentEntityException;
import Controller.exceptions.PreexistingEntityException;
import Controller.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Model.TbFuncionarioTurnoSemana;
import java.util.ArrayList;
import java.util.Collection;
import Model.TbFuncionariosRecursos;
import Model.TbApontamentosMateriais;
import Model.TbProjetos;
import Model.TbApontamentosFuncionarios;
import Model.TbFuncionarios;
import Model.TbProjetoFuncionarios;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author BERLIM
 */
@ManagedBean
public class TbFuncionariosJpaController implements Serializable {

    public TbFuncionariosJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TbFuncionarios tbFuncionarios) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (tbFuncionarios.getTbFuncionarioTurnoSemanaCollection() == null) {
            tbFuncionarios.setTbFuncionarioTurnoSemanaCollection(new ArrayList<TbFuncionarioTurnoSemana>());
        }
        if (tbFuncionarios.getTbFuncionariosRecursosCollection() == null) {
            tbFuncionarios.setTbFuncionariosRecursosCollection(new ArrayList<TbFuncionariosRecursos>());
        }
        if (tbFuncionarios.getTbApontamentosMateriaisCollection() == null) {
            tbFuncionarios.setTbApontamentosMateriaisCollection(new ArrayList<TbApontamentosMateriais>());
        }
        if (tbFuncionarios.getTbProjetosCollection() == null) {
            tbFuncionarios.setTbProjetosCollection(new ArrayList<TbProjetos>());
        }
        if (tbFuncionarios.getTbApontamentosFuncionariosCollection() == null) {
            tbFuncionarios.setTbApontamentosFuncionariosCollection(new ArrayList<TbApontamentosFuncionarios>());
        }
        if (tbFuncionarios.getTbProjetoFuncionariosCollection() == null) {
            tbFuncionarios.setTbProjetoFuncionariosCollection(new ArrayList<TbProjetoFuncionarios>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<TbFuncionarioTurnoSemana> attachedTbFuncionarioTurnoSemanaCollection = new ArrayList<TbFuncionarioTurnoSemana>();
            for (TbFuncionarioTurnoSemana tbFuncionarioTurnoSemanaCollectionTbFuncionarioTurnoSemanaToAttach : tbFuncionarios.getTbFuncionarioTurnoSemanaCollection()) {
                tbFuncionarioTurnoSemanaCollectionTbFuncionarioTurnoSemanaToAttach = em.getReference(tbFuncionarioTurnoSemanaCollectionTbFuncionarioTurnoSemanaToAttach.getClass(), tbFuncionarioTurnoSemanaCollectionTbFuncionarioTurnoSemanaToAttach.getHand());
                attachedTbFuncionarioTurnoSemanaCollection.add(tbFuncionarioTurnoSemanaCollectionTbFuncionarioTurnoSemanaToAttach);
            }
            tbFuncionarios.setTbFuncionarioTurnoSemanaCollection(attachedTbFuncionarioTurnoSemanaCollection);
            Collection<TbFuncionariosRecursos> attachedTbFuncionariosRecursosCollection = new ArrayList<TbFuncionariosRecursos>();
            for (TbFuncionariosRecursos tbFuncionariosRecursosCollectionTbFuncionariosRecursosToAttach : tbFuncionarios.getTbFuncionariosRecursosCollection()) {
                tbFuncionariosRecursosCollectionTbFuncionariosRecursosToAttach = em.getReference(tbFuncionariosRecursosCollectionTbFuncionariosRecursosToAttach.getClass(), tbFuncionariosRecursosCollectionTbFuncionariosRecursosToAttach.getHand());
                attachedTbFuncionariosRecursosCollection.add(tbFuncionariosRecursosCollectionTbFuncionariosRecursosToAttach);
            }
            tbFuncionarios.setTbFuncionariosRecursosCollection(attachedTbFuncionariosRecursosCollection);
            Collection<TbApontamentosMateriais> attachedTbApontamentosMateriaisCollection = new ArrayList<TbApontamentosMateriais>();
            for (TbApontamentosMateriais tbApontamentosMateriaisCollectionTbApontamentosMateriaisToAttach : tbFuncionarios.getTbApontamentosMateriaisCollection()) {
                tbApontamentosMateriaisCollectionTbApontamentosMateriaisToAttach = em.getReference(tbApontamentosMateriaisCollectionTbApontamentosMateriaisToAttach.getClass(), tbApontamentosMateriaisCollectionTbApontamentosMateriaisToAttach.getHand());
                attachedTbApontamentosMateriaisCollection.add(tbApontamentosMateriaisCollectionTbApontamentosMateriaisToAttach);
            }
            tbFuncionarios.setTbApontamentosMateriaisCollection(attachedTbApontamentosMateriaisCollection);
            Collection<TbProjetos> attachedTbProjetosCollection = new ArrayList<TbProjetos>();
            for (TbProjetos tbProjetosCollectionTbProjetosToAttach : tbFuncionarios.getTbProjetosCollection()) {
                tbProjetosCollectionTbProjetosToAttach = em.getReference(tbProjetosCollectionTbProjetosToAttach.getClass(), tbProjetosCollectionTbProjetosToAttach.getHand());
                attachedTbProjetosCollection.add(tbProjetosCollectionTbProjetosToAttach);
            }
            tbFuncionarios.setTbProjetosCollection(attachedTbProjetosCollection);
            Collection<TbApontamentosFuncionarios> attachedTbApontamentosFuncionariosCollection = new ArrayList<TbApontamentosFuncionarios>();
            for (TbApontamentosFuncionarios tbApontamentosFuncionariosCollectionTbApontamentosFuncionariosToAttach : tbFuncionarios.getTbApontamentosFuncionariosCollection()) {
                tbApontamentosFuncionariosCollectionTbApontamentosFuncionariosToAttach = em.getReference(tbApontamentosFuncionariosCollectionTbApontamentosFuncionariosToAttach.getClass(), tbApontamentosFuncionariosCollectionTbApontamentosFuncionariosToAttach.getHand());
                attachedTbApontamentosFuncionariosCollection.add(tbApontamentosFuncionariosCollectionTbApontamentosFuncionariosToAttach);
            }
            tbFuncionarios.setTbApontamentosFuncionariosCollection(attachedTbApontamentosFuncionariosCollection);
            Collection<TbProjetoFuncionarios> attachedTbProjetoFuncionariosCollection = new ArrayList<TbProjetoFuncionarios>();
            for (TbProjetoFuncionarios tbProjetoFuncionariosCollectionTbProjetoFuncionariosToAttach : tbFuncionarios.getTbProjetoFuncionariosCollection()) {
                tbProjetoFuncionariosCollectionTbProjetoFuncionariosToAttach = em.getReference(tbProjetoFuncionariosCollectionTbProjetoFuncionariosToAttach.getClass(), tbProjetoFuncionariosCollectionTbProjetoFuncionariosToAttach.getHand());
                attachedTbProjetoFuncionariosCollection.add(tbProjetoFuncionariosCollectionTbProjetoFuncionariosToAttach);
            }
            tbFuncionarios.setTbProjetoFuncionariosCollection(attachedTbProjetoFuncionariosCollection);
            em.persist(tbFuncionarios);
            for (TbFuncionarioTurnoSemana tbFuncionarioTurnoSemanaCollectionTbFuncionarioTurnoSemana : tbFuncionarios.getTbFuncionarioTurnoSemanaCollection()) {
                TbFuncionarios oldTbFuncionariosHandOfTbFuncionarioTurnoSemanaCollectionTbFuncionarioTurnoSemana = tbFuncionarioTurnoSemanaCollectionTbFuncionarioTurnoSemana.getTbFuncionariosHand();
                tbFuncionarioTurnoSemanaCollectionTbFuncionarioTurnoSemana.setTbFuncionariosHand(tbFuncionarios);
                tbFuncionarioTurnoSemanaCollectionTbFuncionarioTurnoSemana = em.merge(tbFuncionarioTurnoSemanaCollectionTbFuncionarioTurnoSemana);
                if (oldTbFuncionariosHandOfTbFuncionarioTurnoSemanaCollectionTbFuncionarioTurnoSemana != null) {
                    oldTbFuncionariosHandOfTbFuncionarioTurnoSemanaCollectionTbFuncionarioTurnoSemana.getTbFuncionarioTurnoSemanaCollection().remove(tbFuncionarioTurnoSemanaCollectionTbFuncionarioTurnoSemana);
                    oldTbFuncionariosHandOfTbFuncionarioTurnoSemanaCollectionTbFuncionarioTurnoSemana = em.merge(oldTbFuncionariosHandOfTbFuncionarioTurnoSemanaCollectionTbFuncionarioTurnoSemana);
                }
            }
            for (TbFuncionariosRecursos tbFuncionariosRecursosCollectionTbFuncionariosRecursos : tbFuncionarios.getTbFuncionariosRecursosCollection()) {
                TbFuncionarios oldTbFuncionariosHandOfTbFuncionariosRecursosCollectionTbFuncionariosRecursos = tbFuncionariosRecursosCollectionTbFuncionariosRecursos.getTbFuncionariosHand();
                tbFuncionariosRecursosCollectionTbFuncionariosRecursos.setTbFuncionariosHand(tbFuncionarios);
                tbFuncionariosRecursosCollectionTbFuncionariosRecursos = em.merge(tbFuncionariosRecursosCollectionTbFuncionariosRecursos);
                if (oldTbFuncionariosHandOfTbFuncionariosRecursosCollectionTbFuncionariosRecursos != null) {
                    oldTbFuncionariosHandOfTbFuncionariosRecursosCollectionTbFuncionariosRecursos.getTbFuncionariosRecursosCollection().remove(tbFuncionariosRecursosCollectionTbFuncionariosRecursos);
                    oldTbFuncionariosHandOfTbFuncionariosRecursosCollectionTbFuncionariosRecursos = em.merge(oldTbFuncionariosHandOfTbFuncionariosRecursosCollectionTbFuncionariosRecursos);
                }
            }
            for (TbApontamentosMateriais tbApontamentosMateriaisCollectionTbApontamentosMateriais : tbFuncionarios.getTbApontamentosMateriaisCollection()) {
                TbFuncionarios oldTbFuncionariosHandOfTbApontamentosMateriaisCollectionTbApontamentosMateriais = tbApontamentosMateriaisCollectionTbApontamentosMateriais.getTbFuncionariosHand();
                tbApontamentosMateriaisCollectionTbApontamentosMateriais.setTbFuncionariosHand(tbFuncionarios);
                tbApontamentosMateriaisCollectionTbApontamentosMateriais = em.merge(tbApontamentosMateriaisCollectionTbApontamentosMateriais);
                if (oldTbFuncionariosHandOfTbApontamentosMateriaisCollectionTbApontamentosMateriais != null) {
                    oldTbFuncionariosHandOfTbApontamentosMateriaisCollectionTbApontamentosMateriais.getTbApontamentosMateriaisCollection().remove(tbApontamentosMateriaisCollectionTbApontamentosMateriais);
                    oldTbFuncionariosHandOfTbApontamentosMateriaisCollectionTbApontamentosMateriais = em.merge(oldTbFuncionariosHandOfTbApontamentosMateriaisCollectionTbApontamentosMateriais);
                }
            }
            for (TbProjetos tbProjetosCollectionTbProjetos : tbFuncionarios.getTbProjetosCollection()) {
                TbFuncionarios oldTbFuncionariosHandOfTbProjetosCollectionTbProjetos = tbProjetosCollectionTbProjetos.getTbFuncionariosHand();
                tbProjetosCollectionTbProjetos.setTbFuncionariosHand(tbFuncionarios);
                tbProjetosCollectionTbProjetos = em.merge(tbProjetosCollectionTbProjetos);
                if (oldTbFuncionariosHandOfTbProjetosCollectionTbProjetos != null) {
                    oldTbFuncionariosHandOfTbProjetosCollectionTbProjetos.getTbProjetosCollection().remove(tbProjetosCollectionTbProjetos);
                    oldTbFuncionariosHandOfTbProjetosCollectionTbProjetos = em.merge(oldTbFuncionariosHandOfTbProjetosCollectionTbProjetos);
                }
            }
            for (TbApontamentosFuncionarios tbApontamentosFuncionariosCollectionTbApontamentosFuncionarios : tbFuncionarios.getTbApontamentosFuncionariosCollection()) {
                TbFuncionarios oldTbFuncionariosHandOfTbApontamentosFuncionariosCollectionTbApontamentosFuncionarios = tbApontamentosFuncionariosCollectionTbApontamentosFuncionarios.getTbFuncionariosHand();
                tbApontamentosFuncionariosCollectionTbApontamentosFuncionarios.setTbFuncionariosHand(tbFuncionarios);
                tbApontamentosFuncionariosCollectionTbApontamentosFuncionarios = em.merge(tbApontamentosFuncionariosCollectionTbApontamentosFuncionarios);
                if (oldTbFuncionariosHandOfTbApontamentosFuncionariosCollectionTbApontamentosFuncionarios != null) {
                    oldTbFuncionariosHandOfTbApontamentosFuncionariosCollectionTbApontamentosFuncionarios.getTbApontamentosFuncionariosCollection().remove(tbApontamentosFuncionariosCollectionTbApontamentosFuncionarios);
                    oldTbFuncionariosHandOfTbApontamentosFuncionariosCollectionTbApontamentosFuncionarios = em.merge(oldTbFuncionariosHandOfTbApontamentosFuncionariosCollectionTbApontamentosFuncionarios);
                }
            }
            for (TbProjetoFuncionarios tbProjetoFuncionariosCollectionTbProjetoFuncionarios : tbFuncionarios.getTbProjetoFuncionariosCollection()) {
                TbFuncionarios oldTbFuncionariosHandOfTbProjetoFuncionariosCollectionTbProjetoFuncionarios = tbProjetoFuncionariosCollectionTbProjetoFuncionarios.getTbFuncionariosHand();
                tbProjetoFuncionariosCollectionTbProjetoFuncionarios.setTbFuncionariosHand(tbFuncionarios);
                tbProjetoFuncionariosCollectionTbProjetoFuncionarios = em.merge(tbProjetoFuncionariosCollectionTbProjetoFuncionarios);
                if (oldTbFuncionariosHandOfTbProjetoFuncionariosCollectionTbProjetoFuncionarios != null) {
                    oldTbFuncionariosHandOfTbProjetoFuncionariosCollectionTbProjetoFuncionarios.getTbProjetoFuncionariosCollection().remove(tbProjetoFuncionariosCollectionTbProjetoFuncionarios);
                    oldTbFuncionariosHandOfTbProjetoFuncionariosCollectionTbProjetoFuncionarios = em.merge(oldTbFuncionariosHandOfTbProjetoFuncionariosCollectionTbProjetoFuncionarios);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTbFuncionarios(tbFuncionarios.getHand()) != null) {
                throw new PreexistingEntityException("TbFuncionarios " + tbFuncionarios + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TbFuncionarios tbFuncionarios) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbFuncionarios persistentTbFuncionarios = em.find(TbFuncionarios.class, tbFuncionarios.getHand());
            Collection<TbFuncionarioTurnoSemana> tbFuncionarioTurnoSemanaCollectionOld = persistentTbFuncionarios.getTbFuncionarioTurnoSemanaCollection();
            Collection<TbFuncionarioTurnoSemana> tbFuncionarioTurnoSemanaCollectionNew = tbFuncionarios.getTbFuncionarioTurnoSemanaCollection();
            Collection<TbFuncionariosRecursos> tbFuncionariosRecursosCollectionOld = persistentTbFuncionarios.getTbFuncionariosRecursosCollection();
            Collection<TbFuncionariosRecursos> tbFuncionariosRecursosCollectionNew = tbFuncionarios.getTbFuncionariosRecursosCollection();
            Collection<TbApontamentosMateriais> tbApontamentosMateriaisCollectionOld = persistentTbFuncionarios.getTbApontamentosMateriaisCollection();
            Collection<TbApontamentosMateriais> tbApontamentosMateriaisCollectionNew = tbFuncionarios.getTbApontamentosMateriaisCollection();
            Collection<TbProjetos> tbProjetosCollectionOld = persistentTbFuncionarios.getTbProjetosCollection();
            Collection<TbProjetos> tbProjetosCollectionNew = tbFuncionarios.getTbProjetosCollection();
            Collection<TbApontamentosFuncionarios> tbApontamentosFuncionariosCollectionOld = persistentTbFuncionarios.getTbApontamentosFuncionariosCollection();
            Collection<TbApontamentosFuncionarios> tbApontamentosFuncionariosCollectionNew = tbFuncionarios.getTbApontamentosFuncionariosCollection();
            Collection<TbProjetoFuncionarios> tbProjetoFuncionariosCollectionOld = persistentTbFuncionarios.getTbProjetoFuncionariosCollection();
            Collection<TbProjetoFuncionarios> tbProjetoFuncionariosCollectionNew = tbFuncionarios.getTbProjetoFuncionariosCollection();
            List<String> illegalOrphanMessages = null;
            for (TbFuncionarioTurnoSemana tbFuncionarioTurnoSemanaCollectionOldTbFuncionarioTurnoSemana : tbFuncionarioTurnoSemanaCollectionOld) {
                if (!tbFuncionarioTurnoSemanaCollectionNew.contains(tbFuncionarioTurnoSemanaCollectionOldTbFuncionarioTurnoSemana)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TbFuncionarioTurnoSemana " + tbFuncionarioTurnoSemanaCollectionOldTbFuncionarioTurnoSemana + " since its tbFuncionariosHand field is not nullable.");
                }
            }
            for (TbFuncionariosRecursos tbFuncionariosRecursosCollectionOldTbFuncionariosRecursos : tbFuncionariosRecursosCollectionOld) {
                if (!tbFuncionariosRecursosCollectionNew.contains(tbFuncionariosRecursosCollectionOldTbFuncionariosRecursos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TbFuncionariosRecursos " + tbFuncionariosRecursosCollectionOldTbFuncionariosRecursos + " since its tbFuncionariosHand field is not nullable.");
                }
            }
            for (TbApontamentosMateriais tbApontamentosMateriaisCollectionOldTbApontamentosMateriais : tbApontamentosMateriaisCollectionOld) {
                if (!tbApontamentosMateriaisCollectionNew.contains(tbApontamentosMateriaisCollectionOldTbApontamentosMateriais)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TbApontamentosMateriais " + tbApontamentosMateriaisCollectionOldTbApontamentosMateriais + " since its tbFuncionariosHand field is not nullable.");
                }
            }
            for (TbProjetos tbProjetosCollectionOldTbProjetos : tbProjetosCollectionOld) {
                if (!tbProjetosCollectionNew.contains(tbProjetosCollectionOldTbProjetos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TbProjetos " + tbProjetosCollectionOldTbProjetos + " since its tbFuncionariosHand field is not nullable.");
                }
            }
            for (TbApontamentosFuncionarios tbApontamentosFuncionariosCollectionOldTbApontamentosFuncionarios : tbApontamentosFuncionariosCollectionOld) {
                if (!tbApontamentosFuncionariosCollectionNew.contains(tbApontamentosFuncionariosCollectionOldTbApontamentosFuncionarios)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TbApontamentosFuncionarios " + tbApontamentosFuncionariosCollectionOldTbApontamentosFuncionarios + " since its tbFuncionariosHand field is not nullable.");
                }
            }
            for (TbProjetoFuncionarios tbProjetoFuncionariosCollectionOldTbProjetoFuncionarios : tbProjetoFuncionariosCollectionOld) {
                if (!tbProjetoFuncionariosCollectionNew.contains(tbProjetoFuncionariosCollectionOldTbProjetoFuncionarios)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TbProjetoFuncionarios " + tbProjetoFuncionariosCollectionOldTbProjetoFuncionarios + " since its tbFuncionariosHand field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<TbFuncionarioTurnoSemana> attachedTbFuncionarioTurnoSemanaCollectionNew = new ArrayList<TbFuncionarioTurnoSemana>();
            for (TbFuncionarioTurnoSemana tbFuncionarioTurnoSemanaCollectionNewTbFuncionarioTurnoSemanaToAttach : tbFuncionarioTurnoSemanaCollectionNew) {
                tbFuncionarioTurnoSemanaCollectionNewTbFuncionarioTurnoSemanaToAttach = em.getReference(tbFuncionarioTurnoSemanaCollectionNewTbFuncionarioTurnoSemanaToAttach.getClass(), tbFuncionarioTurnoSemanaCollectionNewTbFuncionarioTurnoSemanaToAttach.getHand());
                attachedTbFuncionarioTurnoSemanaCollectionNew.add(tbFuncionarioTurnoSemanaCollectionNewTbFuncionarioTurnoSemanaToAttach);
            }
            tbFuncionarioTurnoSemanaCollectionNew = attachedTbFuncionarioTurnoSemanaCollectionNew;
            tbFuncionarios.setTbFuncionarioTurnoSemanaCollection(tbFuncionarioTurnoSemanaCollectionNew);
            Collection<TbFuncionariosRecursos> attachedTbFuncionariosRecursosCollectionNew = new ArrayList<TbFuncionariosRecursos>();
            for (TbFuncionariosRecursos tbFuncionariosRecursosCollectionNewTbFuncionariosRecursosToAttach : tbFuncionariosRecursosCollectionNew) {
                tbFuncionariosRecursosCollectionNewTbFuncionariosRecursosToAttach = em.getReference(tbFuncionariosRecursosCollectionNewTbFuncionariosRecursosToAttach.getClass(), tbFuncionariosRecursosCollectionNewTbFuncionariosRecursosToAttach.getHand());
                attachedTbFuncionariosRecursosCollectionNew.add(tbFuncionariosRecursosCollectionNewTbFuncionariosRecursosToAttach);
            }
            tbFuncionariosRecursosCollectionNew = attachedTbFuncionariosRecursosCollectionNew;
            tbFuncionarios.setTbFuncionariosRecursosCollection(tbFuncionariosRecursosCollectionNew);
            Collection<TbApontamentosMateriais> attachedTbApontamentosMateriaisCollectionNew = new ArrayList<TbApontamentosMateriais>();
            for (TbApontamentosMateriais tbApontamentosMateriaisCollectionNewTbApontamentosMateriaisToAttach : tbApontamentosMateriaisCollectionNew) {
                tbApontamentosMateriaisCollectionNewTbApontamentosMateriaisToAttach = em.getReference(tbApontamentosMateriaisCollectionNewTbApontamentosMateriaisToAttach.getClass(), tbApontamentosMateriaisCollectionNewTbApontamentosMateriaisToAttach.getHand());
                attachedTbApontamentosMateriaisCollectionNew.add(tbApontamentosMateriaisCollectionNewTbApontamentosMateriaisToAttach);
            }
            tbApontamentosMateriaisCollectionNew = attachedTbApontamentosMateriaisCollectionNew;
            tbFuncionarios.setTbApontamentosMateriaisCollection(tbApontamentosMateriaisCollectionNew);
            Collection<TbProjetos> attachedTbProjetosCollectionNew = new ArrayList<TbProjetos>();
            for (TbProjetos tbProjetosCollectionNewTbProjetosToAttach : tbProjetosCollectionNew) {
                tbProjetosCollectionNewTbProjetosToAttach = em.getReference(tbProjetosCollectionNewTbProjetosToAttach.getClass(), tbProjetosCollectionNewTbProjetosToAttach.getHand());
                attachedTbProjetosCollectionNew.add(tbProjetosCollectionNewTbProjetosToAttach);
            }
            tbProjetosCollectionNew = attachedTbProjetosCollectionNew;
            tbFuncionarios.setTbProjetosCollection(tbProjetosCollectionNew);
            Collection<TbApontamentosFuncionarios> attachedTbApontamentosFuncionariosCollectionNew = new ArrayList<TbApontamentosFuncionarios>();
            for (TbApontamentosFuncionarios tbApontamentosFuncionariosCollectionNewTbApontamentosFuncionariosToAttach : tbApontamentosFuncionariosCollectionNew) {
                tbApontamentosFuncionariosCollectionNewTbApontamentosFuncionariosToAttach = em.getReference(tbApontamentosFuncionariosCollectionNewTbApontamentosFuncionariosToAttach.getClass(), tbApontamentosFuncionariosCollectionNewTbApontamentosFuncionariosToAttach.getHand());
                attachedTbApontamentosFuncionariosCollectionNew.add(tbApontamentosFuncionariosCollectionNewTbApontamentosFuncionariosToAttach);
            }
            tbApontamentosFuncionariosCollectionNew = attachedTbApontamentosFuncionariosCollectionNew;
            tbFuncionarios.setTbApontamentosFuncionariosCollection(tbApontamentosFuncionariosCollectionNew);
            Collection<TbProjetoFuncionarios> attachedTbProjetoFuncionariosCollectionNew = new ArrayList<TbProjetoFuncionarios>();
            for (TbProjetoFuncionarios tbProjetoFuncionariosCollectionNewTbProjetoFuncionariosToAttach : tbProjetoFuncionariosCollectionNew) {
                tbProjetoFuncionariosCollectionNewTbProjetoFuncionariosToAttach = em.getReference(tbProjetoFuncionariosCollectionNewTbProjetoFuncionariosToAttach.getClass(), tbProjetoFuncionariosCollectionNewTbProjetoFuncionariosToAttach.getHand());
                attachedTbProjetoFuncionariosCollectionNew.add(tbProjetoFuncionariosCollectionNewTbProjetoFuncionariosToAttach);
            }
            tbProjetoFuncionariosCollectionNew = attachedTbProjetoFuncionariosCollectionNew;
            tbFuncionarios.setTbProjetoFuncionariosCollection(tbProjetoFuncionariosCollectionNew);
            tbFuncionarios = em.merge(tbFuncionarios);
            for (TbFuncionarioTurnoSemana tbFuncionarioTurnoSemanaCollectionNewTbFuncionarioTurnoSemana : tbFuncionarioTurnoSemanaCollectionNew) {
                if (!tbFuncionarioTurnoSemanaCollectionOld.contains(tbFuncionarioTurnoSemanaCollectionNewTbFuncionarioTurnoSemana)) {
                    TbFuncionarios oldTbFuncionariosHandOfTbFuncionarioTurnoSemanaCollectionNewTbFuncionarioTurnoSemana = tbFuncionarioTurnoSemanaCollectionNewTbFuncionarioTurnoSemana.getTbFuncionariosHand();
                    tbFuncionarioTurnoSemanaCollectionNewTbFuncionarioTurnoSemana.setTbFuncionariosHand(tbFuncionarios);
                    tbFuncionarioTurnoSemanaCollectionNewTbFuncionarioTurnoSemana = em.merge(tbFuncionarioTurnoSemanaCollectionNewTbFuncionarioTurnoSemana);
                    if (oldTbFuncionariosHandOfTbFuncionarioTurnoSemanaCollectionNewTbFuncionarioTurnoSemana != null && !oldTbFuncionariosHandOfTbFuncionarioTurnoSemanaCollectionNewTbFuncionarioTurnoSemana.equals(tbFuncionarios)) {
                        oldTbFuncionariosHandOfTbFuncionarioTurnoSemanaCollectionNewTbFuncionarioTurnoSemana.getTbFuncionarioTurnoSemanaCollection().remove(tbFuncionarioTurnoSemanaCollectionNewTbFuncionarioTurnoSemana);
                        oldTbFuncionariosHandOfTbFuncionarioTurnoSemanaCollectionNewTbFuncionarioTurnoSemana = em.merge(oldTbFuncionariosHandOfTbFuncionarioTurnoSemanaCollectionNewTbFuncionarioTurnoSemana);
                    }
                }
            }
            for (TbFuncionariosRecursos tbFuncionariosRecursosCollectionNewTbFuncionariosRecursos : tbFuncionariosRecursosCollectionNew) {
                if (!tbFuncionariosRecursosCollectionOld.contains(tbFuncionariosRecursosCollectionNewTbFuncionariosRecursos)) {
                    TbFuncionarios oldTbFuncionariosHandOfTbFuncionariosRecursosCollectionNewTbFuncionariosRecursos = tbFuncionariosRecursosCollectionNewTbFuncionariosRecursos.getTbFuncionariosHand();
                    tbFuncionariosRecursosCollectionNewTbFuncionariosRecursos.setTbFuncionariosHand(tbFuncionarios);
                    tbFuncionariosRecursosCollectionNewTbFuncionariosRecursos = em.merge(tbFuncionariosRecursosCollectionNewTbFuncionariosRecursos);
                    if (oldTbFuncionariosHandOfTbFuncionariosRecursosCollectionNewTbFuncionariosRecursos != null && !oldTbFuncionariosHandOfTbFuncionariosRecursosCollectionNewTbFuncionariosRecursos.equals(tbFuncionarios)) {
                        oldTbFuncionariosHandOfTbFuncionariosRecursosCollectionNewTbFuncionariosRecursos.getTbFuncionariosRecursosCollection().remove(tbFuncionariosRecursosCollectionNewTbFuncionariosRecursos);
                        oldTbFuncionariosHandOfTbFuncionariosRecursosCollectionNewTbFuncionariosRecursos = em.merge(oldTbFuncionariosHandOfTbFuncionariosRecursosCollectionNewTbFuncionariosRecursos);
                    }
                }
            }
            for (TbApontamentosMateriais tbApontamentosMateriaisCollectionNewTbApontamentosMateriais : tbApontamentosMateriaisCollectionNew) {
                if (!tbApontamentosMateriaisCollectionOld.contains(tbApontamentosMateriaisCollectionNewTbApontamentosMateriais)) {
                    TbFuncionarios oldTbFuncionariosHandOfTbApontamentosMateriaisCollectionNewTbApontamentosMateriais = tbApontamentosMateriaisCollectionNewTbApontamentosMateriais.getTbFuncionariosHand();
                    tbApontamentosMateriaisCollectionNewTbApontamentosMateriais.setTbFuncionariosHand(tbFuncionarios);
                    tbApontamentosMateriaisCollectionNewTbApontamentosMateriais = em.merge(tbApontamentosMateriaisCollectionNewTbApontamentosMateriais);
                    if (oldTbFuncionariosHandOfTbApontamentosMateriaisCollectionNewTbApontamentosMateriais != null && !oldTbFuncionariosHandOfTbApontamentosMateriaisCollectionNewTbApontamentosMateriais.equals(tbFuncionarios)) {
                        oldTbFuncionariosHandOfTbApontamentosMateriaisCollectionNewTbApontamentosMateriais.getTbApontamentosMateriaisCollection().remove(tbApontamentosMateriaisCollectionNewTbApontamentosMateriais);
                        oldTbFuncionariosHandOfTbApontamentosMateriaisCollectionNewTbApontamentosMateriais = em.merge(oldTbFuncionariosHandOfTbApontamentosMateriaisCollectionNewTbApontamentosMateriais);
                    }
                }
            }
            for (TbProjetos tbProjetosCollectionNewTbProjetos : tbProjetosCollectionNew) {
                if (!tbProjetosCollectionOld.contains(tbProjetosCollectionNewTbProjetos)) {
                    TbFuncionarios oldTbFuncionariosHandOfTbProjetosCollectionNewTbProjetos = tbProjetosCollectionNewTbProjetos.getTbFuncionariosHand();
                    tbProjetosCollectionNewTbProjetos.setTbFuncionariosHand(tbFuncionarios);
                    tbProjetosCollectionNewTbProjetos = em.merge(tbProjetosCollectionNewTbProjetos);
                    if (oldTbFuncionariosHandOfTbProjetosCollectionNewTbProjetos != null && !oldTbFuncionariosHandOfTbProjetosCollectionNewTbProjetos.equals(tbFuncionarios)) {
                        oldTbFuncionariosHandOfTbProjetosCollectionNewTbProjetos.getTbProjetosCollection().remove(tbProjetosCollectionNewTbProjetos);
                        oldTbFuncionariosHandOfTbProjetosCollectionNewTbProjetos = em.merge(oldTbFuncionariosHandOfTbProjetosCollectionNewTbProjetos);
                    }
                }
            }
            for (TbApontamentosFuncionarios tbApontamentosFuncionariosCollectionNewTbApontamentosFuncionarios : tbApontamentosFuncionariosCollectionNew) {
                if (!tbApontamentosFuncionariosCollectionOld.contains(tbApontamentosFuncionariosCollectionNewTbApontamentosFuncionarios)) {
                    TbFuncionarios oldTbFuncionariosHandOfTbApontamentosFuncionariosCollectionNewTbApontamentosFuncionarios = tbApontamentosFuncionariosCollectionNewTbApontamentosFuncionarios.getTbFuncionariosHand();
                    tbApontamentosFuncionariosCollectionNewTbApontamentosFuncionarios.setTbFuncionariosHand(tbFuncionarios);
                    tbApontamentosFuncionariosCollectionNewTbApontamentosFuncionarios = em.merge(tbApontamentosFuncionariosCollectionNewTbApontamentosFuncionarios);
                    if (oldTbFuncionariosHandOfTbApontamentosFuncionariosCollectionNewTbApontamentosFuncionarios != null && !oldTbFuncionariosHandOfTbApontamentosFuncionariosCollectionNewTbApontamentosFuncionarios.equals(tbFuncionarios)) {
                        oldTbFuncionariosHandOfTbApontamentosFuncionariosCollectionNewTbApontamentosFuncionarios.getTbApontamentosFuncionariosCollection().remove(tbApontamentosFuncionariosCollectionNewTbApontamentosFuncionarios);
                        oldTbFuncionariosHandOfTbApontamentosFuncionariosCollectionNewTbApontamentosFuncionarios = em.merge(oldTbFuncionariosHandOfTbApontamentosFuncionariosCollectionNewTbApontamentosFuncionarios);
                    }
                }
            }
            for (TbProjetoFuncionarios tbProjetoFuncionariosCollectionNewTbProjetoFuncionarios : tbProjetoFuncionariosCollectionNew) {
                if (!tbProjetoFuncionariosCollectionOld.contains(tbProjetoFuncionariosCollectionNewTbProjetoFuncionarios)) {
                    TbFuncionarios oldTbFuncionariosHandOfTbProjetoFuncionariosCollectionNewTbProjetoFuncionarios = tbProjetoFuncionariosCollectionNewTbProjetoFuncionarios.getTbFuncionariosHand();
                    tbProjetoFuncionariosCollectionNewTbProjetoFuncionarios.setTbFuncionariosHand(tbFuncionarios);
                    tbProjetoFuncionariosCollectionNewTbProjetoFuncionarios = em.merge(tbProjetoFuncionariosCollectionNewTbProjetoFuncionarios);
                    if (oldTbFuncionariosHandOfTbProjetoFuncionariosCollectionNewTbProjetoFuncionarios != null && !oldTbFuncionariosHandOfTbProjetoFuncionariosCollectionNewTbProjetoFuncionarios.equals(tbFuncionarios)) {
                        oldTbFuncionariosHandOfTbProjetoFuncionariosCollectionNewTbProjetoFuncionarios.getTbProjetoFuncionariosCollection().remove(tbProjetoFuncionariosCollectionNewTbProjetoFuncionarios);
                        oldTbFuncionariosHandOfTbProjetoFuncionariosCollectionNewTbProjetoFuncionarios = em.merge(oldTbFuncionariosHandOfTbProjetoFuncionariosCollectionNewTbProjetoFuncionarios);
                    }
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tbFuncionarios.getHand();
                if (findTbFuncionarios(id) == null) {
                    throw new NonexistentEntityException("The tbFuncionarios with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbFuncionarios tbFuncionarios;
            try {
                tbFuncionarios = em.getReference(TbFuncionarios.class, id);
                tbFuncionarios.getHand();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tbFuncionarios with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TbFuncionarioTurnoSemana> tbFuncionarioTurnoSemanaCollectionOrphanCheck = tbFuncionarios.getTbFuncionarioTurnoSemanaCollection();
            for (TbFuncionarioTurnoSemana tbFuncionarioTurnoSemanaCollectionOrphanCheckTbFuncionarioTurnoSemana : tbFuncionarioTurnoSemanaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbFuncionarios (" + tbFuncionarios + ") cannot be destroyed since the TbFuncionarioTurnoSemana " + tbFuncionarioTurnoSemanaCollectionOrphanCheckTbFuncionarioTurnoSemana + " in its tbFuncionarioTurnoSemanaCollection field has a non-nullable tbFuncionariosHand field.");
            }
            Collection<TbFuncionariosRecursos> tbFuncionariosRecursosCollectionOrphanCheck = tbFuncionarios.getTbFuncionariosRecursosCollection();
            for (TbFuncionariosRecursos tbFuncionariosRecursosCollectionOrphanCheckTbFuncionariosRecursos : tbFuncionariosRecursosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbFuncionarios (" + tbFuncionarios + ") cannot be destroyed since the TbFuncionariosRecursos " + tbFuncionariosRecursosCollectionOrphanCheckTbFuncionariosRecursos + " in its tbFuncionariosRecursosCollection field has a non-nullable tbFuncionariosHand field.");
            }
            Collection<TbApontamentosMateriais> tbApontamentosMateriaisCollectionOrphanCheck = tbFuncionarios.getTbApontamentosMateriaisCollection();
            for (TbApontamentosMateriais tbApontamentosMateriaisCollectionOrphanCheckTbApontamentosMateriais : tbApontamentosMateriaisCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbFuncionarios (" + tbFuncionarios + ") cannot be destroyed since the TbApontamentosMateriais " + tbApontamentosMateriaisCollectionOrphanCheckTbApontamentosMateriais + " in its tbApontamentosMateriaisCollection field has a non-nullable tbFuncionariosHand field.");
            }
            Collection<TbProjetos> tbProjetosCollectionOrphanCheck = tbFuncionarios.getTbProjetosCollection();
            for (TbProjetos tbProjetosCollectionOrphanCheckTbProjetos : tbProjetosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbFuncionarios (" + tbFuncionarios + ") cannot be destroyed since the TbProjetos " + tbProjetosCollectionOrphanCheckTbProjetos + " in its tbProjetosCollection field has a non-nullable tbFuncionariosHand field.");
            }
            Collection<TbApontamentosFuncionarios> tbApontamentosFuncionariosCollectionOrphanCheck = tbFuncionarios.getTbApontamentosFuncionariosCollection();
            for (TbApontamentosFuncionarios tbApontamentosFuncionariosCollectionOrphanCheckTbApontamentosFuncionarios : tbApontamentosFuncionariosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbFuncionarios (" + tbFuncionarios + ") cannot be destroyed since the TbApontamentosFuncionarios " + tbApontamentosFuncionariosCollectionOrphanCheckTbApontamentosFuncionarios + " in its tbApontamentosFuncionariosCollection field has a non-nullable tbFuncionariosHand field.");
            }
            Collection<TbProjetoFuncionarios> tbProjetoFuncionariosCollectionOrphanCheck = tbFuncionarios.getTbProjetoFuncionariosCollection();
            for (TbProjetoFuncionarios tbProjetoFuncionariosCollectionOrphanCheckTbProjetoFuncionarios : tbProjetoFuncionariosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbFuncionarios (" + tbFuncionarios + ") cannot be destroyed since the TbProjetoFuncionarios " + tbProjetoFuncionariosCollectionOrphanCheckTbProjetoFuncionarios + " in its tbProjetoFuncionariosCollection field has a non-nullable tbFuncionariosHand field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tbFuncionarios);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TbFuncionarios> findTbFuncionariosEntities() {
        return findTbFuncionariosEntities(true, -1, -1);
    }

    public List<TbFuncionarios> findTbFuncionariosEntities(int maxResults, int firstResult) {
        return findTbFuncionariosEntities(false, maxResults, firstResult);
    }

    private List<TbFuncionarios> findTbFuncionariosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TbFuncionarios.class));
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

    public TbFuncionarios findTbFuncionarios(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbFuncionarios.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbFuncionariosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TbFuncionarios> rt = cq.from(TbFuncionarios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
