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
import dao.JpaUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
class ActionGetDemandsAffecter {

    static void run(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JpaUtil.init();
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        response.setContentType("application/json");
        if (session.isNew()) {
            out.print("Error");
            response.sendRedirect("./error.html");
        } else {
            List<Evnmt> evenements = null;
            try {
                evenements = ServiceMetier.consulterListeEvtAValider();
                if (evenements != null) {
                    printEvenementsAffecter(out, evenements);
                } else {
                    printEventVide(out, evenements);
                }
            } catch (Exception ex) {
                Logger.getLogger(ActionMyDemands.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        JpaUtil.destroy();
    }

    private static void printEvenementsAffecter(PrintWriter out, List<Evnmt> evenements) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        JsonArray jsonListe = new JsonArray();
        String date = null;
        int year = 0;
        int month = 0;
        int jourInt = 0;
        String day = "";
        String moisString = "";
        List<Demande> demandes = null;
        Demande d = null;
        Calendar calendar = new GregorianCalendar();
        for (Evnmt evenement : evenements) {
            //TODO: On supose que toutes les demandes sont les meme!?
            demandes = evenement.getMaListeDemandesEVT();
            d = demandes.get(0);
            calendar.setTime(d.getDate());
            JsonObject jsonEvent = new JsonObject();
            jsonEvent.addProperty("id", evenement.getId());
            jsonEvent.addProperty("denomination", d.getMonActMTO().getDenomination());
            year = 1900 + calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH) + 1;
            jourInt = calendar.get(Calendar.DAY_OF_MONTH);
            if (jourInt < 10) {
                day = "0" + jourInt;
            } else {
                day = String.valueOf(jourInt);
            }
            if (month < 10) {
                moisString = "0" + month;
            } else {
                moisString = String.valueOf(month);
            }
            date = day + "/" + moisString + '/' + year;
            jsonEvent.addProperty("date", date);
            jsonEvent.addProperty("moment", d.getMoment());
            jsonListe.add(jsonEvent);
        }
        JsonObject container = new JsonObject();
        container.add("Evenement", jsonListe);
        out.println(gson.toJson(container));
    }

    private static void printEventVide(PrintWriter out, List<Evnmt> evenements) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonArray jsonListe = new JsonArray();
        JsonObject jsonEvent = new JsonObject();
        jsonEvent.addProperty("data", "Aucun evenement à affecter");
        jsonListe.add(jsonEvent);
        JsonObject container = new JsonObject();
        container.add("Evenement", jsonListe);
        out.println(gson.toJson(container));
    }

}
