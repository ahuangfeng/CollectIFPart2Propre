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
import metier.modele.Activite;

/**
 *
 * @author alexh
 */
public class JsonListeActivite {

    public static void print(List<Activite> liste, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        JsonArray jsonListe = new JsonArray();
        for (Activite a : liste) {
            JsonObject jsonActivite = new JsonObject();
            jsonActivite.addProperty("id", a.getId());
            jsonActivite.addProperty("denomination", a.getDenomination());
            jsonListe.add(jsonActivite);
        }
        JsonObject container = new JsonObject();
        container.add("activites", jsonListe);
        out.println(gson.toJson(container));
    }
    
}
