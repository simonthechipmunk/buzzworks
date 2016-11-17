/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buzzworks;

import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.ArrayList;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;



/**
 *
 * @author Simon Junga (simonthechipmunk)
 */
public class BuzzWorks {
    
    private static DialogSerialSelect dialogserial;
    private static mainWindow mainwindow;
    private static Serial serial;
    private static ArrayList<Integer> teamlist;
    
    private static ArrayList<Boolean> checkboxes;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //set look and feel
        try {
            // Set System L&F
            UIManager.setLookAndFeel(
            UIManager.getSystemLookAndFeelClassName());
        } 
        catch (UnsupportedLookAndFeelException e) {
        // handle exception
        }
        catch (ClassNotFoundException e) {
        // handle exception
        }
        catch (InstantiationException e) {
        // handle exception
        }
        catch (IllegalAccessException e) {
       // handle exception
        }
        
        try{
            Toolkit xToolkit = Toolkit.getDefaultToolkit();
            
            //set Application Name
            java.lang.reflect.Field awtAppClassNameField = xToolkit.getClass().getDeclaredField("awtAppClassName");
            awtAppClassNameField.setAccessible(true);
            awtAppClassNameField.set(xToolkit, "Buzzworks");
            
        }
        catch (Exception e){
            e.printStackTrace();
        }
        
        
        
        
        try
        {
            //init
            teamlist = new ArrayList();
            checkboxes = new ArrayList();
            
            //start serial connection
            serial = new Serial();
            
            //user serial selection
            dialogserial = new DialogSerialSelect(serial, teamlist, checkboxes);
            dialogserial.setVisible(true);
            
            //start mainview when finished with serial connection
            dialogserial.addWindowListener(new WindowAdapter() {            
                public void windowClosed(WindowEvent e) {
                    try {
                        //start main window
                        mainwindow = new mainWindow(serial, teamlist, checkboxes);
                        mainwindow.setVisible(true);
                        
                        //close serial on exit. well we at least try.... shit doesn't work
                        mainwindow.addWindowListener(new WindowAdapter() {            
                        public void windowClosed(WindowEvent e) {
                            try {
                                //start main window
                                serial.close();
                            } catch (Throwable t) {
                                t.printStackTrace();
                            }                
                        }
                    }); 
                        
                    } catch (Throwable t) {
                        t.printStackTrace();
                    }                
                }
            });
            
            

        }
        catch ( Exception e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}

