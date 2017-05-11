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
        boolean conf = ActionAffecterPaf.run(request, response);
        int conf2 = ActionAffecterLieu.run(request, response);
        
        String res = "";
        if(conf){
            res += "Lieu et paf affecté \nEvent modifié : "+conf2;
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        return res;
    }

}
