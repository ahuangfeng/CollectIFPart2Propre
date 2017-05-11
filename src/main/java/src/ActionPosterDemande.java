/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import dao.JpaUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import metier.modele.Adherent;
import metier.modele.Demande;
import metier.service.ServiceMetier;
import vueJson.PrintConfirmation;

/**
 *
 * @author alexh
 */
class ActionPosterDemande {

    static String run(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JpaUtil.init();
        String res ="";
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        if (session.isNew()) {
            out.print("Error");
            try {
                response.sendRedirect("./error.html");
            } catch (IOException ex) {
                Logger.getLogger(ActionPosterDemande.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            //casting du session marche pas!
            System.out.println("User : " + session.getAttribute("user"));
            Long sessionUser = (Long) session.getAttribute("user");
            Adherent adherent = null;
            List<Adherent> adherents = null;
            try {
                adherents = ServiceMetier.consulterListeAdherent();
            } catch (Exception ex) {
                Logger.getLogger(ActionPosterDemande.class.getName()).log(Level.SEVERE, null, ex);
            }
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
                System.out.println("Annee : " + anneeInt);
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, anneeInt);
                cal.set(Calendar.MONTH, moisInt);
                cal.set(Calendar.DAY_OF_MONTH, jourInt);
                Date date = cal.getTime();
                Date today = new Date();
                System.out.println("Date demande : " + date);
                System.out.println("Date today" + today);
                if (date.before(today)) {
                    return "Date invalide";
                }
                Demande demande = new Demande(date, moment);
                boolean confirme = false;
                try {
                    confirme = ServiceMetier.saveDemande(adherent, demande, idActivite);
                } catch (Exception ex) {
                    Logger.getLogger(ActionPosterDemande.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (confirme) {
                    res =  "Demande crée, en attente d'être traiter par l'administrateur";
                } else {
                    res = "Demande échoué";
                }
            } else {
                PrintConfirmation.print("Pas de session active",response);
                res = "Pas de session active";
            }
        }
        JpaUtil.destroy();
        return res;
    }

}
