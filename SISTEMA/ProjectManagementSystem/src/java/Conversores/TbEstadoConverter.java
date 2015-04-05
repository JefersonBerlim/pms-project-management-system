package Conversores;

import Modelos.TbEstados;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "tbEstadoConverter")
public class TbEstadoConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {

        if (value != null && !value.isEmpty()) {
            return (TbEstados) uiComponent.getAttributes().get(value);
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if (value instanceof TbEstados) {
            TbEstados entity = (TbEstados) value;
            if (entity != null && entity instanceof TbEstados && entity.getHand() != null) {
                uiComponent.getAttributes().put(entity.getHand().toString(), entity);
                return entity.getHand().toString();
            }
        }
        return "";
    }
}
