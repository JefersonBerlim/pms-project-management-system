/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import Controle.exceptions.IllegalOrphanException;
import Controle.exceptions.NonexistentEntityException;
import Controle.exceptions.PreexistingEntityException;
import Controle.exceptions.RollbackFailureException;
import Entidades.TbMarcos;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import Entidades.TbMarcosServicos;
import java.util.ArrayList;
import java.util.Collection;
import Entidades.TbProjetoMarcos;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;

/**
 *
 * @author berlim
 */
public class TbMarcosJpaController implements Serializable {

    public TbMarcosJpaController() {
        emf = Persistence.createEntityManagerFactory("ProjectManagementSystemPU");
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TbMarcos tbMarcos) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (tbMarcos.getTbMarcosServicosCollection() == null) {
            tbMarcos.setTbMarcosServicosCollection(new ArrayList<TbMarcosServicos>());
        }
        if (tbMarcos.getTbProjetoMarcosCollection() == null) {
            tbMarcos.setTbProjetoMarcosCollection(new ArrayList<TbProjetoMarcos>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<TbMarcosServicos> attachedTbMarcosServicosCollection = new ArrayList<TbMarcosServicos>();
            for (TbMarcosServicos tbMarcosServicosCollectionTbMarcosServicosToAttach : tbMarcos.getTbMarcosServicosCollection()) {
                tbMarcosServicosCollectionTbMarcosServicosToAttach = em.getReference(tbMarcosServicosCollectionTbMarcosServicosToAttach.getClass(), tbMarcosServicosCollectionTbMarcosServicosToAttach.getHand());
                attachedTbMarcosServicosCollection.add(tbMarcosServicosCollectionTbMarcosServicosToAttach);
            }
            tbMarcos.setTbMarcosServicosCollection(attachedTbMarcosServicosCollection);
            Collection<TbProjetoMarcos> attachedTbProjetoMarcosCollection = new ArrayList<TbProjetoMarcos>();
            for (TbProjetoMarcos tbProjetoMarcosCollectionTbProjetoMarcosToAttach : tbMarcos.getTbProjetoMarcosCollection()) {
                tbProjetoMarcosCollectionTbProjetoMarcosToAttach = em.getReference(tbProjetoMarcosCollectionTbProjetoMarcosToAttach.getClass(), tbProjetoMarcosCollectionTbProjetoMarcosToAttach.getHand());
                attachedTbProjetoMarcosCollection.add(tbProjetoMarcosCollectionTbProjetoMarcosToAttach);
            }
            tbMarcos.setTbProjetoMarcosCollection(attachedTbProjetoMarcosCollection);
            em.persist(tbMarcos);
            for (TbMarcosServicos tbMarcosServicosCollectionTbMarcosServicos : tbMarcos.getTbMarcosServicosCollection()) {
                TbMarcos oldTbMarcosHandOfTbMarcosServicosCollectionTbMarcosServicos = tbMarcosServicosCollectionTbMarcosServicos.getTbMarcosHand();
                tbMarcosServicosCollectionTbMarcosServicos.setTbMarcosHand(tbMarcos);
                tbMarcosServicosCollectionTbMarcosServicos = em.merge(tbMarcosServicosCollectionTbMarcosServicos);
                if (oldTbMarcosHandOfTbMarcosServicosCollectionTbMarcosServicos != null) {
                    oldTbMarcosHandOfTbMarcosServicosCollectionTbMarcosServicos.getTbMarcosServicosCollection().remove(tbMarcosServicosCollectionTbMarcosServicos);
                    oldTbMarcosHandOfTbMarcosServicosCollectionTbMarcosServicos = em.merge(oldTbMarcosHandOfTbMarcosServicosCollectionTbMarcosServicos);
                }
            }
            for (TbProjetoMarcos tbProjetoMarcosCollectionTbProjetoMarcos : tbMarcos.getTbProjetoMarcosCollection()) {
                TbMarcos oldTbMarcosHandOfTbProjetoMarcosCollectionTbProjetoMarcos = tbProjetoMarcosCollectionTbProjetoMarcos.getTbMarcosHand();
                tbProjetoMarcosCollectionTbProjetoMarcos.setTbMarcosHand(tbMarcos);
                tbProjetoMarcosCollectionTbProjetoMarcos = em.merge(tbProjetoMarcosCollectionTbProjetoMarcos);
                if (oldTbMarcosHandOfTbProjetoMarcosCollectionTbProjetoMarcos != null) {
                    oldTbMarcosHandOfTbProjetoMarcosCollectionTbProjetoMarcos.getTbProjetoMarcosCollection().remove(tbProjetoMarcosCollectionTbProjetoMarcos);
                    oldTbMarcosHandOfTbProjetoMarcosCollectionTbProjetoMarcos = em.merge(oldTbMarcosHandOfTbProjetoMarcosCollectionTbProjetoMarcos);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTbMarcos(tbMarcos.getHand()) != null) {
                throw new PreexistingEntityException("TbMarcos " + tbMarcos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TbMarcos tbMarcos) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbMarcos persistentTbMarcos = em.find(TbMarcos.class, tbMarcos.getHand());
            Collection<TbMarcosServicos> tbMarcosServicosCollectionOld = persistentTbMarcos.getTbMarcosServicosCollection();
            Collection<TbMarcosServicos> tbMarcosServicosCollectionNew = tbMarcos.getTbMarcosServicosCollection();
            Collection<TbProjetoMarcos> tbProjetoMarcosCollectionOld = persistentTbMarcos.getTbProjetoMarcosCollection();
            Collection<TbProjetoMarcos> tbProjetoMarcosCollectionNew = tbMarcos.getTbProjetoMarcosCollection();
            List<String> illegalOrphanMessages = null;
            for (TbMarcosServicos tbMarcosServicosCollectionOldTbMarcosServicos : tbMarcosServicosCollectionOld) {
                if (!tbMarcosServicosCollectionNew.contains(tbMarcosServicosCollectionOldTbMarcosServicos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TbMarcosServicos " + tbMarcosServicosCollectionOldTbMarcosServicos + " since its tbMarcosHand field is not nullable.");
                }
            }
            for (TbProjetoMarcos tbProjetoMarcosCollectionOldTbProjetoMarcos : tbProjetoMarcosCollectionOld) {
                if (!tbProjetoMarcosCollectionNew.contains(tbProjetoMarcosCollectionOldTbProjetoMarcos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TbProjetoMarcos " + tbProjetoMarcosCollectionOldTbProjetoMarcos + " since its tbMarcosHand field is not nullable.");
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
            tbMarcos.setTbMarcosServicosCollection(tbMarcosServicosCollectionNew);
            Collection<TbProjetoMarcos> attachedTbProjetoMarcosCollectionNew = new ArrayList<TbProjetoMarcos>();
            for (TbProjetoMarcos tbProjetoMarcosCollectionNewTbProjetoMarcosToAttach : tbProjetoMarcosCollectionNew) {
                tbProjetoMarcosCollectionNewTbProjetoMarcosToAttach = em.getReference(tbProjetoMarcosCollectionNewTbProjetoMarcosToAttach.getClass(), tbProjetoMarcosCollectionNewTbProjetoMarcosToAttach.getHand());
                attachedTbProjetoMarcosCollectionNew.add(tbProjetoMarcosCollectionNewTbProjetoMarcosToAttach);
            }
            tbProjetoMarcosCollectionNew = attachedTbProjetoMarcosCollectionNew;
            tbMarcos.setTbProjetoMarcosCollection(tbProjetoMarcosCollectionNew);
            tbMarcos = em.merge(tbMarcos);
            for (TbMarcosServicos tbMarcosServicosCollectionNewTbMarcosServicos : tbMarcosServicosCollectionNew) {
                if (!tbMarcosServicosCollectionOld.contains(tbMarcosServicosCollectionNewTbMarcosServicos)) {
                    TbMarcos oldTbMarcosHandOfTbMarcosServicosCollectionNewTbMarcosServicos = tbMarcosServicosCollectionNewTbMarcosServicos.getTbMarcosHand();
                    tbMarcosServicosCollectionNewTbMarcosServicos.setTbMarcosHand(tbMarcos);
                    tbMarcosServicosCollectionNewTbMarcosServicos = em.merge(tbMarcosServicosCollectionNewTbMarcosServicos);
                    if (oldTbMarcosHandOfTbMarcosServicosCollectionNewTbMarcosServicos != null && !oldTbMarcosHandOfTbMarcosServicosCollectionNewTbMarcosServicos.equals(tbMarcos)) {
                        oldTbMarcosHandOfTbMarcosServicosCollectionNewTbMarcosServicos.getTbMarcosServicosCollection().remove(tbMarcosServicosCollectionNewTbMarcosServicos);
                        oldTbMarcosHandOfTbMarcosServicosCollectionNewTbMarcosServicos = em.merge(oldTbMarcosHandOfTbMarcosServicosCollectionNewTbMarcosServicos);
                    }
                }
            }
            for (TbProjetoMarcos tbProjetoMarcosCollectionNewTbProjetoMarcos : tbProjetoMarcosCollectionNew) {
                if (!tbProjetoMarcosCollectionOld.contains(tbProjetoMarcosCollectionNewTbProjetoMarcos)) {
                    TbMarcos oldTbMarcosHandOfTbProjetoMarcosCollectionNewTbProjetoMarcos = tbProjetoMarcosCollectionNewTbProjetoMarcos.getTbMarcosHand();
                    tbProjetoMarcosCollectionNewTbProjetoMarcos.setTbMarcosHand(tbMarcos);
                    tbProjetoMarcosCollectionNewTbProjetoMarcos = em.merge(tbProjetoMarcosCollectionNewTbProjetoMarcos);
                    if (oldTbMarcosHandOfTbProjetoMarcosCollectionNewTbProjetoMarcos != null && !oldTbMarcosHandOfTbProjetoMarcosCollectionNewTbProjetoMarcos.equals(tbMarcos)) {
                        oldTbMarcosHandOfTbProjetoMarcosCollectionNewTbProjetoMarcos.getTbProjetoMarcosCollection().remove(tbProjetoMarcosCollectionNewTbProjetoMarcos);
                        oldTbMarcosHandOfTbProjetoMarcosCollectionNewTbProjetoMarcos = em.merge(oldTbMarcosHandOfTbProjetoMarcosCollectionNewTbProjetoMarcos);
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
                Integer id = tbMarcos.getHand();
                if (findTbMarcos(id) == null) {
                    throw new NonexistentEntityException("The tbMarcos with id " + id + " no longer exists.");
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
            TbMarcos tbMarcos;
            try {
                tbMarcos = em.getReference(TbMarcos.class, id);
                tbMarcos.getHand();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tbMarcos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TbMarcosServicos> tbMarcosServicosCollectionOrphanCheck = tbMarcos.getTbMarcosServicosCollection();
            for (TbMarcosServicos tbMarcosServicosCollectionOrphanCheckTbMarcosServicos : tbMarcosServicosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbMarcos (" + tbMarcos + ") cannot be destroyed since the TbMarcosServicos " + tbMarcosServicosCollectionOrphanCheckTbMarcosServicos + " in its tbMarcosServicosCollection field has a non-nullable tbMarcosHand field.");
            }
            Collection<TbProjetoMarcos> tbProjetoMarcosCollectionOrphanCheck = tbMarcos.getTbProjetoMarcosCollection();
            for (TbProjetoMarcos tbProjetoMarcosCollectionOrphanCheckTbProjetoMarcos : tbProjetoMarcosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbMarcos (" + tbMarcos + ") cannot be destroyed since the TbProjetoMarcos " + tbProjetoMarcosCollectionOrphanCheckTbProjetoMarcos + " in its tbProjetoMarcosCollection field has a non-nullable tbMarcosHand field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tbMarcos);
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

    public List<TbMarcos> findTbMarcosEntities() {
        return findTbMarcosEntities(true, -1, -1);
    }

    public List<TbMarcos> findTbMarcosEntities(int maxResults, int firstResult) {
        return findTbMarcosEntities(false, maxResults, firstResult);
    }

    private List<TbMarcos> findTbMarcosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from TbMarcos as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public TbMarcos findTbMarcos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbMarcos.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbMarcosCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from TbMarcos as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
