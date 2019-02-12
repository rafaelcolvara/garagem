
package TesteClientes;

import control.CidadeJpaController;
import control.UfJpaController;
import control.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import view.EmProvider;
import model.Cidade;
import model.Lojista;
import org.junit.Test;

public class TesteAdicionarCidade {
    private EntityManagerFactory entityManagerFactory = EmProvider.getInstance().getEntityManagerFactory();
    private control.CidadeJpaController controlCidade = new CidadeJpaController(entityManagerFactory);
    Cidade toTest = new Cidade();
    
    @Test
    public void AdicionarCidade() throws NonexistentEntityException{
        
        toTest.setIdcidade(40);
        toTest.setNomecidade("SANTANA DO LIVRAMENTO");
        toTest.setEstado("RS");
        toTest.setLojistaList(new ArrayList<Lojista>());
        try {
            controlCidade.edit(toTest);
        } catch (Exception ex) {
            Logger.getLogger(TesteAdicionarCidade.class.getName()).log(Level.SEVERE, null, ex);
        }
        
                
    }
}
