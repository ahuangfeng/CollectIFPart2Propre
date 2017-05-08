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
import metier.modele.Adherent;
import metier.service.ServiceMetier;

/**
 *
 */
public class ActionInscription {

    static void run(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JpaUtil.init();
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String adresse = request.getParameter("adresse");
        String mail = request.getParameter("email");
        Adherent adherent = new Adherent(nom, prenom, mail, adresse);
        boolean confirmation = false;
        try {
            confirmation = ServiceMetier.saveAdherent(adherent);
            out.print(confirmation);
        } catch (Exception ex) {
            Logger.getLogger(ActionInscription.class.getName()).log(Level.SEVERE, null, ex);
        }
        JpaUtil.destroy();
    }
}
