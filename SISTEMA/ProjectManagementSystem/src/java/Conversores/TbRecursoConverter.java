package Conversores;

import Modelos.TbRecursos;
import Modelos.TbServicos;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "tbRecursoConverter")
public class TbRecursoConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {

        if (value != null && !value.isEmpty()) {
            return (TbRecursos) uiComponent.getAttributes().get(value);
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if (value instanceof TbRecursos) {
            TbRecursos entity = (TbRecursos) value;
            if (entity != null && entity instanceof TbRecursos && entity.getHand() != null) {
                uiComponent.getAttributes().put(entity.getHand().toString(), entity);
                return entity.getHand().toString();
            }
        }
        return "";
    }
}
