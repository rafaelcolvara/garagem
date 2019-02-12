package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Lojista;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-02-12T15:03:20")
@StaticMetamodel(Cidade.class)
public class Cidade_ { 

    public static volatile SingularAttribute<Cidade, String> estado;
    public static volatile ListAttribute<Cidade, Lojista> lojistaList;
    public static volatile SingularAttribute<Cidade, String> nomecidade;
    public static volatile SingularAttribute<Cidade, Integer> idcidade;

}