/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package studentoons;

/**
 *
 * @author abdulrahim
 */

public class Studentoons {
static int playlistCount=1;

    static Gui gui;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
            try{  
        gui=new Gui();}
            catch(Exception e)
            {
                System.err.println(e.getMessage());
            }
    }
    
}
