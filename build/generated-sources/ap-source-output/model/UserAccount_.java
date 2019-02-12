package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-02-12T15:03:20")
@StaticMetamodel(UserAccount.class)
public class UserAccount_ { 

    public static volatile SingularAttribute<UserAccount, String> userLogin;
    public static volatile SingularAttribute<UserAccount, String> password;
    public static volatile SingularAttribute<UserAccount, String> phone;
    public static volatile SingularAttribute<UserAccount, String> userName;
    public static volatile SingularAttribute<UserAccount, Boolean> isAdministrator;
    public static volatile SingularAttribute<UserAccount, String> email;

}