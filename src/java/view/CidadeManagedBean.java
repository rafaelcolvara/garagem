package view;

import control.CidadeJpaController;
import control.UfJpaController;
import control.exceptions.IllegalOrphanException;
import control.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import model.Cidade;
import model.Uf;

@ManagedBean
@SessionScoped
public class CidadeManagedBean {

    private CidadeJpaController controlCidade = new  CidadeJpaController();
    private final UfJpaController controlUf = new UfJpaController();
    private List<Cidade> listaCidade = new ArrayList<>();
    private Cidade actualCity = new Cidade();
    private Uf actualUf = new Uf();
    private List<Uf> listaUf = new ArrayList<>();

  

   
    public CidadeManagedBean() {
    }

    public String gotoListaCidades(){
        listaCidade = controlCidade.findCidadeEntities();
        return "/jsf/lista/ListarCidades.xhtml?faces-redirect=true";
    }
    
    public String gotoEditCity(){
         actualUf.setUf(actualCity.getEstado());
         listaUf = controlUf.findUfEntities();
         return "/jsf/cadastro/EditarCidade.xhtml?faces-redirect=true";
    }
    
    public String gotoCadastrarCidade(){
       
        listaUf = controlUf.findUfEntities();
        return "/jsf/cadastro/CadastrarCidade.xhtml?faces-redirect=true";
    }
    public String createCidade() {
        
        actualCity.setEstado(actualUf.getUf());
        controlCidade.create(actualCity);
        actualCity = new Cidade();
        actualUf = new Uf();
        return gotoListaCidades();
    }
    
    public String editarCidade() {
        
        actualCity.setEstado(actualUf.getUf());
        try {
            controlCidade.edit(actualCity);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(CidadeManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(CidadeManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        actualCity = new Cidade();
        actualUf = new Uf();
        return gotoListaCidades();
    }
    public String delete(){
        
        try {
            controlCidade.destroy(actualCity.getIdcidade());
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(CidadeManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(CidadeManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        gotoListaCidades();
        return "/jsf/lista/ListarCidades";
    }
    public List<Cidade> getListaCidade() {
        return listaCidade;
    }

    public void setListaCidade(ArrayList<Cidade> listaCidade) {
        this.listaCidade = listaCidade;
    }

    public CidadeJpaController getControlCidade() {
        return controlCidade;
    }

    public void setControlCidade(CidadeJpaController controlCidade) {
        this.controlCidade = controlCidade;
    }

    public Cidade getActualCity() {
        return actualCity;
    }

    public void setActualCity(Cidade actualCity) {
        this.actualCity = actualCity;
    }
    
     public Uf getActualUf() {
        return actualUf;
    }

    public void setActualUf(Uf actualUf) {
        this.actualUf = actualUf;
    }
    
    public List<Uf> getListaUf() {
        return listaUf;
    }

    public void setListaUf(List<Uf> listaUf) {
        this.listaUf = listaUf;
    }
}
