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
class ActionAdminMain {

    static List<Evnmt> run(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JpaUtil.init();
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        response.setContentType("application/json");
        if (session.isNew()) {
            out.print("Error");
            response.sendRedirect("./error.html");
        } else {
            //casting du session marche pas!
            System.out.println(session.getAttribute("user"));
            List<Evnmt> evenements = null;
            try {
                evenements = ServiceMetier.consulterListeEvt();
                if (evenements != null) {
                    return evenements;
                }
            } catch (Exception ex) {
                Logger.getLogger(ActionMyDemands.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        JpaUtil.destroy();
        return null;
    }
}
