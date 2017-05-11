/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vueJson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import metier.modele.Demande;
import metier.modele.Evnmt;

/**
 *
 * @author alexh
 */
public class JsonEvents {

    public static void print(List<Evnmt> events, HttpServletResponse response) throws IOException {
//        HttpSession session = request.getSession();
        if(events!=null){
            printEvenementsAffecter(response, events);
        }else{
            printEventVide(response, events);
        }
    }
    
//    private static void print(String message, HttpServletResponse response) throws IOException{
//        PrintWriter out = response.getWriter();
//        out.println(message);
//    }
    
    private static void printEvenementsAffecter(HttpServletResponse response, List<Evnmt> evenements) throws IOException {
        PrintWriter out = response.getWriter();
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
            year = calendar.get(Calendar.YEAR);
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

    private static void printEventVide(HttpServletResponse response, List<Evnmt> evenements) throws IOException {
        PrintWriter out = response.getWriter();
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
