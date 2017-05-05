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
import metier.service.ServiceMetier;

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
        String date = null;
        int year = 0;
        int month = 0;
        int jourInt = 0;
        String day ="";
        String moisString = "";
        for (Demande demande : demandes) {
            JsonObject jsonDemande = new JsonObject();
            jsonDemande.addProperty("id", demande.getId());
            jsonDemande.addProperty("name", demande.getMonActMTO().getDenomination());
            year = 1900 + demande.getDate().getYear();
            month = demande.getDate().getMonth()+1;
            jourInt = demande.getDate().getDate();
            if(jourInt<10){
                day="0"+jourInt;
            }else{
                day = String.valueOf(jourInt);
            }
            if(month<10){
                moisString="0"+month;
            }else{
                moisString = String.valueOf(month);
            }
            date = day+"/"+moisString+'/'+year;
            jsonDemande.addProperty("date", date);
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
