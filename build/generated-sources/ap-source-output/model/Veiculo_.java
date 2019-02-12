package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Cliente;
import model.Lojista;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-02-12T15:03:20")
@StaticMetamodel(Veiculo.class)
public class Veiculo_ { 

    public static volatile SingularAttribute<Veiculo, String> marca;
    public static volatile SingularAttribute<Veiculo, Lojista> lojistaIdlogista;
    public static volatile SingularAttribute<Veiculo, Integer> idveiculo;
    public static volatile SingularAttribute<Veiculo, Cliente> clienteIdcliente;
    public static volatile SingularAttribute<Veiculo, String> anomodelo;
    public static volatile SingularAttribute<Veiculo, byte[]> imagem;
    public static volatile SingularAttribute<Veiculo, String> anofabricacao;
    public static volatile SingularAttribute<Veiculo, String> modelo;
    public static volatile SingularAttribute<Veiculo, String> placa;

}