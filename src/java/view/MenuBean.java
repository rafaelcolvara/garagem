
package view;

import control.UfJpaController;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import model.Uf;

@ManagedBean
@RequestScoped
public class MenuBean {
    
    private UfJpaController controlUf = new UfJpaController(EmProvider.getInstance().getEntityManagerFactory());
    private List<Uf> listaUf = new ArrayList<>();
    
    public MenuBean() {
    }
    
    public String loadLojista() {
        System.out.println("view.MenuBean.loadLojista()");
        return "/jsf/cadastro/CadastrarLojista.xhtml?faces-redirect=true";
    }
    
    public String gotoCadastrarCidade(){
        
        listaUf = controlUf.findUfEntities();
        return "/jsf/cadastro/CadastrarCidade.xhtml?faces-redirect=true";
    }
}
