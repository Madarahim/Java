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
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.beans.*;
import java.util.Random;
 
public class ProgressBar extends JPanel implements ActionListener, PropertyChangeListener {
 
    private JProgressBar progressBar;
    Timer timer;
    int progTime;
    
    public void setTime(int time, Boolean test){
        timer.stop();
        progressBar.setMaximum(time);
        ActionListener listener = new ActionListener() {
            int counter = 0;
            public void actionPerformed(ActionEvent ae) {
                counter++;
                progressBar.setValue(counter);
                System.out.println(counter);
                if (counter>time) {
                    timer.stop();
                } 
            }
        };
        timer = new Timer(1000, listener);
        timer.start();
    }
 
    public void setTime(int time) {
        /*
         * Main task. Executed in background thread.
         */
        progressBar.setMaximum(time);
        ActionListener listener = new ActionListener() {
            int counter = 0;
            public void actionPerformed(ActionEvent ae) {
                counter++;
                progressBar.setValue(counter);
                System.out.println(counter);
                if (counter>time) {
                    timer.stop();
                } 
            }
        };
        timer = new Timer(1000, listener);
        timer.start();
    }
 
    public ProgressBar() {
        super(new BorderLayout());
        
        progressBar = new JProgressBar(0, 100);
        Dimension prefSize = progressBar.getPreferredSize();
        prefSize.width = 800;
        prefSize.height = 20;
        progressBar.setPreferredSize(prefSize);
       
        //progressBar.setBorder(BorderFactory.createBevelBorder(0, Color.lightGray, Color.yellow));
        progressBar.setValue(0);
        //progressBar.setStringPainted(true);
        
        JPanel panel = new JPanel();
        panel.add(progressBar);
 
        add(panel, BorderLayout.PAGE_START);
 
    }
    
    public void stop(){
        timer.stop();
    }
    
    public void start(){
        timer.start();
    }
    
    public void restart(){
        timer.restart();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
 
    /**
     * Invoked when the user presses the start button.
     */
    
}
