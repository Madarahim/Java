/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TileMap;

import java.awt.image.BufferedImage;
/**
 *
 * @author abdulrahim
 */
public class Tile {
    
    private BufferedImage image;
    private int type;
    
    //tile types
    public static int NORMAL = 0;
    public static int BLOCKED = 1;
    
    
    //constructor
    public Tile(BufferedImage image, int type)
    {
        this.image = image;
        this.type = type;
    }
    
    public BufferedImage getImage(){ return image; }
    public int getType(){ return type; }
}
