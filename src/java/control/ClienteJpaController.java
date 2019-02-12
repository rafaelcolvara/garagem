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
import model.Veiculo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Cliente;

/**
 *
 * @author rafae
 */
public class ClienteJpaController implements Serializable {

    public ClienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cliente cliente) {
        if (cliente.getVeiculoList() == null) {
            cliente.setVeiculoList(new ArrayList<Veiculo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Veiculo> attachedVeiculoList = new ArrayList<Veiculo>();
            for (Veiculo veiculoListVeiculoToAttach : cliente.getVeiculoList()) {
                veiculoListVeiculoToAttach = em.getReference(veiculoListVeiculoToAttach.getClass(), veiculoListVeiculoToAttach.getIdveiculo());
                attachedVeiculoList.add(veiculoListVeiculoToAttach);
            }
            cliente.setVeiculoList(attachedVeiculoList);
            em.persist(cliente);
            for (Veiculo veiculoListVeiculo : cliente.getVeiculoList()) {
                Cliente oldClienteIdclienteOfVeiculoListVeiculo = veiculoListVeiculo.getClienteIdcliente();
                veiculoListVeiculo.setClienteIdcliente(cliente);
                veiculoListVeiculo = em.merge(veiculoListVeiculo);
                if (oldClienteIdclienteOfVeiculoListVeiculo != null) {
                    oldClienteIdclienteOfVeiculoListVeiculo.getVeiculoList().remove(veiculoListVeiculo);
                    oldClienteIdclienteOfVeiculoListVeiculo = em.merge(oldClienteIdclienteOfVeiculoListVeiculo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cliente cliente) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente persistentCliente = em.find(Cliente.class, cliente.getIdcliente());
            List<Veiculo> veiculoListOld = persistentCliente.getVeiculoList();
            List<Veiculo> veiculoListNew = cliente.getVeiculoList();
            List<String> illegalOrphanMessages = null;
            for (Veiculo veiculoListOldVeiculo : veiculoListOld) {
                if (!veiculoListNew.contains(veiculoListOldVeiculo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Veiculo " + veiculoListOldVeiculo + " since its clienteIdcliente field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Veiculo> attachedVeiculoListNew = new ArrayList<Veiculo>();
            for (Veiculo veiculoListNewVeiculoToAttach : veiculoListNew) {
                veiculoListNewVeiculoToAttach = em.getReference(veiculoListNewVeiculoToAttach.getClass(), veiculoListNewVeiculoToAttach.getIdveiculo());
                attachedVeiculoListNew.add(veiculoListNewVeiculoToAttach);
            }
            veiculoListNew = attachedVeiculoListNew;
            cliente.setVeiculoList(veiculoListNew);
            cliente = em.merge(cliente);
            for (Veiculo veiculoListNewVeiculo : veiculoListNew) {
                if (!veiculoListOld.contains(veiculoListNewVeiculo)) {
                    Cliente oldClienteIdclienteOfVeiculoListNewVeiculo = veiculoListNewVeiculo.getClienteIdcliente();
                    veiculoListNewVeiculo.setClienteIdcliente(cliente);
                    veiculoListNewVeiculo = em.merge(veiculoListNewVeiculo);
                    if (oldClienteIdclienteOfVeiculoListNewVeiculo != null && !oldClienteIdclienteOfVeiculoListNewVeiculo.equals(cliente)) {
                        oldClienteIdclienteOfVeiculoListNewVeiculo.getVeiculoList().remove(veiculoListNewVeiculo);
                        oldClienteIdclienteOfVeiculoListNewVeiculo = em.merge(oldClienteIdclienteOfVeiculoListNewVeiculo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cliente.getIdcliente();
                if (findCliente(id) == null) {
                    throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.");
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
            Cliente cliente;
            try {
                cliente = em.getReference(Cliente.class, id);
                cliente.getIdcliente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Veiculo> veiculoListOrphanCheck = cliente.getVeiculoList();
            for (Veiculo veiculoListOrphanCheckVeiculo : veiculoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cliente (" + cliente + ") cannot be destroyed since the Veiculo " + veiculoListOrphanCheckVeiculo + " in its veiculoList field has a non-nullable clienteIdcliente field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(cliente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cliente> findClienteEntities() {
        return findClienteEntities(true, -1, -1);
    }

    public List<Cliente> findClienteEntities(int maxResults, int firstResult) {
        return findClienteEntities(false, maxResults, firstResult);
    }

    private List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cliente.class));
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

    public Cliente findCliente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cliente> rt = cq.from(Cliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
