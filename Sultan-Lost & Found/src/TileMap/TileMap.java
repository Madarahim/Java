/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TileMap;

import sultan.lost.found.GamePanel;

import java.io.*;

import java.awt.*;
import java.awt.image.*;

import javax.imageio.ImageIO;

/**
 *
 * @author abdulrahim
 */

public class TileMap {
    
    //position
    private double x;
    private double y;
    
    //bounds
    private int xmin;
    private int ymin;
    private int xmax;
    private int ymax;
    
    private double tween;
    
    //map
    private int[][] map;
    private int tileSize;
    private int numRows;
    private int numCols;
    private int width;
    private int height;
    
    //tileset
    private BufferedImage tileset;
    private int numTilesAcross;
    private Tile[][] tiles;
    
    //drawing
    private int rowOffset;
    private int colOffset;
    private int numRowsToDraw;
    private int numColsToDraw;
    
    
    //constructor
    public TileMap(int tileSize)
    {
        this.tileSize=tileSize;
        numRowsToDraw = GamePanel.HEIGHT / tileSize + 2;
        numColsToDraw = GamePanel.WIDTH / tileSize + 2;
        tween = 0.7;
    }
    
    
    //load the tiles
    public void loadTiles(String s){
        
        try{
            
            tileset = ImageIO.read(getClass().getResourceAsStream(s));
            numTilesAcross = tileset.getWidth() / tileSize;
            
            //His tile image has 2 rows 
            //and numTilesAcross(about 20) columns of tiles
            tiles = new Tile[2][numTilesAcross];
            
            BufferedImage subimage;
            for(int col = 0; col < numTilesAcross; col++)
            {
                subimage = tileset.getSubimage(
                        col*tileSize,
                        0,
                        tileSize,
                        tileSize
                );
                
                //first row; all normal tiles
                tiles[0][col] = new Tile(subimage, Tile.NORMAL);
                
                subimage = tileset.getSubimage(
                        col*tileSize,
                        tileSize,
                        tileSize,
                        tileSize
                );
                
                //second row; all blocked tiles
                tiles[1][col] = new Tile(subimage, Tile.BLOCKED);
            }
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    
    //load map
    public void loadMap(String s) {
        
        try {
        
            InputStream in = getClass().getResourceAsStream(s);
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(in)
            );
            
            numCols = Integer.parseInt(br.readLine());
            numRows = Integer.parseInt(br.readLine());
            map = new int[numRows][numCols];
            width = numCols * tileSize;
            height = numRows * tileSize;
            
            xmin = GamePanel.WIDTH - width;
            xmax = 0;
            ymin = GamePanel.HEIGHT - height;
            ymax = 0;
            
            //delimeters??? (AKA Symbol for whitespace when using .split() )
            String delims = "\\s+";
            for(int row = 0; row < numRows; row++)
            {
                String line = br.readLine();
                String[] tokens = line.split(delims);
                for(int col = 0; col < numCols; col++)
                {
                    map[row][col] = Integer.parseInt(tokens[col]);
                }
            }
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    
    public int getNumRows() { return numRows; }
    public int getNumCols() { return numCols; }
    
    
    public int getTileSize(){ return tileSize; }
    public double getX() { return x; }
    public double getY() { return y;}
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    
    
    public int getType (int row, int col)
    {
        int rc = map[row][col];
        int r = rc / numTilesAcross;
        int c = rc % numTilesAcross;
        
        return tiles[r][c].getType();
    }
    
    
    public void setPosition(double x, double y)
    {
        this.x +=  (x - this.x) * tween;
        this.y += (y - this.y) * tween;
        
        fixBounds();
        
        colOffset = (int)-this.x / tileSize;
        rowOffset = (int)-this.y / tileSize;
    }
    
    
    public void setTween(double i){
        tween=i;
    }
    
    public void fixBounds(){
    
        if(x < xmin) x = xmin;
        if(y < ymin) y = ymin;
        if(x > xmax) x = xmax;
        if(y > ymax) y = ymax;
    }
    
    public void draw(Graphics2D g)
    {
        for(int row = rowOffset; row < rowOffset + numRowsToDraw; row++)
        {
            
            if(row >= numRows)  break;
            
            for(int col = colOffset; col < colOffset + numColsToDraw; col++)
            {
                if(col >= numCols)  break;
                
                if(map[row][col] == 0)  continue;
                
                int rc = map[row][col];
                int r = rc / numTilesAcross;
                int c =  rc % numTilesAcross;
                
                g.drawImage(
                        tiles[r][c].getImage(),
                        (int)x + col * tileSize,
                        (int)y + row * tileSize,
                        null
                    );
            }
        }
    }
    
}