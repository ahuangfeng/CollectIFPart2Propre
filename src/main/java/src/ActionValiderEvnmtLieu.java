/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author alexh
 */
class ActionValiderEvnmtLieu {

    static String run(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean conf = ActionAffecterPaf.run(request);
        int conf2 = ActionAffecterLieu.run(request);
        
        String res = "";
        if(conf){
            res += "Lieu et paf affecté \nEvent modifié : "+conf2;
        }
        
        return res;
    }

}
