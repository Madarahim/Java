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
public class Last10SongsPlayed {
    
    private String title, path;
    
    public Last10SongsPlayed(String t, String p)
    {
        title=t;
        path=p;
    }
    
    public String getTitle()
    {
        return title;
    }
    
    public String getPath()
    {
        return path;
    }
    
}
