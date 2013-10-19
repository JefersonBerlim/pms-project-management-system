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
import Entidades.TbMarcosServicos;
import java.util.ArrayList;
import java.util.Collection;
import Entidades.TbProjetosServicos;
import Entidades.TbRecursosServicos;
import Entidades.TbServicos;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;

/**
 *
 * @author berlim
 */
public class TbServicosJpaController implements Serializable {

    public TbServicosJpaController() {
        emf = Persistence.createEntityManagerFactory("ProjectManagementSystemPU");
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TbServicos tbServicos) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (tbServicos.getTbMarcosServicosCollection() == null) {
            tbServicos.setTbMarcosServicosCollection(new ArrayList<TbMarcosServicos>());
        }
        if (tbServicos.getTbProjetosServicosCollection() == null) {
            tbServicos.setTbProjetosServicosCollection(new ArrayList<TbProjetosServicos>());
        }
        if (tbServicos.getTbProjetosServicosCollection1() == null) {
            tbServicos.setTbProjetosServicosCollection1(new ArrayList<TbProjetosServicos>());
        }
        if (tbServicos.getTbRecursosServicosCollection() == null) {
            tbServicos.setTbRecursosServicosCollection(new ArrayList<TbRecursosServicos>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<TbMarcosServicos> attachedTbMarcosServicosCollection = new ArrayList<TbMarcosServicos>();
            for (TbMarcosServicos tbMarcosServicosCollectionTbMarcosServicosToAttach : tbServicos.getTbMarcosServicosCollection()) {
                tbMarcosServicosCollectionTbMarcosServicosToAttach = em.getReference(tbMarcosServicosCollectionTbMarcosServicosToAttach.getClass(), tbMarcosServicosCollectionTbMarcosServicosToAttach.getHand());
                attachedTbMarcosServicosCollection.add(tbMarcosServicosCollectionTbMarcosServicosToAttach);
            }
            tbServicos.setTbMarcosServicosCollection(attachedTbMarcosServicosCollection);
            Collection<TbProjetosServicos> attachedTbProjetosServicosCollection = new ArrayList<TbProjetosServicos>();
            for (TbProjetosServicos tbProjetosServicosCollectionTbProjetosServicosToAttach : tbServicos.getTbProjetosServicosCollection()) {
                tbProjetosServicosCollectionTbProjetosServicosToAttach = em.getReference(tbProjetosServicosCollectionTbProjetosServicosToAttach.getClass(), tbProjetosServicosCollectionTbProjetosServicosToAttach.getHand());
                attachedTbProjetosServicosCollection.add(tbProjetosServicosCollectionTbProjetosServicosToAttach);
            }
            tbServicos.setTbProjetosServicosCollection(attachedTbProjetosServicosCollection);
            Collection<TbProjetosServicos> attachedTbProjetosServicosCollection1 = new ArrayList<TbProjetosServicos>();
            for (TbProjetosServicos tbProjetosServicosCollection1TbProjetosServicosToAttach : tbServicos.getTbProjetosServicosCollection1()) {
                tbProjetosServicosCollection1TbProjetosServicosToAttach = em.getReference(tbProjetosServicosCollection1TbProjetosServicosToAttach.getClass(), tbProjetosServicosCollection1TbProjetosServicosToAttach.getHand());
                attachedTbProjetosServicosCollection1.add(tbProjetosServicosCollection1TbProjetosServicosToAttach);
            }
            tbServicos.setTbProjetosServicosCollection1(attachedTbProjetosServicosCollection1);
            Collection<TbRecursosServicos> attachedTbRecursosServicosCollection = new ArrayList<TbRecursosServicos>();
            for (TbRecursosServicos tbRecursosServicosCollectionTbRecursosServicosToAttach : tbServicos.getTbRecursosServicosCollection()) {
                tbRecursosServicosCollectionTbRecursosServicosToAttach = em.getReference(tbRecursosServicosCollectionTbRecursosServicosToAttach.getClass(), tbRecursosServicosCollectionTbRecursosServicosToAttach.getHand());
                attachedTbRecursosServicosCollection.add(tbRecursosServicosCollectionTbRecursosServicosToAttach);
            }
            tbServicos.setTbRecursosServicosCollection(attachedTbRecursosServicosCollection);
            em.persist(tbServicos);
            for (TbMarcosServicos tbMarcosServicosCollectionTbMarcosServicos : tbServicos.getTbMarcosServicosCollection()) {
                TbServicos oldTbServicosHandOfTbMarcosServicosCollectionTbMarcosServicos = tbMarcosServicosCollectionTbMarcosServicos.getTbServicosHand();
                tbMarcosServicosCollectionTbMarcosServicos.setTbServicosHand(tbServicos);
                tbMarcosServicosCollectionTbMarcosServicos = em.merge(tbMarcosServicosCollectionTbMarcosServicos);
                if (oldTbServicosHandOfTbMarcosServicosCollectionTbMarcosServicos != null) {
                    oldTbServicosHandOfTbMarcosServicosCollectionTbMarcosServicos.getTbMarcosServicosCollection().remove(tbMarcosServicosCollectionTbMarcosServicos);
                    oldTbServicosHandOfTbMarcosServicosCollectionTbMarcosServicos = em.merge(oldTbServicosHandOfTbMarcosServicosCollectionTbMarcosServicos);
                }
            }
            for (TbProjetosServicos tbProjetosServicosCollectionTbProjetosServicos : tbServicos.getTbProjetosServicosCollection()) {
                TbServicos oldServicoDependenteOfTbProjetosServicosCollectionTbProjetosServicos = tbProjetosServicosCollectionTbProjetosServicos.getServicoDependente();
                tbProjetosServicosCollectionTbProjetosServicos.setServicoDependente(tbServicos);
                tbProjetosServicosCollectionTbProjetosServicos = em.merge(tbProjetosServicosCollectionTbProjetosServicos);
                if (oldServicoDependenteOfTbProjetosServicosCollectionTbProjetosServicos != null) {
                    oldServicoDependenteOfTbProjetosServicosCollectionTbProjetosServicos.getTbProjetosServicosCollection().remove(tbProjetosServicosCollectionTbProjetosServicos);
                    oldServicoDependenteOfTbProjetosServicosCollectionTbProjetosServicos = em.merge(oldServicoDependenteOfTbProjetosServicosCollectionTbProjetosServicos);
                }
            }
            for (TbProjetosServicos tbProjetosServicosCollection1TbProjetosServicos : tbServicos.getTbProjetosServicosCollection1()) {
                TbServicos oldTbServicosHandOfTbProjetosServicosCollection1TbProjetosServicos = tbProjetosServicosCollection1TbProjetosServicos.getTbServicosHand();
                tbProjetosServicosCollection1TbProjetosServicos.setTbServicosHand(tbServicos);
                tbProjetosServicosCollection1TbProjetosServicos = em.merge(tbProjetosServicosCollection1TbProjetosServicos);
                if (oldTbServicosHandOfTbProjetosServicosCollection1TbProjetosServicos != null) {
                    oldTbServicosHandOfTbProjetosServicosCollection1TbProjetosServicos.getTbProjetosServicosCollection1().remove(tbProjetosServicosCollection1TbProjetosServicos);
                    oldTbServicosHandOfTbProjetosServicosCollection1TbProjetosServicos = em.merge(oldTbServicosHandOfTbProjetosServicosCollection1TbProjetosServicos);
                }
            }
            for (TbRecursosServicos tbRecursosServicosCollectionTbRecursosServicos : tbServicos.getTbRecursosServicosCollection()) {
                TbServicos oldTbServicosHandOfTbRecursosServicosCollectionTbRecursosServicos = tbRecursosServicosCollectionTbRecursosServicos.getTbServicosHand();
                tbRecursosServicosCollectionTbRecursosServicos.setTbServicosHand(tbServicos);
                tbRecursosServicosCollectionTbRecursosServicos = em.merge(tbRecursosServicosCollectionTbRecursosServicos);
                if (oldTbServicosHandOfTbRecursosServicosCollectionTbRecursosServicos != null) {
                    oldTbServicosHandOfTbRecursosServicosCollectionTbRecursosServicos.getTbRecursosServicosCollection().remove(tbRecursosServicosCollectionTbRecursosServicos);
                    oldTbServicosHandOfTbRecursosServicosCollectionTbRecursosServicos = em.merge(oldTbServicosHandOfTbRecursosServicosCollectionTbRecursosServicos);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTbServicos(tbServicos.getHand()) != null) {
                throw new PreexistingEntityException("TbServicos " + tbServicos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TbServicos tbServicos) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbServicos persistentTbServicos = em.find(TbServicos.class, tbServicos.getHand());
            Collection<TbMarcosServicos> tbMarcosServicosCollectionOld = persistentTbServicos.getTbMarcosServicosCollection();
            Collection<TbMarcosServicos> tbMarcosServicosCollectionNew = tbServicos.getTbMarcosServicosCollection();
            Collection<TbProjetosServicos> tbProjetosServicosCollectionOld = persistentTbServicos.getTbProjetosServicosCollection();
            Collection<TbProjetosServicos> tbProjetosServicosCollectionNew = tbServicos.getTbProjetosServicosCollection();
            Collection<TbProjetosServicos> tbProjetosServicosCollection1Old = persistentTbServicos.getTbProjetosServicosCollection1();
            Collection<TbProjetosServicos> tbProjetosServicosCollection1New = tbServicos.getTbProjetosServicosCollection1();
            Collection<TbRecursosServicos> tbRecursosServicosCollectionOld = persistentTbServicos.getTbRecursosServicosCollection();
            Collection<TbRecursosServicos> tbRecursosServicosCollectionNew = tbServicos.getTbRecursosServicosCollection();
            List<String> illegalOrphanMessages = null;
            for (TbMarcosServicos tbMarcosServicosCollectionOldTbMarcosServicos : tbMarcosServicosCollectionOld) {
                if (!tbMarcosServicosCollectionNew.contains(tbMarcosServicosCollectionOldTbMarcosServicos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TbMarcosServicos " + tbMarcosServicosCollectionOldTbMarcosServicos + " since its tbServicosHand field is not nullable.");
                }
            }
            for (TbProjetosServicos tbProjetosServicosCollection1OldTbProjetosServicos : tbProjetosServicosCollection1Old) {
                if (!tbProjetosServicosCollection1New.contains(tbProjetosServicosCollection1OldTbProjetosServicos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TbProjetosServicos " + tbProjetosServicosCollection1OldTbProjetosServicos + " since its tbServicosHand field is not nullable.");
                }
            }
            for (TbRecursosServicos tbRecursosServicosCollectionOldTbRecursosServicos : tbRecursosServicosCollectionOld) {
                if (!tbRecursosServicosCollectionNew.contains(tbRecursosServicosCollectionOldTbRecursosServicos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TbRecursosServicos " + tbRecursosServicosCollectionOldTbRecursosServicos + " since its tbServicosHand field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<TbMarcosServicos> attachedTbMarcosServicosCollectionNew = new ArrayList<TbMarcosServicos>();
            for (TbMarcosServicos tbMarcosServicosCollectionNewTbMarcosServicosToAttach : tbMarcosServicosCollectionNew) {
                tbMarcosServicosCollectionNewTbMarcosServicosToAttach = em.getReference(tbMarcosServicosCollectionNewTbMarcosServicosToAttach.getClass(), tbMarcosServicosCollectionNewTbMarcosServicosToAttach.getHand());
                attachedTbMarcosServicosCollectionNew.add(tbMarcosServicosCollectionNewTbMarcosServicosToAttach);
            }
            tbMarcosServicosCollectionNew = attachedTbMarcosServicosCollectionNew;
            tbServicos.setTbMarcosServicosCollection(tbMarcosServicosCollectionNew);
            Collection<TbProjetosServicos> attachedTbProjetosServicosCollectionNew = new ArrayList<TbProjetosServicos>();
            for (TbProjetosServicos tbProjetosServicosCollectionNewTbProjetosServicosToAttach : tbProjetosServicosCollectionNew) {
                tbProjetosServicosCollectionNewTbProjetosServicosToAttach = em.getReference(tbProjetosServicosCollectionNewTbProjetosServicosToAttach.getClass(), tbProjetosServicosCollectionNewTbProjetosServicosToAttach.getHand());
                attachedTbProjetosServicosCollectionNew.add(tbProjetosServicosCollectionNewTbProjetosServicosToAttach);
            }
            tbProjetosServicosCollectionNew = attachedTbProjetosServicosCollectionNew;
            tbServicos.setTbProjetosServicosCollection(tbProjetosServicosCollectionNew);
            Collection<TbProjetosServicos> attachedTbProjetosServicosCollection1New = new ArrayList<TbProjetosServicos>();
            for (TbProjetosServicos tbProjetosServicosCollection1NewTbProjetosServicosToAttach : tbProjetosServicosCollection1New) {
                tbProjetosServicosCollection1NewTbProjetosServicosToAttach = em.getReference(tbProjetosServicosCollection1NewTbProjetosServicosToAttach.getClass(), tbProjetosServicosCollection1NewTbProjetosServicosToAttach.getHand());
                attachedTbProjetosServicosCollection1New.add(tbProjetosServicosCollection1NewTbProjetosServicosToAttach);
            }
            tbProjetosServicosCollection1New = attachedTbProjetosServicosCollection1New;
            tbServicos.setTbProjetosServicosCollection1(tbProjetosServicosCollection1New);
            Collection<TbRecursosServicos> attachedTbRecursosServicosCollectionNew = new ArrayList<TbRecursosServicos>();
            for (TbRecursosServicos tbRecursosServicosCollectionNewTbRecursosServicosToAttach : tbRecursosServicosCollectionNew) {
                tbRecursosServicosCollectionNewTbRecursosServicosToAttach = em.getReference(tbRecursosServicosCollectionNewTbRecursosServicosToAttach.getClass(), tbRecursosServicosCollectionNewTbRecursosServicosToAttach.getHand());
                attachedTbRecursosServicosCollectionNew.add(tbRecursosServicosCollectionNewTbRecursosServicosToAttach);
            }
            tbRecursosServicosCollectionNew = attachedTbRecursosServicosCollectionNew;
            tbServicos.setTbRecursosServicosCollection(tbRecursosServicosCollectionNew);
            tbServicos = em.merge(tbServicos);
            for (TbMarcosServicos tbMarcosServicosCollectionNewTbMarcosServicos : tbMarcosServicosCollectionNew) {
                if (!tbMarcosServicosCollectionOld.contains(tbMarcosServicosCollectionNewTbMarcosServicos)) {
                    TbServicos oldTbServicosHandOfTbMarcosServicosCollectionNewTbMarcosServicos = tbMarcosServicosCollectionNewTbMarcosServicos.getTbServicosHand();
                    tbMarcosServicosCollectionNewTbMarcosServicos.setTbServicosHand(tbServicos);
                    tbMarcosServicosCollectionNewTbMarcosServicos = em.merge(tbMarcosServicosCollectionNewTbMarcosServicos);
                    if (oldTbServicosHandOfTbMarcosServicosCollectionNewTbMarcosServicos != null && !oldTbServicosHandOfTbMarcosServicosCollectionNewTbMarcosServicos.equals(tbServicos)) {
                        oldTbServicosHandOfTbMarcosServicosCollectionNewTbMarcosServicos.getTbMarcosServicosCollection().remove(tbMarcosServicosCollectionNewTbMarcosServicos);
                        oldTbServicosHandOfTbMarcosServicosCollectionNewTbMarcosServicos = em.merge(oldTbServicosHandOfTbMarcosServicosCollectionNewTbMarcosServicos);
                    }
                }
            }
            for (TbProjetosServicos tbProjetosServicosCollectionOldTbProjetosServicos : tbProjetosServicosCollectionOld) {
                if (!tbProjetosServicosCollectionNew.contains(tbProjetosServicosCollectionOldTbProjetosServicos)) {
                    tbProjetosServicosCollectionOldTbProjetosServicos.setServicoDependente(null);
                    tbProjetosServicosCollectionOldTbProjetosServicos = em.merge(tbProjetosServicosCollectionOldTbProjetosServicos);
                }
            }
            for (TbProjetosServicos tbProjetosServicosCollectionNewTbProjetosServicos : tbProjetosServicosCollectionNew) {
                if (!tbProjetosServicosCollectionOld.contains(tbProjetosServicosCollectionNewTbProjetosServicos)) {
                    TbServicos oldServicoDependenteOfTbProjetosServicosCollectionNewTbProjetosServicos = tbProjetosServicosCollectionNewTbProjetosServicos.getServicoDependente();
                    tbProjetosServicosCollectionNewTbProjetosServicos.setServicoDependente(tbServicos);
                    tbProjetosServicosCollectionNewTbProjetosServicos = em.merge(tbProjetosServicosCollectionNewTbProjetosServicos);
                    if (oldServicoDependenteOfTbProjetosServicosCollectionNewTbProjetosServicos != null && !oldServicoDependenteOfTbProjetosServicosCollectionNewTbProjetosServicos.equals(tbServicos)) {
                        oldServicoDependenteOfTbProjetosServicosCollectionNewTbProjetosServicos.getTbProjetosServicosCollection().remove(tbProjetosServicosCollectionNewTbProjetosServicos);
                        oldServicoDependenteOfTbProjetosServicosCollectionNewTbProjetosServicos = em.merge(oldServicoDependenteOfTbProjetosServicosCollectionNewTbProjetosServicos);
                    }
                }
            }
            for (TbProjetosServicos tbProjetosServicosCollection1NewTbProjetosServicos : tbProjetosServicosCollection1New) {
                if (!tbProjetosServicosCollection1Old.contains(tbProjetosServicosCollection1NewTbProjetosServicos)) {
                    TbServicos oldTbServicosHandOfTbProjetosServicosCollection1NewTbProjetosServicos = tbProjetosServicosCollection1NewTbProjetosServicos.getTbServicosHand();
                    tbProjetosServicosCollection1NewTbProjetosServicos.setTbServicosHand(tbServicos);
                    tbProjetosServicosCollection1NewTbProjetosServicos = em.merge(tbProjetosServicosCollection1NewTbProjetosServicos);
                    if (oldTbServicosHandOfTbProjetosServicosCollection1NewTbProjetosServicos != null && !oldTbServicosHandOfTbProjetosServicosCollection1NewTbProjetosServicos.equals(tbServicos)) {
                        oldTbServicosHandOfTbProjetosServicosCollection1NewTbProjetosServicos.getTbProjetosServicosCollection1().remove(tbProjetosServicosCollection1NewTbProjetosServicos);
                        oldTbServicosHandOfTbProjetosServicosCollection1NewTbProjetosServicos = em.merge(oldTbServicosHandOfTbProjetosServicosCollection1NewTbProjetosServicos);
                    }
                }
            }
            for (TbRecursosServicos tbRecursosServicosCollectionNewTbRecursosServicos : tbRecursosServicosCollectionNew) {
                if (!tbRecursosServicosCollectionOld.contains(tbRecursosServicosCollectionNewTbRecursosServicos)) {
                    TbServicos oldTbServicosHandOfTbRecursosServicosCollectionNewTbRecursosServicos = tbRecursosServicosCollectionNewTbRecursosServicos.getTbServicosHand();
                    tbRecursosServicosCollectionNewTbRecursosServicos.setTbServicosHand(tbServicos);
                    tbRecursosServicosCollectionNewTbRecursosServicos = em.merge(tbRecursosServicosCollectionNewTbRecursosServicos);
                    if (oldTbServicosHandOfTbRecursosServicosCollectionNewTbRecursosServicos != null && !oldTbServicosHandOfTbRecursosServicosCollectionNewTbRecursosServicos.equals(tbServicos)) {
                        oldTbServicosHandOfTbRecursosServicosCollectionNewTbRecursosServicos.getTbRecursosServicosCollection().remove(tbRecursosServicosCollectionNewTbRecursosServicos);
                        oldTbServicosHandOfTbRecursosServicosCollectionNewTbRecursosServicos = em.merge(oldTbServicosHandOfTbRecursosServicosCollectionNewTbRecursosServicos);
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
                Integer id = tbServicos.getHand();
                if (findTbServicos(id) == null) {
                    throw new NonexistentEntityException("The tbServicos with id " + id + " no longer exists.");
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
            TbServicos tbServicos;
            try {
                tbServicos = em.getReference(TbServicos.class, id);
                tbServicos.getHand();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tbServicos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TbMarcosServicos> tbMarcosServicosCollectionOrphanCheck = tbServicos.getTbMarcosServicosCollection();
            for (TbMarcosServicos tbMarcosServicosCollectionOrphanCheckTbMarcosServicos : tbMarcosServicosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbServicos (" + tbServicos + ") cannot be destroyed since the TbMarcosServicos " + tbMarcosServicosCollectionOrphanCheckTbMarcosServicos + " in its tbMarcosServicosCollection field has a non-nullable tbServicosHand field.");
            }
            Collection<TbProjetosServicos> tbProjetosServicosCollection1OrphanCheck = tbServicos.getTbProjetosServicosCollection1();
            for (TbProjetosServicos tbProjetosServicosCollection1OrphanCheckTbProjetosServicos : tbProjetosServicosCollection1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbServicos (" + tbServicos + ") cannot be destroyed since the TbProjetosServicos " + tbProjetosServicosCollection1OrphanCheckTbProjetosServicos + " in its tbProjetosServicosCollection1 field has a non-nullable tbServicosHand field.");
            }
            Collection<TbRecursosServicos> tbRecursosServicosCollectionOrphanCheck = tbServicos.getTbRecursosServicosCollection();
            for (TbRecursosServicos tbRecursosServicosCollectionOrphanCheckTbRecursosServicos : tbRecursosServicosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbServicos (" + tbServicos + ") cannot be destroyed since the TbRecursosServicos " + tbRecursosServicosCollectionOrphanCheckTbRecursosServicos + " in its tbRecursosServicosCollection field has a non-nullable tbServicosHand field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<TbProjetosServicos> tbProjetosServicosCollection = tbServicos.getTbProjetosServicosCollection();
            for (TbProjetosServicos tbProjetosServicosCollectionTbProjetosServicos : tbProjetosServicosCollection) {
                tbProjetosServicosCollectionTbProjetosServicos.setServicoDependente(null);
                tbProjetosServicosCollectionTbProjetosServicos = em.merge(tbProjetosServicosCollectionTbProjetosServicos);
            }
            em.remove(tbServicos);
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

    public List<TbServicos> findTbServicosEntities() {
        return findTbServicosEntities(true, -1, -1);
    }

    public List<TbServicos> findTbServicosEntities(int maxResults, int firstResult) {
        return findTbServicosEntities(false, maxResults, firstResult);
    }

    private List<TbServicos> findTbServicosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from TbServicos as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public TbServicos findTbServicos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbServicos.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbServicosCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from TbServicos as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
