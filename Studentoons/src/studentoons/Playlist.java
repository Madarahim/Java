/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package studentoons;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import static studentoons.Gui.table;
import static studentoons.Gui.windowTable;
import static studentoons.Gui.selectedRow;
import static studentoons.Gui.player;
import static studentoons.Gui.playing;
import static studentoons.Gui.model;
import static studentoons.Gui.playlistGroup;
import java.sql.*;
/**
 *
 * @author abdulrahim
 */
public class Playlist {
 String playlistTitle, title,artist, album, year, path, genre, comment,time;   
 //ArrayList<Mp3File> songsInPlaylist;
 Connection con=null;
 
 public Playlist()
 {
     playlistTitle="Playlist"+ Studentoons.playlistCount;
     Studentoons.playlistCount++;
     //songsInPlaylist=new ArrayList<Mp3File>();

 }
 
 public Playlist(String pName)
 {
     playlistTitle=pName;
     try {
            con = DriverManager.getConnection("jdbc:derby://localhost/Music","abdulrahim","abdulrahim24a");
            Statement sta = con.createStatement(); 

            int count = 0;
            int c=sta.executeUpdate("CREATE TABLE "+playlistTitle+"(cTitle VARCHAR(30), cArtist VARCHAR(30), cAlbum VARCHAR(30),"
            + " cYear VARCHAR(4), cPath VARCHAR(200), cGenre VARCHAR(25), cComment VARCHAR(40), cTime VARCHAR(15), PRIMARY KEY(cPath))");
            
            sta.close();
            con.close();        
            } 
     catch (Exception db) {
            System.err.println("Exception: "+db.getMessage());
            }
          //songsInPlaylist=new ArrayList<Mp3File>();

 }
 
 public String getPlayListTitle()
 {
     return playlistTitle;
 }

 
 public void add(String p, DefaultTableModel model)
 {
     int stime, min, sec, hr;
     String minutes,seconds, hours;
     title=Gui.title;
     try{
         
             path=p;
            FileInputStream fis= new FileInputStream(p);
           // DefaultTableModel model = (DefaultTableModel) table.getModel();
        //path=chooser.getSelectedFile().getAbsolutePath();
            Mp3File songInPlaylist=new Mp3File(path);
                                                      
        if(songInPlaylist.hasId3v1Tag()==true)
        {
            title=songInPlaylist.getId3v1Tag().getTitle();
            artist=songInPlaylist.getId3v1Tag().getArtist();
            album=songInPlaylist.getId3v1Tag().getAlbum();
            year=songInPlaylist.getId3v1Tag().getYear();
            genre=songInPlaylist.getId3v1Tag().getGenreDescription();
            comment=songInPlaylist.getId3v1Tag().getComment();
            stime=(int)songInPlaylist.getLengthInSeconds();
                min=stime/60;
                sec=stime%60;
                hr=stime/3600;
                
                if(sec<10)
                    seconds="0"+sec;
                    else
                       seconds=sec+"";
                
                if(min<10)
                    minutes="0"+min;
                    else
                        minutes=min+"";
            
                if(hr<10)
                    hours="0"+hr;
                    else
                        hours=hr+"";
                
            time=hours+":"+minutes+":"+seconds;
                                       

            
        }
                                                       
        else
        {
            artist="An Artist";
            album="An Album";
            year="XXXX";
            genre="A Genre";
            comment="A Comment";
                stime=(int)songInPlaylist.getLengthInSeconds();
                min=stime/60;
                sec=stime%60;
                hr=stime/3600;

                if(sec<10)
                    seconds="0"+sec;
                    else
                       seconds=sec+"";
                
                if(min<10)
                    minutes="0"+min;
                    else
                        minutes=min+"";
            
                if(hr<10)
                    hours="0"+hr;
                    else
                        hours=hr+"";
                
            time=hours+":"+minutes+":"+seconds;
                                                           
        }
    
        //Database Connection
        try {

                con = DriverManager.getConnection("jdbc:derby://localhost/Music","abdulrahim","abdulrahim24a");
                Statement sta = con.createStatement(); 
                ////////////////////////////////////////////////////
                //Check if it is in Library
                if(playlistTitle!="Library"){
                    
                      try{
                            int rowCount=model.getRowCount();
                            for (int i = rowCount - 1; i > 0; i--) {
                            model.removeRow(i);
                           }
                            con = DriverManager.getConnection("jdbc:derby://localhost/Music","abdulrahim","abdulrahim24a");
                            Statement st=con.createStatement();
                            boolean isInLibrary=false;
                            String sql="SELECT * FROM Library";
                            ResultSet rs = st.executeQuery(sql);
                            while(rs.next())
                           {

                            String e = rs.getString("cPath");
                            System.out.println(e+"=="+path);
                            if(e.equals(path))
                            {
                                isInLibrary=true;
                                break;
                            }

                           }

                            if(isInLibrary==false)
                            {
                                System.out.println("Inserted");
                                int c = sta.executeUpdate("INSERT INTO Library (cTitle, cArtist, cAlbum, cYear, cPath, cGenre, cComment, cTime)"
                                + " VALUES ( '"+title+"', '"+artist+"', '" +album+"', '" +year+"', '"+path +"', '"+genre+"', '"+comment+"','"+time+"')");

                                
                            }
                        }
                        catch(SQLException e)
                        {
                            System.err.println(e.getMessage());
                        }
        }
                ////////////////////////////////////////////////////
                int count = 0;
                                                          
                int c = sta.executeUpdate("INSERT INTO "+playlistTitle+ " (cTitle, cArtist, cAlbum, cYear, cPath, cGenre, cComment, cTime)"
                    + " VALUES ( '"+title+"', '"+artist+"', '" +album+"', '" +year+"', '"+path +"', '"+genre+"', '"+comment+"','"+time+"')");

                int d=sta.executeUpdate("INSERT INTO LIBRARYPLAYLIST (PLAYLISTNAME, CPATH) VALUES('"+getPlayListTitle()+ "', '"+p+"')");
                //model.addRow(new Object[]{title, artist, album, year, path, genre, comment});
                displayPlaylist(model);
                selectedRow=table.getRowCount()-1;
                table.setRowSelectionInterval(selectedRow, selectedRow);
                                                       
                sta.close();
                con.close();        
                } 
        catch (Exception db) {
             System.err.println("Exception: "+db.getMessage());
             displayPlaylist(model);

        }
                                                        
                                                        
    }
     
    catch(IOException io )
    {
        System.out.println("IO Exception");
    }
    
    catch(UnsupportedTagException ut)
    {                                                  
      System.out.println("UnsupportedTag Exception");
    }                                               
    
    catch(InvalidDataException id)
    {
      System.out.println("InvalidData Exception");
    }
 }
 
 public void delete(DefaultTableModel model,int  selectedRow)
 {
     if(selectedRow==0)
        return;
     //Database Connection
    //Connection con = null;
      try {
            con = DriverManager.getConnection(
            "jdbc:derby://localhost/Music","abdulrahim","abdulrahim24a");
            Statement sta = con.createStatement(); 
           ////////////////////////////////////////
            //Check if song is in playlists
                                                           
            if(playlistTitle.equals("Library")){
                    
                      try{

                           
                            con = DriverManager.getConnection("jdbc:derby://localhost/Music","abdulrahim","abdulrahim24a");
                            Statement st=con.createStatement();
                            boolean isInLibrary=false;
                            for(int i=1;i<=playlistGroup.size()-1;i++)
                            {
                                sta.executeUpdate("DELETE FROM "+playlistGroup.get(i).getPlayListTitle()+" WHERE cPath = '"+(String)model.getValueAt(selectedRow, 4)+"'");
                                
                            }

                        }
                        catch(SQLException e)
                        {
                            System.err.println(e.getMessage());
                        }
            }
            ////////////////////////////////////////
                int c = sta.executeUpdate("DELETE FROM "+playlistTitle+" WHERE cPath = '"+(String)model.getValueAt(selectedRow, 4)+"'");
                sta.close();
                con.close();        
             } catch (Exception db) {
                System.err.println("Exception: "+db.getMessage());
             }
            //DefaultTableModel model = (DefaultTableModel) table.getModel();
            //songsInPlaylist.remove(selectedRow-1);
            model.removeRow(selectedRow);
            displayPlaylist(model);
            
            //Have Rows Selected After delete on BOTH WINDOWS
            if(table.getModel().equals(model)){
            table.setRowSelectionInterval(--selectedRow, selectedRow);
         //////
            if(windowTable!=null)
                windowTable.setRowSelectionInterval(1,1);
            }
            //////
            if(windowTable!=null&&windowTable.getModel().equals(model)){
                windowTable.setRowSelectionInterval(--selectedRow, selectedRow);
                table.setRowSelectionInterval(1,1 );
            }
            
            if(playing==true)
            {
                player.stop();
                 playing=false;
            }
 
 
 }
 
 public void displayPlaylist(DefaultTableModel model)
 {
     try{
                         int rowCount=model.getRowCount();
                for (int i = rowCount - 1; i > 0; i--) {
                model.removeRow(i);
               }
     con = DriverManager.getConnection("jdbc:derby://localhost/Music","abdulrahim","abdulrahim24a");
     Statement st=con.createStatement();
     
     String sql="SELECT * FROM "+playlistTitle;
     ResultSet rs = st.executeQuery(sql);
     while(rs.next())
    {
    String a = rs.getString("cTitle");
    String b = rs.getString("cArtist");
    String c = rs.getString("cAlbum");
    String d = rs.getString("cYear");
    String e = rs.getString("cPath");
    String f = rs.getString("cGenre");
    String g = rs.getString("cComment");
    String h = rs.getString("cTime");
    model.addRow(new Object[]{a,b,c,d,e,f,g,h});
    
    }
     }
     catch(SQLException e)
     {
         System.err.println(e.getMessage());
     }
 }
 public int getMinutesFromString(String num)
 {
     String [] n =num.split(":");
     int min = Integer.parseInt(n[0]);
     return min;
 }
 
 public int getSecondsFromString(String num)
 {
    String [] n=num.split(":");
    int sec = Integer.parseInt(n[1]);
    return sec;   
 }
 
 
 
}
