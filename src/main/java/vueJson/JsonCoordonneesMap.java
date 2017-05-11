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
import javax.servlet.http.HttpServletResponse;
import metier.modele.Adherent;
import metier.modele.EvPayant;
import metier.modele.Evnmt;
import metier.modele.Lieu;

/**
 *
 * @author alexh
 */
public class JsonCoordonneesMap {

    public static void print(List<Adherent> a, List<Lieu> llocs, Evnmt ev, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        JsonArray jsonListe = new JsonArray();
        for (Adherent adherent : a) {
            JsonObject jsonAdh = new JsonObject();
            jsonAdh.addProperty("id", adherent.getId());
            if (ev instanceof EvPayant) {
                jsonAdh.addProperty("paf", ((EvPayant) ev).getMontantPaf());
            }
            jsonAdh.addProperty("latitude", adherent.getLatitude());
            jsonAdh.addProperty("longitude", adherent.getLongitude());
            jsonListe.add(jsonAdh);
        }
        JsonArray jsonListe2 = new JsonArray();
        for (Lieu lieu : llocs) {
            JsonObject jsonLieu = new JsonObject();
            jsonLieu.addProperty("id", lieu.getId());
            jsonLieu.addProperty("lieu", lieu.getDenomination());
            jsonLieu.addProperty("longitude", lieu.getLongitude());
            jsonLieu.addProperty("latitude", lieu.getLatitude());
            jsonListe2.add(jsonLieu);
        }
        JsonObject container = new JsonObject();
        container.add("lieu", jsonListe2);
        container.add("adherent", jsonListe);
        out.println(gson.toJson(container));

    }

}
