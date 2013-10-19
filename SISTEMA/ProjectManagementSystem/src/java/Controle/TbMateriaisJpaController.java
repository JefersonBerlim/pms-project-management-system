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
import Entidades.TbUnidadeMedida;
import Entidades.TbApontamentosMateriais;
import Entidades.TbMateriais;
import java.util.ArrayList;
import java.util.Collection;
import Entidades.TbOsMateriais;
import Entidades.TbProjetosMateriais;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;

/**
 *
 * @author berlim
 */
public class TbMateriaisJpaController implements Serializable {

    public TbMateriaisJpaController() {
        emf = Persistence.createEntityManagerFactory("ProjectManagementSystemPU");
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TbMateriais tbMateriais) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (tbMateriais.getTbApontamentosMateriaisCollection() == null) {
            tbMateriais.setTbApontamentosMateriaisCollection(new ArrayList<TbApontamentosMateriais>());
        }
        if (tbMateriais.getTbOsMateriaisCollection() == null) {
            tbMateriais.setTbOsMateriaisCollection(new ArrayList<TbOsMateriais>());
        }
        if (tbMateriais.getTbProjetosMateriaisCollection() == null) {
            tbMateriais.setTbProjetosMateriaisCollection(new ArrayList<TbProjetosMateriais>());
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
            Collection<TbApontamentosMateriais> attachedTbApontamentosMateriaisCollection = new ArrayList<TbApontamentosMateriais>();
            for (TbApontamentosMateriais tbApontamentosMateriaisCollectionTbApontamentosMateriaisToAttach : tbMateriais.getTbApontamentosMateriaisCollection()) {
                tbApontamentosMateriaisCollectionTbApontamentosMateriaisToAttach = em.getReference(tbApontamentosMateriaisCollectionTbApontamentosMateriaisToAttach.getClass(), tbApontamentosMateriaisCollectionTbApontamentosMateriaisToAttach.getHand());
                attachedTbApontamentosMateriaisCollection.add(tbApontamentosMateriaisCollectionTbApontamentosMateriaisToAttach);
            }
            tbMateriais.setTbApontamentosMateriaisCollection(attachedTbApontamentosMateriaisCollection);
            Collection<TbOsMateriais> attachedTbOsMateriaisCollection = new ArrayList<TbOsMateriais>();
            for (TbOsMateriais tbOsMateriaisCollectionTbOsMateriaisToAttach : tbMateriais.getTbOsMateriaisCollection()) {
                tbOsMateriaisCollectionTbOsMateriaisToAttach = em.getReference(tbOsMateriaisCollectionTbOsMateriaisToAttach.getClass(), tbOsMateriaisCollectionTbOsMateriaisToAttach.getHand());
                attachedTbOsMateriaisCollection.add(tbOsMateriaisCollectionTbOsMateriaisToAttach);
            }
            tbMateriais.setTbOsMateriaisCollection(attachedTbOsMateriaisCollection);
            Collection<TbProjetosMateriais> attachedTbProjetosMateriaisCollection = new ArrayList<TbProjetosMateriais>();
            for (TbProjetosMateriais tbProjetosMateriaisCollectionTbProjetosMateriaisToAttach : tbMateriais.getTbProjetosMateriaisCollection()) {
                tbProjetosMateriaisCollectionTbProjetosMateriaisToAttach = em.getReference(tbProjetosMateriaisCollectionTbProjetosMateriaisToAttach.getClass(), tbProjetosMateriaisCollectionTbProjetosMateriaisToAttach.getHand());
                attachedTbProjetosMateriaisCollection.add(tbProjetosMateriaisCollectionTbProjetosMateriaisToAttach);
            }
            tbMateriais.setTbProjetosMateriaisCollection(attachedTbProjetosMateriaisCollection);
            em.persist(tbMateriais);
            if (tbUnidadeMedidaHand != null) {
                tbUnidadeMedidaHand.getTbMateriaisCollection().add(tbMateriais);
                tbUnidadeMedidaHand = em.merge(tbUnidadeMedidaHand);
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
            for (TbOsMateriais tbOsMateriaisCollectionTbOsMateriais : tbMateriais.getTbOsMateriaisCollection()) {
                TbMateriais oldTbMateriaisHandOfTbOsMateriaisCollectionTbOsMateriais = tbOsMateriaisCollectionTbOsMateriais.getTbMateriaisHand();
                tbOsMateriaisCollectionTbOsMateriais.setTbMateriaisHand(tbMateriais);
                tbOsMateriaisCollectionTbOsMateriais = em.merge(tbOsMateriaisCollectionTbOsMateriais);
                if (oldTbMateriaisHandOfTbOsMateriaisCollectionTbOsMateriais != null) {
                    oldTbMateriaisHandOfTbOsMateriaisCollectionTbOsMateriais.getTbOsMateriaisCollection().remove(tbOsMateriaisCollectionTbOsMateriais);
                    oldTbMateriaisHandOfTbOsMateriaisCollectionTbOsMateriais = em.merge(oldTbMateriaisHandOfTbOsMateriaisCollectionTbOsMateriais);
                }
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
            Collection<TbApontamentosMateriais> tbApontamentosMateriaisCollectionOld = persistentTbMateriais.getTbApontamentosMateriaisCollection();
            Collection<TbApontamentosMateriais> tbApontamentosMateriaisCollectionNew = tbMateriais.getTbApontamentosMateriaisCollection();
            Collection<TbOsMateriais> tbOsMateriaisCollectionOld = persistentTbMateriais.getTbOsMateriaisCollection();
            Collection<TbOsMateriais> tbOsMateriaisCollectionNew = tbMateriais.getTbOsMateriaisCollection();
            Collection<TbProjetosMateriais> tbProjetosMateriaisCollectionOld = persistentTbMateriais.getTbProjetosMateriaisCollection();
            Collection<TbProjetosMateriais> tbProjetosMateriaisCollectionNew = tbMateriais.getTbProjetosMateriaisCollection();
            List<String> illegalOrphanMessages = null;
            for (TbApontamentosMateriais tbApontamentosMateriaisCollectionOldTbApontamentosMateriais : tbApontamentosMateriaisCollectionOld) {
                if (!tbApontamentosMateriaisCollectionNew.contains(tbApontamentosMateriaisCollectionOldTbApontamentosMateriais)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TbApontamentosMateriais " + tbApontamentosMateriaisCollectionOldTbApontamentosMateriais + " since its tbMateriaisHand field is not nullable.");
                }
            }
            for (TbOsMateriais tbOsMateriaisCollectionOldTbOsMateriais : tbOsMateriaisCollectionOld) {
                if (!tbOsMateriaisCollectionNew.contains(tbOsMateriaisCollectionOldTbOsMateriais)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TbOsMateriais " + tbOsMateriaisCollectionOldTbOsMateriais + " since its tbMateriaisHand field is not nullable.");
                }
            }
            for (TbProjetosMateriais tbProjetosMateriaisCollectionOldTbProjetosMateriais : tbProjetosMateriaisCollectionOld) {
                if (!tbProjetosMateriaisCollectionNew.contains(tbProjetosMateriaisCollectionOldTbProjetosMateriais)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TbProjetosMateriais " + tbProjetosMateriaisCollectionOldTbProjetosMateriais + " since its tbMateriaisHand field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tbUnidadeMedidaHandNew != null) {
                tbUnidadeMedidaHandNew = em.getReference(tbUnidadeMedidaHandNew.getClass(), tbUnidadeMedidaHandNew.getHand());
                tbMateriais.setTbUnidadeMedidaHand(tbUnidadeMedidaHandNew);
            }
            Collection<TbApontamentosMateriais> attachedTbApontamentosMateriaisCollectionNew = new ArrayList<TbApontamentosMateriais>();
            for (TbApontamentosMateriais tbApontamentosMateriaisCollectionNewTbApontamentosMateriaisToAttach : tbApontamentosMateriaisCollectionNew) {
                tbApontamentosMateriaisCollectionNewTbApontamentosMateriaisToAttach = em.getReference(tbApontamentosMateriaisCollectionNewTbApontamentosMateriaisToAttach.getClass(), tbApontamentosMateriaisCollectionNewTbApontamentosMateriaisToAttach.getHand());
                attachedTbApontamentosMateriaisCollectionNew.add(tbApontamentosMateriaisCollectionNewTbApontamentosMateriaisToAttach);
            }
            tbApontamentosMateriaisCollectionNew = attachedTbApontamentosMateriaisCollectionNew;
            tbMateriais.setTbApontamentosMateriaisCollection(tbApontamentosMateriaisCollectionNew);
            Collection<TbOsMateriais> attachedTbOsMateriaisCollectionNew = new ArrayList<TbOsMateriais>();
            for (TbOsMateriais tbOsMateriaisCollectionNewTbOsMateriaisToAttach : tbOsMateriaisCollectionNew) {
                tbOsMateriaisCollectionNewTbOsMateriaisToAttach = em.getReference(tbOsMateriaisCollectionNewTbOsMateriaisToAttach.getClass(), tbOsMateriaisCollectionNewTbOsMateriaisToAttach.getHand());
                attachedTbOsMateriaisCollectionNew.add(tbOsMateriaisCollectionNewTbOsMateriaisToAttach);
            }
            tbOsMateriaisCollectionNew = attachedTbOsMateriaisCollectionNew;
            tbMateriais.setTbOsMateriaisCollection(tbOsMateriaisCollectionNew);
            Collection<TbProjetosMateriais> attachedTbProjetosMateriaisCollectionNew = new ArrayList<TbProjetosMateriais>();
            for (TbProjetosMateriais tbProjetosMateriaisCollectionNewTbProjetosMateriaisToAttach : tbProjetosMateriaisCollectionNew) {
                tbProjetosMateriaisCollectionNewTbProjetosMateriaisToAttach = em.getReference(tbProjetosMateriaisCollectionNewTbProjetosMateriaisToAttach.getClass(), tbProjetosMateriaisCollectionNewTbProjetosMateriaisToAttach.getHand());
                attachedTbProjetosMateriaisCollectionNew.add(tbProjetosMateriaisCollectionNewTbProjetosMateriaisToAttach);
            }
            tbProjetosMateriaisCollectionNew = attachedTbProjetosMateriaisCollectionNew;
            tbMateriais.setTbProjetosMateriaisCollection(tbProjetosMateriaisCollectionNew);
            tbMateriais = em.merge(tbMateriais);
            if (tbUnidadeMedidaHandOld != null && !tbUnidadeMedidaHandOld.equals(tbUnidadeMedidaHandNew)) {
                tbUnidadeMedidaHandOld.getTbMateriaisCollection().remove(tbMateriais);
                tbUnidadeMedidaHandOld = em.merge(tbUnidadeMedidaHandOld);
            }
            if (tbUnidadeMedidaHandNew != null && !tbUnidadeMedidaHandNew.equals(tbUnidadeMedidaHandOld)) {
                tbUnidadeMedidaHandNew.getTbMateriaisCollection().add(tbMateriais);
                tbUnidadeMedidaHandNew = em.merge(tbUnidadeMedidaHandNew);
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
            for (TbOsMateriais tbOsMateriaisCollectionNewTbOsMateriais : tbOsMateriaisCollectionNew) {
                if (!tbOsMateriaisCollectionOld.contains(tbOsMateriaisCollectionNewTbOsMateriais)) {
                    TbMateriais oldTbMateriaisHandOfTbOsMateriaisCollectionNewTbOsMateriais = tbOsMateriaisCollectionNewTbOsMateriais.getTbMateriaisHand();
                    tbOsMateriaisCollectionNewTbOsMateriais.setTbMateriaisHand(tbMateriais);
                    tbOsMateriaisCollectionNewTbOsMateriais = em.merge(tbOsMateriaisCollectionNewTbOsMateriais);
                    if (oldTbMateriaisHandOfTbOsMateriaisCollectionNewTbOsMateriais != null && !oldTbMateriaisHandOfTbOsMateriaisCollectionNewTbOsMateriais.equals(tbMateriais)) {
                        oldTbMateriaisHandOfTbOsMateriaisCollectionNewTbOsMateriais.getTbOsMateriaisCollection().remove(tbOsMateriaisCollectionNewTbOsMateriais);
                        oldTbMateriaisHandOfTbOsMateriaisCollectionNewTbOsMateriais = em.merge(oldTbMateriaisHandOfTbOsMateriaisCollectionNewTbOsMateriais);
                    }
                }
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
            Collection<TbApontamentosMateriais> tbApontamentosMateriaisCollectionOrphanCheck = tbMateriais.getTbApontamentosMateriaisCollection();
            for (TbApontamentosMateriais tbApontamentosMateriaisCollectionOrphanCheckTbApontamentosMateriais : tbApontamentosMateriaisCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbMateriais (" + tbMateriais + ") cannot be destroyed since the TbApontamentosMateriais " + tbApontamentosMateriaisCollectionOrphanCheckTbApontamentosMateriais + " in its tbApontamentosMateriaisCollection field has a non-nullable tbMateriaisHand field.");
            }
            Collection<TbOsMateriais> tbOsMateriaisCollectionOrphanCheck = tbMateriais.getTbOsMateriaisCollection();
            for (TbOsMateriais tbOsMateriaisCollectionOrphanCheckTbOsMateriais : tbOsMateriaisCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbMateriais (" + tbMateriais + ") cannot be destroyed since the TbOsMateriais " + tbOsMateriaisCollectionOrphanCheckTbOsMateriais + " in its tbOsMateriaisCollection field has a non-nullable tbMateriaisHand field.");
            }
            Collection<TbProjetosMateriais> tbProjetosMateriaisCollectionOrphanCheck = tbMateriais.getTbProjetosMateriaisCollection();
            for (TbProjetosMateriais tbProjetosMateriaisCollectionOrphanCheckTbProjetosMateriais : tbProjetosMateriaisCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbMateriais (" + tbMateriais + ") cannot be destroyed since the TbProjetosMateriais " + tbProjetosMateriaisCollectionOrphanCheckTbProjetosMateriais + " in its tbProjetosMateriaisCollection field has a non-nullable tbMateriaisHand field.");
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
            Query q = em.createQuery("select object(o) from TbMateriais as o");
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
            Query q = em.createQuery("select count(o) from TbMateriais as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
