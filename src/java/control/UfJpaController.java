/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import control.exceptions.NonexistentEntityException;
import control.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Uf;
import view.EmProvider;

/**
 *
 * @author rafae
 */
public class UfJpaController implements Serializable {

    public UfJpaController()
    {
        this.emf = EmProvider.getInstance().getEntityManagerFactory();
    }
    
    public UfJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Uf uf) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(uf);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUf(uf.getUf()) != null) {
                throw new PreexistingEntityException("Uf " + uf + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Uf uf) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            uf = em.merge(uf);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = uf.getUf();
                if (findUf(id) == null) {
                    throw new NonexistentEntityException("The uf with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Uf uf;
            try {
                uf = em.getReference(Uf.class, id);
                uf.getUf();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The uf with id " + id + " no longer exists.", enfe);
            }
            em.remove(uf);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Uf> findUfEntities() {
        return findUfEntities(true, -1, -1);
    }

    public List<Uf> findUfEntities(int maxResults, int firstResult) {
        return findUfEntities(false, maxResults, firstResult);
    }

    private List<Uf> findUfEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Uf.class));
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

    public Uf findUf(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Uf.class, id);
        } finally {
            em.close();
        }
    }

    public int getUfCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Uf> rt = cq.from(Uf.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
