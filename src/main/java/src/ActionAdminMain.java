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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import metier.modele.Demande;
import metier.modele.Evnmt;
import metier.service.ServiceMetier;

/**
 *
 * @author alexh
 */
class ActionAdminMain {

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
            List<Evnmt> evenements = null;
            try {
                evenements = ServiceMetier.consulterListeEvtAValider();
                if (evenements != null) {
                    printDemandesById(out, evenements);
                }
            } catch (Exception ex) {
                Logger.getLogger(ActionMyDemands.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private static void printDemandesById(PrintWriter out, List<Evnmt> evenements) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        JsonArray jsonListe = new JsonArray();
        List<Demande> demandes = null;
        Demande d = null;
        for (Evnmt evenement : evenements) {
            demandes = evenement.getMaListeDemandesEVT();
            //TODO : on supose que tous les demandes sont pareil...
            d = demandes.get(0);
            JsonObject jsonEvn = new JsonObject();
            jsonEvn.addProperty("id", evenement.getId());
            jsonEvn.addProperty("denomination", d.getMonActMTO().getDenomination());
            jsonEvn.addProperty("date", evenement.getDate().toString());
            jsonEvn.addProperty("moment", evenement.getMoment());
            jsonEvn.addProperty("payant", d.getMonActMTO().getPayant());
            jsonEvn.addProperty("statut", evenement.isPlanifie());
            jsonListe.add(jsonEvn);
        }
        JsonObject container = new JsonObject();
        container.add("evenements", jsonListe);
        out.println(gson.toJson(container));
    }
    
}
