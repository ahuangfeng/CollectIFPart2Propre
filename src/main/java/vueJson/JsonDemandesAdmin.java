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
public class JsonDemandesAdmin {

    public static void print(List<Evnmt> evnmts, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        JsonArray jsonListe = new JsonArray();
        List<Demande> demandes = null;
        Demande d = null;
        String date = null;
        int year = 0;
        int month = 0;
        int jourInt = 0;
        String day = "";
        String moisString = "";
        Calendar calendar = new GregorianCalendar();
        for (Evnmt evenement : evnmts) {
            demandes = evenement.getMaListeDemandesEVT();
            calendar.setTime(evenement.getDate());
            //TODO : on supose que tous les demandes sont pareil...
            d = demandes.get(0);
            JsonObject jsonEvn = new JsonObject();
            jsonEvn.addProperty("id", evenement.getId());
            jsonEvn.addProperty("denomination", d.getMonActMTO().getDenomination());
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
            jsonEvn.addProperty("date", date);
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
