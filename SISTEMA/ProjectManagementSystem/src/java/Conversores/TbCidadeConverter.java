package Conversores;

import Modelos.TbCidades;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "tbCidadeConverter")
public class TbCidadeConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {

        if (value != null && !value.isEmpty()) {
            return (TbCidades) uiComponent.getAttributes().get(value);
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if (value instanceof TbCidades) {
            TbCidades entity = (TbCidades) value;
            if (entity != null && entity instanceof TbCidades && entity.getHand() != null) {
                uiComponent.getAttributes().put(entity.getHand().toString(), entity);
                return entity.getHand().toString();
            }
        }
        return "";
    }
}
