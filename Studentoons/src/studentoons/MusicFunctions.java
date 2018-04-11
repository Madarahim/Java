/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package studentoons;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;

import java.io.PrintStream;
import java.io.InputStream;
import java.io.File;
import java.io.FileInputStream;

import java.sql.*;
import java.util.Random;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import javazoom.jl.player.JavaSoundAudioDevice;
import javazoom.jl.player.AudioDevice;
import javax.sound.sampled.*;
import javax.sound.sampled.Line.Info;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Port;
import javax.sound.sampled.FloatControl;

import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerEvent;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import javazoom.jlgui.basicplayer.BasicPlayerListener;

import sun.audio.AudioData;
import sun.audio.AudioDataStream;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

import javax.swing.JTable;


//import javazoom.jl.player.

/**
 *
 * @author abdulrahim
 */
public class MusicFunctions{
         
  
	private BasicPlayer player;
	private Player pPlayer;
                
        private BasicController controller;
        
	private static int p = 0;
        
        private Connection con;
        
        double volume;
        
        private int playerStatus=0;
                
        private String path;
        private String nextSong;
        
        private final static int NOTSTARTED = 0;
        private final static int PLAYING = 1;
        private final static int PAUSED = 2;
        private final static int FINISHED = 3;

        private PrintStream out = null;
        
        public static Thread t;
        int x=0;
        
        private boolean shuffle;
        private boolean repeat;
        private BasicController control;
        private String currentSong;
        private String nextRandomSong;

        JTable currentTable=new JTable();
        
	public MusicFunctions() throws ClassNotFoundException, SQLException
	{
            shuffle=false;
            repeat=false;
            out = System.out;
            player = new BasicPlayer();
            controller = (BasicController) player;
            player.addBasicPlayerListener(new Listener(this));
            volume = .75;


	}

        public void playControlRecent(String filename){
         int count=0;

                currentSong=filename;
		if(player.getStatus()==1){
			try {
                            controller.resume();
			} catch (BasicPlayerException e) {
				//TODO Auto-generated catch block
                            e.printStackTrace();
			}
		}
		else{
                    try
                    {			
                        // Open file, or URL or Stream (shoutcast) to play.
                        controller.open(new File(filename));
                        // control.open(new URL("http://yourshoutcastserver.com:8000"));

                        // Start playback in a thread.
                        controller.play();
                        
                         
                        playerStatus=PLAYING;

                        // Set Volume (0 to 1.0).
                        // setGain should be called after control.play().
                        player.setGain(volume);

                        // Set Pan (-1.0 to 1.0).
                        // setPan should be called after control.play().
                        player.setPan(0.0);
                        
                        // If you want to pause/resume/pause the played file then
                        // write a Swing player and just call control.pause(),
                        // control.resume() or control.stop().			
                        // Use control.seek(bytesToSkip) to seek file
                        // (i.e. fast forward and rewind). seek feature will
                        // work only if underlying JavaSound SPI implements
                        // skip(...). True for MP3SPI (JavaZOOM) and SUN SPI's
                        // (WAVE, AU, AIFF).

                    }
                    catch (BasicPlayerException e)
                    {
                            e.printStackTrace();
                    }
             

		}
        }
        
        
	public void play(String filename, JTable table)
	{

                int count=0;
                currentTable=table;
                currentSong=filename;
		if(player.getStatus()==1){
			try {
                            controller.resume();
			} catch (BasicPlayerException e) {
				//TODO Auto-generated catch block
                            e.printStackTrace();
			}
		}
		else{
                    try
                    {			
                        // Open file, or URL or Stream (shoutcast) to play.
                        System.out.println(filename);
                        controller.open(new File(filename));
                        // control.open(new URL("http://yourshoutcastserver.com:8000"));

                        // Start playback in a thread.
                        controller.play();
                        
                         con = DriverManager.getConnection("jdbc:derby://localhost/Music","abdulrahim","abdulrahim24a");
                        Statement st=con.createStatement();
                        Statement sta=con.createStatement();
                        String sql="SELECT * FROM Last10PlayedSongs";
                       ResultSet rs = sta.executeQuery(sql);
                       // con.setAutoCommit(false);

                      int c=st.executeUpdate("DELETE FROM Last10PlayedSongs WHERE cPath='"+filename+"'");
                      for(int i=0;i<Gui.last10SongsPlayed.size();i++)
                      {
                          if(Gui.last10SongsPlayed.get(i).getPath().equals(filename))
                              Gui.last10SongsPlayed.remove(i);
                              
                      }
                      
                      int d= st.executeUpdate("INSERT INTO Last10PlayedSongs (cTitle, cArtist, cAlbum, cYear, cPath, cGenre, cComment, cTime)"
                                + " VALUES ( '"+(String)table.getModel().getValueAt(table.getSelectedRow(), 0)+
                                 "', '"+(String)table.getModel().getValueAt(table.getSelectedRow(), 1)+
                                 "', '" +(String)table.getModel().getValueAt(table.getSelectedRow(), 2)+
                                 "', '" +(String)table.getModel().getValueAt(table.getSelectedRow(), 3)+
                                 "', '"+filename +
                                 "', '"+(String)table.getModel().getValueAt(table.getSelectedRow(), 5)+
                                 "', '"+(String)table.getModel().getValueAt(table.getSelectedRow(), 6)+
                                 "','"+(String)table.getModel().getValueAt(table.getSelectedRow(), 7)+"')");
                         while(rs.next())
                             count++;
                        // if(count==10)
                              //  st.executeUpdate("DELETE FROM Last10PlayedSongs WHERE cPath IN (SELECT cPath FROM Last10PlayedSongs ORDER BY cPath asc LIMIT 1)");
                         
                         Gui.last10SongsPlayed.add(new Last10SongsPlayed((String)table.getModel().getValueAt(table.getSelectedRow(), 0),filename));
                         Gui.last10PlayedSongs();
                         
                        playerStatus=PLAYING;

                        // Set Volume (0 to 1.0).
                        // setGain should be called after control.play().
                        player.setGain(volume);

                        // Set Pan (-1.0 to 1.0).
                        // setPan should be called after control.play().
                        player.setPan(0.0);
                        
                        // If you want to pause/resume/pause the played file then
                        // write a Swing player and just call control.pause(),
                        // control.resume() or control.stop().			
                        // Use control.seek(bytesToSkip) to seek file
                        // (i.e. fast forward and rewind). seek feature will
                        // work only if underlying JavaSound SPI implements
                        // skip(...). True for MP3SPI (JavaZOOM) and SUN SPI's
                        // (WAVE, AU, AIFF).

                    }
                    catch (BasicPlayerException e)
                    {
                            e.printStackTrace();
                    }
                    catch(SQLException sqle)
                    {
                        sqle.printStackTrace();
                    }
                    finally{
                        if(con!=null) {
                            try {
                                //con.setAutoCommit(false);
                                 con.close();
                            }
                            catch(SQLException ex) {
                                  System.out.println("Could not close query");
                            }
                       }
                    }

		}
                
	}
        
        
        public boolean isPlaying(){
            return (player.getStatus()==0);
        }
        
        
        public String getPath(){
            return currentSong;
        }
        
        
        public int getStatus(){
            return player.getStatus();
        }
        
        public void setShuffle(Boolean s){
        System.out.println("Set shuffle to "+s);
        shuffle = s;
        }
       
        public Boolean getShuffle()
        {
        return shuffle;
        }
        
        public void setRepeat(Boolean r)
        {
        System.out.println("Set repeat to "+r);
        repeat = r;
        }
        
        public Boolean getRepeat()
        {
            return repeat;
        }
    
	public void pause(){
            try{
                player.pause();
                playerStatus=PAUSED;
            }
            catch(BasicPlayerException e)
            {
                System.out.println(e.getMessage());
            }
	}
	public void stop(){
		try{
			player.stop();
			//jpb.setValue(0);
                        playerStatus=FINISHED;
			
		}
		catch(BasicPlayerException e){
			e.printStackTrace();
		}/*
		player = new BasicPlayer();
		controller = (BasicController) player;
		player.addBasicPlayerListener(this);*/
	}
	public void seek(long bytes){
		try{
			controller.seek(bytes);
		}
		catch(BasicPlayerException e){
			e.printStackTrace();
		}
	}
    
        
     public void setVolume(double vol){
            try {
                //public void setGain(double fGain) throws BasicPlayerException
                volume = vol;
                controller.setGain(volume);
                
            } catch (BasicPlayerException ex) {
                Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        public double getVolume(){
            return volume;
        }
        
        public String getNext()
        {
           return nextSong;
        }
        
        public void nextSong(String url)
        {
            nextSong=url;
        }
        
        public int getPlayerStatus()
        {
            return playerStatus;
        }
    
        public BasicController getController()
        {
            return controller;
        }
        
        public BasicPlayer getPlayer()
        {
            return player;
        }

        
    class Listener implements BasicPlayerListener { 
 
        private MusicFunctions pimp; 
 
        Listener(MusicFunctions pimp) { 
            this.pimp = pimp; 
        } 
 
        @Override 
        public void opened(Object stream, Map properties){
        }
        @Override 
        public void progress(int arg0, long arg1, byte[] arg2, Map arg3) { 
        } 
 
        @Override 
        public void setController(BasicController arg0) { 
        } 
 
        @Override
            public void stateUpdated(BasicPlayerEvent event) {
          //display("stateUpdated : "+event.toString());
                System.out.println("WE NOT THERE");
          if (event.getCode() == BasicPlayerEvent.STOPPED) {
             if (repeat) {
                try {
                   player.play();
                   //GUI.mControl.play();
                }
                catch (BasicPlayerException ex) {
                   System.out.println("cannot repeat song");
                }
             }
             else if (shuffle) {
                System.out.println("WE HERE WHAT UP");
                Random rand = new Random(); 
                int value=0;
                while(value==0){
                 value = rand.nextInt(currentTable.getRowCount());
                }
                String s = (String)Gui.table.getModel().getValueAt(value, 4); 
                currentTable.setRowSelectionInterval(value,value);

                    playControlRecent(s);
                    //player.open(new File(s));
                   // player.play();
                   // Gui.playing=true;
                        
             }
             else {

                }
          }
        }
    }; 
}
