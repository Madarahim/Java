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
public class setTime {
    int minutes;
    int seconds;
    int finishMin;
    int finishSec;
    
    setTime(int m, int s){
        minutes = m;
        seconds = s;
        System.out.println("setting minuts and sec minute:"+minutes+" seconds:"+seconds);
    }
    public void setMin(int m){
        minutes = m;
        
        System.out.println("minutes:"+minutes);
    }
    
    public void setSec(int n){
        
        seconds = n;
        
        System.out.println("seconds:"+seconds);
    }
    public void setBoth(int n, int m){
        minutes = n;
        seconds = m;
    }
    
    public int returnMin(){
        System.out.println("minutes:"+minutes);
        return minutes;
    }
    public int returnSec(){
        
        System.out.println("seconds:"+seconds);
        return seconds;
    }
}
