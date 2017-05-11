/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import dao.JpaUtil;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.service.ServiceMetier;

/**
 *
 * @author alexh
 */
class ActionValiderEvnmtLieu {

    static String run(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JpaUtil.init();
        String res = "";
//        PrintWriter out = response.getWriter();
        String SEvent = request.getParameter("evnmt");
        String SLieu = request.getParameter("lieu");
        String SPaf = request.getParameter("paf");
        int idEvent = Integer.parseInt(SEvent);
        int paf;
        if (SPaf.isEmpty()) {
            paf = Integer.parseInt(SPaf);
        } else {
            paf = 0;
        }
        int idLieu = Integer.parseInt(SLieu);
        try {
            ServiceMetier.affecterPaf(idEvent, paf);
            ServiceMetier.affecterLieu(idEvent, idLieu);
        } catch (Exception ex) {
            Logger.getLogger(ActionValiderEvnmtLieu.class.getName()).log(Level.SEVERE, null, ex);
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        //Appel methode pour printer les activites
//        out.println("Lieu et paf affecté : ");
//        out.println("Event modifie : " + idEvent);
        JpaUtil.destroy();
        res+="Lieu et paf affecté \nEvent modifié : "+idEvent;
        return res;
    }

}
