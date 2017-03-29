package com.tmIndicadores.view;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;

@ManagedBean(name="menu")
@SessionScoped
public class MenuBean {

    public void refreshBusesProgramados(){
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(ec.getRequestContextPath()
                    + "/secured/busesProgramados.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshChart(){
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(ec.getRequestContextPath()
                    + "/secured/extendedChart.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
