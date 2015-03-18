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
import Model.TbOrdemServico;
import Model.TbProjetosServicos;
import Model.TbStatus;
import Model.TbApontamentosRecursos;
import java.util.ArrayList;
import java.util.Collection;
import Model.TbOsRecursosTotal;
import Model.TbApontamentosMateriais;
import Model.TbOsMateriaisTotal;
import Model.TbOsServico;
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
public class TbOsServicoJpaController implements Serializable {

    public TbOsServicoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TbOsServico tbOsServico) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (tbOsServico.getTbApontamentosRecursosCollection() == null) {
            tbOsServico.setTbApontamentosRecursosCollection(new ArrayList<TbApontamentosRecursos>());
        }
        if (tbOsServico.getTbOsRecursosTotalCollection() == null) {
            tbOsServico.setTbOsRecursosTotalCollection(new ArrayList<TbOsRecursosTotal>());
        }
        if (tbOsServico.getTbApontamentosMateriaisCollection() == null) {
            tbOsServico.setTbApontamentosMateriaisCollection(new ArrayList<TbApontamentosMateriais>());
        }
        if (tbOsServico.getTbOsMateriaisTotalCollection() == null) {
            tbOsServico.setTbOsMateriaisTotalCollection(new ArrayList<TbOsMateriaisTotal>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbOrdemServico tbOrdemServicoHand = tbOsServico.getTbOrdemServicoHand();
            if (tbOrdemServicoHand != null) {
                tbOrdemServicoHand = em.getReference(tbOrdemServicoHand.getClass(), tbOrdemServicoHand.getHand());
                tbOsServico.setTbOrdemServicoHand(tbOrdemServicoHand);
            }
            TbProjetosServicos tbProjetosServicosHand = tbOsServico.getTbProjetosServicosHand();
            if (tbProjetosServicosHand != null) {
                tbProjetosServicosHand = em.getReference(tbProjetosServicosHand.getClass(), tbProjetosServicosHand.getHand());
                tbOsServico.setTbProjetosServicosHand(tbProjetosServicosHand);
            }
            TbStatus tbStatusHand = tbOsServico.getTbStatusHand();
            if (tbStatusHand != null) {
                tbStatusHand = em.getReference(tbStatusHand.getClass(), tbStatusHand.getHand());
                tbOsServico.setTbStatusHand(tbStatusHand);
            }
            Collection<TbApontamentosRecursos> attachedTbApontamentosRecursosCollection = new ArrayList<TbApontamentosRecursos>();
            for (TbApontamentosRecursos tbApontamentosRecursosCollectionTbApontamentosRecursosToAttach : tbOsServico.getTbApontamentosRecursosCollection()) {
                tbApontamentosRecursosCollectionTbApontamentosRecursosToAttach = em.getReference(tbApontamentosRecursosCollectionTbApontamentosRecursosToAttach.getClass(), tbApontamentosRecursosCollectionTbApontamentosRecursosToAttach.getHand());
                attachedTbApontamentosRecursosCollection.add(tbApontamentosRecursosCollectionTbApontamentosRecursosToAttach);
            }
            tbOsServico.setTbApontamentosRecursosCollection(attachedTbApontamentosRecursosCollection);
            Collection<TbOsRecursosTotal> attachedTbOsRecursosTotalCollection = new ArrayList<TbOsRecursosTotal>();
            for (TbOsRecursosTotal tbOsRecursosTotalCollectionTbOsRecursosTotalToAttach : tbOsServico.getTbOsRecursosTotalCollection()) {
                tbOsRecursosTotalCollectionTbOsRecursosTotalToAttach = em.getReference(tbOsRecursosTotalCollectionTbOsRecursosTotalToAttach.getClass(), tbOsRecursosTotalCollectionTbOsRecursosTotalToAttach.getHand());
                attachedTbOsRecursosTotalCollection.add(tbOsRecursosTotalCollectionTbOsRecursosTotalToAttach);
            }
            tbOsServico.setTbOsRecursosTotalCollection(attachedTbOsRecursosTotalCollection);
            Collection<TbApontamentosMateriais> attachedTbApontamentosMateriaisCollection = new ArrayList<TbApontamentosMateriais>();
            for (TbApontamentosMateriais tbApontamentosMateriaisCollectionTbApontamentosMateriaisToAttach : tbOsServico.getTbApontamentosMateriaisCollection()) {
                tbApontamentosMateriaisCollectionTbApontamentosMateriaisToAttach = em.getReference(tbApontamentosMateriaisCollectionTbApontamentosMateriaisToAttach.getClass(), tbApontamentosMateriaisCollectionTbApontamentosMateriaisToAttach.getHand());
                attachedTbApontamentosMateriaisCollection.add(tbApontamentosMateriaisCollectionTbApontamentosMateriaisToAttach);
            }
            tbOsServico.setTbApontamentosMateriaisCollection(attachedTbApontamentosMateriaisCollection);
            Collection<TbOsMateriaisTotal> attachedTbOsMateriaisTotalCollection = new ArrayList<TbOsMateriaisTotal>();
            for (TbOsMateriaisTotal tbOsMateriaisTotalCollectionTbOsMateriaisTotalToAttach : tbOsServico.getTbOsMateriaisTotalCollection()) {
                tbOsMateriaisTotalCollectionTbOsMateriaisTotalToAttach = em.getReference(tbOsMateriaisTotalCollectionTbOsMateriaisTotalToAttach.getClass(), tbOsMateriaisTotalCollectionTbOsMateriaisTotalToAttach.getHand());
                attachedTbOsMateriaisTotalCollection.add(tbOsMateriaisTotalCollectionTbOsMateriaisTotalToAttach);
            }
            tbOsServico.setTbOsMateriaisTotalCollection(attachedTbOsMateriaisTotalCollection);
            em.persist(tbOsServico);
            if (tbOrdemServicoHand != null) {
                tbOrdemServicoHand.getTbOsServicoCollection().add(tbOsServico);
                tbOrdemServicoHand = em.merge(tbOrdemServicoHand);
            }
            if (tbProjetosServicosHand != null) {
                tbProjetosServicosHand.getTbOsServicoCollection().add(tbOsServico);
                tbProjetosServicosHand = em.merge(tbProjetosServicosHand);
            }
            if (tbStatusHand != null) {
                tbStatusHand.getTbOsServicoCollection().add(tbOsServico);
                tbStatusHand = em.merge(tbStatusHand);
            }
            for (TbApontamentosRecursos tbApontamentosRecursosCollectionTbApontamentosRecursos : tbOsServico.getTbApontamentosRecursosCollection()) {
                TbOsServico oldTbOsServicoHandOfTbApontamentosRecursosCollectionTbApontamentosRecursos = tbApontamentosRecursosCollectionTbApontamentosRecursos.getTbOsServicoHand();
                tbApontamentosRecursosCollectionTbApontamentosRecursos.setTbOsServicoHand(tbOsServico);
                tbApontamentosRecursosCollectionTbApontamentosRecursos = em.merge(tbApontamentosRecursosCollectionTbApontamentosRecursos);
                if (oldTbOsServicoHandOfTbApontamentosRecursosCollectionTbApontamentosRecursos != null) {
                    oldTbOsServicoHandOfTbApontamentosRecursosCollectionTbApontamentosRecursos.getTbApontamentosRecursosCollection().remove(tbApontamentosRecursosCollectionTbApontamentosRecursos);
                    oldTbOsServicoHandOfTbApontamentosRecursosCollectionTbApontamentosRecursos = em.merge(oldTbOsServicoHandOfTbApontamentosRecursosCollectionTbApontamentosRecursos);
                }
            }
            for (TbOsRecursosTotal tbOsRecursosTotalCollectionTbOsRecursosTotal : tbOsServico.getTbOsRecursosTotalCollection()) {
                TbOsServico oldTbOsServicoHandOfTbOsRecursosTotalCollectionTbOsRecursosTotal = tbOsRecursosTotalCollectionTbOsRecursosTotal.getTbOsServicoHand();
                tbOsRecursosTotalCollectionTbOsRecursosTotal.setTbOsServicoHand(tbOsServico);
                tbOsRecursosTotalCollectionTbOsRecursosTotal = em.merge(tbOsRecursosTotalCollectionTbOsRecursosTotal);
                if (oldTbOsServicoHandOfTbOsRecursosTotalCollectionTbOsRecursosTotal != null) {
                    oldTbOsServicoHandOfTbOsRecursosTotalCollectionTbOsRecursosTotal.getTbOsRecursosTotalCollection().remove(tbOsRecursosTotalCollectionTbOsRecursosTotal);
                    oldTbOsServicoHandOfTbOsRecursosTotalCollectionTbOsRecursosTotal = em.merge(oldTbOsServicoHandOfTbOsRecursosTotalCollectionTbOsRecursosTotal);
                }
            }
            for (TbApontamentosMateriais tbApontamentosMateriaisCollectionTbApontamentosMateriais : tbOsServico.getTbApontamentosMateriaisCollection()) {
                TbOsServico oldTbOsServicoHandOfTbApontamentosMateriaisCollectionTbApontamentosMateriais = tbApontamentosMateriaisCollectionTbApontamentosMateriais.getTbOsServicoHand();
                tbApontamentosMateriaisCollectionTbApontamentosMateriais.setTbOsServicoHand(tbOsServico);
                tbApontamentosMateriaisCollectionTbApontamentosMateriais = em.merge(tbApontamentosMateriaisCollectionTbApontamentosMateriais);
                if (oldTbOsServicoHandOfTbApontamentosMateriaisCollectionTbApontamentosMateriais != null) {
                    oldTbOsServicoHandOfTbApontamentosMateriaisCollectionTbApontamentosMateriais.getTbApontamentosMateriaisCollection().remove(tbApontamentosMateriaisCollectionTbApontamentosMateriais);
                    oldTbOsServicoHandOfTbApontamentosMateriaisCollectionTbApontamentosMateriais = em.merge(oldTbOsServicoHandOfTbApontamentosMateriaisCollectionTbApontamentosMateriais);
                }
            }
            for (TbOsMateriaisTotal tbOsMateriaisTotalCollectionTbOsMateriaisTotal : tbOsServico.getTbOsMateriaisTotalCollection()) {
                TbOsServico oldTbOsServicoHandOfTbOsMateriaisTotalCollectionTbOsMateriaisTotal = tbOsMateriaisTotalCollectionTbOsMateriaisTotal.getTbOsServicoHand();
                tbOsMateriaisTotalCollectionTbOsMateriaisTotal.setTbOsServicoHand(tbOsServico);
                tbOsMateriaisTotalCollectionTbOsMateriaisTotal = em.merge(tbOsMateriaisTotalCollectionTbOsMateriaisTotal);
                if (oldTbOsServicoHandOfTbOsMateriaisTotalCollectionTbOsMateriaisTotal != null) {
                    oldTbOsServicoHandOfTbOsMateriaisTotalCollectionTbOsMateriaisTotal.getTbOsMateriaisTotalCollection().remove(tbOsMateriaisTotalCollectionTbOsMateriaisTotal);
                    oldTbOsServicoHandOfTbOsMateriaisTotalCollectionTbOsMateriaisTotal = em.merge(oldTbOsServicoHandOfTbOsMateriaisTotalCollectionTbOsMateriaisTotal);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTbOsServico(tbOsServico.getHand()) != null) {
                throw new PreexistingEntityException("TbOsServico " + tbOsServico + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TbOsServico tbOsServico) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbOsServico persistentTbOsServico = em.find(TbOsServico.class, tbOsServico.getHand());
            TbOrdemServico tbOrdemServicoHandOld = persistentTbOsServico.getTbOrdemServicoHand();
            TbOrdemServico tbOrdemServicoHandNew = tbOsServico.getTbOrdemServicoHand();
            TbProjetosServicos tbProjetosServicosHandOld = persistentTbOsServico.getTbProjetosServicosHand();
            TbProjetosServicos tbProjetosServicosHandNew = tbOsServico.getTbProjetosServicosHand();
            TbStatus tbStatusHandOld = persistentTbOsServico.getTbStatusHand();
            TbStatus tbStatusHandNew = tbOsServico.getTbStatusHand();
            Collection<TbApontamentosRecursos> tbApontamentosRecursosCollectionOld = persistentTbOsServico.getTbApontamentosRecursosCollection();
            Collection<TbApontamentosRecursos> tbApontamentosRecursosCollectionNew = tbOsServico.getTbApontamentosRecursosCollection();
            Collection<TbOsRecursosTotal> tbOsRecursosTotalCollectionOld = persistentTbOsServico.getTbOsRecursosTotalCollection();
            Collection<TbOsRecursosTotal> tbOsRecursosTotalCollectionNew = tbOsServico.getTbOsRecursosTotalCollection();
            Collection<TbApontamentosMateriais> tbApontamentosMateriaisCollectionOld = persistentTbOsServico.getTbApontamentosMateriaisCollection();
            Collection<TbApontamentosMateriais> tbApontamentosMateriaisCollectionNew = tbOsServico.getTbApontamentosMateriaisCollection();
            Collection<TbOsMateriaisTotal> tbOsMateriaisTotalCollectionOld = persistentTbOsServico.getTbOsMateriaisTotalCollection();
            Collection<TbOsMateriaisTotal> tbOsMateriaisTotalCollectionNew = tbOsServico.getTbOsMateriaisTotalCollection();
            List<String> illegalOrphanMessages = null;
            for (TbApontamentosRecursos tbApontamentosRecursosCollectionOldTbApontamentosRecursos : tbApontamentosRecursosCollectionOld) {
                if (!tbApontamentosRecursosCollectionNew.contains(tbApontamentosRecursosCollectionOldTbApontamentosRecursos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TbApontamentosRecursos " + tbApontamentosRecursosCollectionOldTbApontamentosRecursos + " since its tbOsServicoHand field is not nullable.");
                }
            }
            for (TbOsRecursosTotal tbOsRecursosTotalCollectionOldTbOsRecursosTotal : tbOsRecursosTotalCollectionOld) {
                if (!tbOsRecursosTotalCollectionNew.contains(tbOsRecursosTotalCollectionOldTbOsRecursosTotal)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TbOsRecursosTotal " + tbOsRecursosTotalCollectionOldTbOsRecursosTotal + " since its tbOsServicoHand field is not nullable.");
                }
            }
            for (TbApontamentosMateriais tbApontamentosMateriaisCollectionOldTbApontamentosMateriais : tbApontamentosMateriaisCollectionOld) {
                if (!tbApontamentosMateriaisCollectionNew.contains(tbApontamentosMateriaisCollectionOldTbApontamentosMateriais)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TbApontamentosMateriais " + tbApontamentosMateriaisCollectionOldTbApontamentosMateriais + " since its tbOsServicoHand field is not nullable.");
                }
            }
            for (TbOsMateriaisTotal tbOsMateriaisTotalCollectionOldTbOsMateriaisTotal : tbOsMateriaisTotalCollectionOld) {
                if (!tbOsMateriaisTotalCollectionNew.contains(tbOsMateriaisTotalCollectionOldTbOsMateriaisTotal)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TbOsMateriaisTotal " + tbOsMateriaisTotalCollectionOldTbOsMateriaisTotal + " since its tbOsServicoHand field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tbOrdemServicoHandNew != null) {
                tbOrdemServicoHandNew = em.getReference(tbOrdemServicoHandNew.getClass(), tbOrdemServicoHandNew.getHand());
                tbOsServico.setTbOrdemServicoHand(tbOrdemServicoHandNew);
            }
            if (tbProjetosServicosHandNew != null) {
                tbProjetosServicosHandNew = em.getReference(tbProjetosServicosHandNew.getClass(), tbProjetosServicosHandNew.getHand());
                tbOsServico.setTbProjetosServicosHand(tbProjetosServicosHandNew);
            }
            if (tbStatusHandNew != null) {
                tbStatusHandNew = em.getReference(tbStatusHandNew.getClass(), tbStatusHandNew.getHand());
                tbOsServico.setTbStatusHand(tbStatusHandNew);
            }
            Collection<TbApontamentosRecursos> attachedTbApontamentosRecursosCollectionNew = new ArrayList<TbApontamentosRecursos>();
            for (TbApontamentosRecursos tbApontamentosRecursosCollectionNewTbApontamentosRecursosToAttach : tbApontamentosRecursosCollectionNew) {
                tbApontamentosRecursosCollectionNewTbApontamentosRecursosToAttach = em.getReference(tbApontamentosRecursosCollectionNewTbApontamentosRecursosToAttach.getClass(), tbApontamentosRecursosCollectionNewTbApontamentosRecursosToAttach.getHand());
                attachedTbApontamentosRecursosCollectionNew.add(tbApontamentosRecursosCollectionNewTbApontamentosRecursosToAttach);
            }
            tbApontamentosRecursosCollectionNew = attachedTbApontamentosRecursosCollectionNew;
            tbOsServico.setTbApontamentosRecursosCollection(tbApontamentosRecursosCollectionNew);
            Collection<TbOsRecursosTotal> attachedTbOsRecursosTotalCollectionNew = new ArrayList<TbOsRecursosTotal>();
            for (TbOsRecursosTotal tbOsRecursosTotalCollectionNewTbOsRecursosTotalToAttach : tbOsRecursosTotalCollectionNew) {
                tbOsRecursosTotalCollectionNewTbOsRecursosTotalToAttach = em.getReference(tbOsRecursosTotalCollectionNewTbOsRecursosTotalToAttach.getClass(), tbOsRecursosTotalCollectionNewTbOsRecursosTotalToAttach.getHand());
                attachedTbOsRecursosTotalCollectionNew.add(tbOsRecursosTotalCollectionNewTbOsRecursosTotalToAttach);
            }
            tbOsRecursosTotalCollectionNew = attachedTbOsRecursosTotalCollectionNew;
            tbOsServico.setTbOsRecursosTotalCollection(tbOsRecursosTotalCollectionNew);
            Collection<TbApontamentosMateriais> attachedTbApontamentosMateriaisCollectionNew = new ArrayList<TbApontamentosMateriais>();
            for (TbApontamentosMateriais tbApontamentosMateriaisCollectionNewTbApontamentosMateriaisToAttach : tbApontamentosMateriaisCollectionNew) {
                tbApontamentosMateriaisCollectionNewTbApontamentosMateriaisToAttach = em.getReference(tbApontamentosMateriaisCollectionNewTbApontamentosMateriaisToAttach.getClass(), tbApontamentosMateriaisCollectionNewTbApontamentosMateriaisToAttach.getHand());
                attachedTbApontamentosMateriaisCollectionNew.add(tbApontamentosMateriaisCollectionNewTbApontamentosMateriaisToAttach);
            }
            tbApontamentosMateriaisCollectionNew = attachedTbApontamentosMateriaisCollectionNew;
            tbOsServico.setTbApontamentosMateriaisCollection(tbApontamentosMateriaisCollectionNew);
            Collection<TbOsMateriaisTotal> attachedTbOsMateriaisTotalCollectionNew = new ArrayList<TbOsMateriaisTotal>();
            for (TbOsMateriaisTotal tbOsMateriaisTotalCollectionNewTbOsMateriaisTotalToAttach : tbOsMateriaisTotalCollectionNew) {
                tbOsMateriaisTotalCollectionNewTbOsMateriaisTotalToAttach = em.getReference(tbOsMateriaisTotalCollectionNewTbOsMateriaisTotalToAttach.getClass(), tbOsMateriaisTotalCollectionNewTbOsMateriaisTotalToAttach.getHand());
                attachedTbOsMateriaisTotalCollectionNew.add(tbOsMateriaisTotalCollectionNewTbOsMateriaisTotalToAttach);
            }
            tbOsMateriaisTotalCollectionNew = attachedTbOsMateriaisTotalCollectionNew;
            tbOsServico.setTbOsMateriaisTotalCollection(tbOsMateriaisTotalCollectionNew);
            tbOsServico = em.merge(tbOsServico);
            if (tbOrdemServicoHandOld != null && !tbOrdemServicoHandOld.equals(tbOrdemServicoHandNew)) {
                tbOrdemServicoHandOld.getTbOsServicoCollection().remove(tbOsServico);
                tbOrdemServicoHandOld = em.merge(tbOrdemServicoHandOld);
            }
            if (tbOrdemServicoHandNew != null && !tbOrdemServicoHandNew.equals(tbOrdemServicoHandOld)) {
                tbOrdemServicoHandNew.getTbOsServicoCollection().add(tbOsServico);
                tbOrdemServicoHandNew = em.merge(tbOrdemServicoHandNew);
            }
            if (tbProjetosServicosHandOld != null && !tbProjetosServicosHandOld.equals(tbProjetosServicosHandNew)) {
                tbProjetosServicosHandOld.getTbOsServicoCollection().remove(tbOsServico);
                tbProjetosServicosHandOld = em.merge(tbProjetosServicosHandOld);
            }
            if (tbProjetosServicosHandNew != null && !tbProjetosServicosHandNew.equals(tbProjetosServicosHandOld)) {
                tbProjetosServicosHandNew.getTbOsServicoCollection().add(tbOsServico);
                tbProjetosServicosHandNew = em.merge(tbProjetosServicosHandNew);
            }
            if (tbStatusHandOld != null && !tbStatusHandOld.equals(tbStatusHandNew)) {
                tbStatusHandOld.getTbOsServicoCollection().remove(tbOsServico);
                tbStatusHandOld = em.merge(tbStatusHandOld);
            }
            if (tbStatusHandNew != null && !tbStatusHandNew.equals(tbStatusHandOld)) {
                tbStatusHandNew.getTbOsServicoCollection().add(tbOsServico);
                tbStatusHandNew = em.merge(tbStatusHandNew);
            }
            for (TbApontamentosRecursos tbApontamentosRecursosCollectionNewTbApontamentosRecursos : tbApontamentosRecursosCollectionNew) {
                if (!tbApontamentosRecursosCollectionOld.contains(tbApontamentosRecursosCollectionNewTbApontamentosRecursos)) {
                    TbOsServico oldTbOsServicoHandOfTbApontamentosRecursosCollectionNewTbApontamentosRecursos = tbApontamentosRecursosCollectionNewTbApontamentosRecursos.getTbOsServicoHand();
                    tbApontamentosRecursosCollectionNewTbApontamentosRecursos.setTbOsServicoHand(tbOsServico);
                    tbApontamentosRecursosCollectionNewTbApontamentosRecursos = em.merge(tbApontamentosRecursosCollectionNewTbApontamentosRecursos);
                    if (oldTbOsServicoHandOfTbApontamentosRecursosCollectionNewTbApontamentosRecursos != null && !oldTbOsServicoHandOfTbApontamentosRecursosCollectionNewTbApontamentosRecursos.equals(tbOsServico)) {
                        oldTbOsServicoHandOfTbApontamentosRecursosCollectionNewTbApontamentosRecursos.getTbApontamentosRecursosCollection().remove(tbApontamentosRecursosCollectionNewTbApontamentosRecursos);
                        oldTbOsServicoHandOfTbApontamentosRecursosCollectionNewTbApontamentosRecursos = em.merge(oldTbOsServicoHandOfTbApontamentosRecursosCollectionNewTbApontamentosRecursos);
                    }
                }
            }
            for (TbOsRecursosTotal tbOsRecursosTotalCollectionNewTbOsRecursosTotal : tbOsRecursosTotalCollectionNew) {
                if (!tbOsRecursosTotalCollectionOld.contains(tbOsRecursosTotalCollectionNewTbOsRecursosTotal)) {
                    TbOsServico oldTbOsServicoHandOfTbOsRecursosTotalCollectionNewTbOsRecursosTotal = tbOsRecursosTotalCollectionNewTbOsRecursosTotal.getTbOsServicoHand();
                    tbOsRecursosTotalCollectionNewTbOsRecursosTotal.setTbOsServicoHand(tbOsServico);
                    tbOsRecursosTotalCollectionNewTbOsRecursosTotal = em.merge(tbOsRecursosTotalCollectionNewTbOsRecursosTotal);
                    if (oldTbOsServicoHandOfTbOsRecursosTotalCollectionNewTbOsRecursosTotal != null && !oldTbOsServicoHandOfTbOsRecursosTotalCollectionNewTbOsRecursosTotal.equals(tbOsServico)) {
                        oldTbOsServicoHandOfTbOsRecursosTotalCollectionNewTbOsRecursosTotal.getTbOsRecursosTotalCollection().remove(tbOsRecursosTotalCollectionNewTbOsRecursosTotal);
                        oldTbOsServicoHandOfTbOsRecursosTotalCollectionNewTbOsRecursosTotal = em.merge(oldTbOsServicoHandOfTbOsRecursosTotalCollectionNewTbOsRecursosTotal);
                    }
                }
            }
            for (TbApontamentosMateriais tbApontamentosMateriaisCollectionNewTbApontamentosMateriais : tbApontamentosMateriaisCollectionNew) {
                if (!tbApontamentosMateriaisCollectionOld.contains(tbApontamentosMateriaisCollectionNewTbApontamentosMateriais)) {
                    TbOsServico oldTbOsServicoHandOfTbApontamentosMateriaisCollectionNewTbApontamentosMateriais = tbApontamentosMateriaisCollectionNewTbApontamentosMateriais.getTbOsServicoHand();
                    tbApontamentosMateriaisCollectionNewTbApontamentosMateriais.setTbOsServicoHand(tbOsServico);
                    tbApontamentosMateriaisCollectionNewTbApontamentosMateriais = em.merge(tbApontamentosMateriaisCollectionNewTbApontamentosMateriais);
                    if (oldTbOsServicoHandOfTbApontamentosMateriaisCollectionNewTbApontamentosMateriais != null && !oldTbOsServicoHandOfTbApontamentosMateriaisCollectionNewTbApontamentosMateriais.equals(tbOsServico)) {
                        oldTbOsServicoHandOfTbApontamentosMateriaisCollectionNewTbApontamentosMateriais.getTbApontamentosMateriaisCollection().remove(tbApontamentosMateriaisCollectionNewTbApontamentosMateriais);
                        oldTbOsServicoHandOfTbApontamentosMateriaisCollectionNewTbApontamentosMateriais = em.merge(oldTbOsServicoHandOfTbApontamentosMateriaisCollectionNewTbApontamentosMateriais);
                    }
                }
            }
            for (TbOsMateriaisTotal tbOsMateriaisTotalCollectionNewTbOsMateriaisTotal : tbOsMateriaisTotalCollectionNew) {
                if (!tbOsMateriaisTotalCollectionOld.contains(tbOsMateriaisTotalCollectionNewTbOsMateriaisTotal)) {
                    TbOsServico oldTbOsServicoHandOfTbOsMateriaisTotalCollectionNewTbOsMateriaisTotal = tbOsMateriaisTotalCollectionNewTbOsMateriaisTotal.getTbOsServicoHand();
                    tbOsMateriaisTotalCollectionNewTbOsMateriaisTotal.setTbOsServicoHand(tbOsServico);
                    tbOsMateriaisTotalCollectionNewTbOsMateriaisTotal = em.merge(tbOsMateriaisTotalCollectionNewTbOsMateriaisTotal);
                    if (oldTbOsServicoHandOfTbOsMateriaisTotalCollectionNewTbOsMateriaisTotal != null && !oldTbOsServicoHandOfTbOsMateriaisTotalCollectionNewTbOsMateriaisTotal.equals(tbOsServico)) {
                        oldTbOsServicoHandOfTbOsMateriaisTotalCollectionNewTbOsMateriaisTotal.getTbOsMateriaisTotalCollection().remove(tbOsMateriaisTotalCollectionNewTbOsMateriaisTotal);
                        oldTbOsServicoHandOfTbOsMateriaisTotalCollectionNewTbOsMateriaisTotal = em.merge(oldTbOsServicoHandOfTbOsMateriaisTotalCollectionNewTbOsMateriaisTotal);
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
                Integer id = tbOsServico.getHand();
                if (findTbOsServico(id) == null) {
                    throw new NonexistentEntityException("The tbOsServico with id " + id + " no longer exists.");
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
            TbOsServico tbOsServico;
            try {
                tbOsServico = em.getReference(TbOsServico.class, id);
                tbOsServico.getHand();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tbOsServico with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TbApontamentosRecursos> tbApontamentosRecursosCollectionOrphanCheck = tbOsServico.getTbApontamentosRecursosCollection();
            for (TbApontamentosRecursos tbApontamentosRecursosCollectionOrphanCheckTbApontamentosRecursos : tbApontamentosRecursosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbOsServico (" + tbOsServico + ") cannot be destroyed since the TbApontamentosRecursos " + tbApontamentosRecursosCollectionOrphanCheckTbApontamentosRecursos + " in its tbApontamentosRecursosCollection field has a non-nullable tbOsServicoHand field.");
            }
            Collection<TbOsRecursosTotal> tbOsRecursosTotalCollectionOrphanCheck = tbOsServico.getTbOsRecursosTotalCollection();
            for (TbOsRecursosTotal tbOsRecursosTotalCollectionOrphanCheckTbOsRecursosTotal : tbOsRecursosTotalCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbOsServico (" + tbOsServico + ") cannot be destroyed since the TbOsRecursosTotal " + tbOsRecursosTotalCollectionOrphanCheckTbOsRecursosTotal + " in its tbOsRecursosTotalCollection field has a non-nullable tbOsServicoHand field.");
            }
            Collection<TbApontamentosMateriais> tbApontamentosMateriaisCollectionOrphanCheck = tbOsServico.getTbApontamentosMateriaisCollection();
            for (TbApontamentosMateriais tbApontamentosMateriaisCollectionOrphanCheckTbApontamentosMateriais : tbApontamentosMateriaisCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbOsServico (" + tbOsServico + ") cannot be destroyed since the TbApontamentosMateriais " + tbApontamentosMateriaisCollectionOrphanCheckTbApontamentosMateriais + " in its tbApontamentosMateriaisCollection field has a non-nullable tbOsServicoHand field.");
            }
            Collection<TbOsMateriaisTotal> tbOsMateriaisTotalCollectionOrphanCheck = tbOsServico.getTbOsMateriaisTotalCollection();
            for (TbOsMateriaisTotal tbOsMateriaisTotalCollectionOrphanCheckTbOsMateriaisTotal : tbOsMateriaisTotalCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbOsServico (" + tbOsServico + ") cannot be destroyed since the TbOsMateriaisTotal " + tbOsMateriaisTotalCollectionOrphanCheckTbOsMateriaisTotal + " in its tbOsMateriaisTotalCollection field has a non-nullable tbOsServicoHand field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            TbOrdemServico tbOrdemServicoHand = tbOsServico.getTbOrdemServicoHand();
            if (tbOrdemServicoHand != null) {
                tbOrdemServicoHand.getTbOsServicoCollection().remove(tbOsServico);
                tbOrdemServicoHand = em.merge(tbOrdemServicoHand);
            }
            TbProjetosServicos tbProjetosServicosHand = tbOsServico.getTbProjetosServicosHand();
            if (tbProjetosServicosHand != null) {
                tbProjetosServicosHand.getTbOsServicoCollection().remove(tbOsServico);
                tbProjetosServicosHand = em.merge(tbProjetosServicosHand);
            }
            TbStatus tbStatusHand = tbOsServico.getTbStatusHand();
            if (tbStatusHand != null) {
                tbStatusHand.getTbOsServicoCollection().remove(tbOsServico);
                tbStatusHand = em.merge(tbStatusHand);
            }
            em.remove(tbOsServico);
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

    public List<TbOsServico> findTbOsServicoEntities() {
        return findTbOsServicoEntities(true, -1, -1);
    }

    public List<TbOsServico> findTbOsServicoEntities(int maxResults, int firstResult) {
        return findTbOsServicoEntities(false, maxResults, firstResult);
    }

    private List<TbOsServico> findTbOsServicoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TbOsServico.class));
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

    public TbOsServico findTbOsServico(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbOsServico.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbOsServicoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TbOsServico> rt = cq.from(TbOsServico.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
