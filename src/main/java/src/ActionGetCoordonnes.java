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
import javax.servlet.http.HttpSession;
import metier.modele.Adherent;
import metier.modele.EvPayant;
import metier.modele.Evnmt;
import metier.modele.Lieu;
import metier.service.ServiceMetier;

/**
 *
 * @author alexh
 */
class ActionGetCoordonnes {

    static void run(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        JpaUtil.init();
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        List<Adherent> adherents = null;
        List<Lieu> lieux = null;
        if (session.isNew()) {
            response.sendRedirect("./error.html");
        } else {
            String idEvent = request.getParameter("idEvent");
            long idL = Long.parseLong(idEvent);
            Evnmt evenAValider = null;
            try {
                List<Evnmt> e = ServiceMetier.consulterListeEvt();
                for (Evnmt evnmt : e) {
                    if (idL == evnmt.getId()) {
                        evenAValider = evnmt;
                    }
                }
                adherents = ServiceMetier.consulterListeParticipant(evenAValider);
                lieux = ServiceMetier.consulterListeLieu();
                printEvent(out, adherents,lieux, evenAValider);
            } catch (Exception ex) {
                Logger.getLogger(ActionGetCoordonnes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        JpaUtil.destroy();
    }

    private static void printEvent(PrintWriter out, List<Adherent> adherents,List<Lieu> lieux, Evnmt evnmt) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        JsonArray jsonListe = new JsonArray();
        for (Adherent adherent : adherents) {
            JsonObject jsonAdh = new JsonObject();
            jsonAdh.addProperty("id", adherent.getId());
            if (evnmt instanceof EvPayant) {
                jsonAdh.addProperty("paf", ((EvPayant) evnmt).getMontantPaf());
            }
            jsonAdh.addProperty("latitude", adherent.getLatitude());
            jsonAdh.addProperty("longitude", adherent.getLongitude());
            jsonListe.add(jsonAdh);
        }
        JsonArray jsonListe2 = new JsonArray();
        for (Lieu lieu : lieux) {
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
