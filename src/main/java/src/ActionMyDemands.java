/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import metier.modele.Activite;
import metier.modele.Adherent;
import metier.modele.Demande;
import metier.service.ServiceMetier;
import static src.ActionListeActivites.printListeActivite;

/**
 *
 * @author alexh
 */
class ActionMyDemands {

    static void run(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        response.setContentType("application/json");
        if (session.isNew()) {
            out.print("Error");
            response.sendRedirect("./error.html");
        } else {
            //casting du session marche pas!
            System.out.println(session.getAttribute("user"));
            Long sessionUser = (Long) session.getAttribute("user");
            List<Demande> demandes = null;
            try {
                int sessionInt = (int)(long) sessionUser;
                demandes = ServiceMetier.consulterListeDemandeById(sessionInt);
                if (demandes != null) {
                    printDemandesById(out, demandes);
                }
            } catch (Exception ex) {
                Logger.getLogger(ActionMyDemands.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private static void printDemandesById(PrintWriter out, List<Demande> demandes) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        JsonArray jsonListe = new JsonArray();
        for (Demande demande : demandes) {
            JsonObject jsonDemande = new JsonObject();
            jsonDemande.addProperty("name", demande.getMonActMTO().getDenomination());
            jsonDemande.addProperty("date", demande.getDate().toString());
            jsonDemande.addProperty("moment", demande.getMoment());
            jsonDemande.addProperty("payant",demande.getMonActMTO().getPayant());
            jsonDemande.addProperty("statut", demande.getTraiter());
            jsonListe.add(jsonDemande);
        }
        JsonObject container = new JsonObject();
        container.add("demandes", jsonListe);
        out.println(gson.toJson(container));
    }

}
