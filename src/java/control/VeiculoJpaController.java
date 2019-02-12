/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import control.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Cliente;
import model.Lojista;
import model.Veiculo;

/**
 *
 * @author rafae
 */
public class VeiculoJpaController implements Serializable {

    public VeiculoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Veiculo veiculo) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente clienteIdcliente = veiculo.getClienteIdcliente();
            if (clienteIdcliente != null) {
                clienteIdcliente = em.getReference(clienteIdcliente.getClass(), clienteIdcliente.getIdcliente());
                veiculo.setClienteIdcliente(clienteIdcliente);
            }
            Lojista lojistaIdlogista = veiculo.getLojistaIdlogista();
            if (lojistaIdlogista != null) {
                lojistaIdlogista = em.getReference(lojistaIdlogista.getClass(), lojistaIdlogista.getIdlogista());
                veiculo.setLojistaIdlogista(lojistaIdlogista);
            }
            em.persist(veiculo);
            if (clienteIdcliente != null) {
                clienteIdcliente.getVeiculoList().add(veiculo);
                clienteIdcliente = em.merge(clienteIdcliente);
            }
            if (lojistaIdlogista != null) {
                lojistaIdlogista.getVeiculoList().add(veiculo);
                lojistaIdlogista = em.merge(lojistaIdlogista);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Veiculo veiculo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Veiculo persistentVeiculo = em.find(Veiculo.class, veiculo.getIdveiculo());
            Cliente clienteIdclienteOld = persistentVeiculo.getClienteIdcliente();
            Cliente clienteIdclienteNew = veiculo.getClienteIdcliente();
            Lojista lojistaIdlogistaOld = persistentVeiculo.getLojistaIdlogista();
            Lojista lojistaIdlogistaNew = veiculo.getLojistaIdlogista();
            if (clienteIdclienteNew != null) {
                clienteIdclienteNew = em.getReference(clienteIdclienteNew.getClass(), clienteIdclienteNew.getIdcliente());
                veiculo.setClienteIdcliente(clienteIdclienteNew);
            }
            if (lojistaIdlogistaNew != null) {
                lojistaIdlogistaNew = em.getReference(lojistaIdlogistaNew.getClass(), lojistaIdlogistaNew.getIdlogista());
                veiculo.setLojistaIdlogista(lojistaIdlogistaNew);
            }
            veiculo = em.merge(veiculo);
            if (clienteIdclienteOld != null && !clienteIdclienteOld.equals(clienteIdclienteNew)) {
                clienteIdclienteOld.getVeiculoList().remove(veiculo);
                clienteIdclienteOld = em.merge(clienteIdclienteOld);
            }
            if (clienteIdclienteNew != null && !clienteIdclienteNew.equals(clienteIdclienteOld)) {
                clienteIdclienteNew.getVeiculoList().add(veiculo);
                clienteIdclienteNew = em.merge(clienteIdclienteNew);
            }
            if (lojistaIdlogistaOld != null && !lojistaIdlogistaOld.equals(lojistaIdlogistaNew)) {
                lojistaIdlogistaOld.getVeiculoList().remove(veiculo);
                lojistaIdlogistaOld = em.merge(lojistaIdlogistaOld);
            }
            if (lojistaIdlogistaNew != null && !lojistaIdlogistaNew.equals(lojistaIdlogistaOld)) {
                lojistaIdlogistaNew.getVeiculoList().add(veiculo);
                lojistaIdlogistaNew = em.merge(lojistaIdlogistaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = veiculo.getIdveiculo();
                if (findVeiculo(id) == null) {
                    throw new NonexistentEntityException("The veiculo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Veiculo veiculo;
            try {
                veiculo = em.getReference(Veiculo.class, id);
                veiculo.getIdveiculo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The veiculo with id " + id + " no longer exists.", enfe);
            }
            Cliente clienteIdcliente = veiculo.getClienteIdcliente();
            if (clienteIdcliente != null) {
                clienteIdcliente.getVeiculoList().remove(veiculo);
                clienteIdcliente = em.merge(clienteIdcliente);
            }
            Lojista lojistaIdlogista = veiculo.getLojistaIdlogista();
            if (lojistaIdlogista != null) {
                lojistaIdlogista.getVeiculoList().remove(veiculo);
                lojistaIdlogista = em.merge(lojistaIdlogista);
            }
            em.remove(veiculo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Veiculo> findVeiculoEntities() {
        return findVeiculoEntities(true, -1, -1);
    }

    public List<Veiculo> findVeiculoEntities(int maxResults, int firstResult) {
        return findVeiculoEntities(false, maxResults, firstResult);
    }

    private List<Veiculo> findVeiculoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Veiculo.class));
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

    public Veiculo findVeiculo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Veiculo.class, id);
        } finally {
            em.close();
        }
    }

    public int getVeiculoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Veiculo> rt = cq.from(Veiculo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
