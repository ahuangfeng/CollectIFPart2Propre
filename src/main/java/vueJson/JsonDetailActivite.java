/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vueJson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.modele.Activite;

/**
 *
 * @author alexh
 */
public class JsonDetailActivite {

    public static void print(List<Activite> liste, HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        String actID = request.getParameter("id");
        Long id = Long.parseLong(actID);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        JsonObject jsonActivite = new JsonObject();
        for (Activite activite : liste) {
            if (activite.getId().equals(id)) {
                jsonActivite.addProperty("id", activite.getId());
                jsonActivite.addProperty("denomination", activite.getDenomination());
                jsonActivite.addProperty("payant", activite.getPayant());
                jsonActivite.addProperty("nbparticipant", activite.getNbParticipants());
            }
        }
        out.println(gson.toJson(jsonActivite));
        
    }
    
}
