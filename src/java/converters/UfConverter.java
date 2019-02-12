
import control.UfJpaController;
import view.EmProvider;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import model.Uf;

@FacesConverter("ConverterUf")
public class UfConverter implements Converter{
    
    private UfJpaController controlUf = new UfJpaController(EmProvider.getInstance().getEntityManagerFactory());
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        return controlUf.findUf(string);
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        Uf t = (Uf)o;
        return t.getUf();
    }
}