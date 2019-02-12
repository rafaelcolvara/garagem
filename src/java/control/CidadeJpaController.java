/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import control.exceptions.IllegalOrphanException;
import control.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Lojista;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Cidade;
import view.EmProvider;

/**
 *
 * @author rafae
 */
public class CidadeJpaController implements Serializable {

    public CidadeJpaController()
    {
       this.emf =  EmProvider.getInstance().getEntityManagerFactory();
    }
    public CidadeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cidade cidade) {
        if (cidade.getLojistaList() == null) {
            cidade.setLojistaList(new ArrayList<Lojista>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Lojista> attachedLojistaList = new ArrayList<Lojista>();
            for (Lojista lojistaListLojistaToAttach : cidade.getLojistaList()) {
                lojistaListLojistaToAttach = em.getReference(lojistaListLojistaToAttach.getClass(), lojistaListLojistaToAttach.getIdlogista());
                attachedLojistaList.add(lojistaListLojistaToAttach);
            }
            cidade.setLojistaList(attachedLojistaList);
            em.persist(cidade);
            for (Lojista lojistaListLojista : cidade.getLojistaList()) {
                Cidade oldCidadeIdcidadeOfLojistaListLojista = lojistaListLojista.getCidadeIdcidade();
                lojistaListLojista.setCidadeIdcidade(cidade);
                lojistaListLojista = em.merge(lojistaListLojista);
                if (oldCidadeIdcidadeOfLojistaListLojista != null) {
                    oldCidadeIdcidadeOfLojistaListLojista.getLojistaList().remove(lojistaListLojista);
                    oldCidadeIdcidadeOfLojistaListLojista = em.merge(oldCidadeIdcidadeOfLojistaListLojista);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cidade cidade) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cidade persistentCidade = em.find(Cidade.class, cidade.getIdcidade());
            List<Lojista> lojistaListOld = persistentCidade.getLojistaList();
            List<Lojista> lojistaListNew = cidade.getLojistaList();
            List<String> illegalOrphanMessages = null;
            for (Lojista lojistaListOldLojista : lojistaListOld) {
                if (!lojistaListNew.contains(lojistaListOldLojista)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Lojista " + lojistaListOldLojista + " since its cidadeIdcidade field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Lojista> attachedLojistaListNew = new ArrayList<Lojista>();
            for (Lojista lojistaListNewLojistaToAttach : lojistaListNew) {
                lojistaListNewLojistaToAttach = em.getReference(lojistaListNewLojistaToAttach.getClass(), lojistaListNewLojistaToAttach.getIdlogista());
                attachedLojistaListNew.add(lojistaListNewLojistaToAttach);
            }
            lojistaListNew = attachedLojistaListNew;
            cidade.setLojistaList(lojistaListNew);
            cidade = em.merge(cidade);
            for (Lojista lojistaListNewLojista : lojistaListNew) {
                if (!lojistaListOld.contains(lojistaListNewLojista)) {
                    Cidade oldCidadeIdcidadeOfLojistaListNewLojista = lojistaListNewLojista.getCidadeIdcidade();
                    lojistaListNewLojista.setCidadeIdcidade(cidade);
                    lojistaListNewLojista = em.merge(lojistaListNewLojista);
                    if (oldCidadeIdcidadeOfLojistaListNewLojista != null && !oldCidadeIdcidadeOfLojistaListNewLojista.equals(cidade)) {
                        oldCidadeIdcidadeOfLojistaListNewLojista.getLojistaList().remove(lojistaListNewLojista);
                        oldCidadeIdcidadeOfLojistaListNewLojista = em.merge(oldCidadeIdcidadeOfLojistaListNewLojista);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cidade.getIdcidade();
                if (findCidade(id) == null) {
                    throw new NonexistentEntityException("The cidade with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cidade cidade;
            try {
                cidade = em.getReference(Cidade.class, id);
                cidade.getIdcidade();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cidade with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Lojista> lojistaListOrphanCheck = cidade.getLojistaList();
            for (Lojista lojistaListOrphanCheckLojista : lojistaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cidade (" + cidade + ") cannot be destroyed since the Lojista " + lojistaListOrphanCheckLojista + " in its lojistaList field has a non-nullable cidadeIdcidade field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(cidade);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cidade> findCidadeEntities() {
        return findCidadeEntities(true, -1, -1);
    }

    public List<Cidade> findCidadeEntities(int maxResults, int firstResult) {
        return findCidadeEntities(false, maxResults, firstResult);
    }

    private List<Cidade> findCidadeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cidade.class));
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

    public Cidade findCidade(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cidade.class, id);
        } finally {
            em.close();
        }
    }

    public int getCidadeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cidade> rt = cq.from(Cidade.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
