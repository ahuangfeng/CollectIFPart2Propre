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
import metier.modele.Adherent;
import metier.modele.Evnmt;
import metier.service.ServiceMetier;

/**
 *
 * @author alexh
 */
public class ActionParticipantByEvent {

    static List<Adherent> run(HttpServletRequest request, HttpServletResponse response, Evnmt ev) {
        JpaUtil.init();
        HttpSession session = request.getSession();
        List<Adherent> adherents = null;
        if (session.isNew()) {
            try {
                response.sendRedirect("./error.html");
            } catch (IOException ex) {
                Logger.getLogger(ActionGetEventAValider.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                adherents = ServiceMetier.consulterListeParticipant(ev);
            } catch (Exception ex) {
                Logger.getLogger(ActionParticipantByEvent.class.getName()).log(Level.SEVERE, null, ex);
            }
            JpaUtil.destroy();
        }
        return adherents;
    }

}
