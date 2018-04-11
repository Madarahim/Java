/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//DONE- No Regrets!!
package studentoons;


import com.mpatric.mp3agic.*;

import java.awt.Insets;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.dnd.DragSource;
import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.awt.Rectangle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


import java.lang.*;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JComponent;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.tree.*;
import javax.swing.table.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.JViewport;
import javax.swing.event.*;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.TransferHandler;
import javax.swing.DropMode;
import javax.swing.SwingConstants;
import javax.swing.KeyStroke;
import javax.swing.JProgressBar;
import javax.swing.Timer;

import javax.activation.ActivationDataFlavor;
import javax.activation.DataHandler;
import javax.swing.JCheckBox;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import javazoom.jl.decoder.JavaLayerException;


/**
 *
 * @author abdulrahim
 */



public class Gui extends JFrame{
    JPanel frame, panel, playlistPanel;
    JFrame newPanel, wPanel;
    JMenuBar menuBar, wMenuBar;
    public static JMenu file, wFile, addToPlaylist, controls, wControls, cPlayRecent,wcPlayRecent;
    JMenuItem exit, openSong, add, delete,createPlaylist, wExit, wOpenSong, wAdd, wDelete;
    public static JMenuItem cPlay,cNext,cPrevious,cGo2CurrentSong, cIncreaseVol, cDecreaseVol;
    public static JMenuItem wcPlay,wcNext,wcPrevious,wcGo2CurrentSong, wcIncreaseVol, wcDecreaseVol;
    public static JCheckBoxMenuItem cRepeat,cShuffle;
    public static JCheckBoxMenuItem wcRepeat,wcShuffle;
    public static JTable  table, windowTable;
    JButton play, stop, pause, skipNext, skipPrevious,addSong, deleteSong, playAlternate, pauseAlternate, stopAlternate,exitAlternate,
            wPlay, wStop, wPause, wSkipNext, wSkipPrevious, wAddSong, wDeleteSong,wPlayAlternate,wPauseAlternate,wStopAlternate, wExitAlternate;
    JFileChooser chooser;
    JScrollPane scroll, wScroll;
    JTree libraryTree, playlistTree;
    public static MusicFunctions player, wPlayer;
    ArrayList<Mp3File> song;
    Playlist currentPlaylist, windowPlaylist;
    FileInputStream inputStream;
    private int paused=2;
    public static int selectedRow=0, wSelectedRow=0, playingRow=0, wPlayingRow=0;
    public static boolean playing=false;
    public static String title, artist, album, year, path, genre, comment,time;
    Connection con=null;
    private GridBagConstraints gbc;
    private Random random;
    DefaultMutableTreeNode library=new DefaultMutableTreeNode("Library");
    DefaultMutableTreeNode playlist=new DefaultMutableTreeNode("Playlist");
    DefaultTreeModel treeModel;
    public static ArrayList<Playlist> playlistGroup = new ArrayList<Playlist>();
    JSlider volumeSlider, wVolumeSlider;
    public static DefaultTableModel model; ;
    public static DefaultTableModel windowModel;;
    public static boolean isShuffleOn=false, isRepeatOn=false, wIsShuffleOn=false, wIsRepeatOn=false;
    int entered=0;
    public static ArrayList<Last10SongsPlayed> last10SongsPlayed=new ArrayList<Last10SongsPlayed>();
    public static ArrayList<String> playListNames=new ArrayList<String>();  
    public static boolean stopButton;
    JPopupMenu popup=new JPopupMenu();
    JPopupMenu pmenu=new JPopupMenu();
    Statement stmt;
    Connection conn=DriverManager.getConnection("jdbc:derby://localhost/Music","abdulrahim","abdulrahim24a");;
    String defaultSortName="Title",  defaultSortOrder="ASC";
    
    
    //5/3/16
         /////////////////////////////ProgressBar
        JPanel playTime;
        JProgressBar progTime = new JProgressBar();
        JLabel startTime = new JLabel("0:00");
        JLabel finishTime = new JLabel("0:00");
        ProgressBar progBar = new ProgressBar();
        Thread x = new Thread();
        Boolean stopIsPressed = false;
        Timer timer;
        //ProgressBarCount progBarTime = new ProgressBarCount();
        Boolean pausePressed = false;
        Boolean pausePressedTime = false;
        int m, s, uc;
        setTime settingTime = new setTime(0,0);
        setTime finishingTime = new setTime(0,0);
    
    
    
    
    public  Gui() throws Exception{
        super("STUDENTOONS");
           gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.insets = new Insets(5, 5, 5, 5);

        random = new Random();
        //INITIALIZATION
        /////////////////////song =new ArrayList<Mp3File>();
        //currentPlaylist=new Playlist("Music");
        playlistGroup.add(new Playlist("Library"));
        currentPlaylist=playlistGroup.get(0);
        frame=new JPanel();
        panel = new JPanel()
        {
            @Override
            public Dimension getPreferredSize() {
                return (new Dimension(700,500));
            }
        };
        playlistPanel=new JPanel()  {
            @Override
            public Dimension getPreferredSize() {
                return (new Dimension(100,100));
            }
        };
        menuBar=new JMenuBar();   

        //SIZE
        frame.setSize(1000, 400);
       // panel.setSize(100,100);
        //playlistPanel.setMaximumSize(new Dimension(1000,100));

        //LAYOUT
        FlowLayout layout = new FlowLayout(); 
        GridLayout gLayout = new GridLayout();
        BoxLayout bLayout = new BoxLayout(playlistPanel, BoxLayout.Y_AXIS);
        GridBagLayout gbLayout = new GridBagLayout();
        frame.setLayout(gbLayout);
        panel.setLayout(layout);
        
       playlistPanel.setLayout(bLayout);
     // playlistPanel.add(Box.createRigidArea(new Dimension(500,50)));
      
        ///////
        
        
        Container container =getContentPane();
        //container.setSize(new Dimension(10000,10000));
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.add(Box.createRigidArea(new Dimension(50, 0)));

        /////
        
        //MENU-BAR
        menuBar=new JMenuBar();
        file=new JMenu("File");
        controls= new JMenu("Controls");
        
        //File JMenu Items
        openSong=new JMenuItem("Open a file");
        openSong.addActionListener(new fileMenuListener());
        file.add(openSong);
        
        add=new JMenuItem("Add a Song");
        add.addActionListener(new playButtonListener());
        file.add(add);
        
        delete=new JMenuItem("Delete a Song");
        delete.addActionListener(new playButtonListener());
        file.add(delete);
                
        createPlaylist=new JMenuItem("Create A Playlist");
        createPlaylist.addActionListener(new fileMenuListener());
        file.add(createPlaylist);
        
        exit=new JMenuItem("Exit");
        exit.addActionListener(new exitActionListener());
        file.add(exit);

        //Controls JMenuItems
        cPlay=new JMenuItem("Play");
        cPlay.addActionListener(new playButtonListener());
        cPlay.setMnemonic(KeyEvent.VK_SPACE);
        cPlay.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0));
        controls.add(cPlay);
        
        cNext=new JMenuItem("Next");
        cNext.addActionListener(new playButtonListener());
        cNext.setMnemonic(KeyEvent.VK_RIGHT);
        cNext.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT,  ActionEvent.ALT_MASK));
        controls.add(cNext);
        
        cPrevious=new JMenuItem("Previous");
        cPrevious.addActionListener(new playButtonListener());
        cPrevious.setMnemonic(KeyEvent.VK_LEFT);
        cPrevious.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT,  ActionEvent.ALT_MASK));
        controls.add(cPrevious);
        
        cPlayRecent=new JMenu("Play Recent");
        cPlayRecent.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {                                                
              //Select from one of the playlists and checks if there are any playlists      
            }
        });
        cPlayRecent.addActionListener(new playButtonListener());
        controls.add(cPlayRecent);
        
        cGo2CurrentSong=new JMenuItem("Go To Current Song");
        cGo2CurrentSong.addActionListener(new playButtonListener());
        cGo2CurrentSong.setMnemonic(KeyEvent.VK_L);
        cGo2CurrentSong.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,  ActionEvent.ALT_MASK));
        controls.add(cGo2CurrentSong);
        
        controls.add(new JSeparator(SwingConstants.HORIZONTAL));
                
        cIncreaseVol=new JMenuItem("Increase Volume");
        cIncreaseVol.addActionListener(new playButtonListener());
        cIncreaseVol.setMnemonic(KeyEvent.VK_I);
        cIncreaseVol.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.ALT_MASK));
        controls.add(cIncreaseVol);
        
        cDecreaseVol=new JMenuItem("Decrease Volume");
        cDecreaseVol.addActionListener(new playButtonListener());
        cDecreaseVol.setMnemonic(KeyEvent.VK_D);
        cDecreaseVol.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.ALT_MASK));
        controls.add(cDecreaseVol);
        
        controls.add(new JSeparator(SwingConstants.HORIZONTAL));
        
        cShuffle=new JCheckBoxMenuItem("Shuffle");
        cShuffle.addActionListener(new ActionListener(){
            
            public void actionPerformed(ActionEvent e)
            {
                if(cShuffle.isSelected())
                    player.setShuffle(true);
                else
                    player.setShuffle(false);
            }
            
        });
        controls.add(cShuffle);
        
        cRepeat=new JCheckBoxMenuItem("Repeat");
        cRepeat.addActionListener(new ActionListener(){
            
            public void actionPerformed(ActionEvent e)
            {
                if(cRepeat.isSelected())
                    player.setRepeat(true);
                else
                    player.setRepeat(false);
            }
            
        });
        controls.add(cRepeat);
        
        menuBar.add(file);
        menuBar.add(controls);
        panel.add(menuBar);  
        
        
        //BUTTONS
        play = new JButton("Play");
        play.addActionListener(new playButtonListener());
        pause = new JButton("Pause");
        pause.addActionListener(new playButtonListener());
        stop = new JButton("Stop");
        stop.addActionListener(new playButtonListener());
        skipPrevious = new JButton("|<");
        skipPrevious.addActionListener(new playButtonListener());
        skipNext= new JButton(">|");
        skipNext.addActionListener(new playButtonListener());
        addSong= new JButton("+");
        addSong.addActionListener(new playButtonListener());
        deleteSong= new JButton("-");
        deleteSong.addActionListener(new playButtonListener());
        
        panel.add(skipPrevious);
        panel.add(play);
        panel.add(pause);
        panel.add(stop);
        panel.add(skipNext);
        panel.add(addSong);
        panel.add(deleteSong);

        //JTABLE      
        table = new JTable(new DefaultTableModel(new Object[]{"Song", "Artist", "Album", "Year", "Path", "Genre","Comment", "Time"},1));
       
                popup = columnView(table);
        table.getTableHeader().addMouseListener(new MouseAdapter(){
            public void mouseReleased(MouseEvent e){
                
               if(e.getButton()==MouseEvent.BUTTON3){
   
                    popup.show(table, e.getX(), e.getY());
                }
            }
        });
        //Hide Path Column
        TableColumnModel tcm = table.getColumnModel();
        tcm.removeColumn(tcm.getColumn(4));
       
        
        //JScroll
        scroll=new JScrollPane(table);
        panel.add(scroll);
        this.add(panel);
        
        //TREE
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("Studentoons");
        
        top.add(library);
        top.add(playlist);
          treeModel = new DefaultTreeModel(top);
                 treeModel.addTreeModelListener(new MyTreeModelListener());
        playlistTree=new JTree(treeModel);
        playlistTree.setEditable(true);
        playlistTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        playlistTree.addTreeSelectionListener(new MyTreeSelectionListener());
        //TREE POP-UP MENU
        final JPopupMenu treePopUp=new JPopupMenu();
        JMenuItem openNewWindow = new JMenuItem("Open In New Window");
        openNewWindow.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {                                               
                 DefaultMutableTreeNode node = (DefaultMutableTreeNode)playlistTree.getSelectionPath().getLastPathComponent();
                 if(node.getUserObject().toString().equals("Library"))
                     return;
                 for(int k=1;k<playlistGroup.size();k++)
                 {
                     if(playlistGroup.get(k).getPlayListTitle().equals(node.getUserObject().toString())){
                        windowPlaylist=playlistGroup.get(k);
                        break;
                     }
                 }
                 newWindow(node.getUserObject().toString());
                 currentPlaylist=playlistGroup.get(0);
                 currentPlaylist.displayPlaylist(model);
                 //Bottom line does almost nothing
                 playlistTree.setSelectionPath(new TreePath (playlistTree.getSelectionPath().getPathComponent(1)));
                   
            }
        });
        treePopUp.add(openNewWindow);
        
        JMenuItem deletePlaylist = new JMenuItem("Delete Playlist");
        deletePlaylist.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {                                                
                DefaultMutableTreeNode dmtn, node;
                

                if (e.getActionCommand().equals("Delete Playlist")) {
                 node = (DefaultMutableTreeNode)playlistTree.getSelectionPath().getLastPathComponent();
                                             System.out.println("UHHH WHERE AM I!!!");
                    if(node.getUserObject().toString().equals("Library"))
                        return;
                 for (int i=1; i<playlistGroup.size(); i++){
                     System.out.println(playlistGroup.get(i).getPlayListTitle()+" = "+node.getUserObject());
                    if(playlistGroup.get(i).getPlayListTitle().equals(node.getUserObject().toString())){
                        System.out.println(" ENTERED THE VOID ");
                        deletePlaylist(playlistGroup.get(i).getPlayListTitle());
                        if(currentPlaylist.equals(playlistGroup.get(i))){
                            currentPlaylist=playlistGroup.get(0);
                            currentPlaylist.displayPlaylist(model);
                        }
                        
                        addToPlaylist.remove(addToPlaylist.getMenuComponent(i-1));
                        playlistGroup.remove(i);
                        refreshBothPlaylists();
                        break;
                    }
                    
                 }
                 
                                             System.out.println("UHHH WHERE AM I!!!");

                 node.removeFromParent();
                ((DefaultTreeModel )playlistTree.getModel()).nodeStructureChanged((TreeNode)node); 
                for (int i = 0; i < playlistTree.getRowCount(); i++) {
                    playlistTree.expandRow(i);
}
                 }
            }
        });
        treePopUp.add(deletePlaylist);
        
        playlistTree.setComponentPopupMenu(treePopUp);
        container.add(playlistTree);
       JScrollPane newScroll= new JScrollPane(container);

        playlistPanel.add(newScroll);
       

        //ADD TO FRAME
        addComp(frame, playlistPanel,  0, 0, 1, 1,
                    GridBagConstraints.BOTH, 1.0, 1.0);
         addComp(frame, panel, 1, 0, 1, 1,
                    GridBagConstraints.BOTH, 0.0, 1.0);

        
        //SET SELECTED ROW TO ZERO
        table.setRowSelectionInterval(selectedRow, selectedRow);
        
        //JSlider
    
       // FileInputStream is = new FileInputStream("/Users/abdulrahim/Music/Blank.mp3");
        player=new MusicFunctions();
        
     
        volumeSlider = new JSlider(0,100, (int)(player.getVolume()*100));
                volumeSlider.addChangeListener(new volumeListener());
                panel.add(volumeSlider);
        //JPOPUP Add
        final JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem addItem = new JMenuItem("Add");
        addItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {                                                
                selectedRow=table.getSelectedRow();

                addChooser(table, currentPlaylist);            
            }
        });
        popupMenu.add(addItem);
        
        //JPOPUP Delete
        JMenuItem deleteItem = new JMenuItem("Delete");
        deleteItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {                                                
                selectedRow=table.getSelectedRow();
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                currentPlaylist.delete(model, selectedRow);            
            }
        });
        popupMenu.add(deleteItem);
        
        //JPOPUP ADD to Playlist
         addToPlaylist = new JMenu("Add to Playlist");
        addToPlaylist.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {                                                
              //Select from one of the playlists and checks if there are any playlists      
            }
        });
        popupMenu.add(addToPlaylist);
        
        table.setComponentPopupMenu(popupMenu);
        
        /////////////
        
        
        //DRAG AND DROP FEATURE
        /////////////
        
table.setDropTarget(new DropTarget() {
                @Override
                public synchronized void dragOver(DropTargetDragEvent dtde) {
                    Point point = dtde.getLocation();
                    int row = table.rowAtPoint(point);
                    if (row < 0) {
                        table.clearSelection();
                    } else {
                        table.setRowSelectionInterval(row, row);
                    }
                    dtde.acceptDrag(DnDConstants.ACTION_COPY_OR_MOVE);
                }

                @Override
                public synchronized void drop(DropTargetDropEvent dtde) {
       
                    if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                        dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
                        Transferable t = dtde.getTransferable();
                        List fileList = null;
                        try {
                            fileList = (List) t.getTransferData(DataFlavor.javaFileListFlavor);
                            if (fileList.size() > 0) {
                                table.clearSelection();
                                Point point = dtde.getLocation();
                                int row = table.rowAtPoint(point);
                                DefaultTableModel model = (DefaultTableModel) table.getModel();
                                for (Object value : fileList) {
                                    if (value instanceof File) {
                                        File f = (File) value;
                                        title=f.getName();
                                        
                                        
                                        if (row < 0) {
                                             currentPlaylist.add(f.getAbsolutePath(), model);
                                             //if(windowTabl)
                                        } else {
                                             currentPlaylist.add(f.getAbsolutePath(), model);
                                             System.out.println(f.getAbsolutePath());
                                            row++;
                                        }
                                    }
                                }
                            }
                        } catch (UnsupportedFlavorException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        dtde.rejectDrop();
                    }
                }

            });

        
        /////////////////////

        //CLOSE-OPERATION
        //frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        JFrame realFrame = new JFrame();
        realFrame.setContentPane(frame);
        realFrame.pack();
        realFrame.setLocationByPlatform(true);
        table.setDragEnabled(true);
        table.setDropMode(DropMode.USE_SELECTION);      
        table.setTransferHandler(new TableRowTransferHandler());
        
        
        table.addMouseListener(new MouseAdapter(){
            public void mouseReleased(MouseEvent e){
                int rnum;
               if(e.getButton()==MouseEvent.BUTTON3)
               {
     
                int r = table.rowAtPoint(e.getPoint());
                if(r>=0 && r<table.getRowCount())
                {
                    table.setRowSelectionInterval(r,r);
                }
                else
                {
                    table.clearSelection();
                }
                rnum = table.getSelectedRow();
                if (rnum < 0)
                      return;
                    pmenu.show(table, e.getX(), e.getY());
                }
            }
        });    
        
         table.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getButton()==MouseEvent.BUTTON1){
                    if(e.getClickCount()==2){
                        int col = table.columnAtPoint(e.getPoint());
                        String name = table.getColumnName(col);
                        defaultSortName=name;
                        defaultSortOrder="DESC";
                        sort(name, "DESC");
                    }
                    else{
                        int col = table.columnAtPoint(e.getPoint());
                        String name = table.getColumnName(col);
                        defaultSortName=name;
                        defaultSortOrder="ASC";
                        sort(name, "ASC");
                    }        
                }
            }
        });
        
        panel.add(progBar);
        refreshEverything();
        //VISIBILITY
        realFrame.setVisible(true);
         

    }
    
    public void sort(String name, String ord){
        if(name.equals("Title")){
            name="cTitle";
        }
        if(name.equals("Length")){
            name="cTime";
        }      
        try {
            model = sortTable(name, ord);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        table.setModel(model);
        TableColumnModel tcm = table.getColumnModel();
        tcm.removeColumn(tcm.getColumn(4));
        
        //DO SHIT 
        SetToVisibleColumns();
    }
    public DefaultTableModel sortTable(String name, String choice) throws SQLException {
           
        String query = "select * from Library order by "+name+" "+choice;
    
        String[] columnName = {"cTitle", "cArtist", "cAlbum", "cYear", "cPath", "cGenre", "cComment","cTime"};
        Object[][] data = new Object[getRowNumber()][7];       
        DefaultTableModel dtm = new DefaultTableModel(0,0);      
        dtm.setColumnIdentifiers(columnName);
        
        try{
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            dtm.addRow(new Object[]{null, null, null, null, null, null, null, null});
            //Dummy row so code keeps working normal
            while(rs.next()){
                   String a = rs.getString("cTitle");
                   String b = rs.getString("cArtist");
                   String c = rs.getString("cAlbum");
                   String d = rs.getString("cYear");
                   String e = rs.getString("cPath");
                   String f = rs.getString("cGenre");
                   String g = rs.getString("cComment");
                   String h = rs.getString("cTime");
                   //model.addRow(new Object[]{a,b,c,d,e,f,g,h});
                dtm.addRow(new Object[] {a,b,c,d,e,f,g,h});
            }
            
        }
       catch(SQLException e){
            System.out.println(e);
        }
        return dtm;
    }
    
        public int getRowNumber()throws SQLException{
        int no_rows =0;
        ResultSet rs;
        rs = stmt.executeQuery("SELECT cArtist FROM Library");
        while(rs.next()){
            no_rows++;
        }
        return no_rows;
            
    }
    private void addComp(JPanel panel, JComponent comp, int gridx,
                            int gridy, int gridwidth, int gridheight,int fill, double weightx, double weighty) {
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.gridwidth = gridwidth;
        gbc.gridheight = gridheight;
        gbc.fill = fill;
        gbc.weightx = weightx;
        gbc.weighty = weighty;

        panel.add(comp, gbc);
    }

    public void changePlayerTitle()
    {
    
    }
    
    
    public void playSeparate()
    {
         chooser = new JFileChooser();
                
                chooser.setCurrentDirectory(new java.io.File("/Users/abdulrahim/Desktop/Music"));
                chooser.setDialogTitle("Choose an MP3 File to Play");
                
                if(chooser.showOpenDialog(openSong)==JFileChooser.APPROVE_OPTION)
                {
                
                try{
                   
                    FlowLayout layout = new FlowLayout();   

                    newPanel=new JFrame();
                    //newPanel.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                    newPanel.setSize(500, 50);
                    newPanel.setLayout(layout);



                    playAlternate = new JButton("Play");
                    playAlternate.addActionListener(new playButtonListener());
                        
                    pauseAlternate = new JButton("Pause");
                    pauseAlternate.addActionListener(new playButtonListener());
                 
                    stopAlternate=new JButton("Stop");
                    stopAlternate.addActionListener(new playButtonListener());
                    
                    exitAlternate=new JButton("Exit");
                    exitAlternate.addActionListener(new playButtonListener());
                      
                    newPanel.setTitle(chooser.getSelectedFile().getName());
                    newPanel.add(playAlternate);
                    newPanel.add(pauseAlternate);
                    newPanel.add(stopAlternate);
                    newPanel.add(exitAlternate);
                    
                    /*if(newPanel.)
                    {
                        
                    }*/
                    newPanel.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                    newPanel.setVisible(true);

                }
                catch(Exception ex)
                {}
                
                }
    }
    
    
    class playButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
           System.out.println("You pressed " + e.getActionCommand());
           if(table.getSelectedRow()>-1)
           System.out.println("Song row " + table.getModel().getValueAt(table.getSelectedRow(), 4) + " was selected.");
           

            //PLAY-ALTERNATE
            ////////////////////////////////////////////////////////////////////
           if(e.getSource().equals(playAlternate) )
           {
           
                   if(playing==false)
                   {
                    //inputStream= new FileInputStream(chooser.getSelectedFile().getAbsolutePath()); 
                                                try{    
                                                player= new MusicFunctions();
                                                }
                                                catch(Exception ee){}
                    player.play(chooser.getSelectedFile().getAbsolutePath(),table);
                    playing=true;
                   }
                   else if(playing==true){
                       if(player.getPlayerStatus()==paused)
                       {
                           player.play((String)table.getModel().getValueAt(table.getSelectedRow(), 4),table);
                           return;
                        }
                       player.stop();
                                 //inputStream= new FileInputStream(chooser.getSelectedFile().getAbsolutePath()); 
                                                try{    
                                                player= new MusicFunctions();
                                                }
                                                catch(Exception ee){}
                                                
                    player.play(chooser.getSelectedFile().getAbsolutePath(),table);
                    playing=true;
                
                        }
               
        
           }
            ////////////////////////////////////////////////////////////////////
           
           
           
            //PAUSE-ALTERNATE
            ////////////////////////////////////////////////////////////////////
                else if(e.getSource().equals(pauseAlternate) )
                {
                        player.pause(); 
             
                }
            ////////////////////////////////////////////////////////////////////
            
                
                
            //STOP-ALTERNATE
            ////////////////////////////////////////////////////////////////////    
                    else if(e.getSource().equals(stopAlternate))
                    {
                            player.stop();
                            playing=false;
                    }
            ////////////////////////////////////////////////////////////////////
                    
                    
                    
            //PLAY 
            ////////////////////////////////////////////////////////////////////
                     else if( e.getSource().equals(play)||e.getSource().equals(cPlay))
                     {
                        int value;
                            if(player.getShuffle())
                            {
                                  Random rand = new Random(); 
                                 value = rand.nextInt(table.getRowCount());
                                 table.setRowSelectionInterval(value, value);
                            }
                            int row =selectedRow;
                            selectedRow=table.getSelectedRow();
                            if(playing==false){
                            //
                            
                                    if(selectedRow==0)
                                        return;
                                            else
                                            {
                                                    //inputStream= new FileInputStream(); 
                                                    //System.out.println(inputStream.toString());
                                                try{    
                                                player= new MusicFunctions();
                                                }
                                                catch(Exception ee){}
                                                    player.play((String)table.getModel().getValueAt(table.getSelectedRow(), 4),table);
                                                    playingRow=table.getSelectedRow();
                                                    playing=true;
                                                   stopButton=false;
                                                    
                                            }
                                }
                            else if(playing==true)
                            {
                               if(selectedRow==0)
                                        return;
                               if(player.getPlayerStatus()==paused&&selectedRow==row)
                                {
                                    //RESUME PLAYING SAME SONG
                                    player.play((String)table.getModel().getValueAt(table.getSelectedRow(), 4),table);
                                    return;
                                }
                                //STOP OTHER SONG PLAYING SO WE CAN START NEW SONG
                                player.stop();
                                
                                //inputStream= new FileInputStream((String)table.getValueAt(table.getSelectedRow(), 4)); 
                                                try{    
                                                player= new MusicFunctions();
                                                }
                                                catch(Exception ee){}
                                player.play((String)table.getModel().getValueAt(table.getSelectedRow(), 4),table);
                                playing=true;
                                   stopButton=false;
                            }
                            
                            
                    
                        

                     }
            ////////////////////////////////////////////////////////////////////
                     
                     
                     
                     
            //PAUSE 
            ////////////////////////////////////////////////////////////////////         
                         else if(e.getSource().equals(pause))
                         {
                                selectedRow=table.getSelectedRow();
                                
                                if(selectedRow==0)
                                    return;
                                else
                                 player.pause(); 
                                 
                         }
            ////////////////////////////////////////////////////////////////////
                         
                
                         
                         
            //STOP
            ////////////////////////////////////////////////////////////////////
                             else if(e.getSource().equals(stop))
                             {
                                 selectedRow=table.getSelectedRow();
                                 
                                 if(selectedRow==0)
                                     return;
                                 else{
                                     player.stop();
                                     playing=false;
                                 }
                                 stopButton=true;
                             }
            ////////////////////////////////////////////////////////////////////
                             
                
                             
                             
            //SKIP-NEXT
            ////////////////////////////////////////////////////////////////////
                                 else if(e.getSource().equals(skipNext) || e.getSource().equals(cNext))
                                 {

                                     if(playing==true)
                                     {
                                        player.stop();
                                        System.out.println(player.getShuffle());
                                                                             if(player.getShuffle())
                                     {
                                         return;
                                     }
                                     }
                                     
                                     selectedRow=table.getSelectedRow();
                                     
                                     if(selectedRow==0)
                                         return;
                                     
                                     else if(selectedRow==(table.getRowCount()-1))
                                         selectedRow=1;
                                     
                                        else
                                            selectedRow++;
                                     table.setRowSelectionInterval(selectedRow, selectedRow);
                                     

                                        //inputStream= new FileInputStream((String)table.getValueAt(table.getSelectedRow(), 4));
                                                try{    
                                                player= new MusicFunctions();
                                                }
                                                catch(Exception ee){}
                                        playing=true;
                                        
                                        player.play((String)table.getModel().getValueAt(table.getSelectedRow(), 4), table);
                                       
                                        playingRow=table.getSelectedRow();

                                     
                                    
                                 }
            ////////////////////////////////////////////////////////////////////                     
              
                
                                 
                                 
            //SKIP-PREVIOUS
            ////////////////////////////////////////////////////////////////////
                                     else if(e.getSource().equals(skipPrevious) || e.getSource().equals(cPrevious))
                                     {
                                        if(playing==true)
                                        {
                                            player.stop();
                                            if(player.getShuffle())
                                     {
                                         return;
                                     }
                                        }
                                        
                                        selectedRow=table.getSelectedRow();
                                        
                                        if(selectedRow==0)
                                            return;
                                            else if(selectedRow==1)
                                            {}
                                                else
                                                    selectedRow--;
                                        
                                        table.setRowSelectionInterval(selectedRow, selectedRow);
             
                                               //inputStream= new FileInputStream((String)table.getValueAt(table.getSelectedRow(), 4));
                                                try{    
                                                player= new MusicFunctions();
                                                }
                                                catch(Exception ee){}
                                               playing=true;
                                               player.play((String)table.getModel().getValueAt(table.getSelectedRow(), 4),table);
                                               
                                               playingRow=table.getSelectedRow();


                                     }
            ////////////////////////////////////////////////////////////////////                        
              
                   
                                     
                                     
            //ADD-SONG and ADD
            ////////////////////////////////////////////////////////////////////
                                        else if(e.getSource().equals(addSong) || e.getSource().equals(add))
                                        {
                                            addChooser(table, currentPlaylist);
                                            refreshBothPlaylists();
                                            sort(defaultSortName, defaultSortOrder);
                                            
                                        }
            ////////////////////////////////////////////////////////////////////                            
                                        
                         
                                        
                                        
                                        
            //DELETE-SONG and DELETE
            ////////////////////////////////////////////////////////////////////
                                            else if(e.getSource().equals(deleteSong)||e.getSource().equals(delete))
                                            {
                                                
                                    
                                                if(playing==true)
                                                {
                                                    player.stop();
                                                }
                                                selectedRow=table.getSelectedRow();
                                                if(selectedRow==0)
                                                    return;
                                                else{
                                                     DefaultTableModel model = (DefaultTableModel) table.getModel();

                                                        currentPlaylist.delete(model, selectedRow);
                                                        refreshBothPlaylists();
                                                }
                                            }
            ////////////////////////////////////////////////////////////////////
                                        
           
            
           
            //EXIT-ALTERNATE
            ////////////////////////////////////////////////////////////////////
                                            else if(e.getSource().equals(exitAlternate))
                                            {
                                                if(playing==true||player.getPlayerStatus()==paused)
                                                {
                                                    player.stop();
                                                    newPanel.dispose();
                                                }
                                                else
                                                {
                                                        newPanel.dispose();
                                                }
                                            }
           
            
            ////////////////////////////////////////////////////////////////////
                                        
             
            //Scroll to Playing Song
            ////////////////////////////////////////////////////////////////////
                                                     else if(e.getSource().equals(cGo2CurrentSong))
                                                     {
                                                         if(table.getSelectedRow()==-1)
                                                          return;   
                                                         if(table.getSelectedRow()!=playingRow){
                                                             table.setRowSelectionInterval(playingRow, playingRow);
                                                             selectedRow=playingRow;
                                                         }
                                                           
                                                            scrollToVisible(table,table.getSelectedRow(),0);
                                                      
                                                     }
            ////////////////////////////////////////////////////////////////////
           
            //VOLUME Increase Or Decrease                              
            ////////////////////////////////////////////////////////////////////                                               
                else if(e.getSource().equals(cIncreaseVol))
               {
                   int iVolume= volumeSlider.getValue();
                       iVolume+=5;
                       //int iVolume=((int)(volume*0.05)+(int)volume)*100;

                   if(iVolume>100)
                       volumeSlider.setValue(100);
                      else 
                          volumeSlider.setValue(iVolume);


               }
                  else if(e.getSource().equals(cDecreaseVol))
                  {
                      int dVolume=volumeSlider.getValue();
                          dVolume-=5;

                      if(dVolume<0)
                          volumeSlider.setValue(0);
                         else
                              volumeSlider.setValue(dVolume);
                      //player.setVolume(volume);
                  }
            ////////////////////////////////////////////////////////////////////
           
        }
                               
    }

    
    class fileMenuListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if(e.getSource().equals(openSong))
            {
                playSeparate();
            }
            else if(e.getSource().equals(createPlaylist))
            {
                String s = (String)JOptionPane.showInputDialog(
                 frame,"Choose a unique name for your PlayList: ");
                if(s!=null)
                {
                addObject(new DefaultMutableTreeNode(s));
                playlistGroup.add(new Playlist(s));
                model=(DefaultTableModel) table.getModel();
                currentPlaylist=playlistGroup.get(playlistGroup.size()-1);
                currentPlaylist.displayPlaylist(model);
                addToPlaylist(currentPlaylist.getPlayListTitle());
                }
            }
            
        }
    }
    
    
 class exitActionListener implements ActionListener{
   
     public void actionPerformed(ActionEvent e)
    {
      
        if(e.getSource().equals(wExit))
        {
            wPlayer.stop();
            wPanel.dispose();
            System.out.println(windowPlaylist);
            return;
        }
        
        System.exit(0);
        
    }
 }
 
 
 public void addChooser(JTable table, Playlist currentPlaylist)
 {
    chooser=new JFileChooser();
    chooser.setCurrentDirectory(new java.io.File("/Users/abdulrahim/Music"));
    chooser.setDialogTitle("Choose an MP3 File to Add to the Library");

    if(chooser.showOpenDialog(addSong)==JFileChooser.APPROVE_OPTION)
    {
        title=chooser.getSelectedFile().getName();
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        currentPlaylist.add(chooser.getSelectedFile().getAbsolutePath(),model);
    }
    else
        return;
 }
 
 class MyTreeSelectionListener implements TreeSelectionListener
 {
            public void valueChanged(TreeSelectionEvent e) {
      //Returns the last path element of the selection.
      //This method is useful only when the selection model allows a single selection.
          DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                             playlistTree.getLastSelectedPathComponent();

          if (node == null)
          //Nothing is selected.     
          return;

          Object nodeInfo = node.getUserObject();
          if (node.isLeaf()) {
              DefaultTableModel model= (DefaultTableModel) (table.getModel());
               System.out.println("Yes is a Leaf");
              for(int i=0; i<=playlistGroup.size()-1; i++){
                  if(playlistGroup.get(i).getPlayListTitle()== nodeInfo.toString())
                  {
                      System.out.println("Found");
                      currentPlaylist=playlistGroup.get(i);

                      playlistGroup.get(i).displayPlaylist(model);
                      table.setRowSelectionInterval(0,0);
                      
                      break;
                  }
                  if(playlistGroup.get(i).getPlayListTitle().equals("Library"))
                                                                  SetToVisibleColumns();

                       
              }
             
              System.out.println("CPLAYLIST="+currentPlaylist);

           // System.out.println(nodeInfo);
          } else {
              System.out.println("PLLLLZ"); 
          }
      }
 }
 
 
 class MyTreeModelListener implements TreeModelListener {
    public void treeNodesChanged(TreeModelEvent e) {
        DefaultMutableTreeNode node;
        node = (DefaultMutableTreeNode)
                 (e.getTreePath().getLastPathComponent());
        System.out.println(node.getChildAt(e.getChildIndices()[0]));
        /*
         * If the event lists children, then the changed
         * node is the child of the node we have already
         * gotten.  Otherwise, the changed node and the
         * specified node are the same.
         */
        try {
            int index = e.getChildIndices()[0];
            node = (DefaultMutableTreeNode)
                   (node.getChildAt(index));
        } catch (NullPointerException exc) {}

        System.out.println("The user has finished editing the node.");
        System.out.println("New value: " + node.getUserObject());
    }
    public void treeNodesInserted(TreeModelEvent e) {System.out.println("NANI!!!!!");
    }
    public void treeNodesRemoved(TreeModelEvent e) {
        
    }
    public void treeStructureChanged(TreeModelEvent e) {
    }  
}
 
 
 public DefaultMutableTreeNode addObject(Object child) {
    DefaultMutableTreeNode parentNode = null;
    TreePath parentPath = playlistTree.getSelectionPath();

    if (parentPath == null) {
        //There is no selection. Default to the root node.
        parentNode = playlist;
    } else {
        parentNode = (DefaultMutableTreeNode)
                     (parentPath.getLastPathComponent());
    }

    return addObject(parentNode, child, true);
}

 
public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent,
                                        Object child,
                                        boolean shouldBeVisible) {
    DefaultMutableTreeNode childNode =
            new DefaultMutableTreeNode(child);

    treeModel.insertNodeInto(childNode, parent,
                             parent.getChildCount());

    //Make sure the user can see the lovely new node.
    if (shouldBeVisible) {
        playlistTree.scrollPathToVisible(new TreePath(childNode.getPath()));
    }
    return childNode;
}

public void deletePlaylist(String tableName)
{
 try {
              con = DriverManager.getConnection(
              "jdbc:derby://localhost/Music","abdulrahim","abdulrahim24a");
              Statement sta = con.createStatement(); 

              int c;
     
                  c=sta.executeUpdate("DROP TABLE "+tableName);
              
              sta.close();
              con.close();        
            } 
        catch (Exception db) {
              System.err.println("Exception: "+db.getMessage());
            }
}


class volumeListener implements ChangeListener{
    
 @Override
 public void stateChanged(ChangeEvent e)
 {
     double volume = (double) volumeSlider.getValue()/100;
     player.setVolume(volume);
     
     
 }
}


public void newWindow(String playlistName)
{
    
        //song =new ArrayList<Mp3File>();
        //this.setSize(700, 400);
        //this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        wPanel = new JFrame(playlistName);
        wPanel.setSize(700,400);
        wPanel.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                
        wMenuBar=new JMenuBar();
        
        FlowLayout layout = new FlowLayout();   
        wPanel.setLayout(layout);
        
        wMenuBar=new JMenuBar();
        wFile=new JMenu("File");
        wControls= new JMenu("Controls");
        //file.addMenuListener(new thisMenuListener());
        //menuBar.add(file);
        
        
        wOpenSong=new JMenuItem("Open a file");
        wOpenSong.addActionListener(new fileMenuListener());
        wFile.add(wOpenSong);
        
        wAdd=new JMenuItem("Add a Song");
        wAdd.addActionListener(new windowButtonListener());
        wFile.add(wAdd);
        
        wDelete=new JMenuItem("Delete a Song");
        wDelete.addActionListener(new windowButtonListener());
        wFile.add(wDelete);
        
        wExit=new JMenuItem("Exit");
        wExit.addActionListener(new exitActionListener());
        wFile.add(wExit);
        
        //Controls JMenuItems
        wcPlay=new JMenuItem("Play");
        wcPlay.addActionListener(new windowButtonListener());
        wcPlay.setMnemonic(KeyEvent.VK_SPACE);
        wcPlay.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0));
        wControls.add(wcPlay);
        
        wcNext=new JMenuItem("Next");
        wcNext.addActionListener(new windowButtonListener());
        wcNext.setMnemonic(KeyEvent.VK_RIGHT);
        wcNext.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT,  ActionEvent.ALT_MASK));
        wControls.add(wcNext);
        
        wcPrevious=new JMenuItem("Previous");
        wcPrevious.addActionListener(new windowButtonListener());
        wcPrevious.setMnemonic(KeyEvent.VK_LEFT);
        wcPrevious.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT,  ActionEvent.ALT_MASK));
        wControls.add(wcPrevious);
        
        wcPlayRecent=new JMenu("Play Recent");
        wcPlayRecent.addActionListener(new windowButtonListener());
        wControls.add(wcPlayRecent);
        
        wcGo2CurrentSong=new JMenuItem("Go To Current Song");
        wcGo2CurrentSong.addActionListener(new windowButtonListener());
        wcGo2CurrentSong.setMnemonic(KeyEvent.VK_L);
        wcGo2CurrentSong.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,  ActionEvent.ALT_MASK));
        wControls.add(wcGo2CurrentSong);
        
        controls.add(new JSeparator(SwingConstants.HORIZONTAL));
                
        wcIncreaseVol=new JMenuItem("Increase Volume");
        wcIncreaseVol.addActionListener(new windowButtonListener());
        wcIncreaseVol.setMnemonic(KeyEvent.VK_I);
        wcIncreaseVol.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.ALT_MASK));
        wControls.add(wcIncreaseVol);
        
        wcDecreaseVol=new JMenuItem("Decrease Volume");
        wcDecreaseVol.addActionListener(new windowButtonListener());
        wcDecreaseVol.setMnemonic(KeyEvent.VK_D);
        wcDecreaseVol.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.ALT_MASK));
        wControls.add(wcDecreaseVol);
        
        wControls.add(new JSeparator(SwingConstants.HORIZONTAL));
        
        wcShuffle=new JCheckBoxMenuItem("Shuffle");
        wcShuffle.addActionListener(new ActionListener(){
            
            public void actionPerformed(ActionEvent e)
            {
                if(wcShuffle.isSelected())
                    player.setShuffle(true);
                else
                    player.setShuffle(false);
            }
            
        });
        wControls.add(wcShuffle);
        
        wcRepeat=new JCheckBoxMenuItem("Repeat");
        wcRepeat.addActionListener(new ActionListener(){
            
            public void actionPerformed(ActionEvent e)
            {
                if(wcRepeat.isSelected())
                    player.setRepeat(true);
                else
                    player.setRepeat(false);
            }
            
        });
        wControls.add(wcRepeat);
        
        wMenuBar.add(wFile);
        wMenuBar.add(wControls);
        wPanel.add(wMenuBar);  
        
        
        
        wPlay = new JButton("Play");
        wPlay.addActionListener(new windowButtonListener());
        wPause = new JButton("Pause");
        wPause.addActionListener(new windowButtonListener());
        wStop = new JButton("Stop");
        wStop.addActionListener(new windowButtonListener());
        wSkipPrevious = new JButton("|<");
        wSkipPrevious.addActionListener(new windowButtonListener());
        wSkipNext= new JButton(">|");
        wSkipNext.addActionListener(new windowButtonListener());
        wAddSong= new JButton("+");
        wAddSong.addActionListener(new windowButtonListener());
        wDeleteSong= new JButton("-");
        wDeleteSong.addActionListener(new windowButtonListener());
        
        wPanel.add(wSkipPrevious);
        wPanel.add(wPlay);
        wPanel.add(wPause);
        wPanel.add(wStop);
        wPanel.add(wSkipNext);
        wPanel.add(wAddSong);
        wPanel.add(wDeleteSong);
        
               
        windowTable = new JTable(new DefaultTableModel(new Object[]{"Song", "Artist", "Album", "Year", "Path", "Genre","Comment", "Time"},1));
        //Hide Column
        TableColumnModel tcm = windowTable.getColumnModel();
        tcm.removeColumn(tcm.getColumn(4));
        
        wScroll=new JScrollPane(windowTable);
        wPanel.add(wScroll);
        //this.add(wPanel);
        wSelectedRow=0;
        windowTable.setRowSelectionInterval(wSelectedRow, wSelectedRow);
        try{        
        wPlayer= new MusicFunctions();
        }
        catch(Exception e)
        {
            
        }
        wVolumeSlider = new JSlider(0,100, (int)(wPlayer.getVolume()*100));
                wVolumeSlider.addChangeListener(new wVolumeListener());
                wPanel.add(wVolumeSlider);
                
        /////////////
        
windowTable.setDropTarget(new DropTarget() {
                @Override
                public synchronized void dragOver(DropTargetDragEvent dtde) {
                    Point point = dtde.getLocation();
                    int row = windowTable.rowAtPoint(point);
                    if (row < 0) {
                        windowTable.clearSelection();
                    } else {
                        windowTable.setRowSelectionInterval(row, row);
                    }
                    dtde.acceptDrag(DnDConstants.ACTION_COPY_OR_MOVE);
                }

                @Override
                public synchronized void drop(DropTargetDropEvent dtde) {
       
                    if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                        dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
                        Transferable t = dtde.getTransferable();
                        List fileList = null;
                        try {
                            fileList = (List) t.getTransferData(DataFlavor.javaFileListFlavor);
                            if (fileList.size() > 0) {
                                windowTable.clearSelection();
                                Point point = dtde.getLocation();
                                int row = windowTable.rowAtPoint(point);
                                DefaultTableModel model = (DefaultTableModel) windowTable.getModel();
                                for (Object value : fileList) {
                                    if (value instanceof File) {
                                        File f = (File) value;
                                        title=f.getName();
                                        /////////////////////////
                                        
                                        if (row < 0) {
                                            for(int i=0;i<playlistGroup.size();i++)
                                                if(playlistName.equals(playlistGroup.get(i).playlistTitle))
                                                    windowPlaylist.add(f.getAbsolutePath(), model);
                                                    refreshBothPlaylists();
                                             //currentPlaylist.add(f.getAbsolutePath(), model);
                                        } else {
                                            for(int i=0;i<playlistGroup.size();i++)
                                                if(playlistName.equals(playlistGroup.get(i).playlistTitle))
                                                    windowPlaylist.add(f.getAbsolutePath(), model);
                                                    refreshBothPlaylists(); 
                                             //currentPlaylist.add(f.getAbsolutePath(), model);
                                            row++;
                                        }
                                    }
                                }
                            }
                        } catch (UnsupportedFlavorException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        dtde.rejectDrop();
                    }
                }

            });
        
        windowTable.setDragEnabled(true);
        windowTable.setDropMode(DropMode.USE_SELECTION);      
        windowTable.setTransferHandler(new TableRowTransferHandler());
        
        windowPlaylist.displayPlaylist((DefaultTableModel) windowTable.getModel());
        /////////////////////

        wPanel.setVisible(true);

}

 class wVolumeListener implements ChangeListener{
 @Override
 public void stateChanged(ChangeEvent e)
 {
     double volume = (double) wVolumeSlider.getValue()/100;
     wPlayer.setVolume(volume);
 }
 }
 
class windowButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
           System.out.println("You pressed " + e.getActionCommand());
           System.out.println("Song row " + windowTable.getModel().getValueAt(windowTable.getSelectedRow(), 4) + " was selected.");
           

            //PLAY-ALTERNATE
            ////////////////////////////////////////////////////////////////////
           if(e.getSource().equals(wPlayAlternate) )
           {
           
                   if(playing==false)
                   {
                    //inputStream= new FileInputStream(chooser.getSelectedFile().getAbsolutePath()); 
                       try{
                    wPlayer = new MusicFunctions();
                       }
                       catch(Exception ee)
                       {}
                    wPlayer.play(chooser.getSelectedFile().getAbsolutePath(),windowTable);
                    playing=true;
                   }
                   else if(playing==true){
                       if(wPlayer.getPlayerStatus()==paused)
                       {
                           wPlayer.play((String)windowTable.getModel().getValueAt(windowTable.getSelectedRow(), 4),windowTable);
                           return;
                        }
                       wPlayer.stop();
                                 //inputStream= new FileInputStream(chooser.getSelectedFile().getAbsolutePath()); 
                                         try{
                    wPlayer = new MusicFunctions();
                       }
                       catch(Exception ee)
                       {}
                    wPlayer.play(chooser.getSelectedFile().getAbsolutePath(),windowTable);
                    playing=true;
                
                        }
               
        
           }
           
            ////////////////////////////////////////////////////////////////////
           
           
           
            //PAUSE-ALTERNATE
            ////////////////////////////////////////////////////////////////////
                else if(e.getSource().equals(wPauseAlternate) )
                {
                        wPlayer.pause(); 
             
                }
            ////////////////////////////////////////////////////////////////////
            
                
                
            //STOP-ALTERNATE
            ////////////////////////////////////////////////////////////////////    
                    else if(e.getSource().equals(wStopAlternate))
                    {
                            wPlayer.stop();
                            playing=false;
                    }
            ////////////////////////////////////////////////////////////////////
                    
                    
                    
            //PLAY 
            ////////////////////////////////////////////////////////////////////
                     else if( e.getSource().equals(wPlay)||e.getSource().equals(wcPlay))
                     {
                        
                          int value;
                            if(player.getShuffle())
                            {
                                  Random rand = new Random(); 
                                 value = rand.nextInt(windowTable.getRowCount());
                                 windowTable.setRowSelectionInterval(value, value);
                            }
                            
                            int row =wSelectedRow;
                            wSelectedRow=windowTable.getSelectedRow();
                            if(playing==false){
                            //
                            
                                    if(wSelectedRow==0)
                                        return;
                                            else
                                            {
                                                    //inputStream= new FileInputStream(); 
                                                    //System.out.println(inputStream.toString());
                      try{
                    wPlayer = new MusicFunctions();
                       }
                       catch(Exception ee)
                       {}                                                    wPlayer.play((String)windowTable.getModel().getValueAt(windowTable.getSelectedRow(), 4),windowTable);
                                                    wPlayingRow=windowTable.getSelectedRow();
                                                    playing=true;
                                            }
                                }
                            else if(playing==true)
                            {
                               if(wSelectedRow==0)
                                        return;
                               if(wPlayer.getPlayerStatus()==paused&&wSelectedRow==row)
                                {
                                    //RESUME PLAYING SAME SONG
                                    wPlayer.play((String)windowTable.getModel().getValueAt(windowTable.getSelectedRow(), 4),windowTable);
                                    return;
                                }
                                //STOP OTHER SONG PLAYING SO WE CAN START NEW SONG
                                wPlayer.stop();
                                
                                //inputStream= new FileInputStream((String)table.getValueAt(table.getSelectedRow(), 4)); 
                      try{
                    wPlayer = new MusicFunctions();
                       }
                       catch(Exception ee)
                       {}                                wPlayer.play((String)windowTable.getModel().getValueAt(windowTable.getSelectedRow(), 4),windowTable);
                                playing=true;
                                   
                            }
                            
                    
                        

                     }

            ////////////////////////////////////////////////////////////////////
                     
                     
                     
                     
            //PAUSE 
            ////////////////////////////////////////////////////////////////////         
                         else if(e.getSource().equals(wPause))
                         {
                                wSelectedRow=windowTable.getSelectedRow();
                                
                                if(wSelectedRow==0)
                                    return;
                                else
                                 wPlayer.pause(); 
                                 
                         }
            ////////////////////////////////////////////////////////////////////
                         
                
                         
                         
            //STOP
            ////////////////////////////////////////////////////////////////////
                             else if(e.getSource().equals(wStop))
                             {
                                 wSelectedRow=windowTable.getSelectedRow();
                                 
                                 if(wSelectedRow==0)
                                     return;
                                 else{
                                     wPlayer.stop();
                                     playing=false;
                                 }
                             }
            ////////////////////////////////////////////////////////////////////
                             
                
                             
                             
            //SKIP-NEXT
            ////////////////////////////////////////////////////////////////////
                                 else if(e.getSource().equals(wSkipNext) || e.getSource().equals(wcNext))
                                 {
                                     if(playing==true)
                                     {
                                        wPlayer.stop();
                                     }
                                     
                                     wSelectedRow=windowTable.getSelectedRow();
                                     
                                     if(wSelectedRow==0)
                                         return;
                                     
                                     else if(wSelectedRow==(windowTable.getRowCount()-1))
                                         wSelectedRow=1;
                                     
                                        else
                                            selectedRow++;
                                     windowTable.setRowSelectionInterval(wSelectedRow, wSelectedRow);
                                     

                                        //inputStream= new FileInputStream((String)table.getValueAt(table.getSelectedRow(), 4));
                      try{
                    wPlayer = new MusicFunctions();
                       }
                       catch(Exception ee)
                       {}                                        playing=true;
                                        wPlayer.play((String)windowTable.getModel().getValueAt(windowTable.getSelectedRow(), 4),windowTable);
                                        
                                        wPlayingRow=windowTable.getSelectedRow();
                                    
                                 }
            ////////////////////////////////////////////////////////////////////                     
              
                
                                 
                                 
            //SKIP-PREVIOUS
            ////////////////////////////////////////////////////////////////////
                                     else if(e.getSource().equals(wSkipPrevious) || e.getSource().equals(wcPrevious))
                                     {
                                        if(playing==true)
                                        {
                                            wPlayer.stop();
                                        }
                                        
                                        wSelectedRow=windowTable.getSelectedRow();
                                        
                                        if(wSelectedRow==0)
                                            return;
                                            else if(wSelectedRow==1)
                                            {}
                                                else
                                                    wSelectedRow--;
                                        
                                        windowTable.setRowSelectionInterval(wSelectedRow, wSelectedRow);
             
                                               //inputStream= new FileInputStream((String)table.getValueAt(table.getSelectedRow(), 4));
                      try{
                    wPlayer = new MusicFunctions();
                       }
                       catch(Exception ee)
                       {}                                               playing=true;
                                               wPlayer.play((String)windowTable.getModel().getValueAt(windowTable.getSelectedRow(), 4),windowTable);
                                               
                                               wPlayingRow=windowTable.getSelectedRow();
                                     }
            ////////////////////////////////////////////////////////////////////                        
              
                   
                                     
                                     
            //ADD-SONG and ADD
            ////////////////////////////////////////////////////////////////////
                                        else if(e.getSource().equals(wAddSong) || e.getSource().equals(wAdd))
                                        {
                                            addChooser(windowTable, windowPlaylist);
                                            refreshBothPlaylists();
                                            sort(defaultSortName, defaultSortOrder);

                                        }
            ////////////////////////////////////////////////////////////////////                            
                                        
                         
                                        
                                        
                                        
            //DELETE-SONG and DELETE
            ////////////////////////////////////////////////////////////////////
                                            else if(e.getSource().equals(wDeleteSong)||e.getSource().equals(wDelete))
                                            {
                                                
                                    
                                                if(playing==true)
                                                {
                                                    wPlayer.stop();
                                                }
                                                wSelectedRow=windowTable.getSelectedRow();
                                                if(wSelectedRow==0)
                                                    return;
                                                else{
                                                     DefaultTableModel model = (DefaultTableModel) windowTable.getModel();

                                                        windowPlaylist.delete(model, wSelectedRow);
                                                        refreshBothPlaylists();
                                                }
                                            }
            ////////////////////////////////////////////////////////////////////
                                        
           
            
           
            //EXIT-ALTERNATE
            ////////////////////////////////////////////////////////////////////
                                            else if(e.getSource().equals(wExitAlternate))
                                            {
                                                if(playing==true||wPlayer.getPlayerStatus()==paused)
                                                {
                                                    wPlayer.stop();
                                                    newPanel.dispose();
                                                }
                                                else
                                                {
                                                        newPanel.dispose();
                                                }
                                            }
           
            
            ////////////////////////////////////////////////////////////////////
                                            
                                            
            //Scroll to Playing Song
            ////////////////////////////////////////////////////////////////////
                                                     else if(e.getSource().equals(wcGo2CurrentSong))
                                                     {
                                                         if(windowTable.getSelectedRow()==-1)
                                                          return;   
                                                         if(windowTable.getSelectedRow()!=wPlayingRow){
                                                             windowTable.setRowSelectionInterval(wPlayingRow, wPlayingRow);
                                                             wSelectedRow=wPlayingRow;
                                                         }
                                                           
                                                            scrollToVisible(windowTable,windowTable.getSelectedRow(),0);
                                                      
                                                     }
            ////////////////////////////////////////////////////////////////////
           
            //VOLUME Increase Or Decrease                              
            ////////////////////////////////////////////////////////////////////                                               
                else if(e.getSource().equals(wcIncreaseVol))
               {
                   int iVolume= wVolumeSlider.getValue();
                       iVolume+=5;
                       //int iVolume=((int)(volume*0.05)+(int)volume)*100;

                   if(iVolume>100)
                       wVolumeSlider.setValue(100);
                      else 
                          wVolumeSlider.setValue(iVolume);


               }
                  else if(e.getSource().equals(wcDecreaseVol))
                  {
                      int dVolume=wVolumeSlider.getValue();
                          dVolume-=5;

                      if(dVolume<0)
                          wVolumeSlider.setValue(0);
                         else
                              wVolumeSlider.setValue(dVolume);
                      //player.setVolume(volume);
                  }
            ////////////////////////////////////////////////////////////////////
                                        
        }   
        
    }


class TableRowTransferHandler extends TransferHandler {
  private int[] rows    = null;
  private int addIndex  = -1; //Location where items were added
  private int addCount  = 0;  //Number of items added.
  private final DataFlavor localObjectFlavor;
  private Object[] transferedObjects = null;
  private JComponent source = null;
  
  
  public TableRowTransferHandler() {
    localObjectFlavor = new ActivationDataFlavor(
      Object[].class, "application/x-java-object-array;class=java.lang.Object", "Array of Object");
  }
  
  
  @Override protected Transferable createTransferable(JComponent c) {
    source = c;
    JTable table = (JTable) c;
    DefaultTableModel model = (DefaultTableModel)table.getModel();
    ArrayList< Object > list = new ArrayList< Object >();
    for(int i: rows = table.getSelectedRows())
      list.add(model.getDataVector().elementAt(i));
    transferedObjects = list.toArray();
    return new DataHandler(transferedObjects,localObjectFlavor.getMimeType());
  }
  
  
  @Override public boolean canImport(TransferHandler.TransferSupport info) {
          System.out.println("canImport()");

    JTable t = (JTable)info.getComponent();
    boolean b = info.isDrop()&&info.isDataFlavorSupported(localObjectFlavor);
    //XXX bug?
    t.setCursor(b?DragSource.DefaultMoveDrop:DragSource.DefaultMoveNoDrop);
    return b;
  }
  
  
  @Override public int getSourceActions(JComponent c) {
          System.out.println("getSourceActions()");

    return TransferHandler.COPY_OR_MOVE;
  }
  
  
  @SuppressWarnings("unchecked")
  
  
  @Override public boolean importData(TransferHandler.TransferSupport info) {
    JTable target = (JTable)info.getComponent();
    JTable.DropLocation dl = (JTable.DropLocation)info.getDropLocation();
    DefaultTableModel model = (DefaultTableModel)target.getModel();
    int index = dl.getRow();
    int max = model.getRowCount();
    if(index<0 || index>max) index = max;
    addIndex = index;
    System.out.println("importData()");
    target.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    try {
      Object[] values =
        (Object[])info.getTransferable().getTransferData(localObjectFlavor);
      if(source==target) addCount = values.length;
      for(int i=0;i < values.length;i++) {
        int idx = index++;
        model.insertRow(idx, (Vector)values[i]);
        target.getSelectionModel().addSelectionInterval(idx, idx);
      }
      return true;
    }catch(Exception ufe) { ufe.printStackTrace(); }
    return false;
  }
  
  
  @Override protected void exportDone(JComponent c, Transferable t, int act) {
          System.out.println("exportDone()");

    cleanup(c, act == MOVE);
  }
  
  
  private void cleanup(JComponent src, boolean remove) {
          System.out.println("cleanup()");

    if(remove && rows != null) {
      JTable table = (JTable)src;
      src.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
      DefaultTableModel model = (DefaultTableModel)table.getModel();
      if(addCount > 0) {
        for(int i=0;i < rows.length;i++) {
          if(rows[i] >= addIndex) { rows[i] += addCount; }
        }
      }
      for(int i=rows.length-1;i >= 0;i--) model.removeRow(rows[i]);
    }
    rows     = null;
    addCount = 0;
    addIndex = -1;
  }
}
    
    public void refreshEverything()
    {
        
        try
        {
            con = DriverManager.getConnection("jdbc:derby://localhost/Music","abdulrahim","abdulrahim24a");
                        
            Statement sta=con.createStatement();
                        String sql="SELECT * FROM Last10PlayedSongs";
                       ResultSet rs = sta.executeQuery(sql);
               while(rs.next())
               {
                   String t = rs.getString("cTitle");
                   String p = rs.getString("cPath");
                   
                   last10SongsPlayed.add(new Last10SongsPlayed(t,p) );
               }
               last10PlayedSongs();
               
               ///////Get playlist names
                String TABLE_NAME = "TABLE_NAME";
                String TABLE_SCHEMA = "TABLE_SCHEM";
                String[] TABLE_TYPES = {"TABLE"};
                DatabaseMetaData dbmd = con.getMetaData();

                ResultSet tables = dbmd.getTables(null, null, null, TABLE_TYPES);
                while (tables.next()) {
                  playListNames.add(tables.getString(TABLE_NAME));
                  System.out.println(tables.getString(TABLE_NAME));
                  
                }
                
               String librarySelect="SELECT * FROM Library";
               ResultSet addtoLib = sta.executeQuery(librarySelect);
               while(addtoLib.next())
               {
                 String a = addtoLib.getString("cTitle");
                 String b = addtoLib.getString("cArtist");
                 String c = addtoLib.getString("cAlbum");
                 String d = addtoLib.getString("cYear");
                 String e = addtoLib.getString("cPath");
                 String f = addtoLib.getString("cGenre");
                 String g = addtoLib.getString("cComment");
                 String h = addtoLib.getString("cTime");
                model=(DefaultTableModel) table.getModel();
                 model.addRow(new Object[]{a,b,c,d,e,f,g,h});
               }

               
               for(int i=0;i<playListNames.size();i++)
               {
                   if(playListNames.get(i).equals("LIBRARY") || playListNames.get(i).equals("LAST10PLAYEDSONGS")||playListNames.get(i).equals("COLUMNVIEW"))
                       System.out.println();
                   else{
                        addObject(new DefaultMutableTreeNode(playListNames.get(i)));
                        playlistGroup.add(new Playlist(playListNames.get(i)));
                        model=(DefaultTableModel) table.getModel();
                        currentPlaylist=playlistGroup.get(playlistGroup.size()-1);
                        currentPlaylist.displayPlaylist(model);
                        addToPlaylist(currentPlaylist.getPlayListTitle());                   
                   }
               }
                
                currentPlaylist=playlistGroup.get(0);
                currentPlaylist.displayPlaylist(model);
                sort(defaultSortName, defaultSortOrder);
                SetToVisibleColumns();

               //////////
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        
        
    }
    
    public void SetToVisibleColumns()
    {
        try{
            Boolean visibility = false;
        int column = 0;
        String[] columns = {"Artist", "Album", "Year", "Genre", "Comments", "Time"};
        for(int i = 0; i<6; i++){
            String name = columns[i];
            System.out.println(name);
            visibility = getVisibility(name);
            System.out.println(visibility + " "+name);
            if(!visibility){
                TableColumnModel tcm = table.getColumnModel();
                tcm.getColumn(getColumnIndex(name)-1).setMinWidth(000);
                tcm.getColumn(getColumnIndex(name)-1).setMaxWidth(000);
            }
        }              
        DefaultTableModel model =(DefaultTableModel) table.getModel();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    public boolean getVisibility(String name) throws SQLException{
        String query = "SELECT VISIBILE FROM columnView where tablecolumn = '"+name+"'";
        ResultSet r = stmt.executeQuery(query);
        boolean visibility = false;
        while(r.next())
        {
            visibility=r.getBoolean(1);
        }
        return visibility;
     }
     
     public int getColumnIndex(String name) throws SQLException
     {
        String query = "SELECT * FROM columnView where tablecolumn = '"+name+"'";
        
        int row = 1;
        try {
            ResultSet rss = stmt.executeQuery(query);
            while(rss.next()){
                row = rss.getInt(3);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return row;
       
     }
    
    
    public void refreshBothPlaylists()
    {
        if(windowPlaylist!=null)
        {
        currentPlaylist.displayPlaylist(model);
        windowPlaylist.displayPlaylist((DefaultTableModel)windowTable.getModel());
        }
    }
    
    public static void last10PlayedSongs()
    {
        int c=0;
        int s=last10SongsPlayed.size()-1;
        cPlayRecent.removeAll();
     while(c<10&&s>=0){
         String title=last10SongsPlayed.get(s).getTitle();
      JCheckBoxMenuItem last10ListOption = new JCheckBoxMenuItem (title);
       last10ListOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {  
                int count=0;
                int start=last10SongsPlayed.size()-1;
                        

                while(count<10&&start>=0)
                {
                   if(e.getActionCommand().equals(last10SongsPlayed.get(start).getTitle()))
                   {
                     player.playControlRecent(last10SongsPlayed.get(start).getPath());
                   }
                   start--;
                   count++;
                           
                }

             //  table.getValueAt(table.getSelectedRow(), 4);
              // table.get;
                
            }
        });
            cPlayRecent.add(last10ListOption);
            s--;
            c++;
        }
    }
    
   public void addToPlaylist(String s)
   {
       JMenuItem playlistOption = new JMenuItem (s);
       playlistOption.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {  
               for(int i=1; i<playlistGroup.size(); i++)
               {
                                          System.out.println(e.getActionCommand()+" = "+playlistGroup.get(i).getPlayListTitle());

                   if(e.getActionCommand().equals(playlistGroup.get(i).getPlayListTitle()))
                   {
                        DefaultTableModel model = (DefaultTableModel) table.getModel();
                        title=table.getModel().getValueAt(table.getSelectedRow(),0).toString();
                        playlistGroup.get(i).add(table.getModel().getValueAt(table.getSelectedRow(), 4).toString(), model);
                   }
               }
             //  table.getValueAt(table.getSelectedRow(), 4);
              // table.get;
               refreshBothPlaylists();
                
            }
        });
       addToPlaylist.add(playlistOption);
   }
   public JPopupMenu columnView(JTable table){
        JPopupMenu header = new JPopupMenu();
        String query = "select * from columnView";
         try{
           stmt = conn.createStatement();
           ResultSet rs = stmt.executeQuery(query);
           while(rs.next()){//
              JCheckBox column = new JCheckBox(rs.getString(1));
              column.addActionListener(new ActionListener() {
                  public void actionPerformed(ActionEvent e){
                      JCheckBox cb = (JCheckBox) e.getSource();
                      String name = cb.getText();
                      String st;                   
                      try {
                           String colNum = "SELECT num from columnView where tablecolumn = '"+name+"'";  
                           if(cb.isSelected()){
                              System.out.println("checked true"); 
                              st = "UPDATE columnView SET VISIBILE=true WHERE tablecolumn='"+name+"'";
                              int x = getColumn(name);
                              stmt.executeUpdate(st);
                              //RestoreColumn
                                TableColumnModel tcm = table.getColumnModel();
                                tcm.getColumn(x-1).setMinWidth(100);
                                tcm.getColumn(x-1).setMaxWidth(100);
                                TableColumn col = table.getColumnModel().getColumn(x-1);
                                int width = 200;
                                col.setPreferredWidth(width);
                             
                              
                            } else {//if cb is not selected
                                System.out.println("checked false");
                                //put the method here 
                                int x = getColumn(name);
                                st = "UPDATE columnView SET VISIBILE=false WHERE tablecolumn='"+name+"'";
                            
                               stmt.executeUpdate(st);   
                               //Hide Column
                               TableColumnModel tcm = table.getColumnModel();
                                tcm.getColumn(x-1).setMinWidth(000);
                                tcm.getColumn(x-1).setMaxWidth(000);
                            }   
                      } catch (SQLException ex) {
                            ex.printStackTrace();                      
                      }                                          
                  }
              });               
              column.setSelected(rs.getBoolean(2));             
              header.add(column);
           }
        } catch(SQLException e){
           System.out.println(e);
        }             
        return header;
     }
   
    public int getColumn(String name) throws SQLException{
        String query = "SELECT * FROM columnView where tablecolumn = '"+name+"'";
        
        int row = 1;
        try {
            ResultSet rss = stmt.executeQuery(query);
            while(rss.next()){
                row = rss.getInt(3);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.out.println(row+" inside getColumn");
        return row;
       
     }
   public static void scrollToVisible(JTable table, int rowIndex, int vColIndex) {
        if (!(table.getParent() instanceof JViewport)) {
            return;
        }
        JViewport viewport = (JViewport)table.getParent();

        // This rectangle is relative to the table where the
        // northwest corner of cell (0,0) is always (0,0).
        Rectangle rect = table.getCellRect(rowIndex, vColIndex, true);

        // The location of the viewport relative to the table
        Point pt = viewport.getViewPosition();

        // Translate the cell location so that it is relative
        // to the view, assuming the northwest corner of the
        // view is (0,0)
        rect.setLocation(rect.x-pt.x, rect.y-pt.y);

        table.scrollRectToVisible(rect);

        // Scroll the area into view
        //viewport.scrollRectToVisible(rect);
    }
   
   
   
 
}