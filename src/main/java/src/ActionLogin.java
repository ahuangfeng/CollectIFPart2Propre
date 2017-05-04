/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import metier.modele.Adherent;
import metier.service.ServiceMetier;

/**
 *
 * @author alexh
 */
class ActionLogin {

    static void run(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String user = request.getParameter("email");
        HttpSession session = request.getSession();
        Adherent adherent = null;
        try {
            adherent = ServiceMetier.Connexion(user);
        } catch (Exception ex) {
            Logger.getLogger(ActionServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (adherent != null) {
            System.out.println(adherent.getId());
            session.setAttribute("user", adherent.getId());
            response.sendRedirect("./posterDemande.html");
        } else {
            response.sendRedirect("./inscription.html");
        }

    }

}
