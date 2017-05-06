/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import dao.JpaUtil;
import java.io.IOException;
import java.io.PrintWriter;
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
        JpaUtil.init();
        String user = request.getParameter("email");
        HttpSession session = request.getSession();
        Adherent adherent = null;
        try {
            adherent = ServiceMetier.Connexion(user);
        } catch (Exception ex) {
            Logger.getLogger(ActionServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        PrintWriter out = response.getWriter();
        if (adherent != null && !("admin@admin.com".equals(adherent.getMail()))){
            System.out.println(adherent.getId());
            session.setAttribute("user", adherent.getId());
            out.print(adherent.getId());
//            response.sendRedirect("./posterDemande.html");
        } else if(adherent != null && "admin@admin.com".equals(adherent.getMail())){
            out.print("admin");
        }else{
            out.print("null");
//            response.sendRedirect("./inscription.html");
        }
        JpaUtil.destroy();

    }

}
