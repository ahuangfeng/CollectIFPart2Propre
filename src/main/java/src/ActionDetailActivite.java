/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import metier.service.ServiceMetier;

/**
 * Nous renvoie un JSON avec les detail d'une activité saisie par le ID
 *
 * @author alexh
 */
public class ActionDetailActivite {

    static List<Activite> run(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JpaUtil.init();
        PrintWriter out = response.getWriter();
        List<Activite> activite = null;
        try {
            activite = ServiceMetier.consulterListeActivite();
        } catch (Exception ex) {
            Logger.getLogger(ActionServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        //Appel methode pour printer les activites
//        printDetailActivite(out, activite);
        JpaUtil.destroy();
        return activite;
    }
//
//    public static void printDetailActivite(PrintWriter out, List<Activite> activites) {
//        
//        String actID = request.getParameter("id");
//        Long id = Long.parseLong(actID);
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//
//        JsonObject jsonActivite = new JsonObject();
//        for (Activite activite : activites) {
//            if (activite.getId().equals(id)) {
//                jsonActivite.addProperty("id", activite.getId());
//                jsonActivite.addProperty("denomination", activite.getDenomination());
//                jsonActivite.addProperty("payant", activite.getPayant());
//                jsonActivite.addProperty("nbparticipant", activite.getNbParticipants());
//            }
//        }
//        out.println(gson.toJson(jsonActivite));
//
//    }

}
