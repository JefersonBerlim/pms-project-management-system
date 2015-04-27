/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conversores;

import java.util.Calendar;
import java.util.Date;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author jeferson
 */
@FacesConverter(value = "stringEmTime")
public class StringEmTime {

    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {

        Calendar calendario = Calendar.getInstance();
        Date data = new Date();
        int nHora;
        int nMinuto;

        nHora = Integer.parseInt(value.substring(0, 2));
        nMinuto = Integer.parseInt(value.substring(3, 5));

        calendario.setTime(data);
        calendario.set(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, nHora, nMinuto);

        data = calendario.getTime();

        return data;

    }

    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {

        return "";
    }

}
