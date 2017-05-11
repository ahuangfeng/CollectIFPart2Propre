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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import metier.modele.Lieu;

/**
 *
 * @author alexh
 */
public class JsonLieu {

    public static void print(List<Lieu> l, HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            if (l != null) {
                printLieus(response, l);
            } else {
                printError(response);
            }
        } catch (IOException ex) {
            Logger.getLogger(JsonLieu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void printLieus(HttpServletResponse response, List<Lieu> lieus) throws IOException {
        PrintWriter out = response.getWriter();
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
    private static void printError(HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
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
