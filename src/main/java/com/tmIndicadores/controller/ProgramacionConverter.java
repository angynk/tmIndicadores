package com.tmIndicadores.controller;

import com.tmIndicadores.controller.servicios.PorgramacionAppService;
import com.tmIndicadores.controller.servicios.ProgramacionServicios;
import com.tmIndicadores.model.entity.Programacion;
import com.tmIndicadores.view.DuplicarIndicadoresBean;
import org.springframework.beans.factory.annotation.Autowired;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import java.util.Date;

@FacesConverter("programacionConverter")
public class ProgramacionConverter implements Converter {

    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if(value != null && value.trim().length() > 0) {
            try {
//                PorgramacionAppService service = (PorgramacionAppService) fc.getExternalContext().getApplicationMap().get("appService");
                Programacion prog = new Programacion();
                prog.setId(Integer.parseInt(value));
                prog.setFecha(new Date());
                prog.setCuadro("TEST");
                return prog;
            } catch(NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid theme."));
            }
        }
        else {
            return null;
        }
    }

    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if(object != null) {
            return String.valueOf(((Programacion) object).getId());
        }
        else {
            return null;
        }
    }

    }