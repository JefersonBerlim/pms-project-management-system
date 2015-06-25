package Conversores;

import Modelos.TbMarcosServicos;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "tbMarcosServicosConverter")
public class TbMarcosServicosConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {

        if (value != null && !value.isEmpty()) {
            return (TbMarcosServicos) uiComponent.getAttributes().get(value);
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if (value instanceof TbMarcosServicos) {
            TbMarcosServicos entity = (TbMarcosServicos) value;
            if (entity != null && entity instanceof TbMarcosServicos && entity.getHand() != null) {
                uiComponent.getAttributes().put(entity.getHand().toString(), entity);
                return entity.getHand().toString();
            }
        }
        return "";
    }
}
