/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import metier.modele.Adherent;
import metier.modele.Demande;
import metier.service.ServiceMetier;

/**
 *
 * @author alexh
 */
class ActionPosterDemande {

    static void run(HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        if (session.isNew()) {
            out.print("Error");
            response.sendRedirect("./error.html");
        } else {
            //casting du session marche pas!
            System.out.println(session.getAttribute("user"));
            Long sessionUser = (Long) session.getAttribute("user");
            Adherent adherent = null;
            List<Adherent> adherents = ServiceMetier.consulterListeAdherent();
            for (Adherent adh : adherents) {
                if (adh.getId().equals(sessionUser)) {
                    adherent = adh;
                }
            }
            if (adherent != null) {
                //TODO : gerer si la date est passé
                String moment = request.getParameter("moment");
                String activite = request.getParameter("activite");
                int idActivite = Integer.parseInt(activite);
                String jour = request.getParameter("jour");
                int jourInt = Integer.parseInt(jour);
                String mois = request.getParameter("mois");
                int moisInt = Integer.parseInt(mois) - 1;
                String annee = request.getParameter("annee");
                int anneeInt = Integer.parseInt(annee);
                System.out.println(anneeInt);
                anneeInt = anneeInt - 1900;
                Date date = new Date(anneeInt, moisInt, jourInt);
                Date today = new Date();
                System.out.println(date);
                System.out.println(today);
                if (date.before(today)) {
                    out.print("Date invalide");
                    return;
                }
                Demande demande = new Demande(date, moment);
                boolean confirme = ServiceMetier.saveDemande(adherent, demande, idActivite);
                if (confirme) {
                    out.print("Demande cree : " + demande.toString());
                    out.println("Adherent : " + adherent.toString());
                    out.println("Id Activite : " + idActivite);
                } else {
                    out.print("Demande échoué");
                }
            } else {
                out.print("Pas de session active");
            }
        }
    }

}
