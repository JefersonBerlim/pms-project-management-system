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
import Model.TbApontamentosRecursos;
import java.util.ArrayList;
import java.util.Collection;
import Model.TbFuncionariosRecursos;
import Model.TbProjetosRecursos;
import Model.TbRecursos;
import Model.TbRecursosServicos;
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
public class TbRecursosJpaController implements Serializable {

    public TbRecursosJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TbRecursos tbRecursos) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (tbRecursos.getTbApontamentosRecursosCollection() == null) {
            tbRecursos.setTbApontamentosRecursosCollection(new ArrayList<TbApontamentosRecursos>());
        }
        if (tbRecursos.getTbFuncionariosRecursosCollection() == null) {
            tbRecursos.setTbFuncionariosRecursosCollection(new ArrayList<TbFuncionariosRecursos>());
        }
        if (tbRecursos.getTbProjetosRecursosCollection() == null) {
            tbRecursos.setTbProjetosRecursosCollection(new ArrayList<TbProjetosRecursos>());
        }
        if (tbRecursos.getTbRecursosServicosCollection() == null) {
            tbRecursos.setTbRecursosServicosCollection(new ArrayList<TbRecursosServicos>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<TbApontamentosRecursos> attachedTbApontamentosRecursosCollection = new ArrayList<TbApontamentosRecursos>();
            for (TbApontamentosRecursos tbApontamentosRecursosCollectionTbApontamentosRecursosToAttach : tbRecursos.getTbApontamentosRecursosCollection()) {
                tbApontamentosRecursosCollectionTbApontamentosRecursosToAttach = em.getReference(tbApontamentosRecursosCollectionTbApontamentosRecursosToAttach.getClass(), tbApontamentosRecursosCollectionTbApontamentosRecursosToAttach.getHand());
                attachedTbApontamentosRecursosCollection.add(tbApontamentosRecursosCollectionTbApontamentosRecursosToAttach);
            }
            tbRecursos.setTbApontamentosRecursosCollection(attachedTbApontamentosRecursosCollection);
            Collection<TbFuncionariosRecursos> attachedTbFuncionariosRecursosCollection = new ArrayList<TbFuncionariosRecursos>();
            for (TbFuncionariosRecursos tbFuncionariosRecursosCollectionTbFuncionariosRecursosToAttach : tbRecursos.getTbFuncionariosRecursosCollection()) {
                tbFuncionariosRecursosCollectionTbFuncionariosRecursosToAttach = em.getReference(tbFuncionariosRecursosCollectionTbFuncionariosRecursosToAttach.getClass(), tbFuncionariosRecursosCollectionTbFuncionariosRecursosToAttach.getHand());
                attachedTbFuncionariosRecursosCollection.add(tbFuncionariosRecursosCollectionTbFuncionariosRecursosToAttach);
            }
            tbRecursos.setTbFuncionariosRecursosCollection(attachedTbFuncionariosRecursosCollection);
            Collection<TbProjetosRecursos> attachedTbProjetosRecursosCollection = new ArrayList<TbProjetosRecursos>();
            for (TbProjetosRecursos tbProjetosRecursosCollectionTbProjetosRecursosToAttach : tbRecursos.getTbProjetosRecursosCollection()) {
                tbProjetosRecursosCollectionTbProjetosRecursosToAttach = em.getReference(tbProjetosRecursosCollectionTbProjetosRecursosToAttach.getClass(), tbProjetosRecursosCollectionTbProjetosRecursosToAttach.getHand());
                attachedTbProjetosRecursosCollection.add(tbProjetosRecursosCollectionTbProjetosRecursosToAttach);
            }
            tbRecursos.setTbProjetosRecursosCollection(attachedTbProjetosRecursosCollection);
            Collection<TbRecursosServicos> attachedTbRecursosServicosCollection = new ArrayList<TbRecursosServicos>();
            for (TbRecursosServicos tbRecursosServicosCollectionTbRecursosServicosToAttach : tbRecursos.getTbRecursosServicosCollection()) {
                tbRecursosServicosCollectionTbRecursosServicosToAttach = em.getReference(tbRecursosServicosCollectionTbRecursosServicosToAttach.getClass(), tbRecursosServicosCollectionTbRecursosServicosToAttach.getHand());
                attachedTbRecursosServicosCollection.add(tbRecursosServicosCollectionTbRecursosServicosToAttach);
            }
            tbRecursos.setTbRecursosServicosCollection(attachedTbRecursosServicosCollection);
            em.persist(tbRecursos);
            for (TbApontamentosRecursos tbApontamentosRecursosCollectionTbApontamentosRecursos : tbRecursos.getTbApontamentosRecursosCollection()) {
                TbRecursos oldTbRecursosHandOfTbApontamentosRecursosCollectionTbApontamentosRecursos = tbApontamentosRecursosCollectionTbApontamentosRecursos.getTbRecursosHand();
                tbApontamentosRecursosCollectionTbApontamentosRecursos.setTbRecursosHand(tbRecursos);
                tbApontamentosRecursosCollectionTbApontamentosRecursos = em.merge(tbApontamentosRecursosCollectionTbApontamentosRecursos);
                if (oldTbRecursosHandOfTbApontamentosRecursosCollectionTbApontamentosRecursos != null) {
                    oldTbRecursosHandOfTbApontamentosRecursosCollectionTbApontamentosRecursos.getTbApontamentosRecursosCollection().remove(tbApontamentosRecursosCollectionTbApontamentosRecursos);
                    oldTbRecursosHandOfTbApontamentosRecursosCollectionTbApontamentosRecursos = em.merge(oldTbRecursosHandOfTbApontamentosRecursosCollectionTbApontamentosRecursos);
                }
            }
            for (TbFuncionariosRecursos tbFuncionariosRecursosCollectionTbFuncionariosRecursos : tbRecursos.getTbFuncionariosRecursosCollection()) {
                TbRecursos oldTbRecursosHandOfTbFuncionariosRecursosCollectionTbFuncionariosRecursos = tbFuncionariosRecursosCollectionTbFuncionariosRecursos.getTbRecursosHand();
                tbFuncionariosRecursosCollectionTbFuncionariosRecursos.setTbRecursosHand(tbRecursos);
                tbFuncionariosRecursosCollectionTbFuncionariosRecursos = em.merge(tbFuncionariosRecursosCollectionTbFuncionariosRecursos);
                if (oldTbRecursosHandOfTbFuncionariosRecursosCollectionTbFuncionariosRecursos != null) {
                    oldTbRecursosHandOfTbFuncionariosRecursosCollectionTbFuncionariosRecursos.getTbFuncionariosRecursosCollection().remove(tbFuncionariosRecursosCollectionTbFuncionariosRecursos);
                    oldTbRecursosHandOfTbFuncionariosRecursosCollectionTbFuncionariosRecursos = em.merge(oldTbRecursosHandOfTbFuncionariosRecursosCollectionTbFuncionariosRecursos);
                }
            }
            for (TbProjetosRecursos tbProjetosRecursosCollectionTbProjetosRecursos : tbRecursos.getTbProjetosRecursosCollection()) {
                TbRecursos oldTbRecursosHandOfTbProjetosRecursosCollectionTbProjetosRecursos = tbProjetosRecursosCollectionTbProjetosRecursos.getTbRecursosHand();
                tbProjetosRecursosCollectionTbProjetosRecursos.setTbRecursosHand(tbRecursos);
                tbProjetosRecursosCollectionTbProjetosRecursos = em.merge(tbProjetosRecursosCollectionTbProjetosRecursos);
                if (oldTbRecursosHandOfTbProjetosRecursosCollectionTbProjetosRecursos != null) {
                    oldTbRecursosHandOfTbProjetosRecursosCollectionTbProjetosRecursos.getTbProjetosRecursosCollection().remove(tbProjetosRecursosCollectionTbProjetosRecursos);
                    oldTbRecursosHandOfTbProjetosRecursosCollectionTbProjetosRecursos = em.merge(oldTbRecursosHandOfTbProjetosRecursosCollectionTbProjetosRecursos);
                }
            }
            for (TbRecursosServicos tbRecursosServicosCollectionTbRecursosServicos : tbRecursos.getTbRecursosServicosCollection()) {
                TbRecursos oldTbRecursosHandOfTbRecursosServicosCollectionTbRecursosServicos = tbRecursosServicosCollectionTbRecursosServicos.getTbRecursosHand();
                tbRecursosServicosCollectionTbRecursosServicos.setTbRecursosHand(tbRecursos);
                tbRecursosServicosCollectionTbRecursosServicos = em.merge(tbRecursosServicosCollectionTbRecursosServicos);
                if (oldTbRecursosHandOfTbRecursosServicosCollectionTbRecursosServicos != null) {
                    oldTbRecursosHandOfTbRecursosServicosCollectionTbRecursosServicos.getTbRecursosServicosCollection().remove(tbRecursosServicosCollectionTbRecursosServicos);
                    oldTbRecursosHandOfTbRecursosServicosCollectionTbRecursosServicos = em.merge(oldTbRecursosHandOfTbRecursosServicosCollectionTbRecursosServicos);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTbRecursos(tbRecursos.getHand()) != null) {
                throw new PreexistingEntityException("TbRecursos " + tbRecursos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TbRecursos tbRecursos) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbRecursos persistentTbRecursos = em.find(TbRecursos.class, tbRecursos.getHand());
            Collection<TbApontamentosRecursos> tbApontamentosRecursosCollectionOld = persistentTbRecursos.getTbApontamentosRecursosCollection();
            Collection<TbApontamentosRecursos> tbApontamentosRecursosCollectionNew = tbRecursos.getTbApontamentosRecursosCollection();
            Collection<TbFuncionariosRecursos> tbFuncionariosRecursosCollectionOld = persistentTbRecursos.getTbFuncionariosRecursosCollection();
            Collection<TbFuncionariosRecursos> tbFuncionariosRecursosCollectionNew = tbRecursos.getTbFuncionariosRecursosCollection();
            Collection<TbProjetosRecursos> tbProjetosRecursosCollectionOld = persistentTbRecursos.getTbProjetosRecursosCollection();
            Collection<TbProjetosRecursos> tbProjetosRecursosCollectionNew = tbRecursos.getTbProjetosRecursosCollection();
            Collection<TbRecursosServicos> tbRecursosServicosCollectionOld = persistentTbRecursos.getTbRecursosServicosCollection();
            Collection<TbRecursosServicos> tbRecursosServicosCollectionNew = tbRecursos.getTbRecursosServicosCollection();
            List<String> illegalOrphanMessages = null;
            for (TbApontamentosRecursos tbApontamentosRecursosCollectionOldTbApontamentosRecursos : tbApontamentosRecursosCollectionOld) {
                if (!tbApontamentosRecursosCollectionNew.contains(tbApontamentosRecursosCollectionOldTbApontamentosRecursos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TbApontamentosRecursos " + tbApontamentosRecursosCollectionOldTbApontamentosRecursos + " since its tbRecursosHand field is not nullable.");
                }
            }
            for (TbFuncionariosRecursos tbFuncionariosRecursosCollectionOldTbFuncionariosRecursos : tbFuncionariosRecursosCollectionOld) {
                if (!tbFuncionariosRecursosCollectionNew.contains(tbFuncionariosRecursosCollectionOldTbFuncionariosRecursos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TbFuncionariosRecursos " + tbFuncionariosRecursosCollectionOldTbFuncionariosRecursos + " since its tbRecursosHand field is not nullable.");
                }
            }
            for (TbProjetosRecursos tbProjetosRecursosCollectionOldTbProjetosRecursos : tbProjetosRecursosCollectionOld) {
                if (!tbProjetosRecursosCollectionNew.contains(tbProjetosRecursosCollectionOldTbProjetosRecursos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TbProjetosRecursos " + tbProjetosRecursosCollectionOldTbProjetosRecursos + " since its tbRecursosHand field is not nullable.");
                }
            }
            for (TbRecursosServicos tbRecursosServicosCollectionOldTbRecursosServicos : tbRecursosServicosCollectionOld) {
                if (!tbRecursosServicosCollectionNew.contains(tbRecursosServicosCollectionOldTbRecursosServicos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TbRecursosServicos " + tbRecursosServicosCollectionOldTbRecursosServicos + " since its tbRecursosHand field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<TbApontamentosRecursos> attachedTbApontamentosRecursosCollectionNew = new ArrayList<TbApontamentosRecursos>();
            for (TbApontamentosRecursos tbApontamentosRecursosCollectionNewTbApontamentosRecursosToAttach : tbApontamentosRecursosCollectionNew) {
                tbApontamentosRecursosCollectionNewTbApontamentosRecursosToAttach = em.getReference(tbApontamentosRecursosCollectionNewTbApontamentosRecursosToAttach.getClass(), tbApontamentosRecursosCollectionNewTbApontamentosRecursosToAttach.getHand());
                attachedTbApontamentosRecursosCollectionNew.add(tbApontamentosRecursosCollectionNewTbApontamentosRecursosToAttach);
            }
            tbApontamentosRecursosCollectionNew = attachedTbApontamentosRecursosCollectionNew;
            tbRecursos.setTbApontamentosRecursosCollection(tbApontamentosRecursosCollectionNew);
            Collection<TbFuncionariosRecursos> attachedTbFuncionariosRecursosCollectionNew = new ArrayList<TbFuncionariosRecursos>();
            for (TbFuncionariosRecursos tbFuncionariosRecursosCollectionNewTbFuncionariosRecursosToAttach : tbFuncionariosRecursosCollectionNew) {
                tbFuncionariosRecursosCollectionNewTbFuncionariosRecursosToAttach = em.getReference(tbFuncionariosRecursosCollectionNewTbFuncionariosRecursosToAttach.getClass(), tbFuncionariosRecursosCollectionNewTbFuncionariosRecursosToAttach.getHand());
                attachedTbFuncionariosRecursosCollectionNew.add(tbFuncionariosRecursosCollectionNewTbFuncionariosRecursosToAttach);
            }
            tbFuncionariosRecursosCollectionNew = attachedTbFuncionariosRecursosCollectionNew;
            tbRecursos.setTbFuncionariosRecursosCollection(tbFuncionariosRecursosCollectionNew);
            Collection<TbProjetosRecursos> attachedTbProjetosRecursosCollectionNew = new ArrayList<TbProjetosRecursos>();
            for (TbProjetosRecursos tbProjetosRecursosCollectionNewTbProjetosRecursosToAttach : tbProjetosRecursosCollectionNew) {
                tbProjetosRecursosCollectionNewTbProjetosRecursosToAttach = em.getReference(tbProjetosRecursosCollectionNewTbProjetosRecursosToAttach.getClass(), tbProjetosRecursosCollectionNewTbProjetosRecursosToAttach.getHand());
                attachedTbProjetosRecursosCollectionNew.add(tbProjetosRecursosCollectionNewTbProjetosRecursosToAttach);
            }
            tbProjetosRecursosCollectionNew = attachedTbProjetosRecursosCollectionNew;
            tbRecursos.setTbProjetosRecursosCollection(tbProjetosRecursosCollectionNew);
            Collection<TbRecursosServicos> attachedTbRecursosServicosCollectionNew = new ArrayList<TbRecursosServicos>();
            for (TbRecursosServicos tbRecursosServicosCollectionNewTbRecursosServicosToAttach : tbRecursosServicosCollectionNew) {
                tbRecursosServicosCollectionNewTbRecursosServicosToAttach = em.getReference(tbRecursosServicosCollectionNewTbRecursosServicosToAttach.getClass(), tbRecursosServicosCollectionNewTbRecursosServicosToAttach.getHand());
                attachedTbRecursosServicosCollectionNew.add(tbRecursosServicosCollectionNewTbRecursosServicosToAttach);
            }
            tbRecursosServicosCollectionNew = attachedTbRecursosServicosCollectionNew;
            tbRecursos.setTbRecursosServicosCollection(tbRecursosServicosCollectionNew);
            tbRecursos = em.merge(tbRecursos);
            for (TbApontamentosRecursos tbApontamentosRecursosCollectionNewTbApontamentosRecursos : tbApontamentosRecursosCollectionNew) {
                if (!tbApontamentosRecursosCollectionOld.contains(tbApontamentosRecursosCollectionNewTbApontamentosRecursos)) {
                    TbRecursos oldTbRecursosHandOfTbApontamentosRecursosCollectionNewTbApontamentosRecursos = tbApontamentosRecursosCollectionNewTbApontamentosRecursos.getTbRecursosHand();
                    tbApontamentosRecursosCollectionNewTbApontamentosRecursos.setTbRecursosHand(tbRecursos);
                    tbApontamentosRecursosCollectionNewTbApontamentosRecursos = em.merge(tbApontamentosRecursosCollectionNewTbApontamentosRecursos);
                    if (oldTbRecursosHandOfTbApontamentosRecursosCollectionNewTbApontamentosRecursos != null && !oldTbRecursosHandOfTbApontamentosRecursosCollectionNewTbApontamentosRecursos.equals(tbRecursos)) {
                        oldTbRecursosHandOfTbApontamentosRecursosCollectionNewTbApontamentosRecursos.getTbApontamentosRecursosCollection().remove(tbApontamentosRecursosCollectionNewTbApontamentosRecursos);
                        oldTbRecursosHandOfTbApontamentosRecursosCollectionNewTbApontamentosRecursos = em.merge(oldTbRecursosHandOfTbApontamentosRecursosCollectionNewTbApontamentosRecursos);
                    }
                }
            }
            for (TbFuncionariosRecursos tbFuncionariosRecursosCollectionNewTbFuncionariosRecursos : tbFuncionariosRecursosCollectionNew) {
                if (!tbFuncionariosRecursosCollectionOld.contains(tbFuncionariosRecursosCollectionNewTbFuncionariosRecursos)) {
                    TbRecursos oldTbRecursosHandOfTbFuncionariosRecursosCollectionNewTbFuncionariosRecursos = tbFuncionariosRecursosCollectionNewTbFuncionariosRecursos.getTbRecursosHand();
                    tbFuncionariosRecursosCollectionNewTbFuncionariosRecursos.setTbRecursosHand(tbRecursos);
                    tbFuncionariosRecursosCollectionNewTbFuncionariosRecursos = em.merge(tbFuncionariosRecursosCollectionNewTbFuncionariosRecursos);
                    if (oldTbRecursosHandOfTbFuncionariosRecursosCollectionNewTbFuncionariosRecursos != null && !oldTbRecursosHandOfTbFuncionariosRecursosCollectionNewTbFuncionariosRecursos.equals(tbRecursos)) {
                        oldTbRecursosHandOfTbFuncionariosRecursosCollectionNewTbFuncionariosRecursos.getTbFuncionariosRecursosCollection().remove(tbFuncionariosRecursosCollectionNewTbFuncionariosRecursos);
                        oldTbRecursosHandOfTbFuncionariosRecursosCollectionNewTbFuncionariosRecursos = em.merge(oldTbRecursosHandOfTbFuncionariosRecursosCollectionNewTbFuncionariosRecursos);
                    }
                }
            }
            for (TbProjetosRecursos tbProjetosRecursosCollectionNewTbProjetosRecursos : tbProjetosRecursosCollectionNew) {
                if (!tbProjetosRecursosCollectionOld.contains(tbProjetosRecursosCollectionNewTbProjetosRecursos)) {
                    TbRecursos oldTbRecursosHandOfTbProjetosRecursosCollectionNewTbProjetosRecursos = tbProjetosRecursosCollectionNewTbProjetosRecursos.getTbRecursosHand();
                    tbProjetosRecursosCollectionNewTbProjetosRecursos.setTbRecursosHand(tbRecursos);
                    tbProjetosRecursosCollectionNewTbProjetosRecursos = em.merge(tbProjetosRecursosCollectionNewTbProjetosRecursos);
                    if (oldTbRecursosHandOfTbProjetosRecursosCollectionNewTbProjetosRecursos != null && !oldTbRecursosHandOfTbProjetosRecursosCollectionNewTbProjetosRecursos.equals(tbRecursos)) {
                        oldTbRecursosHandOfTbProjetosRecursosCollectionNewTbProjetosRecursos.getTbProjetosRecursosCollection().remove(tbProjetosRecursosCollectionNewTbProjetosRecursos);
                        oldTbRecursosHandOfTbProjetosRecursosCollectionNewTbProjetosRecursos = em.merge(oldTbRecursosHandOfTbProjetosRecursosCollectionNewTbProjetosRecursos);
                    }
                }
            }
            for (TbRecursosServicos tbRecursosServicosCollectionNewTbRecursosServicos : tbRecursosServicosCollectionNew) {
                if (!tbRecursosServicosCollectionOld.contains(tbRecursosServicosCollectionNewTbRecursosServicos)) {
                    TbRecursos oldTbRecursosHandOfTbRecursosServicosCollectionNewTbRecursosServicos = tbRecursosServicosCollectionNewTbRecursosServicos.getTbRecursosHand();
                    tbRecursosServicosCollectionNewTbRecursosServicos.setTbRecursosHand(tbRecursos);
                    tbRecursosServicosCollectionNewTbRecursosServicos = em.merge(tbRecursosServicosCollectionNewTbRecursosServicos);
                    if (oldTbRecursosHandOfTbRecursosServicosCollectionNewTbRecursosServicos != null && !oldTbRecursosHandOfTbRecursosServicosCollectionNewTbRecursosServicos.equals(tbRecursos)) {
                        oldTbRecursosHandOfTbRecursosServicosCollectionNewTbRecursosServicos.getTbRecursosServicosCollection().remove(tbRecursosServicosCollectionNewTbRecursosServicos);
                        oldTbRecursosHandOfTbRecursosServicosCollectionNewTbRecursosServicos = em.merge(oldTbRecursosHandOfTbRecursosServicosCollectionNewTbRecursosServicos);
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
                Integer id = tbRecursos.getHand();
                if (findTbRecursos(id) == null) {
                    throw new NonexistentEntityException("The tbRecursos with id " + id + " no longer exists.");
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
            TbRecursos tbRecursos;
            try {
                tbRecursos = em.getReference(TbRecursos.class, id);
                tbRecursos.getHand();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tbRecursos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TbApontamentosRecursos> tbApontamentosRecursosCollectionOrphanCheck = tbRecursos.getTbApontamentosRecursosCollection();
            for (TbApontamentosRecursos tbApontamentosRecursosCollectionOrphanCheckTbApontamentosRecursos : tbApontamentosRecursosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbRecursos (" + tbRecursos + ") cannot be destroyed since the TbApontamentosRecursos " + tbApontamentosRecursosCollectionOrphanCheckTbApontamentosRecursos + " in its tbApontamentosRecursosCollection field has a non-nullable tbRecursosHand field.");
            }
            Collection<TbFuncionariosRecursos> tbFuncionariosRecursosCollectionOrphanCheck = tbRecursos.getTbFuncionariosRecursosCollection();
            for (TbFuncionariosRecursos tbFuncionariosRecursosCollectionOrphanCheckTbFuncionariosRecursos : tbFuncionariosRecursosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbRecursos (" + tbRecursos + ") cannot be destroyed since the TbFuncionariosRecursos " + tbFuncionariosRecursosCollectionOrphanCheckTbFuncionariosRecursos + " in its tbFuncionariosRecursosCollection field has a non-nullable tbRecursosHand field.");
            }
            Collection<TbProjetosRecursos> tbProjetosRecursosCollectionOrphanCheck = tbRecursos.getTbProjetosRecursosCollection();
            for (TbProjetosRecursos tbProjetosRecursosCollectionOrphanCheckTbProjetosRecursos : tbProjetosRecursosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbRecursos (" + tbRecursos + ") cannot be destroyed since the TbProjetosRecursos " + tbProjetosRecursosCollectionOrphanCheckTbProjetosRecursos + " in its tbProjetosRecursosCollection field has a non-nullable tbRecursosHand field.");
            }
            Collection<TbRecursosServicos> tbRecursosServicosCollectionOrphanCheck = tbRecursos.getTbRecursosServicosCollection();
            for (TbRecursosServicos tbRecursosServicosCollectionOrphanCheckTbRecursosServicos : tbRecursosServicosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbRecursos (" + tbRecursos + ") cannot be destroyed since the TbRecursosServicos " + tbRecursosServicosCollectionOrphanCheckTbRecursosServicos + " in its tbRecursosServicosCollection field has a non-nullable tbRecursosHand field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tbRecursos);
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

    public List<TbRecursos> findTbRecursosEntities() {
        return findTbRecursosEntities(true, -1, -1);
    }

    public List<TbRecursos> findTbRecursosEntities(int maxResults, int firstResult) {
        return findTbRecursosEntities(false, maxResults, firstResult);
    }

    private List<TbRecursos> findTbRecursosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TbRecursos.class));
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

    public TbRecursos findTbRecursos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbRecursos.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbRecursosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TbRecursos> rt = cq.from(TbRecursos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
