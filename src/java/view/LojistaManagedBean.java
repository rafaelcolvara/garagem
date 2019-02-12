/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import control.LojistaJpaController;
import control.exceptions.IllegalOrphanException;
import control.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import model.Lojista;

/**
 *
 * @author rafae
 */
@ManagedBean
@SessionScoped
public class LojistaManagedBean {

    LojistaJpaController controlLojista = new LojistaJpaController(EmProvider.getInstance().getEntityManagerFactory());
    Lojista actualLojista = new Lojista();
    List<Lojista> listaLojista = new ArrayList();

   
    public LojistaManagedBean() {
    }

    public String createLojista()
    {
        controlLojista.create(actualLojista);
        actualLojista = new Lojista();
        
        return gotoListaLojistas();
    }
    
    public String gotoListaLojistas()
    {
        listaLojista = controlLojista.findLojistaEntities();
        return "/jsf/lista/ListarLojistas.xhtml?faces-redirect=true";
     
    }
    public String gotoEditLojista(){
         
         return "/jsf/cadastro/EditarCidade.xhtml?faces-redirect=true";
    }
    public String delete()
    {
        try {
            controlLojista.destroy(actualLojista.getIdlogista());
        } catch (IllegalOrphanException | NonexistentEntityException ex) {
            Logger.getLogger(CidadeManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        gotoListaLojistas();
        return "/jsf/lista/ListarCidades";
    }
    
    public Lojista getActualLojista() {
        return actualLojista;
    }

    public void setActualLojista(Lojista actualLojista) {
        this.actualLojista = actualLojista;
    }
     public List<Lojista> getListaLojista() {
        return listaLojista;
    }

    public void setListaLojista(List<Lojista> listaLojista) {
        this.listaLojista = listaLojista;
    }
    
    
}
