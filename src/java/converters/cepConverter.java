/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converters;
 
import java.text.ParseException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.swing.text.MaskFormatter;

@FacesConverter("cepConverter")
public class cepConverter implements Converter {
   
public static String formatString(String value, String pattern) {
        MaskFormatter mf;
        try {
            mf = new MaskFormatter(pattern);
            mf.setValueContainsLiteralCharacters(false);
            return mf.valueToString(value);
        } catch (ParseException ex) {
            return value;
        }
    }

@Override
public String getAsString(FacesContext context, UIComponent component, Object value) {
    
    System.out.println("passou por aqui " + formatString(value.toString(),"#####-###"));
    
    if (value != null)
         return formatString(value.toString(),"#####-###");
    else 
        return null;
}
 
 @Override
 public Object getAsObject(FacesContext context, UIComponent component, String value) {
      
    if (value != null)
         return formatString(value,"#####-###");
    else 
        return null;
 } 
}
 