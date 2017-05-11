/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import dao.JpaUtil;
import java.io.IOException;
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

    static List<Lieu> run(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JpaUtil.init();
        List<Lieu> lieus = null;
        try {
            lieus = ServiceMetier.consulterListeLieu();
        } catch (Exception ex) {
            Logger.getLogger(ActionServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        response.setContentType("application/json");
//        if (lieus != null) {
//            printLieus(out, lieus);
//        } else {
//            printError(out);
//        }
        JpaUtil.destroy();
        return lieus;
    }
//
//    private static void printLieus(HttpServletResponse response, List<Lieu> lieus) throws IOException {
//        PrintWriter out = response.getWriter();
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//
//        JsonArray jsonListe = new JsonArray();
//        for (Lieu lieu : lieus) {
//            JsonObject jsonLieu = new JsonObject();
//            jsonLieu.addProperty("id", lieu.getId());
//            jsonLieu.addProperty("lieu", lieu.getDenomination());
//            jsonLieu.addProperty("longitude", lieu.getLongitude());
//            jsonLieu.addProperty("latitude", lieu.getLatitude());
//            jsonListe.add(jsonLieu);
//        }
//        JsonObject container = new JsonObject();
//        container.add("lieu", jsonListe);
//        out.println(gson.toJson(container));
//    }
//
//    //revoir cette methode
//    private static void printError(HttpServletResponse response) throws IOException {
//        PrintWriter out = response.getWriter();
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//
//        JsonArray jsonListe = new JsonArray();
//        JsonObject jsonLieu = new JsonObject();
//        jsonLieu.addProperty("id", "Pas de lieux dispo");
//        jsonLieu.addProperty("lieu", "Pas de lieux dispo");
//        jsonListe.add(jsonLieu);
//        JsonObject container = new JsonObject();
//        container.add("lieu", jsonListe);
//        out.println(gson.toJson(container));
//    }

}
