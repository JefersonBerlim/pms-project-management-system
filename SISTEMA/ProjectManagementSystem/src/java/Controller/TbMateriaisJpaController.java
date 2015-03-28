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
import Model.TbUnidadeMedida;
import Model.TbProjetosMateriais;
import java.util.ArrayList;
import java.util.Collection;
import Model.TbApontamentosMateriais;
import Model.TbMateriais;
import Model.TbMaterialMarcoSvc;
import Model.TbOsMateriaisTotal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author BERLIM
 */
public class TbMateriaisJpaController implements Serializable {

    public TbMateriaisJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TbMateriais tbMateriais) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (tbMateriais.getTbProjetosMateriaisCollection() == null) {
            tbMateriais.setTbProjetosMateriaisCollection(new ArrayList<TbProjetosMateriais>());
        }
        if (tbMateriais.getTbApontamentosMateriaisCollection() == null) {
            tbMateriais.setTbApontamentosMateriaisCollection(new ArrayList<TbApontamentosMateriais>());
        }
        if (tbMateriais.getTbMaterialMarcoSvcCollection() == null) {
            tbMateriais.setTbMaterialMarcoSvcCollection(new ArrayList<TbMaterialMarcoSvc>());
        }
        if (tbMateriais.getTbOsMateriaisTotalCollection() == null) {
            tbMateriais.setTbOsMateriaisTotalCollection(new ArrayList<TbOsMateriaisTotal>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbUnidadeMedida tbUnidadeMedidaHand = tbMateriais.getTbUnidadeMedidaHand();
            if (tbUnidadeMedidaHand != null) {
                tbUnidadeMedidaHand = em.getReference(tbUnidadeMedidaHand.getClass(), tbUnidadeMedidaHand.getHand());
                tbMateriais.setTbUnidadeMedidaHand(tbUnidadeMedidaHand);
            }
            Collection<TbProjetosMateriais> attachedTbProjetosMateriaisCollection = new ArrayList<TbProjetosMateriais>();
            for (TbProjetosMateriais tbProjetosMateriaisCollectionTbProjetosMateriaisToAttach : tbMateriais.getTbProjetosMateriaisCollection()) {
                tbProjetosMateriaisCollectionTbProjetosMateriaisToAttach = em.getReference(tbProjetosMateriaisCollectionTbProjetosMateriaisToAttach.getClass(), tbProjetosMateriaisCollectionTbProjetosMateriaisToAttach.getHand());
                attachedTbProjetosMateriaisCollection.add(tbProjetosMateriaisCollectionTbProjetosMateriaisToAttach);
            }
            tbMateriais.setTbProjetosMateriaisCollection(attachedTbProjetosMateriaisCollection);
            Collection<TbApontamentosMateriais> attachedTbApontamentosMateriaisCollection = new ArrayList<TbApontamentosMateriais>();
            for (TbApontamentosMateriais tbApontamentosMateriaisCollectionTbApontamentosMateriaisToAttach : tbMateriais.getTbApontamentosMateriaisCollection()) {
                tbApontamentosMateriaisCollectionTbApontamentosMateriaisToAttach = em.getReference(tbApontamentosMateriaisCollectionTbApontamentosMateriaisToAttach.getClass(), tbApontamentosMateriaisCollectionTbApontamentosMateriaisToAttach.getHand());
                attachedTbApontamentosMateriaisCollection.add(tbApontamentosMateriaisCollectionTbApontamentosMateriaisToAttach);
            }
            tbMateriais.setTbApontamentosMateriaisCollection(attachedTbApontamentosMateriaisCollection);
            Collection<TbMaterialMarcoSvc> attachedTbMaterialMarcoSvcCollection = new ArrayList<TbMaterialMarcoSvc>();
            for (TbMaterialMarcoSvc tbMaterialMarcoSvcCollectionTbMaterialMarcoSvcToAttach : tbMateriais.getTbMaterialMarcoSvcCollection()) {
                tbMaterialMarcoSvcCollectionTbMaterialMarcoSvcToAttach = em.getReference(tbMaterialMarcoSvcCollectionTbMaterialMarcoSvcToAttach.getClass(), tbMaterialMarcoSvcCollectionTbMaterialMarcoSvcToAttach.getHand());
                attachedTbMaterialMarcoSvcCollection.add(tbMaterialMarcoSvcCollectionTbMaterialMarcoSvcToAttach);
            }
            tbMateriais.setTbMaterialMarcoSvcCollection(attachedTbMaterialMarcoSvcCollection);
            Collection<TbOsMateriaisTotal> attachedTbOsMateriaisTotalCollection = new ArrayList<TbOsMateriaisTotal>();
            for (TbOsMateriaisTotal tbOsMateriaisTotalCollectionTbOsMateriaisTotalToAttach : tbMateriais.getTbOsMateriaisTotalCollection()) {
                tbOsMateriaisTotalCollectionTbOsMateriaisTotalToAttach = em.getReference(tbOsMateriaisTotalCollectionTbOsMateriaisTotalToAttach.getClass(), tbOsMateriaisTotalCollectionTbOsMateriaisTotalToAttach.getHand());
                attachedTbOsMateriaisTotalCollection.add(tbOsMateriaisTotalCollectionTbOsMateriaisTotalToAttach);
            }
            tbMateriais.setTbOsMateriaisTotalCollection(attachedTbOsMateriaisTotalCollection);
            em.persist(tbMateriais);
            if (tbUnidadeMedidaHand != null) {
                tbUnidadeMedidaHand.getTbMateriaisCollection().add(tbMateriais);
                tbUnidadeMedidaHand = em.merge(tbUnidadeMedidaHand);
            }
            for (TbProjetosMateriais tbProjetosMateriaisCollectionTbProjetosMateriais : tbMateriais.getTbProjetosMateriaisCollection()) {
                TbMateriais oldTbMateriaisHandOfTbProjetosMateriaisCollectionTbProjetosMateriais = tbProjetosMateriaisCollectionTbProjetosMateriais.getTbMateriaisHand();
                tbProjetosMateriaisCollectionTbProjetosMateriais.setTbMateriaisHand(tbMateriais);
                tbProjetosMateriaisCollectionTbProjetosMateriais = em.merge(tbProjetosMateriaisCollectionTbProjetosMateriais);
                if (oldTbMateriaisHandOfTbProjetosMateriaisCollectionTbProjetosMateriais != null) {
                    oldTbMateriaisHandOfTbProjetosMateriaisCollectionTbProjetosMateriais.getTbProjetosMateriaisCollection().remove(tbProjetosMateriaisCollectionTbProjetosMateriais);
                    oldTbMateriaisHandOfTbProjetosMateriaisCollectionTbProjetosMateriais = em.merge(oldTbMateriaisHandOfTbProjetosMateriaisCollectionTbProjetosMateriais);
                }
            }
            for (TbApontamentosMateriais tbApontamentosMateriaisCollectionTbApontamentosMateriais : tbMateriais.getTbApontamentosMateriaisCollection()) {
                TbMateriais oldTbMateriaisHandOfTbApontamentosMateriaisCollectionTbApontamentosMateriais = tbApontamentosMateriaisCollectionTbApontamentosMateriais.getTbMateriaisHand();
                tbApontamentosMateriaisCollectionTbApontamentosMateriais.setTbMateriaisHand(tbMateriais);
                tbApontamentosMateriaisCollectionTbApontamentosMateriais = em.merge(tbApontamentosMateriaisCollectionTbApontamentosMateriais);
                if (oldTbMateriaisHandOfTbApontamentosMateriaisCollectionTbApontamentosMateriais != null) {
                    oldTbMateriaisHandOfTbApontamentosMateriaisCollectionTbApontamentosMateriais.getTbApontamentosMateriaisCollection().remove(tbApontamentosMateriaisCollectionTbApontamentosMateriais);
                    oldTbMateriaisHandOfTbApontamentosMateriaisCollectionTbApontamentosMateriais = em.merge(oldTbMateriaisHandOfTbApontamentosMateriaisCollectionTbApontamentosMateriais);
                }
            }
            for (TbMaterialMarcoSvc tbMaterialMarcoSvcCollectionTbMaterialMarcoSvc : tbMateriais.getTbMaterialMarcoSvcCollection()) {
                TbMateriais oldTbMateriaisHandOfTbMaterialMarcoSvcCollectionTbMaterialMarcoSvc = tbMaterialMarcoSvcCollectionTbMaterialMarcoSvc.getTbMateriaisHand();
                tbMaterialMarcoSvcCollectionTbMaterialMarcoSvc.setTbMateriaisHand(tbMateriais);
                tbMaterialMarcoSvcCollectionTbMaterialMarcoSvc = em.merge(tbMaterialMarcoSvcCollectionTbMaterialMarcoSvc);
                if (oldTbMateriaisHandOfTbMaterialMarcoSvcCollectionTbMaterialMarcoSvc != null) {
                    oldTbMateriaisHandOfTbMaterialMarcoSvcCollectionTbMaterialMarcoSvc.getTbMaterialMarcoSvcCollection().remove(tbMaterialMarcoSvcCollectionTbMaterialMarcoSvc);
                    oldTbMateriaisHandOfTbMaterialMarcoSvcCollectionTbMaterialMarcoSvc = em.merge(oldTbMateriaisHandOfTbMaterialMarcoSvcCollectionTbMaterialMarcoSvc);
                }
            }
            for (TbOsMateriaisTotal tbOsMateriaisTotalCollectionTbOsMateriaisTotal : tbMateriais.getTbOsMateriaisTotalCollection()) {
                TbMateriais oldTbMateriaisHandOfTbOsMateriaisTotalCollectionTbOsMateriaisTotal = tbOsMateriaisTotalCollectionTbOsMateriaisTotal.getTbMateriaisHand();
                tbOsMateriaisTotalCollectionTbOsMateriaisTotal.setTbMateriaisHand(tbMateriais);
                tbOsMateriaisTotalCollectionTbOsMateriaisTotal = em.merge(tbOsMateriaisTotalCollectionTbOsMateriaisTotal);
                if (oldTbMateriaisHandOfTbOsMateriaisTotalCollectionTbOsMateriaisTotal != null) {
                    oldTbMateriaisHandOfTbOsMateriaisTotalCollectionTbOsMateriaisTotal.getTbOsMateriaisTotalCollection().remove(tbOsMateriaisTotalCollectionTbOsMateriaisTotal);
                    oldTbMateriaisHandOfTbOsMateriaisTotalCollectionTbOsMateriaisTotal = em.merge(oldTbMateriaisHandOfTbOsMateriaisTotalCollectionTbOsMateriaisTotal);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTbMateriais(tbMateriais.getHand()) != null) {
                throw new PreexistingEntityException("TbMateriais " + tbMateriais + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TbMateriais tbMateriais) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbMateriais persistentTbMateriais = em.find(TbMateriais.class, tbMateriais.getHand());
            TbUnidadeMedida tbUnidadeMedidaHandOld = persistentTbMateriais.getTbUnidadeMedidaHand();
            TbUnidadeMedida tbUnidadeMedidaHandNew = tbMateriais.getTbUnidadeMedidaHand();
            Collection<TbProjetosMateriais> tbProjetosMateriaisCollectionOld = persistentTbMateriais.getTbProjetosMateriaisCollection();
            Collection<TbProjetosMateriais> tbProjetosMateriaisCollectionNew = tbMateriais.getTbProjetosMateriaisCollection();
            Collection<TbApontamentosMateriais> tbApontamentosMateriaisCollectionOld = persistentTbMateriais.getTbApontamentosMateriaisCollection();
            Collection<TbApontamentosMateriais> tbApontamentosMateriaisCollectionNew = tbMateriais.getTbApontamentosMateriaisCollection();
            Collection<TbMaterialMarcoSvc> tbMaterialMarcoSvcCollectionOld = persistentTbMateriais.getTbMaterialMarcoSvcCollection();
            Collection<TbMaterialMarcoSvc> tbMaterialMarcoSvcCollectionNew = tbMateriais.getTbMaterialMarcoSvcCollection();
            Collection<TbOsMateriaisTotal> tbOsMateriaisTotalCollectionOld = persistentTbMateriais.getTbOsMateriaisTotalCollection();
            Collection<TbOsMateriaisTotal> tbOsMateriaisTotalCollectionNew = tbMateriais.getTbOsMateriaisTotalCollection();
            List<String> illegalOrphanMessages = null;
            for (TbProjetosMateriais tbProjetosMateriaisCollectionOldTbProjetosMateriais : tbProjetosMateriaisCollectionOld) {
                if (!tbProjetosMateriaisCollectionNew.contains(tbProjetosMateriaisCollectionOldTbProjetosMateriais)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TbProjetosMateriais " + tbProjetosMateriaisCollectionOldTbProjetosMateriais + " since its tbMateriaisHand field is not nullable.");
                }
            }
            for (TbApontamentosMateriais tbApontamentosMateriaisCollectionOldTbApontamentosMateriais : tbApontamentosMateriaisCollectionOld) {
                if (!tbApontamentosMateriaisCollectionNew.contains(tbApontamentosMateriaisCollectionOldTbApontamentosMateriais)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TbApontamentosMateriais " + tbApontamentosMateriaisCollectionOldTbApontamentosMateriais + " since its tbMateriaisHand field is not nullable.");
                }
            }
            for (TbMaterialMarcoSvc tbMaterialMarcoSvcCollectionOldTbMaterialMarcoSvc : tbMaterialMarcoSvcCollectionOld) {
                if (!tbMaterialMarcoSvcCollectionNew.contains(tbMaterialMarcoSvcCollectionOldTbMaterialMarcoSvc)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TbMaterialMarcoSvc " + tbMaterialMarcoSvcCollectionOldTbMaterialMarcoSvc + " since its tbMateriaisHand field is not nullable.");
                }
            }
            for (TbOsMateriaisTotal tbOsMateriaisTotalCollectionOldTbOsMateriaisTotal : tbOsMateriaisTotalCollectionOld) {
                if (!tbOsMateriaisTotalCollectionNew.contains(tbOsMateriaisTotalCollectionOldTbOsMateriaisTotal)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TbOsMateriaisTotal " + tbOsMateriaisTotalCollectionOldTbOsMateriaisTotal + " since its tbMateriaisHand field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tbUnidadeMedidaHandNew != null) {
                tbUnidadeMedidaHandNew = em.getReference(tbUnidadeMedidaHandNew.getClass(), tbUnidadeMedidaHandNew.getHand());
                tbMateriais.setTbUnidadeMedidaHand(tbUnidadeMedidaHandNew);
            }
            Collection<TbProjetosMateriais> attachedTbProjetosMateriaisCollectionNew = new ArrayList<TbProjetosMateriais>();
            for (TbProjetosMateriais tbProjetosMateriaisCollectionNewTbProjetosMateriaisToAttach : tbProjetosMateriaisCollectionNew) {
                tbProjetosMateriaisCollectionNewTbProjetosMateriaisToAttach = em.getReference(tbProjetosMateriaisCollectionNewTbProjetosMateriaisToAttach.getClass(), tbProjetosMateriaisCollectionNewTbProjetosMateriaisToAttach.getHand());
                attachedTbProjetosMateriaisCollectionNew.add(tbProjetosMateriaisCollectionNewTbProjetosMateriaisToAttach);
            }
            tbProjetosMateriaisCollectionNew = attachedTbProjetosMateriaisCollectionNew;
            tbMateriais.setTbProjetosMateriaisCollection(tbProjetosMateriaisCollectionNew);
            Collection<TbApontamentosMateriais> attachedTbApontamentosMateriaisCollectionNew = new ArrayList<TbApontamentosMateriais>();
            for (TbApontamentosMateriais tbApontamentosMateriaisCollectionNewTbApontamentosMateriaisToAttach : tbApontamentosMateriaisCollectionNew) {
                tbApontamentosMateriaisCollectionNewTbApontamentosMateriaisToAttach = em.getReference(tbApontamentosMateriaisCollectionNewTbApontamentosMateriaisToAttach.getClass(), tbApontamentosMateriaisCollectionNewTbApontamentosMateriaisToAttach.getHand());
                attachedTbApontamentosMateriaisCollectionNew.add(tbApontamentosMateriaisCollectionNewTbApontamentosMateriaisToAttach);
            }
            tbApontamentosMateriaisCollectionNew = attachedTbApontamentosMateriaisCollectionNew;
            tbMateriais.setTbApontamentosMateriaisCollection(tbApontamentosMateriaisCollectionNew);
            Collection<TbMaterialMarcoSvc> attachedTbMaterialMarcoSvcCollectionNew = new ArrayList<TbMaterialMarcoSvc>();
            for (TbMaterialMarcoSvc tbMaterialMarcoSvcCollectionNewTbMaterialMarcoSvcToAttach : tbMaterialMarcoSvcCollectionNew) {
                tbMaterialMarcoSvcCollectionNewTbMaterialMarcoSvcToAttach = em.getReference(tbMaterialMarcoSvcCollectionNewTbMaterialMarcoSvcToAttach.getClass(), tbMaterialMarcoSvcCollectionNewTbMaterialMarcoSvcToAttach.getHand());
                attachedTbMaterialMarcoSvcCollectionNew.add(tbMaterialMarcoSvcCollectionNewTbMaterialMarcoSvcToAttach);
            }
            tbMaterialMarcoSvcCollectionNew = attachedTbMaterialMarcoSvcCollectionNew;
            tbMateriais.setTbMaterialMarcoSvcCollection(tbMaterialMarcoSvcCollectionNew);
            Collection<TbOsMateriaisTotal> attachedTbOsMateriaisTotalCollectionNew = new ArrayList<TbOsMateriaisTotal>();
            for (TbOsMateriaisTotal tbOsMateriaisTotalCollectionNewTbOsMateriaisTotalToAttach : tbOsMateriaisTotalCollectionNew) {
                tbOsMateriaisTotalCollectionNewTbOsMateriaisTotalToAttach = em.getReference(tbOsMateriaisTotalCollectionNewTbOsMateriaisTotalToAttach.getClass(), tbOsMateriaisTotalCollectionNewTbOsMateriaisTotalToAttach.getHand());
                attachedTbOsMateriaisTotalCollectionNew.add(tbOsMateriaisTotalCollectionNewTbOsMateriaisTotalToAttach);
            }
            tbOsMateriaisTotalCollectionNew = attachedTbOsMateriaisTotalCollectionNew;
            tbMateriais.setTbOsMateriaisTotalCollection(tbOsMateriaisTotalCollectionNew);
            tbMateriais = em.merge(tbMateriais);
            if (tbUnidadeMedidaHandOld != null && !tbUnidadeMedidaHandOld.equals(tbUnidadeMedidaHandNew)) {
                tbUnidadeMedidaHandOld.getTbMateriaisCollection().remove(tbMateriais);
                tbUnidadeMedidaHandOld = em.merge(tbUnidadeMedidaHandOld);
            }
            if (tbUnidadeMedidaHandNew != null && !tbUnidadeMedidaHandNew.equals(tbUnidadeMedidaHandOld)) {
                tbUnidadeMedidaHandNew.getTbMateriaisCollection().add(tbMateriais);
                tbUnidadeMedidaHandNew = em.merge(tbUnidadeMedidaHandNew);
            }
            for (TbProjetosMateriais tbProjetosMateriaisCollectionNewTbProjetosMateriais : tbProjetosMateriaisCollectionNew) {
                if (!tbProjetosMateriaisCollectionOld.contains(tbProjetosMateriaisCollectionNewTbProjetosMateriais)) {
                    TbMateriais oldTbMateriaisHandOfTbProjetosMateriaisCollectionNewTbProjetosMateriais = tbProjetosMateriaisCollectionNewTbProjetosMateriais.getTbMateriaisHand();
                    tbProjetosMateriaisCollectionNewTbProjetosMateriais.setTbMateriaisHand(tbMateriais);
                    tbProjetosMateriaisCollectionNewTbProjetosMateriais = em.merge(tbProjetosMateriaisCollectionNewTbProjetosMateriais);
                    if (oldTbMateriaisHandOfTbProjetosMateriaisCollectionNewTbProjetosMateriais != null && !oldTbMateriaisHandOfTbProjetosMateriaisCollectionNewTbProjetosMateriais.equals(tbMateriais)) {
                        oldTbMateriaisHandOfTbProjetosMateriaisCollectionNewTbProjetosMateriais.getTbProjetosMateriaisCollection().remove(tbProjetosMateriaisCollectionNewTbProjetosMateriais);
                        oldTbMateriaisHandOfTbProjetosMateriaisCollectionNewTbProjetosMateriais = em.merge(oldTbMateriaisHandOfTbProjetosMateriaisCollectionNewTbProjetosMateriais);
                    }
                }
            }
            for (TbApontamentosMateriais tbApontamentosMateriaisCollectionNewTbApontamentosMateriais : tbApontamentosMateriaisCollectionNew) {
                if (!tbApontamentosMateriaisCollectionOld.contains(tbApontamentosMateriaisCollectionNewTbApontamentosMateriais)) {
                    TbMateriais oldTbMateriaisHandOfTbApontamentosMateriaisCollectionNewTbApontamentosMateriais = tbApontamentosMateriaisCollectionNewTbApontamentosMateriais.getTbMateriaisHand();
                    tbApontamentosMateriaisCollectionNewTbApontamentosMateriais.setTbMateriaisHand(tbMateriais);
                    tbApontamentosMateriaisCollectionNewTbApontamentosMateriais = em.merge(tbApontamentosMateriaisCollectionNewTbApontamentosMateriais);
                    if (oldTbMateriaisHandOfTbApontamentosMateriaisCollectionNewTbApontamentosMateriais != null && !oldTbMateriaisHandOfTbApontamentosMateriaisCollectionNewTbApontamentosMateriais.equals(tbMateriais)) {
                        oldTbMateriaisHandOfTbApontamentosMateriaisCollectionNewTbApontamentosMateriais.getTbApontamentosMateriaisCollection().remove(tbApontamentosMateriaisCollectionNewTbApontamentosMateriais);
                        oldTbMateriaisHandOfTbApontamentosMateriaisCollectionNewTbApontamentosMateriais = em.merge(oldTbMateriaisHandOfTbApontamentosMateriaisCollectionNewTbApontamentosMateriais);
                    }
                }
            }
            for (TbMaterialMarcoSvc tbMaterialMarcoSvcCollectionNewTbMaterialMarcoSvc : tbMaterialMarcoSvcCollectionNew) {
                if (!tbMaterialMarcoSvcCollectionOld.contains(tbMaterialMarcoSvcCollectionNewTbMaterialMarcoSvc)) {
                    TbMateriais oldTbMateriaisHandOfTbMaterialMarcoSvcCollectionNewTbMaterialMarcoSvc = tbMaterialMarcoSvcCollectionNewTbMaterialMarcoSvc.getTbMateriaisHand();
                    tbMaterialMarcoSvcCollectionNewTbMaterialMarcoSvc.setTbMateriaisHand(tbMateriais);
                    tbMaterialMarcoSvcCollectionNewTbMaterialMarcoSvc = em.merge(tbMaterialMarcoSvcCollectionNewTbMaterialMarcoSvc);
                    if (oldTbMateriaisHandOfTbMaterialMarcoSvcCollectionNewTbMaterialMarcoSvc != null && !oldTbMateriaisHandOfTbMaterialMarcoSvcCollectionNewTbMaterialMarcoSvc.equals(tbMateriais)) {
                        oldTbMateriaisHandOfTbMaterialMarcoSvcCollectionNewTbMaterialMarcoSvc.getTbMaterialMarcoSvcCollection().remove(tbMaterialMarcoSvcCollectionNewTbMaterialMarcoSvc);
                        oldTbMateriaisHandOfTbMaterialMarcoSvcCollectionNewTbMaterialMarcoSvc = em.merge(oldTbMateriaisHandOfTbMaterialMarcoSvcCollectionNewTbMaterialMarcoSvc);
                    }
                }
            }
            for (TbOsMateriaisTotal tbOsMateriaisTotalCollectionNewTbOsMateriaisTotal : tbOsMateriaisTotalCollectionNew) {
                if (!tbOsMateriaisTotalCollectionOld.contains(tbOsMateriaisTotalCollectionNewTbOsMateriaisTotal)) {
                    TbMateriais oldTbMateriaisHandOfTbOsMateriaisTotalCollectionNewTbOsMateriaisTotal = tbOsMateriaisTotalCollectionNewTbOsMateriaisTotal.getTbMateriaisHand();
                    tbOsMateriaisTotalCollectionNewTbOsMateriaisTotal.setTbMateriaisHand(tbMateriais);
                    tbOsMateriaisTotalCollectionNewTbOsMateriaisTotal = em.merge(tbOsMateriaisTotalCollectionNewTbOsMateriaisTotal);
                    if (oldTbMateriaisHandOfTbOsMateriaisTotalCollectionNewTbOsMateriaisTotal != null && !oldTbMateriaisHandOfTbOsMateriaisTotalCollectionNewTbOsMateriaisTotal.equals(tbMateriais)) {
                        oldTbMateriaisHandOfTbOsMateriaisTotalCollectionNewTbOsMateriaisTotal.getTbOsMateriaisTotalCollection().remove(tbOsMateriaisTotalCollectionNewTbOsMateriaisTotal);
                        oldTbMateriaisHandOfTbOsMateriaisTotalCollectionNewTbOsMateriaisTotal = em.merge(oldTbMateriaisHandOfTbOsMateriaisTotalCollectionNewTbOsMateriaisTotal);
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
                Integer id = tbMateriais.getHand();
                if (findTbMateriais(id) == null) {
                    throw new NonexistentEntityException("The tbMateriais with id " + id + " no longer exists.");
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
            TbMateriais tbMateriais;
            try {
                tbMateriais = em.getReference(TbMateriais.class, id);
                tbMateriais.getHand();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tbMateriais with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TbProjetosMateriais> tbProjetosMateriaisCollectionOrphanCheck = tbMateriais.getTbProjetosMateriaisCollection();
            for (TbProjetosMateriais tbProjetosMateriaisCollectionOrphanCheckTbProjetosMateriais : tbProjetosMateriaisCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbMateriais (" + tbMateriais + ") cannot be destroyed since the TbProjetosMateriais " + tbProjetosMateriaisCollectionOrphanCheckTbProjetosMateriais + " in its tbProjetosMateriaisCollection field has a non-nullable tbMateriaisHand field.");
            }
            Collection<TbApontamentosMateriais> tbApontamentosMateriaisCollectionOrphanCheck = tbMateriais.getTbApontamentosMateriaisCollection();
            for (TbApontamentosMateriais tbApontamentosMateriaisCollectionOrphanCheckTbApontamentosMateriais : tbApontamentosMateriaisCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbMateriais (" + tbMateriais + ") cannot be destroyed since the TbApontamentosMateriais " + tbApontamentosMateriaisCollectionOrphanCheckTbApontamentosMateriais + " in its tbApontamentosMateriaisCollection field has a non-nullable tbMateriaisHand field.");
            }
            Collection<TbMaterialMarcoSvc> tbMaterialMarcoSvcCollectionOrphanCheck = tbMateriais.getTbMaterialMarcoSvcCollection();
            for (TbMaterialMarcoSvc tbMaterialMarcoSvcCollectionOrphanCheckTbMaterialMarcoSvc : tbMaterialMarcoSvcCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbMateriais (" + tbMateriais + ") cannot be destroyed since the TbMaterialMarcoSvc " + tbMaterialMarcoSvcCollectionOrphanCheckTbMaterialMarcoSvc + " in its tbMaterialMarcoSvcCollection field has a non-nullable tbMateriaisHand field.");
            }
            Collection<TbOsMateriaisTotal> tbOsMateriaisTotalCollectionOrphanCheck = tbMateriais.getTbOsMateriaisTotalCollection();
            for (TbOsMateriaisTotal tbOsMateriaisTotalCollectionOrphanCheckTbOsMateriaisTotal : tbOsMateriaisTotalCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbMateriais (" + tbMateriais + ") cannot be destroyed since the TbOsMateriaisTotal " + tbOsMateriaisTotalCollectionOrphanCheckTbOsMateriaisTotal + " in its tbOsMateriaisTotalCollection field has a non-nullable tbMateriaisHand field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            TbUnidadeMedida tbUnidadeMedidaHand = tbMateriais.getTbUnidadeMedidaHand();
            if (tbUnidadeMedidaHand != null) {
                tbUnidadeMedidaHand.getTbMateriaisCollection().remove(tbMateriais);
                tbUnidadeMedidaHand = em.merge(tbUnidadeMedidaHand);
            }
            em.remove(tbMateriais);
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

    public List<TbMateriais> findTbMateriaisEntities() {
        return findTbMateriaisEntities(true, -1, -1);
    }

    public List<TbMateriais> findTbMateriaisEntities(int maxResults, int firstResult) {
        return findTbMateriaisEntities(false, maxResults, firstResult);
    }

    private List<TbMateriais> findTbMateriaisEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TbMateriais.class));
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

    public TbMateriais findTbMateriais(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbMateriais.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbMateriaisCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TbMateriais> rt = cq.from(TbMateriais.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
