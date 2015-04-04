package Conversores;

import Modelos.TbPaises;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

//@FacesConverter(value = "classeConverter")    
@FacesConverter(value = "tbPaisConverter")
public class TbPaisConverter implements Converter {
    
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && !value.isEmpty()) {
            return (TbPaises) uiComponent.getAttributes().get(value);
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if (value instanceof TbPaises) {
            TbPaises entity = (TbPaises) value;
            if (entity != null && entity instanceof TbPaises && entity.getHand()!= null) {
                uiComponent.getAttributes().put( entity.getHand().toString(), entity);
                return entity.getHand().toString();
            }
        }
        return "";
    }
}