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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.modele.Lieu;
import metier.service.ServiceMetier;

/**
 *
 * @author alexh
 */
class ActionGetLieu {

    static void run(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JpaUtil.init();
        PrintWriter out = response.getWriter();
        List<Lieu> lieus = null;
        try {
            lieus = ServiceMetier.consulterListeLieu();
        } catch (Exception ex) {
            Logger.getLogger(ActionServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        response.setContentType("application/json");
        //Appel methode pour printer les activites
        if (lieus != null) {
            printLieus(out, lieus);
        } else {
            printError(out);
        }
        JpaUtil.destroy();
    }

    private static void printLieus(PrintWriter out, List<Lieu> lieus) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        JsonArray jsonListe = new JsonArray();
        for (Lieu lieu : lieus) {
            JsonObject jsonLieu = new JsonObject();
            jsonLieu.addProperty("id", lieu.getId());
            jsonLieu.addProperty("lieu", lieu.getDenomination());
            jsonLieu.addProperty("longitude", lieu.getLongitude());
            jsonLieu.addProperty("latitude", lieu.getLatitude());
            jsonListe.add(jsonLieu);
        }
        JsonObject container = new JsonObject();
        container.add("lieu", jsonListe);
        out.println(gson.toJson(container));
    }

    //revoir cette methode
    private static void printError(PrintWriter out) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        JsonArray jsonListe = new JsonArray();
        JsonObject jsonLieu = new JsonObject();
        jsonLieu.addProperty("id", "Pas de lieux dispo");
        jsonLieu.addProperty("lieu", "Pas de lieux dispo");
        jsonListe.add(jsonLieu);
        JsonObject container = new JsonObject();
        container.add("lieu", jsonListe);
        out.println(gson.toJson(container));
    }

}
