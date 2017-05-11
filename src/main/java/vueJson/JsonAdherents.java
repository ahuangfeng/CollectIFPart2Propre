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

/**
 *
 * @author alexh
 */
public class JsonAdherents {

    public static void print(List<Adherent> adherents, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        JsonArray jsonListe = new JsonArray();
        for (Adherent adherent : adherents) {
            JsonObject jsonAdherent = new JsonObject();
            jsonAdherent.addProperty("id", adherent.getId());
            jsonAdherent.addProperty("email",adherent.getMail());
            jsonListe.add(jsonAdherent);
        }
        JsonObject container = new JsonObject();
        container.add("adherent", jsonListe);
        out.println(gson.toJson(container));
    }
    
}
