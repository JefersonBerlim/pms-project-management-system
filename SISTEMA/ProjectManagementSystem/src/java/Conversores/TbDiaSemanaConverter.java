package Conversores;

import Modelos.TbDiaSemana;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "tbDiaSemanaConverter")
public class TbDiaSemanaConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {

        if (value != null && !value.isEmpty()) {
            return (TbDiaSemana) uiComponent.getAttributes().get(value);
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if (value instanceof TbDiaSemana) {
            TbDiaSemana entity = (TbDiaSemana) value;
            if (entity != null && entity instanceof TbDiaSemana && entity.getHand() != null) {
                uiComponent.getAttributes().put(entity.getHand().toString(), entity);
                return entity.getHand().toString();
            }
        }
        return "";
    }
}
