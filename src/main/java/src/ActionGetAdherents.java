/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import dao.JpaUtil;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.modele.Adherent;
import metier.service.ServiceMetier;

/**
 *
 * @author alexh
 */
class ActionGetAdherents {

    static List<Adherent> run(HttpServletRequest request, HttpServletResponse response) {
        JpaUtil.init();
        List<Adherent> adherents = null;
        try {
            adherents = ServiceMetier.consulterListeAdherent();
        } catch (Exception ex) {
            Logger.getLogger(ActionServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        response.setContentType("application/json");
        //Appel methode pour printer les activites
//        printListeAdherents(response, adherents);
        JpaUtil.destroy();
        return adherents;
    }

//    private static void printListeAdherents(HttpServletResponse response, List<Adherent> adherents) throws IOException {
//        //TODO remetre la conversion à JSON dans ActionServlet
//        PrintWriter out = response.getWriter();
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//
//        JsonArray jsonListe = new JsonArray();
//        for (Adherent adherent : adherents) {
//            JsonObject jsonAdherent = new JsonObject();
//            jsonAdherent.addProperty("id", adherent.getId());
//            jsonAdherent.addProperty("email",adherent.getMail());
//            jsonListe.add(jsonAdherent);
//        }
//        JsonObject container = new JsonObject();
//        container.add("adherent", jsonListe);
//        out.println(gson.toJson(container));
//    }
    
}
