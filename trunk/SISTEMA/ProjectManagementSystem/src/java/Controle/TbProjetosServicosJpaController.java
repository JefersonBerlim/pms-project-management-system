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
import Entidades.TbServicos;
import Entidades.TbProjetoMarcos;
import Entidades.TbProjetosRecursos;
import java.util.ArrayList;
import java.util.Collection;
import Entidades.TbOsServico;
import Entidades.TbProjetosMateriais;
import Entidades.TbProjetosServicos;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;

/**
 *
 * @author berlim
 */
public class TbProjetosServicosJpaController implements Serializable {

    public TbProjetosServicosJpaController() {
        emf = Persistence.createEntityManagerFactory("ProjectManagementSystemPU");
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TbProjetosServicos tbProjetosServicos) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (tbProjetosServicos.getTbProjetosRecursosCollection() == null) {
            tbProjetosServicos.setTbProjetosRecursosCollection(new ArrayList<TbProjetosRecursos>());
        }
        if (tbProjetosServicos.getTbOsServicoCollection() == null) {
            tbProjetosServicos.setTbOsServicoCollection(new ArrayList<TbOsServico>());
        }
        if (tbProjetosServicos.getTbProjetosMateriaisCollection() == null) {
            tbProjetosServicos.setTbProjetosMateriaisCollection(new ArrayList<TbProjetosMateriais>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbServicos servicoDependente = tbProjetosServicos.getServicoDependente();
            if (servicoDependente != null) {
                servicoDependente = em.getReference(servicoDependente.getClass(), servicoDependente.getHand());
                tbProjetosServicos.setServicoDependente(servicoDependente);
            }
            TbServicos tbServicosHand = tbProjetosServicos.getTbServicosHand();
            if (tbServicosHand != null) {
                tbServicosHand = em.getReference(tbServicosHand.getClass(), tbServicosHand.getHand());
                tbProjetosServicos.setTbServicosHand(tbServicosHand);
            }
            TbProjetoMarcos tbProjetoMarcosHand = tbProjetosServicos.getTbProjetoMarcosHand();
            if (tbProjetoMarcosHand != null) {
                tbProjetoMarcosHand = em.getReference(tbProjetoMarcosHand.getClass(), tbProjetoMarcosHand.getHand());
                tbProjetosServicos.setTbProjetoMarcosHand(tbProjetoMarcosHand);
            }
            Collection<TbProjetosRecursos> attachedTbProjetosRecursosCollection = new ArrayList<TbProjetosRecursos>();
            for (TbProjetosRecursos tbProjetosRecursosCollectionTbProjetosRecursosToAttach : tbProjetosServicos.getTbProjetosRecursosCollection()) {
                tbProjetosRecursosCollectionTbProjetosRecursosToAttach = em.getReference(tbProjetosRecursosCollectionTbProjetosRecursosToAttach.getClass(), tbProjetosRecursosCollectionTbProjetosRecursosToAttach.getHand());
                attachedTbProjetosRecursosCollection.add(tbProjetosRecursosCollectionTbProjetosRecursosToAttach);
            }
            tbProjetosServicos.setTbProjetosRecursosCollection(attachedTbProjetosRecursosCollection);
            Collection<TbOsServico> attachedTbOsServicoCollection = new ArrayList<TbOsServico>();
            for (TbOsServico tbOsServicoCollectionTbOsServicoToAttach : tbProjetosServicos.getTbOsServicoCollection()) {
                tbOsServicoCollectionTbOsServicoToAttach = em.getReference(tbOsServicoCollectionTbOsServicoToAttach.getClass(), tbOsServicoCollectionTbOsServicoToAttach.getHand());
                attachedTbOsServicoCollection.add(tbOsServicoCollectionTbOsServicoToAttach);
            }
            tbProjetosServicos.setTbOsServicoCollection(attachedTbOsServicoCollection);
            Collection<TbProjetosMateriais> attachedTbProjetosMateriaisCollection = new ArrayList<TbProjetosMateriais>();
            for (TbProjetosMateriais tbProjetosMateriaisCollectionTbProjetosMateriaisToAttach : tbProjetosServicos.getTbProjetosMateriaisCollection()) {
                tbProjetosMateriaisCollectionTbProjetosMateriaisToAttach = em.getReference(tbProjetosMateriaisCollectionTbProjetosMateriaisToAttach.getClass(), tbProjetosMateriaisCollectionTbProjetosMateriaisToAttach.getHand());
                attachedTbProjetosMateriaisCollection.add(tbProjetosMateriaisCollectionTbProjetosMateriaisToAttach);
            }
            tbProjetosServicos.setTbProjetosMateriaisCollection(attachedTbProjetosMateriaisCollection);
            em.persist(tbProjetosServicos);
            if (servicoDependente != null) {
                servicoDependente.getTbProjetosServicosCollection().add(tbProjetosServicos);
                servicoDependente = em.merge(servicoDependente);
            }
            if (tbServicosHand != null) {
                tbServicosHand.getTbProjetosServicosCollection().add(tbProjetosServicos);
                tbServicosHand = em.merge(tbServicosHand);
            }
            if (tbProjetoMarcosHand != null) {
                tbProjetoMarcosHand.getTbProjetosServicosCollection().add(tbProjetosServicos);
                tbProjetoMarcosHand = em.merge(tbProjetoMarcosHand);
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
            for (TbOsServico tbOsServicoCollectionTbOsServico : tbProjetosServicos.getTbOsServicoCollection()) {
                TbProjetosServicos oldTbProjetosServicosHandOfTbOsServicoCollectionTbOsServico = tbOsServicoCollectionTbOsServico.getTbProjetosServicosHand();
                tbOsServicoCollectionTbOsServico.setTbProjetosServicosHand(tbProjetosServicos);
                tbOsServicoCollectionTbOsServico = em.merge(tbOsServicoCollectionTbOsServico);
                if (oldTbProjetosServicosHandOfTbOsServicoCollectionTbOsServico != null) {
                    oldTbProjetosServicosHandOfTbOsServicoCollectionTbOsServico.getTbOsServicoCollection().remove(tbOsServicoCollectionTbOsServico);
                    oldTbProjetosServicosHandOfTbOsServicoCollectionTbOsServico = em.merge(oldTbProjetosServicosHandOfTbOsServicoCollectionTbOsServico);
                }
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
            TbServicos servicoDependenteOld = persistentTbProjetosServicos.getServicoDependente();
            TbServicos servicoDependenteNew = tbProjetosServicos.getServicoDependente();
            TbServicos tbServicosHandOld = persistentTbProjetosServicos.getTbServicosHand();
            TbServicos tbServicosHandNew = tbProjetosServicos.getTbServicosHand();
            TbProjetoMarcos tbProjetoMarcosHandOld = persistentTbProjetosServicos.getTbProjetoMarcosHand();
            TbProjetoMarcos tbProjetoMarcosHandNew = tbProjetosServicos.getTbProjetoMarcosHand();
            Collection<TbProjetosRecursos> tbProjetosRecursosCollectionOld = persistentTbProjetosServicos.getTbProjetosRecursosCollection();
            Collection<TbProjetosRecursos> tbProjetosRecursosCollectionNew = tbProjetosServicos.getTbProjetosRecursosCollection();
            Collection<TbOsServico> tbOsServicoCollectionOld = persistentTbProjetosServicos.getTbOsServicoCollection();
            Collection<TbOsServico> tbOsServicoCollectionNew = tbProjetosServicos.getTbOsServicoCollection();
            Collection<TbProjetosMateriais> tbProjetosMateriaisCollectionOld = persistentTbProjetosServicos.getTbProjetosMateriaisCollection();
            Collection<TbProjetosMateriais> tbProjetosMateriaisCollectionNew = tbProjetosServicos.getTbProjetosMateriaisCollection();
            List<String> illegalOrphanMessages = null;
            for (TbProjetosRecursos tbProjetosRecursosCollectionOldTbProjetosRecursos : tbProjetosRecursosCollectionOld) {
                if (!tbProjetosRecursosCollectionNew.contains(tbProjetosRecursosCollectionOldTbProjetosRecursos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TbProjetosRecursos " + tbProjetosRecursosCollectionOldTbProjetosRecursos + " since its tbProjetosServicosHand field is not nullable.");
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
            for (TbProjetosMateriais tbProjetosMateriaisCollectionOldTbProjetosMateriais : tbProjetosMateriaisCollectionOld) {
                if (!tbProjetosMateriaisCollectionNew.contains(tbProjetosMateriaisCollectionOldTbProjetosMateriais)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TbProjetosMateriais " + tbProjetosMateriaisCollectionOldTbProjetosMateriais + " since its tbProjetosServicosHand field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (servicoDependenteNew != null) {
                servicoDependenteNew = em.getReference(servicoDependenteNew.getClass(), servicoDependenteNew.getHand());
                tbProjetosServicos.setServicoDependente(servicoDependenteNew);
            }
            if (tbServicosHandNew != null) {
                tbServicosHandNew = em.getReference(tbServicosHandNew.getClass(), tbServicosHandNew.getHand());
                tbProjetosServicos.setTbServicosHand(tbServicosHandNew);
            }
            if (tbProjetoMarcosHandNew != null) {
                tbProjetoMarcosHandNew = em.getReference(tbProjetoMarcosHandNew.getClass(), tbProjetoMarcosHandNew.getHand());
                tbProjetosServicos.setTbProjetoMarcosHand(tbProjetoMarcosHandNew);
            }
            Collection<TbProjetosRecursos> attachedTbProjetosRecursosCollectionNew = new ArrayList<TbProjetosRecursos>();
            for (TbProjetosRecursos tbProjetosRecursosCollectionNewTbProjetosRecursosToAttach : tbProjetosRecursosCollectionNew) {
                tbProjetosRecursosCollectionNewTbProjetosRecursosToAttach = em.getReference(tbProjetosRecursosCollectionNewTbProjetosRecursosToAttach.getClass(), tbProjetosRecursosCollectionNewTbProjetosRecursosToAttach.getHand());
                attachedTbProjetosRecursosCollectionNew.add(tbProjetosRecursosCollectionNewTbProjetosRecursosToAttach);
            }
            tbProjetosRecursosCollectionNew = attachedTbProjetosRecursosCollectionNew;
            tbProjetosServicos.setTbProjetosRecursosCollection(tbProjetosRecursosCollectionNew);
            Collection<TbOsServico> attachedTbOsServicoCollectionNew = new ArrayList<TbOsServico>();
            for (TbOsServico tbOsServicoCollectionNewTbOsServicoToAttach : tbOsServicoCollectionNew) {
                tbOsServicoCollectionNewTbOsServicoToAttach = em.getReference(tbOsServicoCollectionNewTbOsServicoToAttach.getClass(), tbOsServicoCollectionNewTbOsServicoToAttach.getHand());
                attachedTbOsServicoCollectionNew.add(tbOsServicoCollectionNewTbOsServicoToAttach);
            }
            tbOsServicoCollectionNew = attachedTbOsServicoCollectionNew;
            tbProjetosServicos.setTbOsServicoCollection(tbOsServicoCollectionNew);
            Collection<TbProjetosMateriais> attachedTbProjetosMateriaisCollectionNew = new ArrayList<TbProjetosMateriais>();
            for (TbProjetosMateriais tbProjetosMateriaisCollectionNewTbProjetosMateriaisToAttach : tbProjetosMateriaisCollectionNew) {
                tbProjetosMateriaisCollectionNewTbProjetosMateriaisToAttach = em.getReference(tbProjetosMateriaisCollectionNewTbProjetosMateriaisToAttach.getClass(), tbProjetosMateriaisCollectionNewTbProjetosMateriaisToAttach.getHand());
                attachedTbProjetosMateriaisCollectionNew.add(tbProjetosMateriaisCollectionNewTbProjetosMateriaisToAttach);
            }
            tbProjetosMateriaisCollectionNew = attachedTbProjetosMateriaisCollectionNew;
            tbProjetosServicos.setTbProjetosMateriaisCollection(tbProjetosMateriaisCollectionNew);
            tbProjetosServicos = em.merge(tbProjetosServicos);
            if (servicoDependenteOld != null && !servicoDependenteOld.equals(servicoDependenteNew)) {
                servicoDependenteOld.getTbProjetosServicosCollection().remove(tbProjetosServicos);
                servicoDependenteOld = em.merge(servicoDependenteOld);
            }
            if (servicoDependenteNew != null && !servicoDependenteNew.equals(servicoDependenteOld)) {
                servicoDependenteNew.getTbProjetosServicosCollection().add(tbProjetosServicos);
                servicoDependenteNew = em.merge(servicoDependenteNew);
            }
            if (tbServicosHandOld != null && !tbServicosHandOld.equals(tbServicosHandNew)) {
                tbServicosHandOld.getTbProjetosServicosCollection().remove(tbProjetosServicos);
                tbServicosHandOld = em.merge(tbServicosHandOld);
            }
            if (tbServicosHandNew != null && !tbServicosHandNew.equals(tbServicosHandOld)) {
                tbServicosHandNew.getTbProjetosServicosCollection().add(tbProjetosServicos);
                tbServicosHandNew = em.merge(tbServicosHandNew);
            }
            if (tbProjetoMarcosHandOld != null && !tbProjetoMarcosHandOld.equals(tbProjetoMarcosHandNew)) {
                tbProjetoMarcosHandOld.getTbProjetosServicosCollection().remove(tbProjetosServicos);
                tbProjetoMarcosHandOld = em.merge(tbProjetoMarcosHandOld);
            }
            if (tbProjetoMarcosHandNew != null && !tbProjetoMarcosHandNew.equals(tbProjetoMarcosHandOld)) {
                tbProjetoMarcosHandNew.getTbProjetosServicosCollection().add(tbProjetosServicos);
                tbProjetoMarcosHandNew = em.merge(tbProjetoMarcosHandNew);
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
            Collection<TbProjetosRecursos> tbProjetosRecursosCollectionOrphanCheck = tbProjetosServicos.getTbProjetosRecursosCollection();
            for (TbProjetosRecursos tbProjetosRecursosCollectionOrphanCheckTbProjetosRecursos : tbProjetosRecursosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbProjetosServicos (" + tbProjetosServicos + ") cannot be destroyed since the TbProjetosRecursos " + tbProjetosRecursosCollectionOrphanCheckTbProjetosRecursos + " in its tbProjetosRecursosCollection field has a non-nullable tbProjetosServicosHand field.");
            }
            Collection<TbOsServico> tbOsServicoCollectionOrphanCheck = tbProjetosServicos.getTbOsServicoCollection();
            for (TbOsServico tbOsServicoCollectionOrphanCheckTbOsServico : tbOsServicoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbProjetosServicos (" + tbProjetosServicos + ") cannot be destroyed since the TbOsServico " + tbOsServicoCollectionOrphanCheckTbOsServico + " in its tbOsServicoCollection field has a non-nullable tbProjetosServicosHand field.");
            }
            Collection<TbProjetosMateriais> tbProjetosMateriaisCollectionOrphanCheck = tbProjetosServicos.getTbProjetosMateriaisCollection();
            for (TbProjetosMateriais tbProjetosMateriaisCollectionOrphanCheckTbProjetosMateriais : tbProjetosMateriaisCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbProjetosServicos (" + tbProjetosServicos + ") cannot be destroyed since the TbProjetosMateriais " + tbProjetosMateriaisCollectionOrphanCheckTbProjetosMateriais + " in its tbProjetosMateriaisCollection field has a non-nullable tbProjetosServicosHand field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            TbServicos servicoDependente = tbProjetosServicos.getServicoDependente();
            if (servicoDependente != null) {
                servicoDependente.getTbProjetosServicosCollection().remove(tbProjetosServicos);
                servicoDependente = em.merge(servicoDependente);
            }
            TbServicos tbServicosHand = tbProjetosServicos.getTbServicosHand();
            if (tbServicosHand != null) {
                tbServicosHand.getTbProjetosServicosCollection().remove(tbProjetosServicos);
                tbServicosHand = em.merge(tbServicosHand);
            }
            TbProjetoMarcos tbProjetoMarcosHand = tbProjetosServicos.getTbProjetoMarcosHand();
            if (tbProjetoMarcosHand != null) {
                tbProjetoMarcosHand.getTbProjetosServicosCollection().remove(tbProjetosServicos);
                tbProjetoMarcosHand = em.merge(tbProjetoMarcosHand);
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
            Query q = em.createQuery("select object(o) from TbProjetosServicos as o");
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
            Query q = em.createQuery("select count(o) from TbProjetosServicos as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
