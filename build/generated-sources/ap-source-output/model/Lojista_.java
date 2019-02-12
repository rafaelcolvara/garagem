package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Cidade;
import model.Veiculo;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-02-12T15:03:20")
@StaticMetamodel(Lojista.class)
public class Lojista_ { 

    public static volatile SingularAttribute<Lojista, String> telefone;
    public static volatile ListAttribute<Lojista, Veiculo> veiculoList;
    public static volatile SingularAttribute<Lojista, String> endereco;
    public static volatile SingularAttribute<Lojista, Integer> numero;
    public static volatile SingularAttribute<Lojista, String> dddtelefone;
    public static volatile SingularAttribute<Lojista, Cidade> cidadeIdcidade;
    public static volatile SingularAttribute<Lojista, String> nome;
    public static volatile SingularAttribute<Lojista, Integer> idlogista;
    public static volatile SingularAttribute<Lojista, String> cep;
    public static volatile SingularAttribute<Lojista, String> site;
    public static volatile SingularAttribute<Lojista, String> complemento;
    public static volatile SingularAttribute<Lojista, Character> tipoContato;
    public static volatile SingularAttribute<Lojista, String> nomeContato;
    public static volatile SingularAttribute<Lojista, Date> dataCadastro;

}