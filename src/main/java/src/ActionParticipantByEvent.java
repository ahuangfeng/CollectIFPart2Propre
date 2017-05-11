/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import dao.JpaUtil;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        List<Adherent> adherents = null;
        try {
            adherents = ServiceMetier.consulterListeParticipant(ev);
        } catch (Exception ex) {
            Logger.getLogger(ActionParticipantByEvent.class.getName()).log(Level.SEVERE, null, ex);
        }
        JpaUtil.destroy();
        return adherents;
    }

}
