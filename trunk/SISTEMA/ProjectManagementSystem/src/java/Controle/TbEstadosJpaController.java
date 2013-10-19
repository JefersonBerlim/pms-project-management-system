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
import Entidades.TbPaises;
import Entidades.TbCidades;
import Entidades.TbEstados;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;

/**
 *
 * @author berlim
 */
public class TbEstadosJpaController implements Serializable {

    public TbEstadosJpaController() {
        emf = Persistence.createEntityManagerFactory("ProjectManagementSystemPU");
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TbEstados tbEstados) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (tbEstados.getTbCidadesCollection() == null) {
            tbEstados.setTbCidadesCollection(new ArrayList<TbCidades>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbPaises tbPaisesHand = tbEstados.getTbPaisesHand();
            if (tbPaisesHand != null) {
                tbPaisesHand = em.getReference(tbPaisesHand.getClass(), tbPaisesHand.getHand());
                tbEstados.setTbPaisesHand(tbPaisesHand);
            }
            Collection<TbCidades> attachedTbCidadesCollection = new ArrayList<TbCidades>();
            for (TbCidades tbCidadesCollectionTbCidadesToAttach : tbEstados.getTbCidadesCollection()) {
                tbCidadesCollectionTbCidadesToAttach = em.getReference(tbCidadesCollectionTbCidadesToAttach.getClass(), tbCidadesCollectionTbCidadesToAttach.getHand());
                attachedTbCidadesCollection.add(tbCidadesCollectionTbCidadesToAttach);
            }
            tbEstados.setTbCidadesCollection(attachedTbCidadesCollection);
            em.persist(tbEstados);
            if (tbPaisesHand != null) {
                tbPaisesHand.getTbEstadosCollection().add(tbEstados);
                tbPaisesHand = em.merge(tbPaisesHand);
            }
            for (TbCidades tbCidadesCollectionTbCidades : tbEstados.getTbCidadesCollection()) {
                TbEstados oldTbEstadosHandOfTbCidadesCollectionTbCidades = tbCidadesCollectionTbCidades.getTbEstadosHand();
                tbCidadesCollectionTbCidades.setTbEstadosHand(tbEstados);
                tbCidadesCollectionTbCidades = em.merge(tbCidadesCollectionTbCidades);
                if (oldTbEstadosHandOfTbCidadesCollectionTbCidades != null) {
                    oldTbEstadosHandOfTbCidadesCollectionTbCidades.getTbCidadesCollection().remove(tbCidadesCollectionTbCidades);
                    oldTbEstadosHandOfTbCidadesCollectionTbCidades = em.merge(oldTbEstadosHandOfTbCidadesCollectionTbCidades);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTbEstados(tbEstados.getHand()) != null) {
                throw new PreexistingEntityException("TbEstados " + tbEstados + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TbEstados tbEstados) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TbEstados persistentTbEstados = em.find(TbEstados.class, tbEstados.getHand());
            TbPaises tbPaisesHandOld = persistentTbEstados.getTbPaisesHand();
            TbPaises tbPaisesHandNew = tbEstados.getTbPaisesHand();
            Collection<TbCidades> tbCidadesCollectionOld = persistentTbEstados.getTbCidadesCollection();
            Collection<TbCidades> tbCidadesCollectionNew = tbEstados.getTbCidadesCollection();
            List<String> illegalOrphanMessages = null;
            for (TbCidades tbCidadesCollectionOldTbCidades : tbCidadesCollectionOld) {
                if (!tbCidadesCollectionNew.contains(tbCidadesCollectionOldTbCidades)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TbCidades " + tbCidadesCollectionOldTbCidades + " since its tbEstadosHand field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tbPaisesHandNew != null) {
                tbPaisesHandNew = em.getReference(tbPaisesHandNew.getClass(), tbPaisesHandNew.getHand());
                tbEstados.setTbPaisesHand(tbPaisesHandNew);
            }
            Collection<TbCidades> attachedTbCidadesCollectionNew = new ArrayList<TbCidades>();
            for (TbCidades tbCidadesCollectionNewTbCidadesToAttach : tbCidadesCollectionNew) {
                tbCidadesCollectionNewTbCidadesToAttach = em.getReference(tbCidadesCollectionNewTbCidadesToAttach.getClass(), tbCidadesCollectionNewTbCidadesToAttach.getHand());
                attachedTbCidadesCollectionNew.add(tbCidadesCollectionNewTbCidadesToAttach);
            }
            tbCidadesCollectionNew = attachedTbCidadesCollectionNew;
            tbEstados.setTbCidadesCollection(tbCidadesCollectionNew);
            tbEstados = em.merge(tbEstados);
            if (tbPaisesHandOld != null && !tbPaisesHandOld.equals(tbPaisesHandNew)) {
                tbPaisesHandOld.getTbEstadosCollection().remove(tbEstados);
                tbPaisesHandOld = em.merge(tbPaisesHandOld);
            }
            if (tbPaisesHandNew != null && !tbPaisesHandNew.equals(tbPaisesHandOld)) {
                tbPaisesHandNew.getTbEstadosCollection().add(tbEstados);
                tbPaisesHandNew = em.merge(tbPaisesHandNew);
            }
            for (TbCidades tbCidadesCollectionNewTbCidades : tbCidadesCollectionNew) {
                if (!tbCidadesCollectionOld.contains(tbCidadesCollectionNewTbCidades)) {
                    TbEstados oldTbEstadosHandOfTbCidadesCollectionNewTbCidades = tbCidadesCollectionNewTbCidades.getTbEstadosHand();
                    tbCidadesCollectionNewTbCidades.setTbEstadosHand(tbEstados);
                    tbCidadesCollectionNewTbCidades = em.merge(tbCidadesCollectionNewTbCidades);
                    if (oldTbEstadosHandOfTbCidadesCollectionNewTbCidades != null && !oldTbEstadosHandOfTbCidadesCollectionNewTbCidades.equals(tbEstados)) {
                        oldTbEstadosHandOfTbCidadesCollectionNewTbCidades.getTbCidadesCollection().remove(tbCidadesCollectionNewTbCidades);
                        oldTbEstadosHandOfTbCidadesCollectionNewTbCidades = em.merge(oldTbEstadosHandOfTbCidadesCollectionNewTbCidades);
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
                Integer id = tbEstados.getHand();
                if (findTbEstados(id) == null) {
                    throw new NonexistentEntityException("The tbEstados with id " + id + " no longer exists.");
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
            TbEstados tbEstados;
            try {
                tbEstados = em.getReference(TbEstados.class, id);
                tbEstados.getHand();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tbEstados with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TbCidades> tbCidadesCollectionOrphanCheck = tbEstados.getTbCidadesCollection();
            for (TbCidades tbCidadesCollectionOrphanCheckTbCidades : tbCidadesCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TbEstados (" + tbEstados + ") cannot be destroyed since the TbCidades " + tbCidadesCollectionOrphanCheckTbCidades + " in its tbCidadesCollection field has a non-nullable tbEstadosHand field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            TbPaises tbPaisesHand = tbEstados.getTbPaisesHand();
            if (tbPaisesHand != null) {
                tbPaisesHand.getTbEstadosCollection().remove(tbEstados);
                tbPaisesHand = em.merge(tbPaisesHand);
            }
            em.remove(tbEstados);
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

    public List<TbEstados> findTbEstadosEntities() {
        return findTbEstadosEntities(true, -1, -1);
    }

    public List<TbEstados> findTbEstadosEntities(int maxResults, int firstResult) {
        return findTbEstadosEntities(false, maxResults, firstResult);
    }

    private List<TbEstados> findTbEstadosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from TbEstados as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public TbEstados findTbEstados(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TbEstados.class, id);
        } finally {
            em.close();
        }
    }

    public int getTbEstadosCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from TbEstados as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
