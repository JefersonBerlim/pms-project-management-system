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
import Model.TbProjetoMarcos;
import Model.TbServicos;
import Model.TbProjetosMateriais;
import java.util.ArrayList;
import java.util.Collection;
import Model.TbOsServico;
import Model.TbProjetosRecursos;
import Model.TbProjetosServicos;
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
public class TbProjetosServicosJpaController implements Serializable {

    public TbProjetosServicosJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TbProjetosServicos tbProjetosServicos) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (tbProjetosServicos.getTbProjetosMateriaisCollection() == null) {
            tbProjetosServicos.setTbProjetosMateriaisCollection(new ArrayList<TbProjetosMateriais>());
        }
        if (tbProjetosServicos.getTbOsServicoCollection() == null) {
            tbProjetosServicos.setTbOsServicoCollection(new ArrayList<TbOsServico>());
        }
        if (tbProjetosServicos.getTbProjetosRecursosCollection() == null) {
            tbProjetosServicos.setTbProjetosRecursosCollection(new ArrayList<TbProjetosRecursos>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbProjetoMarcos tbProjetoMarcosHand = tbProjetosServicos.getTbProjetoMarcosHand();
            if (tbProjetoMarcosHand != null) {
                tbProjetoMarcosHand = em.getReference(tbProjetoMarcosHand.getClass(), tbProjetoMarcosHand.getHand());
                tbProjetosServicos.setTbProjetoMarcosHand(tbProjetoMarcosHand);
            }
            TbServicos tbServicosHand = tbProjetosServicos.getTbServicosHand();
            if (tbServicosHand != null) {
                tbServicosHand = em.getReference(tbServicosHand.getClass(), tbServicosHand.getHand());
                tbProjetosServicos.setTbServicosHand(tbServicosHand);
            }
            TbServicos servicoDependente = tbProjetosServicos.getServicoDependente();
            if (servicoDependente != null) {
                servicoDependente = em.getReference(servicoDependente.getClass(), servicoDependente.getHand());
                tbProjetosServicos.setServicoDependente(servicoDependente);
            }
            Collection<TbProjetosMateriais> attachedTbProjetosMateriaisCollection = new ArrayList<TbProjetosMateriais>();
            for (TbProjetosMateriais tbProjetosMateriaisCollectionTbProjetosMateriaisToAttach : tbProjetosServicos.getTbProjetosMateriaisCollection()) {
                tbProjetosMateriaisCollectionTbProjetosMateriaisToAttach = em.getReference(tbProjetosMateriaisCollectionTbProjetosMateriaisToAttach.getClass(), tbProjetosMateriaisCollectionTbProjetosMateriaisToAttach.getHand());
                attachedTbProjetosMateriaisCollection.add(tbProjetosMateriaisCollectionTbProjetosMateriaisToAttach);
            }
            tbProjetosServicos.setTbProjetosMateriaisCollection(attachedTbProjetosMateriaisCollection);
            Collection<TbOsServico> attachedTbOsServicoCollection = new ArrayList<TbOsServico>();
            for (TbOsServico tbOsServicoCollectionTbOsServicoToAttach : tbProjetosServicos.getTbOsServicoCollection()) {
                tbOsServicoCollectionTbOsServicoToAttach = em.getReference(tbOsServicoCollectionTbOsServicoToAttach.getClass(), tbOsServicoCollectionTbOsServicoToAttach.getHand());
                attachedTbOsServicoCollection.add(tbOsServicoCollectionTbOsServicoToAttach);
            }
            tbProjetosServicos.setTbOsServicoCollection(attachedTbOsServicoCollection);
            Collection<TbProjetosRecursos> attachedTbProjetosRecursosCollection = new ArrayList<TbProjetosRecursos>();
            for (TbProjetosRecursos tbProjetosRecursosCollectionTbProjetosRecursosToAttach : tbProjetosServicos.getTbProjetosRecursosCollection()) {
                tbProjetosRecursosCollectionTbProjetosRecursosToAttach = em.getReference(tbProjetosRecursosCollectionTbProjetosRecursosToAttach.getClass(), tbProjetosRecursosCollectionTbProjetosRecursosToAttach.getHand());
                attachedTbProjetosRecursosCollection.add(tbProjetosRecursosCollectionTbProjetosRecursosToAttach);
            }
            tbProjetosServicos.setTbProjetosRecursosCollection(attachedTbProjetosRecursosCollection);
            em.persist(tbProjetosServicos);
            if (tbProjetoMarcosHand != null) {
                tbProjetoMarcosHand.getTbProjetosServicosCollection().add(tbProjetosServicos);
                tbProjetoMarcosHand = em.merge(tbProjetoMarcosHand);
            }
            if (tbServicosHand != null) {
                tbServicosHand.getTbProjetosServicosCollection().add(tbProjetosServicos);
                tbServicosHand = em.merge(tbServicosHand);
            }
            if (servicoDependente != null) {
                servicoDependente.getTbProjetosServicosCollection().add(tbProjetosServicos);
                servicoDependente = em.merge(servicoDependente);
            }
            for (TbProjetosMateriais tbProjetosMateriaisCollectionTbProjetosMateriais : tbProjetosServicos.getTbProjetosMateriaisCollection()) {
                TbProjetosServicos oldTbProjetosServicosHandOfTbProjetosMateriaisCollectionTbProjetosMateriais = tbProjetosMateriaisCollectionTbProjetosMateriais.getTbProjetosServicosHand();
                tbProjetosMateriaisCollectionTbProjetosMateriais.setTbProjetosServicosHand(tbProjetosServicos);
                tbProjetosMateriaisCollectionTbProjetosMateriais = em.merge(tbProjetosMateriaisCollectionTbProjetosMateriais);
                if (oldTbProjetosServicosHandOfTbProjetosMateriaisCollectionTbProjetosMateriais != null) {
                    oldTbProjetosServicosHandOfTbProjetosMateriaisCollectionTbProjetosMateriais.getTbProjetosMateriaisCollection().remove(tbProjetosMateriaisCollectionTbProjetosMateriais);
                    oldTbProjetosServicosHandOfTbProjetosMateriaisCollectionTbProjetosMateriais = em.merge(oldTbProjetosServicosHandOfTbProjetosMateriaisCollectionTbProjetosMateriais);
                }
            }
            for (TbOsServico tbOsServicoCollectionTbOsServico : tbProjetosServicos.getTbOsServicoCollection()) {
                TbProjetosServicos oldTbProjetosServicosHandOfTbOsServicoCollectionTbOsServico = tbOsServicoCollectionTbOsServico.getTbProjetosServicosHand();
                tbOsServicoCollectionTbOsServico.setTbProjetosServicosHand(tbProjetosServicos);
                tbOsServicoCollectionTbOsServico = em.merge(tbOsServicoCollectionTbOsServico);
                if (oldTbProjetosServicosHandOfTbOsServicoCollectionTbOsServico != null) {
                    oldTbProjetosServicosHandOfTbOsServicoCollectionTbOsServico.getTbOsServicoCollection().remove(tbOsServicoCollectionTbOsServico);
                    oldTbProjetosServicosHandOfTbOsServicoCollectionTbOsServico = em.merge(oldTbProjetosServicosHandOfTbOsServicoCollectionTbOsServico);
                }
            }
            for (TbProjetosRecursos tbProjetosRecursosCollectionTbProjetosRecursos : tbProjetosServicos.getTbProjetosRecursosCollection()) {
                TbProjetosServicos oldTbProjetosServicosHandOfTbProjetosRecursosCollectionTbProjetosRecursos = tbProjetosRecursosCollectionTbProjetosRecursos.getTbProjetosServicosHand();
                tbProjetosRecursosCollectionTbProjetosRecursos.setTbProjetosServicosHand(tbProjetosServicos);
                tbProjetosRecursosCollectionTbProjetosRecursos = em.merge(tbProjetosRecursosCollectionTbProjetosRecursos);
                if (oldTbProjetosServicosHandOfTbProjetosRecursosCollectionTbProjetosRecursos != null) {
                    oldTbProjetosServicosHandOfTbProjetosRecursosCollectionTbProjetosRecursos.getTbProjetosRecursosCollection().remove(tbProjetosRecursosCollectionTbProjetosRecursos);
                    oldTbProjetosServicosHandOfTbProjetosRecursosCollectionTbProjetosRecursos = em.merge(oldTbProjetosServicosHandOfTbProjetosRecursosCollectionTbProjetosRecursos);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTbProjetosServicos(tbProjetosServicos.getHand()) != null) {
                throw new PreexistingEntityException("TbProjetosServicos " + tbProjetosServicos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TbProjetosServicos tbProjetosServicos) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbProjetosServicos persistentTbProjetosServicos = em.find(TbProjetosServicos.class, tbProjetosServicos.getHand());
            TbProjetoMarcos tbProjetoMarcosHandOld = persistentTbProjetosServicos.getTbProjetoMarcosHand();
            TbProjetoMarcos tbProjetoMarcosHandNew = tbProjetosServicos.getTbProjetoMarcosHand();
            TbServicos tbServicosHandOld = persistentTbProjetosServicos.getTbServicosHand();
            TbServicos tbServicosHandNew = tbProjetosServicos.getTbServicosHand();
            TbServicos servicoDependenteOld = persistentTbProjetosServicos.getServicoDependente();
            TbServicos servicoDependenteNew = tbProjetosServicos.getServicoDependente();
            Collection<TbProjetosMateriais> tbProjetosMateriaisCollectionOld = persistentTbProjetosServicos.getTbProjetosMateriaisCollection();
            Collection<TbProjetosMateriais> tbProjetosMateriaisCollectionNew = tbProjetosServicos.getTbProjetosMateriaisCollection();
            Collection<TbOsServico> tbOsServicoCollectionOld = persistentTbProjetosServicos.getTbOsServicoCollection();
            Collection<TbOsServico> tbOsServicoCollectionNew = tbProjetosServicos.getTbOsServicoCollection();
            Collection<TbProjetosRecursos> tbProjetosRecursosCollectionOld = persistentTbProjetosServicos.getTbProjetosRecursosCollection();
            Collection<TbProjetosRecursos> tbProjetosRecursosCollectionNew = tbProjetosServicos.getTbProjetosRecursosCollection();
            List<String> illegalOrphanMessages = null;
            for (TbProjetosMateriais tbProjetosMateriaisCollectionOldTbProjetosMateriais : tbProjetosMateriaisCollectionOld) {
                if (!tbProjetosMateriaisCollectionNew.contains(tbProjetosMateriaisCollectionOldTbProjetosMateriais)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TbProjetosMateriais " + tbProjetosMateriaisCollectionOldTbProjetosMateriais + " since its tbProjetosServicosHand field is not nullable.");
                }
            }
            for (TbOsServico tbOsServicoCollectionOldTbOsServico : tbOsServicoCollectionOld) {
                if (!tbOsServicoCollectionNew.contains(tbOsServicoCollectionOldTbOsServico)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TbOsServico " + tbOsServicoCollectionOldTbOsServico + " since its tbProjetosServicosHand field is not nullable.");
                }
            }
            for (TbProjetosRecursos tbProjetosRecursosCollectionOldTbProjetosRecursos : tbProjetosRecursosCollectionOld) {
                if (!tbProjetosRecursosCollectionNew.contains(tbProjetosRecursosCollectionOldTbProjetosRecursos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TbProjetosRecursos " + tbProjetosRecursosCollectionOldTbProjetosRecursos + " since its tbProjetosServicosHand field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tbProjetoMarcosHandNew != null) {
                tbProjetoMarcosHandNew = em.getReference(tbProjetoMarcosHandNew.getClass(), tbProjetoMarcosHandNew.getHand());
                tbProjetosServicos.setTbProjetoMarcosHand(tbProjetoMarcosHandNew);
            }
            if (tbServicosHandNew != null) {
                tbServicosHandNew = em.getReference(tbServicosHandNew.getClass(), tbServicosHandNew.getHand());
                tbProjetosServicos.setTbServicosHand(tbServicosHandNew);
            }
            if (servicoDependenteNew != null) {
                servicoDependenteNew = em.getReference(servicoDependenteNew.getClass(), servicoDependenteNew.getHand());
                tbProjetosServicos.setServicoDependente(servicoDependenteNew);
            }
            Collection<TbProjetosMateriais> attachedTbProjetosMateriaisCollectionNew = new ArrayList<TbProjetosMateriais>();
            for (TbProjetosMateriais tbProjetosMateriaisCollectionNewTbProjetosMateriaisToAttach : tbProjetosMateriaisCollectionNew) {
                tbProjetosMateriaisCollectionNewTbProjetosMateriaisToAttach = em.getReference(tbProjetosMateriaisCollectionNewTbProjetosMateriaisToAttach.getClass(), tbProjetosMateriaisCollectionNewTbProjetosMateriaisToAttach.getHand());
                attachedTbProjetosMateriaisCollectionNew.add(tbProjetosMateriaisCollectionNewTbProjetosMateriaisToAttach);
            }
            tbProjetosMateriaisCollectionNew = attachedTbProjetosMateriaisCollectionNew;
            tbProjetosServicos.setTbProjetosMateriaisCollection(tbProjetosMateriaisCollectionNew);
            Collection<TbOsServico> attachedTbOsServicoCollectionNew = new ArrayList<TbOsServico>();
            for (TbOsServico tbOsServicoCollectionNewTbOsServicoToAttach : tbOsServicoCollectionNew) {
                tbOsServicoCollectionNewTbOsServicoToAttach = em.getReference(tbOsServicoCollectionNewTbOsServicoToAttach.getClass(), tbOsServicoCollectionNewTbOsServicoToAttach.getHand());
                attachedTbOsServicoCollectionNew.add(tbOsServicoCollectionNewTbOsServicoToAttach);
            }
            tbOsServicoCollectionNew = attachedTbOsServicoCollectionNew;
            tbProjetosServicos.setTbOsServicoCollection(tbOsServicoCollectionNew);
            Collection<TbProjetosRecursos> attachedTbProjetosRecursosCollectionNew = new ArrayList<TbProjetosRecursos>();
            for (TbProjetosRecursos tbProjetosRecursosCollectionNewTbProjetosRecursosToAttach : tbProjetosRecursosCollectionNew) {
                tbProjetosRecursosCollectionNewTbProjetosRecursosToAttach = em.getReference(tbProjetosRecursosCollectionNewTbProjetosRecursosToAttach.getClass(), tbProjetosRecursosCollectionNewTbProjetosRecursosToAttach.getHand());
                attachedTbProjetosRecursosCollectionNew.add(tbProjetosRecursosCollectionNewTbProjetosRecursosToAttach);
            }
            tbProjetosRecursosCollectionNew = attachedTbProjetosRecursosCollectionNew;
            tbProjetosServicos.setTbProjetosRecursosCollection(tbProjetosRecursosCollectionNew);
            tbProjetosServicos = em.merge(tbProjetosServicos);
            if (tbProjetoMarcosHandOld != null && !tbProjetoMarcosHandOld.equals(tbProjetoMarcosHandNew)) {
                tbProjetoMarcosHandOld.getTbProjetosServicosCollection().remove(tbProjetosServicos);
                tbProjetoMarcosHandOld = em.merge(tbProjetoMarcosHandOld);
            }
            if (tbProjetoMarcosHandNew != null && !tbProjetoMarcosHandNew.equals(tbProjetoMarcosHandOld)) {
                tbProjetoMarcosHandNew.getTbProjetosServicosCollection().add(tbProjetosServicos);
                tbProjetoMarcosHandNew = em.merge(tbProjetoMarcosHandNew);
            }
            if (tbServicosHandOld != null && !tbServicosHandOld.equals(tbServicosHandNew)) {
                tbServicosHandOld.getTbProjetosServicosCollection().remove(tbProjetosServicos);
                tbServicosHandOld = em.merge(tbServicosHandOld);
            }
            if (tbServicosHandNew != null && !tbServicosHandNew.equals(tbServicosHandOld)) {
                tbServicosHandNew.getTbProjetosServicosCollection().add(tbProjetosServicos);
                tbServicosHandNew = em.merge(tbServicosHandNew);
            }
            if (servicoDependenteOld != null && !servicoDependenteOld.equals(servicoDependenteNew)) {
                servicoDependenteOld.getTbProjetosServicosCollection().remove(tbProjetosServicos);
                servicoDependenteOld = em.merge(servicoDependenteOld);
            }
            if (servicoDependenteNew != null && !servicoDependenteNew.equals(servicoDependenteOld)) {
                servicoDependenteNew.getTbProjetosServicosCollection().add(tbProjetosServicos);
                servicoDependenteNew = em.merge(servicoDependenteNew);
            }
            for (TbProjetosMateriais tbProjetosMateriaisCollectionNewTbProjetosMateriais : tbProjetosMateriaisCollectionNew) {
                if (!tbProjetosMateriaisCollectionOld.contains(tbProjetosMateriaisCollectionNewTbProjetosMateriais)) {
                    TbProjetosServicos oldTbProjetosServicosHandOfTbProjetosMateriaisCollectionNewTbProjetosMateriais = tbProjetosMateriaisCollectionNewTbProjetosMateriais.getTbProjetosServicosHand();
                    tbProjetosMateriaisCollectionNewTbProjetosMateriais.setTbProjetosServicosHand(tbProjetosServicos);
                    tbProjetosMateriaisCollectionNewTbProjetosMateriais = em.merge(tbProjetosMateriaisCollectionNewTbProjetosMateriais);
                    if (oldTbProjetosServicosHandOfTbProjetosMateriaisCollectionNewTbProjetosMateriais != null && !oldTbProjetosServicosHandOfTbProjetosMateriaisCollectionNewTbProjetosMateriais.equals(tbProjetosServicos)) {
                        oldTbProjetosServicosHandOfTbProjetosMateriaisCollectionNewTbProjetosMateriais.getTbProjetosMateriaisCollection().remove(tbProjetosMateriaisCollectionNewTbProjetosMateriais);
                        oldTbProjetosServicosHandOfTbProjetosMateriaisCollectionNewTbProjetosMateriais = em.merge(oldTbProjetosServicosHandOfTbProjetosMateriaisCollectionNewTbProjetosMateriais);
                    }
                }
            }
            for (TbOsServico tbOsServicoCollectionNewTbOsServico : tbOsServicoCollectionNew) {
                if (!tbOsServicoCollectionOld.contains(tbOsServicoCollectionNewTbOsServico)) {
                    TbProjetosServicos oldTbProjetosServicosHandOfTbOsServicoCollectionNewTbOsServico = tbOsServicoCollectionNewTbOsServico.getTbProjetosServicosHand();
                    tbOsServicoCollectionNewTbOsServico.setTbProjetosServicosHand(tbProjetosServicos);
                    tbOsServicoCollectionNewTbOsServico = em.merge(tbOsServicoCollectionNewTbOsServico);
                    if (oldTbProjetosServicosHandOfTbOsServicoCollectionNewTbOsServico != null && !oldTbProjetosServicosHandOfTbOsServicoCollectionNewTbOsServico.equals(tbProjetosServicos)) {
                        oldTbProjetosServicosHandOfTbOsServicoCollectionNewTbOsServico.getTbOsServicoCollection().remove(tbOsServicoCollectionNewTbOsServico);
                        oldTbProjetosServicosHandOfTbOsServicoCollectionNewTbOsServico = em.merge(oldTbProjetosServicosHandOfTbOsServicoCollectionNewTbOsServico);
                    }
                }
            }
            for (TbProjetosRecursos tbProjetosRecursosCollectionNewTbProjetosRecursos : tbProjetosRecursosCollectionNew) {
                if (!tbProjetosRecursosCollectionOld.contains(tbProjetosRecursosCollectionNewTbProjetosRecursos)) {
                    TbProjetosServicos oldTbProjetosServicosHandOfTbProjetosRecursosCollectionNewTbProjetosRecursos = tbProjetosRecursosCollectionNewTbProjetosRecursos.getTbProjetosServicosHand();
                    tbProjetosRecursosCollectionNewTbProjetosRecursos.setTbProjetosServicosHand(tbProjetosServicos);
                    tbProjetosRecursosCollectionNewTbProjetosRecursos = em.merge(tbProjetosRecursosCollectionNewTbProjetosRecursos);
                    if (oldTbProjetosServicosHandOfTbProjetosRecursosCollectionNewTbProjetosRecursos != null && !oldTbProjetosServicosHandOfTbProjetosRecursosCollectionNewTbProjetosRecursos.equals(tbProjetosServicos)) {
                        oldTbProjetosServicosHandOfTbProjetosRecursosCollectionNewTbProjetosRecursos.getTbProjetosRecursosCollection().remove(tbProjetosRecursosCollectionNewTbProjetosRecursos);
                        oldTbProjetosServicosHandOfTbProjetosRecursosCollectionNewTbProjetosRecursos = em.merge(oldTbProjetosServicosHandOfTbProjetosRecursosCollectionNewTbProjetosRecursos);
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
                Integer id = tbProjetosServicos.getHand();
                if (findTbProjetosServicos(id) == null) {
                    throw new NonexistentEntityException("The tbProjetosServicos with id " + id + " no longer exists.");
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
            TbProjetosServicos tbProjetosServicos;
            try {
                tbProjetosServicos = em.getReference(TbProjetosServicos.class, id);
                tbProjetosServicos.getHand();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tbProjetosServicos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TbProjetosMateriais> tbProjetosMateriaisCollectionOrphanCheck = tbProjetosServicos.getTbProjetosMateriaisCollection();
            for (TbProjetosMateriais tbProjetosMateriaisCollectionOrphanCheckTbProjetosMateriais : tbProjetosMateriaisCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbProjetosServicos (" + tbProjetosServicos + ") cannot be destroyed since the TbProjetosMateriais " + tbProjetosMateriaisCollectionOrphanCheckTbProjetosMateriais + " in its tbProjetosMateriaisCollection field has a non-nullable tbProjetosServicosHand field.");
            }
            Collection<TbOsServico> tbOsServicoCollectionOrphanCheck = tbProjetosServicos.getTbOsServicoCollection();
            for (TbOsServico tbOsServicoCollectionOrphanCheckTbOsServico : tbOsServicoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbProjetosServicos (" + tbProjetosServicos + ") cannot be destroyed since the TbOsServico " + tbOsServicoCollectionOrphanCheckTbOsServico + " in its tbOsServicoCollection field has a non-nullable tbProjetosServicosHand field.");
            }
            Collection<TbProjetosRecursos> tbProjetosRecursosCollectionOrphanCheck = tbProjetosServicos.getTbProjetosRecursosCollection();
            for (TbProjetosRecursos tbProjetosRecursosCollectionOrphanCheckTbProjetosRecursos : tbProjetosRecursosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbProjetosServicos (" + tbProjetosServicos + ") cannot be destroyed since the TbProjetosRecursos " + tbProjetosRecursosCollectionOrphanCheckTbProjetosRecursos + " in its tbProjetosRecursosCollection field has a non-nullable tbProjetosServicosHand field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            TbProjetoMarcos tbProjetoMarcosHand = tbProjetosServicos.getTbProjetoMarcosHand();
            if (tbProjetoMarcosHand != null) {
                tbProjetoMarcosHand.getTbProjetosServicosCollection().remove(tbProjetosServicos);
                tbProjetoMarcosHand = em.merge(tbProjetoMarcosHand);
            }
            TbServicos tbServicosHand = tbProjetosServicos.getTbServicosHand();
            if (tbServicosHand != null) {
                tbServicosHand.getTbProjetosServicosCollection().remove(tbProjetosServicos);
                tbServicosHand = em.merge(tbServicosHand);
            }
            TbServicos servicoDependente = tbProjetosServicos.getServicoDependente();
            if (servicoDependente != null) {
                servicoDependente.getTbProjetosServicosCollection().remove(tbProjetosServicos);
                servicoDependente = em.merge(servicoDependente);
            }
            em.remove(tbProjetosServicos);
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

    public List<TbProjetosServicos> findTbProjetosServicosEntities() {
        return findTbProjetosServicosEntities(true, -1, -1);
    }

    public List<TbProjetosServicos> findTbProjetosServicosEntities(int maxResults, int firstResult) {
        return findTbProjetosServicosEntities(false, maxResults, firstResult);
    }

    private List<TbProjetosServicos> findTbProjetosServicosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TbProjetosServicos.class));
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

    public TbProjetosServicos findTbProjetosServicos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbProjetosServicos.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbProjetosServicosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TbProjetosServicos> rt = cq.from(TbProjetosServicos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
