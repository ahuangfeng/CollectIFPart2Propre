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
import metier.modele.Activite;
import metier.service.ServiceMetier;

/**
 *
 * @author alexh
 */
public class ActionListeActivites {

    static void run(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        List<Activite> activite = null;
        try {
            activite = ServiceMetier.consulterListeActivite();
        } catch (Exception ex) {
            Logger.getLogger(ActionServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        response.setContentType("application/json");
        //Appel methode pour printer les activites
        printListeActivite(out, activite);
    }
    
    public static void printListeActivite(PrintWriter out, List<Activite> Activite) {
        //TODO remetre la conversion à JSON dans ActionServlet
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        JsonArray jsonListe = new JsonArray();
        for (Activite a : Activite) {
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
