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
import metier.modele.Lieu;
import metier.service.ServiceMetier;

/**
 *
 * @author alexh
 */
class ActionGetLieu {

    static List<Lieu> run(HttpServletRequest request) throws IOException {
        JpaUtil.init();
        List<Lieu> lieus = null;
        try {
            lieus = ServiceMetier.consulterListeLieu();
        } catch (Exception ex) {
            Logger.getLogger(ActionServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        JpaUtil.destroy();
        return lieus;
    }

}
