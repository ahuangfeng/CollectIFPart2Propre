/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import dao.JpaUtil;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import metier.modele.Evnmt;
import metier.service.ServiceMetier;

/**
 *
 * @author alexh
 */
public class ActionGetEventAValider {

    static Evnmt run(HttpServletRequest request, HttpServletResponse response) {
        JpaUtil.init();
        Evnmt evenAValider = null;
        HttpSession session = request.getSession();
        if (session.isNew()) {
            try {
                response.sendRedirect("./error.html");
            } catch (IOException ex) {
                Logger.getLogger(ActionGetEventAValider.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            String idEvent = request.getParameter("idEvent");
            long idL = Long.parseLong(idEvent);
            try {
                List<Evnmt> evnmts = ServiceMetier.consulterListeEvt();
                for (Evnmt evnmt : evnmts) {
                    if (idL == evnmt.getId()) {
                        evenAValider = evnmt;
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(ActionGetEventAValider.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        JpaUtil.destroy();
        return evenAValider;
    }

}
