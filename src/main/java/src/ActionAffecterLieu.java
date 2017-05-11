/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import dao.JpaUtil;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.service.ServiceMetier;

/**
 *
 * @author alexh
 */
public class ActionAffecterLieu {

    static int run(HttpServletRequest request, HttpServletResponse response) {
        JpaUtil.init();
        String SEvent = request.getParameter("evnmt");
        String SLieu = request.getParameter("lieu");
        int idEvent = Integer.parseInt(SEvent);
        int idLieu = Integer.parseInt(SLieu);
        try {
            ServiceMetier.affecterLieu(idEvent, idLieu);
        } catch (Exception ex) {
            Logger.getLogger(ActionAffecterLieu.class.getName()).log(Level.SEVERE, null, ex);
        }
        JpaUtil.destroy();
        return idEvent;
    }
    
}
