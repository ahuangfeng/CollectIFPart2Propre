/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import dao.JpaUtil;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import metier.service.ServiceMetier;

/**
 *
 * @author alexh
 */
public class ActionAffecterPaf {

    static boolean run(HttpServletRequest request) {
        JpaUtil.init();
        String SEvent = request.getParameter("evnmt");
        boolean res = false;
        String SPaf = request.getParameter("paf");
        int idEvent = Integer.parseInt(SEvent);
        int paf;
        if (SPaf.isEmpty()) {
            paf = Integer.parseInt(SPaf);
        } else {
            paf = 0;
        }
        try {
            ServiceMetier.affecterPaf(idEvent, paf);
            res = true;
        } catch (Exception ex) {
            Logger.getLogger(ActionAffecterPaf.class.getName()).log(Level.SEVERE, null, ex);
        }
        JpaUtil.destroy();
        return res;
    }
    
}
