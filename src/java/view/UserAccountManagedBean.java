package view;

import javax.faces.bean.ManagedBean;
import control.UserAccountJpaController;
import control.exceptions.IllegalOrphanException;
import control.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;
import model.UserAccount;

@ManagedBean(name = "UserAccountManagedBean")
@SessionScoped
public class UserAccountManagedBean {

    private UserAccount ActualUserAccount = new UserAccount();

    //lists
    private ArrayList<UserAccount> listOfUserAccounts = new ArrayList<>();

    //controls
    private UserAccountJpaController controlUserAccount = new UserAccountJpaController(EmProvider.getInstance().getEntityManagerFactory());

    //auxiliary
    private String typePassword;
    private String verifyPassword;
    private String filterUser;

    //Administrator Access
    private String adminLogin;
    private String adminPassword;

    public UserAccountManagedBean() {
    }

    //gotos
    public String gotoIndex() {
        return "index?faces-redirect=true";
    }

    public String gotoAddAccounts() {
        ActualUserAccount = new UserAccount();
        return "addAccounts.xhtml?faces-redirect=true";
    }

    public String gotoListUsers() {
        loadUserAccounts();
        return "/public/manageAccounts/ManageUserAccounts.xhtml?faces-redirect=true";
    }

    public String gotoEditMyProfile() {
        String userlogin = ManageSessions.getUserId();
        ActualUserAccount = controlUserAccount.findUserAccount(userlogin);
        return "/public/manageAccounts/EditMyProfile.xhtml?faces-redirect=true";
    }

    public String gotoManageTravel() {
        return "/public/manageAccounts/ManageTravels.xhtml?faces-redirect=true";
    }

    public String gotoManagePermissions() {
        return "/Admin/managePermissions.xhtml?faces-redirect=true";
    }

    public String gotoEditPermissions() {
        return "/editPermissions.xhtml?faces-redirect=true";
    }

    //loads
    
    public void loadUserAccounts() {
        listOfUserAccounts = new ArrayList(controlUserAccount.findUserAccountEntities());
    }

    public void clearFields() {
        adminPassword = "";
        ActualUserAccount = new UserAccount();
    }

    //filters
    public void filterUsersByName() {
        listOfUserAccounts.clear();
        EntityManager em = EmProvider.getInstance().getEntityManagerFactory().createEntityManager();
        List<UserAccount> users = em.createQuery("SELECT a FROM UserAccount a WHERE a.userLogin LIKE :filterUser", UserAccount.class)
                .setParameter("filterUser", "%" + filterUser + "%")
                .getResultList();
        listOfUserAccounts = new ArrayList<>(users);
    }

    //verification
    public boolean isAnyoneLoggedIn() {
        boolean isLogado = false;
        if (ManageSessions.getUserId() != null) {
            isLogado = true;
        }
        return isLogado;
    }

    public boolean adminIsLoggedIn() {
        boolean isLogado = false;
        if (ManageSessions.getLoggedAdmin() != null) {
            isLogado = true;
        }
        return isLogado;
    }

    public String validateUsernamePassword() {
        if (validateUser(ActualUserAccount)) {
            HttpSession session = ManageSessions.getSession();
            session.setAttribute("username", ActualUserAccount.getUserName());
            session.setAttribute("userid", ActualUserAccount.getUserLogin());
            return "/jsf/principal/principal.xhtml?faces-redirect=true";
        } else {

            return "/index?faces-redirect=true";
        }
    }
 

    public String logout() {
        HttpSession session = ManageSessions.getSession();
        session.invalidate();
        return "/index?faces-redirect=true";
    }
    
    public boolean validateUser(UserAccount comparar) {
        UserAccount validate = controlUserAccount.findUserAccount(ActualUserAccount.getUserLogin());
        if (validate == null) {
            return false;
        }
        if (validate.getUserLogin().equals(comparar.getUserLogin())
                && validate.getPassword().equals(comparar.getPassword())) {
            ActualUserAccount = validate;
            return true;
        }
        return false;
    }

    //saves
    public String saveUserAccount() {
        try {
            if (typePassword != null && typePassword.equals(verifyPassword)) {
                ActualUserAccount.setIsAdministrator(false);
                ActualUserAccount.setPassword(typePassword);
                controlUserAccount.create(ActualUserAccount);
                HttpSession session = ManageSessions.getSession();
                session.setAttribute("username", ActualUserAccount.getUserName());
                session.setAttribute("userid", ActualUserAccount.getUserLogin());
                return "/public/manageTravel/ManageTravel?faces-redirect=true";
            } else {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("Opa! As senhas nao estao iguais", "Erro"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "#";
    }

 

    //edits
    

    public String editUserAccount() {
        try {
            if (typePassword != null && typePassword.equals(verifyPassword)) {
                ActualUserAccount.setPassword(typePassword);
                controlUserAccount.edit(ActualUserAccount);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return gotoListUsers();
    }

    //destroys
    
   

    //getsets
    public UserAccount getActualUserAccount() {
        return ActualUserAccount;
    }

    public void setActualUserAccount(UserAccount actualUserAccount) {
        this.ActualUserAccount = actualUserAccount;
    }

    public ArrayList<UserAccount> getListOfUserAccounts() {
        return listOfUserAccounts;
    }

    public void setListOfUserAccounts(ArrayList<UserAccount> listOfUserAccounts) {
        this.listOfUserAccounts = listOfUserAccounts;
    }

    public String getTypePassword() {
        return typePassword;
    }

    public void setTypePassword(String typePassword) {
        this.typePassword = typePassword;
    }

    public String getVerifyPassword() {
        return verifyPassword;
    }

    public void setVerifyPassword(String verifyPassword) {
        this.verifyPassword = verifyPassword;
    }

    public String getAdminLogin() {
        return adminLogin;
    }

    public void setAdminLogin(String adminLogin) {
        this.adminLogin = adminLogin;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public String getFilterUser() {
        return filterUser;
    }

    public void setFilterUser(String filterUser) {
        this.filterUser = filterUser;
    }
    
  
}