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
import model.Cidade;
import model.Veiculo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Lojista;
import view.EmProvider;

/**
 *
 * @author rafae
 */
public class LojistaJpaController implements Serializable {

    public LojistaJpaController() {
    
        this.emf = EmProvider.getInstance().getEntityManagerFactory();
    
    }
    
    public LojistaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Lojista lojista) {
        if (lojista.getVeiculoList() == null) {
            lojista.setVeiculoList(new ArrayList<Veiculo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cidade cidadeIdcidade = lojista.getCidadeIdcidade();
            if (cidadeIdcidade != null) {
                cidadeIdcidade = em.getReference(cidadeIdcidade.getClass(), cidadeIdcidade.getIdcidade());
                lojista.setCidadeIdcidade(cidadeIdcidade);
            }
            List<Veiculo> attachedVeiculoList = new ArrayList<Veiculo>();
            for (Veiculo veiculoListVeiculoToAttach : lojista.getVeiculoList()) {
                veiculoListVeiculoToAttach = em.getReference(veiculoListVeiculoToAttach.getClass(), veiculoListVeiculoToAttach.getIdveiculo());
                attachedVeiculoList.add(veiculoListVeiculoToAttach);
            }
            lojista.setVeiculoList(attachedVeiculoList);
            em.persist(lojista);
            if (cidadeIdcidade != null) {
                cidadeIdcidade.getLojistaList().add(lojista);
                cidadeIdcidade = em.merge(cidadeIdcidade);
            }
            for (Veiculo veiculoListVeiculo : lojista.getVeiculoList()) {
                Lojista oldLojistaIdlogistaOfVeiculoListVeiculo = veiculoListVeiculo.getLojistaIdlogista();
                veiculoListVeiculo.setLojistaIdlogista(lojista);
                veiculoListVeiculo = em.merge(veiculoListVeiculo);
                if (oldLojistaIdlogistaOfVeiculoListVeiculo != null) {
                    oldLojistaIdlogistaOfVeiculoListVeiculo.getVeiculoList().remove(veiculoListVeiculo);
                    oldLojistaIdlogistaOfVeiculoListVeiculo = em.merge(oldLojistaIdlogistaOfVeiculoListVeiculo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Lojista lojista) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Lojista persistentLojista = em.find(Lojista.class, lojista.getIdlogista());
            Cidade cidadeIdcidadeOld = persistentLojista.getCidadeIdcidade();
            Cidade cidadeIdcidadeNew = lojista.getCidadeIdcidade();
            List<Veiculo> veiculoListOld = persistentLojista.getVeiculoList();
            List<Veiculo> veiculoListNew = lojista.getVeiculoList();
            List<String> illegalOrphanMessages = null;
            for (Veiculo veiculoListOldVeiculo : veiculoListOld) {
                if (!veiculoListNew.contains(veiculoListOldVeiculo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Veiculo " + veiculoListOldVeiculo + " since its lojistaIdlogista field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (cidadeIdcidadeNew != null) {
                cidadeIdcidadeNew = em.getReference(cidadeIdcidadeNew.getClass(), cidadeIdcidadeNew.getIdcidade());
                lojista.setCidadeIdcidade(cidadeIdcidadeNew);
            }
            List<Veiculo> attachedVeiculoListNew = new ArrayList<Veiculo>();
            for (Veiculo veiculoListNewVeiculoToAttach : veiculoListNew) {
                veiculoListNewVeiculoToAttach = em.getReference(veiculoListNewVeiculoToAttach.getClass(), veiculoListNewVeiculoToAttach.getIdveiculo());
                attachedVeiculoListNew.add(veiculoListNewVeiculoToAttach);
            }
            veiculoListNew = attachedVeiculoListNew;
            lojista.setVeiculoList(veiculoListNew);
            lojista = em.merge(lojista);
            if (cidadeIdcidadeOld != null && !cidadeIdcidadeOld.equals(cidadeIdcidadeNew)) {
                cidadeIdcidadeOld.getLojistaList().remove(lojista);
                cidadeIdcidadeOld = em.merge(cidadeIdcidadeOld);
            }
            if (cidadeIdcidadeNew != null && !cidadeIdcidadeNew.equals(cidadeIdcidadeOld)) {
                cidadeIdcidadeNew.getLojistaList().add(lojista);
                cidadeIdcidadeNew = em.merge(cidadeIdcidadeNew);
            }
            for (Veiculo veiculoListNewVeiculo : veiculoListNew) {
                if (!veiculoListOld.contains(veiculoListNewVeiculo)) {
                    Lojista oldLojistaIdlogistaOfVeiculoListNewVeiculo = veiculoListNewVeiculo.getLojistaIdlogista();
                    veiculoListNewVeiculo.setLojistaIdlogista(lojista);
                    veiculoListNewVeiculo = em.merge(veiculoListNewVeiculo);
                    if (oldLojistaIdlogistaOfVeiculoListNewVeiculo != null && !oldLojistaIdlogistaOfVeiculoListNewVeiculo.equals(lojista)) {
                        oldLojistaIdlogistaOfVeiculoListNewVeiculo.getVeiculoList().remove(veiculoListNewVeiculo);
                        oldLojistaIdlogistaOfVeiculoListNewVeiculo = em.merge(oldLojistaIdlogistaOfVeiculoListNewVeiculo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = lojista.getIdlogista();
                if (findLojista(id) == null) {
                    throw new NonexistentEntityException("The lojista with id " + id + " no longer exists.");
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
            Lojista lojista;
            try {
                lojista = em.getReference(Lojista.class, id);
                lojista.getIdlogista();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The lojista with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Veiculo> veiculoListOrphanCheck = lojista.getVeiculoList();
            for (Veiculo veiculoListOrphanCheckVeiculo : veiculoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Lojista (" + lojista + ") cannot be destroyed since the Veiculo " + veiculoListOrphanCheckVeiculo + " in its veiculoList field has a non-nullable lojistaIdlogista field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Cidade cidadeIdcidade = lojista.getCidadeIdcidade();
            if (cidadeIdcidade != null) {
                cidadeIdcidade.getLojistaList().remove(lojista);
                cidadeIdcidade = em.merge(cidadeIdcidade);
            }
            em.remove(lojista);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Lojista> findLojistaEntities() {
        return findLojistaEntities(true, -1, -1);
    }

    public List<Lojista> findLojistaEntities(int maxResults, int firstResult) {
        return findLojistaEntities(false, maxResults, firstResult);
    }

    private List<Lojista> findLojistaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Lojista.class));
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

    public Lojista findLojista(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Lojista.class, id);
        } finally {
            em.close();
        }
    }

    public int getLojistaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Lojista> rt = cq.from(Lojista.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
