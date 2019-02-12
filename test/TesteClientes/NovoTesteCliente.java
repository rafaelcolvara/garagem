
package TesteClientes;

import control.CidadeJpaController;
import control.ClienteJpaController;
import control.LojistaJpaController;
import control.VeiculoJpaController;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import model.Cliente;
import model.Lojista;
import model.Veiculo;
import model.Cidade;

import org.junit.Test;
import view.EmProvider;


public class NovoTesteCliente {
    @Test
    public void testarrelacionamento(){
         EntityManagerFactory entityManagerFactory = EmProvider.getInstance().getEntityManagerFactory();
         ClienteJpaController CliControl = new ClienteJpaController(entityManagerFactory);
         VeiculoJpaController VeiControl = new VeiculoJpaController(entityManagerFactory);
         LojistaJpaController LojControl = new LojistaJpaController(entityManagerFactory);
         CidadeJpaController  CidControl = new CidadeJpaController(entityManagerFactory);
         
         
         Cliente toAdd = new Cliente();
         toAdd.setNome("Gilberto Farias");
         toAdd.setSexo("M");
         CliControl.create(toAdd);
         
         Cliente toAdd2 = new Cliente();
         toAdd2.setNome("Fernanda Lima");
         toAdd2.setSexo("F");
         CliControl.create(toAdd2);
         
         Cidade toAddCidade = new Cidade();
         toAddCidade.setIdcidade(1);
         toAddCidade.setEstado("SP");
         toAddCidade.setNomecidade("São Paulo");
        try {
            CidControl.create(toAddCidade);
        } catch (Exception ex) {
            Logger.getLogger(NovoTesteCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
         
         Lojista lojAdd = new Lojista();
         java.util.Date dataUtil = new java.util.Date();
         java.sql.Date dataSql;
         dataSql = new java.sql.Date(dataUtil.getTime());
      
         lojAdd.setIdlogista(1);
         lojAdd.setNome("Loja 10");
         lojAdd.setEndereco("rua das alamedas");
         lojAdd.setDataCadastro(dataSql);
         lojAdd.setComplemento("Complem");
         lojAdd.setDddtelefone("011");
         lojAdd.setNomeContato("Fernando");
         lojAdd.setNumero(100);
         lojAdd.setSite("www.loja10.com.br");
         lojAdd.setTelefone("999201001");
         lojAdd.setDddtelefone("011");
         lojAdd.setTipoContato('C');
         lojAdd.setCidadeIdcidade(toAddCidade);
         LojControl.create(lojAdd);
         
         
         Veiculo toAddVei = new Veiculo();
         toAddVei.setMarca("Land Rover");
         toAddVei.setAnofabricacao("2018");
         toAddVei.setAnomodelo("2018");
         toAddVei.setModelo("Freelander");
         toAddVei.setLojistaIdlogista(lojAdd);
         toAddVei.setClienteIdcliente(toAdd2);
         VeiControl.create(toAddVei);
       
         

         toAddVei = new Veiculo();
         toAddVei.setMarca("Wolksvagen");
         toAddVei.setAnofabricacao("1996");
         toAddVei.setAnomodelo("1996");
         toAddVei.setModelo("Fusca");
         toAddVei.setLojistaIdlogista(lojAdd);
         toAddVei.setClienteIdcliente(toAdd);
         
         
         
    }
}
