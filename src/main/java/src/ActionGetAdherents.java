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
import metier.modele.Activite;
import metier.modele.Adherent;
import metier.service.ServiceMetier;
import static src.ActionListeActivites.printListeActivite;

/**
 *
 * @author alexh
 */
class ActionGetAdherents {

    static void run(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JpaUtil.init();
        PrintWriter out = response.getWriter();
        List<Adherent> adherents = null;
        try {
            adherents = ServiceMetier.consulterListeAdherent();
        } catch (Exception ex) {
            Logger.getLogger(ActionServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        response.setContentType("application/json");
        //Appel methode pour printer les activites
        printListeAdherents(out, adherents);
        JpaUtil.destroy();
    }

    private static void printListeAdherents(PrintWriter out, List<Adherent> adherents) {
        //TODO remetre la conversion à JSON dans ActionServlet
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
