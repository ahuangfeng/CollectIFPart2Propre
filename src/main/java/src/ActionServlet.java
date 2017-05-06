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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import metier.modele.Adherent;
import metier.service.ServiceMetier;

/**
 *
 */
public class ActionServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Session
        HttpSession session = request.getSession();
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String todo = request.getParameter("action");
        //TODO printeWriter dans chaque classe
        PrintWriter out = response.getWriter();
        System.out.println(todo);
        System.out.println("inscription".equals(todo));
        if ("login".equals(todo)) {
            ActionLogin.run(request, response);
        } else if ("logged".equals(todo)) {
            //Effacer
            String sessionUser = (String) session.getAttribute("user");
            response.setContentType("application/json");
            printUser(out, sessionUser);
            //TODO :si no logged il doit envoyer vers accueil.html
//                response.sendRedirect("./accueil.html");
        } else if ("inscription".equals(todo)) {
            ActionInscription.run(request, response);
        } else if ("listeActivites".equals(todo)) {
            ActionListeActivites.run(request, response);
        } else if ("detailActivite".equals(todo)) {
            ActionDetailActivite.run(request, response);
        } else if ("posterDemande".equals(todo)) {
                ActionPosterDemande.run(request, response);
        } else if ("myDemands".equals(todo)) {
            ActionMyDemands.run(request, response);
        } else if ("listeAdherents".equals(todo)) {
            ActionGetAdherents.run(request, response);
        } else if ("admin".equals(todo)) {
            ActionAdminMain.run(request, response);
        } else if("demandsAffecter".equals(todo)){
            ActionGetDemandsAffecter.run(request,response);
        } else if("getLieu".equals(todo)){
            ActionGetLieu.run(request,response);
        } else if("afectation".equals(todo)){
            ActionGetCoordonnes.run(request,response);
        }
        out.close();
    }

    /**
     * Envoi JSON de l'Adherent
     *
     * @param out
     * @param sessionUser
     */
    private void printUser(PrintWriter out, String sessionUser) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Long idAdh = Long.valueOf(sessionUser);
            Adherent adhSession = null;
            JpaUtil.init();
            List<Adherent> adherents = ServiceMetier.consulterListeAdherent();
            for (Adherent adherent : adherents) {
                if (adherent.getId().equals(idAdh)) {
                    adhSession = adherent;
                }
            }
            JpaUtil.destroy();
            JsonObject jsonAdherent = new JsonObject();
            if (adhSession != null) {
                jsonAdherent.addProperty("id", adhSession.getId());
                jsonAdherent.addProperty("email", adhSession.getMail());
            }
            out.println(gson.toJson(jsonAdherent));
        } catch (Exception ex) {
            Logger.getLogger(ActionServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    public String getServletInfo() {
        return "Servlet pour gerer les diferents actions.";
    }

}
