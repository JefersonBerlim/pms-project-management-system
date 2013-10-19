/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Controle.exceptions.IllegalOrphanException;
import Controle.exceptions.NonexistentEntityException;
import Controle.exceptions.PreexistingEntityException;
import Controle.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import Entidades.TbStatus;
import Entidades.TbProjetosServicos;
import Entidades.TbOrdemServico;
import Entidades.TbOsRecursos;
import java.util.ArrayList;
import java.util.Collection;
import Entidades.TbApontamentosRecursos;
import Entidades.TbApontamentosMateriais;
import Entidades.TbOsMateriais;
import Entidades.TbOsServico;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;

/**
 *
 * @author berlim
 */
public class TbOsServicoJpaController implements Serializable {

    public TbOsServicoJpaController() {
        emf = Persistence.createEntityManagerFactory("ProjectManagementSystemPU");
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TbOsServico tbOsServico) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (tbOsServico.getTbOsRecursosCollection() == null) {
            tbOsServico.setTbOsRecursosCollection(new ArrayList<TbOsRecursos>());
        }
        if (tbOsServico.getTbApontamentosRecursosCollection() == null) {
            tbOsServico.setTbApontamentosRecursosCollection(new ArrayList<TbApontamentosRecursos>());
        }
        if (tbOsServico.getTbApontamentosMateriaisCollection() == null) {
            tbOsServico.setTbApontamentosMateriaisCollection(new ArrayList<TbApontamentosMateriais>());
        }
        if (tbOsServico.getTbOsMateriaisCollection() == null) {
            tbOsServico.setTbOsMateriaisCollection(new ArrayList<TbOsMateriais>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbStatus tbStatusHand = tbOsServico.getTbStatusHand();
            if (tbStatusHand != null) {
                tbStatusHand = em.getReference(tbStatusHand.getClass(), tbStatusHand.getHand());
                tbOsServico.setTbStatusHand(tbStatusHand);
            }
            TbProjetosServicos tbProjetosServicosHand = tbOsServico.getTbProjetosServicosHand();
            if (tbProjetosServicosHand != null) {
                tbProjetosServicosHand = em.getReference(tbProjetosServicosHand.getClass(), tbProjetosServicosHand.getHand());
                tbOsServico.setTbProjetosServicosHand(tbProjetosServicosHand);
            }
            TbOrdemServico tbOrdemServicoHand = tbOsServico.getTbOrdemServicoHand();
            if (tbOrdemServicoHand != null) {
                tbOrdemServicoHand = em.getReference(tbOrdemServicoHand.getClass(), tbOrdemServicoHand.getHand());
                tbOsServico.setTbOrdemServicoHand(tbOrdemServicoHand);
            }
            Collection<TbOsRecursos> attachedTbOsRecursosCollection = new ArrayList<TbOsRecursos>();
            for (TbOsRecursos tbOsRecursosCollectionTbOsRecursosToAttach : tbOsServico.getTbOsRecursosCollection()) {
                tbOsRecursosCollectionTbOsRecursosToAttach = em.getReference(tbOsRecursosCollectionTbOsRecursosToAttach.getClass(), tbOsRecursosCollectionTbOsRecursosToAttach.getHand());
                attachedTbOsRecursosCollection.add(tbOsRecursosCollectionTbOsRecursosToAttach);
            }
            tbOsServico.setTbOsRecursosCollection(attachedTbOsRecursosCollection);
            Collection<TbApontamentosRecursos> attachedTbApontamentosRecursosCollection = new ArrayList<TbApontamentosRecursos>();
            for (TbApontamentosRecursos tbApontamentosRecursosCollectionTbApontamentosRecursosToAttach : tbOsServico.getTbApontamentosRecursosCollection()) {
                tbApontamentosRecursosCollectionTbApontamentosRecursosToAttach = em.getReference(tbApontamentosRecursosCollectionTbApontamentosRecursosToAttach.getClass(), tbApontamentosRecursosCollectionTbApontamentosRecursosToAttach.getHand());
                attachedTbApontamentosRecursosCollection.add(tbApontamentosRecursosCollectionTbApontamentosRecursosToAttach);
            }
            tbOsServico.setTbApontamentosRecursosCollection(attachedTbApontamentosRecursosCollection);
            Collection<TbApontamentosMateriais> attachedTbApontamentosMateriaisCollection = new ArrayList<TbApontamentosMateriais>();
            for (TbApontamentosMateriais tbApontamentosMateriaisCollectionTbApontamentosMateriaisToAttach : tbOsServico.getTbApontamentosMateriaisCollection()) {
                tbApontamentosMateriaisCollectionTbApontamentosMateriaisToAttach = em.getReference(tbApontamentosMateriaisCollectionTbApontamentosMateriaisToAttach.getClass(), tbApontamentosMateriaisCollectionTbApontamentosMateriaisToAttach.getHand());
                attachedTbApontamentosMateriaisCollection.add(tbApontamentosMateriaisCollectionTbApontamentosMateriaisToAttach);
            }
            tbOsServico.setTbApontamentosMateriaisCollection(attachedTbApontamentosMateriaisCollection);
            Collection<TbOsMateriais> attachedTbOsMateriaisCollection = new ArrayList<TbOsMateriais>();
            for (TbOsMateriais tbOsMateriaisCollectionTbOsMateriaisToAttach : tbOsServico.getTbOsMateriaisCollection()) {
                tbOsMateriaisCollectionTbOsMateriaisToAttach = em.getReference(tbOsMateriaisCollectionTbOsMateriaisToAttach.getClass(), tbOsMateriaisCollectionTbOsMateriaisToAttach.getHand());
                attachedTbOsMateriaisCollection.add(tbOsMateriaisCollectionTbOsMateriaisToAttach);
            }
            tbOsServico.setTbOsMateriaisCollection(attachedTbOsMateriaisCollection);
            em.persist(tbOsServico);
            if (tbStatusHand != null) {
                tbStatusHand.getTbOsServicoCollection().add(tbOsServico);
                tbStatusHand = em.merge(tbStatusHand);
            }
            if (tbProjetosServicosHand != null) {
                tbProjetosServicosHand.getTbOsServicoCollection().add(tbOsServico);
                tbProjetosServicosHand = em.merge(tbProjetosServicosHand);
            }
            if (tbOrdemServicoHand != null) {
                tbOrdemServicoHand.getTbOsServicoCollection().add(tbOsServico);
                tbOrdemServicoHand = em.merge(tbOrdemServicoHand);
            }
            for (TbOsRecursos tbOsRecursosCollectionTbOsRecursos : tbOsServico.getTbOsRecursosCollection()) {
                TbOsServico oldTbOsServicoHandOfTbOsRecursosCollectionTbOsRecursos = tbOsRecursosCollectionTbOsRecursos.getTbOsServicoHand();
                tbOsRecursosCollectionTbOsRecursos.setTbOsServicoHand(tbOsServico);
                tbOsRecursosCollectionTbOsRecursos = em.merge(tbOsRecursosCollectionTbOsRecursos);
                if (oldTbOsServicoHandOfTbOsRecursosCollectionTbOsRecursos != null) {
                    oldTbOsServicoHandOfTbOsRecursosCollectionTbOsRecursos.getTbOsRecursosCollection().remove(tbOsRecursosCollectionTbOsRecursos);
                    oldTbOsServicoHandOfTbOsRecursosCollectionTbOsRecursos = em.merge(oldTbOsServicoHandOfTbOsRecursosCollectionTbOsRecursos);
                }
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
            for (TbApontamentosMateriais tbApontamentosMateriaisCollectionTbApontamentosMateriais : tbOsServico.getTbApontamentosMateriaisCollection()) {
                TbOsServico oldTbOsServicoHandOfTbApontamentosMateriaisCollectionTbApontamentosMateriais = tbApontamentosMateriaisCollectionTbApontamentosMateriais.getTbOsServicoHand();
                tbApontamentosMateriaisCollectionTbApontamentosMateriais.setTbOsServicoHand(tbOsServico);
                tbApontamentosMateriaisCollectionTbApontamentosMateriais = em.merge(tbApontamentosMateriaisCollectionTbApontamentosMateriais);
                if (oldTbOsServicoHandOfTbApontamentosMateriaisCollectionTbApontamentosMateriais != null) {
                    oldTbOsServicoHandOfTbApontamentosMateriaisCollectionTbApontamentosMateriais.getTbApontamentosMateriaisCollection().remove(tbApontamentosMateriaisCollectionTbApontamentosMateriais);
                    oldTbOsServicoHandOfTbApontamentosMateriaisCollectionTbApontamentosMateriais = em.merge(oldTbOsServicoHandOfTbApontamentosMateriaisCollectionTbApontamentosMateriais);
                }
            }
            for (TbOsMateriais tbOsMateriaisCollectionTbOsMateriais : tbOsServico.getTbOsMateriaisCollection()) {
                TbOsServico oldTbOsServicoHandOfTbOsMateriaisCollectionTbOsMateriais = tbOsMateriaisCollectionTbOsMateriais.getTbOsServicoHand();
                tbOsMateriaisCollectionTbOsMateriais.setTbOsServicoHand(tbOsServico);
                tbOsMateriaisCollectionTbOsMateriais = em.merge(tbOsMateriaisCollectionTbOsMateriais);
                if (oldTbOsServicoHandOfTbOsMateriaisCollectionTbOsMateriais != null) {
                    oldTbOsServicoHandOfTbOsMateriaisCollectionTbOsMateriais.getTbOsMateriaisCollection().remove(tbOsMateriaisCollectionTbOsMateriais);
                    oldTbOsServicoHandOfTbOsMateriaisCollectionTbOsMateriais = em.merge(oldTbOsServicoHandOfTbOsMateriaisCollectionTbOsMateriais);
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
            TbStatus tbStatusHandOld = persistentTbOsServico.getTbStatusHand();
            TbStatus tbStatusHandNew = tbOsServico.getTbStatusHand();
            TbProjetosServicos tbProjetosServicosHandOld = persistentTbOsServico.getTbProjetosServicosHand();
            TbProjetosServicos tbProjetosServicosHandNew = tbOsServico.getTbProjetosServicosHand();
            TbOrdemServico tbOrdemServicoHandOld = persistentTbOsServico.getTbOrdemServicoHand();
            TbOrdemServico tbOrdemServicoHandNew = tbOsServico.getTbOrdemServicoHand();
            Collection<TbOsRecursos> tbOsRecursosCollectionOld = persistentTbOsServico.getTbOsRecursosCollection();
            Collection<TbOsRecursos> tbOsRecursosCollectionNew = tbOsServico.getTbOsRecursosCollection();
            Collection<TbApontamentosRecursos> tbApontamentosRecursosCollectionOld = persistentTbOsServico.getTbApontamentosRecursosCollection();
            Collection<TbApontamentosRecursos> tbApontamentosRecursosCollectionNew = tbOsServico.getTbApontamentosRecursosCollection();
            Collection<TbApontamentosMateriais> tbApontamentosMateriaisCollectionOld = persistentTbOsServico.getTbApontamentosMateriaisCollection();
            Collection<TbApontamentosMateriais> tbApontamentosMateriaisCollectionNew = tbOsServico.getTbApontamentosMateriaisCollection();
            Collection<TbOsMateriais> tbOsMateriaisCollectionOld = persistentTbOsServico.getTbOsMateriaisCollection();
            Collection<TbOsMateriais> tbOsMateriaisCollectionNew = tbOsServico.getTbOsMateriaisCollection();
            List<String> illegalOrphanMessages = null;
            for (TbOsRecursos tbOsRecursosCollectionOldTbOsRecursos : tbOsRecursosCollectionOld) {
                if (!tbOsRecursosCollectionNew.contains(tbOsRecursosCollectionOldTbOsRecursos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TbOsRecursos " + tbOsRecursosCollectionOldTbOsRecursos + " since its tbOsServicoHand field is not nullable.");
                }
            }
            for (TbApontamentosRecursos tbApontamentosRecursosCollectionOldTbApontamentosRecursos : tbApontamentosRecursosCollectionOld) {
                if (!tbApontamentosRecursosCollectionNew.contains(tbApontamentosRecursosCollectionOldTbApontamentosRecursos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TbApontamentosRecursos " + tbApontamentosRecursosCollectionOldTbApontamentosRecursos + " since its tbOsServicoHand field is not nullable.");
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
            for (TbOsMateriais tbOsMateriaisCollectionOldTbOsMateriais : tbOsMateriaisCollectionOld) {
                if (!tbOsMateriaisCollectionNew.contains(tbOsMateriaisCollectionOldTbOsMateriais)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TbOsMateriais " + tbOsMateriaisCollectionOldTbOsMateriais + " since its tbOsServicoHand field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tbStatusHandNew != null) {
                tbStatusHandNew = em.getReference(tbStatusHandNew.getClass(), tbStatusHandNew.getHand());
                tbOsServico.setTbStatusHand(tbStatusHandNew);
            }
            if (tbProjetosServicosHandNew != null) {
                tbProjetosServicosHandNew = em.getReference(tbProjetosServicosHandNew.getClass(), tbProjetosServicosHandNew.getHand());
                tbOsServico.setTbProjetosServicosHand(tbProjetosServicosHandNew);
            }
            if (tbOrdemServicoHandNew != null) {
                tbOrdemServicoHandNew = em.getReference(tbOrdemServicoHandNew.getClass(), tbOrdemServicoHandNew.getHand());
                tbOsServico.setTbOrdemServicoHand(tbOrdemServicoHandNew);
            }
            Collection<TbOsRecursos> attachedTbOsRecursosCollectionNew = new ArrayList<TbOsRecursos>();
            for (TbOsRecursos tbOsRecursosCollectionNewTbOsRecursosToAttach : tbOsRecursosCollectionNew) {
                tbOsRecursosCollectionNewTbOsRecursosToAttach = em.getReference(tbOsRecursosCollectionNewTbOsRecursosToAttach.getClass(), tbOsRecursosCollectionNewTbOsRecursosToAttach.getHand());
                attachedTbOsRecursosCollectionNew.add(tbOsRecursosCollectionNewTbOsRecursosToAttach);
            }
            tbOsRecursosCollectionNew = attachedTbOsRecursosCollectionNew;
            tbOsServico.setTbOsRecursosCollection(tbOsRecursosCollectionNew);
            Collection<TbApontamentosRecursos> attachedTbApontamentosRecursosCollectionNew = new ArrayList<TbApontamentosRecursos>();
            for (TbApontamentosRecursos tbApontamentosRecursosCollectionNewTbApontamentosRecursosToAttach : tbApontamentosRecursosCollectionNew) {
                tbApontamentosRecursosCollectionNewTbApontamentosRecursosToAttach = em.getReference(tbApontamentosRecursosCollectionNewTbApontamentosRecursosToAttach.getClass(), tbApontamentosRecursosCollectionNewTbApontamentosRecursosToAttach.getHand());
                attachedTbApontamentosRecursosCollectionNew.add(tbApontamentosRecursosCollectionNewTbApontamentosRecursosToAttach);
            }
            tbApontamentosRecursosCollectionNew = attachedTbApontamentosRecursosCollectionNew;
            tbOsServico.setTbApontamentosRecursosCollection(tbApontamentosRecursosCollectionNew);
            Collection<TbApontamentosMateriais> attachedTbApontamentosMateriaisCollectionNew = new ArrayList<TbApontamentosMateriais>();
            for (TbApontamentosMateriais tbApontamentosMateriaisCollectionNewTbApontamentosMateriaisToAttach : tbApontamentosMateriaisCollectionNew) {
                tbApontamentosMateriaisCollectionNewTbApontamentosMateriaisToAttach = em.getReference(tbApontamentosMateriaisCollectionNewTbApontamentosMateriaisToAttach.getClass(), tbApontamentosMateriaisCollectionNewTbApontamentosMateriaisToAttach.getHand());
                attachedTbApontamentosMateriaisCollectionNew.add(tbApontamentosMateriaisCollectionNewTbApontamentosMateriaisToAttach);
            }
            tbApontamentosMateriaisCollectionNew = attachedTbApontamentosMateriaisCollectionNew;
            tbOsServico.setTbApontamentosMateriaisCollection(tbApontamentosMateriaisCollectionNew);
            Collection<TbOsMateriais> attachedTbOsMateriaisCollectionNew = new ArrayList<TbOsMateriais>();
            for (TbOsMateriais tbOsMateriaisCollectionNewTbOsMateriaisToAttach : tbOsMateriaisCollectionNew) {
                tbOsMateriaisCollectionNewTbOsMateriaisToAttach = em.getReference(tbOsMateriaisCollectionNewTbOsMateriaisToAttach.getClass(), tbOsMateriaisCollectionNewTbOsMateriaisToAttach.getHand());
                attachedTbOsMateriaisCollectionNew.add(tbOsMateriaisCollectionNewTbOsMateriaisToAttach);
            }
            tbOsMateriaisCollectionNew = attachedTbOsMateriaisCollectionNew;
            tbOsServico.setTbOsMateriaisCollection(tbOsMateriaisCollectionNew);
            tbOsServico = em.merge(tbOsServico);
            if (tbStatusHandOld != null && !tbStatusHandOld.equals(tbStatusHandNew)) {
                tbStatusHandOld.getTbOsServicoCollection().remove(tbOsServico);
                tbStatusHandOld = em.merge(tbStatusHandOld);
            }
            if (tbStatusHandNew != null && !tbStatusHandNew.equals(tbStatusHandOld)) {
                tbStatusHandNew.getTbOsServicoCollection().add(tbOsServico);
                tbStatusHandNew = em.merge(tbStatusHandNew);
            }
            if (tbProjetosServicosHandOld != null && !tbProjetosServicosHandOld.equals(tbProjetosServicosHandNew)) {
                tbProjetosServicosHandOld.getTbOsServicoCollection().remove(tbOsServico);
                tbProjetosServicosHandOld = em.merge(tbProjetosServicosHandOld);
            }
            if (tbProjetosServicosHandNew != null && !tbProjetosServicosHandNew.equals(tbProjetosServicosHandOld)) {
                tbProjetosServicosHandNew.getTbOsServicoCollection().add(tbOsServico);
                tbProjetosServicosHandNew = em.merge(tbProjetosServicosHandNew);
            }
            if (tbOrdemServicoHandOld != null && !tbOrdemServicoHandOld.equals(tbOrdemServicoHandNew)) {
                tbOrdemServicoHandOld.getTbOsServicoCollection().remove(tbOsServico);
                tbOrdemServicoHandOld = em.merge(tbOrdemServicoHandOld);
            }
            if (tbOrdemServicoHandNew != null && !tbOrdemServicoHandNew.equals(tbOrdemServicoHandOld)) {
                tbOrdemServicoHandNew.getTbOsServicoCollection().add(tbOsServico);
                tbOrdemServicoHandNew = em.merge(tbOrdemServicoHandNew);
            }
            for (TbOsRecursos tbOsRecursosCollectionNewTbOsRecursos : tbOsRecursosCollectionNew) {
                if (!tbOsRecursosCollectionOld.contains(tbOsRecursosCollectionNewTbOsRecursos)) {
                    TbOsServico oldTbOsServicoHandOfTbOsRecursosCollectionNewTbOsRecursos = tbOsRecursosCollectionNewTbOsRecursos.getTbOsServicoHand();
                    tbOsRecursosCollectionNewTbOsRecursos.setTbOsServicoHand(tbOsServico);
                    tbOsRecursosCollectionNewTbOsRecursos = em.merge(tbOsRecursosCollectionNewTbOsRecursos);
                    if (oldTbOsServicoHandOfTbOsRecursosCollectionNewTbOsRecursos != null && !oldTbOsServicoHandOfTbOsRecursosCollectionNewTbOsRecursos.equals(tbOsServico)) {
                        oldTbOsServicoHandOfTbOsRecursosCollectionNewTbOsRecursos.getTbOsRecursosCollection().remove(tbOsRecursosCollectionNewTbOsRecursos);
                        oldTbOsServicoHandOfTbOsRecursosCollectionNewTbOsRecursos = em.merge(oldTbOsServicoHandOfTbOsRecursosCollectionNewTbOsRecursos);
                    }
                }
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
            for (TbOsMateriais tbOsMateriaisCollectionNewTbOsMateriais : tbOsMateriaisCollectionNew) {
                if (!tbOsMateriaisCollectionOld.contains(tbOsMateriaisCollectionNewTbOsMateriais)) {
                    TbOsServico oldTbOsServicoHandOfTbOsMateriaisCollectionNewTbOsMateriais = tbOsMateriaisCollectionNewTbOsMateriais.getTbOsServicoHand();
                    tbOsMateriaisCollectionNewTbOsMateriais.setTbOsServicoHand(tbOsServico);
                    tbOsMateriaisCollectionNewTbOsMateriais = em.merge(tbOsMateriaisCollectionNewTbOsMateriais);
                    if (oldTbOsServicoHandOfTbOsMateriaisCollectionNewTbOsMateriais != null && !oldTbOsServicoHandOfTbOsMateriaisCollectionNewTbOsMateriais.equals(tbOsServico)) {
                        oldTbOsServicoHandOfTbOsMateriaisCollectionNewTbOsMateriais.getTbOsMateriaisCollection().remove(tbOsMateriaisCollectionNewTbOsMateriais);
                        oldTbOsServicoHandOfTbOsMateriaisCollectionNewTbOsMateriais = em.merge(oldTbOsServicoHandOfTbOsMateriaisCollectionNewTbOsMateriais);
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
            Collection<TbOsRecursos> tbOsRecursosCollectionOrphanCheck = tbOsServico.getTbOsRecursosCollection();
            for (TbOsRecursos tbOsRecursosCollectionOrphanCheckTbOsRecursos : tbOsRecursosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbOsServico (" + tbOsServico + ") cannot be destroyed since the TbOsRecursos " + tbOsRecursosCollectionOrphanCheckTbOsRecursos + " in its tbOsRecursosCollection field has a non-nullable tbOsServicoHand field.");
            }
            Collection<TbApontamentosRecursos> tbApontamentosRecursosCollectionOrphanCheck = tbOsServico.getTbApontamentosRecursosCollection();
            for (TbApontamentosRecursos tbApontamentosRecursosCollectionOrphanCheckTbApontamentosRecursos : tbApontamentosRecursosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbOsServico (" + tbOsServico + ") cannot be destroyed since the TbApontamentosRecursos " + tbApontamentosRecursosCollectionOrphanCheckTbApontamentosRecursos + " in its tbApontamentosRecursosCollection field has a non-nullable tbOsServicoHand field.");
            }
            Collection<TbApontamentosMateriais> tbApontamentosMateriaisCollectionOrphanCheck = tbOsServico.getTbApontamentosMateriaisCollection();
            for (TbApontamentosMateriais tbApontamentosMateriaisCollectionOrphanCheckTbApontamentosMateriais : tbApontamentosMateriaisCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbOsServico (" + tbOsServico + ") cannot be destroyed since the TbApontamentosMateriais " + tbApontamentosMateriaisCollectionOrphanCheckTbApontamentosMateriais + " in its tbApontamentosMateriaisCollection field has a non-nullable tbOsServicoHand field.");
            }
            Collection<TbOsMateriais> tbOsMateriaisCollectionOrphanCheck = tbOsServico.getTbOsMateriaisCollection();
            for (TbOsMateriais tbOsMateriaisCollectionOrphanCheckTbOsMateriais : tbOsMateriaisCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbOsServico (" + tbOsServico + ") cannot be destroyed since the TbOsMateriais " + tbOsMateriaisCollectionOrphanCheckTbOsMateriais + " in its tbOsMateriaisCollection field has a non-nullable tbOsServicoHand field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            TbStatus tbStatusHand = tbOsServico.getTbStatusHand();
            if (tbStatusHand != null) {
                tbStatusHand.getTbOsServicoCollection().remove(tbOsServico);
                tbStatusHand = em.merge(tbStatusHand);
            }
            TbProjetosServicos tbProjetosServicosHand = tbOsServico.getTbProjetosServicosHand();
            if (tbProjetosServicosHand != null) {
                tbProjetosServicosHand.getTbOsServicoCollection().remove(tbOsServico);
                tbProjetosServicosHand = em.merge(tbProjetosServicosHand);
            }
            TbOrdemServico tbOrdemServicoHand = tbOsServico.getTbOrdemServicoHand();
            if (tbOrdemServicoHand != null) {
                tbOrdemServicoHand.getTbOsServicoCollection().remove(tbOsServico);
                tbOrdemServicoHand = em.merge(tbOrdemServicoHand);
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
            Query q = em.createQuery("select object(o) from TbOsServico as o");
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
            Query q = em.createQuery("select count(o) from TbOsServico as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
