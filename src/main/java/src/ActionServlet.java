package src;

import vueJson.JsonAdherents;
import vueJson.JsonMyDemands;
import vueJson.JsonListeActivite;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import metier.modele.Activite;
import metier.modele.Adherent;
import metier.modele.Demande;
import metier.modele.Evnmt;
import vueJson.JsonDemandesAdmin;
import vueJson.JsonDetailActivite;
import vueJson.PrintConfirmation;

/**
 *
 */
public class ActionServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Session
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String todo = request.getParameter("action");
        if ("login".equals(todo)) {
            ActionLogin.run(request, response);
        } else if ("inscription".equals(todo)) {
            boolean b = ActionInscription.run(request, response);
            out.println(b);
        } else if ("listeActivites".equals(todo)) {
            List<Activite> liste = ActionListeActivites.run(request, response);
            JsonListeActivite.print(liste, response);
        } else if ("detailActivite".equals(todo)) {
            List<Activite> liste = ActionDetailActivite.run(request, response);
            JsonDetailActivite.print(liste, request, response);
        } else if ("posterDemande".equals(todo)) {
            String resp = ActionPosterDemande.run(request, response);
            PrintConfirmation.print(resp, response);
        } else if ("myDemands".equals(todo)) {
            List<Demande> dem = ActionMyDemands.run(request, response);
            if (dem != null) {
                JsonMyDemands.print(dem, response);
            }
        } else if ("listeAdherents".equals(todo)) {
            List<Adherent> adherents = ActionGetAdherents.run(request, response);
            JsonAdherents.print(adherents,response);
        } else if ("admin".equals(todo)) {
            List<Evnmt> evnmts = ActionAdminMain.run(request, response);
            JsonDemandesAdmin.print(evnmts, response);
        } else if ("demandsAffecter".equals(todo)) {
            ActionGetDemandsAffecter.run(request, response);
        } else if ("getLieu".equals(todo)) {
            ActionGetLieu.run(request, response);
        } else if ("afectation".equals(todo)) {
            ActionGetCoordonnes.run(request, response);
        } else if ("validationEvent".equals(todo)) {
            ActionValiderEvnmtLieu.run(request, response);
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
