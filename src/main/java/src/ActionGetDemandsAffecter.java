/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import dao.JpaUtil;
import java.io.IOException;
import java.io.PrintWriter;
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
class ActionGetDemandsAffecter {

    static List<Evnmt> run(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Evnmt> evenements = null;
        JpaUtil.init();
        //TODO mettre sendRedirect dans html
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        response.setContentType("application/json");
        if (session.isNew()) {
            out.print("Error");
            response.sendRedirect("./error.html");
        } else {
            try {
                evenements = ServiceMetier.consulterListeEvtAValider();
            } catch (Exception ex) {
                Logger.getLogger(ActionMyDemands.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        JpaUtil.destroy();
        return evenements;
    }

}
