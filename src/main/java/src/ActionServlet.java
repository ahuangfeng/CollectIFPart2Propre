package src;

import vueJson.JsonCoordonneesMap;
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
import metier.modele.Activite;
import metier.modele.Adherent;
import metier.modele.Demande;
import metier.modele.Evnmt;
import metier.modele.Lieu;
import vueJson.JsonDemandesAdmin;
import vueJson.JsonDetailActivite;
import vueJson.JsonEvents;
import vueJson.JsonLieu;
import vueJson.PrintConfirmation;

/**
 *
 */
public class ActionServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Session
        PrintWriter out = response.getWriter();
//        HttpSession session = request.getSession();
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String todo = request.getParameter("action");
        if (null != todo) switch (todo) {
            case "login":
                ActionLogin.run(request, response);
                break;
            case "inscription":
                boolean b = ActionInscription.run(request);
                out.println(b);
                break;
            case "listeActivites":{
                List<Activite> liste = ActionListeActivites.run(request);
                JsonListeActivite.print(liste, response);
                    break;
                }
            case "detailActivite":{
                List<Activite> liste = ActionDetailActivite.run(request);
                JsonDetailActivite.print(liste, request, response);
                    break;
                }
            case "posterDemande":
                String resp = ActionPosterDemande.run(request, response);
                PrintConfirmation.print(resp, response);
                break;
            case "myDemands":
                List<Demande> dem = ActionMyDemands.run(request, response);
                if (dem != null) {
                    JsonMyDemands.print(dem, response);
                }   break;
            case "listeAdherents":
                List<Adherent> adherents = ActionGetAdherents.run(request);
                JsonAdherents.print(adherents,response);
                break;
            case "admin":
                List<Evnmt> evnmts = ActionAdminMain.run(request, response);
                JsonDemandesAdmin.print(evnmts, response);
                break;
            case "demandsAffecter":
                List<Evnmt> events = ActionGetDemandsAffecter.run(request, response);
                JsonEvents.print(events,response);
                break;
            case "getLieu":
                List<Lieu> l = ActionGetLieu.run(request);
                JsonLieu.print(l,response);
                break;
            case "afectation":
                Evnmt ev = ActionGetEventAValider.run(request,response);
                List<Adherent> a = ActionParticipantByEvent.run(request,response,ev);
                List<Lieu> llocs = ActionGetLieu.run(request);
                JsonCoordonneesMap.print(a,llocs,ev,response);
                break;
            case "validationEvent":
                String res = ActionValiderEvnmtLieu.run(request, response);
                PrintConfirmation.print(res,response);
                break;
            default:
                break;
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
