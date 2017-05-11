/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vueJson;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import metier.modele.Adherent;

/**
 *
 * @author alexh
 */
public class PrintConfirmation {

    public static void print(String message, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        out.println(message);
    }

    public static void print(List<Adherent> ad, HttpServletResponse response) {
        
    }
    
}
