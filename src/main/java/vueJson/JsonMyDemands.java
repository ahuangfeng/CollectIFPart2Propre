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

/**
 *
 * @author alexh
 */
public class JsonMyDemands {

    public static void print(List<Demande> dem, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        JsonArray jsonListe = new JsonArray();
        String date = null;
        int year = 0;
        int month = 0;
        int jourInt = 0;
        String day = "";
        String moisString = "";
        Calendar calendar = new GregorianCalendar();
        for (Demande demande : dem) {
            JsonObject jsonDemande = new JsonObject();
            jsonDemande.addProperty("id", demande.getId());
            jsonDemande.addProperty("name", demande.getMonActMTO().getDenomination());
            calendar.setTime(demande.getDate());
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
            jsonDemande.addProperty("date", date);
            jsonDemande.addProperty("moment", demande.getMoment());
            jsonDemande.addProperty("payant", demande.getMonActMTO().getPayant());
            jsonDemande.addProperty("statut", demande.getTraiter());
            jsonListe.add(jsonDemande);
        }
        JsonObject container = new JsonObject();
        container.add("demandes", jsonListe);
        out.println(gson.toJson(container));
    }
    
}
